package com.klindziuk.framework.util;

import java.text.DecimalFormat;

public class CommandResult {
	public static final String format = "#0.000";
		
	private boolean result;
	private String command;
	private float time;

	public CommandResult(boolean result, String command) {
		this.result = result;
		this.command = command;
	}

	public CommandResult(boolean result, String command, float time) {
		this(result, command);
		this.time = time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public boolean isPassed() {
		return result;
	}

	public float getTime() {
		return time;
	}

	@Override
	public String toString() {
		return (result ? "+" : "!") + " [" + command + "] " + new DecimalFormat(format).format(time);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((command == null) ? 0 : command.hashCode());
		result = prime * result + (this.result ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommandResult other = (CommandResult) obj;
		if (command == null) {
			if (other.command != null)
				return false;
		} else if (!command.equals(other.command))
			return false;
		if (result != other.result)
			return false;
		return true;
	}
	
}
