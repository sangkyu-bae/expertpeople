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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.Map;

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
                saveWorkCreatedNotification(work, account);

                ResponseEmitter emitter =emitterRepository.findByAccountId(account.getId());
                notificationService.sendToNewNotification(emitter.getSseEmitter(),emitter.getId(),"관심 일감 생성 알림");
            }
        });
    }

    private void saveWorkCreatedNotification(Work work, Account account) {
        Notification notification=Notification.builder().
                title(work.getTitle()).
                link("/work/"+ work.getPath()).
                checked(false).
                createDateTime(LocalDateTime.now()).
                message(work.getShortDescription()).
                account(account).
                notificationType(NotificationType.WORK_CREATED).
                build();
        notificationRepository.save(notification);
    }
}
