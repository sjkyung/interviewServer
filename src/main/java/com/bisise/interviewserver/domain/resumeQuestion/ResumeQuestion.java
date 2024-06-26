package com.bisise.interviewserver.domain.resumeQuestion;


import com.bisise.interviewserver.common.types.CategoryType;
import com.bisise.interviewserver.common.types.ExperienceType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "`resume_question`")
@Entity
public class ResumeQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeQuestionsId;
    private String title;
    private String subTitle;
    @Enumerated(EnumType.STRING)
    private ExperienceType experienceRange;
    @Enumerated(EnumType.STRING)
    private CategoryType category;

    public static ResumeQuestion createResumeQuestion(String title, String subTitle, ExperienceType experienceRange, CategoryType category){
        return  ResumeQuestion.builder()
                .title(title)
                .subTitle(subTitle)
                .experienceRange(experienceRange)
                .category(category)
                .build();
    }
}
