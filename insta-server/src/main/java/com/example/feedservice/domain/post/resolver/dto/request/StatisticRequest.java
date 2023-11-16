package com.example.feedservice.domain.post.resolver.dto.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticRequest {
    private String hashTag;
    private String types;
    private String values;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
