package com.expertpeople.modules.work;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.zone.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
public interface WorkRepositoryExtension {
   //Page<Work> findByKeyword(String keyword, Pageable pageable);

    List<Work> findByKeyword(String keyword);

    List<Work> findByManagers(Account account);

    List<Work> findByJobsAndZones(Set<Job> jobs, Set<Zone> zones);

    List<Work>findByMembers(Account account);

    List<Work>findLimit6ByManagers(Account account);
}
