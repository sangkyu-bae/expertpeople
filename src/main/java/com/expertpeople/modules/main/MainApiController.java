package com.expertpeople.modules.main;

import com.expertpeople.modules.work.Work;
import com.expertpeople.modules.work.WorkRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainApiController {

    private final WorkRepository workRepository;
    @GetMapping("/search/work")
    public ResponseEntity<?> searchWork(String keyword){
        List<Work> works=workRepository.findByKeyword(keyword);
        return ResponseEntity.ok().body(new ResponseSearchWork<>(works,keyword));
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
