package com.spanish_inquisition.battleship.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static com.spanish_inquisition.battleship.common.AppLogger.DEFAULT_LEVEL;
import static com.spanish_inquisition.battleship.common.AppLogger.initializeLogger;
import static com.spanish_inquisition.battleship.common.AppLogger.logger;

public class BattleshipServer {
    private static final int NUMBER_OF_PLAYERS = 2;
    private static final Integer PORT_NUMBER = 6666;
    static List<ClientConnectionHandler> clients;

    public static void main(String[] args) {
        initializeLogger();
        ServerSocket serverSocket = createServerSocket();
        connectWithPlayers(serverSocket);
    }

    static void connectWithPlayers(ServerSocket serverSocket) {
        List<ClientConnectionHandler> clientSockets = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            new Thread(()->{
                ClientConnectionHandler clientConnectionHandler = null;
                try {
                    clientConnectionHandler = new ClientConnectionHandler(serverSocket);
                    clientConnectionHandler.start();
                } catch (IOException e) {
                    logger.log(DEFAULT_LEVEL, "There was an error while waiting for connection");
                }
                clientSockets.add(clientConnectionHandler);
            }).run();
        }
        clients =  clientSockets;
    }

    static ServerSocket createServerSocket() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
        } catch (IOException e) {
            logger.log(Level.WARNING, Arrays.toString(e.getStackTrace()));
            System.exit(-1);
        }
        return serverSocket;
    }

    static void acceptNameFromPlayers() {
    }
}