package com.deploy.core.hello.service;

import com.deploy.core.hello.domain.Hello;
import com.deploy.core.hello.repository.HelloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HelloServiceV1 implements HelloService {

    private final HelloRepository helloRepository;

    @Override
    public Hello findByIdOrThrow(Long id) {
        return helloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id에 맞는 결과를 찾을 수 없습니다"));
    }

    @Override
    public List<Hello> findAllOrThrow() {
        return helloRepository.findAll();
    }

    @Override
    @Transactional
    public Long saveOrThrow(Hello entity) {
        Hello save = helloRepository.save(entity);
        return save.getId();
    }
}
