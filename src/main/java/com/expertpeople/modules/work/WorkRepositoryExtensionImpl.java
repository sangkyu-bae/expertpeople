package com.expertpeople.modules.work;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.QAccount;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.job.QJob;
import com.expertpeople.modules.zone.QZone;
import com.expertpeople.modules.zone.Zone;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Set;

public class WorkRepositoryExtensionImpl extends QuerydslRepositorySupport implements WorkRepositoryExtension{
    public WorkRepositoryExtensionImpl() {
        super(Work.class);
    }

//    @Override
//    public Page<Work> findByKeyword(String keyword, Pageable pageable) {
//        QWork qWork=QWork.work;
//        JPQLQuery<Work>query= from(qWork).where(qWork.published.isTrue()
//                .and(qWork.title.containsIgnoreCase(keyword))
//                .or(qWork.jobs.any().job.containsIgnoreCase(keyword))
//                .or(qWork.zones.any().localNameOfCity.containsIgnoreCase(keyword)))
//                .leftJoin(qWork.jobs, QJob.job1).fetchJoin()
//                .leftJoin(qWork.zones, QZone.zone).fetchJoin()
//                .leftJoin(qWork.members, QAccount.account).fetchJoin()
//                .distinct();
//        JPQLQuery<Work> pageableQuery= getQuerydsl().applyPagination(pageable,query);
//        QueryResults<Work> workQueryResults=pageableQuery.fetchResults();
//        return new PageImpl<>(workQueryResults.getResults(),pageable, workQueryResults.getTotal());
//    }



    @Override
    public List<Work> findByKeyword(String keyword) {
        QWork qWork=QWork.work;
        JPQLQuery<Work>query= from(qWork).where(qWork.published.isTrue()
                        .and(qWork.title.containsIgnoreCase(keyword))
                        .or(qWork.jobs.any().job.containsIgnoreCase(keyword))
                        .or(qWork.zones.any().localNameOfCity.containsIgnoreCase(keyword)))
                .leftJoin(qWork.jobs, QJob.job1).fetchJoin()
                .leftJoin(qWork.zones, QZone.zone).fetchJoin()
                .leftJoin(qWork.members, QAccount.account).fetchJoin()
                .distinct();
        return query.fetch();
    }

    @Override
    public List<Work> findByManagers(Account account) {
        QWork qWork=QWork.work;
        JPQLQuery<Work>query=from(qWork).where(qWork.managers.any().in(account))
                .leftJoin(qWork.jobs,QJob.job1).fetchJoin()
                .leftJoin(qWork.zones,QZone.zone).fetchJoin()
                .leftJoin(qWork.members,QAccount.account).fetchJoin()
                .distinct().limit(6);
        return query.fetch();
    }

    @Override
    public List<Work> findByJobsAndZones(Set<Job> jobs, Set<Zone> zones) {
        QWork qWork=QWork.work;
        JPQLQuery<Work>query=from(qWork).where(qWork.jobs.any().in(jobs)
                .and(qWork.zones.any().in(zones)))
                .leftJoin(qWork.jobs,QJob.job1).fetchJoin()
                .leftJoin(qWork.zones,QZone.zone).fetchJoin()
                .leftJoin(qWork.members,QAccount.account).fetchJoin()
                .distinct()
                .limit(9);
        return query.fetch();
    }

    @Override
    public List<Work> findByMembers(Account account) {
       QWork qWork=QWork.work;
       JPQLQuery<Work>query=from(qWork).where(qWork.members.any().in(account));
       return query.fetch();
    }
}
