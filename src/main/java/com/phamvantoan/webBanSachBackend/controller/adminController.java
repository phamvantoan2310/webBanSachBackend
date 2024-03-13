package com.phamvantoan.webBanSachBackend.controller;

import com.phamvantoan.webBanSachBackend.entity.Book;
import com.phamvantoan.webBanSachBackend.entity.Image;
import com.phamvantoan.webBanSachBackend.entity.Notification;
import com.phamvantoan.webBanSachBackend.entity.User;
import com.phamvantoan.webBanSachBackend.service.JwtService;
import com.phamvantoan.webBanSachBackend.service.adminService;
import com.phamvantoan.webBanSachBackend.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin")
public class adminController {
    @Autowired
    private adminService adminservice;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private userService userservice;

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
                        this.adminservice.addBook(addbookresponse.getBook(),addbookresponse.getImages(), user.getEmail());
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

}
