package com.expertpeople.modules.recruitmentGroup;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.enrollment.Enrollment;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.work.Work;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
@EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Recruitment {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Work work;

    @ManyToOne
    private Account createBy;

    @ManyToOne
    private Job job;

    @Column(nullable = false)
    private String title;

    @Lob
    private String description;

    @Column(nullable = false)
    private LocalDateTime createTime;

    @Column(nullable = false)
    private LocalDateTime endEnrollmentDateTime;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Column(nullable = true)
    private Integer limitOfEnrollments;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "recruitment")
    private List<Enrollment> erollments;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    public boolean isManager(Account account) {
        return this.createBy.equals(account);
    }

    public boolean isAbleToAccptWaitingEnrollment() {
        return this.eventType==EventType.FCFS&&this.limitOfEnrollments>this.getNumberOfAcceptedEnrollments();
    }

    private long getNumberOfAcceptedEnrollments() {
        return this.erollments.stream().filter(Enrollment::isAccepted).count();
    }

    public void addEnrollment(Enrollment enrollment) {
        this.erollments.add(enrollment);
        enrollment.setRecruitment(this);
    }

    public boolean isEnrollment(Account account) {
        for(Enrollment e:this.erollments){
            if(e.getAccount().equals(account)&&e.isAttended()) return true;
        }
        return false;
    }

    public int numberOfRemainSpot(){
        int acceptCount=0;
        for (Enrollment enrollment:this.erollments){
            if(enrollment.isAccepted()){
                acceptCount++;
            }
        }
        return this.limitOfEnrollments-acceptCount;
    }

    public void acceptEnrollmentList() {
        int remainAcceptCount= numberOfRemainSpot();
        if(isCheckRemainEnrollment(remainAcceptCount)){
            List<Enrollment> waitingList= getWaitingList();
            if(!waitingList.isEmpty()){
                waitingList.subList(0,remainAcceptCount).forEach(e->e.setAccepted(true));
            }
        }
    }

    private boolean isCheckRemainEnrollment(int remainAcceptCount) {
        return remainAcceptCount > 0 && this.eventType == EventType.FCFS;
    }

    private List<Enrollment> getWaitingList() {
        return this.erollments.stream().filter(enrollment -> !enrollment.isAttended()).collect(Collectors.toList());
    }

    public void acceptEnrollment(Enrollment enrollment) {
        checkContainEnrollment(enrollment);
        if(isCheckRemainAndComfirmative()){
            enrollment.setAccepted(true);
        }
    }

    private void checkContainEnrollment(Enrollment enrollment) {
        if(this.erollments.contains(enrollment)){
            throw new IllegalArgumentException("일감 참가 대기중인 신청자가 아닙니다.");
        }
    }

    private boolean isCheckRemainAndComfirmative() {
        return numberOfRemainSpot() > 0 && this.eventType == EventType.COMFIRMATIVE;
    }

    public void rejectEnrollment(Enrollment enrollment) {
        checkContainEnrollment(enrollment);
        if(this.eventType == EventType.COMFIRMATIVE){
            enrollment.setAccepted(false);
        }
    }

    public void acceptAttend(Enrollment enrollment) {
        if(!enrollment.isAttended()&&isCheckAttend(enrollment)){
            enrollment.setAttended(true);
        }
    }

    private boolean isCheckAttend(Enrollment enrollment) {
        return this.erollments.contains(enrollment)&&enrollment.isAccepted();
    }

    public void cancelAttend(Enrollment enrollment) {
        if(enrollment.isAttended()&&isCheckAttend(enrollment)){
            enrollment.setAttended(false);
        }
    }
}
