package com.klindziuk.framework.util;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class CheckArguments {
	private static final String DEFAULT_LOG_PATH_AND_NAME = CheckArguments.class.getProtectionDomain()
			.getCodeSource().getLocation().getPath() + "TATframeworklog.txt";
	private static final Logger logger = Logger.getLogger(CheckArguments.class);
	private String commandPath;
	private String logPath;
	
	//check arguments size
	private void checkArguments(String[] args) {
		if (0 == args.length) {
			System.out.println("You don't input path for commandline file.Please,try again.");
			System.exit(-1);
		}
		if (2 < args.length) {
			System.out.println("You input to many arguments.Please, try again.");
			System.exit(-1);
		}
	    if (1 == args.length) {
		logger.warn("You don't input path for log file.\nLog will be printed at " + DEFAULT_LOG_PATH_AND_NAME);
		this.commandPath = args[0];
		this.logPath = DEFAULT_LOG_PATH_AND_NAME;
		
	} else {
		this.commandPath = args[0];
		this.logPath = args[1];
	}
	}
	
 	// checking input for same path for commandfile and logfile
 private void checkEqualsPath(String commandPath, String logPath){
	if (commandPath.equals(logPath)) {
		logger.error("You  input equals path for instructions and log file.\nLog will be printed at "
				+ DEFAULT_LOG_PATH_AND_NAME);
		this.logPath = DEFAULT_LOG_PATH_AND_NAME;
	}
 }

//checking path for special symbols
private void checkSpecialSymbols(String filePath) {
	Pattern pattern = Pattern.compile("[$&,;=?#|'<>^*()!]");
	if (pattern.matcher(filePath).find()) {
		logger.error("Special symbols founded at filename : " + filePath + ". Try again.");
		System.exit(-1);
	}
}

private void checkFileXtension(String filePath){
	String extension = "";
	int i = filePath.lastIndexOf('.');
	if (i >= 0) {
	    extension = filePath.substring(i+1);
	}
			if( (!"log".equals(extension)) && (!"txt".equals(extension)) ){
				logger.error("Bad file format at : " + filePath + ", Try again." );
				System.exit(-1);
	}
	}

public  String[] FullCheck(String[] args){
	checkArguments(args);
	checkSpecialSymbols(commandPath);
	checkSpecialSymbols(logPath);
	checkFileXtension(commandPath);
	checkFileXtension(logPath);
	checkEqualsPath(commandPath, logPath);
	String[] pathArray = {this.commandPath,this.logPath};
	
	return pathArray;
	
	
	
	
}

}

