package com.klindziuk.framework.command;

import com.klindziuk.framework.util.CommandResult;

/**
 * using for performing command "checkPageTitle"
 *
 */
class CommandCheckTitle extends Command {

	@Override
	public CommandResult run(String... params) {
		LOGGER.debug("Command: " + getName());
		return new CommandResult(validateParamsAndDocument(1, params) && params[0].equals(document.title()),
				getName() + " "  + buildParamsString(params));
	}
	
	public CommandCheckTitle() {
		super("checkPageTitle");
		
	}
}
