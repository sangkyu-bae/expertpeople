package com.expertpeople.modules.recruitmentGroup;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.enrollment.Enrollment;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.work.Work;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@NoArgsConstructor
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
    //@JsonBackReference
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
}
