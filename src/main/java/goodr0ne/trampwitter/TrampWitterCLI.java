package goodr0ne.trampwitter;

import com.google.gson.JsonObject;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class TrampWitterCLI {
    /**
     * Turn most of outputs onn/off. Expected blank command input (verbose)
     * or with parameter disable (verbose --disable). Turned off by default.
     * @param disable exactly one or these: totally blank or --disable input expected
     * @return String will be outputted to console
     */
    @ShellMethod("Turn TrampWeet crawler output on (without args) or off (--disable)")
    public String verbose(boolean disable) {
        String output;
        TrampWitterCrawler.setIsVerbose(!disable);
        TrampWeetRepoConnector.getInstance().setVerbose(!disable);
        if (!disable) {
            output = "Crawler output enabled, enjoy full version!";
        } else {
            output = "Crawler output disabled, yeah, it's annoying...";
        }
        System.out.println("Verbose status - " + TrampWitterCrawler.getIsVerbose());
        return output;
    }

    @ShellMethod("Get 10 most recent crawled Tramp tweets")
    public String trampweet() {
        TrampWeet[] trampWeets = TrampWeetRepoConnector.getInstance().getAllTweets();
        int count = 0;
        if (trampWeets.length > 0) {
            for (TrampWeet trampWeet : trampWeets) {
                if (count < 10) {
                    JsonObject trampWeetObj = trampWeet.getAsJson();
                    System.out.println("\n" + (count + 1)
                            + ". " + trampWeetObj.get("body").getAsString());
                    count++;
                } else {
                    break;
                }
            }
        } else {
            System.out.println("There is no TrampWeets to display");
        }
        return "\nBUILD THE WALL!!\n";
    }

    @ShellMethod("Clean all stored TrampWeets")
    public String clean() {
        TrampWeetRepoConnector.getInstance().flushTable();
        return "cowfeve";
    }

    @ShellMethod("Delete database table (debug mode, exceptions may rise)")
    public String delete() {
        TrampWeetRepoConnector.getInstance().deleteTable();
        return "Make everything great again!";
    }

    @ShellMethod("Recount all stored tweets")
    public int recount() {
        return TrampWeetRepoConnector.getInstance().recount();
    }

    @ShellMethod("Manually crawl bunch of those (debug mode only)")
    public void crawl() {
        TrampWitterCrawler.crawlTweet();
    }

    @ShellMethod("Test TrampWeet insertion (debug mode only)")
    public void test() {
        TrampWeetRepoConnector.getInstance().insertTestTweet();
    }
}
