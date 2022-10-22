package com.expertpeople.modules.recruitmentGroup;

import com.expertpeople.modules.enrollment.Enrollment;
import com.expertpeople.modules.enrollment.EnrollmentRepository;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.recruitmentGroup.form.RecruitForm;
import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.work.Work;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final ModelMapper modelMapper;
    private final EnrollmentRepository enrollmentRepository;

    public Recruitment createRecruitment(Account account, Work work, RecruitForm recruitForm, Job job) {
        Recruitment recruitment=modelMapper.map(recruitForm,Recruitment.class);

        recruitment.setJob(job);
        recruitment.setWork(work);
        recruitment.setCreateTime(LocalDateTime.now());
        recruitment.setEventType(recruitForm.getEventType());
        recruitment.setCreateBy(account);
        recruitmentRepository.save(recruitment);

        return recruitment;
    }

    public Recruitment addEnrollment(Account account, Long id) {
        Recruitment recruitment=recruitmentRepository.findById(id).orElseThrow();
        if(!enrollmentRepository.existsByRecruitmentAndAccount(recruitment,account)){
            Enrollment enrollment=Enrollment.builder().
                    account(account).
                    enrolledAt(LocalDateTime.now()).
                    accepted(recruitment.isAbleToAccptWaitingEnrollment()).
                    build();
            recruitment.addEnrollment(enrollment);
            enrollmentRepository.save(enrollment);
        }else{
            throw new IllegalArgumentException("이미 참가신청된 사용자 입니다.");
        }

        return recruitment;
    }
}
