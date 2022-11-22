package com.expertpeople.modules.work;

import com.expertpeople.modules.account.QAccount;
import com.expertpeople.modules.job.QJob;
import com.expertpeople.modules.zone.QZone;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class WorkRepositoryExtensionImpl extends QuerydslRepositorySupport implements WorkRepositoryExtension{
    public WorkRepositoryExtensionImpl() {
        super(Work.class);
    }

    @Override
    public Page<Work> findByKeyword(String keyword, Pageable pageable) {
        QWork qWork=QWork.work;
        JPQLQuery<Work>query= from(qWork).where(qWork.published.isTrue()
                .and(qWork.title.containsIgnoreCase(keyword))
                .or(qWork.jobs.any().job.containsIgnoreCase(keyword))
                .or(qWork.zones.any().localNameOfCity.containsIgnoreCase(keyword)))
                .leftJoin(qWork.jobs, QJob.job1).fetchJoin()
                .leftJoin(qWork.zones, QZone.zone).fetchJoin()
                .leftJoin(qWork.members, QAccount.account).fetchJoin()
                .distinct();
        JPQLQuery<Work> pageableQuery= getQuerydsl().applyPagination(pageable,query);
        QueryResults<Work> workQueryResults=pageableQuery.fetchResults();
        return new PageImpl<>(workQueryResults.getResults(),pageable, workQueryResults.getTotal());
    }
}
