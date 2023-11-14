package com.example.feedservice.domain.post.domain;

import com.example.feedservice.domain.hashtag.HashTag;
import com.example.feedservice.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String title;
    private String description;
    private Long viewCount;
    private Long heartCount;
    private Long shareCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HashTag> hashTags = new ArrayList<>();

    public void mappingHashTag(HashTag hashTag) {
        hashTags.add(hashTag);
    }

    public PostEditor.PostEditorBuilder toUpdate() {
        return PostEditor.builder()
                .title(title)
                .description(description);
    }

    public void update (PostEditor postEditor) {
        title = postEditor.getTitle();
        description = postEditor.getDescription();
    }
}
