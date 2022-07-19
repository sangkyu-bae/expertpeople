package com.expertpeople.modules.main;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.CurrentAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String main(@CurrentAccount Account account, Model model){

        if(account!=null){
            model.addAttribute(account);
        }

        return "index";
    }

}
