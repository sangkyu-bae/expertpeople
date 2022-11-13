package com.expertpeople.modules.account;

import com.expertpeople.modules.Jwt.JwtResponse;
import com.expertpeople.modules.Jwt.JwtService;
import com.expertpeople.modules.account.form.JoinUpForm;
import com.expertpeople.modules.account.validator.JoinUpFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountApiController {
    private final AccountService accountService;
    private final JoinUpFormValidator joinUpFormValidator;
    private final JwtService jwtService;
    private final JavaMailSender javaMailSender;

    @InitBinder("joinUpForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(joinUpFormValidator);
    }
    @PostMapping("/api/join-up")
    public ResponseEntity<?> createAccount(@RequestBody @Valid JoinUpForm joinUpForm, Errors errors) throws Exception {
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }
        Account account=accountService.newAccount(joinUpForm);
        JwtResponse jwtResponse = jwtService.getJwtResponse(account,false);

        return ResponseEntity.ok(jwtResponse);
    }
    @GetMapping("/api/account")
    public Account getAccountInfo(@RequestParam(name="email") String email) throws Exception {
        Account account=accountService.getAccount(email);
        return account;
    }
    @GetMapping("/test")
    public ResponseEntity<?> test(){
        Account newAccount= Account.builder()
                .email("tkdrb1361@naver.com")
                .emailCheckToken("test")
                .build();
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(newAccount.getEmail());
        mailMessage.setSubject("expertPeople, 회원가입 인증");
        mailMessage.setText("/check-email-token?token="+newAccount.getEmailCheckToken()+
                "&email="+ newAccount.getEmail());
        javaMailSender.send(mailMessage);
        return null;
    }
}
