package com.expertpeople.modules.work;

import com.expertpeople.modules.account.QAccount;
import com.expertpeople.modules.job.QJob;
import com.expertpeople.modules.zone.QZone;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.TypedQuery;
import java.util.List;

public class WorkRepositoryExtensionImpl extends QuerydslRepositorySupport implements WorkRepositoryExtension{
    public WorkRepositoryExtensionImpl() {
        super(Work.class);
    }

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
}
