package com.klindziuk.framework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.SimpleLayout;

public class CommandExecutor {
	private static final Logger logger = Logger.getLogger(CommandExecutor.class);
	private StringBuilder builder;
	private List<CommandResult> logList;
	private String methodname;
	private int lineNumber;
	private String logPath;
	private String commandLine;
	
	
	public void execute(String importFile) throws IllegalArgumentException{
		
		logList = new ArrayList();
		
		try {
			File file = new File(importFile);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				commandLine = scanner.nextLine();
				lineNumber++;
				
				List<String> listOfCommands = new ArrayList<>();
				Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(commandLine);
				while (m.find()) {
					listOfCommands.add(m.group(1).replace("\"", ""));
				}
				
				try {
					
					methodname = listOfCommands.get(0);
					String[] params = setParameters(listOfCommands);
					CommandResult cr = CommandEnum.getCommandByName(methodname).runWithTimer(params);
					logList.add(cr);
				}	
				catch (UnsupportedOperationException uoex) {
					
					logger.warn("Unfortunately we don't support test for \"" + methodname + "\".");
				}
				catch (IndexOutOfBoundsException iobex) {
					logger.warn(Command.NOT_ENOUGH_ARGS +  commandLine + "(" + file.getName() + ":"
							+ lineNumber + ").Test with this command failed.");
					logList.add(new CommandResult(false, commandLine, 0));
				}
				catch (NullPointerException npex) {
					
					logger.warn("Unfortunately we don't support test for \"" + methodname + "\".");	
				}
					
	}	
			scanner.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} finally {
	printResult();
		}
	}
	
	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}
	public CommandExecutor() {
		builder = new StringBuilder();
	}
	
	private String[] setParameters(List <String> list){
		String[] array = new String[list.size() - 1];
		list.remove(0);
		array = list.toArray(array);
		return array;
	}
	
	private void printResult() {
		int quantityOfTests = logList.size();
		int passedTestQuantity = 0;
		int failedTestQuantity = 0;
		float fullTimeOfTests = 0;
		
			for(CommandResult cr : logList){
				logger.info(cr.toString());
				fullTimeOfTests = fullTimeOfTests + cr.getTime();
				if(true == cr.isResult() ) {
					passedTestQuantity++;
				}
				else {
					failedTestQuantity++;
				}
			}
			
			logger.warn(String.format("Total tests: %s  ", logList.size()));
			logger.info(String.format("Passed/Failed: %d/%d ", passedTestQuantity, failedTestQuantity));
			logger.info(String.format("Total time: %.3f ", fullTimeOfTests));
			logger.info(String.format("Average time: %.3f ", fullTimeOfTests / quantityOfTests));
		
		
	}

}
