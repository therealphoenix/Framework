package com.klindziuk.framework;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class CommandOpen extends Command {
	   public static final String name = "open";
	   private static final Logger logger = Logger.getLogger(CommandOpen.class);

	   @Override
	   public CommandResult run(String...params) {
		    System.out.println("Command: " + name);
	       validateParams(params);
	       
	       int CONNECTION_TIMEOUT_MS = Integer.parseInt(params[1]) * 1000;

			try {
				Connection.Response response = Jsoup.connect(params[0]).timeout(CONNECTION_TIMEOUT_MS).execute();
				if(200 == response.statusCode()) {
				document = response.parse();
				return new CommandResult(validateParams(params),name + " " + "\"" + params[0] + "\""+ " \"" + params[1] + "\"" );
			}
			} catch (IOException e) {

			} catch (IllegalArgumentException ieax) {
				timer.setExceptionTime();
				document = null;
				logger.error("Unfortunately we can't try open " + "\"" + params[0] + "\"");
				return new CommandResult(false,name + " " + "\"" + params[0] + "\""+ "\"" + params[1] + "\"" );
			}
			document = null;
			return new CommandResult(validateParams(params),name + " " + "\"" + params[0] + "\""+ "\"" + params[1] + "\"" );
	   }

	   @Override
	   public boolean validateParams(String...params) {
		   if(params.length < 2) {
			   logger.error(NOT_ENOUGH_ARGS + name + " " + "\"" + params[0] + "\"");
			   return false;
		   }
	       if(params.length > 2){
	    	   logger.error(TOO_MANY_ARGS + name + " " + "\"" + params[0] + "\""); 
	    	   return false;
	       }
	       return true;
	   }
	}