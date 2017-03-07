package com.klindziuk.framework.command;

import com.klindziuk.framework.util.CommandResult;

public class CommandCheckContainsText extends Command {

	public static final String NAME = "checkPageContainsText";

	@Override
	public CommandResult run(String... params) {
		LOGGER.debug("Command: " + NAME);
		boolean result = validateParamsAndDocument(1, params) && document.toString().contains(params[0]);
		return new CommandResult(result, NAME  + buildParamsString(params));
	}
	
	@Override
	String getName() {
		return NAME;
	}

}
