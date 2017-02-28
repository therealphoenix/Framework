package com.klindziuk.microframework;

public class Timer {
	private long startTime;
	private long stopTime;
	
	public void setStartTime() {
		this.startTime = System.nanoTime();
	}
	
	public void setStopTime() {
		this.stopTime = System.nanoTime();
	}
	public float getFullTime() {
	    return (this.stopTime - this.startTime) / 100000000;
	}
	
}
