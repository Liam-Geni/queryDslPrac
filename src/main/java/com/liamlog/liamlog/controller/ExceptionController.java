package com.liamlog.liamlog.controller;

import com.liamlog.liamlog.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExceptionController {

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public Map<String, String> invalidRequestHandler(MethodArgumentNotValidException e){
//        // MethodArgumentNotValidException
//        // e.getField()
//        FieldError fieldError = e.getFieldError();
//        String field = fieldError.getField();
//        String message = fieldError.getDefaultMessage();
//        Map<String, String> response = new HashMap<>();
//        response.put(field, message);
//        return response;
//    }
//        @ResponseStatus(HttpStatus.BAD_REQUEST)
//        @ExceptionHandler(Exception.class)
//        @ResponseBody
//        public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
//            if (e.hasErrors()) {
//    //                    FieldError fieldError = e.getFieldError();
//    //                    String field = fieldError.getField();
//    //                    String message = fieldError.getDefaultMessage();
//                return new ErrorResponse("400", "잘못된 요청입니다. ");
//            }
//        }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
//          FieldError fieldError = e.getFieldError();
//          String field = fieldError.getField();
//          String message = fieldError.getDefaultMessage();
            ErrorResponse response = new ErrorResponse("400", "잘못된 요청입니다.");
            for(FieldError fieldError : e.getFieldErrors()){

                response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
            return response;
    }
}
