package com.expertpeople.modules.account;

import lombok.*;

import javax.persistence.*;

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

    private String password;

    private boolean emailVerified;

    private String emailCheckToken;
    //자기소개
    private String bio;

    private String url;
    //수정필요 일
    private String job;
    //경력
    private int career;
    //주소
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
}
