package com.spanish_inquisition.battleship.client.network;

import com.spanish_inquisition.battleship.common.Header;
import com.spanish_inquisition.battleship.common.NetworkMessage;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Michal_Partacz
 */
public class ResponsesBusTest {

    @Test
    public void testHasServerResponses() throws Exception {
        ResponsesBus responsesBus = new ResponsesBus();
        assertFalse(responsesBus.hasServerResponses());
        responsesBus.addAServerResponse(Header.UNKNOWN.name());
        assertTrue(responsesBus.hasServerResponses());
    }

    @Test
    public void testGetAServerResponse() throws Exception {
        ResponsesBus responsesBus = new ResponsesBus();
        Header header = Header.UNKNOWN;
        responsesBus.addAServerResponse(header.name());
        assertEquals(new NetworkMessage(header, ""), responsesBus.getAServerResponse());
    }

    @Test
    public void testAddAServerResponse() throws Exception {
        ResponsesBus responsesBus = new ResponsesBus();
        assertFalse(responsesBus.hasServerResponses());
        responsesBus.addAServerResponse("");
        assertFalse(responsesBus.hasServerResponses());
    }

}