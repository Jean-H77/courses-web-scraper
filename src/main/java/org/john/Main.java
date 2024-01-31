package org.john;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.john.course.CourseScraper;
import org.john.course.CourseRefreshCronJob;
import org.quartz.JobBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class Main {

    public static void main(String[] args) throws SchedulerException {
        Injector injector = Guice.createInjector(
                new MainModule()
        );

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        CourseRefreshCronJob courseRefreshScheduler = injector.getInstance(CourseRefreshCronJob.class);

        scheduler.getContext().put("courseScraper", injector.getInstance(CourseScraper.class));

        scheduler.scheduleJob(
                JobBuilder.newJob(CourseRefreshCronJob.class)
                        .build(),
                courseRefreshScheduler.getTrigger());

        scheduler.start();
    }
}