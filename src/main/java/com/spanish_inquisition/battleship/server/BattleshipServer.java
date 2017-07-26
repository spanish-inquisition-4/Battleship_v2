package com.spanish_inquisition.battleship.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.spanish_inquisition.battleship.common.AppLogger.*;

public class BattleshipServer {
    private static final int NUMBER_OF_PLAYERS = 2;
    private static final Integer PORT_NUMBER = 6666;
    private static List<Socket> clients;

    public static void main(String[] args) {
        initializeLogger();
        ServerSocket serverSocket = createServerSocket(PORT_NUMBER);
        clients = connectWithPlayers(serverSocket);
        connectWithPlayers(serverSocket);
    }

    static List<Socket> connectWithPlayers(ServerSocket serverSocket) {
        List<Socket> clientSockets = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            new Thread(()->{
                logger.log(DEFAULT_LEVEL, "Hello from thread " + Thread.currentThread().getName());
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    logger.log(DEFAULT_LEVEL, "There was an error while waiting for connection");
                }
                clientSockets.add(clientSocket);
            }).run();
        }
        return clientSockets;
    }

    static ServerSocket createServerSocket(int portNumber) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Exception during socket creation: " +e);
            System.exit(-1);
        }
        return serverSocket;
    }
}