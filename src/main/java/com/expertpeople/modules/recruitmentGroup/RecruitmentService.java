package com.expertpeople.modules.recruitmentGroup;

import com.expertpeople.modules.enrollment.Enrollment;
import com.expertpeople.modules.enrollment.EnrollmentRepository;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.recruitmentGroup.Event.EnrollmentAcceptedEvent;
import com.expertpeople.modules.recruitmentGroup.Event.EnrollmentRejectEvent;
import com.expertpeople.modules.recruitmentGroup.Event.RecruitmentCreatedEvent;
import com.expertpeople.modules.recruitmentGroup.Event.RecruitmentUpdateEvent;
import com.expertpeople.modules.recruitmentGroup.Vo.RecruitmentVo;
import com.expertpeople.modules.recruitmentGroup.form.RecruitForm;
import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.recruitmentGroup.form.RecruitUpdateForm;
import com.expertpeople.modules.work.Work;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final ModelMapper modelMapper;
    private final EnrollmentRepository enrollmentRepository;
    private final ApplicationEventPublisher eventPublisher;

    public Recruitment createRecruitment(Account account, Work work, RecruitForm recruitForm, Job job) {
        Recruitment recruitment=modelMapper.map(recruitForm,Recruitment.class);

        recruitment.setJob(job);
        recruitment.setWork(work);
        recruitment.setCreateTime(LocalDateTime.now());
        recruitment.setEventType(recruitForm.getEventType());
        recruitment.setCreateBy(account);
        recruitmentRepository.save(recruitment);
        eventPublisher.publishEvent(new RecruitmentCreatedEvent(recruitment));
        return recruitment;
    }

    public Recruitment addEnrollment(Account account, Recruitment recruitment) {
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

    public RecruitmentVo convertRecruit(Recruitment recruitment) {
        RecruitmentVo recruitmentVo=new RecruitmentVo(recruitment);
        List<RecruitmentVo.Enrollments> enrollmentsList=recruitment.getErollments().stream().map(e->new RecruitmentVo.Enrollments(e)).collect(Collectors.toList());
        recruitmentVo.setErollments(enrollmentsList);
        return recruitmentVo;
    }

    public List<RecruitmentVo> convertRecruitList(List<Recruitment> recruitments) {
        List<RecruitmentVo> recruitmentVos=new ArrayList<>();
        for(Recruitment recruitment:recruitments){
            RecruitmentVo recruitmentVo=this.convertRecruit(recruitment);
            recruitmentVos.add(recruitmentVo);
        }

        return recruitmentVos;
    }

    public Recruitment getUpdateRecruit(Long id, Account account) {
        Recruitment recruitment=recruitmentRepository.findByCreateByAndId(account,id);
        if(recruitment==null){
            throw new IllegalArgumentException("존재하지 않은 경로의 일감이거나, 일감 관리자가 아닙니다.");
        }
        return recruitment;
    }

    public void updateRecruit(RecruitUpdateForm recruitUpdateForm,Recruitment recruitment) {
        recruitment.setEventType(recruitUpdateForm.getEventType());
        modelMapper.map(recruitUpdateForm,recruitment);
        recruitment.acceptEnrollmentList();
        eventPublisher.publishEvent(new RecruitmentUpdateEvent(recruitment,"구인이 내용이 변경 되었습니다."));
    }

    public void removeRecruitment(Recruitment recruitment) {
        recruitmentRepository.delete(recruitment);
        eventPublisher.publishEvent(new RecruitmentUpdateEvent(recruitment,"구인을 취소 하였습니다."));
    }

    public void acceptEnrollment(Recruitment recruitment, Enrollment enrollment) {
       // Enrollment enrollment=enrollmentRepository.findById(enrollmentId).orElseThrow(()->new IllegalArgumentException("존재하지 않은 근로자 입니다."));
        recruitment.acceptEnrollment(enrollment);
        eventPublisher.publishEvent(new EnrollmentAcceptedEvent(enrollment));
    }

    public void rejectEnrollment(Recruitment recruitment,Enrollment enrollment) {
        //Enrollment enrollment=enrollmentRepository.findById(enrollmentId).orElseThrow(()->new IllegalArgumentException("존재하지 않은 근로자 입니다."));
        recruitment.rejectEnrollment(enrollment);
        eventPublisher.publishEvent(new EnrollmentRejectEvent(enrollment));
    }

    public void attendAcceptEnrollment(Recruitment recruitment,Enrollment enrollment) {
        recruitment.acceptAttend(enrollment);
    }

    public void cancelAttend(Recruitment recruitment, Enrollment enrollment) {
        recruitment.cancelAttend(enrollment);
    }
}
