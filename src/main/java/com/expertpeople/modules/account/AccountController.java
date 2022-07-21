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

    @InitBinder("joinUpForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(joinUpFormValidator);
    }

    @GetMapping("/join-up")
    public String joinUpFrom(@CurrentAccount Account account, Model model){

        if(account!=null){
            return "redirect:/";
        }
        model.addAttribute("joinUpForm",new JoinUpForm());

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

}
