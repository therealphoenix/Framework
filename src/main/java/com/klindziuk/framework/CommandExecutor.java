package com.klindziuk.framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.klindziuk.framework.util.Timer;
import org.apache.log4j.Logger;
import com.klindziuk.framework.command.Command;
import com.klindziuk.framework.command.CommandEnum;
import com.klindziuk.framework.util.CommandResult;

/**
 * Execution of commands
 *
 */
public class CommandExecutor {
	private static final Logger LOGGER = Logger.getLogger(CommandExecutor.class);

	public List<CommandResult> execute(String importFile) throws FileNotFoundException {

		List<CommandResult> logList = new ArrayList<>();
		File file = new File(importFile);
		Scanner scanner = new Scanner(file);
		int lineNumber = 0;
		
		//parsing line from file
		while (scanner.hasNextLine()) {
			String commandLine = scanner.nextLine();
			lineNumber++;
			List<String> listOfCommands = new ArrayList<>();
			Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(commandLine);
			while (m.find()) {
				listOfCommands.add(m.group(1).replace("\"", ""));
			}

			if (listOfCommands.isEmpty()) {
				LOGGER.warn("Line:" + lineNumber + " at command file is empty.");
				continue;
			}

			String methodname = listOfCommands.get(0);
			CommandResult cr = new CommandResult(false, commandLine, Timer.RESET_TIME);

			// making an array of method parameters 
			String[] params = listOfCommands.size() == 1 ? new String[] {}
					: listOfCommands.subList(1, listOfCommands.size()).toArray(new String[] {});

			Command command = CommandEnum.getCommandByName(methodname);
			
			if (command == null) {
				LOGGER.error("Unfortunately we don't support test for \"" + methodname + "\".");
				continue;
			}
			cr = command.runWithTimer(params);
			logList.add(cr);

		}
		scanner.close();
		return logList;
	}

}
