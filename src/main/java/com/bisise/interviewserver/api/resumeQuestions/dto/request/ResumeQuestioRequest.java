package com.bisise.interviewserver.api.resumeQuestions.dto.request;

import com.bisise.interviewserver.common.types.CategoryType;
import com.bisise.interviewserver.common.types.ExperienceType;

public record ResumeQuestioRequest(
        ExperienceType experienceType,
        CategoryType categoryType
) {
}
