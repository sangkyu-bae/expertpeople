package com.expertpeople.modules.work.Event;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.AccountPredicates;
import com.expertpeople.modules.account.AccountRepository;
import com.expertpeople.modules.notification.Notification;
import com.expertpeople.modules.notification.NotificationRepository;
import com.expertpeople.modules.notification.NotificationService;
import com.expertpeople.modules.notification.NotificationType;
import com.expertpeople.modules.notification.emitter.EmitterRepository;
import com.expertpeople.modules.notification.emitter.ResponseEmitter;
import com.expertpeople.modules.work.Work;
import com.expertpeople.modules.work.WorkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class WorkEventListener {

    private final WorkRepository workRepository;
    private final AccountRepository accountRepository;

    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    private final EmitterRepository emitterRepository;
    @EventListener
    public void handleWorkCreatedEvent(WorkCreatedEvent workCreatedEvent){

        Work work=workRepository.findWorkWithJobAndZoneById(workCreatedEvent.getWork().getId());
        Iterable<Account> accounts =accountRepository.findAll(AccountPredicates.findByTagsAndZones(work.getJobs(),work.getZones()));
        accounts.forEach(account -> {
            if(account.isWorkCreateByEmail()){

            }

            if(account.isWorkCreateByWeb()){
                saveWorkCreatedNotification(work, account,work.getShortDescription(),NotificationType.WORK_CREATED);

                ResponseEmitter emitter =emitterRepository.findByAccountId(account.getId());
                notificationService.sendToNewNotification(emitter.getSseEmitter(),emitter.getId(),"관심 일감 생성 알림");
            }
        });
    }

    @EventListener
    public void handleWorkUpdateWork(WorkUpdateEvent workUpdateEvent){
        Work work=workRepository.findWorkWithManagersAndMembersById(workUpdateEvent.getWork().getId());
        Set<Account>accounts=new HashSet<>();
        accounts.addAll(work.getManagers());
        accounts.addAll(work.getMembers());

        accounts.forEach(account -> {
            if(account.isWorkCreateByEmail()){

            }

            if(account.isWorkCreateByWeb()){
                saveWorkCreatedNotification(work, account,workUpdateEvent.getMessage(),NotificationType.WORK_UPDATED);

                ResponseEmitter emitter =emitterRepository.findByAccountId(account.getId());
                notificationService.sendToNewNotification(emitter.getSseEmitter(),emitter.getId(),"일감에 새로운 소식이 있습니다. ");
            }
        });
    }
    private void saveWorkCreatedNotification(Work work, Account account,String message,NotificationType notificationType) {
        Notification notification=Notification.builder().
                title(work.getTitle()).
                link("/work/"+ work.getPath()).
                checked(false).
                createDateTime(LocalDateTime.now()).
                message(message).
                account(account).
                notificationType(notificationType).
                build();
        notificationRepository.save(notification);
    }
}
