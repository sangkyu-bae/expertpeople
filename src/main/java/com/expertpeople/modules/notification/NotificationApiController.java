package com.expertpeople.modules.notification;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.CurrentAccount;
import com.expertpeople.modules.notification.emitter.EmitterRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NotificationApiController {

    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    @GetMapping(value = "/notify",produces = "text/event-stream")
    public SseEmitter notify(@RequestParam(value="jwt") String jwt,
                             @RequestHeader(value = "Last-Event-ID",required = false,defaultValue = "")String lastEventId){
        Account account=notificationService.getAccountId(jwt);
        return notificationService.subscribe(account,lastEventId);
    }

    @GetMapping("/notification")
    public ResponseEntity<?> getNotification(@CurrentAccount Account account){
        List<Notification> notifications=notificationRepository.findByAccountOrderByCreateDateTimeDesc(account);
        List<Notification> newNotifications=new ArrayList<>();
        List<Notification> oldNotifications=new ArrayList<>();

        if(!notifications.isEmpty()){
          notifications.forEach(notification -> {
              if(!notification.isChecked()) newNotifications.add(notification);
              if(notification.isChecked()) oldNotifications.add(notification);
          });
        }
        Long newCount=newNotifications.stream().count();
        Long oldCount=oldNotifications.stream().count();

        return ResponseEntity.ok().body(new ResponseNotify<>(newNotifications,oldNotifications,newCount,oldCount));
    }

    @Getter
    @Setter
    static class ResponseNotify<T>{
        private T newNotifications;
        private T oldNotifications;
        private T newCount;
        private T oldCount;

        public ResponseNotify(T newNotifications,T oldNotifications,T newCount,T oldCount){
            this.newNotifications=newNotifications;
            this.oldNotifications=oldNotifications;
            this.oldCount=oldCount;
            this.newCount=newCount;
        }
    }
}
