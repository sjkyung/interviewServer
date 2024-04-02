package com.bisise.interviewserver.domain.user;


import com.bisise.interviewserver.domain.interview.Interview;
import com.bisise.interviewserver.domain.resume.Resume;
import com.bisise.interviewserver.common.types.PlatformType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//USER Entity 구성
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "`user`")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String nick;
    private String careerExperience;
    private String jobPosition;
    private String platformId;
    @Enumerated(EnumType.STRING)
    private PlatformType platform;
    private String refreshToken;

    @Builder.Default
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Interview> interviews = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Resume> resumes = new ArrayList<>();

    private String email;

    public static User createUser(String nick,String careerExperience,String jobPosition,String platformId,PlatformType platform){
        return  User.builder()
                .nick(nick)
                .careerExperience(careerExperience)
                .jobPosition(jobPosition)
                .platformId(platformId)
                .platform(platform)
                .build();
    }
    public static User createSocialUser(String email,String platformId){
        return  User.builder()
                .email(email)
                .platformId(platformId)
                .build();
    }
    public static User createById(Long id) {
        return User.builder()
                .userId(id)
                .build();
    }

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
