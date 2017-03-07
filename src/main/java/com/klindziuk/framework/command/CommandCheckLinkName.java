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
					return new CommandResult(true, NAME + buildParamsString(params));
				}
			}
		}
		return new CommandResult(false, NAME  + buildParamsString(params));
	}
	
	@Override
	String getName() {
		return NAME;
	}
}