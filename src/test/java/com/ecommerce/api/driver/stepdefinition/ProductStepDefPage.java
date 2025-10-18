package com.ecommerce.api.driver.stepdefinition;

import com.ecommerce.api.driver.implementation.ProductPage;
import io.cucumber.java.en.*;

public class ProductStepDefPage {

    ProductPage productPage = new ProductPage();

    @Given("user prepared request to get all product data")
    public void userPreparedRequestToGetAllProductData() {
        productPage.prepareGetAllProductsRequest();
    }

    @When("user send a getRequest to get product endpoint with valid payload")
    public void userSendAGetRequestToGetProductEndpointWithValidPayload() {
        productPage.sendGetAllProductsRequest();
    }

    @Then("validate the complete API response for getAllProducts")
    public void validateCompleteApiResponse() {
        productPage.validateGetAllProductsResponse();
    }

    @Given("user prepared request to get single product by id")
    public void userPreparedRequestToGetSingleProductById() {
        productPage.prepareGetSingleProductByIdRequest();
    }

    @When("user sends get request to get product by id endpoint")
    public void userSendsGetRequestToGetProductByIdEndpoint() {
        productPage.sendGetSingleProductByIdRequest();
    }

    @Then("response status code should be {int} with valid product details")
    public void responseStatusCodeShouldBe(Integer statusCode) {
        productPage.validateGetSingleProductByIdResponse();
    }

    @Given("user prepared request to get limited number of products")
    public void userPreparedRequestToGetLimitedNumberOfProducts() {
        productPage.prepareGetLimitedProductsRequest();
    }

    @When("user sends get request to get products with limit")
    public void userSendsGetRequestToGetProductsWithLimit() {
        productPage.sendGetLimitedProductsRequest();
    }

    @Then("validate the status code should be {int} with body size be {int}")
    public void validateTheStatusCodeShouldBeWithBodySizeBe(int statusCode, int limitSize) {
        productPage.validateGetLimitedProductsResponse(statusCode, limitSize);
    }

    @Given("user prepare a request to get products sorted in {string} order")
    public void prepareSortedProductsRequest(String order) {
        productPage.prepareGetSortedProductsRequest(order);
    }

    @When("user send the request to get sorted products")
    public void sendSortedProductsRequest() {
        productPage.sendGetSortedProductsRequest();
    }

    @Then("validate products should be sorted in {string} order")
    public void validateProductsShouldBeSortdIn(String order) {
        productPage.validateSortedProducts(order);
    }
    @Given("user prepare request to get all categories")
    public void prepareAllCategoriesRequest() {
        // No params needed
    }

    @When("user send request to get all categories")
    public void sendAllCategoriesRequest() {
        productPage.sendGetAllCategoriesRequest();
    }

    @Then("validate all categories are returned")
    public void validateAllCategories() {
        productPage.validateAllCategories();
    }

    @Given("user prepare request to get products by category {string}")
    public void prepareProductsByCategoryRequest(String category) {
    }

    @When("user send request to get products by category")
    public void sendProductsByCategoryRequest() {
        productPage.sendGetProductsByCategoryRequest();
    }

    @Then("validate products belong to category {string}")
    public void validateProductsByCategory(String category) {
        productPage.validateProductsByCategory(category);
    }
}
