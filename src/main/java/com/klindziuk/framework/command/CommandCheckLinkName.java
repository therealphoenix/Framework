package com.klindziuk.framework.command;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.klindziuk.framework.util.CommandResult;

class CommandCheckLinkName extends Command {
	public static final String name = "checkLinkPresentByName";
	private static final Logger logger = Logger.getLogger(CommandCheckLinkName.class);

	@Override
	public CommandResult run(String... params) {

		System.out.println("Command: " + name);
		validateParams(params);
		Elements links = document.select("a[href]");
		for (Element link : links) {
			if (params[0].equals(link.text()) && (validateParams(params))) {
				return new CommandResult(true, name + " " + "\"" + params[0] + "\"");
			}
		}
		return new CommandResult(false, name + " " + "\"" + params[0] + "\"");
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