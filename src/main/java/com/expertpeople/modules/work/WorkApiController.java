package com.expertpeople.modules.work;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.CurrentAccount;
import com.expertpeople.modules.work.Vo.WorkVo;
import com.expertpeople.modules.work.form.WorkForm;
import com.expertpeople.modules.work.validator.WorkFormValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
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

    @GetMapping("/work/{path}")
    public ResponseEntity<?> getWork(@CurrentAccount Account account, @PathVariable String path){
        Work work=workService.getWork(path);
        boolean isManager=work.isManager(account);
        boolean isMember=work.isMember(account);
        boolean isJoinable=work.isJoinable(account);

        WorkVo workVo=workService.convertWorkVo(work);
        return ResponseEntity.ok().body(new WorkResult<>(workVo,isManager,isMember,isJoinable));
        //return ResponseEntity.ok().body(new WorkResult<>(work,isManager,isMember,isJoinable));
    }

    @Getter
    @Setter
    static class WorkResult<T>{
        private T work;
        private T isManager;
        private T isMember;
        private T isJoinable;
        public WorkResult(T work,T isManager,T isMember,T isJoinable){
            this.work=work;
            this.isManager=isManager;
            this.isMember=isMember;
            this.isJoinable=isJoinable;
        }
    }
}
