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
	private final String OPEN = "open";
	private final String LINK_BY_HREF = "checkLinkPresentByHref";
	private final String LINK_BY_NAME = "checkLinkPresentByName";
	private final String PAGE_TITLE = "checkPageTitle";
	private final String PAGE_CONTAINS = "checkPageContains";

	private StringBuilder builder = new StringBuilder();
	private FrameWorkCommands frame;
	private String logPath;
	private String line;
	private long startTime;
	private float fullTimeOfTests;
	private int quantityOFTests;
	private int passedCount;
	private int failedCount;
	private boolean testResult;
	
	public void execute(String importFile) {
		frame = new FrameWorkCommands();
		try {
			File file = new File(importFile);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				List<String> listOfCommands = new ArrayList<>();
				Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(line);
				while (m.find()) {
					listOfCommands.add(m.group(1).replace("\"", ""));
				}
				String methodname = listOfCommands.get(0);
				startTestTime();
				switch (methodname) {

				case OPEN: {
					testResult = frame.open1(listOfCommands.get(1), listOfCommands.get(2));
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
                break;
				
				}
				finishTestTime();
				countTestResult();
				String executionResult  = testResult ? " + " : " ! ";
				builder.append(executionResult + "[" + line + "]\n");
			}
			scanner.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("1");
			fnfe.printStackTrace();
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("Not enought arguments in command: "+ line + ". Test with this command failed." );
			testResult = false;
			finishTestTime();
			countTestResult();
			builder.append(" ! " + "[" + line + "]\n");
		}
		finally {
			writeLog();
		}
	}

	private void startTestTime() {
		startTime = System.nanoTime();
	}
	
	private void finishTestTime() {
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 100000000;
		fullTimeOfTests = fullTimeOfTests + duration;
	}

	private void countTestResult() {
		quantityOFTests++;
		if (testResult) {
			passedCount++;
		} else {
			failedCount++;
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
	

	private String printResult() {
		builder.append(String.format("Total tests: %s  \n", quantityOFTests))
				.append(String.format("Passed/Failed: %d/%d \n", passedCount, failedCount))
				.append(String.format("Total time: %.3f \n", fullTimeOfTests))
				.append(String.format("Average time: %.3f \n", fullTimeOfTests / quantityOFTests));
		System.out.println(builder.toString()); // output for user
		return builder.toString();
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

}
