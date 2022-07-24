package com.expertpeople.modules.account;

import com.expertpeople.modules.account.form.Profile;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AccountSettingController {

    private static final String SETTING_PROFILE_URL="/setting/profile";

    private static final String SETTING_PROFILE_VIEW="setting/profile";
    private final ModelMapper modelMapper;

    private final AccountService accountService;
    @GetMapping(SETTING_PROFILE_URL)
    public String updateProfileForm(@CurrentAccount Account account, Model model){
        model.addAttribute(account);
        model.addAttribute(modelMapper.map(account, Profile.class));
        return SETTING_PROFILE_VIEW;
    }

    @PostMapping(SETTING_PROFILE_URL)
    public String updateProfile(@CurrentAccount Account account, RedirectAttributes attributes,Model model,
                                @Valid Profile profile, Errors errors){
        if(errors.hasErrors()){
            model.addAttribute(account);
            return SETTING_PROFILE_VIEW;
        }

        accountService.updateAccountProfile(account,profile);

        attributes.addFlashAttribute("message","프로필을 수정 했습니다.");

        return "redirect:"+SETTING_PROFILE_URL;
    }

}
