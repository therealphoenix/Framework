package com.klindziuk.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TotalResult {
	
	private static final Logger logger = Logger.getLogger(TotalResult.class);
	
	public void updateLog4jConfiguration(String logFile) { 
	    Properties props = new Properties(); 
	    try { 
	        InputStream configStream = getClass().getResourceAsStream( "/log4j.properties"); 
	        props.load(configStream); 
	        configStream.close(); 
	    } catch (IOException e) { 
	        System.out.println("Error: Cannot laod configuration file "); 
	    } 
	    props.setProperty("log4j.appender.file.File", logFile); 
	    PropertyConfigurator.configure(props); 
	 }

	public void printResult(List<CommandResult> logList) {
		int quantityOfTests = logList.size();
		int passedTestQuantity = 0;
		int failedTestQuantity = 0;
		float fullTimeOfTests = 0;

		for (CommandResult cr : logList) {
			logger.info(cr.toString());
			fullTimeOfTests = fullTimeOfTests + cr.getTime();
			if (true == cr.isResult()) {
				passedTestQuantity++;
			} else {
				failedTestQuantity++;
			}
		}

		logger.info(String.format("Total tests: %s  ", logList.size()));
		logger.info(String.format("Passed/Failed: %d/%d ", passedTestQuantity, failedTestQuantity));
		logger.info(String.format("Total time: %.3f ", fullTimeOfTests));
		logger.info(String.format("Average time: %.3f ", fullTimeOfTests / quantityOfTests));

	}



}
