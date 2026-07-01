package com.demo.ecommerce.infrastructure.config;

public final class ApiPaths {

    public static final String API_V1 = "/api/v1";
    public static final String AUTH = API_V1 + "/auth";
    public static final String USERS = API_V1 + "/users";
    public static final String PRODUCTS = API_V1 + "/products";
    public static final String SALES = API_V1 + "/sales";
    public static final String CATEGORIES = API_V1 + "/categories";
    public static final String BRANDS = API_V1 + "/brands";
    public static final String AUTH_REFRESH = AUTH + "/refresh";

    private ApiPaths() {
    }
}
