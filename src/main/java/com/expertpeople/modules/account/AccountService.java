package com.expertpeople.modules.account;

import com.expertpeople.infra.config.AppProperties;
import com.expertpeople.infra.mail.EmailMessage;
import com.expertpeople.infra.mail.EmailService;
import com.expertpeople.modules.account.form.JoinUpForm;
import com.expertpeople.modules.account.form.Notifications;
import com.expertpeople.modules.account.form.Profile;
import com.expertpeople.modules.account.form.PasswordForm;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.job.JobRepository;
import com.expertpeople.modules.zone.Zone;
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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService  {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private final AppProperties  appProperties;
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
        Context context=new Context();
        context.setVariable("link","/check-email-token?token="+newAccount.getEmailCheckToken()+
                "&email="+ newAccount.getEmail());
        context.setVariable("name",newAccount.getName());
        context.setVariable("linkName","이메일 인증하기");
        context.setVariable("message","expert people를 사용하려면 링크를 클릭하세요");
        context.setVariable("host",appProperties.getHost());


        String message=templateEngine.process("Email/simple-link",context);
        EmailMessage emailMessage=EmailMessage.builder()
                .to(newAccount.getEmail())
                .subject("expertPeople, 회원가입 인증")
                .message(message)
                .build();
        emailService.sendEmail(emailMessage);
    }

//    public void sendLoginLink(Account account){
//        Context context=new Context();
//        context.setVariable("link","/check-email-token?token="+account.getEmailCheckToken()+
//                "&email="+ account.getEmail());
//        context.setVariable("name",account.getName());
//        context.setVariable("linkName","이메일 로그인하기");
//        context.setVariable("message","expert people를 사용하려면 링크를 클릭하세요");
//        context.setVariable("host",appProperties.getHost());
//
//        EmailMessage emailMessage=EmailMessage.builder()
//                .to(account.getEmail())
//                .subject("expertPeople, 로그인 링크")
//                .message("/check-email-token?token="+account.getEmailCheckToken()+
//                        "&email="+ account.getEmail())
//                .build();
//        emailService.sendEmail(emailMessage);
//    }

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

    public void updateNotification(Account account, Notifications notifications) {
        modelMapper.map(notifications,account);
        accountRepository.save(account);
    }
}
