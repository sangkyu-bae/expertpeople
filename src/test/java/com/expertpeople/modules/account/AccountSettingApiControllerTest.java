package com.expertpeople.modules.account;


import com.expertpeople.NotificationJobConfig;
import com.expertpeople.TestJobConfiguration;
import com.expertpeople.infra.config.BatchConfig;
import com.expertpeople.infra.mail.EmailService;
import com.expertpeople.modules.Jwt.JwtService;
import com.expertpeople.modules.account.form.PasswordForm;
import com.expertpeople.modules.account.form.Profile;
import com.expertpeople.modules.job.Carrer;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.job.JobsRepository;
import com.expertpeople.modules.job.form.JobForm;
import com.expertpeople.modules.zone.Zone;
import com.expertpeople.modules.zone.ZoneRepository;
import com.expertpeople.modules.zone.form.ZoneForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = {TestJobConfiguration.class,  NotificationJobConfig.class, Scheduled.class})
@AutoConfigureMockMvc
@Transactional
@WebAppConfiguration
@EnableBatchProcessing
@EnableConfigurationProperties
@ContextConfiguration
@ExtendWith(SpringExtension.class)
@SpringBatchTest
class AccountSettingApiControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    JwtService jwtService;
    @MockBean
    EmailService emailService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ZoneRepository zoneRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    JobsRepository jobsRepository;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    private String token = "";

    private Zone testZone = Zone.builder().city("test도시").localNameOfCity("테스트광역시").province("테스트구").build();
    private Job testJob = Job.builder().job("test잡").averagePrice("test원").carrer(Carrer.TECH).build();
    @BeforeEach
    public void settingUserTest() throws Exception {
        Account account = new Account();

        account.setEmail("uiwv29l@naver.com");
        account.setPassword(passwordEncoder.encode("wnsvaf309"));
        account.setRole("ROLE_USER");
        accountRepository.save(account);

        zoneRepository.save(testZone);
        jobsRepository.save(testJob);

        jobRepositoryTestUtils.removeJobExecutions();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now=simpleDateFormat.format(new Date());
        Map<String, JobParameter> jobParameterMap=new HashMap<>();
        jobParameterMap.put("requestDate",new JobParameter(now));
        JobParameters jobParameters=new JobParameters(jobParameterMap);

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters); // (3)

    }

    @Transactional
    @Test
    @DisplayName("프로필 수정 - 입력값 정상")
    @WithUserDetails(value = "uiwv29l@naver.com", userDetailsServiceBeanName = "jwtUserDetailService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void updateProfile() throws Exception {
        String bio = "소개 test";

        Profile profile = new Profile();
        profile.setBio(bio);
        mockMvc.perform(post("/api/setting/profile")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isOk())
                .andDo(print());

        Account account = accountRepository.findByEmail("uiwv29l@naver.com");
        assertEquals(account.getBio(), bio);
    }

    @Test
    @DisplayName("패스워드 수정 - 입력값 정상")
    @WithUserDetails(value = "uiwv29l@naver.com", userDetailsServiceBeanName = "jwtUserDetailService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void updatePassword() throws Exception {

        PasswordForm passwordForm = new PasswordForm();
        String password = "wnsvaf309";
        passwordForm.setPassword(password);
        passwordForm.setPasswordCheck(password);

        mockMvc.perform(post("/api/setting/password")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordForm)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("패스워드 수정 - 입력값 비정상")
    @WithUserDetails(value = "uiwv29l@naver.com", userDetailsServiceBeanName = "jwtUserDetailService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void noUpdatePassword() throws Exception {

        PasswordForm passwordForm = new PasswordForm();
        passwordForm.setPassword("wnsvaf309");
        passwordForm.setPasswordCheck("wnsvaf309ㄴ");

        mockMvc.perform(post("/api/setting/password")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordForm)))
                .andExpect(status().isBadRequest());

        Account account = accountRepository.findByEmail("uiwv29l@naver.com");
    }

    @Test
    @DisplayName("지역테그 가져오기")
    @WithUserDetails(value = "uiwv29l@naver.com", userDetailsServiceBeanName = "jwtUserDetailService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void getZoneTag() throws Exception {
        mockMvc.perform(get("/api/setting/zone"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("관심지역 추가하기")
    @WithUserDetails(value = "uiwv29l@naver.com", userDetailsServiceBeanName = "jwtUserDetailService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void addZoneTag() throws Exception {

        ZoneForm zoneForm = new ZoneForm();
        zoneForm.setZoneName(testZone.toString());
        mockMvc.perform(post("/api/setting/zone/add")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(zoneForm)))
                .andExpect(status().isOk())
                .andDo(print());
        Account account = accountRepository.findByEmail("uiwv29l@naver.com");
        Zone zone = zoneRepository.findByCityAndProvince(testZone.getCity(), testZone.getProvince());
        assertTrue(account.getZone().contains(zone));
    }

    @Test
    @DisplayName("관심지역 삭제하기")
    @WithUserDetails(value = "uiwv29l@naver.com", userDetailsServiceBeanName = "jwtUserDetailService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void removeZoneTag() throws Exception {
        Account account = accountRepository.findByEmail("uiwv29l@naver.com");
        accountService.addZone(account, testZone);
        assertTrue(account.getZone().contains(testZone));

        ZoneForm zoneForm = new ZoneForm();
        zoneForm.setZoneName(testZone.toString());
        mockMvc.perform(delete("/api/setting/zone/delete")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(zoneForm)))
                .andExpect(status().isOk())
                .andDo(print());

        assertFalse(account.getZone().contains(testZone));
    }

    @Test
    @DisplayName("직업테그 가져오기")
    @WithUserDetails(value = "uiwv29l@naver.com", userDetailsServiceBeanName = "jwtUserDetailService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void getJobTag() throws Exception {
        mockMvc.perform(get("/api/setting/job"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("관심직업 추가하기")
    @WithUserDetails(value = "uiwv29l@naver.com", userDetailsServiceBeanName = "jwtUserDetailService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void addJobsTag() throws Exception {
        JobForm jobForm = new JobForm();
        jobForm.setJobsName(testJob.toString());
        mockMvc.perform(post("/api/setting/add/jobs")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobForm)))
                .andExpect(status().isOk())
                .andDo(print());
        Account account = accountRepository.findByEmail("uiwv29l@naver.com");
        Job job = jobsRepository.findByJobAndCarrer(testJob.getJob(), testJob.getCarrer());
        assertTrue(account.getJobs().contains(job));
    }

    @Test
    @DisplayName("관심직업 삭제하기")
    @WithUserDetails(value = "uiwv29l@naver.com", userDetailsServiceBeanName = "jwtUserDetailService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void removeJobsTag() throws Exception {
        Account account = accountRepository.findByEmail("uiwv29l@naver.com");
        accountService.addJobs(account, testJob);
        assertTrue(account.getJobs().contains(testJob));

        JobForm jobForm = new JobForm();
        jobForm.setJobsName(testJob.toString());

        mockMvc.perform(delete("/api/setting/delete/jobs")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobForm)))
                .andExpect(status().isOk())
                .andDo(print());

        Job job = jobsRepository.findByJobAndCarrer(testJob.getJob(), testJob.getCarrer());
        assertFalse(account.getJobs().contains(job));
    }


}