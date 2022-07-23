package com.expertpeople.modules.account;

import com.expertpeople.modules.account.form.JoinUpForm;
import com.expertpeople.modules.account.validator.JoinUpFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final JoinUpFormValidator joinUpFormValidator;
    private final AccountRepository accountRepository;
    @InitBinder("joinUpForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(joinUpFormValidator);
    }

    @GetMapping("/join-up")
    public String joinUpFrom(@CurrentAccount Account account, Model model){

        if(account!=null){
            return "redirect:/";
        }
        model.addAttribute(new JoinUpForm());

        return "account/join-up";
    }

    @PostMapping("/join-up")
    public String joinUpSubmit(@Valid JoinUpForm joinUpForm, Errors errors){

        if(errors.hasErrors()){
            return "account/join-up";
        }
        Account account=accountService.newAccount(joinUpForm);
        accountService.login(account);
        return "redirect:/";
    }

    @GetMapping("check-email-token")
    public String checkEmailToken(String token, String email,Model model){
        String view = "account/checked-email";
        Account account=accountRepository.findByEmail(email);
        System.out.println("ddd");
        if(account==null){
            model.addAttribute("error","존재하는 이메일이 아닙니다");
            return view;
        }

        if(!account.isValidToken(token)){
            model.addAttribute("error","토큰 값이 틀립니다.");
            return view;
        }

        accountService.completSignUp(account);

        model.addAttribute("userCount",accountRepository.count());
        model.addAttribute("name",account.getName());

        return view;
    }

}
