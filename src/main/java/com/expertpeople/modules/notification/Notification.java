package com.expertpeople.modules.notification;

import com.expertpeople.modules.account.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter@Setter
@EqualsAndHashCode(of="id") @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {
    @Id @GeneratedValue
    private Long id;

    private String title;

    private String link;

    private String message;

    private boolean checked;

    @ManyToOne
    private Account account;

    private LocalDateTime createDateTime;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    public void updateReadNotification() {
        if(!this.checked){
            this.checked=true;
        }
    }

    public Notification test() {
        this.setChecked(false);
        return this;
    }
}
