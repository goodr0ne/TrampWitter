package goodr0ne.trampwitter;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class TrampWitterCLI {

    @ShellMethod("Launch TrampWeet Crawler")
    public String launch() {
        return "TrampWeet is operational, wait for more tweets\n"
                + "You can hide crawler output by printing verbose --disable in console";
    }

    @ShellMethod("Turn TrampWeet crawler output on/off")
    public String verbose(boolean disable) {
        String output;
        if (!disable) {
            output = "Crawler output enabled, enjoy full version!";
        } else {
            output = "Crawler output disabled, yeah, it's annoying...";
        }
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
