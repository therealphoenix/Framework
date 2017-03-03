package com.klindziuk.framework;


public class Timer {
	
	private long startTime;
	private long stopTime;
			
	public void start() {
		this.startTime = System.nanoTime();
	}
	
	public void stop() {
		if(0 == this.startTime){
		this.stopTime = 0;
		}
		else { 
			this.stopTime = System.nanoTime();
	   }
	} 
	public void setExceptionTime(){
		this.startTime = 0;
	}
	public long getTestTime() {
		return (this.stopTime - this.startTime) / 100000000; // convert from nanoseconds to seconds
	}
	
	
}
