package com.expertpeople.modules.account;

import com.expertpeople.modules.Jwt.JwtResponse;
import com.expertpeople.modules.Jwt.JwtService;
import com.expertpeople.modules.account.form.JoinUpForm;
import com.expertpeople.modules.account.validator.JoinUpFormValidator;
import com.expertpeople.modules.work.Vo.fetchWorkVo;
import com.expertpeople.modules.work.Work;
import com.expertpeople.modules.work.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AccountApiController {
    private final AccountService accountService;
    private final JoinUpFormValidator joinUpFormValidator;
    private final JwtService jwtService;
    private final WorkRepository workRepository;

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
    @GetMapping("/api/account/work")
    public ResponseEntity<?> getWorkWithManager(@CurrentAccount Account account){
        List<Work> managerWorks=workRepository.findByManagers(account);
        List<fetchWorkVo>workVos=managerWorks.stream().map(fetchWorkVo::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(workVos);
    }
}
