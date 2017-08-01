package com.spanish_inquisition.battleship.client.network;

import com.spanish_inquisition.battleship.common.AppLogger;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spanish_inquisition.battleship.common.AppLogger.DEFAULT_LEVEL;

/**
 * @author Michal_Partacz
 */
public class SocketClient {
    private static final String HOST_NAME = "localhost";
    static int PORT = 6666;
    private static final Logger logger = Logger.getLogger(AppLogger.class.getName());
    private static final int THREAD_TIMEOUT = 100_000; //[ms]

    Socket socket;
    BufferedReader input;
    PrintWriter output;
    private ResponsesBus responsesBus = new ResponsesBus();
    private boolean isRunning = true;
    private Thread updatesThread;

    SocketClient() {}

    void setUpStreamsOnSocket() throws IOException {
        logger.log(DEFAULT_LEVEL, "Setting up streams and socket");
        input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    public static SocketClient createSocketClientWithSocket() throws IOException {
        SocketClient socketClient = new SocketClient();
        socketClient.socket = new Socket(HOST_NAME, PORT);
        socketClient.setUpStreamsOnSocket();
        socketClient.createUpdatesThreadAndRunIt();
        return socketClient;
    }

    private void createUpdatesThreadAndRunIt() {
        updatesThread = new Thread(this::readServerUpdatesContinuously);
        updatesThread.start();
    }

    private void readServerUpdatesContinuously() {
        while (isRunning) {
            try {
                this.responsesBus.addAServerResponse(readUpdateFromServer());
                Thread.sleep(100);
            } catch (IOException | InterruptedException e) {
                logger.log(DEFAULT_LEVEL, "An exception while reading server responses: ", e);
                break;
            }
        }
    }

    private String readUpdateFromServer() throws IOException {
        if (socket != null && output != null && input != null) {
            try {
                return input.readLine();
            } catch (UnknownHostException e) {
                logger.warning("Trying to connect to unknown host: " + e);
            }
        }
        return "";
    }

    public void closeTheSocketClient() {
        try {
            isRunning = false;
            updatesThread.join(THREAD_TIMEOUT);
            updatesThread.interrupt();
            output.close();
            input.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            logger.log(DEFAULT_LEVEL, "An exception while closing the SocketClient: ", e);
        }
    }

    public void sendStringToServer(String string) {
        try {
            logger.log(DEFAULT_LEVEL, "Attempting sent string to server = " + string);
            this.output.println(string);
            logger.log(DEFAULT_LEVEL, "Sent string to server = " + string);
        } catch (NullPointerException e) {
            logger.log(Level.WARNING, "Could not send " + string + " to server", e);
        }
    }
}
