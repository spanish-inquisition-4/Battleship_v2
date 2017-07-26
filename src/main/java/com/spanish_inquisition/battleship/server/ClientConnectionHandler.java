package com.spanish_inquisition.battleship.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.spanish_inquisition.battleship.common.AppLogger.DEFAULT_LEVEL;
import static com.spanish_inquisition.battleship.common.AppLogger.logger;

public class ClientConnectionHandler extends Thread{
    Socket clientSocket;
    String name;

    public ClientConnectionHandler(ServerSocket serverSocket) throws IOException {
        clientSocket = serverSocket.accept();
    }

    public void run() {
        logger.log(DEFAULT_LEVEL, "Hello from thread " + Thread.currentThread().getName());

    }
}
