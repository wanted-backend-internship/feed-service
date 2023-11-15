package com.example.feedservice.domain.post.resolver.dto.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StatisticRequest {
    private final String hashTag;
    private final String types;
    private final String values;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
}
