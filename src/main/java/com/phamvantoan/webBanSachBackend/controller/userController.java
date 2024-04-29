package com.phamvantoan.webBanSachBackend.controller;

import com.phamvantoan.webBanSachBackend.dao.*;
import com.phamvantoan.webBanSachBackend.entity.*;
import com.phamvantoan.webBanSachBackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class userController {
    private userService userservice;
    private JwtService jwtService;
    private cartService cartservice;
    private orderService orderservice;
    private wishlistService wishlistservice;
    private evaluateService evaluateservice;
    private reportService reportservice;
    @Autowired
    public userController(userService userservice, JwtService jwtService, cartService cartservice, orderService orderservice, wishlistService wishlistservice, evaluateService evaluateservice, reportService reportservice){
        this.userservice = userservice;
        this.jwtService = jwtService;
        this.cartservice = cartservice;
        this.orderservice = orderservice;
        this.wishlistservice = wishlistservice;
        this.evaluateservice = evaluateservice;
        this.reportservice = reportservice;
    }
    @PostMapping("/addwishlist")
    public ResponseEntity<?> addWishList(@RequestBody WishList wishList, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                if(userName != null){
                    User user = this.userservice.findByUserName(userName);
                    wishList.setUser(user);

                    List<WishList> wishLists = user.getWishListList(); //thêm wishlist vào wishLists để add
                    wishLists.add(wishList);
                    user.setWishListList(wishLists);
                    this.wishlistservice.addWishList(wishList);
                    return ResponseEntity.ok("Thêm thành công");
                }else {
                    return ResponseEntity.badRequest().body("userName null");
                }
            }else {
                return ResponseEntity.badRequest().body("token null");
            }
        }else {
            return ResponseEntity.badRequest().body("Không lấy được token");
        }
    }
    @PostMapping("/deletewishlist")
    public ResponseEntity<?> deleteWishList(@RequestBody int wishListID, @RequestHeader("Authorization") String authorizationHeader){
            try {
                this.wishlistservice.deleteWishList(wishListID);
                return ResponseEntity.ok("Xóa wishlist thành công");
            }catch (Exception e){
                throw e;
            }
    }

    @PostMapping("/addbooktowishlist")
    public ResponseEntity<?> addBookToWishList(@RequestBody bookToWishListResponse booktowishlistresponse, @RequestHeader("Authorization") String authorizationHeader){
        return this.wishlistservice.addBookToWishList(booktowishlistresponse);
    }

    @PostMapping("/removebookinwishlist")
    public ResponseEntity<?> RemoveBookInWishList(@RequestBody bookToWishListResponse booktowishlistresponse, @RequestHeader("Authorization") String authorizationHeader){
        return this.wishlistservice.RemoveBookInWishList(booktowishlistresponse);
    }
    @PostMapping("/addCartItem")
    public ResponseEntity<?> addCartItem(@RequestBody addCartItemResponse addcartitemresponse, @RequestHeader("Authorization") String authorizationHeader){
        try {
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                String token = authorizationHeader.substring(7);
                if(token != null){
                    String userName = this.jwtService.extractUserName(token);
                    if(userName != null){
                        User user = this.userservice.findByUserName(userName);
                        this.cartservice.addCartItem(addcartitemresponse.getNumberOfBook(), addcartitemresponse.getBookID(), user.getCart());
                        return ResponseEntity.ok("Thêm cartItem thành công");
                    }
                    else {
                        return ResponseEntity.badRequest().body("Lấy user thất bại");
                    }
                }else {
                    return ResponseEntity.badRequest().body("Lấy token thất bại");
                }
            }else {
                return ResponseEntity.badRequest().body("Lấy header thất bại");
            }
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/deleteCartItem")
    public ResponseEntity<?> deleteCartItem(@RequestBody int cartItemID, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                if(userName != null){
                    this.cartservice.deleteCartItem(cartItemID);
                    return ResponseEntity.ok("Xóa cartItem thành công");
                }else {
                    return ResponseEntity.badRequest().body("Lỗi khi lấy userName");
                }
            }else {
                return ResponseEntity.badRequest().body("Lỗi khi lấy token");
            }
        }else {
            return ResponseEntity.badRequest().body("Lỗi khi lấy header");
        }
    }

    @PostMapping("/deleteAllCartItem")
    public ResponseEntity<?> deleteAllCartItem(@RequestBody int cartID){
        try {
            this.cartservice.deleteAllCartItem(cartID);
            return ResponseEntity.ok("Xóa tất cả cartItem thành công");
        }catch (Exception e){
            throw e;
        }
    }
    @PostMapping("/changeInformationUser")
    public ResponseEntity<?> changeInformationUser(@RequestBody changeInformationUserResponse changeinformationuserresponse, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader!= null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                if(userName != null){
                    User user = this.userservice.findByUserName(userName);
                    return this.userservice.changeInformationUser(user, changeinformationuserresponse);
                }else {
                    return ResponseEntity.badRequest().body("Lỗi khi lấy userName");
                }
            }else {
                return ResponseEntity.badRequest().body("Lỗi khi lấy token");
            }
        }else {
            return ResponseEntity.badRequest().body("Lỗi khi lấy header");
        }
    }
    @PostMapping("/deleteorder")
    public ResponseEntity<?> deleteOrder(@RequestBody int orderID){
        try {
            this.orderservice.deleteOrder(orderID);
            return ResponseEntity.ok("Xóa order thành công");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }
    @PostMapping("/buynow")
    public ResponseEntity<?> buyNow(@RequestBody createOrderResponse createorderresponse, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                if(userName != null){
                    User user = this.userservice.findByUserName(userName);

                    this.orderservice.createOrder(user, createorderresponse.getDeliveryTypeID(), createorderresponse.getPaymentID(), createorderresponse.getBookID(), createorderresponse.getNumberOfBook());
                    return ResponseEntity.ok("Tạo order thành công");
                }else {
                    return ResponseEntity.badRequest().body("Lỗi khi lấy userName");
                }
            }else {
                return ResponseEntity.badRequest().body("Lỗi khi lấy token");
            }
        }else {
            return ResponseEntity.badRequest().body("Lỗi khi lấy header");
        }
    }

    @PostMapping("/addevaluate")
    public ResponseEntity<?> addEvaluate(@RequestBody createEvaluateResponse createevaluateresponse, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                if(userName != null){
                    User user = this.userservice.findByUserName(userName);

                    return this.evaluateservice.addEvaluate(user, createevaluateresponse);
                }else {
                    return ResponseEntity.badRequest().body("Lỗi khi lấy userName");
                }
            }else {
                return ResponseEntity.badRequest().body("Lỗi khi lấy token");
            }
        }else {
            return ResponseEntity.badRequest().body("Lỗi khi lấy header");
        }
    }

    @PostMapping("/createreport")
    public ResponseEntity<?> createReport(@RequestBody reportResponse reportresponse , @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                if(userName != null){
                    User user = this.userservice.findByUserName(userName);
                    return this.reportservice.createReport(user, reportresponse.getReportDetail(), reportresponse.getOrderID(), reportresponse.getReportTypeID(), reportresponse.getReportImageDetail());
                }else {
                    return ResponseEntity.badRequest().body("Lỗi khi lấy userName");
                }
            }else {
                return ResponseEntity.badRequest().body("Lỗi khi lấy token");
            }
        }else {
            return ResponseEntity.badRequest().body("Lỗi khi lấy header");
        }
    }
    @PostMapping("/completeorder")
    public ResponseEntity<?> completeOrder(@RequestBody int orderID, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                if(userName != null){
                    return this.orderservice.completeOrder(orderID);
                }else {
                    return ResponseEntity.badRequest().body("Lỗi khi lấy userName");
                }
            }else {
                return ResponseEntity.badRequest().body("Lỗi khi lấy token");
            }
        }else {
            return ResponseEntity.badRequest().body("Lỗi khi lấy header");
        }
    }



}
