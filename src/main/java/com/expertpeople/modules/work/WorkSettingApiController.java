package com.expertpeople.modules.work;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.CurrentAccount;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.job.JobRepository;
import com.expertpeople.modules.job.Vo.JobResult;
import com.expertpeople.modules.job.form.JobForm;
import com.expertpeople.modules.work.form.WorkDescriptionForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/work/setting/{path}")
public class WorkSettingApiController {

    private final WorkService workService;
    private final JobRepository jobRepository;

    @PutMapping("/description")
    public ResponseEntity<?> updateWorkInfo(@CurrentAccount Account account, @PathVariable String path,
                                            @RequestBody @Valid WorkDescriptionForm workDescriptionForm, Errors errors){
       Work work=workService.getWorkToUpdate(account,path);

        if(errors.hasErrors()){
            return ResponseEntity.badRequest().build();
        }
        workService.updateWorkDescription(workDescriptionForm,work);

        return ResponseEntity.ok().body(path);
    }

    @GetMapping("/jobs")
    public ResponseEntity<?>getJob(@CurrentAccount Account account,@PathVariable String path){
        Set<Job> jobs=workService.getJob(path,account);

        List<String> allJobs=jobRepository.findAll().stream().map(Job::toString).collect(Collectors.toList());
        List<String> job=jobs.stream().map(Job::toString).collect(Collectors.toList());

        return ResponseEntity.ok().body(new JobResult<>(job,allJobs));
    }
    @PutMapping("/jobs/add")
    public ResponseEntity<?> addJob(@CurrentAccount Account account, @PathVariable String path,
                                    @RequestBody JobForm jobForm){

      return null;
    }


}
