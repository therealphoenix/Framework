package com.klindziuk.framework;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class CommandCheckLink extends Command {
	public static final String name = "checkLinkPresentByHref";

	@Override
	public CommandResult run(String line,String... params) {
		timer.start();
		System.out.println("Command: " + name);
		validateParams(params);
		
		Elements links = document.select("a[href]");
		for (Element link : links) {
			if (name.equals(link.text())) {
				timer.stop();
				return new CommandResult(true, line, timer.getTestTime());
			}
		}
				timer.stop();
				return new CommandResult(false, line, timer.getTestTime());
			}
		
	
	@Override
	public boolean validateParams(String... params) {
		if (params.length < 1) {
			throw new NotEnoughArgumenException();
		}
		if (params.length > 1) {
			throw new ManyArgumentException();
		}
		if (null == document) {
			throw new NullDocumentException();
		}
		return false;
	}
}
