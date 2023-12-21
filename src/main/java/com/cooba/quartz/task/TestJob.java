package com.cooba.quartz.task;

import com.cooba.quartz.service.HelloService;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class TestJob extends QuartzJobBean {
    @Autowired
    HelloService helloService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        helloService.sayHello();
        System.out.println(context.getJobDetail().getDescription() + " Job exec!");
    }
}
