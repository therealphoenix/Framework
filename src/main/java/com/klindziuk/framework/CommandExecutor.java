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

public class CommandExecutor {
    private static final Logger LOGGER = Logger.getLogger(CommandExecutor.class);
  

    public List<CommandResult> execute(String importFile) throws FileNotFoundException {
    	
    	List<CommandResult>  logList = new ArrayList<>();
        File file = new File(importFile);
        Scanner scanner = new Scanner(file);
        int lineNumber = 0;
        while (scanner.hasNextLine()) {
            String commandLine = scanner.nextLine();
            lineNumber++;
            List<String> listOfCommands = new ArrayList<>();
            Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(commandLine);
            while (m.find()) {
                listOfCommands.add(m.group(1).replace("\"", ""));
            }

            if (listOfCommands.isEmpty()) {
                LOGGER.warn("Line:"+lineNumber + " at command file is empty." );
                continue;
            }
            
            
            String methodname = listOfCommands.get(0);
            CommandResult cr = new CommandResult(false, commandLine, Timer.RESET_TIME);
            try {

            	String[] params = setParameters(listOfCommands);
                Command command = CommandEnum.getCommandByName(methodname);
                if (command == null) {
                    LOGGER.error("Unfortunately we don't support test for \"" + methodname + "\".");
                   continue;
                }
                cr = command.runWithTimer(params);
                logList.add(cr);
            } catch (IndexOutOfBoundsException iobex) {
                LOGGER.error(Command.NOT_ENOUGH_ARGS + commandLine + "(" + file.getName() + ":" + lineNumber
                        + "). Test with this command failed.");
            } 
        }
        scanner.close();

        return logList;
    }
    
    private String[] setParameters(List<String> list) {
		String[] array = new String[list.size() - 1];
		list.remove(0);
		array = list.toArray(array);
		return array;
	}

}
