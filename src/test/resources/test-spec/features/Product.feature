@Product

Feature: Product API test cases different operations

  Scenario: Verify that user can get all the products successfully
    Given user prepared request to get all product data
    When user send a getRequest to get product endpoint with valid payload
    Then validate the complete API response for getAllProducts

  Scenario: Verify that user can retrieve a single product by valid ID
    Given user prepared request to get single product by id
    When user sends get request to get product by id endpoint
    Then response status code should be 200 with valid product details

  Scenario: Verify that user can retrieve a limited number of products
    Given user prepared request to get limited number of products
    When user sends get request to get products with limit
    Then validate the status code should be 200 with body size be 3

  Scenario Outline: Get products sorted in <sortedOrder> order
    Given user prepare a request to get products sorted in "<order>" order
    When user send the request to get sorted products
    Then validate products should be sorted in "<order>" order
    Examples:
      | sortedOrder | order |
      | ascending   | asc   |
      | descending  | desc  |

  Scenario: Get all product categories
    Given user prepare request to get all categories
    When user send request to get all categories
    Then validate all categories are returned

  Scenario: Get products by category
    Given user prepare request to get products by category "electronics"
    When user send request to get products by category
    Then validate products belong to category "electronics"