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
        productPage.validateGetLimitedProductsResponse(statusCode,limitSize);
    }
}
