package com.bogdan.logic;

import com.bogdan.logic.tasks.AutoBirthdayMail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;

public class ScheduleExecutor {

    public void executeSchedule() throws ParseException, SchedulerException {
        JobDetailImpl job = new JobDetailImpl();
        job.setJobClass(AutoBirthdayMail.class);
        job.setName(AutoBirthdayMail.getName() + "Job");

        CronTriggerImpl trigger = new CronTriggerImpl();
        trigger.setCronExpression("0 30 23 1/1 * ? *");
        trigger.setName(AutoBirthdayMail.getName() + "Trigger");

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }
}
