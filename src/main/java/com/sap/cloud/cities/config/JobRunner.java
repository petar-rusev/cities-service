package com.sap.cloud.cities.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobRunner implements CommandLineRunner {
    private final JobLauncher jobLauncher;
    private final Job jobA;

    public JobRunner(JobLauncher jobLauncher, Job jobA) {
        this.jobLauncher = jobLauncher;
        this.jobA = jobA;
    }

    public void run(String... args) throws Exception {
        JobParameters jobParameters = (new JobParametersBuilder()).addLong("time", System.currentTimeMillis()).toJobParameters();
        this.jobLauncher.run(this.jobA, jobParameters);
        System.out.println("JOB Execution completed!");
    }
}
