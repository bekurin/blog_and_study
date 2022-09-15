package com.deploy.core.hello.repository;

import com.deploy.core.hello.domain.Hello;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelloRepository extends JpaRepository<Hello, Long> {
}
