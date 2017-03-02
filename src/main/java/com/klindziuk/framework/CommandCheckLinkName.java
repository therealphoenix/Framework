package com.klindziuk.framework;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class CommandCheckLinkName extends Command {
	   public static final String name = "checkLinkPresentByName";
	   @Override
	   public CommandResult run(String...params) {
		   timer.start();
	       System.out.println("Command: " + name);
	       validateParams(params);
	       Elements links = document.select("a[href]");
			for (Element link : links) {
				if (name.equals(link.text())) {
					timer.stop();
					return new CommandResult(true, name + " " + "\"" + params[0] + "\"");
				}
			}
					timer.stop();
					return new CommandResult(false, name + " " + "\"" + params[0] + "\"");
				}
	   

	   @Override
	   public void validateParams(String...params) {
		   if(params.length < 1) {
			   throw new NotEnoughArgumenException();
		   }
	       if(params.length > 1){
	    	   throw new ManyArgumentException();
	       }
	       if(null == document){
	    	   throw new NullDocumentException();
	       }
	       	      
	   }
	}