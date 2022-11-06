package com.expertpeople.modules.notification;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.CurrentAccount;
import com.expertpeople.modules.notification.emitter.EmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NotificationApiController {

    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    private final EmitterRepository emitterRepository;
//    private final SimpMessageSendingOperations simpMessageSendingOperations;
//
//    @MessageMapping("/notify/message")
//    public void checkNotify(@CurrentAccount Account account){
//        long count =notificationRepository.countByAccountAndChecked(account,false);
//
//    }
    public List<SseEmitter> emitters=new CopyOnWriteArrayList<>();
    @GetMapping(value = "/notify/{id}",produces = "text/event-stream")
    public SseEmitter notify(@PathVariable Long id,
                             @RequestHeader(value = "Last-Event-ID",required = false,defaultValue = "")String lastEventId){
//      SseEmitter sseEmitter=new SseEmitter(Long.MAX_VALUE);
//      try{
//          sseEmitter.send(SseEmitter.event().name("InIT"));
//      }catch (IOException e){
//          e.printStackTrace();
//      }
//      sseEmitter.onCompletion(()->emitters.remove(sseEmitter));
//      emitters.add(sseEmitter);
//      return sseEmitter;

        return notificationService.subscribe(id,lastEventId);
    }

    @PostMapping("/testnoti")
    public void dispatch(){
        Map<String,SseEmitter>emitters=emitterRepository.findAll();
        System.out.println(emitters);
        for(String key:emitters.keySet()){
          SseEmitter sseEmitter=emitters.get(key);
            try {
                sseEmitter.send(SseEmitter.event()
                        .name("test")
                        .data("dataTest"));
            } catch (IOException e) {
                emitterRepository.deleteById(key);
                throw new RuntimeException("연결 오류!");
            }
        }
//        for(SseEmitter emitter:emitters){
//            try{
//                emitter.send(SseEmitter.event().name("lastTest").data("tete"));
//            }catch (IOException e){
//                emitters.remove(emitter);
//                e.printStackTrace();
//            }
//        }

    }
}
