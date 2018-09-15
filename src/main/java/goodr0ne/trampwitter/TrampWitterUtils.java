package goodr0ne.trampwitter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TrampWitterUtils {
    private static TrampWeetRepository trampWeetRepository;
    private static boolean isVerbose = true;

    public static void setIsVerbose(boolean inIsVerbose) {
        isVerbose = inIsVerbose;
    }

    public static boolean getIsVerbose() {
        return isVerbose;
    }

    public static String crawlTweet() {
        String jsonData = "Trump tweets for e1337s";
        try {
            Document doc = Jsoup.connect("https://twitter.com/realdonaldtrump").get();
            if (isVerbose) {
                System.out.println(doc.title());
                Elements tweets = doc.getElementsByClass("TweetTextSize");
                for (int i = 0; i < tweets.size(); i++) {
                    System.out.println(tweets.get(i).text());
                }
            }
        }
        catch (Exception e) {
            System.out.println("error occured with twitter crawler");
        }
        try {
            TrampWeet trampWeet = trampWeetRepository.save(new TrampWeet("leet"));
            return trampWeetRepository.getOne(trampWeet.getId()).getJson();
        } catch (Exception e) {
            System.out.println("Error with saving tweet to H2");
        }
        return jsonData;
    }
}
