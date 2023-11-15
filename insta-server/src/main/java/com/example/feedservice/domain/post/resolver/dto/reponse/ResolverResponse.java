package com.example.feedservice.domain.post.resolver.dto.reponse;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ResolverResponse {
    private LocalDateTime localDateTime;
    private String date;
    private String hour;
    private Long countOfPost;
    private Long countOfHeart;
    private Long countOfView;
    private Long countOfShare;
}
