package com.klindziuk.microframework;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FrameworkExecutor {

	private StringBuilder builder = new StringBuilder();
	private BufferedWriter writer;
	private Map<String, List<String>> commandMap;
	private CommandFrame frame;
	private String logPath;
	private boolean testresult;
	private long endTime;
	private long duration;
	private long startTime;
	private int quantityOFTests;
	private int passedCount;
	private int failedCount;
	private float fullTimeOfTests;

	// is it good to have nested try ?
	// it protected us if user input ^*%~$ as name of log file.
	public void writeLog() {

		try {
			writer = new BufferedWriter(new FileWriter(logPath));
			writer.write(printResult());
			writer.close();

		} catch (FileNotFoundException e) {

			System.out.println("You input incorrect path for log file.\nLog will be printed at "
					+ FrameWorkRunner.DEFAULT_LOG_PATH_AND_NAME);
			try {
				writer = new BufferedWriter(new FileWriter(FrameWorkRunner.DEFAULT_LOG_PATH_AND_NAME));
				writer.write(printResult());
				writer.close();
			} catch (IOException ioex) {
				ioex.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void execute() {

		frame = new CommandFrame();

		// I'll add some exceptions later
		try {
			for (String methodname : commandMap.keySet()) {
				switch (methodname) {
				case "open": {
					startTestTime();
					testresult = frame.open1(commandMap.get(methodname).get(1), commandMap.get(methodname).get(2));
					finishTestTime();
					countTestResult();
					appendResult(testresult, methodname, commandMap.get(methodname).get(1),
							commandMap.get(methodname).get(2));
					break;
				}
				case "checkPageTitle": {
					startTestTime();
					testresult = frame.checkPageTitle(commandMap.get(methodname).get(1));
					finishTestTime();
					countTestResult();
					appendResult(testresult, methodname, commandMap.get(methodname).get(1));
					break;
				}
				case "checkPageContains": {
					startTestTime();
					testresult = frame.checkPageContains(commandMap.get(methodname).get(1));
					finishTestTime();
					countTestResult();
					appendResult(testresult, methodname, commandMap.get(methodname).get(1));
					break;
				}
				case "checkLinkPresentByHref": {
					startTestTime();
					testresult = frame.checkLinkPresentByHref(commandMap.get(methodname).get(1));
					finishTestTime();
					countTestResult();
					appendResult(testresult, methodname, commandMap.get(methodname).get(1));

					break;
				}
				case "checkLinkPresentByName": {
					startTestTime();
					testresult = frame.checkLinkPresentByName(commandMap.get(methodname).get(1));
					finishTestTime();
					countTestResult();
					appendResult(testresult, methodname, commandMap.get(methodname).get(1));
					break;
				}
				}

			}
		}
			finally {
			writeLog();
			}
	}

	//  i think its not very good variant for print
	private void appendResult(boolean testresult, String... args) {
		
		String executionResult = "";
		executionResult = testresult ? " + " : " ! ";
		if(3 == args.length){
		builder.append(executionResult + "[" + args[0] + " \"" + args[1] + "\"" + " \"" + args[2] + "\" ] " + "\n");
		}
		else {
			builder.append(executionResult + "[" + args[0] + " \"" + args[1] +  "\" ] " + "\n");
		}
		
	}

	private void startTestTime() {
		 startTime = System.nanoTime();
		
	}

	private void countTestResult() {
		quantityOFTests++;
		if (true == testresult) {
			passedCount++;
		} else {
			failedCount++;
		}
	}

	private void finishTestTime() {
		endTime = System.nanoTime();
		duration = (endTime - startTime) / 100000000;
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

	public void setCommandMap(Map<String, List<String>> commandMap) {
		this.commandMap = commandMap;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

}
