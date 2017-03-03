package com.klindziuk.framework;

public class CommandCheckContains extends Command {

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
			   throw new NotEnoughArgumentException();
		   }
	       if(params.length > 1){
	    	   throw new ManyArgumentException();
	       }
	       if(null == document){
	    	   throw new NullDocumentException();
	       }
	       
	   }
	}
