package com.spanish_inquisition.battleship.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import static com.spanish_inquisition.battleship.common.AppLogger.defaultLevel;
import static com.spanish_inquisition.battleship.common.AppLogger.initializeLogger;
import static com.spanish_inquisition.battleship.common.AppLogger.logger;

public class BattleshipServer {
    private static final int NUMBER_OF_PLAYERS = 2;
    private static final Integer PORT_NUMBER = 6666;
    private static List<Socket> clients;


    public static void main(String[] args) throws IOException {
        initializeLogger();

        ServerSocket serverSocket = createServerSocket();
        clients = connectWithPlayers(serverSocket);
        connectWithPlayers(serverSocket);
    }

    static List<Socket> connectWithPlayers(ServerSocket serverSocket) {
        List<Socket> clientSockets = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            new Thread(()->{
                System.out.println("Hello from thread " + Thread.currentThread().getName());
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    logger.log(defaultLevel, "Client didn't connect with server");
                }
                clientSockets.add(clientSocket);
            }).run();
        }
        return clientSockets;
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
}