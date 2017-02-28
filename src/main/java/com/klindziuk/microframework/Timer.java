package com.klindziuk.microframework;

public class Timer {
	
	private long startTime;
	private long stopTime;
	private long allTestTime;
	
	public void setStartTime() {
		this.startTime = System.nanoTime();
	}
	
	public void setStopTime() {
		this.stopTime = System.nanoTime();
	}
	public long getTestTime() {
		return this.stopTime - this.startTime; 	
		}
	
	public void calculateFullTime(long time) {
		this.allTestTime = this.allTestTime + time;
	}
		
	public long getFullTime(){
		return allTestTime  / 100000000;
	}
	
}
