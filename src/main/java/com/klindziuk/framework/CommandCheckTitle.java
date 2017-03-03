package com.klindziuk.framework;

class CommandCheckTitle extends Command {
	   public static final String name = "checkPageTitle";
	   @Override
	   public CommandResult run(String...params) {
		  
	       System.out.println("Command: " + name);
	       validateParams(params);
	      return new CommandResult(params[0].equals(document.title()),name + " " + "\"" + params[0] + "\"");
	       
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
