package org.john;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.john.course.CourseModule;
import org.john.course.CourseRefreshCronJob;
import org.john.course.CourseScraper;
import org.john.discord.DiscordBot;
import org.john.discord.DiscordModule;
import org.quartz.JobBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class Main {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        Injector injector = Guice.createInjector(
                new CourseModule(),
                new DiscordModule()
        );

        setupCourseScraperCronJob(injector);
        injector.getInstance(DiscordBot.class).setup(args[0]);
    }

    private static void setupCourseScraperCronJob(Injector injector) throws SchedulerException {
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