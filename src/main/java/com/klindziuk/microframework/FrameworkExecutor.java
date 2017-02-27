package com.klindziuk.microframework;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FrameworkExecutor {

	private final String OUT_PATH = "frameWorkLog.txt";
	private StringBuilder builder = new StringBuilder();
	private Map<String, List<String>> commandMap;
	private CommandImport ci;
	CommandFrame frame;
	long startTime;
	long endtime;
	long endTime;
	private int quantityOFTests;
	private int passedCount;
	private int failedCount;
	long duration;
	private float fullTimeOfTests;

	public void execute() {

		frame = new CommandFrame();
		ci = new CommandImport();
		commandMap = ci.getCommandMap();
		boolean testresult;
		String executionResult = "";

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(OUT_PATH));

			for (String methodname : commandMap.keySet()) {
				switch (methodname) {
				case "open": {
					startTime();
					testresult = frame.open1(commandMap.get(methodname).get(1), commandMap.get(methodname).get(2));
					endTime();
					executionResult = testresult ? " + " : " ! ";
					if (true == testresult) {
						passedCount++;
					} else {
						failedCount++;
					}

					builder.append(executionResult + "[" + methodname + " \"" + commandMap.get(methodname).get(1)
							+ "\"] " + " \"" + commandMap.get(methodname).get(2) + "\"] " + "\n");
					break;
				}
				case "checkPageTitle": {
					startTime();
					testresult = frame.checkPageTitle(commandMap.get(methodname).get(1));
					endTime();
					executionResult = testresult ? " + " : " ! ";
					if (true == testresult) {
						passedCount++;
					} else {
						failedCount++;
					}

					builder.append(executionResult + "[" + methodname + " \"" + commandMap.get(methodname).get(1)
							+ "\"] " + "\n");
					break;
				}
				case "checkPageContains": {
					startTime();
					testresult = frame.checkPageContains(commandMap.get(methodname).get(1));
					endTime();
					executionResult = testresult ? " + " : " ! ";
					if (true == testresult) {
						passedCount++;
					} else {
						failedCount++;
					}

					builder.append(executionResult + "[" + methodname + " \"" + commandMap.get(methodname).get(1)
							+ "\"] " + "\n");
					break;
				}
				case "checkLinkPresentByHref": {
					startTime();
					testresult = frame.checkLinkPresentByHref(commandMap.get(methodname).get(1));
					endTime();
					duration = (endTime - startTime) / 100000000;
					executionResult = testresult ? " + " : " ! ";
					if (true == testresult) {
						passedCount++;
					} else {
						failedCount++;
					}

					builder.append(executionResult + "[" + methodname + " \"" + commandMap.get(methodname).get(1)
							+ "\"] " + "\n");
					break;
				}
				case "checkLinkPresentByName": {
					startTime();
					testresult = frame.checkLinkPresentByName(commandMap.get(methodname).get(1));
					endTime();
					executionResult = testresult ? " + " : " ! ";
					if (true == testresult) {
						passedCount++;
					} else {
						failedCount++;
					}
					builder.append(executionResult + "[" + methodname + " \"" + commandMap.get(methodname).get(1)
							+ "\"] " + "\n");
					break;
				}
				}
				fullTimeOfTests = fullTimeOfTests + duration;
				quantityOFTests++;
			}
			writer.write(printResult());
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void startTime() {
		startTime = System.nanoTime();

	}

	private void endTime() {
		endTime = System.nanoTime();
		duration = (endTime - startTime) / 100000000;

	}

	private String printResult() {

		builder.append(String.format("Total tests: %s  \n", quantityOFTests))
				.append(String.format("Passed/Failed: %d/%d \n", passedCount, failedCount))
				.append(String.format("Total time: %.3f \n", fullTimeOfTests))
				.append(String.format("Average time: %.3f \n", fullTimeOfTests / quantityOFTests));
		System.out.println(builder.toString()); // for testing
		return builder.toString();
	}

}
