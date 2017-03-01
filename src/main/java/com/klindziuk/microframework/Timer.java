package com.klindziuk.microframework;

public class Timer {
	
	private long startTime;
	private long stopTime;
		
	public void start() {
		this.startTime = System.nanoTime();
	}
	
	public void stop() {
		this.stopTime = System.nanoTime();
	}
	public long getTestTime() {
		return (this.stopTime - this.startTime) / 100000000; 	// convert from nanoseconds to seconds
		}
	
	

	
}
