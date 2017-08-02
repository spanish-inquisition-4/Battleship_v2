package com.spanish_inquisition.battleship.server.fleet;

import com.spanish_inquisition.battleship.common.NetworkMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.spanish_inquisition.battleship.common.AppLogger.logger;


public class FleetParser {
    public static List<Integer> parseMessageToIntegersList(String message) {
        String[] messageSplitted = message.split(String.valueOf(NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER));
        return parseStringArrayToIntegerList(StringToStringArrayParser.parse(messageSplitted[1]));
    }

    private static List<Integer> parseStringArrayToIntegerList(String[] strArr) {
        List<Integer> intList = new ArrayList<>(strArr.length);
        for (String aStrArr : strArr) {
            try {
                intList.add(Integer.parseInt(aStrArr));
            } catch (NumberFormatException nfe) {
                logger.log(Level.WARNING, "wrong number format", nfe);
            }
        }
        return intList;
    }

}
