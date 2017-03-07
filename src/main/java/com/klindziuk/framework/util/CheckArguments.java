package com.klindziuk.framework.util;

import java.util.regex.Pattern;
import org.apache.log4j.Logger;

public class CheckArguments {
    private static final String DEFAULT_LOG_PATH_AND_NAME = CheckArguments.class.getProtectionDomain()
            .getCodeSource().getLocation().getPath() + "TATframeworklog.log";
    private static final Logger LOGGER = Logger.getLogger(CheckArguments.class);

    private String commandPath;
    private String logPath;

    /**
     * checking arguments size
     * @param args
     */
    private void checkArguments(String[] args) {
        if (0 == args.length) {
            LOGGER.error("You didn't input path for commandline file. Please,try again.");
            System.exit(-1);
        }
        if (2 < args.length) {
            LOGGER.error("You input to many arguments. Please, try again.");
            System.exit(-1);
        }
        if (1 == args.length) {
            LOGGER.warn("You didn't input path for log file.\nLog will be printed at " + DEFAULT_LOG_PATH_AND_NAME);
            this.commandPath = args[0];
            this.logPath = DEFAULT_LOG_PATH_AND_NAME;

        } else {
            this.commandPath = args[0];
            this.logPath = args[1];
        }
    }

    /**
     * checking input for same path for commandfile and logfile
     * @param commandPath
     * @param logPath
     */
    private void checkEqualsPath(String commandPath, String logPath) {
        if (commandPath.equals(logPath)) {
            LOGGER.warn("You  input equals path for instructions and log file.\nLog will be printed at "
                    + DEFAULT_LOG_PATH_AND_NAME);
            this.logPath = DEFAULT_LOG_PATH_AND_NAME;
        }
    }

    /**
     * checking path for special symbols
     */
    private void checkSpecialSymbols(String filePath) {
        Pattern pattern = Pattern.compile("[$&,;=?#|'<>^*()!]");
        if (pattern.matcher(filePath).find()) {
            LOGGER.error("Special symbols founded at filename : " + filePath + ". Try again.");
            System.exit(-1);
        }
    }

    /**
     * checking file extensions
     * @param filePath
     */
    private void checkFileXtension(String filePath) {
        String extension = "";
        int i = filePath.lastIndexOf('.');
        if (i >= 0) {
            extension = filePath.substring(i + 1);
        }
        if ((!"log".equals(extension)) && (!"txt".equals(extension))) {
            LOGGER.error("Bad file format at : " + filePath + ", Try again.");
            System.exit(-1);
        }
    }

    /**
     * full check in one method
     * 
     */
    public String[] FullCheck(String[] args) {
        checkArguments(args);
        checkSpecialSymbols(commandPath);
        checkSpecialSymbols(logPath);
        checkFileXtension(commandPath);
        checkFileXtension(logPath);
        checkEqualsPath(commandPath, logPath);
        String[] pathArray = {this.commandPath, this.logPath};

        return pathArray;
    }
}

