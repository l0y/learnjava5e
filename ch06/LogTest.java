package ch06;

import java.util.logging.*;

/**
 * A simple example of creating a logger and then using some of its methods.
 * For homework, try adjusting the log level where the comment is and see
 * which lines of output you still get on the console.
 */ 
public class LogTest {
    public static void main(String argv[]) {
        Logger logger = Logger.getLogger("com.oreilly.LogTest");

		// try setting the log level here
        logger.severe("Power lost - running on backup!");
        logger.warning("Database connection lost, retrying...");
        logger.info("Startup complete.");
        logger.config("Server configuration: standalone, JVM version 1.5");
        logger.fine("Loading graphing package.");
        logger.finer("Doing pie chart");
        logger.finest("Starting bubble sort: value =" + 42);
    }
}
