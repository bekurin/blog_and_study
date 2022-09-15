package com.deploy.core.hello.dto;

import com.deploy.core.hello.domain.Hello;
import lombok.Getter;

@Getter
public class HelloResponseDto {
    private Long id;
    private String sender;
    private String message;

    public HelloResponseDto(Hello entity) {
        id = entity.getId();
        sender = entity.getSender();
        message = entity.getMessage();
    }
}
