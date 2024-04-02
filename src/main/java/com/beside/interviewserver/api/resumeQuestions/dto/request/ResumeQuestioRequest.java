package com.beside.interviewserver.api.resumeQuestions.dto.request;

import com.beside.interviewserver.common.types.CategoryType;
import com.beside.interviewserver.common.types.ExperienceType;

public record ResumeQuestioRequest(
        ExperienceType experienceType,
        CategoryType categoryType
) {
}
