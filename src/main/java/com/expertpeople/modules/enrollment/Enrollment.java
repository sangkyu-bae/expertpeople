package com.expertpeople.modules.enrollment;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.recruitmentGroup.Recruitment;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Enrollment {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Recruitment recruitment;

    @ManyToOne
    private Account account;

    private LocalDateTime enrolledAt;

    private boolean accepted;

    private boolean attended;
}
