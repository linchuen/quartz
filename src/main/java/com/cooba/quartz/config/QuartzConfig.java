package com.cooba.quartz.config;

import com.cooba.quartz.task.TestJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer(JobDetail[] jobDetails, Trigger[] triggers) {
        return schedulerFactoryBean -> {
            schedulerFactoryBean.setConfigLocation(new ClassPathResource("quartz.properties"));
            schedulerFactoryBean.setOverwriteExistingJobs(true);
            schedulerFactoryBean.setAutoStartup(true);
            schedulerFactoryBean.setJobDetails(jobDetails);
            schedulerFactoryBean.setTriggers(triggers);
        };
    }

    @Bean
    public JobDetail task1() {
       return JobBuilder.newJob(TestJob.class)
                .withIdentity("testTask1")
                .withDescription("testTask1")
                .storeDurably()
                .build();
    }

    @Bean
    public JobDetail task2() {
        return JobBuilder.newJob(TestJob.class)
                .withIdentity("testTask2")
                .withDescription("testTask2")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger testQuartzTrigger1() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(task1())
                .withIdentity("trigger1")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public Trigger testQuartzTrigger2() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(task2())
                .withIdentity("trigger2")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
