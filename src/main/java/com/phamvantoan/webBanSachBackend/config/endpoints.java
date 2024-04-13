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
            "/reporttypes",
            "/reporttypes/**",
            "/reports",
            "/reports/**",
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
            "/user/addevaluate",
            "/user/createreport",
            "/user/completeorder",
    };
    protected static final String[] ADMIN_GET_ENDPOINTS = {
            "/users",
            "/users/**",
    };
    protected static final String[] ADMIN_POST_ENDPOINTS = {
            "/books",
            "/books/**",

    };

    protected  static final String[] STAFF_GET_ENDPOINT = {
            "/users",
            "/users/**",
            "/meetings",
            "/meetings/**",
    };

    protected static  final String[] STAFF_POST_ENDPOINT = {
            "/books",
            "/books/**",
            "/staff/addbookimage",
            "/staff/bookchange",
            "/admin/addbook",
            "/staff/numberofbookchange",
            "/staff/sendreportresponse",
            "/staff/confirmorder",
    };
    protected static  final String[] STAFF_DELETE_ENDPOINT = {
            "/staff/deletebookimage",
            "/staff/deletebook",
            "/staff/deleteuser",
    };
    protected static final String[] STAFF_PUT_ENDPOINT = {
            "/staff/updateuser",
    };

}
