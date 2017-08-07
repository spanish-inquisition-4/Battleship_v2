package com.spanish_inquisition.battleship.client.network;

import com.spanish_inquisition.battleship.common.NetworkMessage;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.spanish_inquisition.battleship.common.AppLogger.DEFAULT_LEVEL;
import static com.spanish_inquisition.battleship.common.AppLogger.logger;

/**
 * @author Michal_Partacz
 */
public class ResponsesBus {
    private Queue<NetworkMessage> serverResponses;

    ResponsesBus() {
        this.serverResponses = new ConcurrentLinkedQueue<>();
    }

    public boolean hasServerResponses() {
        return !this.serverResponses.isEmpty();
    }

    public NetworkMessage getAServerResponse() {
        return this.serverResponses.poll();
    }

    void addAServerResponse(String response) {
        logger.log(DEFAULT_LEVEL, "Response from server : " +response);
        if (response == null || response.isEmpty()) {
            logger.info("Response empty");
            return;
        }
        List<NetworkMessage> clientServerMessages = NetworkMessage.Parser.parseServerResponse(response);
        this.serverResponses.addAll(clientServerMessages);
    }
}
