package com.expertpeople.modules.recruitmentGroup.Event;

import com.expertpeople.infra.config.AppProperties;
import com.expertpeople.infra.mail.EmailMessage;
import com.expertpeople.infra.mail.EmailService;
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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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
    private final AppProperties appProperties;
    private final TemplateEngine templateEngine;
    private final EmailService emailService;

    @EventListener
    public void handleEnrollmentEvent(EnrollmentEvent enrollmentEvent){
        Enrollment enrollment=enrollmentEvent.getEnrollment();
        Account account=enrollment.getAccount();
        Recruitment recruitment=enrollment.getRecruitment();
        Work work=recruitment.getWork();

        String link="/work/"+work.getPath()+"/recruitment/"+recruitment.getId();
        if(account.isWorkCreateByEmail()){
            sendEnrollmentEmail(link,account, enrollmentEvent.getMessage(),
                    "expert PeoPle '"+work.getTitle()+" "+recruitment.getTitle()+"'의 구인 참가에 변경사항이 있습니다." );
        }
        if(account.isWorkCreateByWeb()){
            createNotification(enrollmentEvent,account,recruitment,work);
            notificationService.sendToEvent(account,"구인 참가에 변경사항이 있습니다.");
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

    private void sendEnrollmentEmail(String link, Account account,String contextMessage, String emailSubject){
        Context context=new Context();
        context.setVariable("name",account.getName());
        context.setVariable("message",contextMessage);
        context.setVariable("host",appProperties.getHost());
        context.setVariable("link",link);

        String message=templateEngine.process("Email/simple-link",context);

        EmailMessage emailMessage= EmailMessage.builder().
                message(message).
                to(account.getEmail()).
                subject(emailSubject).
                build();
        emailService.sendEmail(emailMessage);
    }
}
