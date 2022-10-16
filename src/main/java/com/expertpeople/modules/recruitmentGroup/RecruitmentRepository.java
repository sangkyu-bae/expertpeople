package com.expertpeople.modules.recruitmentGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RecruitmentRepository extends JpaRepository<Recruitment,Long> {
}
