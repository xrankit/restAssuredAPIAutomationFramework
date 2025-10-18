package com.ecommerce.api.basepage.response.validation.handler;

import com.ecommerce.api.basepage.response.validation.ResponseHandler;
import io.restassured.response.Response;
import static org.testng.Assert.assertNotNull;

public class HeaderValidator implements ResponseHandler {

    private ResponseHandler nextHandler;

    @Override
    public void setNext(ResponseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(Response response) {
        assertNotNull(response.getHeader("Content-Type"), "❌ Missing Content-Type header!");
        System.out.println("✅ Headers validated successfully.");
        if (nextHandler != null) nextHandler.handle(response);
    }
}