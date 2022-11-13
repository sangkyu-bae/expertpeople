package com.expertpeople.modules.recruitmentGroup.Event;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.enrollment.Enrollment;
import com.expertpeople.modules.notification.Notification;
import com.expertpeople.modules.notification.NotificationRepository;
import com.expertpeople.modules.notification.NotificationService;
import com.expertpeople.modules.notification.NotificationType;
import com.expertpeople.modules.notification.emitter.EmitterRepository;
import com.expertpeople.modules.notification.emitter.ResponseEmitter;
import com.expertpeople.modules.recruitmentGroup.Recruitment;
import com.expertpeople.modules.work.Work;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class EnrollmentEventListener {
    private final EmitterRepository emitterRepository;

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @EventListener
    public void handleEnrollmentEvent(EnrollmentEvent enrollmentEvent){
        Enrollment enrollment=enrollmentEvent.getEnrollment();
        Account account=enrollment.getAccount();
        Recruitment recruitment=enrollment.getRecruitment();
        Work work=recruitment.getWork();

        if(account.isWorkCreateByEmail()){

        }
        if(account.isWorkCreateByWeb()){
            createNotification(enrollmentEvent,account,recruitment,work);
            ResponseEmitter emitter =emitterRepository.findByAccountId(account.getId());
            //notificationService.sendToNewNotification(emitter.getSseEmitter(),emitter.getId(),"구인 참가에 변경사항이 있습니다. ");
        }
    }

    private void createNotification(EnrollmentEvent enrollmentEvent, Account account, Recruitment recruitment, Work work) {
        Notification notification= Notification.builder()
                .title(work.getTitle()+" / "+recruitment.getTitle())
                .link("/work/"+work.getPath()+"/recruitment/"+recruitment.getId())
                .checked(false)
                .createDateTime(LocalDateTime.now())
                .message(enrollmentEvent.getMessage())
                .account(account)
                .notificationType(NotificationType.RECRUIT_ENROLLMENT)
                .build();
        notificationRepository.save(notification);
    }
}
