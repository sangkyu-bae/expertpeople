package com.expertpeople.modules.account;

import com.expertpeople.infra.jwt.JwtTokenUtil;
import com.expertpeople.infra.jwt.JwtUserDetailService;
import com.expertpeople.modules.Jwt.JwtService;
import com.expertpeople.modules.account.form.JoinUpForm;
import com.expertpeople.modules.account.validator.JoinUpFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountApiController {

    private final AccountService accountService;
    private final JoinUpFormValidator joinUpFormValidator;
    private final JwtService jwtService;
    private final JwtUserDetailService jwtUserDetailService;
    private final JwtTokenUtil jwtTokenUtil;


    @InitBinder("joinUpForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(joinUpFormValidator);
    }
    @PostMapping("/join-up")
    public ResponseEntity<?> createAccount(@RequestBody @Valid JoinUpForm joinUpForm, Errors errors) throws Exception {
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().build();
        }
        Account account=accountService.newAccount(joinUpForm);

        jwtService.authenticate(account);
        final UserDetails userDetails=jwtUserDetailService.loadUserByUsername(account.getEmail());
        final String token=jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok("dskj");
    }

}