package com.expertpeople.modules.account;

import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.work.Work;
import com.expertpeople.modules.zone.Zone;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account {


    @Id @GeneratedValue
    private Long id;

    @Column
    private String email;

    @Column
    private String nickname;

    private String name;
    //주소
    private String address;

    private String role;

    private String password;
    private boolean emailVerified;
    private String emailCheckToken;
    //자기소개
    @Lob @Basic(fetch = FetchType.EAGER)
    private String bio;
    private LocalDateTime joinAt;
    //수정필요 일
    private String job;
    //경력
    private int career;
    //원하는 지역
    private String location;
    //핸드폰
    private String phone;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    private boolean workCreateByEmail;

    private boolean workCreateByWeb=true;

    private boolean workEnrollmentResultByEmail;

    private boolean workEnrollmentResultByWeb;

    private boolean workUpdateByEmail;

    private boolean workUpdateByWeb;
    //자기소개 오픈 여부
    private boolean openBio;
    @ManyToMany
    private Set<Zone> zone =new HashSet<>();
    //직업 태그
    @ManyToMany
    private Set<Job> jobs =new HashSet<>();


    public void createEmailCheckToken() {
        this.emailCheckToken= UUID.randomUUID().toString();
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public void completeSginUp() {
        this.emailVerified=true;
        this.joinAt=LocalDateTime.now();
    }

    public boolean isManagerOf(Work work) {
        return work.getManagers().contains(this);
    }
    public boolean isMemberOf(Work work){
        return work.getMembers().contains(this);
    }
}
