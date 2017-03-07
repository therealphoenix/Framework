package com.klindziuk.framework.command;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.klindziuk.framework.util.CommandResult;

class CommandCheckLinkName extends Command {

	public static final String NAME = "checkLinkPresentByName";
	@Override
	public CommandResult run(String... params) {
		LOGGER.debug("Command: " + NAME);
		if (validateParamsAndDocument(1, params)) {
			Elements links = document.select("a[href]");
			for (Element link : links) {
				if (params[0].equals(link.text())) {
					return new CommandResult(true, NAME + " " + "\"" + params[0] + "\"");
				}
			}
		}
		return new CommandResult(false, NAME + " " + "\"" + params[0] + "\"");
	}
	
	@Override
	String getName() {
		return NAME;
	}
}