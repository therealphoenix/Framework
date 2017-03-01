package com.klindziuk.microframework;

import java.util.regex.Pattern;

public class FrameWorkRunner {
	protected static final String DEFAULT_LOG_PATH_AND_NAME = FrameWorkRunner.class.getProtectionDomain()
			.getCodeSource().getLocation().getPath() + "microframework.txt";
	
	private static String logPath;

	public static void main(String[] args) {
		
		// checking for special symbols in path
		if (checkPathWithRegExp(args[0]) || checkPathWithRegExp(args[1])) {
			System.out.println("Don't use special symbols in path.Please, try again.");
			System.exit(-1);
		}
		if (0 == args.length) {
			System.out.println("You don't input path for commandline file.Please,try again.");
			System.exit(-1);
		}
		if (2 < args.length) {
			System.out.println("You input to many arguments.Please, try again.");
			System.exit(-1);
		}
		// checking input for same path for commandfile and logfile
		if  (args[0].equals(args[1])) {
			System.out
					.println("You  input equals path for instructions and log file.\nLog will be printed at " 
			+ DEFAULT_LOG_PATH_AND_NAME);
			logPath = DEFAULT_LOG_PATH_AND_NAME;
		}
		// if user don't input log path
		if (1 == args.length) {
			System.out
					.println("You don't input path for log file.\nLog will be printed at " + DEFAULT_LOG_PATH_AND_NAME);
			logPath = DEFAULT_LOG_PATH_AND_NAME;
		} else {
			logPath = args[1];
		}
		System.out.println(checkPathWithRegExp("Program FilesEclipseIDEworkspaceFrameworkinstructions.txt"));
		CommandExecutor fe = new CommandExecutor();
		fe.setLogPath(logPath);
		fe.execute(args[0]);
	}

	public static boolean checkPathWithRegExp(String filePath) {
		Pattern pattern = Pattern.compile("[$&,;=?#|'<>^*()%!]");
		if(pattern.matcher(filePath).find()) {
			return true;
					}
		return false;
	}

}
