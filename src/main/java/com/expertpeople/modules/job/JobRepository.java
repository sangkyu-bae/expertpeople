package com.expertpeople.modules.job;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job,Long> {
    Job findByJobAndCarrer(String jobName, Enum carrer);

    Job findByJob(String job);
}
