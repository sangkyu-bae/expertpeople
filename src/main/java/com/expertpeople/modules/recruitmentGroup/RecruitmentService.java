package com.expertpeople.modules.recruitmentGroup;

import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.job.JobRepository;
import com.expertpeople.modules.recruitmentGroup.form.RecruitForm;
import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.work.Work;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final ModelMapper modelMapper;

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

//    public Recruitment createRecruitment(Account account, Work work, RecruitForm recruitForm, Job job, EventType eventType) {
//        Recruitment recruitment=modelMapper.map(recruitForm,Recruitment.class);
//
//        recruitment.setJob(job);
//        recruitment.setWork(work);
//        recruitment.setCreateBy(account);
//        recruitment.setCreateTime(LocalDateTime.now());
//        recruitment.setEventType(eventType);
//
//        return recruitmentRepository.save(recruitment);
//    }

}
