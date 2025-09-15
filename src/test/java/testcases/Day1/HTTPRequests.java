package testcases.Day1;
/*
given() - content type, set cookies, add auth, add parameters, set headers etc...
when() - get, post, put, delete
Then() - validate  status code, extract responses, extract headers cookies, and response body....,
 */

import org.testng.annotations.Test;
import java.util.*;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.ResponseAwareMatcher.*;
import static org.hamcrest.Matchers.*;

public class HTTPRequests {

    @Test(priority = 1)
    void getProducts(){
        given()

        .when()
                .get("https://fakestoreapi.com/products")

        .then()
                .statusCode(200)
                .log().all();
    }

    @Test(priority = 2)
    void createProducts(){
        HashMap map = new HashMap();
        map.put("title","New Product");
        map.put("price",29.99);

        given()
                .contentType("application/json")
                .body(map)
        .when()
                .post("https://fakestoreapi.com/products")
        .then()
                .statusCode(201)
                .log().all();
    }

}
