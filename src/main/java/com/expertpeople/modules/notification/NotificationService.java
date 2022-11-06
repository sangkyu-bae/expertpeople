package com.expertpeople.modules.notification;

import com.expertpeople.modules.account.AccountRepository;
import com.expertpeople.modules.notification.emitter.EmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final NotificationRepository notificationRepository;
    private final AccountRepository accountRepository;
    private final EmitterRepository emitterRepository;

    public SseEmitter subscribe(Long id, String lastEventId) {
        boolean isAccount=accountRepository.existsById(id);
        if(!isAccount){
            throw new IllegalArgumentException("존재하는 계정이 아닙니다.");
        }
        String userId=id+"_"+System.currentTimeMillis();
        SseEmitter sseEmitter=emitterRepository.save(userId,new SseEmitter(DEFAULT_TIMEOUT));

        sseEmitter.onCompletion(()->emitterRepository.deleteById(userId));
        sseEmitter.onTimeout(()->emitterRepository.deleteById(userId));

        this.sendToClient(sseEmitter, userId, " EventStream Created. [userId=" + userId + "]");

        if(!lastEventId.isEmpty()){
            Map<String, SseEmitter> events = emitterRepository.findAllEmitterStartWithByMemberId(String.valueOf(id));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(sseEmitter, entry.getKey(), entry.getValue()));
        }


        return sseEmitter;
    }
    public void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
            throw new RuntimeException("연결 오류!");
        }
    }

    public void sendToNewNotification(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("newWork")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
            throw new RuntimeException("연결 오류!");
        }
    }


}
