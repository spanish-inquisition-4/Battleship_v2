package com.spanish_inquisition.battleship.common;

import java.io.IOException;
import java.util.logging.*;

/**
 * @author Michal_Partacz
 *         A logger which will store the logs in a file specified in path of the handler file and of level specified in newLogger's
 *         class. There is also a formatter which will format the output of the logger before saving it in the logfile.
 *         The class should be initialized as soon as the app starts by static invoke of the initializeLogger method
 */
public class AppLogger {
    public static final Logger logger = Logger.getLogger(AppLogger.class.getName());
    static Handler handler = null;
    public static final Level DEFAULT_LEVEL = Level.CONFIG;

    /**
     * This method will initialize the logger with the path and a name of the logfile, level of the logging info and
     * a formatter which will modify the logged information.
     */
    public static void initializeLogger() {
        try {
            handler = new FileHandler("Battleship.log", false);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not create file");
            e.printStackTrace();
        }
        Logger newLogger = Logger.getLogger(AppLogger.class.getName());
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
        logger.setLevel(Level.CONFIG);
    }
}
