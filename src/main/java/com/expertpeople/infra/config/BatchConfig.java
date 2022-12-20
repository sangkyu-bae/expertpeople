package com.expertpeople.infra.config;

import com.expertpeople.modules.notification.Notification;
import com.expertpeople.modules.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "BatchConfig")
public class BatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final NotificationRepository notificationRepository;
    private static int cnt=0;

    @Bean
    public Job oldNotificationRemove()throws Exception{
        return jobBuilderFactory.get("oldNotificationRemove")
                .start(removeNotificationStep()).build();
    }
    @Bean
    @JobScope
    public Step removeNotificationStep()throws Exception{
        return stepBuilderFactory.get("removeNotificationStep")
                .<Notification,Notification>chunk(100)
                .reader(oldNotificationReader())
                .writer(myWriter())
                .build();
    }
    @Bean
    @StepScope
    public ListItemReader<Notification> oldNotificationReader(){
        LocalDateTime monthBeforeDay=LocalDateTime.now().minusMonths(1);
        List<Notification> oldNotifications=notificationRepository.findByCheckedAndCreateDateTimeBefore(true,monthBeforeDay);
//        List<Notification> oldNotifications=notificationRepository.findAll();
//        if(cnt>1){
//            log.info(String.valueOf(cnt));
//        }
//        cnt++;

        return new ListItemReader<>(oldNotifications);
    }
    @Bean
    @StepScope
    public ItemWriter<Notification> myWriter() {
        return ((List<? extends Notification> notifications) ->
                notificationRepository.deleteAll(notifications));
//        return ((List<? extends Notification> notifications) ->
//                notificationRepository.saveAll(notifications));
    }
}
