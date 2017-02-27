package com.klindziuk.microframework;

public class FrameWorkRunner {
	protected static final String DEFAULT_LOG_PATH_AND_NAME = 
		FrameWorkRunner.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "microframework.txt";
	private static String logPath;

	public static void main(String[] args) {
		
		
		if(0 == args.length){
			System.out.println("You don't input path for commandline file.Try again.");
			System.exit(-1);
					}
		if(2 < args.length){
			System.out.println("You input to many arguments.Try again.");
			System.exit(-1);
			}
		// if user input equals path
		if((1 == args.length) || (args[0].equals(args[1]))){
			System.out.println("You don't input path for log file.\nLog will be printed at " + DEFAULT_LOG_PATH_AND_NAME);
			logPath = DEFAULT_LOG_PATH_AND_NAME;
		}
		else{
			logPath = args[1];
		}
			
		
		CommandImport ci = new CommandImport();
		FrameworkExecutor fe = new FrameworkExecutor();
		fe.setCommandMap(ci.fillMap(args[0]));
		fe.setLogPath(logPath);
		fe.execute();
		

		
	}

}
