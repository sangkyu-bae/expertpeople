package com.expertpeople.modules.notification;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.AccountRepository;
import com.expertpeople.modules.job.Carrer;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.job.JobRepository;
import com.expertpeople.modules.work.Work;
import com.expertpeople.modules.work.form.WorkForm;
import com.expertpeople.modules.zone.Zone;
import com.expertpeople.modules.zone.ZoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class NotificationApiControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ZoneRepository zoneRepository;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    NotificationRepository notificationRepository;

    private Zone testZone = Zone.builder().city("test도시").localNameOfCity("대전광역시").province("테스트구").build();
    private Job testJob=Job.builder().job("석공").averagePrice("test원").carrer(Carrer.TECH).build();
    private String PATH="/api/notification";
    @BeforeEach
    public void settingUserTest() throws Exception {
        Account account=new Account();
        Zone zone= zoneRepository.save(testZone);
        Job job=jobRepository.save(testJob);

        account.setEmail("uiwv29l@naver.com");
        account.setPassword(passwordEncoder.encode("wnsvaf309"));
        account.setRole("ROLE_USER");
        account.getZone().add(zone);
        account.getJobs().add(job);
        accountRepository.save(account);


    }

    @Test
    void testNotify() {
    }

    @Test
    @DisplayName("알림 정보 가져오기-안읽은 일감")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void getNotification() throws Exception {
        Account account=accountRepository.findByEmail("uiwv29l@naver.com");
        Notification notification=createNotification(account,false);
        mockMvc.perform(get(PATH))
                .andExpect(status().isOk())
                .andDo(print());
        List<Notification> newNotifications=notificationRepository.findByAccountAndCheckedOrderByCreateDateTimeDesc(account,false);
        Long oldCount=notificationRepository.countByAccountAndChecked(account,true);
        assertTrue(!newNotifications.isEmpty());
        assertTrue(oldCount==0);
    }

    @Test
    @DisplayName("알림 정보 가져오기-읽은 일감")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void getOldNotification() throws Exception {
        Account account=accountRepository.findByEmail("uiwv29l@naver.com");
        Notification notification=createNotification(account,true);
        mockMvc.perform(get(PATH+"/old"))
                .andExpect(status().isOk())
                .andDo(print());
        List<Notification> oldNotifications=notificationRepository.findByAccountAndCheckedOrderByCreateDateTimeDesc(account,true);
        Long newCount=notificationRepository.countByAccountAndChecked(account,false);
        assertTrue(!oldNotifications.isEmpty());
        assertTrue(newCount==0);
    }


    private Notification createNotification(Account account,boolean isChecked) {
        Notification notification= Notification.builder()
                .title("testTitle")
                .link("testLink")
                .message("testMessage")
                .checked(isChecked)
                .account(account)
                .createDateTime(LocalDateTime.now())
                .notificationType(NotificationType.WORK_CREATED)
                .build();
        notificationRepository.save(notification);
        return notification;
    }


}