package com.expertpeople.modules.work;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.CurrentAccount;
import com.expertpeople.modules.work.form.WorkDescriptionForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/work/setting/{path}")
public class WorkSettingApiController {

    private final WorkService workService;

    @PutMapping("/description")
    public ResponseEntity<?> updateWorkInfo(@CurrentAccount Account account, @PathVariable String path,
                                            @RequestBody @Valid WorkDescriptionForm workDescriptionForm, Errors errors){
       Work work=workService.getWorkToUpdate(account,path);

        if(errors.hasErrors()){
            return ResponseEntity.badRequest().build();
        }
        workService.updateWorkDescription(workDescriptionForm,work);

        return ResponseEntity.ok().body(path);
    }


}
