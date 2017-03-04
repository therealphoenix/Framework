package com.klindziuk.framework;

public class CommandCheckContainsText extends Command {

	   public static final String name = "checkPageContains";

	   @Override
	   public CommandResult run(String...params) {
		   
	       System.out.println("Command: " + name);
	       validateParams(params);
	       return new CommandResult(document.toString().contains(params[0]),name + " " + "\"" + params[0] + "\"");
	   }

	   @Override
	   public void validateParams(String...params) {
		   if(params.length < 1) {
			   throw new IllegalArgumentException(NOT_ENOUGH_ARGS);
		   }
	       if(params.length > 1){
	    	   throw new IllegalArgumentException(TOO_MANY_ARGS);
	       }
	       if(null == document){
	    	   throw new IllegalStateException(NULL_DOCUMENT);
	       }
	       
	   }
	}
