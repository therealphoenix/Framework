package com.klindziuk.framework;

class CommandCheckTitle extends Command {
	   public static final String name = "checkPageTitle";
	   @Override
	   public CommandResult run(String line, String...params) {
		   timer.start();
	       System.out.println("Command: " + name);
	       validateParams(params);
	       if(params[0].equals(document.title())) {
	    	   timer.stop();
	    	   return new CommandResult(true, name, timer.getTestTime());
	       }
	       else {
	    	   timer.stop();
	    	   return new CommandResult(false, name,timer.getTestTime() );
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
