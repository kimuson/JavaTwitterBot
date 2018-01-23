
package com.example;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class AutoFollowTask implements Runnable {

	AutoFollowTask() {
	}

	@Override
	public void run() {
		// 初期化
		Twitter twitter = new TwitterFactory().getInstance();
		Query query = new Query();

		// 検索ワードをセット
		query.setQuery("プログラミング");
		// 1度のリクエストで取得するTweetの数
		query.setCount(30);

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
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
	}

}