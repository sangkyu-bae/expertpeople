package com.expertpeople.modules.job.Vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobResult<T> {
    private T job;
    private T allJobs;

    public JobResult(T job,T allJobs){
        this.job=job;
        this.allJobs=allJobs;
    }

}
