package com.klindziuk.framework;

import org.apache.log4j.Logger;
import org.jsoup.select.Elements;

class CommandCheckLink extends Command {
	public static final String name = "checkLinkPresentByHref";
	 private static final Logger logger = Logger.getLogger(CommandCheckLink.class);

	@Override
	public CommandResult run(String... params) {
		System.out.println("Command: " + name);
		validateParams(params);
		Elements links = document.select("a[href]");
		return new CommandResult( (links.contains(params[0]) && validateParams(params)),
				 name + " " + "\"" + params[0] + "\"");
			}
	
	
	@Override
	   public boolean validateParams(String...params) {
		   if(params.length < 1) {
			   logger.warn(NOT_ENOUGH_ARGS + name + " " + "\"" + params[0] + "\"");
			   return false;
		   }
	       if(params.length > 1){
	    	   logger.warn(TOO_MANY_ARGS + name + " " + "\"" + params[0] + "\""); 
	    	   return false;
	       }
	       if(null == document){
	    	   logger.warn(NULL_DOCUMENT);
	    	   return false;
	       }
	       return true;
	       
	   }
}
