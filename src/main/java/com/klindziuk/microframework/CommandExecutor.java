package com.klindziuk.microframework;

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
	private FrameWorkCommands frame;
	private Timer timer;
	private String logPath;
	private String commandLline;
	private float fullTimeOfTests;
	private int quantityOFTests;
	private int passedTestQuantity;
	private int failedTestQuantity;
	private boolean testResult;

	public void execute(String importFile) throws IllegalArgumentException{
		frame = new FrameWorkCommands();
		try {
			File file = new File(importFile);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				commandLline = scanner.nextLine();
				List<String> listOfCommands = new ArrayList<>();
				Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(commandLline);
				while (m.find()) {
					listOfCommands.add(m.group(1).replace("\"", ""));
				}
				
				timer = new Timer();
				timer.start();
				String methodname = listOfCommands.get(0);
				
				// validation of parameters
				 if("open".equals(methodname) && (3 < listOfCommands.size())){
					testResult = false;
					timer.setExceptionTime();
					System.out.println("Too much arguments at: " + commandLline + "(" + file.getName() + ":"
							+ (quantityOFTests + 1) + ").Test with this command failed.");
					builder.append(" ! " + "[" + commandLline + "] " + timer.printTestTime() + "\n");
				}
				 				 
				 else if(!"open".equals(methodname) && (2 < listOfCommands.size())){
						testResult = false;
						timer.setExceptionTime();
						System.out.println("Too much arguments at: " + commandLline + "(" + file.getName() + ":"
								+ (quantityOFTests + 1) + ").Test with this command failed.");
						builder.append(" ! " + "[" + commandLline + "] " + timer.printTestTime() + "\n");
					}
				 else {
				try {
					
					switch (methodname) {
					case OPEN: {
						testResult = frame.open(listOfCommands.get(1), listOfCommands.get(2));
						break;
					}
					case PAGE_TITLE: {
						testResult = frame.checkPageTitle(listOfCommands.get(1));
						break;
					}
					case PAGE_CONTAINS: {
						testResult = frame.checkPageContains(listOfCommands.get(1));
						break;
					}
					case LINK_BY_HREF: {
						testResult = frame.checkLinkPresentByHref(listOfCommands.get(1));
						break;
					}
					case LINK_BY_NAME: {
						testResult = frame.checkLinkPresentByName(listOfCommands.get(1));
						break;
					}
					default: {
						testResult = false;
						System.out.println("Unfortunately we don't support test for \"" + methodname + "\".");
					}
					}
					timer.stop();
				    timer.getTestTime();
					fullTimeOfTests = fullTimeOfTests +  timer.getTestTime();
					String executionResult = testResult ? " + " : " ! ";
					builder.append(String.format("%s[%s] %s \n", executionResult, commandLline,  timer.printTestTime()));
					
				} catch (IndexOutOfBoundsException iobe) {
					testResult = false;
					timer.setExceptionTime();
					System.out.println("Not enough arguments at: " + commandLline + "(" + file.getName() + ":"
							+ (quantityOFTests + 1) + ").Test with this command failed.");
					builder.append(" ! " + "[" + commandLline + "] " + "0,000" + "\n");
				}
				
			}
				 countTestResult();
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
	private void countTestResult() {
		quantityOFTests++;
		if (testResult) {
			passedTestQuantity++;
		} else {
			failedTestQuantity++;
		}
	}
	private String printResult() {
		builder.append(String.format("Total tests: %s  \n", quantityOFTests))
				.append(String.format("Passed/Failed: %d/%d \n", passedTestQuantity, failedTestQuantity))
				.append(String.format("Total time: %.3f \n", fullTimeOfTests))
				.append(String.format("Average time: %.3f \n", fullTimeOfTests / quantityOFTests));
		System.out.println(builder.toString()); // output for user
		return builder.toString();
	}

}
