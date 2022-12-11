package com.expertpeople.modules.main;

import com.expertpeople.infra.mail.EmailService;
import com.expertpeople.modules.Jwt.JwtService;
import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.AccountRepository;
import com.expertpeople.modules.job.Carrer;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.job.JobRepository;
import com.expertpeople.modules.work.Work;
import com.expertpeople.modules.work.WorkRepository;
import com.expertpeople.modules.work.WorkService;
import com.expertpeople.modules.zone.Zone;
import com.expertpeople.modules.zone.ZoneRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MainApiControllerTest {
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
    JobRepository jobRepository;
    @Autowired
    ZoneRepository zoneRepository;
    @Autowired
    WorkRepository workRepository;
    @Autowired
    private WorkService workService;
    private Zone testZone = Zone.builder().city("test도시").localNameOfCity("대전광역시").province("테스트구").build();
    private Job testJob=Job.builder().job("석공").averagePrice("test원").carrer(Carrer.TECH).build();

    @BeforeEach
    public void settingUserTest() throws Exception {
        Account account=new Account();

        account.setEmail("uiwv29l@naver.com");
        account.setPassword(passwordEncoder.encode("wnsvaf309"));
        account.setRole("ROLE_USER");
        Account account1=accountRepository.save(account);

        Zone zone= zoneRepository.save(testZone);
        Job job = jobRepository.save(testJob);

        createAutoWork(account1, zone, job);
    }



    @Test
    @DisplayName("일감 지역 키워드 검색")
    @WithUserDetails(value="uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void searchWork_WithZone() throws Exception{
        String keyword="대전";

        mockMvc.perform(get("/api/search/work?keyword="+keyword))
                .andExpect(status().isOk())
                .andDo(print());
        List<Work>works=workRepository.findByKeyword(keyword);
        assertNotEquals(works.size(),0);
    }

    @Test
    @DisplayName("일감 직업 키워드 검색")
    @WithUserDetails(value="uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void searchWork_WithJob() throws Exception{
        String keyword="석공";

        mockMvc.perform(get("/api/search/work?keyword="+keyword))
                .andExpect(status().isOk())
                .andDo(print());
        List<Work>works=workRepository.findByKeyword(keyword);
        assertNotEquals(works.size(),0);
    }

    @Test
    @DisplayName("로그인 하지 않은 사용자 일감공개된 정보 가져오기")
    void getMainWork() throws Exception {
        mockMvc.perform(get("/api/main/work"))
                .andExpect(status().isOk())
                .andDo(print());
        List<Work>works=workRepository.findTop9ByPublishedOrderByPublishedDateTimeAsc(true);

        assertEquals(works.size(),9);
    }

    @Test
    @DisplayName("로그인된 사용자 메인 정보 가져오기")
    @WithUserDetails(value="uiwv29l@naver.com",userDetailsServiceBeanName = "jwtUserDetailService",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void mainMyData() throws Exception {
        mockMvc.perform(get("/api/main/my-data"))
                .andExpect(status().isOk())
                .andDo(print());
    }


    private void createAutoWork(Account account1, Zone zone, Job job) {
        for (int i = 0; i < 30; i++) {
            String randomvalue = RandomString.make(5);
            Work work = Work.builder()
                    .title("테스트 일감" + randomvalue)
                    .path("test-" + randomvalue)
                    .shortDescription("test 일감")
                    .fullDescription("test")
                    .jobs(new HashSet<>())
                    .managers(new HashSet<>())
                    .zones(new HashSet<>())
                    .members(new HashSet<>())
                    .build();
            work.getJobs().add(job);
            work.getZones().add(zone);
            work.publish();
            work.getManagers().add(account1);
            Work newWork = workRepository.save(work);
        }
    }

}