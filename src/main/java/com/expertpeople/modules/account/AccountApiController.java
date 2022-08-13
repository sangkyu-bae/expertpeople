package com.expertpeople.modules.account;

import com.expertpeople.modules.Jwt.JwtResponse;
import com.expertpeople.modules.Jwt.JwtService;
import com.expertpeople.modules.account.form.JoinUpForm;
import com.expertpeople.modules.account.validator.JoinUpFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountApiController {
    private final AccountService accountService;
    private final JoinUpFormValidator joinUpFormValidator;
    private final JwtService jwtService;

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
        JwtResponse jwtResponse = jwtService.getJwtResponse(account);
        return ResponseEntity.ok(jwtResponse);
    }
}
