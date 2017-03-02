package com.klindziuk.framework;

import java.text.DecimalFormat;

public class CommandResult {
	String format = "#0.000";
		
	private boolean result;
	private String command;
	private float time;

	public CommandResult(boolean result, String command, float time) {
		this.result = result;
		this.command = command;
		this.time = time;
	}

	public boolean isResult() {
		return result;
	}

	public float getTime() {
		return time;
	}

	@Override
	public String toString() {
		return (result ? "+" : "!") + " [" + command + "] " + new DecimalFormat(format).format(time);
	}
}
