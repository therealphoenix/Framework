package com.klindziuk.framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

import com.klindziuk.framework.command.Command;
import com.klindziuk.framework.command.CommandEnum;
import com.klindziuk.framework.util.CommandResult;

public class CommandExecutor {
	private static final Logger logger = Logger.getLogger(CommandExecutor.class);
	private List<CommandResult> logList;
	private String methodname;
	private int lineNumber;
	private String commandLine;

	public void execute(String importFile) throws IllegalArgumentException {

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
				} catch (UnsupportedOperationException uoex) {
					logger.error("Unfortunately we don't support test for \"" + methodname + "\".");
				} catch (IndexOutOfBoundsException iobex) {
					logger.error(Command.NOT_ENOUGH_ARGS + commandLine + "(" + file.getName() + ":" + lineNumber
							+ ").Test with this command failed.");
					logList.add(new CommandResult(false, commandLine, 0));
				} catch (NullPointerException npex) {
					logger.error("Unfortunately we don't support test for \"" + methodname + "\".");
				}

			}
			scanner.close();
		} catch (FileNotFoundException fnfe) {
			logger.error("System can not find open this file.Try again");
			fnfe.printStackTrace();
		} 
	}
	
	private String[] setParameters(List<String> list) {
		String[] array = new String[list.size() - 1];
		list.remove(0);
		array = list.toArray(array);
		return array;
	}
	
	public List<CommandResult> getLogList() {
		return logList;
	}
	
	}
