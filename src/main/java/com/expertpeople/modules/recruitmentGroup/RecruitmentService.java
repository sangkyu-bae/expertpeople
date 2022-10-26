package com.expertpeople.modules.recruitmentGroup;

import com.expertpeople.modules.enrollment.Enrollment;
import com.expertpeople.modules.enrollment.EnrollmentRepository;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.recruitmentGroup.Vo.RecruitmentVo;
import com.expertpeople.modules.recruitmentGroup.form.RecruitForm;
import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.recruitmentGroup.form.RecruitUpdateForm;
import com.expertpeople.modules.work.Work;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

//    public RecruitmentVo convertRecruit(Recruitment recruitment, List<Enrollment> enrollments) {
//        RecruitmentVo recruitmentVo=new RecruitmentVo(recruitment);
//        if(!enrollments.isEmpty()){
//            List<RecruitmentVo.Enrollments> enrollmentsList=enrollments.stream().map(e->new RecruitmentVo.Enrollments(e)).collect(Collectors.toList());
//            recruitmentVo.setErollments(enrollmentsList);
//        }else{
//            System.out.println("dd");
//        }
//        return recruitmentVo;
//    }
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
        modelMapper.map(recruitUpdateForm,recruitment);
    }
}
