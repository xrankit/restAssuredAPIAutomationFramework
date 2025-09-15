package testcases.Day1;
/*
given() - content type, set cookies, add auth, add parameters, set headers etc...
when() - get, post, put, delete
Then() - validate  status code, extract responses, extract headers cookies, and response body....,
 */

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.ResponseAwareMatcher.*;
import static org.hamcrest.Matchers.*;

public class HTTPRequests {

    @Test
    void getUsers(){
        given()

        .when()
                .get("https://fakestoreapi.com/products")

        .then()
                .statusCode(200)
                .log().all();
    }

}
