package com.ecommerce.api.basepage.response.validation.handler;

import com.ecommerce.api.basepage.response.validation.ResponseHandler;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class BodyValidator implements ResponseHandler {

    private ResponseHandler nextHandler;

    @Override
    public void setNext(ResponseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(Response response) {
        assertThat(response.jsonPath().getList("$").size(), greaterThan(0));
        System.out.println("✅ Body validation passed — product list is not empty.");
        if (nextHandler != null) nextHandler.handle(response);
    }
}
