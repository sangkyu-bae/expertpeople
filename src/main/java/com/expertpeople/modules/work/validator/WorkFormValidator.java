package com.expertpeople.modules.work.validator;

import com.expertpeople.modules.work.WorkRepository;
import com.expertpeople.modules.work.form.WorkForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class WorkFormValidator implements Validator {


    private final WorkRepository workRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(WorkForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WorkForm workForm=(WorkForm) target;

        if(workRepository.existsByPath(workForm.getPath())){
            errors.rejectValue("path","invalid.path",new Object[]{workForm.getPath()},"이미 존재하는 경로 입니다.");
        }

    }
}
