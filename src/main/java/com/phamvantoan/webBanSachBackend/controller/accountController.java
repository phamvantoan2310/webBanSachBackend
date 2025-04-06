package com.phamvantoan.webBanSachBackend.controller;

import com.phamvantoan.webBanSachBackend.entity.User;
import com.phamvantoan.webBanSachBackend.service.JwtService;
import com.phamvantoan.webBanSachBackend.service.accountService;
import com.phamvantoan.webBanSachBackend.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from 'http://localhost:3000'
@RequestMapping("/account")
public class accountController {
    @Autowired
    private accountService accountservice;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private userService userservice;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody userResponse userResponse){
        ResponseEntity<?> response = accountservice.registerUser(userResponse.getUser(), userResponse.getRole());
        return response;
    }
    @GetMapping("/activate")
    public ResponseEntity<?> activate(@RequestParam String email, @RequestParam String activationCode){
        ResponseEntity<?> response = accountservice.accountActivate(email, activationCode);
        return  response;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginRequest loginrequest){
        //xác thực người dùng bằng tên đăng nhập và mật khẩu
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginrequest.getUserName(), loginrequest.getPassword()));

            if(authentication.isAuthenticated()){
                User user = this.userservice.findByUserName(loginrequest.getUserName());
                final String jwt = jwtService.generateToken(loginrequest.getUserName(), user.getUserID());

                return ResponseEntity.ok(new JwtResponse(jwt));
            }
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().body("tên đăng nhập hoặc mật khẩu không đúng");
        }
        return ResponseEntity.badRequest().body("Xác thực không thành công");
    }

    @PutMapping("/reactivate")
    public ResponseEntity<?> reactivate(@Validated @RequestBody int userID){
        ResponseEntity<?> response = accountservice.reactivate(userID);
        return response;
    }

    @PutMapping("/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody changePasswordResponse changePasswordResponse, @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            if(token != null){
                String userName = this.jwtService.extractUserName(token);
                if(userName != null){
                    User user = this.userservice.findByUserName(userName);
                    System.out.println(changePasswordResponse + ":" + user.getUserID());
                    return this.userservice.changePassword(changePasswordResponse.getOldPassword(), changePasswordResponse.getNewPassword(), user.getUserID());
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
    @PostMapping("/sendOTP")
    public void sendOTP(@RequestParam String email){
        this.accountservice.sendOTP(email);
    }

    @PostMapping("/checkOTP")
    public ResponseEntity<?> checkOTP(@RequestParam String email, @RequestParam String otp){
        return this.accountservice.checkOTP(email, otp);
    }

    @PutMapping("/changepasswordwhenforgotpassword")
    public ResponseEntity<?> changePasswordWhenForgotPassword(@RequestBody changePasswordWhenForgotPasswordResponse changePasswordWhenForgotPasswordResponse){
        if(this.userservice.existsByEmail(changePasswordWhenForgotPasswordResponse.getEmail())){
            User user = this.userservice.findByEmail(changePasswordWhenForgotPasswordResponse.getEmail());
            return this.userservice.changePasswordWhenForgotPassword(changePasswordWhenForgotPasswordResponse.getNewPassword(), user.getUserID());
        }else {
            return ResponseEntity.badRequest().body("Email chưa được đăng ký");
        }
    }
}
