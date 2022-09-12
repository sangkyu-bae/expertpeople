package com.expertpeople.modules.account;

import com.expertpeople.modules.account.form.JoinUpForm;
import com.expertpeople.modules.account.form.PasswordForm;
import com.expertpeople.modules.account.form.Profile;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.job.JobRepository;
import com.expertpeople.modules.zone.Zone;
import com.expertpeople.modules.zone.ZoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService  {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final JobRepository jobRepository;

//    @Override
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//       Account account=accountRepository.findByEmail(email);
//       if(account==null){
//           throw new UsernameNotFoundException(email);
//       }
//
//       return new UserAccount(account);
//    }

    public Account newAccount(JoinUpForm joinUpForm) {
        Account newAccount=saveNewAccount(joinUpForm);
        sandJoinUpEmail(newAccount);
        return newAccount;
    }

    private void sandJoinUpEmail(Account newAccount) {
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(newAccount.getEmail());
        mailMessage.setSubject("expertPeople, 회원가입 인증");
        mailMessage.setText("/check-email-token?token="+newAccount.getEmailCheckToken()+
                "&email="+ newAccount.getEmail());
        javaMailSender.send(mailMessage);
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
                account.getEmail(),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority(account.getRole())));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public void completeSignUp(Account account) {
        account.completeSginUp();
        login(account);
    }

    public void updateAccountProfile(Account account, Profile profile) {
        modelMapper.map(profile,account);
        accountRepository.save(account);
    }

    public Account getAccount(String email) throws Exception{
        Account account=accountRepository.findByEmail(email);

        if(account==null){
            throw new NullPointerException("존재하지 않은 아이디 입니다.");
        }

        return account;
    }

    public void addZone(Account account, Zone zone) {
        Optional<Account> byId=accountRepository.findById(account.getId());
        byId.ifPresent(a ->a.getZone().add(zone));
    }

    public Set<Zone> getZone(Account account) {
        Optional<Account> byId=accountRepository.findById(account.getId());
        return byId.orElseThrow().getZone();
    }

    public void updatePassword(Account account,PasswordForm passwordForm) {
        account.setPassword(passwordEncoder.encode(passwordForm.getPassword()));
        accountRepository.save(account);
    }

    public Set<Job> getJob(Account account) {
        Optional<Account> byId=accountRepository.findById(account.getId());
        return byId.orElseThrow().getJobs();
    }

    public void addJobs(Account account, Job job) {
        Optional<Account> byId=accountRepository.findById(account.getId());
        byId.ifPresent(a->a.getJobs().add(job));
    }

    public void removeJobs(Account account, Job job) {
        Optional<Account> byId=accountRepository.findById(account.getId());
        byId.ifPresent(a->a.getJobs().remove(job));
    }

    public void removeZone(Account account, Zone zone) {
        Optional<Account> byId=accountRepository.findById(account.getId());
        byId.ifPresent(a->a.getZone().remove(zone));
    }
}
