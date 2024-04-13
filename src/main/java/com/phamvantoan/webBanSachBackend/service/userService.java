package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.controller.bookToWishListResponse;
import com.phamvantoan.webBanSachBackend.controller.changeInformationUserResponse;
import com.phamvantoan.webBanSachBackend.entity.User;
import com.phamvantoan.webBanSachBackend.entity.WishList;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface userService extends UserDetailsService {
    public User findByUserName(String userName);
    public ResponseEntity<?> changeInformationUser(User user, changeInformationUserResponse changeinformationuserresponse);

    public User save(User user);
    public boolean existsByUserName(String userName);
    public boolean existsByEmail(String email);
    public User findByEmail(String email);

    public ResponseEntity<?> staffUpdateUser(User user);
    public ResponseEntity<?> deleteUser(int userID);
}
