package com.deploy.core.hello.controller;

import com.deploy.core.hello.dto.HelloRequestDto;
import com.deploy.core.hello.dto.HelloResponseDto;
import com.deploy.core.hello.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class HelloControllerV1 implements HelloController {

    private final HelloService helloService;

    @Override
    @GetMapping("/find/{id}")
    public ResponseEntity<HelloResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(new HelloResponseDto(helloService.findByIdOrThrow(id)));
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<HelloResponseDto> create(@RequestBody HelloRequestDto dto) {
        Long id = helloService.saveOrThrow(dto.toEntity());
        return ResponseEntity.status(201)
                .body(new HelloResponseDto(helloService.findByIdOrThrow(id)));
    }
}
