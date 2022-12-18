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
        List<Notification> newNotifications=notificationRepository.findByAccountAndCheckedOrderByCreateDateTimeDesc(account,false);

        Long newCount=newNotifications.stream().count();
        Long oldCount=notificationRepository.countByAccountAndChecked(account,true);

        return ResponseEntity.ok().body(new ResponseNotify<>(newNotifications,newCount,oldCount));
    }

    @GetMapping("/notification/old")
    public ResponseEntity<?> getOldNotification(@CurrentAccount Account account){
        List<Notification>oldNotifications=notificationRepository.findByAccountAndCheckedOrderByCreateDateTimeDesc(account,true);
        Long newCount=notificationRepository.countByAccountAndChecked(account,false);
        Long oldCount=oldNotifications.stream().count();

        return ResponseEntity.ok().body(new ResponseNotify<>(oldNotifications,newCount,oldCount));
    }
    @PutMapping("/notification/read/{notificationId}")
    public ResponseEntity<?> readNotification(@CurrentAccount Account account,@PathVariable("notificationId")Notification notification){
        notificationService.isNotification(notification.getId(),account);
        notificationService.readNotification(notification);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/notification/read/all")
    public ResponseEntity<?>readAllNotification(@CurrentAccount Account account){
        List<Notification> notifications=notificationRepository.findByAccountAndCheckedOrderByCreateDateTimeDesc(account,false);
        notificationService.readAllNotification(notifications);
        Long oldCount=notifications.stream().count();

        return ResponseEntity.ok().body(new ResponseNotify<>(notifications,0,oldCount));
    }

    @DeleteMapping("/notification/remove/old")
    public ResponseEntity<?>removeOldNotification(@CurrentAccount Account account){
        List<Notification> oldNotifications=notificationRepository.findByAccountAndCheckedOrderByCreateDateTimeDesc(account,true);
        if(!oldNotifications.isEmpty()){
            notificationService.remove(oldNotifications);
        }

        return ResponseEntity.ok().build();
    }

    @Getter
    @Setter
    static class ResponseNotify<T>{
        private T notifications;
        private T newCount;
        private T oldCount;

        public ResponseNotify(T newNotifications,T newCount,T oldCount){
            this.notifications=newNotifications;
            this.oldCount=oldCount;
            this.newCount=newCount;
        }
    }
//    @Getter
//    @Setter
//    static class ResponseNotify<T>{
//        private T newNotifications;
//        private T oldNotifications;
//        private T newCount;
//        private T oldCount;
//
//        public ResponseNotify(T newNotifications,T oldNotifications,T newCount,T oldCount){
//            this.newNotifications=newNotifications;
//            this.oldNotifications=oldNotifications;
//            this.oldCount=oldCount;
//            this.newCount=newCount;
//        }
//    }
}
