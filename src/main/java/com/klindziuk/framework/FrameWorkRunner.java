package com.klindziuk.framework;

import com.klindziuk.framework.util.CheckArguments;
import com.klindziuk.framework.util.TotalResult;

public class FrameWorkRunner {
	
	public static void main(String[] args) {
		CheckArguments ca = new CheckArguments();
		String[] parameters = ca.FullCheck(args);
		
		CommandExecutor fe = new CommandExecutor();
		TotalResult totalResult = new TotalResult();
		fe.execute(args[0]);
		totalResult.updateLog4jConfiguration(parameters[1]);
		totalResult.printResult(fe.getLogList());
	}

	
}
