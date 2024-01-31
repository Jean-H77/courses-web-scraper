package org.john;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.john.course.CourseLoader;
import org.john.course.CourseRefreshScheduler;
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
        CourseRefreshScheduler courseRefreshScheduler = injector.getInstance(CourseRefreshScheduler.class);

        scheduler.getContext().put("courseLoader", injector.getInstance(CourseLoader.class));

        scheduler.scheduleJob(
                JobBuilder.newJob(CourseRefreshScheduler.class)
                        .build(),
                courseRefreshScheduler.getTrigger());

        scheduler.start();
    }
}