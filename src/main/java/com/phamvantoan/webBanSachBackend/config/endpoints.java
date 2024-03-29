package com.phamvantoan.webBanSachBackend.config;

public class endpoints {
    public static final String FRONT_END_HOST = "http://localhost:3000";
    protected static final String[] PUBLIC_GET_ENDPOINTS = {
            "/books",
            "/books/**",
            "/images",
            "/images/**",
            "/users/search/existsByUserName",
            "/users/search/existsByEmail",
            "/account/activate",
            "/wish-lists",
            "/wish-lists/**",
            "/users/**",
            "/carts",
            "/carts/**",
            "/cart-items",
            "/cart-items/**",
            "/delivery-types",
            "/payments",
            "/orders",
            "/orders/**",
            "/order-items",
            "/order-items/**",
            "/categorys",
            "/categorys/**",
            "/evaluates",
            "/evaluates/**",
            "/authors",
            "/authors/**",
    };
    protected static final String[] PUBLIC_POST_ENDPOINTS = {
            "/account/register",
            "/account/login",
            "/user/addwishlist",
            "/user/deletewishlist",
            "/user/addbooktowishlist",
            "/user/removebookinwishlist",
            "/user/addCartItem",
            "/user/deleteCartItem",
            "/user/deleteAllCartItem",
            "/user/changeInformationUser",
            "/user/deleteorder",
            "/user/buynow",
            "/api/payment/charge",
    };
    protected static final String[] ADMIN_GET_ENDPOINTS = {
            "/users",
            "/users/**",
    };
    protected static final String[] ADMIN_POST_ENDPOINTS = {
            "/books",
            "/books/**",
            "/admin/addbook",
    };

}
