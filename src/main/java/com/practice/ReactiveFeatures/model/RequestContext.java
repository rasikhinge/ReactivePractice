package com.practice.ReactiveFeatures.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class RequestContext {
    private String requestId;
    private static RequestContext instance = null;

    private RequestContext() {
        requestId = "Request : " + Math.random() * 1000;
    }

    public static RequestContext getInstance() {
        if (instance == null) {
            log.info("Request Context Constructor Called.....");
            instance = new RequestContext();
        }
        return instance;
    }

}
