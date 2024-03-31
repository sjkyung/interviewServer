package com.bisise.interviewserver.domain.interview;

import com.bisise.interviewserver.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "interview")
@Entity
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(length = 2000)
    private String question;
    @Column(length = 2000)
    private String answer;
    private String pass;

    public static Interview createInterview(User user,String question,String answer,String pass){
        Interview interview = Interview.builder()
                .user(user)
                .question(question)
                .answer(answer)
                .pass(pass)
                .build();

        return interview;
    }
}
