package com.ecommerce.api.basepage.response;

import com.ecommerce.api.basepage.response.validation.ResponseHandler;
import com.ecommerce.api.basepage.response.validation.handler.*;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseValidator {

    private ResponseHandler firstHandler;
    private ResponseHandler lastHandler;
    private Response response;
    private final BodyValidator bodyValidator;

    private Integer expectedStatus;
    private String schemaPath;
    private boolean validateHeader = false;
    private boolean validateBody = false;

    public ResponseValidator(){
        bodyValidator = new BodyValidator();
    }

    public ResponseValidator setResponse(Response response) {
        this.response = response;
        return this;
    }

    public ResponseValidator withStatusCode(int statusCode) {
        this.expectedStatus = statusCode;
        return this;
    }

    public ResponseValidator withSchema(String schemaPath) {
        this.schemaPath = schemaPath;
        return this;
    }

    public ResponseValidator withHeaderValidation() {
        this.validateHeader = true;
        return this;
    }

    public ResponseValidator withBodyValidation() {
        this.validateBody = true;
        return this;
    }

    public ResponseValidator build() {
        ResponseHandler prev = null;

        if (expectedStatus != null) {
            ResponseHandler statusValidator = new StatusCodeValidator(expectedStatus);
            addToChain(statusValidator, prev);
            prev = statusValidator;
        }

        if (validateHeader) {
            ResponseHandler headerValidator = new HeaderValidator();
            addToChain(headerValidator, prev);
            prev = headerValidator;
        }

        if (schemaPath != null) {
            ResponseHandler schemaValidator = new SchemaValidator(schemaPath);
            addToChain(schemaValidator, prev);
            prev = schemaValidator;
        }

        if (validateBody) {
            ResponseHandler bodyValidator = new BodyValidator();
            addToChain(bodyValidator, prev);
            prev = bodyValidator;
        }

        return this;
    }

    private void addToChain(ResponseHandler newHandler, ResponseHandler prev) {
        if (firstHandler == null) {
            firstHandler = newHandler;
        } else if (prev != null) {
            prev.setNext(newHandler);
        }
        lastHandler = newHandler;
    }

    public void execute() {
        if (firstHandler == null) {
            throw new IllegalStateException("❌ No validation configured in ResponseValidator!");
        }
        try {
            firstHandler.handle(response);
            log.info("✅ Validation passed for response: {}", response.getStatusCode());
        } catch (AssertionError | Exception e) {
            log.info("❌ Validation failed: {}", e.getMessage());
            throw e;
        }
    }

    public void validateStatusOnly(int expectedStatus) {
        try {
            new StatusCodeValidator(expectedStatus).handle(response);
            log.info("✅ Status code validated successfully: {}", expectedStatus);
        } catch (AssertionError e) {
            log.info("❌ Status code validation failed: {}", e.getMessage());
            throw e;
        }
    }

    public void validateHeaderOnly() {
        new HeaderValidator().handle(response);
    }

    public void validateSchemaOnly(String schemaPath) {
        new SchemaValidator(schemaPath).handle(response);
    }

    public void validateBodyOnly() {
        try {
            bodyValidator.handle(response);
            log.info("✅ Body validation successful");
        } catch (AssertionError e) {
            log.info("❌ Body validation failed: {}", e.getMessage());
            throw e;
        }
    }

}
