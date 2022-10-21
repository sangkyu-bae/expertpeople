package com.expertpeople.modules.work;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.work.form.WorkDescriptionForm;
import com.expertpeople.modules.work.form.WorkForm;
import com.expertpeople.modules.zone.Zone;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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
        checkExistWork(work);
        return work;
    }

    public void updateWorkDescription(WorkDescriptionForm workDescriptionForm, Work work) {
        modelMapper.map(workDescriptionForm,work);
    }

    public Work getWorkToUpdate(Account account, String path) {
        Work work=this.getWork(path);
        checkManager(account,work);
        return work;
    }


    public Set<Job> getJob(String path,Account account) {
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
    public void addJobs(Account account, Job job,String path) {
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
    }

    public void startRecruit(Work work) {
        work.startRecruit();
    }

    public void stopRecruit(Work work) {
        work.stopRecruit();
    }

    public void updateWorkTitle(Work work,String title) {
        work.setTitle(title);
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

    public boolean existWork(String path) {
        boolean isWork=workRepository.existsByPath(path);
        return isWork;
    }
}
