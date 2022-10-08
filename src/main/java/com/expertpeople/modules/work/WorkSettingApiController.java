package com.expertpeople.modules.work;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.CurrentAccount;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.job.JobRepository;
import com.expertpeople.modules.job.Vo.JobResult;
import com.expertpeople.modules.job.form.JobForm;
import com.expertpeople.modules.work.form.WorkDescriptionForm;
import com.expertpeople.modules.zone.Vo.ZoneResult;
import com.expertpeople.modules.zone.Zone;
import com.expertpeople.modules.zone.ZoneRepository;
import com.expertpeople.modules.zone.form.ZoneForm;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final ZoneRepository zoneRepository;

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
    @PostMapping("/jobs/add")
    public ResponseEntity<?> addJob(@CurrentAccount Account account, @PathVariable String path,
                                    @RequestBody JobForm jobForm){
        Job job=jobRepository.findByJobAndCarrer(jobForm.getJobName(),jobForm.getCarrer());
      if(job==null){
          return ResponseEntity.badRequest().build();
      }
      workService.addJobs(account,job,path);

      return ResponseEntity.ok().build();
    }

    @DeleteMapping("/jobs/remove")
    public ResponseEntity<?> removeJob(@CurrentAccount Account account,@PathVariable String path,
                                       @RequestBody JobForm jobForm){
        Job job=jobRepository.findByJobAndCarrer(jobForm.getJobName(),jobForm.getCarrer());
       if(job==null){
           return ResponseEntity.badRequest().build();
       }

       workService.removeJobs(account,job,path);

       return ResponseEntity.ok().build();
    }

    @GetMapping("/zone")
    public ResponseEntity<?>getZone(@CurrentAccount Account account,@PathVariable String path){
        Set<Zone> zones=workService.getZone(account,path);

        List<String> allZones=zoneRepository.findAll().stream().map(Zone::toString).collect(Collectors.toList());
        List<String> zone=zones.stream().map(Zone::toString).collect(Collectors.toList());

        return ResponseEntity.ok().body(new ZoneResult<>(zone,allZones));
    }

    @PostMapping("/zone/add")
    public ResponseEntity<?> addZone(@CurrentAccount Account account, @PathVariable String path,
                                         @RequestBody ZoneForm zoneForm)throws JsonProcessingException{
        Zone zone=zoneRepository.findByCityAndProvince(zoneForm.getCityName(),zoneForm.getProvinceName());
        if(zone==null){
            return ResponseEntity.badRequest().build();
        }
        workService.addZone(account,zone,path);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/zone/remove")
    public ResponseEntity<?> removeZone(@CurrentAccount Account account,@PathVariable String path,
                                        @RequestBody ZoneForm zoneForm){
        Zone zone=zoneRepository.findByCityAndProvince(zoneForm.getCityName(),zoneForm.getProvinceName());
        if(zone==null){
            return ResponseEntity.badRequest().build();
        }
        workService.removeZone(account,zone,path);
        return ResponseEntity.ok().build();
    }

}
