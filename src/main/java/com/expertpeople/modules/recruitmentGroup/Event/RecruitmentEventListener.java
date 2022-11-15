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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class RecruitmentEventListener {

    private final EmitterRepository emitterRepository;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final AppProperties appProperties;
    private final TemplateEngine templateEngine;

    @EventListener
    public void handleRecruitmentCreatedEvent(RecruitmentCreatedEvent recruitmentCreatedEvent){
        Recruitment recruitment=recruitmentCreatedEvent.getRecruitment();
        Work work=recruitment.getWork();
        Set<Account> accounts=new HashSet<>();
        accounts.addAll(work.getMembers());
        accounts.addAll(work.getMembers());
        String link="/work/"+ work.getPath()+"/"+recruitment.getId();
        accounts.forEach(account -> {
            if(account.isWorkCreateByEmail()){
                sendWorkUpdateNotification(work,account,"새로운 구인이 시작 되었습니다",
                        "Expert PeoPle '"+work.getTitle()+"' 새로운 구인이 시작 되었습니다",link);
            }

            if(account.isWorkCreateByWeb()){
                saveWorkCreatedNotification(work, account,recruitment.getTitle(), NotificationType.RECRUIT_ENROLLMENT,recruitment.getId());

                ResponseEmitter emitter =emitterRepository.findByAccountId(account.getId());
                notificationService.sendToNewNotification(emitter.getSseEmitter(),emitter.getId(),"일감의 새로운 구인이 시작되었습니다.");
            }
        });
    }
    @EventListener
    public void handleRecruitmentUpdateEvent(RecruitmentUpdateEvent recruitmentUpdateEvent){
        Recruitment recruitment=recruitmentUpdateEvent.getRecruitment();
        Work work=recruitment.getWork();
        List<Enrollment> enrollments=recruitment.getErollments();
        String link="/work/"+ work.getPath()+"/"+recruitment.getId();
        enrollments.forEach(enrollment -> {
            if(enrollment.getAccount().isWorkCreateByEmail()){
                sendWorkUpdateNotification(work,enrollment.getAccount(),"구인 내용이 변경 되었습니다",
                        "Expert PeoPle '"+work.getTitle()+" "+recruitment.getTitle()+"' 구인 내용이 변경 되었습니다.",link);
            }

            if(enrollment.getAccount().isWorkCreateByWeb()){
                saveWorkCreatedNotification(work, enrollment.getAccount(), recruitmentUpdateEvent.getMessage(), NotificationType.RECRUIT_ENROLLMENT,recruitment.getId());

                ResponseEmitter emitter =emitterRepository.findByAccountId(enrollment.getAccount().getId());
                notificationService.sendToNewNotification(emitter.getSseEmitter(),emitter.getId(),"구인 내용이 변경되었습니다.");
            }
        });
    }
    private void saveWorkCreatedNotification(Work work, Account account,String message,NotificationType notificationType,Long recruitmentId) {
        Notification notification=Notification.builder().
                title(work.getTitle()).
                link("/work/"+ work.getPath()+"/"+recruitmentId).
                checked(false).
                createDateTime(LocalDateTime.now()).
                message(message).
                account(account).
                notificationType(notificationType).
                build();
        notificationRepository.save(notification);
    }
    private void sendWorkUpdateNotification(Work work,Account account,String contextMessage,String contextSubject,String link){
        Context context=new Context();
        context.setVariable("name",account.getName());
        context.setVariable("link",link);
        context.setVariable("message",contextMessage);
        context.setVariable("host",appProperties.getHost());

        String message=templateEngine.process("Email/simple-link",context);
        EmailMessage emailMessage = EmailMessage.builder().
                subject(contextSubject).
                message(message).
                to(account.getEmail()).
                build();
        emailService.sendEmail(emailMessage);
    }
}
