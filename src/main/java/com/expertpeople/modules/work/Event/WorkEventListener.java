package com.expertpeople.modules.work.Event;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.AccountPredicates;
import com.expertpeople.modules.account.AccountRepository;
import com.expertpeople.modules.notification.Notification;
import com.expertpeople.modules.notification.NotificationRepository;
import com.expertpeople.modules.notification.NotificationType;
import com.expertpeople.modules.work.Work;
import com.expertpeople.modules.work.WorkRepository;
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
public class WorkEventListener {

    private final WorkRepository workRepository;
    private final AccountRepository accountRepository;

    private final NotificationRepository notificationRepository;
    @EventListener
    public void handleWorkCreatedEvent(WorkCreatedEvent workCreatedEvent){

        Work work=workRepository.findWorkWithJobAndZoneById(workCreatedEvent.getWork().getId());
        Iterable<Account> accounts =accountRepository.findAll(AccountPredicates.findByTagsAndZones(work.getJobs(),work.getZones()));
        accounts.forEach(account -> {
            if(account.isWorkCreateByEmail()){

            }

            if(account.isWorkCreateByWeb()){
                saveWorkCreatedNotification(work, account);
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
