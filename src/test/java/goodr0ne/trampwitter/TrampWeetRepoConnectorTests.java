package goodr0ne.trampwitter;

import com.google.gson.JsonObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TrampWeetRepoConnectorTests {
    private static TrampWeetRepoConnector repo = TrampWeetRepoConnector.getInstance();

    @BeforeClass
    public static void initialize() {
        repo.deleteTable();
        repo.recount();
    }

    @After
    public void tearDown() {
        repo.flushTable();
    }

    @Test
    public void testSuccessTweetInsertion() {
        Assert.assertEquals(0, repo.recount());
        JsonObject tweetObj = new JsonObject();
        String body = "Great Always";
        tweetObj.addProperty("id", 1338);
        tweetObj.addProperty("timestamp", 6331);
        tweetObj.addProperty("body", body);
        TrampWeet tweet = new TrampWeet(tweetObj);
        repo.insertTweet(tweet);
        Assert.assertEquals(1, repo.recount());
        TrampWeet stored = repo.getAllTweets()[0];
        Assert.assertEquals(body, stored.getAsJson().get("body").getAsString());
    }

    @Test
    public void testTweetsUniqueStoredOnly() {
        Assert.assertEquals(0, repo.recount());
        JsonObject tweetObj = new JsonObject();
        String body = "Great Always";
        tweetObj.addProperty("id", 1338);
        tweetObj.addProperty("timestamp", 6331);
        tweetObj.addProperty("body", body);
        TrampWeet tweet = new TrampWeet(tweetObj);
        repo.insertTweet(tweet);
        Assert.assertEquals(1, repo.recount());
        repo.insertTweet(tweet);
        Assert.assertEquals(1, repo.recount());
    }

    @Test
    public void testTweetWithAdditionalJsonData() {
        Assert.assertEquals(0, repo.recount());
        JsonObject tweetObj = new JsonObject();
        String body = "Great Always";
        String hacking = "some_dangerous_stuff";
        tweetObj.addProperty("id", 1338);
        tweetObj.addProperty("timestamp", 6331);
        tweetObj.addProperty("body", body);
        tweetObj.addProperty("hacking", hacking);
        TrampWeet tweet = new TrampWeet(tweetObj);
        repo.insertTweet(tweet);
        Assert.assertEquals(1, repo.recount());
        TrampWeet stored = repo.getAllTweets()[0];
        boolean condition = false;
        try {
            String storedHacking = stored.getAsJson().get("hacking").getAsString();
            if (storedHacking.equals(hacking)) {
                System.out.println("ACTUALLY HACKING CODE WAS INSERTED, " +
                        "CODE RED ALERT! CODE RED!");
            }
        } catch (Exception e) {
            //usually means there is no such property in JsonObject
            condition = true;
        }
        Assert.assertTrue(condition);
    }

    @Test
    public void testTweetWithStringId() {
        Assert.assertEquals(0, repo.recount());
        JsonObject tweetObj = new JsonObject();
        String body = "Great Always";
        String id = "NotImmigrant";
        tweetObj.addProperty("id", id);
        tweetObj.addProperty("timestamp", 6331);
        tweetObj.addProperty("body", body);
        TrampWeet tweet = new TrampWeet(tweetObj);
        repo.insertTweet(tweet);
        Assert.assertEquals(0, repo.recount());
    }

    @Test
    public void testTweetWithWrongTimestampPropertyName() {
        Assert.assertEquals(0, repo.recount());
        JsonObject tweetObj = new JsonObject();
        String body = "Great Always";
        tweetObj.addProperty("id", 1338);
        tweetObj.addProperty("time", 6331);
        tweetObj.addProperty("body", body);
        TrampWeet tweet = new TrampWeet(tweetObj);
        repo.insertTweet(tweet);
        Assert.assertEquals(0, repo.recount());
    }
}
