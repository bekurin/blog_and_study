package com.deploy.core.hello.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloTest {

    @Test
    void createSuccessTest() {
        //given
        String sender = "hangman";
        String message = "Welcome to API test";

        //when
        Hello hello = new Hello(sender, message);

        //then
        assertThat(hello.getSender()).isEqualTo(sender);
        assertThat(hello.getMessage()).isEqualTo(message);
    }
}