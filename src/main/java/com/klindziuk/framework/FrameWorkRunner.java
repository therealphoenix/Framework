package com.klindziuk.framework;

import com.klindziuk.framework.util.CheckArguments;
import com.klindziuk.framework.util.CommandResult;
import com.klindziuk.framework.util.LoggerUtils;
import com.klindziuk.framework.util.TotalResult;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * using for run framework 
 *
 */
public class FrameWorkRunner {
	private static final Logger LOGGER = Logger.getLogger(FrameWorkRunner.class);

	public static void main(String[] args) {
		CheckArguments ca = new CheckArguments();
		String[] parameters = ca.FullCheck(args);
		LoggerUtils.updateLog4jConfiguration(parameters[1]);
		try {
			CommandExecutor fe = new CommandExecutor();
			List<CommandResult> listOfResults = fe.execute(parameters[0]);
			TotalResult.printResult(listOfResults);
		} catch (FileNotFoundException e) {
			LOGGER.error("System can not find open this file. Try again", e);
			System.exit(1);
		}
	}

	
}
