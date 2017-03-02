package com.klindziuk.framework;

import java.util.EnumSet;
import java.util.HashMap;

enum CommandEnum {
	   open(new CommandOpen()),
	   checkLinkPresentByHref(new CommandCheckLink()),
	   checkLinkPresentByName(new CommandCheckLinkName()),
	   checkPageTitle(new CommandCheckTitle()),
	   checkPageContains(new CommandCheckContains());

	   private static HashMap<String, CommandEnum> commandMap;

	   static {
	       commandMap = new HashMap<>();
	       for (CommandEnum s : EnumSet.allOf(CommandEnum.class))
	           commandMap.put(s.name().toUpperCase(), s);
	   }

	   private Command command;

	   CommandEnum(Command command) {
	       this.command = command;
	   }

	   public static Command getCommandByName(String name) throws UnsupportedOperationException {
		   if(commandMap.containsKey(name)) {
			   throw new IllegalArgumentException();
		   }
	       return commandMap.get(name.toUpperCase()).command;
	   }
	}
