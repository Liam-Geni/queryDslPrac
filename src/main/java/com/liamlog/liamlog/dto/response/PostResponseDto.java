package com.liamlog.liamlog.dto.response;


import com.liamlog.liamlog.domain.Post;
import lombok.Builder;
import lombok.Getter;


/*
* 서비스 정책에 맞는 클래스
* */
@Getter

public class PostResponseDto {
    private Long id;
    private String title;
    private String content;

    //생성자 오버로딩
    public PostResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
    @Builder
    public PostResponseDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
