package com.klindziuk.framework;

import org.jsoup.nodes.Document;

public abstract class Command {
	   public static final String name = "unknown";
	   protected String [] params;
	   protected Timer timer = new Timer();
	   static protected Document document; 

	   abstract CommandResult run(String...params);
	   abstract void validateParams(String...params) throws IllegalArgumentException;
	   
	   public CommandResult runWithTimer(String...params){
		   Timer timer = new Timer();
		   timer.start();
		   CommandResult cr = run(params);
		   timer.stop();
		   cr.setTime(timer.getTestTime());
		   return cr;
	   }
	   
	   
}