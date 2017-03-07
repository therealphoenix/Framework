package com.klindziuk.framework.command;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.klindziuk.framework.util.CommandResult;

class CommandCheckLinkHref extends Command {

	public static final String NAME = "checkLinkPresentByHref";
	public static final String HREF = "href";

	@Override
	public CommandResult run(String... params) {
		LOGGER.debug("Command: " + NAME);
		if (validateParamsAndDocument(1, params)) {
			Elements links = document.select("a[href]");
			for (Element link : links) {

				if (params[0].equals(link.attr(HREF))) {
					return new CommandResult(true, NAME + " " + "\"" + params[0] + "\"");
				}
			}
		}
		return new CommandResult(false, NAME + " " + "\"" + params[0] + "\"");
	}
}
