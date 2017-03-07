package com.klindziuk.framework.command;

import com.klindziuk.framework.util.CommandResult;

class CommandCheckTitle extends Command {

	public static final String NAME = "checkPageTitle";

	@Override
	public CommandResult run(String... params) {
		LOGGER.debug("Command: " + NAME);
		return new CommandResult(validateParamsAndDocument(1, params) && params[0].equals(document.title()),
				NAME + " " + "\"" + params[0] + "\"");
	}
	
	@Override
	String getName() {
		return NAME;
	}
}
