package com.klindziuk.framework;

import java.util.HashMap;

enum CommandEnum {
	
	    open(new CommandOpen()),
	    checkLinkPresentByHref(new CommandCheckLinkHref()),
	    checkLinkPresentByName(new CommandCheckLinkName()),
	    checkPageTitle(new CommandCheckTitle()),
	    checkPageContainsText(new CommandCheckContainsText());

	    private static HashMap<String, Command> commandMap;

	    static {
	        commandMap = new HashMap<>();
	        for (CommandEnum s : CommandEnum.values())
	            commandMap.put(s.name().toUpperCase(), s.command);
	    }

	    private Command command;

	    CommandEnum(Command command) {
	        this.command = command;
	    }

	    public static Command getCommandByName(String name) throws UnsupportedOperationException {
	        if(commandMap.containsKey(name)) {
	            throw new IllegalArgumentException();
	        }
	        return commandMap.get(name.toUpperCase());
	    }
	}
	
