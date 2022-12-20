package com.expertpeople.infra.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableScheduling
@RequiredArgsConstructor
@Component

public class Scheduler {
    private final Job job;
    private final JobLauncher jobLauncher;

    @Scheduled(cron = "0 0 00 * * *")
//    @Scheduled(fixedDelay = 30000)
    public void startJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Map<String, JobParameter> jobParameterMap=new HashMap<>();

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now=simpleDateFormat.format(new Date());

        jobParameterMap.put("requestDate",new JobParameter(now));
        JobParameters jobParameters=new JobParameters(jobParameterMap);
        JobExecution jobExecution=jobLauncher.run(job,jobParameters);

        while (jobExecution.isRunning()){
            log.info("배치 실행중");
        }
    }
}
