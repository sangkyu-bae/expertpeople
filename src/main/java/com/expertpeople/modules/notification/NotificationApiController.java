package com.expertpeople.modules.notification;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.CurrentAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NotificationApiController {

    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
//    private final SimpMessageSendingOperations simpMessageSendingOperations;
//
//    @MessageMapping("/notify/message")
//    public void checkNotify(@CurrentAccount Account account){
//        long count =notificationRepository.countByAccountAndChecked(account,false);
//
//    }

    @GetMapping(value = "/notify/{id}",produces = "text/event-stream")
    public SseEmitter notify(@PathVariable Long id,
                             @RequestHeader(value = "Last-Event-ID",required = false,defaultValue = "")String lastEventId){
        return notificationService.subscribe(id,lastEventId);
    }
}
