package com.bisise.interviewserver.common.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public record PageableRequest(
        int page,
        int size
){
    public Pageable toPageable() {
        return PageRequest.of(this.page, this.size);
    }
}
