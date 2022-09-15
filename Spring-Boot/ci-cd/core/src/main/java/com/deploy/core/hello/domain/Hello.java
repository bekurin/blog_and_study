package com.deploy.core.hello.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hello {
    @Id @GeneratedValue
    private Long id;
    private String sender;
    private String message;

    @Builder
    public Hello(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }
}
