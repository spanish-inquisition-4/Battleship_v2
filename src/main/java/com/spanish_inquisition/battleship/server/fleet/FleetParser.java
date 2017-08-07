package com.spanish_inquisition.battleship.server.fleet;

import com.spanish_inquisition.battleship.common.NetworkMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.spanish_inquisition.battleship.common.AppLogger.logger;

class FleetParser {

    static List<Integer> parseMessageToIntegersList(String message) {
        String[] messageSplitted = message.split(String.valueOf(NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER));
        return parseStringArrayToIntegerList(StringToStringArrayParser.parse(messageSplitted[1]));
    }

    private static List<Integer> parseStringArrayToIntegerList(String[] fleet) {
        List<Integer> intList = new ArrayList<>(fleet.length);
        for (String shipIndex : fleet) {
            try {
                intList.add(Integer.parseInt(shipIndex));
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "wrong number format", e);
            }
        }
        return intList;
    }
}
