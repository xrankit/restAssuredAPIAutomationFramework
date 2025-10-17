package com.ecommerce.api.basepage;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static com.ecommerce.api.routes.Routes.BASE_URL;

public class RestAssuredExtension {

    public static RequestSpecification Request;

    public RestAssuredExtension(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(BASE_URL);
        builder.setContentType(ContentType.JSON);
        var reqSpec = builder.build();
        Request = RestAssured.given().spec(reqSpec);
    }
}
