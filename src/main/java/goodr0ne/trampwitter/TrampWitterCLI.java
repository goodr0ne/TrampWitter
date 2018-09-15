package goodr0ne.trampwitter;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class TrampWitterCLI {
    private boolean isLaunched = false;

    @ShellMethod("Launch TrampWeet Crawler")
    public void launch() {
        if (!isLaunched) {
            isLaunched = true;
            System.out.println("TrampWeet is operational, wait for more tweets\n"
                    + "You can hide crawler output by printing verbose --disable in console");
            System.out.println(TrampWitterUtils.crawlTweet());
        } else {
            System.out.println("TrampWeet is already launched");
        }
    }

    @ShellMethod("Turn TrampWeet crawler output on (without args) or off (--disable)")
    public String verbose(boolean disable) {
        String output;
        TrampWitterUtils.setIsVerbose(!disable);
        if (!disable) {
            output = "Crawler output enabled, enjoy full version!";
        } else {
            output = "Crawler output disabled, yeah, it's annoying...";
        }
        System.out.println("Verbose status - " + TrampWitterUtils.getIsVerbose());
        return output;
    }

    @ShellMethod("Get 10 most recent crawled Tramp tweets")
    public String trampweet() {
        return "BUILD THE WALL!!";
    }

    @ShellMethod("Clean all stored TrampWeets")
    public String clean() {
        return "cowfeve";
    }
}
