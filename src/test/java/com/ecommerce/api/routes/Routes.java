package com.ecommerce.api.routes;

public class Routes {

    public static final String BASE_URL="https://fakestoreapi.com";

    //Product
    public static final String GET_ALL_PRODUCTS="/products";
    public static final String GET_PRODUCT_BY_ID = "/products/{id}";
    public static final String GET_PRODUCTS_WITH_LIMIT = "/products?limit={limit}";
    public static final String GET_PRODUCTS_SORTED = "/products?sort={order}";
    public static final String GET_ALL_CATEGORIES = "/products/categories";
    public static final String GET_PRODUCTS_BY_CATEGORY = "/products/category/{category}";
    public static final String CREATE_PRODUCT = "/products";
    public static final String UPDATE_PRODUCT = "/products/{id}";
    public static final String DELETE_PRODUCT = "/products/{id}";

    //Users
    public static final String GET_ALL_USERS = "/users";
    public static final String GET_USER_BY_ID = "/users/{id}";
    public static final String GET_USERS_WITH_LIMIT = "/users?limit={limit}";
    public static final String GET_USERS_SORTED = "/users?sort={order}";
    public static final String CREATE_USER = "/users";
    public static final String UPDATE_USER = "/users/{id}";
    public static final String DELETE_USER = "/users/{id}";


    //Cart
    public static final String GET_ALL_CARTS = "/carts";
    public static final String GET_CART_BY_ID = "/carts/{id}";
    public static final String GET_CARTS_BY_DATE_RANGE = "/carts?startdate={startdate}&enddate={enddate}";
    public static final String GET_USER_CART = "/carts/user/{userId}";
    public static final String GET_CARTS_WITH_LIMIT = "/carts?limit={limit}";
    public static final String GET_CARTS_SORTED = "/carts?sort={order}";
    public static final String CREATE_CART = "/carts";
    public static final String UPDATE_CART = "/carts/{id}";
    public static final String DELETE_CART = "/carts/{id}";


    //Login (Auth)
    public static final String AUTH_LOGIN = "/auth/login";

}
