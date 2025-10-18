package com.ecommerce.api.basepage.response;

import com.ecommerce.api.basepage.response.validation.ResponseHandler;
import com.ecommerce.api.basepage.response.validation.handler.*;
import io.restassured.response.Response;

public class ResponseValidator {

    private ResponseHandler firstHandler;
    private ResponseHandler lastHandler;
    private final Response response;

    // Optional configuration
    private Integer expectedStatus;
    private String schemaPath;
    private boolean validateHeader = false;
    private boolean validateBody = false;

    // Constructor
    public ResponseValidator(Response response) {
        this.response = response;
    }

    // ---------------------- Builder Style Config ----------------------

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

    // ---------------------- Build the Chain ----------------------

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

    // ---------------------- Add handler to chain ----------------------

    private void addToChain(ResponseHandler newHandler, ResponseHandler prev) {
        if (firstHandler == null) {
            firstHandler = newHandler;
        } else if (prev != null) {
            prev.setNext(newHandler);
        }
        lastHandler = newHandler;
    }

    // ---------------------- Execute ----------------------

    public void execute() {
        if (firstHandler == null) {
            throw new IllegalStateException("❌ No validation configured in ResponseValidator!");
        }
        firstHandler.handle(response);
    }

    // ---------------------- Single Validations ----------------------

    public void validateStatusOnly(int expectedStatus) {
        new StatusCodeValidator(expectedStatus).handle(response);
    }

    public void validateHeaderOnly() {
        new HeaderValidator().handle(response);
    }

    public void validateSchemaOnly(String schemaPath) {
        new SchemaValidator(schemaPath).handle(response);
    }

    public void validateBodyOnly() {
        new BodyValidator().handle(response);
    }
}
