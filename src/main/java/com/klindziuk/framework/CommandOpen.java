package com.klindziuk.framework;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

class CommandOpen extends Command {
	   public static final String name = "open";

	   @Override
	   public CommandResult run(String line, String...params) {
		   
		   timer.start();
	       System.out.println("Command: " + name);
	       validateParams(params);
	       
	       // Put command logic implementation here:
	       // 1. initialize the document object declared in the parent class
	       int CONNECTION_TIMEOUT_MS = Integer.parseInt(params[1]) * 1000;

			try {
				Connection.Response response = Jsoup.connect(params[0]).timeout(CONNECTION_TIMEOUT_MS).execute();
				if(200 == response.statusCode()) {
				document = response.parse();
				timer.stop();
				return new CommandResult(true, line, timer.getTestTime());
				
				}
			} catch (IOException e) {

			} catch (IllegalArgumentException ieax) {
				timer.setExceptionTime();
				System.out.println("Unfortunately we can't try open " + "\"" + params[0] + "\"");
				return new CommandResult(false, line, timer.getTestTime());
			}
			timer.stop();
			return new CommandResult(false, line, timer.getTestTime());
	        // dummy document string
	        // replace name with required string command + params and set valid timing
	   }

	   @Override
	   public boolean validateParams(String...params) {
		   if(params.length < 2) {
			   throw new NotEnoughArgumenException();
		   }
	       if(params.length > 2){
	    	   throw new ManyArgumentException();
	       }
	    //   System.out.println("Number of params" + params.length);
	       return false;
	   }
	}