package com.spanish_inquisition.battleship.common;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Michal_Partacz
 */
public class NetworkMessage {
    private static final String RESPONSE_SPLIT_CHARACTER = ";";
    private static final String RESPONSE_HEADER_SPLIT_CHARACTER = ":";

    private Header header;
    private String body;

    public NetworkMessage(Header header, String body) {
        this.header = header;
        this.body = body;
    }

    public static List<NetworkMessage> parseServerResponse(String rawResponse) {
        String[] responses = rawResponse.split(RESPONSE_SPLIT_CHARACTER);
        List<NetworkMessage> parsedResponses = new LinkedList<>();
        for (String stringResponse : responses) {
            addToListIfCorrect(parsedResponses, stringResponse);
        }
        return parsedResponses;
    }

    public static void addToListIfCorrect(List<NetworkMessage> list, String response) {
        try {
            NetworkMessage clientServerMessage = parseSingleResponse(response);
            list.add(clientServerMessage);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Malformed server response: " + response);
        }
    }

    public static NetworkMessage parseSingleResponse(String rawSingleResponse) throws ArrayIndexOutOfBoundsException {
        String[] responseParts = rawSingleResponse.split(RESPONSE_HEADER_SPLIT_CHARACTER);
        Header header = Header.parseResponseHeader(responseParts[0]);
        String body = "";
        if(responseParts.length > 1) {
            body = responseParts[1];
        }
        return new NetworkMessage(header, body);
    }
}
