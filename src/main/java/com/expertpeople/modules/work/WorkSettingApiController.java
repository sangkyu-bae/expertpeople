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

    @PutMapping("/publish")
    public ResponseEntity<?> updateWorkPublish(@CurrentAccount Account account,@PathVariable String path){
        Work work=workService.getWorkToUpdateStatus(account,path);
        workService.publish(work);
        return ResponseEntity.ok().body("구인 현장 을 공개 하였습니다.");
    }

    @PutMapping("/close")
    public ResponseEntity<?> updateWorkClose(@CurrentAccount Account account,@PathVariable String path){
        Work work=workService.getWorkToUpdateStatus(account,path);
        workService.close(work);
        return ResponseEntity.ok().body("일감을 종료 하였습니다.");
    }

    @PutMapping("/recruit/start")
    public ResponseEntity<?> startRecruit(@CurrentAccount Account account,@PathVariable String path){
        Work work=workService.getWorkToUpdateStatus(account,path);
        if(!work.canUpdateRecruit()){
            return ResponseEntity.badRequest().body("1시간 이내 요청은 받을 수 없습니다.");
        }

        workService.startRecruit(work);
        return ResponseEntity.ok().body("구인을 시작했습니다.");
    }

    @PutMapping("/recruit/stop")
    public ResponseEntity<?> stopRecruit(@CurrentAccount Account account,@PathVariable String path){
        Work work=workService.getWorkToUpdateStatus(account,path);
        if(!work.canUpdateRecruit()){
            return ResponseEntity.badRequest().body("1시간 이내 요청은 받을 수 없습니다.");
        }

        workService.stopRecruit(work);
        return ResponseEntity.ok().body("구인을 종료합니다.");
    }
    @PutMapping("/url")
    public ResponseEntity<?> updateWorkUrl(@CurrentAccount Account account,@PathVariable String path,String newPath){
        Work work=workService.getWorkToUpdateStatus(account,path);
        if(!workService.isValidPath(newPath)){
            return ResponseEntity.badRequest().body("해당하는 구인 경로는 사용할 수 없습니다.");
        }
        workService.updateWorkUrl(work,newPath);

        return ResponseEntity.ok().body("일감 경로를 변경 하였습니다.");
    }

    @PutMapping("/title")
    public ResponseEntity<?> updateTitle(@CurrentAccount Account account,@PathVariable String path,String title){
        Work work=workService.getWorkToUpdateStatus(account,path);
        if(!workService.isValidTitle(title)){
            return ResponseEntity.badRequest().body("제목이 50자 이상입니다. 다시 입력하세요");
        }
        workService.updateWorkTitle(work,title);

        return ResponseEntity.ok().body("일감 타이틀을 변경했습니다.");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?>removeWork(@CurrentAccount Account account,@PathVariable String path){
        Work work=workService.getWorkToUpdateStatus(account,path);
        workService.removeWork(work);
        return ResponseEntity.ok().body("일감을 삭제했습니다.");
    }
}
