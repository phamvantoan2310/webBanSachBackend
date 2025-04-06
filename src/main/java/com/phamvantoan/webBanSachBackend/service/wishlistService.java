package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.controller.bookToWishListResponse;
import com.phamvantoan.webBanSachBackend.entity.WishList;
import org.springframework.http.ResponseEntity;

public interface wishlistService {
    public ResponseEntity<?> addWishList(WishList wishList);
    public ResponseEntity<?> deleteWishList(int wishListID, int userID);
    public ResponseEntity<?> addBookToWishList(bookToWishListResponse booktowishlistresponse);
    public ResponseEntity<?> RemoveBookInWishList(bookToWishListResponse booktowishlistresponse);
    public ResponseEntity<?> changeWishlistName(int wishListID, String wishlistName, int userID);
}
