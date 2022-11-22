package com.expertpeople.modules.main;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.AccountRepository;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.job.JobRepository;
import com.expertpeople.modules.work.Work;
import com.expertpeople.modules.work.WorkRepository;
import com.expertpeople.modules.work.WorkService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainApiController {

    private final WorkRepository workRepository;
    private final JobRepository jobRepository;
    private final WorkService workService;
    private final AccountRepository accountRepository;
    @GetMapping("/search/work")
    public ResponseEntity<?> searchWork(Pageable pageable, String keyword){
        Page<Work> works=workRepository.findByKeyword(keyword,pageable);
        return ResponseEntity.ok().body(new ResponseSearchWork<>(works,keyword));
    }

    @GetMapping("/work/data")
    public String generateTestData(){

        for (int i=0; i<30;i++){
            String randomvalue= RandomString.make(5);
            Work work= Work.builder()
                    .title("테스트 일감"+randomvalue)
                    .path("test-"+randomvalue)
                    .shortDescription("test 일감")
                    .fullDescription("test")
                    .jobs(new HashSet<>())
                    .managers(new HashSet<>())
                    .members(new HashSet<>())
                    .build();
            work.publish();
            Work newWork=workService.create(work);
        }
        return null;
    }

    @Getter
    @Setter
    static class ResponseSearchWork<T>{
        private T works;
        private T keyword;

        public ResponseSearchWork(T works,T keyword){
            this.keyword=keyword;
            this.works=works;
        }

    }
}
