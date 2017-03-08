package com.klindziuk.framework.command;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.klindziuk.framework.util.CommandResult;

/**
 * using for performing command "checkLinkPresentByName"
 *
 */
class CommandCheckLinkName extends Command {

	public static final String NAME = "checkLinkPresentByName";
	@Override
	public CommandResult run(String... params) {
		LOGGER.debug("Command: " + getName());
		if (validateParamsAndDocument(1, params)) {
			Elements links = document.select("a[href]");
			for (Element link : links) {
				if (params[0].equals(link.text())) {
					return new CommandResult(true, getName() + buildParamsString(params));
				}
			}
		}
		return new CommandResult(false, getName() + " " + buildParamsString(params));
	}
	
	public CommandCheckLinkName() {
		super("checkLinkPresentByName");
		
	}
}