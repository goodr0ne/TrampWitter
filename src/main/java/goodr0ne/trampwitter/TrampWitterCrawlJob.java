package goodr0ne.trampwitter;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class TrampWitterCrawlJob implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        TrampWitterCrawler.crawlTweet();
    }
}