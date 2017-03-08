package com.klindziuk.framework.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class LoggerUtils {

    private static final Logger LOGGER = Logger.getLogger(TotalResult.class);

    /**
     * Changing path for output log file 
     * @param logFile
     */
    public static void updateLog4jConfiguration(String logFile) {
        Properties props = new Properties();
        try {
            InputStream configStream = LoggerUtils.class.getResourceAsStream( "/log4j.properties");
            props.load(configStream);
            configStream.close();
        } catch (IOException e) {
            LOGGER.error("Error: Cannot load configuration file ", e);
        }
        props.setProperty("log4j.appender.file.File", logFile);
        PropertyConfigurator.configure(props);
    }


}
