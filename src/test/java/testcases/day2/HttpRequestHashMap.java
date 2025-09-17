package testcases.day2;

import org.testng.annotations.Test;
import java.util.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HttpRequestHashMap {

    private int productId;

    @Test(priority = 1)
    void testGetHttpRequestHashMap(){

        given()

                .when()
                .get("http://localhost:8765/products")

                .then()
                .header("Content-Type","application/json; charset=utf-8")
                .log().all();
    }

    @Test(priority = 2)
    void testPostHttpRequestHashMap (){

        Map<String,Object> data = new HashMap<>();
        data.put("title","Ankit Product");
        data.put("price",13.5);
        data.put("description","You can also use the limit number and sort the ankit product");
        data.put("image","https://i.ankitxr.cc");
        data.put("category","electronic");

/*        given()
                .contentType("application/json")
                .body(data)

                .when()
                .post("http://localhost:8765/products")

                .then()
                .statusCode(200)
                .body("id",equalTo(21))
                .body("price",equalTo(13.5F))
                .header("Content-Type","application/json; charset=utf-8")
                .log().all();
 */
        productId = given()
                .contentType("application/json")
                .body(data)

                .when()
                .post("http://localhost:8765/products")
                .jsonPath().getInt("id");

    }

    @Test(priority = 3,dependsOnMethods = {"testPostHttpRequestHashMap"})
    void testPutHttpRequestHashMap(){
        Map<String, Object> data = new HashMap<>();
        data.put("title","Tested Ankit Product Update");
        data.put("category","electronic update");

        given()
                .contentType("application/json")
                .body(data)

                .when()
                .put("http://localhost:8765/products/"+productId)

                .then()
                .statusCode(200)
                .body("id",equalTo(productId))
                .body("price",equalTo(13.5F))
                .body("category",equalTo("electronic update"))
                .header("Content-Type","application/json; charset=utf-8")
                .log().all();

    }

    @Test(priority = 4,dependsOnMethods = {"testPostHttpRequestHashMap"})
    void testDeleteHttpRequestHashMap(){

        given()
                .contentType("application/json")

                .when()
                .delete("http://localhost:8765/products/"+productId)

                .then()
                .statusCode(200);

    }



}
