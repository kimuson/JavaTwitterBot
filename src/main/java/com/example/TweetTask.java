package com.example;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TweetTask implements Runnable {

	ArrayList<String> rssPathList = new ArrayList<String>();
	String rssPath1 = "https://japanese.engadget.com/rss.xml";
	String rssPath2 = "http://plus.appgiga.jp/feed/user";
	String rssPath3 = "https://codeiq.jp/magazine/feed/";
	String rssPath4 = "http://feeds.rocketnews24.com/rocketnews24?format=xml";

	int i = 0;
	int testCount = 1;
	int j = 1;

	TweetTask() {
		rssPathList.add(rssPath1);
		rssPathList.add(rssPath2);
		rssPathList.add(rssPath3);
		rssPathList.add(rssPath4);
	}

	@Override
	public void run() {
		try {

			if (i > 3) {
				i = 1;
				j++;
			}
			if (j > 2) {
				j = 1;
			}
			System.out.println("つぶやき" + testCount + "回目");
			System.out.println("つぶやいたRSS:" + rssPathList.get(i));
			doTweet(rssPathList.get(i));

		} catch (DOMException | TwitterException e) {
			e.printStackTrace();
			testCount++;
			i++;
		}
	}

	public void doTweet(String path) throws DOMException, TwitterException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(path);
			Element root = document.getDocumentElement();

			/* Get and print Title of RSS Feed. */
			NodeList item_list = root.getElementsByTagName("item");
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.verifyCredentials();

			Date d = new Date();
			SimpleDateFormat d2 = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分ss秒");
			String q2 = d2.format(d);

			if (j == 1) {
				for (int index = 0; index < 2; index++) {
					Element element = (Element) item_list.item(index);
					NodeList item_title = element.getElementsByTagName("title");
					NodeList item_link = element.getElementsByTagName("link");
					twitter.updateStatus(item_title.item(0).getFirstChild().getNodeValue() + "(" + q2 + ")" + "\n"
							+ item_link.item(0).getFirstChild().getNodeValue());
				}
			}

			if (j == 2) {
				for (int index = 2; index < 4; index++) {
					Element element = (Element) item_list.item(index);
					NodeList item_title = element.getElementsByTagName("title");
					NodeList item_link = element.getElementsByTagName("link");
					twitter.updateStatus(item_title.item(0).getFirstChild().getNodeValue() + "(" + q2 + ")" + "\n"
							+ item_link.item(0).getFirstChild().getNodeValue());
				}
			}
			testCount++;
			i++;

		} catch (IOException e) {
			System.out.println("IO Exception");
			testCount++;
			i++;
		} catch (ParserConfigurationException e) {
			System.out.println("Parser Configuration Exception");
			testCount++;
			i++;
		} catch (SAXException e) {
			System.out.println("SAX Exception");
			testCount++;
			i++;
		}
	}
}