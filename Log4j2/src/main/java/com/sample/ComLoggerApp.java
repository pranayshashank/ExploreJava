package com.sample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ComLoggerApp {

    private static final Logger LOGGER = LogManager.getLogger(ComLoggerApp.class);

    public static void print(){
        LOGGER.debug( "Hey {}!", ()->"Pranay");
    }
}
