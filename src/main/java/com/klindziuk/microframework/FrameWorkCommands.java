package com.klindziuk.microframework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FrameWorkCommands {
	protected Scanner scanner;
	protected Document document;
	
	//JSoup only downloads html code that is present in that page. 
	//It does not download JavaScript-generated html, linked css or js or images.
	public boolean open1(String URL, String timeout) {

		int CONNECTION_TIMEOUT_MS = Integer.parseInt(timeout) * 1000;

		try {
			document = Jsoup.connect(URL).data("query", "Java").timeout(CONNECTION_TIMEOUT_MS).get();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return true;
	}

	public boolean open(String URL, String timeout) {

		// Timeout in millis.
		int CONNECTION_TIMEOUT_MS = Integer.parseInt(timeout) * 1000;

		try {

			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECTION_TIMEOUT_MS)
					.setConnectTimeout(CONNECTION_TIMEOUT_MS).setSocketTimeout(CONNECTION_TIMEOUT_MS).build();

			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet request = new HttpGet(URL);
			request.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(request);

			// Get the response
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder builder = new StringBuilder();

			String html = "";
			while ((html = reader.readLine()) != null) {
				builder.append(html);
			}
			// html = builder.toString();
			document = Jsoup.parse(builder.toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public boolean checkLinkPresentByName(String name) {
		Elements links = document.select("a[href]");
		for (Element link : links) {
			if (name.equals(link.text()))
				return true;
		}
		return false;
	}

	public boolean checkLinkPresentByHref(String href) {
		Elements links = document.select("a[href]");
		for (Element link : links) {
			if (href.equals(link.attr("href")))
				return true;
		}
		return false;
	}

	public boolean checkPageTitle(String text) {
		return text.equals(document.title());
	}

	public boolean checkPageContains(String text) {
		return document.toString().contains(text);
	}
}
