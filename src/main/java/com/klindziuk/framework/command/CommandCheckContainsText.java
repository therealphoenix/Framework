package com.klindziuk.framework.command;

import com.klindziuk.framework.util.CommandResult;

/**
 * using for performing command "checkPageContainsText"
 *
 */
public class CommandCheckContainsText extends Command {
	
	@Override
	public CommandResult run(String... params) {
		LOGGER.debug("Command: " + getName());
		boolean result = validateParamsAndDocument(1, params) && document.toString().contains(params[0]);
		return new CommandResult(result, getName() + " "  + buildParamsString(params));
	}
	
	public CommandCheckContainsText() {
		super("checkPageContainsText");
		
	}

}
