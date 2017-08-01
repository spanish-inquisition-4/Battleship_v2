package com.spanish_inquisition.battleship.common;

import org.testng.annotations.Test;

import java.util.List;

import static com.spanish_inquisition.battleship.common.NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER;
import static com.spanish_inquisition.battleship.common.NetworkMessage.RESPONSE_SPLIT_CHARACTER;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Michal_Partacz
 */
public class ParserTest {
    @Test
    public void testParseServerResponse() throws Exception {
        Header firstHeader = Header.UNKNOWN;
        Header secondHeader = Header.NAME;
        String body = "Name";
        String rawString = firstHeader + RESPONSE_SPLIT_CHARACTER +
                secondHeader + RESPONSE_HEADER_SPLIT_CHARACTER + body;
        List<NetworkMessage> responses = NetworkMessage.Parser.parseServerResponse(rawString);
        assertEquals(2, responses.size());
        assertTrue(responses.contains(new NetworkMessage(firstHeader, "")));
        assertTrue(responses.contains(new NetworkMessage(secondHeader, body)));
    }

    @Test
    public void testParseSingleResponse_withBody() throws Exception {
        String body = "42";
        Header header = Header.UNKNOWN;
        String stringToParse = header.name() + RESPONSE_HEADER_SPLIT_CHARACTER + body;
        NetworkMessage networkMessage = NetworkMessage.Parser.parseSingleResponse(stringToParse);
        assertEquals(new NetworkMessage(header, body), networkMessage);
    }

    @Test
    public void testParseSingleResponse_withoutBody() throws Exception {
        String body = "";
        Header header = Header.UNKNOWN;
        String stringToParse = header.name();
        NetworkMessage networkMessage = NetworkMessage.Parser.parseSingleResponse(stringToParse);
        assertEquals(new NetworkMessage(header, body), networkMessage);
    }

}