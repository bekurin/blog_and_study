package com.example.springboot.web.dto;

import com.example.springboot.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts Entity){
        this.id = Entity.getId();
        this.title = Entity.getTitle();
        this.content = Entity.getContent();
        this.author = Entity.getAuthor();
    }
}
