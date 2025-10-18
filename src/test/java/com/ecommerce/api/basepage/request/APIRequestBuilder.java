package com.ecommerce.api.basepage.request;

import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class APIRequestBuilder {

    private final RequestSpecification spec;

    public APIRequestBuilder() {
        this.spec = given().spec(RestAssuredExtension.getRequestSpec());
    }

    public APIRequestBuilder withBody(Object body) {
        spec.body(body);
        return this;
    }

    public APIRequestBuilder withHeader(String key, String value) {
        spec.header(key, value);
        return this;
    }

    public APIRequestBuilder withHeaders(Map<String, String> headers) {
        spec.headers(headers);
        return this;
    }

    public APIRequestBuilder withQueryParam(String key, Object value) {
        spec.queryParam(key, value);
        return this;
    }

    public APIRequestBuilder withQueryParams(Map<String, ?> queryParams) {
        spec.queryParams(queryParams);
        return this;
    }

    public APIRequestBuilder withPathParam(String key, Object value) {
        spec.pathParam(key, value);
        return this;
    }

    public APIRequestBuilder withPathParams(Map<String, ?> pathParams) {
        spec.pathParams(pathParams);
        return this;
    }

    public APIRequestBuilder withAuthToken(String token) {
        spec.header("Authorization", "Bearer " + token);
        return this;
    }

    public APIRequestBuilder withContentType(String contentType) {
        spec.contentType(contentType);
        return this;
    }

    public RequestSpecification build() {
        return spec;
    }
}

