package com.expertpeople.modules.job;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@AllArgsConstructor @NoArgsConstructor @Builder
public class Job {
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String job;

    @Column(nullable = false)
    private String averagePrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Carrer carrer;
}
