package com.expertpeople.modules.account;

import com.expertpeople.WithAccount;
import com.expertpeople.modules.Jwt.JwtResponse;
import com.expertpeople.modules.Jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private String token="";

//    public void getToken()throws Exception{
//        Account account= Account.builder().
//                email("uiwv29l@naver.com")
//                .password("wnsvaf309")
//                .build();
//        JwtResponse jwtResponse=jwtService.getJwtResponse(account,true);
//        token=jwtResponse.getToken();
//    }

    @BeforeEach
    public void initializationToken() throws Exception {
        token="";
        Account account= Account.builder().
                email("uiwv29l@naver.com")
                .password("wnsvaf309")
                .build();
        JwtResponse jwtResponse=jwtService.testGetJwtResponse(true);
        token=jwtResponse.getToken();
    }

    @WithAccount(email = "uiwv29l@naver.com")
    @DisplayName("프로필 수정 - 입력값 정상")
    @Test
    void updateProfile() throws Exception{
        System.out.println("타?");
        String bio = "소개  test";
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);

        mockMvc.perform(post("/api/setting/profile")
                .param("bio", bio))
                .andExpect(status().isOk()).andDo(print());
        Account account=accountRepository.findByEmail("uiwv29l@naver.com");
        assertEquals(bio,account.getBio());
    }


}