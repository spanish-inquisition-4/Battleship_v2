package com.spanish_inquisition.battleship.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

import static com.spanish_inquisition.battleship.common.AppLogger.logger;

public class ClientConnectionHandler extends Thread {
    String name;
    BufferedReader clientInput;
    PrintWriter clientOutput;

    public void initializeSocketStreams(ServerSocket serverSocket) throws IOException {
        Socket clientSocket = serverSocket.accept();
        clientInput = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream(),
                        StandardCharsets.UTF_8));
        clientOutput = new PrintWriter(
                new OutputStreamWriter(clientSocket.getOutputStream(),
                        StandardCharsets.UTF_8), true);
    }

    public void run() {
        name = acceptNameFromClient();
        // todo: loop for handling player requests
    }

    private String acceptNameFromClient() {
        String readName = "";
        try {
            while (!clientInput.ready()) {
                Thread.sleep(10);
            }
            readName = clientInput.readLine();
        } catch (IOException | InterruptedException e) {
            logger.log(Level.WARNING, "couldn't read name from client", e);
        }
        return readName;
    }
}