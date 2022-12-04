package com.expertpeople.modules.recruitmentGroup;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.work.Work;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RecruitmentRepository extends JpaRepository<Recruitment,Long> {
    @EntityGraph(attributePaths = {"work","createBy","erollments","job"})
    Optional<Recruitment> findRecruitmentWithWorkById(Long id);
    @EntityGraph(attributePaths = "erollments")
    List<Recruitment> findByWorkOrderByStartDateTime(Work work);
    Recruitment findByCreateByAndId(Account account, Long id);

}
