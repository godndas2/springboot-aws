package org.sb.aws.rest.dto;

import lombok.Getter;
import org.sb.aws.entity.posts.Posts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
public class PostsListResponseDto {

    private Long id;
    private String title;
    private String author;
    private String modifiedDate; //

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}

