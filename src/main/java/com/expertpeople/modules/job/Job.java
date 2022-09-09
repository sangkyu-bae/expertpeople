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

    @Override
    public String toString(){
        String changeCarrer="";
        if(this.carrer.name().equals("NORMAL")){
            changeCarrer="일반";
        }else {
            changeCarrer="기술공";
        }
        return String.format("%s(%s)",job,changeCarrer);
    }
}
