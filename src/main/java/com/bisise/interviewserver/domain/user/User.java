package com.bisise.interviewserver.domain.user;

import jakarta.persistence.*;
import lombok.*;

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
    private Long Id;
    private String email;
    private String refreshToken;



    public static User createUser(String email){
        return  User.builder()
                .email(email)
                .build();
    }

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }


}
