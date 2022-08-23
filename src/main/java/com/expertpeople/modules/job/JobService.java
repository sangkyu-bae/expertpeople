package com.expertpeople.modules.job;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;

    @PostConstruct
    public void initJobData() throws IOException{
        if(jobRepository.count()==0){
            Resource resource=new ClassPathResource("job.csv");
            List<Job> jobList=  Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8).stream()
                    .map(line->{
                        String[] split=line.split(",");
                        Carrer carrer=Enum.valueOf(Carrer.class,"NORMAL");
                        if (split[2].equals(carrer.name())){
                            return Job.builder().job(split[0]).averagePrice(split[1]).carrer(Carrer.NORMAL).build();
                        }
                        return Job.builder().job(split[0]).averagePrice(split[1]).carrer(Carrer.TECH).build();
                    }).collect(Collectors.toList());
            jobRepository.saveAll(jobList);
        }
    }
}
