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
            "/user/addCartItem",
            "/user/buynow",
            "/api/payment/charge",
            "/user/addevaluate",
            "/user/createreport",
            "/account/sendOTP",
            "/account/checkOTP",
    };
    protected static final String[] PUBLIC_PUT_ENDPOINTS = {
            "/user/updateNumberOfCartItem",
            "/user/addbooktowishlist",
            "/user/removebookinwishlist",
            "/user/changeInformationUser",
            "/user/completeorder",
            "/account/changepassword",
            "/account/changepasswordwhenforgotpassword",
            "/account/reactivate",
            "/user/updateorderaddress",
            "/user/changewishlistname",
    };
    protected static final String[] PUBLIC_DELETE_ENDPOINTS = {
            "/user/deletewishlist",
            "/user/deleteCartItem",
            "/user/deleteAllCartItem",
            "/user/deleteorder",
    };
    protected static final String[] ADMIN_GET_ENDPOINTS = {
            "/users",
            "/users/**",
            "/admin/getrevenuebyrevenuedate",
            "/bills",
            "/bills/**",
            "/billitems/**",
            "/authors/**",
    };
    protected static final String[] ADMIN_POST_ENDPOINTS = {
            "/books",
            "/books/**",
            "/bills/**",
            "/admin/createbill",
            "/admin/createauthor",
            "/admin/createcategory",
    };

    protected static final String[] ADMIN_PUT_ENDPOINTS = {
            "/admin/updatestaff",
            "/bills/**",
            "/admin/updateauthor",
            "/account/changepassword",
    };

    protected static final String[] ADMIN_DELETE_ENDPOINTS = {
            "/admin/cancelmeeting",
            "/bills/**",
            "/admin/deletebill",
            "/admin/deleteauthor",
            "/admin/deletecategory",
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
            "/admin/addbook",
            "/staff/sendreportresponse",
            "/admin/createauthor",
            "/admin/createcategory",
    };
    protected static  final String[] STAFF_DELETE_ENDPOINT = {
            "/staff/deletebookimage",
            "/staff/deletebook",
            "/staff/deleteuser",
            "/admin/deleteauthor",
            "/admin/deletecategory",
    };
    protected static final String[] STAFF_PUT_ENDPOINT = {
            "/staff/updateuser",
            "/admin/updateauthor",
            "/staff/bookchange",
            "/staff/numberofbookchange",
            "/staff/confirmorder",
            "/account/changepassword",
    };

}
