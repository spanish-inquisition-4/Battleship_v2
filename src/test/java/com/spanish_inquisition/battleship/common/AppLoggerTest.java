package com.spanish_inquisition.battleship.common;

import org.testng.annotations.Test;

import java.util.logging.Level;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * @author Michal_Partacz
 */
public class AppLoggerTest {

    @Test
    public void testInitializeLogger() throws Exception {
        assertNull(AppLogger.handler);
        AppLogger.initializeLogger();
        assertNotNull(AppLogger.handler);
        assertEquals((AppLogger.logger.getHandlers().length), 1);
        assertEquals(AppLogger.logger.getLevel(), Level.CONFIG);
    }

}