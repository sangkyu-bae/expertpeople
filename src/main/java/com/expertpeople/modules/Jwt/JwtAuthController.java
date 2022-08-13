package com.expertpeople.modules.Jwt;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtAuthController {
    private final JwtService jwtService;
    private final AccountRepository accountRepository;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authenticationRequest)throws Exception{
         Account account=accountRepository.findByEmail(authenticationRequest.getEmail());
         if(account==null){
             return ResponseEntity.badRequest().body("존재하지 않은 이메일입니다.");
         }
        JwtResponse jwtResponse=jwtService.getJwtResponse(account);
         return ResponseEntity.ok(jwtResponse);
    }

}
