package com.expertpeople.modules.enrollment;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.recruitmentGroup.Recruitment;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NamedEntityGraph(
        name = "Enrollment.withEventAndWork",
        attributeNodes = {
                @NamedAttributeNode(value = "recruitment",subgraph = "work")
        },
        subgraphs = @NamedSubgraph(name = "work",attributeNodes = @NamedAttributeNode("work"))
)
@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@AllArgsConstructor @NoArgsConstructor @Builder
public class Enrollment {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    //@JsonManagedReference
    private Recruitment recruitment;

    @ManyToOne
    private Account account;

    private LocalDateTime enrolledAt;

    private boolean accepted;

    private boolean attended;

}
