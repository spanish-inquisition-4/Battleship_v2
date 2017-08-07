package com.spanish_inquisition.battleship.server;

import com.spanish_inquisition.battleship.server.bus.MessageBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

import static com.spanish_inquisition.battleship.common.AppLogger.DEFAULT_LEVEL;
import static com.spanish_inquisition.battleship.common.AppLogger.logger;
import static com.spanish_inquisition.battleship.server.BattleshipServer.SERVER_ID;

public class ClientConnectionHandler extends Thread {
    String name;
    Socket clientSocket;
    BufferedReader clientInput;
    PrintWriter clientOutput;
    final int clientId;
    private MessageBus requestBus;
    boolean isConnected;

    public ClientConnectionHandler(final int clientId, MessageBus requestBus) {
        this.clientId = clientId;
        this.requestBus = requestBus;
        this.isConnected = true;
    }

    public int getClientId(){
        return clientId;
    }

    public String getPlayerName() {
        return name;
    }

    public void disconnect() {
        isConnected = false;
    }

    public void initializeSocket(final ServerSocket serverSocket) throws IOException {
        clientSocket = serverSocket.accept();
    }

    public void setUpStreams() throws IOException {
        clientInput = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream(),
                        StandardCharsets.UTF_8));
        clientOutput = new PrintWriter(
                new OutputStreamWriter(clientSocket.getOutputStream(),
                        StandardCharsets.UTF_8), true);
    }

    public void run() {
        name = acceptNameFromClient();

        while(clientSocket.isConnected() && isConnected) {
            sendMessageToUser(clientOutput);
            getMessageFromUser(clientInput);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.log(DEFAULT_LEVEL, name + " client connection handler thread interrupted");
            }
        }
    }

    private String acceptNameFromClient() {
        String readName = "";
        try {
            if(clientInput != null) {
                while (!clientInput.ready()) {
                    Thread.sleep(10);
                }
                readName = clientInput.readLine();
            }
        } catch (IOException | InterruptedException e) {
            logger.log(Level.WARNING, "couldn't read name from client", e);
        }
        return readName;
    }

    private void sendMessageToUser(PrintWriter output) {
        if(output != null) {
            if (requestBus.haveMessageForRecipient(clientId)) {
                String messageToSend = requestBus.getMessageFor(clientId).getContent();
                output.println(messageToSend);
            }
        }
    }

    private void getMessageFromUser(BufferedReader input) {
        try {
            if(input != null) {
                if (input.ready()) {
                    String lineFromClient = input.readLine();
                    if (lineFromClient.trim().equals("EXIT")) {
                        isConnected = false;
                    } else {
                        requestBus.addMessage(clientId, SERVER_ID, lineFromClient);
                    }
                }
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could't read message from user: " + name + " with ID: " + clientId, e);
        }
    }
}