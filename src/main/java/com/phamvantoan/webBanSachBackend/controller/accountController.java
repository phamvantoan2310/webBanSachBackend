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
    public ResponseEntity<?> register(@Validated @RequestBody User user){
        ResponseEntity<?> response = accountservice.registerUser(user);
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

            //nếu xác thực thành công thì tạo token
            if(authentication.isAuthenticated()){
                User user = this.userservice.findByUserName(loginrequest.getUserName());
                final String jwt = jwtService.generateToken(loginrequest.getUserName(), user.getUserID());
                //gửi token cho frontend
                return ResponseEntity.ok(new JwtResponse(jwt));
            }
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().body("tên đăng nhập hoặc mật khẩu không đúng");
        }
        return ResponseEntity.badRequest().body("Xác thực không thành công");
    }
}
