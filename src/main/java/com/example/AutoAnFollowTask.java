package com.example;

import java.util.ArrayList;
import java.util.List;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class AutoAnFollowTask implements Runnable {

	AutoAnFollowTask() {
	}

	@Override
	public void run() {
		Twitter twitter = new TwitterFactory().getInstance();

		// 自動リフォロー
		List<Long> followersList = new ArrayList<Long>();
		List<Long> friendsList = new ArrayList<Long>();

		// フォロワーリスト取得
		long cursor = -1L;
		while (true) {
			IDs followers = null;
			try {
				followers = twitter.getFollowersIDs(cursor);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			long[] ids = followers.getIDs();
			if (0 == ids.length)
				break;
			for (int i = 0; i < ids.length; i++) {
				followersList.add(ids[i]);
			}
			cursor = followers.getNextCursor();
		}

		// フレンドリスト取得
		cursor = -1L;
		while (true) {
			IDs friends = null;
			try {
				friends = twitter.getFriendsIDs(cursor);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			long[] ids = friends.getIDs();
			if (0 == ids.length)
				break;
			for (int i = 0; i < ids.length; i++) {
				friendsList.add(ids[i]);
			}
			cursor = friends.getNextCursor();
		}

		// フォロ-リストをループし、1件ごとにリフォローされているか確認し、されていなければフレンド解除する。
		int c = 0;
		for (Long userId : friendsList) {
			if (c < 30) {
				if (!followersList.contains(userId)) {
					try {
						twitter.destroyFriendship(userId);
						c++;
					} catch (TwitterException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}