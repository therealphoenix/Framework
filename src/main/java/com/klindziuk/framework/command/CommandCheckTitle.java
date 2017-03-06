package com.klindziuk.framework.command;

import org.apache.log4j.Logger;

import com.klindziuk.framework.util.CommandResult;

class CommandCheckTitle extends Command {
	public static final String name = "checkPageTitle";
	private static final Logger logger = Logger.getLogger(CommandCheckTitle.class);

	@Override
	public CommandResult run(String... params) {

		System.out.println("Command: " + name);
		validateParams(params);
		return new CommandResult((params[0].equals(document.title()) && validateParams(params)),
				name + " " + "\"" + params[0] + "\"");
	}

	@Override
	public boolean validateParams(String... params) {
		if (params.length < 1) {
			logger.error(NOT_ENOUGH_ARGS + name + " " + "\"" + params[0] + "\"");
			return false;
		}
		if (params.length > 1) {
			logger.error(TOO_MANY_ARGS + name + " " + "\"" + params[0] + "\"");
			return false;
		}
		if (null == document) {
			logger.error(NULL_DOCUMENT);
			return false;
		}
		return true;
	}
}
