package com.klindziuk.framework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandExecutor {
	private static final String OPEN = "open";
	private static final String LINK_BY_HREF = "checkLinkPresentByHref";
	private static final String LINK_BY_NAME = "checkLinkPresentByName";
	private static final String PAGE_TITLE = "checkPageTitle";
	private static final String PAGE_CONTAINS = "checkPageContains";
	
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
					
				} catch (ManyArgumentException maex) {
					System.out.println("Too much arguments at: " + commandLine + "(" + file.getName() + ":"
								+ lineNumber + ").Test with this command failed.");
					
					CommandResult exception = new CommandResult(false, commandLine);
					exception.setTime(0);
					logList.add(exception);
				}
				catch (NotEnoughArgumentException neex) {
					System.out.println("Not enough arguments at: " + commandLine + "(" + file.getName() + ":"
								+ lineNumber + ").Test with this command failed.");
					
					CommandResult exception = new CommandResult(false, commandLine);
					exception.setTime(0);
					logList.add(exception);
				}	
				catch (UnsupportedOperationException uoex) {
					System.out.println("Unfortunately we don't support test for \"" + methodname + "\".");
				}
				catch (NullDocumentException ndex) {
					System.out.println("Cannot instantiate test without opened page.");
					
					CommandResult exception = new CommandResult(false, commandLine);
					exception.setTime(0);
					logList.add(exception);
				}
				catch (IllegalArgumentException iobex) {
					System.out.println("Unfortunately we don't support test for \"" +  methodname + "\".");	
				}
				
				catch (IndexOutOfBoundsException iobex) {
					System.out.println("Unfortunately we don't support test for \"" + " \"\" " + "\".");	
				}
				catch (NullPointerException npex) {
					System.out.println("Unfortunately we don't support test for \"" + methodname + "\".");	
				}
					
	}	
			scanner.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} finally {
			writeLog();
		}
	}
	
	public void writeLog() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(logPath));
			writer.write(printResult());
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unfortunately we can't open file at this path");
		} catch (IOException e) {
			e.printStackTrace();
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
	
	private String printResult() {
		int quantityOfTests = logList.size();
		int passedTestQuantity = 0;
		int failedTestQuantity = 0;
		float fullTimeOfTests = 0;
		
			for(CommandResult cr : logList){
				builder.append(cr.toString()+ "\n");
				fullTimeOfTests = fullTimeOfTests + cr.getTime();
				if(true == cr.isResult() ) {
					passedTestQuantity++;
				}
				else {
					failedTestQuantity++;
				}
			}
			
		builder.append(String.format("Total tests: %s  \n", logList.size()))
				.append(String.format("Passed/Failed: %d/%d \n", passedTestQuantity, failedTestQuantity))
				.append(String.format("Total time: %.3f \n", fullTimeOfTests))
				.append(String.format("Average time: %.3f \n", fullTimeOfTests / quantityOfTests));
		System.out.println("\n" + builder.toString()); // output for user
		return builder.toString();
	}

}
