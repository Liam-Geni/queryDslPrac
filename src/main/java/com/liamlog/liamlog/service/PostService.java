package com.liamlog.liamlog.service;

import com.liamlog.liamlog.domain.Post;
import com.liamlog.liamlog.dto.request.PostCreateDto;
import com.liamlog.liamlog.dto.request.PostSearch;
import com.liamlog.liamlog.dto.response.PostResponseDto;
import com.liamlog.liamlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    //생성자 인덱션
    private final PostRepository postRepository;


    public void write(PostCreateDto postCreateDto){
        // postCreateDto -> Entity로 변환 postCreateDto는 entity가 아니기때문에 db에 저장이 안됨
        Post post = Post.builder()
                .title(postCreateDto.getTitle())
                .content(postCreateDto.getContent())
                .build();

        postRepository.save(post);
    }

    public PostResponseDto get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("존재 하지 않는 글 아이디 입니다.")
                );
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
////    방법1  Builder
//    public List<PostResponseDto> getList() {
//        return postRepository.findAll().stream()
//                .map(post -> {
//                  return   PostResponseDto.builder()
//                            .id(post.getId())
//                            .title(post.getTitle())
//                            .content(post.getContent())
//                            .build();
//                })
//                .collect(Collectors.toList());
//    }

//    방법2 짧은 표현 생성자 오버로딩

    //문제점
    // 1. 글이  너무 많은 경우 -> 비용이 너무 많이 든다.
    // 2. 글이 -> 100,000, 000 -> DB글 모두 조회하는 경우 -> DB가 뻗을 수 있다.
    // DB -> 애플리케이션 서버로 전달하는 시간, 트래픽비용 등이 많이 발생할 수 있다.
    // 해결 방법: 페이징 처리
//    public List<PostResponseDto> getList() {
//        return postRepository.findAll().stream()
//                .map(PostResponseDto::new)
//                .collect(Collectors.toList());
//    }
    public List<PostResponseDto> getList(PostSearch postSearch){
        return postRepository.getList(postSearch).stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }
}

