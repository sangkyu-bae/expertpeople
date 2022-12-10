package com.expertpeople.modules.enrollment.Vo;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.enrollment.Enrollment;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.recruitmentGroup.EventType;
import com.expertpeople.modules.recruitmentGroup.Recruitment;
import com.expertpeople.modules.work.Work;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class RecruitmentVo {

    private Long id;

    private String workPath;

    private String title;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime endDateTime;

    private Long recruitId;

    private String workDescription;

    public RecruitmentVo(Enrollment enrollment){
        Recruitment recruitment=enrollment.getRecruitment();
        this.id=recruitment.getId();
        this.workPath=recruitment.getWork().getPath();
        this.title=recruitment.getTitle();
        this.description=recruitment.getDescription();
        this.createTime=recruitment.getCreateTime();
        this.endDateTime=recruitment.getEndDateTime();
        this.recruitId=recruitment.getId();
        this.workDescription=recruitment.getWork().getShortDescription();
    }

}
