package com.ecommerce.api.basepage.response;

import com.ecommerce.api.basepage.response.validation.ResponseHandler;
import com.ecommerce.api.basepage.response.validation.handler.*;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import static org.hamcrest.Matchers.*;

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
    private final Map<String, Object> expectedBodyValues = new HashMap<>();
    private final Map<String, Object> arrayFieldExpectedValues = new HashMap<>();
    private final Map<String, Predicate<List<?>>> arrayFieldPredicates = new HashMap<>();

    public ResponseValidator() {
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

    public ResponseValidator withBodyField(String key, Object expectedValue) {
        expectedBodyValues.put(key, expectedValue);
        return this;
    }

    public ResponseValidator withBodyArrayField(String fieldName) {
        arrayFieldPredicates.put(fieldName, list -> list.stream().allMatch(Objects::nonNull));
        return this;
    }
    public ResponseValidator withBodyArrayFieldEveryItemEqual(String fieldName, Object expectedValue) {
        arrayFieldExpectedValues.put(fieldName, expectedValue);
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
        if (firstHandler == null && expectedBodyValues.isEmpty()) {
            throw new IllegalStateException("❌ No validation configured in ResponseValidator!");
        }

        try {
            if (firstHandler != null) {
                firstHandler.handle(response);
            }
            if (!expectedBodyValues.isEmpty()) {
                for (Map.Entry<String, Object> entry : expectedBodyValues.entrySet()) {
                    response.then().body(entry.getKey(), equalTo(entry.getValue()));
                    log.info("✅ Body field '{}' validated successfully: {}", entry.getKey(), entry.getValue());
                }
            }
            if (!arrayFieldPredicates.isEmpty()) {
                for (Map.Entry<String, Predicate<List<?>>> entry : arrayFieldPredicates.entrySet()) {
                    List<?> fieldValues = response.jsonPath().getList(entry.getKey());
                    if (!entry.getValue().test(fieldValues)) {
                        throw new AssertionError("❌ Array field '" + entry.getKey() + "' contains null values!");
                    }
                    log.info("✅ Array field '{}' validated successfully", entry.getKey());
                }
            }
            if (!arrayFieldExpectedValues.isEmpty()) {
                for (Map.Entry<String, Object> entry : arrayFieldExpectedValues.entrySet()) {
                    response.then().body(entry.getKey(), everyItem(equalTo(entry.getValue())));
                    log.info("✅ All elements of array '{}' equal '{}'", entry.getKey(), entry.getValue());
                }
            }
            log.info("✅ All validations passed for response: {}", response.getStatusCode());

        } catch (AssertionError | Exception e) {
            log.error("❌ Validation failed: {}", e.getMessage());
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

    public void validateBodyOnly() {
        try {
            bodyValidator.handle(response);
            log.info("✅ Body validation successful");
        } catch (AssertionError e) {
            log.info("❌ Body validation failed: {}", e.getMessage());
            throw e;
        }
    }

    public void validateHeaderOnly() {
        new HeaderValidator().handle(response);
    }

    public void validateSchemaOnly(String schemaPath) {
        new SchemaValidator(schemaPath).handle(response);
    }

}
