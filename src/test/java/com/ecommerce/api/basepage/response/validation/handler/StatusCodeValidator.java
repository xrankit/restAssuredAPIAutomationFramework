package com.ecommerce.api.basepage.response.validation.handler;

import com.ecommerce.api.basepage.response.validation.ResponseHandler;
import io.restassured.response.Response;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import static org.testng.Assert.assertEquals;

public class StatusCodeValidator implements ResponseHandler {

    private ResponseHandler nextHandler;
    private final int expectedStatus;

    public StatusCodeValidator(int expectedStatus) {
        this.expectedStatus = expectedStatus;
    }

    @Override
    public void setNext(ResponseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(Response response) {
        assertEquals(response.getStatusCode(), expectedStatus, "❌ Status Code Mismatch!");
        System.out.println("✅ Status Code validated: " + expectedStatus);
        if (nextHandler != null) nextHandler.handle(response);
    }
}