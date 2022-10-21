package com.expertpeople.modules.enrollment;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.recruitmentGroup.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    boolean existsByRecruitmentAndAccount(Recruitment recruitment, Account account);
}
