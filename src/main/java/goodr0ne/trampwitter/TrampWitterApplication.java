package goodr0ne.trampwitter;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@SpringBootApplication
public class TrampWitterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrampWitterApplication.class, args);
    }

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob().ofType(TrampWitterCrawlJob.class)
                .storeDurably()
                .withIdentity("Tramp_Tweet_Crawl_Job_Detail")
                .withDescription("Invoke the invocation of Trump's fury...")
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
                .withIdentity("Tramp_Tweet_Crawl_Job_Trigger")
                .withDescription("Just tell him he is orange")
                .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(10))
                .build();
    }
}
