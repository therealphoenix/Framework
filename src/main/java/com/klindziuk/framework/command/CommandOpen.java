package com.klindziuk.framework.command;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import com.klindziuk.framework.util.CommandResult;

/**
 * Using for performing command "open"
 *
 */
public class CommandOpen extends Command {
	
	 public CommandOpen() {
		   super("open");
	   }

	@Override
    public CommandResult run(String... params) {
        LOGGER.debug("Command: " + getName());

        if (validateParams(2, params)) {
            
            try {
            	int CONNECTION_TIMEOUT_MS = Integer.parseInt(params[1]) * 1000;
            	// JSoup only downloads html code that is present in that page.
            	// It does not download JavaScript-generated html, linked css or js or
            	// images.That's why our tests will run faster.
            	Connection.Response response = Jsoup.connect(params[0]).timeout(CONNECTION_TIMEOUT_MS).execute();
                if (200 == response.statusCode()) {
                    document = response.parse();
                    return new CommandResult(true, getName()  + buildParamsString(params));
                }
             } catch (IOException e) {
                LOGGER.error("Unfortunately we can't read the response from " + "\"" + buildParamsString(params) + "\"");
                // handling MalformetURLException, because "Connection" re-thows MalformetURLException to IllegalArgumentException
            } catch (NumberFormatException ieax) {
                resetTimer();
                LOGGER.error("Bat timeout data.Command: " + getName() + " " + buildParamsString(params) + " " + "failed.");
            } 
            catch (IllegalArgumentException ieax) {
                resetTimer();
                LOGGER.error("Unfortunately we can't open " + "\"" + buildParamsString(params) + "\"");
            }
           
        }
        document = null;
        return new CommandResult(false, getName() + " " + buildParamsString(params));
    }
  }