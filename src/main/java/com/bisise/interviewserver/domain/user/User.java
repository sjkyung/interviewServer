package com.bisise.interviewserver.domain.user;

import com.bisise.interviewserver.domain.interview.Interview;
import com.bisise.interviewserver.domain.resume.Resume;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String refreshToken;

    @Builder.Default
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Interview> interviews = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Resume> resumes = new ArrayList<>();


    public static User createUser(String email){
        return  User.builder()
                .email(email)
                .build();
    }

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }


}
