package com.phamvantoan.webBanSachBackend.controller;


import com.phamvantoan.webBanSachBackend.entity.Book;
import com.phamvantoan.webBanSachBackend.entity.Notification;
import com.phamvantoan.webBanSachBackend.entity.User;
import com.phamvantoan.webBanSachBackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/staff")
public class staffController {
    private imageService imageservice;
    private bookService bookservice;
    private reportService reportservice;
    private orderService orderservice;
    private userService userservice;
    private JwtService jwtService;
    @Autowired
    public staffController(imageService imageservice,bookService bookservice, reportService reportservice, orderService orderservice, userService userservice, JwtService jwtService){
        this.imageservice = imageservice;
        this.bookservice = bookservice;
        this.reportservice = reportservice;
        this.orderservice = orderservice;
        this.userservice = userservice;
        this.jwtService = jwtService;
    }
    @PostMapping("/addbookimage")
    public ResponseEntity<?> addBookImage(@RequestBody addBookImageResponse addbookimageresponse, @RequestHeader("Authorization") String authorizationHeader){
        return this.imageservice.save(addbookimageresponse.getImage(), addbookimageresponse.getBookID());
    }
    @DeleteMapping("/deletebookimage")
    public ResponseEntity<?> deleteBookImage(@RequestBody int imageID){
        return this.imageservice.deleteBookImage(imageID);
    }
    @PutMapping("/bookchange")
    public ResponseEntity<?> bookChange(@RequestBody bookChangeResponse bookchangeresponse,@RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                UserDetails userDetails = this.userservice.loadUserByUsername(userName);
                if(userName != null && this.jwtService.validateToken(token, userDetails)){
                    User user = this.userservice.findByUserName((userName));
                    if(user != null){
                        this.bookservice.bookChange(bookchangeresponse.getBook(), bookchangeresponse.getAuthorID(), bookchangeresponse.getCategoryID());
                        return ResponseEntity.ok("Cập nhật sách thành công");
                    }
                }else {
                    return ResponseEntity.badRequest().body(new Notification("Lỗi khi lấy user name"));
                }
            }else {
                return ResponseEntity.badRequest().body(new Notification("Không lấy được token"));
            }
        }
        return ResponseEntity.badRequest().body(new Notification("gặp lỗi khi cập nhật sách"));
    }
    @PutMapping("/numberofbookchange")
    public ResponseEntity<?> numberOfBookChange(@RequestBody numberOfBookChangeRespponse numberofbookchangerespponse,@RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                UserDetails userDetails = this.userservice.loadUserByUsername(userName);
                if(userName != null && this.jwtService.validateToken(token, userDetails)){
                    User user = this.userservice.findByUserName((userName));
                    if(user != null){
                        this.bookservice.numberOfBookChange(numberofbookchangerespponse.getBookID(), numberofbookchangerespponse.getNumberOfBook());
                        return ResponseEntity.ok("Cập nhật sách thành công");
                    }
                }else {
                    return ResponseEntity.badRequest().body(new Notification("Lỗi khi lấy user name"));
                }
            }else {
                return ResponseEntity.badRequest().body(new Notification("Không lấy được token"));
            }
        }
        return ResponseEntity.badRequest().body(new Notification("gặp lỗi khi cập nhật sách"));
    }
    @DeleteMapping("/deletebook")
    public ResponseEntity<?> deleteBook(@RequestBody int bookID,@RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                UserDetails userDetails = this.userservice.loadUserByUsername(userName);
                if(userName != null && this.jwtService.validateToken(token, userDetails)){
                    User user = this.userservice.findByUserName((userName));
                    if(user != null){
                        this.bookservice.deleteBook(bookID);
                        return ResponseEntity.ok("Xóa sách thành công");
                    }
                }else {
                    return ResponseEntity.badRequest().body(new Notification("Lỗi khi lấy user name"));
                }
            }else {
                return ResponseEntity.badRequest().body(new Notification("Không lấy được token"));
            }
        }
        return ResponseEntity.badRequest().body(new Notification("gặp lỗi khi xóa sách"));
    }
    @PostMapping("/sendreportresponse")
    public ResponseEntity<?> sendReportResponse(@RequestBody responseSendData responsesenddata,@RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                UserDetails userDetails = this.userservice.loadUserByUsername(userName);
                if(userName != null && this.jwtService.validateToken(token, userDetails)){
                    User user = this.userservice.findByUserName((userName));
                    if(user != null){
                        this.reportservice.addReportResponse(responsesenddata.getReportID(), responsesenddata.getResponseReport());
                        return ResponseEntity.ok("Gửi báo cáo thành công");
                    }
                }else {
                    return ResponseEntity.badRequest().body(new Notification("Lỗi khi lấy user name"));
                }
            }else {
                return ResponseEntity.badRequest().body(new Notification("Không lấy được token"));
            }
        }
        return ResponseEntity.badRequest().body(new Notification("gặp lỗi khi gửi báo cáo"));
    }
    @PutMapping("/confirmorder")
    public ResponseEntity<?> confirmOrder(@RequestBody int orderID,@RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                UserDetails userDetails = this.userservice.loadUserByUsername(userName);
                if(userName != null && this.jwtService.validateToken(token, userDetails)){
                    User user = this.userservice.findByUserName((userName));
                    if(user != null){
                        this.orderservice.confirmOrder(orderID);
                        return ResponseEntity.ok("Xác nhận đơn hàng thành công");
                    }
                }else {
                    return ResponseEntity.badRequest().body(new Notification("Lỗi khi lấy user name"));
                }
            }else {
                return ResponseEntity.badRequest().body(new Notification("Không lấy được token"));
            }
        }
        return ResponseEntity.badRequest().body(new Notification("gặp lỗi khi xác nhận đơn hàng"));
    }
    @PutMapping("/updateuser")
    public ResponseEntity<?> updateUser(@Validated @RequestBody User user,@RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                UserDetails userDetails = this.userservice.loadUserByUsername(userName);
                if(userName != null && this.jwtService.validateToken(token, userDetails)){
                    User user1 = this.userservice.findByUserName((userName));
                    if(user1 != null){
                        this.userservice.staffUpdateUser(user);
                        return ResponseEntity.ok("Cập nhật tài khoản thành công");
                    }
                }else {
                    return ResponseEntity.badRequest().body(new Notification("Lỗi khi lấy user name"));
                }
            }else {
                return ResponseEntity.badRequest().body(new Notification("Không lấy được token"));
            }
        }
        return ResponseEntity.badRequest().body(new Notification("gặp lỗi khi cập nhật tài khoản"));
    }
    @DeleteMapping("/deleteuser")
    public ResponseEntity<?> deleteUser(@Validated @RequestBody int userID,@RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                UserDetails userDetails = this.userservice.loadUserByUsername(userName);
                if(userName != null && this.jwtService.validateToken(token, userDetails)){
                    User user1 = this.userservice.findByUserName((userName));
                    if(user1 != null){
                        this.userservice.deleteUser(userID, user1.getUserID());
                        return ResponseEntity.ok("Xóa tài khoản thành công");
                    }
                }else {
                    return ResponseEntity.badRequest().body(new Notification("Lỗi khi lấy user name"));
                }
            }else {
                return ResponseEntity.badRequest().body(new Notification("Không lấy được token"));
            }
        }
        return ResponseEntity.badRequest().body(new Notification("gặp lỗi khi xóa tài khoản"));
    }
}
