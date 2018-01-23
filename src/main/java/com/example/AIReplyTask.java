
package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class AIReplyTask implements Runnable {

	@Override
	public void run() {

		Twitter twitter = TwitterFactory.getSingleton();
		ResponseList<Status> TL = null;

		try {
			TL = twitter.getMentionsTimeline();
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		if (!TL.isEmpty()) {
			for (Status status : TL) {
				User user = status.getUser();
				String toRep = "@" + user.getScreenName() + " ";
				String tweet = status.getText();
				String aiTweetRep = toRep + airep(tweet);
				try {
					twitter.updateStatus(new StatusUpdate(aiTweetRep).inReplyToStatusId(status.getId()));
					twitter.createFavorite(status.getId());
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public String airep(String tweet) {
		while (true) {
			HttpURLConnection conn = null;
			try {

				if (tweet == null || tweet.isEmpty()) {
					continue;
				} else if (tweet.equals("bye")) {
					System.out.println("終了");
				}

				// 送信データ
				// 発行したAPI KEYを書く
				String data = "apikey=edz1TbMgVV2YVTDC8dnUKZycMGAZt5t2&query=" + tweet;

				// HTTP接続
				URL url = new URL("https://api.a3rt.recruit-tech.co.jp/talk/v1/smalltalk");
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setUseCaches(false);

				// POST送信
				try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
					dos.writeBytes(data);
				}

				// レスポンス受信
				int rescode = conn.getResponseCode();
				if (rescode == HttpURLConnection.HTTP_OK) {
					try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
						StringBuilder buf = new StringBuilder();
						String airep;
						String aiTweetrep;

						while ((airep = reader.readLine()) != null) {
							buf.append(airep);
						}
						aiTweetrep = convertToOiginal(buf.toString());
						return aiTweetrep;
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (conn != null) {
					conn.disconnect();
				}
			}
		}
	}

	/**
	 * unicode文字列を文字列に変換(u\3042 ⇒ あ)
	 *
	 * @param unicode
	 *            unicode文字列を含む文字列
	 * @return 文字列
	 */
	private String convertToOiginal(String unicode) {
		String tmp = unicode;
		while (tmp.indexOf("\\u") > 0) {
			String str = tmp.substring(tmp.indexOf("\\u"), tmp.indexOf("\\u") + 6);
			int c = Integer.parseInt(str.substring(2), 16);
			tmp = tmp.replaceFirst("\\" + str, new String(new int[] { c }, 0, 1));
		}
		return tmp;
	}
}
