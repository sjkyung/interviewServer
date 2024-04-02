package com.beside.interviewserver.domain.resume;

import com.beside.interviewserver.domain.common.BaseEntity;
import com.beside.interviewserver.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.parameters.P;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "resume")
@Entity
public class Resume extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(length = 2000)
    private String question;
    @Column(length = 2000)
    private String answer;
    @Column(length = 2000)
    private String ai_answer;

    public static Resume createResume(User user, String question, String answer,String ai_answer){
        Resume resume = Resume.builder()
                .user(user)
                .question(question)
                .answer(answer)
                .ai_answer(ai_answer)
                .build();
        return  resume;
    }
}
