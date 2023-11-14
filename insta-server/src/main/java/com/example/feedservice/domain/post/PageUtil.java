package com.example.feedservice.domain.post;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
public class PageUtil<T> {
    private List<T> content;
    private int currentPageNumber;
    private int totalPageNumber;

    public PageUtil(List<T> content, Pageable pageable, int totalPageNumber) {
        this.content = content;
        this.currentPageNumber = pageable.getPageNumber();
        this.totalPageNumber = totalPageNumber;
    }
}
