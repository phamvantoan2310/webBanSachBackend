package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.entity.User;
import com.phamvantoan.webBanSachBackend.entity.WishList;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface userService extends UserDetailsService {
    public User findByUserName(String userName);
    public ResponseEntity<?> addWishList(WishList wishList);
    public ResponseEntity<?> deleteWishList(int wishListID);

}
