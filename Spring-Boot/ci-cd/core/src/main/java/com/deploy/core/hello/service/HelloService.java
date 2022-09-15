package com.deploy.core.hello.service;

import com.deploy.core.hello.domain.Hello;

import java.util.List;

public interface HelloService {
    Hello findByIdOrThrow(Long id);

    List<Hello> findAllOrThrow();

    Long saveOrThrow(Hello entity);
}
