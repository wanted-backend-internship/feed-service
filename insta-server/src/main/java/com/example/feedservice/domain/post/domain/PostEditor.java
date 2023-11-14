package com.example.feedservice.domain.post.domain;

import lombok.Getter;

@Getter
public class PostEditor {
    private String title;
    private String description;

    public PostEditor (String title, String description) {
        this.title = title;
        this.description = description;
    }

    public static PostEditorBuilder builder() {
        return new PostEditorBuilder();
    }

    public static class PostEditorBuilder {
        private String title;
        private String description;

        PostEditorBuilder() {
        }

        public PostEditorBuilder title(final String title) {
            if (title != null && !title.isEmpty()) {
                this.title = title;
            }
            return this;
        }

        public PostEditorBuilder description(final String description) {
            if (description != null && !description.isEmpty()) {
                this.description = description;
            }
            return this;
        }

        public PostEditor build() {
            return new PostEditor(title, description);
        }
    }
}
