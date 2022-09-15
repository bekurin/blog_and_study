package com.deploy.core.hello.dto;

import com.deploy.core.hello.domain.Hello;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HelloRequestDto {
    private String sender;
    private String message;

    public HelloRequestDto(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public Hello toEntity() {
        return Hello.builder()
                .sender(sender)
                .message(message)
                .build();

    }
}
