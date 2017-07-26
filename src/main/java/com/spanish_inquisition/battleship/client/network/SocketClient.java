package com.spanish_inquisition.battleship.client.network;

import com.spanish_inquisition.battleship.common.AppLogger;

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
    private final static String HOST_NAME = "localhost";
  
    final static int PORT = 6666;
    private final static Logger logger = Logger.getLogger(AppLogger.class.getName());

    Socket socket;
    BufferedReader input;
    PrintWriter output;

    SocketClient() {}

    public void setUpStreamsOnSocket() throws IOException {
        logger.log(DEFAULT_LEVEL, "Setting up streams and socket");
        input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    public static SocketClient createSocketClientWithSocket() throws IOException {
        SocketClient socketClient = new SocketClient();
        socketClient.socket = new Socket(HOST_NAME, PORT);
        socketClient.setUpStreamsOnSocket();
        return socketClient;
    }

    public void sendStringToServer(String string) {
        try {
            this.output.println(string);
            logger.log(DEFAULT_LEVEL, "sent string to server = " + string);
        } catch (NullPointerException e) {
            logger.log(Level.WARNING, "Could not send " + string + " to server", e);
        }
    }
}
