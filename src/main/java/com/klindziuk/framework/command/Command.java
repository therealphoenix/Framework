package com.klindziuk.framework.command;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import com.klindziuk.framework.util.CommandResult;
import com.klindziuk.framework.util.Timer;

/**
 * Abstract superclass for command classes
 *
 */
public abstract class Command {

	protected static final Logger LOGGER = Logger.getLogger(Command.class);
	public static final String TOO_MANY_ARGS = "Too many parameters at command: ";
	public static final String NOT_ENOUGH_ARGS = "Not enough parameters at: ";
	public static final String NULL_DOCUMENT = "Cannot instantiate test without opened page.";
	protected static Document document;
	private String name;
	private Timer timer;

	public Command(String name) {
		this.name = name;
		this.timer = new Timer();
	}
		/**
	 * execution of test command
	 * 
	 * @param params
	 * @return
	 */
	abstract CommandResult run(String... params);

	public void resetTimer() {
		timer.resetTime();
	}
	/**
	 * run command and calculating time of execution
	 * 
	 * @param params
	 * @return instance of CommandResult class
	 */
	public CommandResult runWithTimer(String... params) {
		Timer timer = new Timer();
		timer.start();
		CommandResult cr = run(params);
		timer.stop();
		cr.setTime(timer.getTestTime());
		return cr;
	}
	/**
	 * making string of parameters
	 * 
	 * @param params
	 * @return String with parameters
	 */
	public String buildParamsString(String... params) {
		StringBuilder builder = new StringBuilder();
		for (String param : params) {
			builder.append("\"").append(param).append("\"").append(" ");
		}
		return builder.toString().trim();
	}
	/**
	 * validation of parameters
	 * 
	 * @param length
	 * @param params
	 * @return result of validation
	 */
	protected boolean validateParams(int length, String... params) {
		if (params.length < length) {
			LOGGER.error(NOT_ENOUGH_ARGS + "[" + getName() + " " + buildParamsString(params) + "]");
			return false;
		}
		if (params.length > length) {
			LOGGER.error(TOO_MANY_ARGS + "[" + getName() + " " + buildParamsString(params) + "]");
			return false;
		}
		return true;
	}
	/**
	 * validation existing of document
	 */
	protected boolean validateNullDocument() {
		if (null == document) {
			LOGGER.error(getName() + ": " + NULL_DOCUMENT);
			return false;
		}
		return true;
	}

	protected boolean validateParamsAndDocument(int length, String... params) {
		return validateNullDocument() && validateParams(length, params);
	}

	public String getName() {
		return this.name;
	}
}