package com.ecommerce.api.basepage.response.validation.handler;

import com.ecommerce.api.basepage.response.validation.ResponseHandler;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import java.io.File;

public class SchemaValidator implements ResponseHandler {

    private ResponseHandler nextHandler;
    private final String schemaPath;

    public SchemaValidator(String schemaPath) {
        this.schemaPath = schemaPath;
    }

    @Override
    public void setNext(ResponseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(Response response) {
        response.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(new File(schemaPath)));
        System.out.println("✅ JSON Schema validated successfully.");
        if (nextHandler != null) nextHandler.handle(response);
    }
}
