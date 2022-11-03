package com.expertpeople.modules.work.Event;

import com.expertpeople.modules.account.AccountRepository;
import com.expertpeople.modules.work.Work;
import com.expertpeople.modules.work.WorkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class WorkEventListener {

    private final WorkRepository workRepository;
    private final AccountRepository accountRepository;
    @EventListener
    public void handleWorkCreatedEvent(WorkCreatedEvent workCreatedEvent){
        Work work=workRepository.findWorkWithJobAndZoneById(workCreatedEvent.getWork().getId());


    }
}
