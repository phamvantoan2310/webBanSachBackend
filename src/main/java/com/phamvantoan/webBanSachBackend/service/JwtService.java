package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.entity.Role;
import com.phamvantoan.webBanSachBackend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    public static final String serect = "19478365465b19848259b29352175b129f2198529367ak2829835";
    @Autowired
    private userService userservice;

    //tạo JWT dựa trên tên đăng nhập
    public String generateToken(String username, int userID){
        Map<String, Object> claims = new HashMap<>();

        User user = this.userservice.findByUserName(username); //
        boolean isAdmin = false;                               //
        boolean isStaff = false;                               //
        boolean isUser = false;                                //
        if(user!=null && user.getRoleList().size() > 0){       //
            List<Role> roleList = user.getRoleList();          //
            for (Role role : roleList){                        //
                if(role.getRoleName().equals("ADMIN")){        //
                    isAdmin = true;                            //
                }                                              // lấy role gửi cho frontend
                if(role.getRoleName().equals("STAFF")){        //
                    isStaff = true;                            //
                }                                              //
                if(role.getRoleName().equals("USER")){         //
                    isUser = true;                             //
                }                                              //
            }                                                  //
        }                                                      //
        claims.put("isAdmin", isAdmin);                        //
        claims.put("isStaff", isStaff);                        //
        claims.put("isUser", isUser);                          //

        return createToken(claims, username, userID);
    }

    //tạo JWT với claim đã chọn
    private String createToken(Map<String, Object> claims, String username, int userID){
        return Jwts.builder()
                .setClaims(claims)  //set claims
                .setSubject(username) //set subject là tên đăng nhập
                .setId(String.valueOf(userID))
                .setIssuedAt(new Date(System.currentTimeMillis())) //set thời gian ban hành
                .setExpiration(new Date(System.currentTimeMillis()+24*60*60*1000)) //set thời gian hết hạn 1 ngày
                .signWith(SignatureAlgorithm.HS256, getSigneKey())
                .compact();
    }

    //lấy serect key
    private Key getSigneKey(){
        byte [] keyBytes = Decoders.BASE64.decode(serect);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //extract/trích xuất thông tin claim
    private Claims extractAllClaim(String token){
        return Jwts.parserBuilder().setSigningKey(getSigneKey()).build().parseClaimsJws(token).getBody();  //bị nhầm thành parseClaimsJwt
    }

    //extract thông tin của một claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction){
        final Claims claims = extractAllClaim(token);
        return claimsTFunction.apply(claims);
    }

    //extract tên đăng nhập
    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    //extract thời gian hết hạn của token
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    //kiểm tra thời gian hết hạn
    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    //kiểm tra sự hợp lệ của token
    public boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
