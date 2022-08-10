package com.expertpeople.modules.Jwt;

import com.expertpeople.infra.jwt.JwtTokenUtil;
import com.expertpeople.infra.jwt.JwtUserDetailService;
import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtAuthController {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailService jwtUserDetailService;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authenticationRequest)throws Exception{
         Account account=accountRepository.findByEmail(authenticationRequest.getEmail());
         if(account==null){
             return ResponseEntity.badRequest().build();
         }
         jwtService.authenticate(account);

         final UserDetails userDetails=jwtUserDetailService.loadUserByUsername(authenticationRequest.getEmail());
         final String token =jwtTokenUtil.generateToken(userDetails);

         return ResponseEntity.ok(new JwtResponse(token));
    }

}
