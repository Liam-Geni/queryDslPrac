package com.liamlog.liamlog.controller;

import com.liamlog.liamlog.dto.request.PostCreateDto;
import com.liamlog.liamlog.dto.request.PostSearch;
import com.liamlog.liamlog.dto.response.PostResponseDto;
import com.liamlog.liamlog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {


    private final PostService postService;

    // http Method
    //GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    //글 등록
    // POST Method

//    @PostMapping("/posts")
//    public String post(@RequestParam String title ,@RequestParam String content){
//        log.info("title={}, content={}", title, content);
//        return "Hello World";
//    }
//    @PostMapping("/posts")
//    public String post(@RequestParam Map<String, String> params){
//        log.info("params={}", params);
//        return "Hello World";
//    }
    // 데이터를 검증하는 이유

    // 1. client 개발자가 깜박할 수 있다. 실수로 값을 안보낼 수 있다.
    // 2. client bug로 값이 누락될 수 있다.
    // 3. 외부에 나쁜 사람이 값을 임의로 조작해서 보낼수 있다.
    // 4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있다.
//    @PostMapping("/posts")
//    public String post(@RequestBody @Valid PostCreateDto postCreateDto) throws Exception{
//        // 데이터를 검증하는 이유
//
//        // 1. client 개발자가 깜박할 수 있다. 실수로 값을 안보낼 수 있다.
//        // 2. client bug로 값이 누락될 수 있다.
//        // 3. 외부에 나쁜 사람이 값을 임의로 조작해서 보낼수 있다.
//        // 4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있다.
//        // 5. 서버 개발자의 편안함을 위해서
//        log.info("params = {}", postCreateDto.toString());
//        String title = postCreateDto.getTitle();
//        if(null == title || title.equals("")){
//            // 1. 개발팁 -> 무언가 3번이상 반복작업을 할때 내가 뭔가 잘못하고 있는건 아닐지 의심한다.
//            // 2. 누락가능성
//            // 3. 생각보다 검증해야할 게 많다.
//            // 4. 뭔가 개발자스럽지 않음
//            // 5. 해결 valid(@NotBlank)로 spring에서 에러 처리
//            throw new Exception("타이틀 값이 없어요");
//        }
//        String content = postCreateDto.getContent();
//        if(null == content || content.equals("")){
//           throw new Exception("컨테츠의 내용이 없어요 ");
//       }
//        return "Hello World";
//    }
//    @PostMapping("/posts")
//    public Map<String, String> postCreate(@RequestBody @Valid PostCreateDto postCreateDto, BindingResult result){
//        //이방법도 위와 마찬가지로
//        // 1. 개발팁 -> 무언가 3번이상 반복작업을 할때 내가 뭔가 잘못하고 있는건 아닐지 의심한다.
//        // 2. 누락가능성
//        // 3. 생각보다 검증해야할 게 많다.
//        // 4. 뭔가 개발자스럽지 않음
//        // 5. 두번째 해결방법 @ControllerAdvice
//        if(result.hasErrors()){
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            FieldError firstFieldError = fieldErrors.get(0);
//            String fieldName = firstFieldError.getField();
//            String errorMessage = firstFieldError.getDefaultMessage();
//            Map<String, String> error = new HashMap<>();
//            error.put(fieldName, errorMessage);
//            return error;
//        }
//        return Map.of();
//    }
    // 5. 두번째 해결방법 @ControllerAdvice
//    @PostMapping("/posts")
//    public Map<String, String> post(@RequestBody @Valid PostCreateDto postCreateDto){
//        // db.save(postCreateDto)
//        //
//
//        return Map.of();
//    }
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreateDto postCreateDto){
        //Case1. 저장한 데이터 Entity -> response로 응답하기
        //Case2. 저장한 데이터의 primary_id -> response로 응답하기
        //   Client에서는 수신한 id를 post 조회 API를 통해서 글 데이터를 수신받음
        //Case3. 응답 필요 없음 -> 클라이언트에서의 모든 POST(글) 데이터 context를 관리함
        // Bad Case: 서버에서 -> 반드시 이렇게 할껍니다! fix
        // -> 서버에서는 차라리 유연하게 대응하는것이 좋다. -> 코드를 잘 짜야함...
        // -> 한 번에 일괄적으로 잘 처리되는 케이스가 없다. -> 잘 관리하는 형태가 중요

        postService.write(postCreateDto);
    }

    /*
    * /posts -> 글 전체 조회(검색 + 페이징)
    * /posts/{postId} -> 글 한개만 조회
    * */
    @GetMapping("/posts/{postId}")
    public PostResponseDto get(@PathVariable(name = "postId") Long id){
        //RequestDto 클래스
        //ResponseDto 클래스
        return postService.get(id);
    }

    /*
    *  /posts -> 여러개의 글을 조회하는 API
    *
    * */
    //문제점
    // 1. 글이  너무 많은 경우 -> 비용이 너무 많이 든다.
    // 2. 글이 -> 100,000, 000 -> DB글 모두 조회하는 경우 -> DB가 뻗을 수 있다.
    // DB -> 애플리케이션 서버로 전달하는 시간, 트래픽비용 등이 많이 발생할 수 있다.
    //Pageable 사용
    @GetMapping("/posts")
    public List<PostResponseDto> getList(@ModelAttribute PostSearch postSearch){
        return postService.getList(postSearch);
    }
}
