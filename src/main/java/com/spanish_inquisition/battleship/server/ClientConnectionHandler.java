package com.spanish_inquisition.battleship.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnectionHandler extends Thread {
    String name;
    BufferedReader clientInput;
    PrintWriter clientOutput;

    public ClientConnectionHandler(ServerSocket serverSocket) throws IOException {
        Socket clientSocket = serverSocket.accept();
        clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void run() {
        name = acceptNameFromClient();
        // todo: loop for handling player requests
    }

    private String acceptNameFromClient() {
        String readName = "";
        try {
            while(!clientInput.ready()) {
                Thread.sleep(10);
            }
            readName = clientInput.readLine();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return readName;
    }
}