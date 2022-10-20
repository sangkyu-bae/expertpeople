package com.expertpeople.modules.recruitmentGroup;

import com.expertpeople.modules.job.form.JobForm;
import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.CurrentAccount;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.job.JobRepository;
import com.expertpeople.modules.recruitmentGroup.form.RecruitForm;
import com.expertpeople.modules.recruitmentGroup.validator.RecruitmentValidator;
import com.expertpeople.modules.work.Work;
import com.expertpeople.modules.work.WorkService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
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
    @InitBinder("recruitForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(recruitmentValidator);
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

    @GetMapping("/{path}/recruitment/{id}")
    public ResponseEntity<?> getRecruitment(@CurrentAccount Account account,@PathVariable String path,@PathVariable Long id){
        Recruitment recruitment= recruitmentRepository.findById(id).orElseThrow();
        boolean isManager=recruitment.isManager(account);
        return ResponseEntity.ok().body(new RecruitResult<>(recruitment,isManager));
    }


    static class RecruitResult<T>{
        private T recruitment;
        private T isManager;

        public RecruitResult(T recruitment,T isManager){
            this.isManager=isManager;
            this.recruitment=recruitment;
        }
    }
}
