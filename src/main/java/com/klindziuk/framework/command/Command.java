package com.klindziuk.framework.command;

import org.jsoup.nodes.Document;

import com.klindziuk.framework.util.CommandResult;
import com.klindziuk.framework.util.Timer;

public abstract class Command {
	public static final String name = "unknown";
	public static final String TOO_MANY_ARGS = "Too many parameters at: ";
	public static final String NOT_ENOUGH_ARGS = "Not enough parameters at: ";
	public static final String NULL_DOCUMENT = "Cannot instantiate test without opened page.";
	protected String[] params;
	protected Timer timer = new Timer();
	static protected Document document;

	abstract CommandResult run(String... params);

	abstract boolean validateParams(String... params) throws IllegalArgumentException;

	public CommandResult runWithTimer(String... params) {
		Timer timer = new Timer();
		timer.start();
		CommandResult cr = run(params);
		timer.stop();
		cr.setTime(timer.getTestTime());
		return cr;
	}
}