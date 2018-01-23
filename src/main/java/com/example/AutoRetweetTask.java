
package com.example;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class AutoRetweetTask implements Runnable {

	@Override
	public void run() {
		// 初期化
		Twitter twitter = new TwitterFactory().getInstance();
		Query query = new Query();

		// 検索ワードをセット
		query.setQuery("プログラミング");
		// 1度のリクエストで取得するTweetの数
		query.setCount(1);

		// 検索実行
		QueryResult result = null;
		try {
			result = twitter.search(query);
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		// 検索結果を見てみる
		for (Status tweet : result.getTweets()) {
			try {
				twitter.createFriendship(tweet.getUser().getId());
				twitter.retweetStatus(tweet.getId());
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}

	}

}
