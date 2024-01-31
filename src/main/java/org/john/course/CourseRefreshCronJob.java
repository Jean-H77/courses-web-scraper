package org.john.course;

import org.quartz.*;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class CourseRefreshCronJob implements Job {

    public final Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("CourseScrapeTrigger")
            .withSchedule(  simpleSchedule()
                            .withIntervalInMinutes(5)
                            .repeatForever()
            )
            .startNow()
            .build();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            CourseScraper courseLoader = (CourseScraper) jobExecutionContext
                    .getScheduler()
                    .getContext()
                    .get("courseScraper");

            courseLoader.scrape();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public Trigger getTrigger() {
        return trigger;
    }
}
