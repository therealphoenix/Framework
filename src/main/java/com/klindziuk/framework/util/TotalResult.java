package com.klindziuk.framework.util;

import java.util.List;
import org.apache.log4j.Logger;

public class TotalResult {
	
	private static final Logger LOGGER = Logger.getLogger(TotalResult.class);

	public static void printResult(List<CommandResult> logList) {
		int quantityOfTests = logList.size();
		int passedTestQuantity = 0;
		int failedTestQuantity = 0;
		float fullTimeOfTests = 0;

		for (CommandResult cr : logList) {
			LOGGER.info(cr.toString());
			fullTimeOfTests = fullTimeOfTests + cr.getTime();
			if (cr.isPassed()) {
				passedTestQuantity++;
			} else {
				failedTestQuantity++;
			}
		}

		LOGGER.info(String.format("Total tests: %s  ", logList.size()));
		LOGGER.info(String.format("Passed/Failed: %d/%d ", passedTestQuantity, failedTestQuantity));
		LOGGER.info(String.format("Total time: %.3f ", fullTimeOfTests));
		LOGGER.info(String.format("Average time: %.3f ", fullTimeOfTests / quantityOfTests));
	}

}
