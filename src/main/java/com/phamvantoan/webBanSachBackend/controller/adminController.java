package com.phamvantoan.webBanSachBackend.controller;

import com.phamvantoan.webBanSachBackend.entity.Author;
import com.phamvantoan.webBanSachBackend.entity.Notification;
import com.phamvantoan.webBanSachBackend.entity.User;
import com.phamvantoan.webBanSachBackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin")
public class adminController {
    private adminService adminservice;
    private JwtService jwtService;
    private userService userservice;
    private billService billservice;
    private authorService authorService;
    private categoryService categoryService;
    @Autowired
    public adminController(adminService adminservice, JwtService jwtService, userService userservice, billService billservice, authorService authorService, categoryService categoryService){
        this.adminservice = adminservice;
        this.jwtService = jwtService;
        this.userservice = userservice;
        this.billservice = billservice;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @PostMapping("/addbook")
    public ResponseEntity<?> addBook(@RequestBody addBookResponse addbookresponse, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                UserDetails userDetails = this.userservice.loadUserByUsername(userName);
                if(userName != null && this.jwtService.validateToken(token, userDetails)){
                    User user = this.userservice.findByUserName((userName));
                    if(user != null){
                        this.adminservice.addBook(addbookresponse.getBook(),addbookresponse.getImages(), addbookresponse.getCategoryID(), user.getEmail(), addbookresponse.getAuthorID());
                        return ResponseEntity.ok("Thêm sách thành công");
                    }
                }else {
                    return ResponseEntity.badRequest().body(new Notification("Lỗi khi lấy user name"));
                }
            }else {
                return ResponseEntity.badRequest().body(new Notification("Không lấy được token"));
            }
        }
        return ResponseEntity.badRequest().body(new Notification("gặp lỗi khi thêm sách"));
    }

    @PostMapping("/createbill")
    public ResponseEntity<?> createBill(@RequestBody createBillResponse createBillResponse, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                UserDetails userDetails = this.userservice.loadUserByUsername(userName);
                if(userName != null && this.jwtService.validateToken(token, userDetails)){
                    User user = this.userservice.findByUserName((userName));
                    if(user != null){
                        return this.billservice.save(createBillResponse.getBill(), createBillResponse.getBillItemResponses(), user.getUserID());
                    }
                }else {
                    return ResponseEntity.badRequest().body(new Notification("Lỗi khi lấy user name"));
                }
            }else {
                return ResponseEntity.badRequest().body(new Notification("Không lấy được token"));
            }
        }
        return ResponseEntity.badRequest().body(new Notification("gặp lỗi khi tạo hóa đơn "));
    }

    @DeleteMapping("/deletebill")
    public ResponseEntity<?> deleteBill(@RequestBody int billID, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                UserDetails userDetails = this.userservice.loadUserByUsername(userName);
                if(userName != null && this.jwtService.validateToken(token, userDetails)){
                    User user = this.userservice.findByUserName((userName));
                    if(user != null){
                        return this.billservice.delete(billID);
                    }
                }else {
                    return ResponseEntity.badRequest().body(new Notification("Lỗi khi lấy user name"));
                }
            }else {
                return ResponseEntity.badRequest().body(new Notification("Không lấy được token"));
            }
        }
        return ResponseEntity.badRequest().body(new Notification("gặp lỗi khi tạo hóa đơn "));
    }

    @PutMapping("/updatestaff")
    public ResponseEntity<?> updateStaff(@Validated @RequestBody User user, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                UserDetails userDetails = this.userservice.loadUserByUsername(userName);
                if(userName != null && this.jwtService.validateToken(token, userDetails)){
                    User user1 = this.userservice.findByUserName((userName));
                    if(user1 != null){
                        return this.userservice.adminUpdateStaff(user);
                    }
                }else {
                    return ResponseEntity.badRequest().body(new Notification("Lỗi khi lấy user name"));
                }
            }else {
                return ResponseEntity.badRequest().body(new Notification("Không lấy được token"));
            }
        }
        return ResponseEntity.badRequest().body(new Notification("gặp lỗi khi tạo hóa đơn "));
    }

    @PostMapping("/createauthor")
    public ResponseEntity<?> createAuthor(@RequestBody Author author, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                UserDetails userDetails = this.userservice.loadUserByUsername(userName);
                if(userName != null && this.jwtService.validateToken(token, userDetails)){
                    User user = this.userservice.findByUserName((userName));
                    if(user != null){
                        return this.authorService.createAuthor(author);
                    }
                }else {
                    return ResponseEntity.badRequest().body(new Notification("Lỗi khi lấy user name"));
                }
            }else {
                return ResponseEntity.badRequest().body(new Notification("Không lấy được token"));
            }
        }
        return ResponseEntity.badRequest().body(new Notification("gặp lỗi khi tạo hóa đơn "));
    }

    @PutMapping("/updateauthor")
    public ResponseEntity<?> updateAuthor(@RequestBody Author author, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                UserDetails userDetails = this.userservice.loadUserByUsername(userName);
                if(userName != null && this.jwtService.validateToken(token, userDetails)){
                    User user = this.userservice.findByUserName((userName));
                    if(user != null){
                        System.out.println(author);
                        return this.authorService.updateAuthor(author);
                    }
                }else {
                    return ResponseEntity.badRequest().body(new Notification("Lỗi khi lấy user name"));
                }
            }else {
                return ResponseEntity.badRequest().body(new Notification("Không lấy được token"));
            }
        }
        return ResponseEntity.badRequest().body(new Notification("gặp lỗi khi tạo hóa đơn "));
    }

    @DeleteMapping("/deleteauthor")
    public ResponseEntity<?> deleteAuthor(@RequestBody int authorID, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                UserDetails userDetails = this.userservice.loadUserByUsername(userName);
                if(userName != null && this.jwtService.validateToken(token, userDetails)){
                    User user = this.userservice.findByUserName((userName));
                    if(user != null){
                        return this.authorService.deleteAuthor(authorID);
                    }
                }else {
                    return ResponseEntity.badRequest().body(new Notification("Lỗi khi lấy user name"));
                }
            }else {
                return ResponseEntity.badRequest().body(new Notification("Không lấy được token"));
            }
        }
        return ResponseEntity.badRequest().body(new Notification("gặp lỗi khi tạo hóa đơn "));
    }

    @PostMapping("/createcategory")
    public ResponseEntity<?> createCategory(@RequestBody createCategoryResponse createCategoryResponse, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                UserDetails userDetails = this.userservice.loadUserByUsername(userName);
                if(userName != null && this.jwtService.validateToken(token, userDetails)){
                    User user = this.userservice.findByUserName((userName));
                    if(user != null){
                        return this.categoryService.createCategory(createCategoryResponse.getCategoryName());
                    }
                }else {
                    return ResponseEntity.badRequest().body(new Notification("Lỗi khi lấy user name"));
                }
            }else {
                return ResponseEntity.badRequest().body(new Notification("Không lấy được token"));
            }
        }
        return ResponseEntity.badRequest().body(new Notification("gặp lỗi khi tạo hóa đơn "));
    }

    @DeleteMapping("/deletecategory")
    public ResponseEntity<?> deleteCategory(@RequestBody int categoryID, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                UserDetails userDetails = this.userservice.loadUserByUsername(userName);
                if(userName != null && this.jwtService.validateToken(token, userDetails)){
                    User user = this.userservice.findByUserName((userName));
                    if(user != null){
                        return this.categoryService.deleteCategory(categoryID);
                    }
                }else {
                    return ResponseEntity.badRequest().body(new Notification("Lỗi khi lấy user name"));
                }
            }else {
                return ResponseEntity.badRequest().body(new Notification("Không lấy được token"));
            }
        }
        return ResponseEntity.badRequest().body(new Notification("gặp lỗi khi tạo hóa đơn "));
    }
}
