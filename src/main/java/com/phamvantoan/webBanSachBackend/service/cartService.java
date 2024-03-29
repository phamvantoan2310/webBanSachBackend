package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.entity.Book;
import com.phamvantoan.webBanSachBackend.entity.Cart;
import com.phamvantoan.webBanSachBackend.entity.CartItem;
import org.springframework.http.ResponseEntity;

public interface cartService {
    public ResponseEntity<?> addCartItem(int numberOfBook, int bookID, Cart cart);
    public ResponseEntity<?> deleteCartItem(int cartItemID);
    public ResponseEntity<?> deleteAllCartItem(int cartID);
}
