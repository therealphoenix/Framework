package com.klindziuk.framework;

import org.jsoup.select.Elements;

class CommandCheckLinkName extends Command {
	public static final String name = "checkLinkPresentByName";

	@Override
	public CommandResult run(String... params) {

		System.out.println("Command: " + name);
		validateParams(params);
		Elements links = document.select("a[href]");
		return new CommandResult(links.contains(params[0]), name + " " + "\"" + params[0] + "\"");
	}

	@Override
	public void validateParams(String... params) {
		if (params.length < 1) {
			throw new NotEnoughArgumentException();
		}
		if (params.length > 1) {
			throw new ManyArgumentException();
		}
		if (null == document) {
			throw new NullDocumentException();
		}

	}
}