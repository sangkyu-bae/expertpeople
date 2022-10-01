package com.expertpeople.modules.work;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.CurrentAccount;
import com.expertpeople.modules.work.form.WorkForm;
import com.expertpeople.modules.work.validator.WorkFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WorkApiController {

    private final WorkFormValidator workFormValidator;
    private final WorkService workService;
    @InitBinder("workForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(workFormValidator);
    }
    @PostMapping("/work/add")
    public ResponseEntity<?> createWork(@CurrentAccount Account account, @RequestBody @Valid  WorkForm workForm, Errors errors)throws Exception{
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }
        Work work=workService.createWork(account,workForm);

        return ResponseEntity.ok().body(work.getPath());
    }

}
