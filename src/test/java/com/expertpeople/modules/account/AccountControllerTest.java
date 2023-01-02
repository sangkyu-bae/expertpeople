package com.expertpeople.modules.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    AccountRepository accountRepository;
    @MockBean
    JavaMailSender javaMailSender;

    @DisplayName("회원 가입 화면 테스트")
    @Test
    void joinUpForm() throws Exception{
        mockMvc.perform(get("/join-up"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("account/join-up"))
                .andExpect(model().attributeExists("joinUpForm"))
                .andExpect(unauthenticated());
    }

    @DisplayName("회원 가입 처리 - 입력값 오류")
    @Test
    void joinUpSubmit_wrong_input()throws Exception{

        mockMvc.perform(post("/join-up")
                        .param("nickname", "testtest")
                        .param("email","email~")
                        .param("password","123")
                        .param("name","s")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("account/join-up"))
                .andExpect(unauthenticated());
    }

    @DisplayName("회원 가입 처리 - 입력값 정상")
    @Test
    void joinUpSubmit_corret_input()throws Exception{

        mockMvc.perform(post("/join-up")
                        .param("nickname", "testtest")
                        .param("email","email@email.com")
                        .param("password","12345678")
                        .param("name","손건")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        assertTrue(accountRepository.existsByEmail("email@email.com"));

        Account account=accountRepository.findByEmail("email@email.com");
        assertNotNull(account);

        assertNotEquals(account.getPassword(),"12345678");
        assertNotNull(account.getEmailCheckToken());
        then(javaMailSender).should().send(any(SimpleMailMessage.class));

    }

}