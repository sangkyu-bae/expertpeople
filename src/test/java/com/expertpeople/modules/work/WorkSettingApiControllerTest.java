package com.expertpeople.modules.work;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.AccountRepository;
import com.expertpeople.modules.account.AccountService;
import com.expertpeople.modules.job.Carrer;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.job.JobRepository;
import com.expertpeople.modules.job.form.JobForm;
import com.expertpeople.modules.work.form.WorkDescriptionForm;
import com.expertpeople.modules.work.form.WorkForm;
import com.expertpeople.modules.zone.Zone;
import com.expertpeople.modules.zone.ZoneRepository;
import com.expertpeople.modules.zone.form.ZoneForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class WorkSettingApiControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    WorkRepository workRepository;
    @Autowired
    WorkService workService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    ZoneRepository zoneRepository;
    private Zone testZone = Zone.builder().city("test도시").localNameOfCity("테스트광역시").province("테스트구").build();
    private Job testJob=Job.builder().job("test잡").averagePrice("test원").carrer(Carrer.TECH).build();

    private String apiPath="/api/work/setting/";
    @BeforeEach
    public void settingUserWithWorkTest() throws Exception {
        Account account=new Account();

        account.setEmail("uiwv29l@naver.com");
        account.setPassword(passwordEncoder.encode("wnsvaf309"));
        account.setRole("ROLE_USER");
        accountRepository.save(account);

        WorkForm workForm=new WorkForm();

        workForm.setPath("test");
        workForm.setTitle("test일감");
        workForm.setShortDescription("test짧은내용ㅇ");
        workForm.setFullDescription("긴test내용");

        Work work=workService.createWork(account,workForm);

        zoneRepository.save(testZone);
        jobRepository.save(testJob);
    }
    @Test
    @DisplayName("일감 수정 - 입력값 정상")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void updateWorkInfo() throws Exception {
        String path=workRepository.findByPath("test").getPath();
        String shortDescription="test짧은내용스";
        String fullDescription="test긴내용";
        WorkDescriptionForm workDescriptionForm=new WorkDescriptionForm();
        workDescriptionForm.setShortDescription(shortDescription);
        workDescriptionForm.setFullDescription(fullDescription);

        mockMvc.perform(put(apiPath+path+"/description")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workDescriptionForm)))
                .andExpect(status().isOk());

        Work work=workRepository.findByPath("test");
        assertEquals(shortDescription,work.getShortDescription());
        assertEquals(fullDescription,work.getFullDescription());
    }

    @Test
    @DisplayName("일감 수정 - 입력값 오류")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void updateWorkInfo_fail() throws Exception {
        String path=workRepository.findByPath("test").getPath();
        String shortDescription="test짧은내용스";
        WorkDescriptionForm workDescriptionForm=new WorkDescriptionForm();
        workDescriptionForm.setShortDescription(shortDescription);

        mockMvc.perform(put(apiPath+path+"/description")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workDescriptionForm)))
                .andExpect(status().isBadRequest());

        Work work=workRepository.findByPath("test");
        assertNotEquals(shortDescription,work.getShortDescription());
    }

    @Test
    @DisplayName("일감 Job태그 가져오기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void getJob() throws Exception {
        String path=workRepository.findByPath("test").getPath();
        mockMvc.perform(get(apiPath+path+"/jobs"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("일감 Job태그 추가하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void addJob() throws Exception {
        Work work=workRepository.findByPath("test");
        String path=work.getPath();

        JobForm jobForm=new JobForm();
        jobForm.setJobsName(testJob.toString());

        mockMvc.perform(post(apiPath+path+"/jobs/add")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobForm)))
                .andExpect(status().isOk())
                .andDo(print());
        Job job=jobRepository.findByJobAndCarrer(testJob.getJob(),testJob.getCarrer());
        assertTrue(work.getJobs().contains(job));
    }

    @Test
    @DisplayName("일감 Job태그 삭제하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void removeJob() throws Exception {
        Work work=workRepository.findByPath("test");
        Account account=accountRepository.findByEmail("uiwv29l@naver.com");
        String path=work.getPath();

        workService.addJobs(account,testJob,path);
        assertTrue(work.getJobs().contains(testJob));

        JobForm jobForm=new JobForm();
        jobForm.setJobsName(testJob.toString());

        mockMvc.perform(delete(apiPath+path+"/jobs/remove")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobForm)))
                .andExpect(status().isOk());
        Job job=jobRepository.findByJobAndCarrer(testJob.getJob(),testJob.getCarrer());
        assertFalse(work.getJobs().contains(job));

    }


    @Test
    @DisplayName("일감 지역태그 가져오기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void getZone() throws Exception {
        String path=workRepository.findByPath("test").getPath();
        mockMvc.perform(get(apiPath+path+"/zone"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("일감 지역태그 추가하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void addZone() throws Exception {
        Work work=workRepository.findByPath("test");
        String path=work.getPath();
        ZoneForm zoneForm=new ZoneForm();
        zoneForm.setZoneName(testZone.toString());

        mockMvc.perform(post(apiPath+path+"/zone/add")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(zoneForm)))
                .andExpect(status().isOk())
                .andDo(print());
        Zone zone=zoneRepository.findByCityAndProvince(testZone.getCity(),testZone.getProvince());
        assertTrue(work.getZones().contains(zone));
    }

    @Test
    @DisplayName("일감 지역태그 삭제하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void removeZone() throws Exception {
        Work work=workRepository.findByPath("test");
        String path=work.getPath();
        Account account=accountRepository.findByEmail("uiwv29l@naver.com");

        workService.addZone(account,testZone,path);
        Zone zone=zoneRepository.findByCityAndProvince(testZone.getCity(),testZone.getProvince());
        assertTrue(work.getZones().contains(zone));

        ZoneForm zoneForm=new ZoneForm();
        zoneForm.setZoneName(testZone.toString());
        mockMvc.perform(delete(apiPath+path+"/zone/remove")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(zoneForm)))
                .andExpect(status().isOk())
                .andDo(print());

        assertFalse(work.getZones().contains(zone));
    }

    @Test
    @DisplayName("일감 공개하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void updateWorkPublish() throws Exception {
        Work work=workRepository.findByPath("test");
        String path=work.getPath();

        mockMvc.perform(put(apiPath+path+"/publish"))
                .andExpect(status().isOk())
                .andDo(print());

        assertEquals(true,work.isPublished());
    }

    @Test
    @DisplayName("일감 종료하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void updateWorkClose() throws Exception {
        Work work=workRepository.findByPath("test");
        String path=work.getPath();
        workService.publish(work);
        mockMvc.perform(put(apiPath+path+"/close"))
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(true,work.isClose());
    }

    @Test
    @DisplayName("일감 구인 시작하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void startRecruit() throws Exception {
        Work work=workRepository.findByPath("test");
        String path=work.getPath();
        workService.publish(work);
        mockMvc.perform(put(apiPath+path+"/recruit/start"))
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(true,work.isRecruiting());
    }

    @Test
    @DisplayName("일감 구인 종료하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void stopRecruit() throws Exception {
        Work work=workRepository.findByPath("test");
        String path=work.getPath();
        workService.publish(work);
        work.setRecruiting(true);
        mockMvc.perform(put(apiPath+path+"/recruit/stop"))
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(false,work.isRecruiting());
    }

    @Test
    @DisplayName("일감 URL 변경하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void updateWorkUrl() throws Exception {
        Work work=workRepository.findByPath("test");
        String path=work.getPath();
        String newPath="test-url";
        mockMvc.perform(put(apiPath+path+"/url/"+newPath))
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(work.getPath(),newPath);
    }

    @Test
    @DisplayName("일감 제목 변경하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void updateTitle() throws Exception {
        Work work=workRepository.findByPath("test");
        String path=work.getPath();
        String newTitle="testTitle";
        mockMvc.perform(put(apiPath+path+"/title/"+newTitle))
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(work.getTitle(),newTitle);
    }

    @Test
    @DisplayName("일감 삭제하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void removeWork() throws Exception {
        Work work=workRepository.findByPath("test");
        String path=work.getPath();

        mockMvc.perform(delete(apiPath+path+"/remove"))
                .andExpect(status().isOk())
                .andDo(print());

        Boolean isExistWork=workRepository.existsByPath(path);
        assertEquals(false,isExistWork);
    }

    @Test
    @DisplayName("일감 멤버 추가 하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void addMember() throws Exception {
        Work work=workRepository.findByPath("test");
        String path=work.getPath();

        mockMvc.perform(put(apiPath+path+"/add/member"))
                .andExpect(status().isOk())
                .andDo(print());

        Account account=accountRepository.findByEmail("uiwv29l@naver.com");
        assertTrue(work.getMembers().contains(account));
    }

    @Test
    @DisplayName("일감 멤버 삭제 하기")
    @WithUserDetails(value = "uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void removeMember() throws Exception {
        Work work=workRepository.findByPath("test");
        String path=work.getPath();
        Account account=accountRepository.findByEmail("uiwv29l@naver.com");
        workService.addMember(account,work);
        assertTrue(work.getMembers().contains(account));

        mockMvc.perform(put(apiPath+path+"/remove/member"))
                .andExpect(status().isOk())
                .andDo(print());

        assertFalse(work.getMembers().contains(account));
    }
}