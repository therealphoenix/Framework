package com.klindziuk.framework.command;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import com.klindziuk.framework.util.CommandResult;


/**
 * using for performing command "open"
 *
 */
public class CommandOpen extends Command {

	@Override
    public CommandResult run(String... params) {
        LOGGER.debug("Command: " + getName());

        if (validateParams(2, params)) {
            int CONNECTION_TIMEOUT_MS = Integer.parseInt(params[1]) * 1000;
            try {
            	// JSoup only downloads html code that is present in that page.
            	// It does not download JavaScript-generated html, linked css or js or
            	// images.That's why our tests will run faster.
            	Connection.Response response = Jsoup.connect(params[0]).timeout(CONNECTION_TIMEOUT_MS).execute();
                if (200 == response.statusCode()) {
                    document = response.parse();
                    return new CommandResult(true, getName()  + buildParamsString(params));
                }
               
            } catch (IOException e) {
                LOGGER.error("Unfortunately we can't read the response from " + "\"" + params[0] + "\"");
                // handling MalformetURLException, because "Connection" re-thows MalformetURLException to IllegalArgumentException
            } catch (IllegalArgumentException ieax) {
                resetTimer();
                LOGGER.error("Unfortunately we can't open " + "\"" + params[0] + "\"");
            }
        }
        document = null;
        return new CommandResult(false, getName() + " " + buildParamsString(params));
    }
    
   public CommandOpen() {
	   super("open");
	   
   }
}