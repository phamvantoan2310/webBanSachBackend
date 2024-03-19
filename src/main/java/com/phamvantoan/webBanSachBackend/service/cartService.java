package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.cartItemRepository;
import com.phamvantoan.webBanSachBackend.dao.cartRepository;
import com.phamvantoan.webBanSachBackend.entity.Book;
import com.phamvantoan.webBanSachBackend.entity.Cart;
import com.phamvantoan.webBanSachBackend.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class cartService {
    private cartRepository cartrepository;
    private cartItemRepository cartitemrepository;
    @Autowired
    public cartService(cartRepository cartrepository, cartItemRepository cartitemrepository){
        this.cartrepository = cartrepository;
        this.cartitemrepository =cartitemrepository;
    }

    public ResponseEntity<?> addCartItem(int numberOfBook, Book book, Cart cart){
        CartItem cartItem = new CartItem();
        cartItem.setNumberOfCartItem(numberOfBook);
        cartItem.setPrice(numberOfBook * book.getPrice());
        cartItem.setBook(book);
        book.getCartItemList().add(cartItem);
        cartItem.setCart(cart);
        cart.getCartItemList().add(cartItem);
        this.cartitemrepository.save(cartItem);
        return ResponseEntity.ok("Thêm cartItem thành công");
    }

    public ResponseEntity<?> deleteCartItem(CartItem cartItem){
        cartItem.getCart().getCartItemList().remove(cartItem);
        cartItem.setCart(null);
        cartItem.getBook().getCartItemList().remove(cartItem);
        cartItem.setBook(null);
        this.cartitemrepository.delete(cartItem);
        return ResponseEntity.ok("Xóa thành công");
    }

    public ResponseEntity<?> deleteAllCartItem(int cartID){
        Cart cart = this.cartrepository.findByCartID(cartID);

        List<CartItem> cartItems = new ArrayList<>();

        for(CartItem cartItem : cart.getCartItemList()){
            cartItem.getBook().getCartItemList().remove(cartItem);
            cartItems.add(cartItem);
        }

        for(CartItem cartItem : cartItems){
            cart.getCartItemList().remove(cartItem);
            this.cartitemrepository.delete(cartItem);
        }
        return ResponseEntity.ok("Xóa thành công");
    }
}
