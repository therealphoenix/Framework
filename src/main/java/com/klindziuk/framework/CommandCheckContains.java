package com.klindziuk.framework;

class CommandCheckContains extends Command {

	   public static final String name = "checkPageContains";

	   @Override
	   public CommandResult run(String line,String...params) {
		   timer.start();
	       System.out.println("Command: " + name);
	       validateParams(params);
	       if(document.toString().contains(params[0])){
	    	   timer.stop();
	    	   return new CommandResult(true, line, 0.0f);
	       }
	       else {
	       	    	   timer.stop();
	       return new CommandResult(false, line, 0.0f);
	       }
	   }

	   @Override
	   public boolean validateParams(String...params) {
		   if(params.length < 1) {
			   throw new NotEnoughArgumenException();
		   }
	       if(params.length > 1){
	    	   throw new ManyArgumentException();
	       }
	       if(null == document){
	    	   throw new NullDocumentException();
	       }
	        return false;
	      
	   }
	}
