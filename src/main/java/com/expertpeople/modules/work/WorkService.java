package com.expertpeople.modules.work;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.AccountRepository;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.job.JobsRepository;
import com.expertpeople.modules.work.Event.WorkCreatedEvent;
import com.expertpeople.modules.work.Event.WorkUpdateEvent;
import com.expertpeople.modules.work.Vo.WorkVo;
import com.expertpeople.modules.work.form.WorkDescriptionForm;
import com.expertpeople.modules.work.form.WorkForm;
import com.expertpeople.modules.zone.Zone;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WorkService {

    private final ModelMapper modelMapper;
    private final WorkRepository workRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final AccountRepository accountRepository;
    private final JobsRepository jobsRepository;



    public Work create(Work work){
        Account account=accountRepository.findByEmail("uiwv29l@naver.com");

        Work newWork= workRepository.save(work);
        work.addManager(account);
        Job test= jobsRepository.findByJob("석공");
        newWork.getJobs().add(test);
        return work;
    }
    public Work createWork(Account account, WorkForm workForm) {
        Work work=modelMapper.map(workForm,Work.class);
        workRepository.save(work);
        work.addManager(account);
        return work;
    }

    public Work getWork(String path) {
        Work work=this.workRepository.findByPath(path);
        checkExistWork(work);
        return work;
    }

    public void updateWorkDescription(WorkDescriptionForm workDescriptionForm, Work work) {
        modelMapper.map(workDescriptionForm,work);
        eventPublisher.publishEvent(new WorkUpdateEvent(work,"일감 소개를 수정했습니다."));
    }

    public Work getWorkToUpdate(Account account, String path) {
        Work work=this.getWork(path);
        checkManager(account,work);
        return work;
    }


    public Set<Job> getJob(String path, Account account) {
        Work work=this.getWorkWithJob(path);
        checkManager(account, work);
        return work.getJobs();
    }

    private static void checkManager(Account account, Work work) {
        if(!account.isManagerOf(work)){
            throw new AccessDeniedException("해당 기능을 사용할 권한이 없습니다.");
        }
    }

    private Work getWorkWithJob(String path){
        Work work=workRepository.findWorkWithJobsByPath(path);
        checkExistWork(work);
        return work;
    }
    public void addJobs(Account account, Job job, String path) {
        Work work=this.getWorkWithJob(path);
        checkManager(account,work);
        work.getJobs().add(job);
    }

    public void removeJobs(Account account, Job job, String path) {
        Work work=this.getWorkWithJob(path);
        checkManager(account,work);
        work.getJobs().remove(job);
    }

    private Work getWorkWithZone(String path){
        Work work=workRepository.findWorkWithZonesByPath(path);
        checkExistWork(work);
        return work;
    }

    private static void checkExistWork(Work work) {
        if(work ==null){
            throw new IllegalArgumentException("존재하지 않은 경로 입니다.");
        }
    }

    public Set<Zone> getZone(Account account, String path) {
        Work work=this.getWorkWithZone(path);
        checkManager(account,work);
        return work.getZones();
    }

    public void addZone(Account account, Zone zone, String path) {
        Work work=this.getWorkWithZone(path);
        checkManager(account,work);
        work.getZones().add(zone);
    }

    public void removeZone(Account account, Zone zone, String path) {
        Work work=this.getWorkWithZone(path);
        checkManager(account,work);
        work.getZones().remove(zone);
    }

    public Work getWorkToUpdateStatus(Account account,String path) {
        Work work=workRepository.findWorkWithManagersByPath(path);
        checkManager(account,work);
        checkExistWork(work);
        return work;
    }

    public void publish(Work work) {
        work.publish();
        this.eventPublisher.publishEvent(new WorkCreatedEvent(work));
    }

    public boolean isValidPath(String newPath) {
        if(!newPath.matches(WorkForm.VALID_PATH_PATTERN)){
            return false;
        }
        return !workRepository.existsByPath(newPath);
    }

    public void updateWorkUrl(Work work,String newPath) {
        work.setPath(newPath);
    }

    public void close(Work work) {
        work.close();
        eventPublisher.publishEvent(new WorkUpdateEvent(work,"일감을 종료했습니다."));
    }

    public void startRecruit(Work work) {
        work.startRecruit();
        eventPublisher.publishEvent(new WorkUpdateEvent(work,"구인을 시작 했습니다. "));
    }

    public void stopRecruit(Work work) {
        work.stopRecruit();
        eventPublisher.publishEvent(new WorkUpdateEvent(work,"구인을 종료 했습니다. "));
    }

    public void updateWorkTitle(Work work,String title) {
        work.setTitle(title);
        eventPublisher.publishEvent(new WorkUpdateEvent(work,"일감 제목이 변경되었습니다."));
    }

    public boolean isValidTitle(String title) {
        return title.length()<=50;
    }

    public void removeWork(Work work) {
        if(work.isRemovable()){
            workRepository.delete(work);
        }else{
            throw new IllegalArgumentException("일감을 삭제할 수 없습니다");
        }
    }

    public Work getWorkToUpdateMember(Account account, String path) {
        Work work=workRepository.findWorkWithMembersByPath(path);
        checkMember(account,work);
        checkExistWork(work);
        return work;
    }

    private void checkMember(Account account, Work work) {

    }

    public void addMember(Account account, Work work) {
        work.addMamber(account);
    }

    public void removeMember(Account account, Work work) {
        work.getMembers().remove(account);
    }


    public WorkVo convertWorkVo(Work work) {
        WorkVo workVo=modelMapper.map(work,WorkVo.class);
        Set<WorkVo.Accounts> managers=new HashSet<>();
        Set<WorkVo.Accounts> members=new HashSet<>();
        for (Account account:work.getManagers()){
            managers.add(new WorkVo.Accounts(account));
        }
        for(Account account:work.getMembers()){
            members.add(new WorkVo.Accounts(account));
        }
        workVo.setManagers(managers);
        workVo.setMembers(members);

        return workVo;
    }

    public void isCheckWork(String path) {
        boolean isWork=workRepository.existsByPath(path);
        if(!isWork){
            throw new IllegalArgumentException("존재하지 않은 일감의 경로입니다.");
        }
    }
}
