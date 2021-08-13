package com.example.springboot.service.posts;

import com.example.springboot.web.dto.PostsSaveRequestsDto;
import com.example.springboot.domain.posts.PostsRepository;
import com.example.springboot.domain.posts.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestsDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
