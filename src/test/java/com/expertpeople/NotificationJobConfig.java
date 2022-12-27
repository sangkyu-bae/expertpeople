package com.expertpeople;

import com.expertpeople.modules.notification.Notification;
import com.expertpeople.modules.notification.NotificationRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
public class NotificationJobConfig {

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    StepBuilderFactory stepBuilderFactory;
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
