package com.klindziuk.microframework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandImport {
	private final String IN_PATH = "instructions.txt";
//	private final String OUT_PATH = "frameWorkLog.txt";
	Scanner scanner;
	
	private Map<String, List<String>> commandMap;
	
	public Map<String, List<String>> getCommandMap() {
		return commandMap;
	}
	
	public CommandImport() {
	 this.commandMap = fillMap();
	}

	private Map<String, List<String>> fillMap() {
		Map<String, List<String>> map = new LinkedHashMap<>();

		try {
			File file = new File(IN_PATH);
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				List<String> listOfParameters = new ArrayList<>();
				Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(line);
				while (m.find()) {
					listOfParameters.add(m.group(1).replace("\"", ""));
				}
				map.put(listOfParameters.get(0), listOfParameters);
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
		scanner.close();
		return map;
	}

}