package com.klindziuk.framework.command;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.klindziuk.framework.util.CommandResult;

/**
 * using for performing command "checkLinkPresentByHref"
 *
 */
class CommandCheckLinkHref extends Command {

	public static final String HREF = "href";

	@Override
	public CommandResult run(String... params) {
		LOGGER.debug("Command: " + getName());
		if (validateParamsAndDocument(1, params)) {
			Elements links = document.select("a[href]");
			for (Element link : links) {

				if (params[0].equals(link.attr(HREF))) {
					return new CommandResult(true, getName() + buildParamsString(params));
				}
			}
		}
		return new CommandResult(false, getName()+ " " + buildParamsString(params));
	}
	
	public CommandCheckLinkHref() {
		super("checkLinkPresentByHref");
		
	}
}
