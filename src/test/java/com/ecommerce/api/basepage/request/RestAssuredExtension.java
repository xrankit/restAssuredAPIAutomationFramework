package com.ecommerce.api.basepage.request;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static com.ecommerce.api.routes.Routes.BASE_URL;

public class RestAssuredExtension {

    private static final ThreadLocal<RequestSpecification> requestSpec = new ThreadLocal<>();

    public static RequestSpecification getRequestSpec() {
        if (requestSpec.get() == null) {
            RequestSpecification spec = new RequestSpecBuilder()
                    .setBaseUri(BASE_URL)
                    .setContentType(ContentType.JSON)
                    .build();
            requestSpec.set(spec);
        }
        return requestSpec.get();
    }
}
