package logger;

import com.sample.ComLoggerApp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BusinessModuleLog {

    private static final Logger LOGGER = LogManager.getLogger(BusinessModuleLog.class);

    public static void print(){
        Thread.currentThread().setName("BusinessModuleThread");
        // log an error
        LOGGER.error("This is a Test-Err Message.");

        // Simply log info.
        LOGGER.info("Hello");

        // Log info with parameterized string.
        LOGGER.info("Hey, this is {}", "Pranay");

        // Log debug if debug enabled using lambda
        LOGGER.debug( "Hey {}!", ()->"Pranay");

        ComLoggerApp.print();
    }
}
