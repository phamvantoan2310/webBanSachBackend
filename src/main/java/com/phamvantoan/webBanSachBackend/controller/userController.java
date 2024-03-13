package com.phamvantoan.webBanSachBackend.controller;

import com.phamvantoan.webBanSachBackend.dao.bookRepository;
import com.phamvantoan.webBanSachBackend.dao.wishListRepository;
import com.phamvantoan.webBanSachBackend.entity.Book;
import com.phamvantoan.webBanSachBackend.entity.User;
import com.phamvantoan.webBanSachBackend.entity.WishList;
import com.phamvantoan.webBanSachBackend.service.JwtService;
import com.phamvantoan.webBanSachBackend.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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
    private wishListRepository wishlistrepository;
    @Autowired
    private bookRepository bookrepository;
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
}
