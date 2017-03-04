package CommandTest;


import java.lang.reflect.Field;

import org.apache.http.impl.io.SocketOutputBuffer;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.klindziuk.framework.CommandOpen;
import com.klindziuk.framework.CommandCheckContains;
import com.klindziuk.framework.CommandResult;
import com.klindziuk.framework.Command;


public class CommandCheckContainsTest {
	
	CommandResult result;
	CommandCheckContains cC;
	CommandOpen cO;
	String URL = "http://protesting.ru/automation/";
	String timeout = "3";
	

 
  @BeforeMethod
  public void beforeMethod() {
	  cO = new CommandOpen();
	  cO.run(URL,timeout);
	  cC = new  CommandCheckContains();
	 
  }
    @AfterMethod
  public void afterMethod() {
	  cO = null;
	  cC = null;
  }

  @BeforeClass
  public void beforeClass() {
  }  
  

  @AfterClass
  public void afterClass() {
  }
  
  
  //  method should return valid(true) instance of CommandResult class
  @Test
  public void run_Test_ValidSizeParams_ValidArguments_Positive() {
	  
	  String text = "Software Automation Tester";
	  result = new CommandResult(true, "checkPageContains" + " " + "\"" + text + "\"");
	  Assert.assertEquals(result, cC.run(text));
	 }
  
  //method should return invalid(false) instance of CommandResult class
  @Test
  public void run_Test_ValidSizeParams_ValidArguments_Negative() {
	  
	  String text = "I think this page do not have this text";
	  result = new CommandResult(false, "checkPageContains" + " " + "\"" + text + "\"");
	  Assert.assertEquals(result, cC.run(text));
	}
  //method should throw NullPointerException for null String
  @Test(expectedExceptions = NullPointerException.class)
  public void run_Test_ValidSizeParams_NullArguments() {
	  String text = null;
	  result = new CommandResult(true, "checkPageContains" + " " + "\"" + text + "\"");
	   cC.run(text);
  }
  //method should throw NullPointerException for empty String
  @Test(expectedExceptions = NullPointerException.class)
	   public void run_Test_ValidSizeParams_EmptyString() {
	 	  String text = null;
	 	  result = new CommandResult(true, "checkPageContains" + " " + "\"" + text + "\"");
	 	   cC.run(text);	   
	   }
  
//method should throw NullDocumentException for null document
  @Test(expectedExceptions = IllegalStateException.class)
  public void validateParams_Test_Null_Document() {
	  cO.run("https://jsoup.org/cookbook111/", "3"); // reset to null document
	 CommandCheckContains command = new CommandCheckContains();
	  String text = "Doesn't matter";
	  command.validateParams(text);
	    }
//method should throw ManyArgumentsException if length of array will be greater than 1.
    @Test(expectedExceptions = IllegalArgumentException.class)
  public void validateParams_Test_ToManyArguments() {
	 CommandCheckContains command = new CommandCheckContains();
	  String[] params = {"1","2","100"};
	  command.validateParams(params);
	  
  }
  //method should throw NotEnoughException if length of array will be smaller than 0.
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void validateParams_Test_NotEnoughArguments() {
  	 CommandCheckContains command = new CommandCheckContains();
  	  String[] params = {};
  	  command.validateParams(params);
}
  //method should throw NullDocumentException for null String
    @Test(expectedExceptions = NullPointerException.class)
    public void validateParams_Test_Null_params() {
  	 CommandCheckContains command = new CommandCheckContains();
  	  String text = null;
  	  command.validateParams(text);
   }
   
//  method should return valid(true) instance of CommandResult class with time of running for this method
    @Test
    public void commandWithTimer_Test_ValidArguments_Positive() {
    	String text = "Software Automation Tester";
  	  CommandResult expectedResult  = cC.runWithTimer(text);
  	  result = new CommandResult(true, "checkPageContains" + " " + "\"" + text + "\"");
  	  Assert.assertEquals(expectedResult, result);
  	  Assert.assertTrue(expectedResult.getTime() > 0);
    	
  //  method should return valid(true) instance of CommandResult class with time of running for this method  	  
    }
    @Test
    public void commandWithTimer_Test_ValidArguments_Negative() {
    	String text = "I think this page do not have this text";
  	  CommandResult expectedResult  = cC.runWithTimer(text);
  	  Assert.assertEquals(expectedResult, result);
  	  Assert.assertTrue(expectedResult.getTime() > 0);
    	
    }
  //method should throw NullDocumentException for null String
    @Test(expectedExceptions = NullPointerException.class)
    public void commandWithTimer_Test_NullArguments() {
  	  String text = null;
  	 result  = cC.runWithTimer(text);
  	  
    }
    //method should throw NullPointerException for empty String
    @Test(expectedExceptions = IllegalArgumentException.class)
  	   public void commandWithTimer_Test_ValidSizeParams_EmptyString() {
    	String[] params = {};
  	 	result  = cC.runWithTimer(params);	   
  	   }

}