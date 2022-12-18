package com.expertpeople.modules.notification;

import com.expertpeople.infra.jwt.JwtTokenProvider;
import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.AccountRepository;
import com.expertpeople.modules.notification.emitter.EmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.List;
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
    private final JwtTokenProvider jwtTokenProvider;
    public SseEmitter subscribe(Account account, String lastEventId) {

        String userId=account.getId()+"_"+System.currentTimeMillis();
        SseEmitter sseEmitter=emitterRepository.save(userId,new SseEmitter(DEFAULT_TIMEOUT));

        sseEmitter.onCompletion(()->emitterRepository.deleteById(userId));
        sseEmitter.onTimeout(()->emitterRepository.deleteById(userId));

        Long notifyCount=notificationRepository.countByAccountAndChecked(account,false);
        this.sendToClient(sseEmitter,userId,notifyCount);

        return sseEmitter;
    }
    public void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("init")
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


    public Account getAccountId(String jwt) {
        String email="";
        email= jwtTokenProvider.getUserEmail(jwt.substring(7));
        if(email.equals("")){
            throw new SecurityException("로그인된 토큰이 아닙니다.");
        }
        Account account=accountRepository.findByEmail(email);
        if(account==null){
            throw new IllegalArgumentException("존재하는 계정이 아닙니다.");
        }


        return account;
    }

    public void isNotification(Long notificationId,Account account) {
        boolean isNotification=notificationRepository.existsByIdAndAccount(notificationId,account);
        if(!isNotification){
            throw new IllegalArgumentException("존재하지 않은 알람입니다.");
        }
    }

    public void readNotification(Notification notification) {
        notification.updateReadNotification();
    }

    public void readAllNotification(List<Notification> notifications) {
        notifications.stream().forEach(notification -> notification.updateReadNotification());
    }
}
