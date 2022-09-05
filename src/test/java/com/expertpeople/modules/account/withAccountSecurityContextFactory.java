package com.expertpeople.modules.account;

import com.expertpeople.WithAccount;
import com.expertpeople.infra.jwt.JwtUserDetailService;
import com.expertpeople.modules.Jwt.JwtResponse;
import com.expertpeople.modules.Jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class withAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {
    private final JwtUserDetailService jwtUserDetailService;

    private final JwtService jwtService;

    @Override
    public SecurityContext createSecurityContext(WithAccount withAccount) {
        String email=withAccount.value();

        Account account= Account.builder().
                email("uiwv29l@naver.com")
                .password("wnsvaf309")
                .build();
        JwtResponse jwtResponse= null;
        try {
            jwtResponse = jwtService.testGetJwtResponse(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //token=jwtResponse.getToken();

        UserDetails userDetails=jwtUserDetailService.loadUserByUsername(email);
        Authentication authentication=new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities());
        SecurityContext securityContext= SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);

        return securityContext;
    }
}
