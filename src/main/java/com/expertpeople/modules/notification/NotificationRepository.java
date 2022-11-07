package com.expertpeople.modules.notification;

import com.expertpeople.modules.account.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    long countByAccountAndChecked(Account account, boolean checked);
    @EntityGraph(attributePaths = {"account"})
    List<Notification> findByAccountOrderByCreateDateTimeDesc(Account account);

}
