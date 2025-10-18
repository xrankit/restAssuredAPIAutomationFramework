package com.ecommerce.api.basepage.request;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * APIRequestExecutor
 * -------------------
 * Responsible for executing HTTP methods using a provided RequestSpecification.
 * This class supports: GET, POST, PUT, PATCH, DELETE requests
 */

public class APIRequestExecutor {

    private final RequestSpecification spec;

    public APIRequestExecutor(RequestSpecification spec) {
        this.spec = spec;
    }

    public Response getRequest(String endpoint) {
        return spec
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response postRequest(String endpoint) {
        return spec
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response putRequest(String endpoint) {
        return spec
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response patchRequest(String endpoint) {
        return spec
                .when()
                .patch(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response deleteRequest(String endpoint) {
        return spec
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }
}

