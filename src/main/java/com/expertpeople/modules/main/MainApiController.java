package com.expertpeople.modules.main;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.AccountRepository;
import com.expertpeople.modules.account.AccountService;
import com.expertpeople.modules.account.CurrentAccount;
import com.expertpeople.modules.enrollment.Enrollment;
import com.expertpeople.modules.enrollment.EnrollmentRepository;
import com.expertpeople.modules.recruitmentGroup.Recruitment;
import com.expertpeople.modules.recruitmentGroup.RecruitmentRepository;
import com.expertpeople.modules.work.Vo.fetchWorkVo;
import com.expertpeople.modules.work.Vo.WorkVo;
import com.expertpeople.modules.work.Work;
import com.expertpeople.modules.work.WorkRepository;
import com.expertpeople.modules.work.WorkService;
import com.expertpeople.modules.zone.Zone;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainApiController {

    private final WorkRepository workRepository;
    private final WorkService workService;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final EnrollmentRepository enrollmentRepository;

    @GetMapping("/search/work")
    public ResponseEntity<?> searchWork(Pageable pageable, String keyword) {
        //Page<Work> works=workRepository.findByKeyword(keyword,pageable);
        List<Work> works = workRepository.findByKeyword(keyword);
        List<fetchWorkVo> fetchWork = works.stream().map(fetchWorkVo::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(new ResponseSearchWork<>(fetchWork, keyword));
    }

    @GetMapping("/main/work")
    public ResponseEntity<?>mainWork(){
        List<Work> works=workRepository.findTop9ByPublishedOrderByPublishedDateTimeAsc(true);
        List<fetchWorkVo> workVos=works.stream().map(fetchWorkVo::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(workVos);
    }

    @GetMapping("/main/my-data")
    public ResponseEntity<?>mainMyData(@CurrentAccount Account account){
        Account loadAccount=accountRepository.findAccountWithJobsAndZonesById(account.getId());

        List<Work> managerWorks=workRepository.findByManagers(loadAccount);
        List<fetchWorkVo>workVos=managerWorks.stream().map(fetchWorkVo::new).collect(Collectors.toList());

        List<Work> interestWork=workRepository.findByJobsAndZones(loadAccount.getJobs(),loadAccount.getZone());
        List<fetchWorkVo>interestWorkVo=interestWork.stream().map(fetchWorkVo::new).collect(Collectors.toList());

        List<Work> attendWork=workRepository.findByMembers(loadAccount);
        List<fetchWorkVo> attendFetchWorkVos=attendWork.stream().map(fetchWorkVo::new).collect(Collectors.toList());

        //List<Recruitment> recruitments=recruitmentRepository.findByAccountAndAcceptedOrderByEnrolledAtDesc(loadAccount,true);

        List<Enrollment> enrollments=enrollmentRepository.findByAccountAndAcceptedOrderByEnrolledAtDesc(loadAccount,true);

        ResponseMainMyData responseMainMyData=new ResponseMainMyData(loadAccount,workVos,interestWorkVo,attendFetchWorkVos,enrollments);

        return ResponseEntity.ok().body(enrollments);
    }
    @GetMapping("/work/data")
    public String generateTestData() {

        for (int i = 0; i < 30; i++) {
            String randomvalue = RandomString.make(5);
            Work work = Work.builder()
                    .title("테스트 일감" + randomvalue)
                    .path("test-" + randomvalue)
                    .shortDescription("test 일감")
                    .fullDescription("test")
                    .jobs(new HashSet<>())
                    .managers(new HashSet<>())
                    .members(new HashSet<>())
                    .build();
            work.publish();
            Work newWork = workService.create(work);
        }
        return null;
    }

    @Getter
    @Setter
    static class ResponseMainMyData{
        Account loadAccount;

        List<fetchWorkVo>workVos;

        List<fetchWorkVo>interestWorkVo;

        List<fetchWorkVo> attendFetchWorkVos;

        List<Enrollment> enrollments;

        public ResponseMainMyData(Account loadAccount,
                                  List<fetchWorkVo>workVos,
                                  List<fetchWorkVo>interestWorkVo,
                                  List<fetchWorkVo> attendFetchWorkVos,
                                  List<Enrollment> enrollments){
            this.loadAccount=loadAccount;
            this.workVos=workVos;
            this.interestWorkVo=interestWorkVo;
            this.attendFetchWorkVos=attendFetchWorkVos;
            this.enrollments=enrollments;
        }
        static class Recurit{

        }
    }

    @Getter
    @Setter
    static class ResponseSearchWork<T> {
        private T works;
        private T keyword;

        public ResponseSearchWork(T works, T keyword) {
            this.keyword = keyword;
            this.works = works;
        }

    }
}
