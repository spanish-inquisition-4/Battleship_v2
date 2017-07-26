package com.spanish_inquisition.battleship.client.network;

import com.spanish_inquisition.battleship.common.AppLogger;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Michal_Partacz
 */
public class SocketClient {
    private final static String HOST_NAME = "localhost";
    private final static Logger logger = Logger.getLogger(AppLogger.class.getName());

    final static int PORT = 6666;
    private Level level = Level.CONFIG;

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    void setUpStreamsAndOpenSocket() throws IOException {
        logger.log(level, "Setting up streams and socket");
        socket = new Socket(HOST_NAME, PORT);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
    }
}
