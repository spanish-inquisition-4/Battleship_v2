package com.spanish_inquisition.battleship.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.spanish_inquisition.battleship.common.AppLogger.initializeLogger;
import static com.spanish_inquisition.battleship.common.AppLogger.logger;


public class BattleshipServer {
    private static final Integer PORT_NUMBER = 6666;
    final int NUMBER_OF_PLAYERS = 2;
    List<ClientConnectionHandler> clients;

    public void proceed(){
        initializeLogger();
        connectWithPlayers(createServerSocket(PORT_NUMBER));
    }

    ServerSocket createServerSocket(int portNumber) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            logger.log(Level.WARNING, "could't create server socket", e);
            System.exit(-1);
        }
        return serverSocket;
    }

    void connectWithPlayers(ServerSocket serverSocket) {
        clients = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            ClientConnectionHandler clientConnectionHandler = null;
            try {
                clientConnectionHandler = new ClientConnectionHandler();
                clientConnectionHandler.initializeSocketStreams(serverSocket);
                clientConnectionHandler.start();
            } catch (IOException e) {
                logger.log(Level.WARNING, "couldn't accept connection from client", e);
            }
            clients.add(clientConnectionHandler);
        }
    }
}