<h1>TwitterBotテンプレート_Java版</h1>

<h2>仕様</h2></br>
自動ツイート</br>
お気に入りのRSSをつぶやきます。</br>

<h2>自動フォロー</h2></br>
自動でフォローします。</br>
やみくもにフォローするわけでなくロジックにしたがって1日30人ほどにフォローします。</br>
ITとバナナに興味を持っているユーザーのみフォローします。</br>

<h2>自動リツイート</h2></br>
リツイートも同じ仕様ですね。IT関連のつぶやきをリツイートします。</br>
こちらは１時間に２回上記の処理を行うようにしています。</br>

<h2>自動コメント返し（今は廃止中）</h2></br>
　自動でコメント返します。このコメント返しは二種類実装しました。設定した文字列をランダムにコメントとして返します。</br>
AIがコメントを受け取り、理解し、コメントを返します。（ちなみにAIはリクルートさんの「A3RT」を使わせていただきました。）</br>

<h2>利用ライブラリ</h2></br>
・Twitter4J 、cron4j</br>
