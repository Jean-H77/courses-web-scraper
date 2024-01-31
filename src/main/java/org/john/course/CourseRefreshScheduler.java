package org.john.course;

import org.quartz.*;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class CourseRefreshScheduler implements Job {

    public final Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("CourseRefreshTrigger")
            .withSchedule(  simpleSchedule()
                            .withIntervalInMinutes(5)
                            .repeatForever()
            )
            .startNow()
            .build();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            CourseLoader courseLoader = (CourseLoader) jobExecutionContext
                    .getScheduler()
                    .getContext()
                    .get("courseLoader");

            courseLoader.loadCourses();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public Trigger getTrigger() {
        return trigger;
    }
}
