package com.expertpeople.modules.work;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.work.form.WorkDescriptionForm;
import com.expertpeople.modules.work.form.WorkForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WorkService {

    private final ModelMapper modelMapper;
    private final WorkRepository workRepository;
    public Work createWork(Account account, WorkForm workForm) {
        Work work=modelMapper.map(workForm,Work.class);
        workRepository.save(work);
        work.addManager(account);
        return work;
    }

    public Work getWork(String path) {
        Work work=this.workRepository.findByPath(path);

        if(work==null){
            throw new NullPointerException("존재하지 않은 경로 입니다.");
        }

        return work;
    }

    public void updateWorkDescription(WorkDescriptionForm workDescriptionForm, String path) {
        Work work=workRepository.findByPath(path);
        if(work==null){
            throw new NullPointerException("존재하지 않은 경로 입니다.");
        }
        modelMapper.map(workDescriptionForm,work);
        workRepository.save(work);
    }
}
