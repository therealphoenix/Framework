package com.klindziuk.framework;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

class CommandOpen extends Command {
	   public static final String name = "open";

	   @Override
	   public CommandResult run(String...params) {
		    System.out.println("Command: " + name);
	       validateParams(params);
	       
	       int CONNECTION_TIMEOUT_MS = Integer.parseInt(params[1]) * 1000;

			try {
				Connection.Response response = Jsoup.connect(params[0]).timeout(CONNECTION_TIMEOUT_MS).execute();
				if(200 == response.statusCode()) {
				document = response.parse();
				
				return new CommandResult(true,name + " " + "\"" + params[0] + "\""+ " \"" + params[1] + "\"" );
			}
			} catch (IOException e) {

			} catch (IllegalArgumentException ieax) {
				timer.setExceptionTime();
				System.out.println("Unfortunately we can't try open " + "\"" + params[0] + "\"");
				return new CommandResult(false,name + " " + "\"" + params[0] + "\""+ "\"" + params[1] + "\"" );
			}
			return new CommandResult(false,name + " " + "\"" + params[0] + "\""+ "\"" + params[1] + "\"" );
	      
	   }

	   @Override
	   public void validateParams(String...params) {
		   if(params.length < 2) {
			   throw new NotEnoughArgumenException();
		   }
	       if(params.length > 2){
	    	   throw new ManyArgumentException();
	       }
	    	      
	   }
	}