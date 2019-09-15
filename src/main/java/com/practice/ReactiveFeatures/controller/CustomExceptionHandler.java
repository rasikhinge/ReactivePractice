package com.practice.ReactiveFeatures.controller;

import com.practice.ReactiveFeatures.model.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

  //  @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity handleException() {
        RequestContext context = RequestContext.getInstance();
        log.info("Request Id - {} ", context.getRequestId());
        return ResponseEntity.badRequest().build();
    }
}
