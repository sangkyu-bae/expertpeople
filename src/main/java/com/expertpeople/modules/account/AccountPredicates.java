package com.expertpeople.modules.account;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.zone.Zone;
import com.querydsl.core.types.Predicate;
import java.util.Set;


public class AccountPredicates {

    public static Predicate findByTagsAndZones(Set<Job> jobs, Set<Zone> zones){
        QAccount qAccount=QAccount.account;

        return qAccount.zone.any().in(zones).and(qAccount.jobs.any().in(jobs));
    }

}