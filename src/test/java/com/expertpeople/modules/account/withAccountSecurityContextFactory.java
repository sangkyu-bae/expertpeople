package com.expertpeople.modules.account;

import com.expertpeople.WithAccount;
import com.expertpeople.infra.jwt.JwtUserDetailService;
import com.expertpeople.modules.Jwt.JwtResponse;
import com.expertpeople.modules.Jwt.JwtService;
import com.expertpeople.modules.account.form.JoinUpForm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class withAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {
    private final JwtUserDetailService jwtUserDetailService;

    private final JwtService jwtService;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;

    @Override
    public SecurityContext createSecurityContext(WithAccount withAccount) {
        String email=withAccount.value();

        JoinUpForm joinUpForm=new JoinUpForm();
        joinUpForm.setNickname("sangkyu");
        joinUpForm.setEmail(email);
        joinUpForm.setName("배상규");
        joinUpForm.setPassword("wnsvaf309");
        Account account=this.saveNewAccount(joinUpForm);

        UserDetails userDetails=jwtUserDetailService.loadUserByUsername(email);
        Authentication authentication=new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities());
        SecurityContext securityContext= SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);

        return securityContext;
    }
    private Account saveNewAccount(JoinUpForm joinUpForm) {
        joinUpForm.setPassword(passwordEncoder.encode(joinUpForm.getPassword()));
        Account account=modelMapper.map(joinUpForm,Account.class);
        account.setRole("ROLE_USER");
        account.createEmailCheckToken();

        return accountRepository.save(account);
    }
}
