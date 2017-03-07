package com.klindziuk.framework.util;

public class Timer {
	public static final int RESET_TIME = 0;

	private long startTime;
	private long stopTime;

	public void start() {
		this.startTime = System.nanoTime();
	}

	public void stop() {
		if(RESET_TIME == this.startTime){
		    this.stopTime = RESET_TIME;
		}
		else {
			this.stopTime = System.nanoTime();
	   }
	}

	public void resetTime(){
		this.startTime = RESET_TIME;
	}

	public long getTestTime() {
		return (this.stopTime - this.startTime) / 100000000; // convert from nanoseconds to seconds
	}
}
