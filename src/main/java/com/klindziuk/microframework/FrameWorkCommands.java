package com.klindziuk.microframework;

import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FrameWorkCommands {
	protected Scanner scanner;
	protected Document document;

	// JSoup only downloads html code that is present in that page.
	// It does not download JavaScript-generated html, linked css or js or
	// images.That's why our tests will run faster.
	public boolean open1(String URL, String timeout) {
		
		int CONNECTION_TIMEOUT_MS = Integer.parseInt(timeout) * 1000;

		try {
			Connection.Response response = Jsoup.connect(URL).timeout(CONNECTION_TIMEOUT_MS).execute();
			if(200 == response.statusCode()) {
			document = response.parse();
			return true;
			}
		} catch (IOException e) {

		} catch (IllegalArgumentException ieax) {
			System.out.println("Unfortunately we can't try open " + "\"" + URL + "\"");
			return false;
		}
		return false;
	}

	public boolean checkLinkPresentByName(String name) {
		if ((null == document) || (null == name)) {
			return false;
		} else {
			Elements links = document.select("a[href]");
			for (Element link : links) {
				if (name.equals(link.text()))
					return true;
			}
		}
		return false;
	}

	public boolean checkLinkPresentByHref(String href) {
		if ((null == document) || (null == href)) {
			return false;
		} else {
			Elements links = document.select("a[href]");
			for (Element link : links) {
				if (href.equals(link.attr("href")))
					return true;
			}
		}
		return false;
	}

	public boolean checkPageTitle(String text) {
		if ((null == document) || (null == text)) {
			return false;
		} else {
			return text.equals(document.title());
		}
	}

	public boolean checkPageContains(String text) {
		if ((null == document) || (null == text)) {
			return false;
		} else {
			return document.toString().contains(text);
		}
	}
}
