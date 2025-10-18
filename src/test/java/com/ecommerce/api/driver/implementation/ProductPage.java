package com.ecommerce.api.driver.implementation;

import com.ecommerce.api.basepage.CommonPage;
import com.ecommerce.api.basepage.response.ResponseValidator;
import com.ecommerce.api.basepage.response.*;
import com.ecommerce.api.basepage.request.*;
import io.restassured.response.Response;

import java.util.List;

import static com.ecommerce.api.routes.Routes.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductPage extends CommonPage {

    private final APIRequestBuilder requestHandler;
    private final APIRequestExecutor executor;
    private final ResponseValidator responseValidator;
    private Response response;

    public ProductPage() {
        requestHandler = new APIRequestBuilder(RestAssuredExtension.getRequestSpec());
        executor = new APIRequestExecutor(requestHandler.build());
        responseValidator = new ResponseValidator();
    }

    public void prepareGetAllProductsRequest() {
        requestHandler.withHeader("Accept", "application/json");
    }

    public void sendGetAllProductsRequest() {
        response = executor.getRequest(GET_ALL_PRODUCTS);
        responseValidator.setResponse(response);
    }

    public void validateGetAllProductsResponse() {
        responseValidator.withStatusCode(200)
                .withBodyValidation()
                .build()
                .execute();
    }

    public void prepareGetSingleProductByIdRequest() {
        int productId = configReader.getIntProperty("productId");
        requestHandler.withPathParam("id", productId)
                        .withHeader("Accept", "application/json");
    }

    public void sendGetSingleProductByIdRequest() {
        response = executor.getRequest(GET_PRODUCT_BY_ID);
        responseValidator.setResponse(response);
    }

    public void validateGetSingleProductByIdResponse() {
        responseValidator.withStatusCode(200)
                .withBodyValidation()
                .build()
                .execute();
    }
    public void prepareGetLimitedProductsRequest() {
        requestHandler.withPathParam("limit", 3)
                .withHeader("Accept", "application/json");
    }

    public void sendGetLimitedProductsRequest() {
        response = executor.getRequest(GET_PRODUCTS_WITH_LIMIT);
        responseValidator.setResponse(response);
    }

    public void validateGetLimitedProductsResponse(int statusCode, int limitSize) {
        responseValidator.withStatusCode(statusCode)
                .withBodyValidation()
                .build()
                .execute();

        assertThat(response.jsonPath().getList("$").size(), equalTo(limitSize));
    }

    public void prepareGetSortedProductsRequest(String order) {
        requestHandler.withPathParam("order", order)
                .withHeader("Accept", "application/json");
    }

    public void sendGetSortedProductsRequest() {
        response = executor.getRequest(GET_PRODUCTS_SORTED);
        responseValidator.setResponse(response);
    }

    public void validateSortedProducts(String order) {
        responseValidator.withStatusCode(200)
                .build()
                .execute();

        List<Integer> productIds = response.jsonPath().getList("id", Integer.class);
        if (order.equalsIgnoreCase("desc")) {
            assertThat(isSortedDescending(productIds), is(true));
        } else {
            assertThat(isSortedAscending(productIds), is(true));
        }
    }
    public void sendGetAllCategoriesRequest() {
        response = executor.getRequest(GET_ALL_CATEGORIES);
        responseValidator.setResponse(response);
    }

    public void validateAllCategories() {
        responseValidator.withStatusCode(200)
                .withBodyValidation()
                .build()
                .execute();
    }
    public void sendGetProductsByCategoryRequest() {
        response = executor.getRequest(GET_PRODUCTS_BY_CATEGORY);
        responseValidator.setResponse(response);
    }

    public void validateProductsByCategory(String category) {
        responseValidator.withStatusCode(200)
                .withBodyValidation()
                .withBodyArrayField("category")
                .withBodyArrayFieldEveryItemEqual("category",category)
                .build()
                .execute();
    }

}



