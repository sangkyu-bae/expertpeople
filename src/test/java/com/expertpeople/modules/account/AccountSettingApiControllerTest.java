package com.expertpeople.modules.account;


import com.expertpeople.infra.mail.EmailService;
import com.expertpeople.modules.Jwt.JwtResponse;
import com.expertpeople.modules.Jwt.JwtService;
import com.expertpeople.modules.account.form.Profile;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class AccountSettingApiControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    AccountService accountService;
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
    private String token="";

    @PostConstruct
    public void settingUserTest() throws Exception {
        Account account=new Account();

        account.setEmail("uiwv29l@naver.com");
        account.setPassword(passwordEncoder.encode("wnsvaf309"));
        account.setRole("ROLE_USER");
        accountRepository.save(account);
    }


    @Test
    @DisplayName("프로필 수정 - 입력값 정상")
    @WithUserDetails(value = "uiwv29l@naver.com")
    void updateProfile() throws Exception{

//        System.out.println("타?");
//
        System.out.println(token);
//        HttpHeaders httpHeaders= new HttpHeaders();
//        httpHeaders.add("Authorization", "Bearer " + token);
        String bio = "소개  test";

        Profile profile=new Profile();
        profile.setBio(bio);

        Account account=accountRepository.findByEmail("uiwv29l@naver.com");
        mockMvc.perform(post("/api/setting/profile")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profile))
                        .with(csrf()))
                        .andExpect(status().isOk());

        Account account2=accountRepository.findByEmail("uiwv29l@naver.com");
        assertTrue(account.getBio()==bio);
    }


}