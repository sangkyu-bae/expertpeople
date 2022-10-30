package com.expertpeople.modules.recruitmentGroup;

import com.expertpeople.modules.job.form.JobForm;
import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.CurrentAccount;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.job.JobRepository;
import com.expertpeople.modules.recruitmentGroup.Vo.RecruitmentVo;
import com.expertpeople.modules.recruitmentGroup.form.RecruitForm;
import com.expertpeople.modules.recruitmentGroup.form.RecruitUpdateForm;
import com.expertpeople.modules.recruitmentGroup.validator.RecruitUpdateValidator;
import com.expertpeople.modules.recruitmentGroup.validator.RecruitmentValidator;
import com.expertpeople.modules.work.Work;
import com.expertpeople.modules.work.WorkService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruitment")
public class RecruitmentApiController {

    private final JobRepository jobRepository;
    private final WorkService workService;
    private final RecruitmentService recruitmentService;
    private final ModelMapper modelMapper;
    private final RecruitmentValidator recruitmentValidator;
    private final RecruitmentRepository recruitmentRepository;
    private final RecruitUpdateValidator recruitUpdateValidator;
    @InitBinder("recruitForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(recruitmentValidator);
    }

    @InitBinder("recruitUpdateForm")
    public void initBinderRecruitUpdate(WebDataBinder webDataBinder){
        webDataBinder.addValidators(recruitUpdateValidator);
    }
    @GetMapping("/jobs")
    public ResponseEntity<?> getJob(@CurrentAccount Account account){
        List<String> alljobs=jobRepository.findAll().stream().map(Job::toString).collect(Collectors.toList());

        return ResponseEntity.ok().body(alljobs);
    }

    @PostMapping("/{path}/add/recruitment")
    public ResponseEntity<?>addRecruitment(@CurrentAccount Account account, @PathVariable String path, @Valid @RequestBody RecruitForm recruitForm, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }
        JobForm jobForm=modelMapper.map(recruitForm,JobForm.class);
        Job job=jobRepository.findByJobAndCarrer(jobForm.getJobName(),jobForm.getCarrer());
        if(job==null){
            return ResponseEntity.badRequest().build();
        }
        Work work=workService.getWorkToUpdateStatus(account,path);
        Recruitment recruitment=recruitmentService.createRecruitment(account,work,recruitForm,job);
        return ResponseEntity.ok().body(recruitment.getId());
    }

    @GetMapping("/{path}")
    public ResponseEntity<RecruitTime> getRecruitInfo(@CurrentAccount Account account,@PathVariable String path){
        Work work=workService.getWork(path);

        List<Recruitment>recruitments=recruitmentRepository.findByWorkOrderByStartDateTime(work);

        List<RecruitmentVo>newRecruitments=new ArrayList<>();
        List<RecruitmentVo>oldRecruitmentList=new ArrayList<>();

        if(!recruitments.isEmpty()){
            List<RecruitmentVo>recruitmentVos=recruitmentService.convertRecruitList(recruitments);
            recruitmentVos.forEach(e->{
                if(e.getEndEnrollmentDateTime().isBefore(LocalDateTime.now()))oldRecruitmentList.add(e);
                else newRecruitments.add(e);
            });
        }

        return ResponseEntity.ok().body(new RecruitTime(newRecruitments,oldRecruitmentList));
    }


    @GetMapping("/{path}/recruitment/{id}")
    public ResponseEntity<?> getRecruitment(@CurrentAccount Account account,@PathVariable String path,@PathVariable Long id){
        workService.isCheckWork(path);
        Recruitment recruitment=recruitmentRepository.findById(id).orElseThrow();
        boolean isManager=recruitment.isManager(account);
        boolean isMember= recruitment.isMember(account);
        RecruitmentVo recruitmentVo=recruitmentService.convertRecruit(recruitment);

        return ResponseEntity.ok().body(new RecruitResult<>(recruitmentVo,isManager,isMember));
    }
    @PutMapping("/update/{path}/{id}")
    public ResponseEntity<?> updateRecruitment(@CurrentAccount Account account, @Valid @RequestBody RecruitUpdateForm recruitUpdateForm
            , @PathVariable String path, @PathVariable Long id,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }
        workService.isCheckWork(path);
        Recruitment recruitment=recruitmentService.getUpdateRecruit(id,account);
        recruitmentService.updateRecruit(recruitUpdateForm,recruitment);

        return null;
    }

    @PutMapping("/{path}/recruitment/{id}")
    public ResponseEntity<?> newEnrollment(@CurrentAccount Account account,@PathVariable String path,@PathVariable Long id){
        workService.isCheckWork(path);
        Recruitment recruitment= recruitmentService.addEnrollment(account,id);
        RecruitmentVo recruitmentVo=recruitmentService.convertRecruit(recruitment);

        return ResponseEntity.ok().body(recruitmentVo);
    }

    @DeleteMapping("/{path}/recruitment/{id}")
    public ResponseEntity<?> removeRecruit(@CurrentAccount Account account,@PathVariable String path,@PathVariable Long id){
        workService.isCheckWork(path);
        Recruitment recruitment=recruitmentService.getUpdateRecruit(id,account);
        recruitmentService.removeRecruitment(recruitment);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{path}/accept/recruitment/{id}/{enrollmentId}")
    public ResponseEntity<?>acceptEnrollment(@CurrentAccount Account account,@PathVariable String path,@PathVariable Long id,@PathVariable Long enrollmentId){
        workService.isCheckWork(path);
        Recruitment recruitment=recruitmentService.getUpdateRecruit(id,account);
        recruitmentService.acceptEnrollment(recruitment,enrollmentId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{path}/reject/recruitment/{id}/{enrollmentId}")
    public ResponseEntity<?>rejectEnrollment(@CurrentAccount Account account,@PathVariable String path,@PathVariable Long id, @PathVariable Long enrollmentId){
        workService.isCheckWork(path);
        Recruitment recruitment=recruitmentService.getUpdateRecruit(id,account);
        recruitmentService.rejectEnrollment(recruitment,enrollmentId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{path}/attend/enrollment/{id}/{enrollmentId}")
    public ResponseEntity<?> attendAcceptEnrollment(@CurrentAccount Account account,@PathVariable String path,@PathVariable Long id, @PathVariable Long enrollmentId){
        workService.isCheckWork(path);
        Recruitment recruitment=recruitmentService.getUpdateRecruit(id,account);
        recruitmentService.attendAcceptEnrollment(recruitment,enrollmentId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{path}/cancel/attend/{id}/{enrollmentId}")
    public ResponseEntity<?> CancelAttendEnrollment(@CurrentAccount Account account, @PathVariable String path, @PathVariable Long id, @PathVariable Long enrollmentId){
        workService.isCheckWork(path);
        Recruitment recruitment=recruitmentService.getUpdateRecruit(id,account);
        recruitmentService.cancelAttend(recruitment,enrollmentId);

        return ResponseEntity.ok().build();
    }
    @Getter
    @Setter
    static class RecruitResult<T>{
        private T recruitment;
        private T isManager;
        private T isMember;
        public RecruitResult(T recruitment,T isManager,T isMember){
            this.recruitment=recruitment;
            this.isManager=isManager;
            this.isMember=isMember;
        }
    }

    @Getter
    @Setter
    static class RecruitTime<T>{
        private T newRecruitments;
        private T oldRecruitments;

        public RecruitTime(T newRecruitments,T oldRecruitments){
            this.newRecruitments=newRecruitments;
            this.oldRecruitments=oldRecruitments;
        }
    }


}
