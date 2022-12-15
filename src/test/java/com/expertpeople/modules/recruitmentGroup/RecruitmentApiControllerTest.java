package com.expertpeople.modules.recruitmentGroup;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.AccountRepository;
import com.expertpeople.modules.enrollment.Enrollment;
import com.expertpeople.modules.enrollment.EnrollmentRepository;
import com.expertpeople.modules.job.Carrer;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.job.JobRepository;
import com.expertpeople.modules.recruitmentGroup.form.RecruitForm;
import com.expertpeople.modules.recruitmentGroup.form.RecruitUpdateForm;
import com.expertpeople.modules.work.Work;
import com.expertpeople.modules.work.WorkRepository;
import com.expertpeople.modules.work.WorkService;
import com.expertpeople.modules.work.form.WorkForm;
import com.expertpeople.modules.zone.Zone;
import com.expertpeople.modules.zone.ZoneRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private String workPath="test";
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
        mockMvc.perform(get(PATH+"/jobs"))
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
    @DisplayName("일감 구인 리스트 가져 오기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void getRecruitInfo() throws Exception {
        Work work=workRepository.findByPath("test");
        createTestRecruit(work,EventType.FCFS);
        mockMvc.perform(get(PATH+"/"+workPath))
                .andExpect(status().isOk())
                .andDo(print());
        List<Recruitment> recruitments=recruitmentRepository.findByWorkOrderByStartDateTime(work);
        assertTrue(!recruitments.isEmpty());
    }


    @Test
    @DisplayName("구인 정보 가져오기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void getRecruitment() throws Exception {
        Work work=workRepository.findByPath("test");
        Recruitment recruitment=createTestRecruit(work,EventType.FCFS);
        Account account=accountRepository.findByEmail("uiwv29l@naver.com");
        mockMvc.perform(get(PATH+"/"+work.getPath()+"/recruitment/"+recruitment.getId()))
                .andExpect(status().isOk())
                .andDo(print());

        assertTrue(recruitment.isManager(account));
        assertTrue(recruitment.isMember(account));
    }

    @Test
    @DisplayName("구인 정보 수정하기-입력값 정상 및 관리자 승인")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void updateRecruitment() throws Exception {
        String testDescription="test 입력값";
        RecruitUpdateForm recruitUpdateForm=getTestRecruitForm(testDescription,"COMFIRMATIVE");

        Work work=workRepository.findByPath("test");
        Recruitment recruitment=createTestRecruit(work,EventType.FCFS);

        mockMvc.perform(put(PATH+"/update/"+workPath+"/"+recruitment.getId())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recruitUpdateForm)))
                .andExpect(status().isOk())
                .andDo(print());
        Recruitment testRecruitment=recruitmentRepository.findById(recruitment.getId()).orElseThrow();
        assertTrue(testRecruitment.getEventType()==EventType.COMFIRMATIVE);
        assertTrue(testRecruitment.getDescription().equals(testDescription));
    }

    @Test
    @DisplayName("구인 정보 수정하기-입력값 정상 및 선착순")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void updateRecruitment_FCFS() throws Exception {
        String testDescription="test 입력값";
        RecruitUpdateForm recruitUpdateForm=getTestRecruitForm(testDescription,"FCFS");

        Work work=workRepository.findByPath("test");
        Recruitment recruitment=createTestRecruit(work,EventType.FCFS);

        Enrollment enrollment=testEnrollmentData(recruitment);

        assertTrue(!enrollment.isAccepted());
        mockMvc.perform(put(PATH+"/update/"+workPath+"/"+recruitment.getId())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recruitUpdateForm)))
                .andExpect(status().isOk())
                .andDo(print());
        Recruitment testRecruitment=recruitmentRepository.findById(recruitment.getId()).orElseThrow();
        assertTrue(testRecruitment.getEventType()==EventType.FCFS);
        assertTrue(testRecruitment.getDescription().equals(testDescription));
        assertTrue(enrollment.isAccepted());
    }

    @Test
    @DisplayName("구인 정보 수정하기-입력값 비정상")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void updateRecruitment_Fail() throws Exception {
        String testDescription="test 입력값";
        RecruitUpdateForm recruitUpdateForm=getTestRecruitForm(testDescription,"FCFS");
        recruitUpdateForm.setDescription(null);
        Work work=workRepository.findByPath("test");
        Recruitment recruitment=createTestRecruit(work,EventType.FCFS);

        mockMvc.perform(put(PATH+"/update/"+workPath+"/"+recruitment.getId())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recruitUpdateForm)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("구직 신청자 구직 승락하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void newEnrollment() throws Exception {
        Work work=workRepository.findByPath("test");
        Account account=getAccount();
        Recruitment recruitment=getRecruitment(work,account,EventType.FCFS);
        Account addEnrollmentAccount=accountRepository.findByEmail("uiwv29l@naver.com");
        List<Enrollment>enrollments=recruitment.getErollments();

        assertTrue(!isEnrollment(enrollments,addEnrollmentAccount));

        mockMvc.perform(put(PATH+"/"+workPath+"/recruitment/"+recruitment.getId())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
       assertTrue(isEnrollment(enrollments,addEnrollmentAccount));
    }
    @Test
    @DisplayName("구인 삭제 하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void removeRecruit() throws Exception {
        Work work=workRepository.findByPath("test");
        Recruitment recruitment=createTestRecruit(work,EventType.FCFS);

        boolean isExistsRecruitment=recruitmentRepository.existsById(recruitment.getId());
        assertTrue(isExistsRecruitment);


        mockMvc.perform(delete(PATH+"/"+workPath+"/recruitment/"+recruitment.getId())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        isExistsRecruitment=recruitmentRepository.existsById(recruitment.getId());
        assertTrue(!isExistsRecruitment);
    }

    @Test
    @DisplayName("구인인력 구인 승락하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void acceptEnrollment() throws Exception {
        List<Enrollment>enrollments=getTestEnrollments(false);
        enrollments.stream().forEach(enrollment -> enrollment.setAccepted(false));
        Enrollment enrollment=enrollments.stream().filter(enrollment1 -> enrollment1.isAccepted()==false).findFirst().orElseThrow();
        Long recruitmentId=enrollment.getRecruitment().getId();

        assertTrue(!enrollment.isAccepted());
        mockMvc.perform(put(PATH+"/"+workPath+"/accept/recruitment/"+recruitmentId+"/"+enrollment.getId())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        enrollments=enrollment.getRecruitment().getErollments();
        enrollment=enrollments.stream().filter(enrollment1 -> enrollment1.isAccepted()==true).findFirst().orElseThrow();
        assertTrue(enrollment.isAccepted());
    }

    @Test
    @DisplayName("구인인력 구인 취소하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void rejectEnrollment() throws Exception {
        List<Enrollment>enrollments=getTestEnrollments(true);
        Enrollment enrollment=enrollments.stream().filter(enrollment1 -> enrollment1.isAccepted()==true).findFirst().orElseThrow();
        Long recruitmentId=enrollment.getRecruitment().getId();

        assertTrue(enrollment.isAccepted());
        mockMvc.perform(put(PATH+"/"+workPath+"/reject/recruitment/"+recruitmentId+"/"+enrollment.getId())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        enrollments=enrollment.getRecruitment().getErollments();
        enrollment=enrollments.stream().filter(enrollment1 -> enrollment1.isAccepted()==false).findFirst().orElseThrow();
        assertTrue(!enrollment.isAccepted());
    }

    @Test
    @DisplayName("구인인력 일 시작시간 참가확인")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void attendAcceptEnrollment() throws Exception {
        List<Enrollment>enrollments=getTestEnrollments(true);
        Enrollment enrollment=enrollments.get(0);
        Long recruitmentId=enrollment.getRecruitment().getId();

        assertTrue(!enrollment.isAttended());
        mockMvc.perform(put(PATH+"/"+workPath+"/attend/enrollment/"+recruitmentId+"/"+enrollment.getId())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        assertTrue(enrollment.isAttended());
    }

    @Test
    @DisplayName("출석확인된 구인 출석 취소하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void cancelAttendEnrollment() throws Exception {
        List<Enrollment>enrollments=getTestEnrollments(true);
        enrollments.stream().forEach(enrollment -> enrollment.setAttended(true));
        Enrollment enrollment=enrollments.get(0);
        Long recruitmentId=enrollment.getRecruitment().getId();
        assertTrue(enrollment.isAttended());

        mockMvc.perform(put(PATH+"/"+workPath+"/cancel/attend/"+recruitmentId+"/"+enrollment.getId())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        assertTrue(!enrollment.isAttended());
    }

    List<Enrollment> getTestEnrollments(boolean isAccepted){
        Work work=workRepository.findByPath("test");
        Recruitment recruitment=createTestRecruit(work,EventType.COMFIRMATIVE);
        List<Enrollment>enrollments=recruitment.getErollments();

        enrollments.stream().forEach(enrollment -> enrollment.setAccepted(isAccepted));
        return enrollments;
    }

    private boolean isEnrollment(List<Enrollment>enrollments,Account account) {
        boolean isEnrollment=false;
        for(Enrollment enrollment:enrollments){
            if(enrollment.getAccount().equals(account)){
                isEnrollment=true;
            }
        }
        return isEnrollment;
    }
    private Enrollment testEnrollmentData(Recruitment recruitment) {
        Account account = getAccount();
        Enrollment enrollment=new Enrollment();
        enrollment.setRecruitment(recruitment);
        enrollment.setAttended(false);
        enrollment.setAccepted(false);
        enrollment.setAccount(account);
        enrollment.setEnrolledAt(LocalDateTime.now());
        recruitment.addEnrollment(enrollment);

        enrollmentRepository.save(enrollment);
        return enrollment;
    }

    private Account getAccount() {
        Account account=new Account();
        account.setEmail("uiwv29l1@naver.com");
        account.setPassword(passwordEncoder.encode("wnsvaf309"));
        account.setRole("ROLE_USER");
        Account saveAccount= accountRepository.save(account);
        return saveAccount;
    }

    private RecruitUpdateForm getTestRecruitForm(String testDescription,String EventType) {
        RecruitUpdateForm recruitUpdateForm=new RecruitUpdateForm();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDateTime=now.plusDays(1);

        recruitUpdateForm.setDescription(testDescription);
        recruitUpdateForm.setEventType(EventType);
        recruitUpdateForm.setEndDateTime(endDateTime);
        recruitUpdateForm.setStartDateTime(now);
        recruitUpdateForm.setLimitOfEnrollments(3);

        return recruitUpdateForm;
    }

    private Recruitment createTestRecruit(Work work,EventType eventType) {
        Account account=accountRepository.findByEmail("uiwv29l@naver.com");
        Recruitment recruitment = getRecruitment(work, account,eventType);

        return recruitment;
    }

    private Recruitment getRecruitment(Work work, Account account,EventType eventType) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDateTime=now.plusDays(1);
        List<Enrollment> enrollments=new ArrayList<>();
        Recruitment recruitment= Recruitment.builder()
                .title("ttt")
                .createTime(now)
                .endDateTime(endDateTime)
                .startDateTime(now)
                .endEnrollmentDateTime(endDateTime)
                .work(work)
                .createBy(account)
                .description("tet")
                .erollments(enrollments)
                .limitOfEnrollments(2)
                .eventType(eventType)
                .build();
        Recruitment saveRecruitment= recruitmentRepository.save(recruitment);
        Recruitment addEnrollmentRecruitment= recruitmentService.addEnrollment(account,saveRecruitment);
        return addEnrollmentRecruitment;
    }

}