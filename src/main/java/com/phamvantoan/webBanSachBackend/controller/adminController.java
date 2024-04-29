package com.phamvantoan.webBanSachBackend.controller;

import com.phamvantoan.webBanSachBackend.entity.Meeting;
import com.phamvantoan.webBanSachBackend.entity.Notification;
import com.phamvantoan.webBanSachBackend.entity.Revenue;
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
    private meetingService meetingservice;
    private revenueService revenueservice;
    @Autowired
    public adminController(adminService adminservice, JwtService jwtService, userService userservice, meetingService meetingservice, revenueService revenueservice){
        this.adminservice = adminservice;
        this.jwtService = jwtService;
        this.userservice = userservice;
        this.meetingservice = meetingservice;
        this.revenueservice = revenueservice;
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
                        this.adminservice.addBook(addbookresponse.getBook(),addbookresponse.getImages(), addbookresponse.getCategoryID(), user.getEmail());
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

    @PostMapping("/createmeeting")
    public ResponseEntity<?> createMeeting(@RequestBody createMeetingResponse createmeetingresponse){
        return meetingservice.createMeeting(createmeetingresponse.getMeeting(), createmeetingresponse.getStaffIDs());
    }
    @DeleteMapping("/cancelmeeting")
    public ResponseEntity<?> cancelMeeting(@RequestBody int meetingID){
        return this.meetingservice.cancelMeeting(meetingID);
    }
    @GetMapping("/getrevenuebyrevenuedate")
    public Revenue getRevenueByRevenueDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate revenueDate){
        return this.revenueservice.getByRevenueDate(revenueDate);
    }
    @PutMapping("/updatestaff")
    public ResponseEntity<?> updateStaff(@Validated @RequestBody User user){
        return this.userservice.adminUpdateStaff(user);
    }
}
