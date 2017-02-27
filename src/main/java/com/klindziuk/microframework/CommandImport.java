package com.klindziuk.microframework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandImport {
	Scanner scanner;

	public Map<String, List<String>> fillMap(String importFile) {
		
		Map<String, List<String>> map = new LinkedHashMap<>();
		try {
			File file = new File(importFile);
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

		}
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		
		}catch (IOException e) {

			e.printStackTrace();
		}
		scanner.close();
		return map;
	}

}