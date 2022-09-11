package com.expertpeople.modules.account;

import com.expertpeople.modules.account.form.PasswordForm;
import com.expertpeople.modules.account.form.Profile;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.job.JobRepository;
import com.expertpeople.modules.job.form.JobForm;
import com.expertpeople.modules.zone.Zone;
import com.expertpeople.modules.zone.ZoneRepository;
import com.expertpeople.modules.zone.ZoneService;
import com.expertpeople.modules.zone.form.ZoneForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.internal.util.Lists;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountSettingApiController {

    private final AccountService accountService;
    private final ZoneRepository zoneRepository;
    private final JobRepository jobRepository;

    @InitBinder("passwordForm")
    public void passwordBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators();
    }
    @PostMapping("/setting/profile")
    public ResponseEntity<?>updateProfile(@CurrentAccount Account account,@RequestBody @Valid Profile profile,
                                          Errors errors)throws JsonProcessingException{

        if(errors.hasErrors()){
            return ResponseEntity.badRequest().build();
        }
        accountService.updateAccountProfile(account,profile);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/setting/password")
    public ResponseEntity<?>updatePassword(@CurrentAccount Account account, @RequestBody @Valid PasswordForm passwordForm,
                                           Errors errors) throws JsonProcessingException{
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().build();
        }
        accountService.updatePassword(account,passwordForm);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/setting/zone")
    public ResponseEntity<?> getZoneTag(@CurrentAccount Account account) throws JsonProcessingException{
        Set<Zone> zones =accountService.getZone(account);
        List<String> allZone=zoneRepository.findAll().stream().map(Zone::toString).collect(Collectors.toList());

        return ResponseEntity.ok().body(new ZoneResult<>(zones,allZone));
    }


    @PostMapping("/setting/zone/add")
    public ResponseEntity<?>addZoneTag(@CurrentAccount Account account, @RequestBody ZoneForm zoneForm)throws JsonProcessingException {
        Zone zone=zoneRepository.findByCityAndProvince(zoneForm.getCityName(),zoneForm.getProvinceName());
        if(zone==null){
            return ResponseEntity.badRequest().build();
        }
        accountService.addZone(account,zone);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/setting/job")
    public ResponseEntity<?> getJobTag(@CurrentAccount Account account) throws JsonProcessingException{
        Set<Job>jobs=accountService.getJob(account);

        List<String> job=jobs.stream().map(Job::toString).collect(Collectors.toList());
        List<String> allJobs=jobRepository.findAll().stream().map(Job::toString).collect(Collectors.toList());

        return ResponseEntity.ok().body(new JobResult<>(job,allJobs));
    }

    @PostMapping("/setting/add/jobs")
    public ResponseEntity<?>addJobsTag(@CurrentAccount Account account, @RequestBody JobForm jobForm)throws JsonProcessingException{
        Job job=jobRepository.findByJobAndCarrer(jobForm.getJobName(),jobForm.getCarrer());
        if(job==null){
            return ResponseEntity.badRequest().build();
        }
        accountService.addJobs(account,job);

        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/setting/delete/jobs")
    public ResponseEntity<?>removeJobsTag(@CurrentAccount Account account,@RequestBody JobForm jobForm)throws JsonProcessingException{
        Job job=jobRepository.findByJobAndCarrer(jobForm.getJobName(),jobForm.getCarrer());
        if(job==null){
            return ResponseEntity.badRequest().build();
        }
        accountService.removeJobs(account,job);

        return ResponseEntity.ok().build();
    }



    @Getter
    @Setter
    static class ZoneResult<T>{
        private T zone;
        private T allZone;

        public ZoneResult(T zones, T allZone) {
            this.zone=zones;
            this.allZone=allZone;
        }
    }

    @Getter
    @Setter
    static class JobResult<T>{
        private T job;
        private T allJobs;

        public JobResult(T job,T allJobs){
            this.job=job;
            this.allJobs=allJobs;
        }
    }
}
