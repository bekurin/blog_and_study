package com.deploy.core.hello.controller;

import com.deploy.core.hello.dto.HelloRequestDto;
import com.deploy.core.hello.dto.HelloResponseDto;
import org.springframework.http.ResponseEntity;

public interface HelloController {
    ResponseEntity<HelloResponseDto> create(HelloRequestDto dto);
    ResponseEntity<HelloResponseDto> findById(Long id);
}
