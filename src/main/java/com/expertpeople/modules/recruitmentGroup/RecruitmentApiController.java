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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruitment")
@Transactional(readOnly = true)
public class RecruitmentApiController {

    private final JobRepository jobRepository;
    private final WorkService workService;
    private final RecruitmentService recruitmentService;
    private final ModelMapper modelMapper;
    private final RecruitmentValidator recruitmentValidator;

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

        return ResponseEntity.ok().body(recruitment);
    }

    @PostMapping("/test")
    public ResponseEntity<?>test(@CurrentAccount Account account,@PathVariable String path){
//        Work work=workService.getWorkToUpdate()
        return null;
    }


}
