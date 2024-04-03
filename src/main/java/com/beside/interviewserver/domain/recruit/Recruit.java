package com.beside.interviewserver.domain.recruit;

import com.beside.interviewserver.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "`recruit`")
@Entity
public class Recruit extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruitId;
    private String title;
    private String company;
    private String experienceRange;
    private String deadline;
    private String thumbnail;
    private String positionId;


    public static Recruit createRecruit(String title, String company,String experienceRange,String deadline,String thumbnail,String positionId){
        return  Recruit.builder()
                .title(title)
                .company(company)
                .experienceRange(experienceRange)
                .deadline(deadline)
                .thumbnail(thumbnail)
                .positionId(positionId)
                .build();
    }
}
