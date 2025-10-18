package com.ecommerce.api.basepage.response.validation;

import io.restassured.response.Response;

import io.restassured.response.Response;

public interface ResponseHandler {
    void setNext(ResponseHandler nextHandler);
    void handle(Response response);
}
