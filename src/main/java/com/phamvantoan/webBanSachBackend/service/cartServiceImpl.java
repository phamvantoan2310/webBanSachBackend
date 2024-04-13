package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.bookRepository;
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
public class cartServiceImpl implements cartService{
    private cartRepository cartrepository;
    private cartItemRepository cartitemrepository;
    private bookService bookservice;
    @Autowired
    public cartServiceImpl(cartRepository cartrepository, cartItemRepository cartitemrepository, bookService bookservice){
        this.cartrepository = cartrepository;
        this.cartitemrepository =cartitemrepository;
        this.bookservice = bookservice;
    }
    @Override
    public ResponseEntity<?> addCartItem(int numberOfBook, int bookID, Cart cart){
        CartItem cartItem = new CartItem();
        cartItem.setNumberOfCartItem(numberOfBook);
        Book book = this.bookservice.findByBookID(bookID);
        cartItem.setPrice(numberOfBook * book.getPrice());
        cartItem.setBook(book);
        book.getCartItemList().add(cartItem);
        if(cart.getCartItemList() != null){
            cartItem.setCart(cart);
            cart.getCartItemList().add(cartItem);
        }
        this.cartitemrepository.save(cartItem);
        return ResponseEntity.ok("Thêm cartItem thành công");
    }
    @Override
    public ResponseEntity<?> deleteCartItem(int cartItemID){
        CartItem cartItem = this.cartitemrepository.findByCartItemID(cartItemID);
        cartItem.getCart().getCartItemList().remove(cartItem);
        cartItem.setCart(null);
        cartItem.getBook().getCartItemList().remove(cartItem);
        cartItem.setBook(null);
        this.cartitemrepository.delete(cartItem);
        return ResponseEntity.ok("Xóa thành công");
    }
    @Override
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

    @Override
    public ResponseEntity<?> deleteCart(int cartID) {
        Cart cart = this.cartrepository.findByCartID(cartID);
        deleteAllCartItem(cartID);
        cart.getUser().setCart(null);
        cart.setUser(null);
        this.cartrepository.delete(cart);
        return ResponseEntity.ok("Xóa giỏ hàng thành công");
    }
}
