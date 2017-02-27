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

	private StringBuilder builder = new StringBuilder();
	boolean testResult;
	private FrameWorkCommands frame;
	private String logPath;
	private long startTime;
	private int quantityOFTests;
	private int passedCount;
	private int failedCount;
	private float fullTimeOfTests;

	public void writeLog() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(logPath));
			writer.write(printResult());
			writer.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void execute(String importFile) {
		frame = new FrameWorkCommands();
		try {
			File file = new File(importFile);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				List<String> listOfCommands = new ArrayList<>();
				Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(line);
				while (m.find()) {
					listOfCommands.add(m.group(1).replace("\"", ""));
				}
				String methodname = listOfCommands.get(0);
				startTestTime();
				switch (methodname) {

				case "open": {
					testResult = frame.open1(listOfCommands.get(1), listOfCommands.get(2));
					break;
				}
				case "checkPageTitle": {
					testResult = frame.checkPageTitle(listOfCommands.get(1));
					break;
				}
				case "checkPageContains": {
					testResult = frame.checkPageContains(listOfCommands.get(1));
					break;
				}
				case "checkLinkPresentByHref": {
					testResult = frame.checkLinkPresentByHref(listOfCommands.get(1));
					break;
				}
				case "checkLinkPresentByName": {
					testResult = frame.checkLinkPresentByName(listOfCommands.get(1));
					break;
				}
				}
				finishTestTime();
				countTestResult();
				String executionResult = "";
				executionResult = testResult ? " + " : " ! ";
				builder.append(executionResult + "[" + line + "]\n");
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			writeLog();
		}
	}

	private void startTestTime() {
		startTime = System.nanoTime();
	}

	private void countTestResult() {
		quantityOFTests++;
		if (testResult) {
			passedCount++;
		} else {
			failedCount++;
		}
	}

	private void finishTestTime() {
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 100000000;
		fullTimeOfTests = fullTimeOfTests + duration;
	}

	private String printResult() {
		builder.append(String.format("Total tests: %s  \n", quantityOFTests))
				.append(String.format("Passed/Failed: %d/%d \n", passedCount, failedCount))
				.append(String.format("Total time: %.3f \n", fullTimeOfTests))
				.append(String.format("Average time: %.3f \n", fullTimeOfTests / quantityOFTests));
		System.out.println(builder.toString()); // for testing
		return builder.toString();
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

}
