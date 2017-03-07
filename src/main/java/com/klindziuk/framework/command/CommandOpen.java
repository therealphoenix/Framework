package com.klindziuk.framework.command;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.klindziuk.framework.util.CommandResult;

public class CommandOpen extends Command {

    public static final String NAME = "open";

    @Override
    public CommandResult run(String... params) {
        LOGGER.debug("Command: " + NAME);

        if (validateParams(2, params)) {
            int CONNECTION_TIMEOUT_MS = Integer.parseInt(params[1]) * 1000;
            try {
                Connection.Response response = Jsoup.connect(params[0]).timeout(CONNECTION_TIMEOUT_MS).execute();
                if (200 == response.statusCode()) {
                    document = response.parse();
                    return new CommandResult(true, NAME  + buildParamsString(params));
                }
            } catch (IOException e) {
                LOGGER.error("Unfortunately we can't read the response from " + "\"" + params[0] + "\"", e);
            } catch (IllegalArgumentException ieax) {
                timer.resetTime();
                LOGGER.error("Unfortunately we can't open " + "\"" + params[0] + "\"", ieax);
            }
        }
        document = null;
        return new CommandResult(false, NAME  + buildParamsString(params));
    }
    
    @Override
	String getName() {
		return NAME;
	}
}