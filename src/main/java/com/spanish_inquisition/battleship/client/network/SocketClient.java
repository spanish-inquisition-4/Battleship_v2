package com.spanish_inquisition.battleship.client.network;

import com.spanish_inquisition.battleship.common.AppLogger;
import com.spanish_inquisition.battleship.common.Header;

import java.io.*;
import java.net.Socket;
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
    private static final int THREAD_TIMEOUT = 3_000; //[ms]

    Socket socket;
    BufferedReader input;
    PrintWriter output;
    private ResponsesBus responsesBus = new ResponsesBus();
    private boolean isRunning = true;
    Thread updatesThread;

    SocketClient() {}

    public ResponsesBus getResponsesBus() { return responsesBus; }

    void setUpStreamsOnSocket() throws IOException {
        logger.log(DEFAULT_LEVEL, "Setting up streams and socket");
        input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    public static SocketClient createSocketClientWithSocket(String hostName, int port) throws IOException {
        SocketClient socketClient = new SocketClient();
        socketClient.socket = new Socket(hostName, port);
        socketClient.setUpStreamsOnSocket();
        socketClient.createUpdatesThreadAndRunIt();
        return socketClient;
    }

    public void createUpdatesThreadAndRunIt() {
        logger.log(DEFAULT_LEVEL, "Creating a thread for reading server updates");
        updatesThread = new Thread(this::readServerUpdatesContinuously);
        updatesThread.start();
    }

    private void readServerUpdatesContinuously() {
        logger.log(DEFAULT_LEVEL, "Reading server updates continously");
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

    String readUpdateFromServer() throws IOException {
        if (socket != null && output != null && input != null) {
                return input.readLine();
        }
        return "";
    }

    public void closeTheSocketClient() {
        try {
            sendStringToServer(Header.EXIT.toString());
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
