package com.liamlog.liamlog.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Setter
@Getter
public class PostCreateDto {

    @NotBlank(message = "타이틀을 입력해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    @Builder
    public PostCreateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //빌더의 장점
    // - 가독성에 좋다.
    // - 필요한 값만 받을 수 있다. //
    // - 객체이 불변성
}
