TwitterBotテンプレート_Java版

仕様
自動ツイート
お気に入りのRSSをつぶやきます。

自動フォロー
自動でフォローします。
やみくもにフォローするわけでなくロジックにしたがって1日30人ほどにフォローします。
ITとバナナに興味を持っているユーザーのみフォローします。

自動リツイート
リツイートも同じ仕様ですね。IT関連のつぶやきをリツイートします。
こちらは１時間に２回上記の処理を行うようにしています。

動コメント返し（今は廃止中）
　自動でコメント返します。このコメント返しは二種類実装しました。設定した文字列をランダムにコメントとして返します。
AIがコメントを受け取り、理解し、コメントを返します。（ちなみにAIはリクルートさんの「A3RT」を使わせていただきました。）

利用ライブラリ
・Twitter4J 、cron4j
