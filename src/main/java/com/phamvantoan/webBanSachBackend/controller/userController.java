package com.phamvantoan.webBanSachBackend.controller;

import com.phamvantoan.webBanSachBackend.dao.*;
import com.phamvantoan.webBanSachBackend.entity.*;
import com.phamvantoan.webBanSachBackend.service.JwtService;
import com.phamvantoan.webBanSachBackend.service.cartService;
import com.phamvantoan.webBanSachBackend.service.orderService;
import com.phamvantoan.webBanSachBackend.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class userController {
    @Autowired
    private userService userservice;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private cartService cartservice;
    @Autowired
    private orderService orderservice;
    @Autowired
    private orderRepository orderrepository;
    @Autowired
    private wishListRepository wishlistrepository;
    @Autowired
    private bookRepository bookrepository;
    @Autowired
    private cartItemRepository cartitemrepository;
    @Autowired
    private cartRepository cartrepository;
    @Autowired
    private userRepository userrepository;
    @Autowired
    private deliveryTypeRepository deliverytyperepository;
    @Autowired
    private paymentRepository paymentrepository;
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
                    this.userservice.addWishList(wishList);
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
                WishList wishList = this.wishlistrepository.findByWishListID(wishListID);  //xóa book trong wistList
                List<Book> books = wishList.getBookList();
                for(Book book : books){
                    book.getListWishList().remove(wishList);
                }

                User user = wishList.getUser();
                if(user != null){                                                           //xóa wishlist trong user
                    user.getWishListList().remove(wishList);

                    wishList.setUser(null);
                }
                this.userservice.deleteWishList(wishListID);
                return ResponseEntity.ok("Xóa thành công");
            }catch (Exception e){
                throw e;
            }
    }

    @PostMapping("/addbooktowishlist")
    public ResponseEntity<?> addBookToWishList(@RequestBody bookToWishListResponse booktowishlistresponse, @RequestHeader("Authorization") String authorizationHeader){
        if(booktowishlistresponse != null){
            WishList wishList = this.wishlistrepository.findByWishListID(booktowishlistresponse.getWishListID());

            boolean inBooks = false;

            for(Book book : wishList.getBookList()){
                if(book.getBookID() == booktowishlistresponse.getBookID()){
                    inBooks = true;
                }
            }

            if(inBooks == false){
                Book book = this.bookrepository.findByBookID(booktowishlistresponse.getBookID());
                wishList.getBookList().add(book);
                this.wishlistrepository.save(wishList);
                return ResponseEntity.ok("Thêm thành công");
            }else {
                return ResponseEntity.badRequest().body("Sách đã tồn tại");
            }
        }else {
            return ResponseEntity.badRequest().body("Thêm không thành công");
        }
    }

    @PostMapping("/removebookinwishlist")
    public ResponseEntity<?> RemoveBookInWishList(@RequestBody bookToWishListResponse booktowishlistresponse, @RequestHeader("Authorization") String authorizationHeader){
        if(booktowishlistresponse != null){
            System.out.println("WistListID: "+booktowishlistresponse.getWishListID() + "WistListID: "+booktowishlistresponse.getBookID());
            WishList wishList = this.wishlistrepository.findByWishListID(booktowishlistresponse.getWishListID());

            for(Book book : wishList.getBookList()){
                if(book.getBookID() == booktowishlistresponse.getBookID()){
                    book.getListWishList().remove(wishList);
                    wishList.getBookList().remove(book);
                    this.wishlistrepository.save(wishList);
                    return ResponseEntity.ok("Xóa thành công");
                }
            }
            return ResponseEntity.badRequest().body("Xóa thất bại");
        }else {
            return ResponseEntity.badRequest().body("Xóa không thành công");
        }
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
                        Book book = this.bookrepository.findByBookID(addcartitemresponse.getBookID());
                        this.cartservice.addCartItem(addcartitemresponse.getNumberOfBook(), book, user.getCart());
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
                    User user = this.userservice.findByUserName(userName);
                    CartItem cartItem = this.cartitemrepository.findByCartItemID(cartItemID);
                    this.cartservice.deleteCartItem(cartItem);
                    return ResponseEntity.ok("Xóa thành công");
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
            return ResponseEntity.ok("Xóa thành công");
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
                    if(changeinformationuserresponse.getPhoneNumber()!=""){
                        user.setPhoneNumber(changeinformationuserresponse.getPhoneNumber());
                    }
                    if(changeinformationuserresponse.getEmail() != ""){
                        user.setEmail(changeinformationuserresponse.getEmail());
                    }
                    if(changeinformationuserresponse.getAddress() != ""){
                        user.setAddress(changeinformationuserresponse.getAddress());
                    }
                    this.userrepository.save(user);
                    return ResponseEntity.ok("Đổi thông tin thành công");
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
            Orders orders = this.orderrepository.findOrdersByOrderID(orderID);
            this.orderservice.deleteOrder(orders);
            return ResponseEntity.ok("Xóa thành công");
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
                    DeliveryType deliveryType = this.deliverytyperepository.findByDeliveryTypeID(createorderresponse.getDeliveryTypeID());
                    Payment payment = this.paymentrepository.findByPaymentID(createorderresponse.getPaymentID());
                    this.orderservice.createOrder(user, deliveryType, payment);
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
}
