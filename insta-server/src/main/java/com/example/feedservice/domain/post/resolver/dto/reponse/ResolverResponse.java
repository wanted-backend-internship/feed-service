package com.example.feedservice.domain.post.resolver.dto.reponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResolverResponse {
    private Date dateOnly;
    private Long dynamicValue;
}
