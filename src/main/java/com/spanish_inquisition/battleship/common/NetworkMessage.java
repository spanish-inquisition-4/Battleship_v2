package com.spanish_inquisition.battleship.common;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Michal_Partacz
 */
public class NetworkMessage {
    public static final String RESPONSE_SPLIT_CHARACTER = ";";
    public static final String RESPONSE_HEADER_SPLIT_CHARACTER = ":";

    private Header header;
    private String body;

    public Header getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public NetworkMessage(Header header, String body) {
        this.header = header;
        this.body = body;
    }

    public static class Parser {

        public static List<NetworkMessage> parseServerResponse(String rawResponse) {
            String[] responses = rawResponse.split(RESPONSE_SPLIT_CHARACTER);
            return Arrays.stream(responses).map(Parser::parseSingleResponse).collect(Collectors.toList());
        }


        static NetworkMessage parseSingleResponse(String rawSingleResponse) {
            String[] responseParts = rawSingleResponse.split(RESPONSE_HEADER_SPLIT_CHARACTER);
            Header header = Header.parseResponseHeader(responseParts[0]);
            String body = "";
            if (responseParts.length > 1) {
                body = responseParts[1];
            }
            return new NetworkMessage(header, body);
        }
    }

    @Override
    public String toString() {
        return "NetworkMessage{" + "header=" + header + ", body='" + body + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NetworkMessage that = (NetworkMessage) o;
        return header == that.header && (body != null ? body.equals(that.body) : that.body == null);
    }

    @Override
    public int hashCode() {
        int result = header != null ? header.hashCode() : 0;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }
}
