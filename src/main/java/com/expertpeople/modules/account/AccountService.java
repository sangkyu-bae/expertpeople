package com.expertpeople.modules.account;

import com.expertpeople.modules.account.form.JoinUpForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

       Account account=accountRepository.findByEmail(email);
       if(account==null){
           throw new UsernameNotFoundException(email);
       }

       return new UserAccount(account);
    }

    public Account newAccount(JoinUpForm joinUpForm) {
        Account newAccount=saveNewAccount(joinUpForm);

        return newAccount;
    }

    private Account saveNewAccount(JoinUpForm joinUpForm) {
        joinUpForm.setPassword(passwordEncoder.encode(joinUpForm.getPassword()));
        Account account=modelMapper.map(joinUpForm,Account.class);
        account.setRole("ROLE_USER");
        account.createEmailCheckToken();

        return accountRepository.save(account);
    }

    public void login(Account account) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(account),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
