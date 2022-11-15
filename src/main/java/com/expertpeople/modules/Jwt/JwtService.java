package com.expertpeople.modules.Jwt;

import com.expertpeople.infra.jwt.JwtTokenProvider;
import com.expertpeople.infra.jwt.JwtTokenUtil;
import com.expertpeople.infra.jwt.JwtUserDetailService;
import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailService jwtUserDetailService;
    private final JwtTokenUtil  jwtTokenUtil;
    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    public void authenticate(Account account) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        account.getEmail(),
                        account.getPassword()
                    ));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("잘못된 패스워드 입니다.");
        }
    }

    public JwtResponse testGetJwtResponse(boolean isInitLogin) throws Exception {
        Account account= Account.builder().
                email("uiwv29l@naver.com")
                .password("wnsvaf309")
                .build();
        Account account1=accountRepository.save(account);
        authenticate(account1);
        final UserDetails userDetails=jwtUserDetailService.loadUserByUsername(account1.getEmail());
        //final String token=jwtTokenUtil.generateToken(userDetails);
        final String token=jwtTokenProvider.generateToken(userDetails);

        JwtResponse jwtResponse;

        if(isInitLogin){
            Account login=accountRepository.findByEmail(account.getEmail());
            jwtResponse=getJwtResponse(login,token);
        }else{
            jwtResponse = getJwtResponse(account, token);
        }

        return jwtResponse;
    }

    public JwtResponse getJwtResponse(Account account,boolean isInitLogin) throws Exception {
        authenticate(account);
        final UserDetails userDetails=jwtUserDetailService.loadUserByUsername(account.getEmail());
        //final String token=jwtTokenUtil.generateToken(userDetails);
        final String token=jwtTokenProvider.generateToken(userDetails);

        JwtResponse jwtResponse;

        if(isInitLogin){
            Account login=accountRepository.findByEmail(account.getEmail());
            jwtResponse=getJwtResponse(login,token);
        }else{
           jwtResponse = getJwtResponse(account, token);
        }

        return jwtResponse;
    }

    private JwtResponse getJwtResponse(Account account, String token) {
        JwtResponse jwtResponse=JwtResponse.builder()
                .id(account.getEmail())
                .name(account.getName())
                .profileImage(account.getProfileImage())
                .token(token)
                .userId(account.getId())
                .build();
        return jwtResponse;
    }

    public Account getAccount(JwtRequest authenticationRequest) {
        Account checkLogin=Account.builder()
                .email(authenticationRequest.getEmail())
                .password(authenticationRequest.getPassword())
                .build();
        return checkLogin;
    }
}
