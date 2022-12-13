package com.expertpeople.modules.recruitmentGroup;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.AccountRepository;
import com.expertpeople.modules.enrollment.Enrollment;
import com.expertpeople.modules.enrollment.EnrollmentRepository;
import com.expertpeople.modules.job.Carrer;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.job.JobRepository;
import com.expertpeople.modules.recruitmentGroup.form.RecruitForm;
import com.expertpeople.modules.work.Work;
import com.expertpeople.modules.work.WorkRepository;
import com.expertpeople.modules.work.WorkService;
import com.expertpeople.modules.work.form.WorkForm;
import com.expertpeople.modules.zone.Zone;
import com.expertpeople.modules.zone.ZoneRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RecruitmentApiControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    WorkRepository workRepository;
    @Autowired
    WorkService workService;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    ZoneRepository zoneRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    RecruitmentRepository recruitmentRepository;
    @Autowired
    EnrollmentRepository enrollmentRepository;
    @Autowired
    RecruitmentService recruitmentService;

    private Zone testZone = Zone.builder().city("test도시").localNameOfCity("대전광역시").province("테스트구").build();
    private Job testJob=Job.builder().job("석공").averagePrice("test원").carrer(Carrer.TECH).build();

    private String PATH="/api/recruitment";
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

        WorkForm workForm=new WorkForm();

        workForm.setPath("test");
        workForm.setTitle("test일감");
        workForm.setShortDescription("test짧은내용ㅇ");
        workForm.setFullDescription("긴test내용");

        Work work=workService.createWork(account,workForm);
    }

    @Test
    @DisplayName("회원 직업 태그 가져오기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void getJob() throws Exception {
        mockMvc.perform(get("/api/recruitment/jobs"))
                .andExpect(status().isOk())
                .andDo(print());

        Account account=accountRepository.findByEmail("uiwv29l@naver.com");;
        Set<Job> job=account.getJobs();
        Job jobTest= job.stream().filter(jobData->jobData.getJob().equals("석공"))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException());

        assertTrue(account.getJobs().size()>0);
        assertNotNull(jobTest);
    }

    @Test
    @DisplayName("일감 구인 구하기 -입력값 정상")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void addRecruitment() throws Exception{
        Work work=workRepository.findByPath("test");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDateTime=now.plusDays(1);

        RecruitForm recruitForm = new RecruitForm();
        recruitForm.setTitle("test");
        recruitForm.setDescription("testDescription");
        recruitForm.setJobsName("조적(기술공)");
        recruitForm.setEventType("FCFS");
        recruitForm.setStartDateTime(now);
        recruitForm.setEndDateTime(endDateTime);

        mockMvc.perform(post(PATH+"/test/add/recruitment")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recruitForm)))
                .andExpect(status().isOk())
                .andDo(print());
//        List<Recruitment> recruitment=recruitmentRepository.findByTitle("test");
//        assertTrue(!recruitment.isEmpty());
    }

    @Test
    @DisplayName("일감 구인 구하기 -입력값 오류")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void addRecruitment_fali() throws Exception{
        Work work=workRepository.findByPath("test");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDateTime=now.plusDays(1);

        RecruitForm recruitForm = new RecruitForm();
        recruitForm.setTitle("test");
        recruitForm.setJobsName("조적(기술공)");
        recruitForm.setEventType("FCFS");
        recruitForm.setStartDateTime(now);
        recruitForm.setEndDateTime(endDateTime);

        mockMvc.perform(post(PATH+"/test/add/recruitment")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recruitForm)))
                .andExpect(status().isBadRequest())
                .andDo(print());
        List<Recruitment> recruitment=recruitmentRepository.findByWorkOrderByStartDateTime(work);
        assertTrue(recruitment.isEmpty());
    }


    @Test
    @DisplayName("구인 정보 가져 오기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void getRecruitInfo() throws Exception {
        Work work=workRepository.findByPath("test");
        createTestRecruit(work);
        mockMvc.perform(get("/api/recruitment/test"))
                .andExpect(status().isOk())
                .andDo(print());
        List<Recruitment> recruitments=recruitmentRepository.findByWorkOrderByStartDateTime(work);
        assertTrue(!recruitments.isEmpty());
    }


    @Test
    @DisplayName("회원 직업 태그 가져오기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void getRecruitment() {
    }

    @Test
    @DisplayName("회원 직업 태그 가져오기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void updateRecruitment() {
    }

    @Test
    @DisplayName("회원 직업 태그 가져오기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void newEnrollment() {
    }

    @Test
    @DisplayName("회원 직업 태그 가져오기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void removeRecruit() {
    }

    @Test
    @DisplayName("회원 직업 태그 가져오기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void acceptEnrollment() {
    }

    @Test
    @DisplayName("회원 직업 태그 가져오기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void rejectEnrollment() {
    }

    @Test
    @DisplayName("회원 직업 태그 가져오기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void attendAcceptEnrollment() {
    }

    @Test
    @DisplayName("회원 직업 태그 가져오기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void cancelAttendEnrollment() {
    }

    private void createTestRecruit(Work work) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDateTime=now.plusDays(1);

        Account account=accountRepository.findByEmail("uiwv29l@naver.com");


        Recruitment recruitment= Recruitment.builder()
                .title("ttt")
                .createTime(now)
                .endDateTime(endDateTime)
                .startDateTime(now)
                .endEnrollmentDateTime(endDateTime)
                .work(work)
                .createBy(account)
                .description("tet")
                .limitOfEnrollments(2)
                .eventType(EventType.FCFS)
                .build();
        Recruitment saveRecruitment= recruitmentRepository.save(recruitment);
        Enrollment enrollment=Enrollment.builder().
                account(account).
                enrolledAt(LocalDateTime.now()).
                accepted(false).
                build();
        recruitment.addEnrollment(enrollment);
        enrollmentRepository.save(enrollment);
    }

}