package com.klindziuk.framework;

import org.jsoup.nodes.Document;

abstract class Command {
	   public static final String name = "unknown";
	   protected String [] params;
	   protected Timer timer = new Timer();
	   static protected Document document; // Replace String with Document

	   abstract CommandResult run(String line,String...params);
	   abstract boolean validateParams(String...params) throws IllegalArgumentException;
	   
	   
}