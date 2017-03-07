package com.klindziuk.framework.command;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.klindziuk.framework.util.CommandResult;
import com.klindziuk.framework.util.Timer;

public abstract class Command {

	protected static final Logger LOGGER = Logger.getLogger(Command.class);

	public static final String NAME = "unknown";
	public static final String TOO_MANY_ARGS = "Too many parameters at: ";
	public static final String NOT_ENOUGH_ARGS = "Not enough parameters at: ";
	public static final String NULL_DOCUMENT = "Cannot instantiate test without opened page.";

	protected static Document document;

	protected Timer timer = new Timer();

	abstract CommandResult run(String... params);

	public CommandResult runWithTimer(String... params) {
		Timer timer = new Timer();
		timer.start();
		CommandResult cr = run(params);
		timer.stop();
		cr.setTime(timer.getTestTime());
		return cr;
	}

	protected boolean validateParams(int length, String...params) {
		if(params.length < length) {
			LOGGER.error(NOT_ENOUGH_ARGS + NAME + " " + "\"" + params[0] + "\"");
			return false;
		}
		if(params.length > length){
			LOGGER.error(TOO_MANY_ARGS + NAME + " " + "\"" + params[0] + "\"");
			return false;
		}
		return true;
	}

	protected boolean validateNullDocument() {
		if (null == document) {
			LOGGER.error(NULL_DOCUMENT);
			return false;
		}
		return true;
	}

	protected boolean validateParamsAndDocument(int length, String...params) {
		return validateParams(length, params) && validateNullDocument();
	}

}