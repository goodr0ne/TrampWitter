package goodr0ne.trampwitter;

import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Random;

class TrampWitterCrawler {
    private static boolean isVerbose = false;

    static void setIsVerbose(boolean inIsVerbose) {
        isVerbose = inIsVerbose;
    }

    static boolean getIsVerbose() {
        return isVerbose;
    }

    /**
     * Pumping all http data from Trump Tweeter and store new entries to db
     */
    static void crawlTweet() {
        String jsonData = "Trump tweets for e1337s";
        try {
            Document doc = Jsoup.connect("https://twitter.com/realdonaldtrump").get();
            if (isVerbose) {
                System.out.println(doc.title());
            }
            Elements tweets = doc.getElementsByClass("TweetTextSize");
            TrampWeet[] trampWeets = new TrampWeet[tweets.size()];
            long timestamp = System.currentTimeMillis();
            for (int i = 0; i < tweets.size(); i++) {
                String body = tweets.get(i).text();
                JsonObject tweetObj = new JsonObject();
                tweetObj.addProperty("id", new Random().nextLong());
                tweetObj.addProperty("timestamp", timestamp - i);
                tweetObj.addProperty("body", body);
                trampWeets[i] = new TrampWeet(tweetObj);
                if (isVerbose) {
                    System.out.println(trampWeets[i].getAsJson().toString());
                }
                TrampWeetRepoConnector.getInstance().insertTweet(trampWeets[i]);
            }
        }
        catch (Exception e) {
            System.out.println("error occurred with twitter crawler");
        }
        if (isVerbose) {
            System.out.println(jsonData);
        }
    }
}
