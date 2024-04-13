package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.controller.bookToWishListResponse;
import com.phamvantoan.webBanSachBackend.dao.wishListRepository;
import com.phamvantoan.webBanSachBackend.entity.Book;
import com.phamvantoan.webBanSachBackend.entity.User;
import com.phamvantoan.webBanSachBackend.entity.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class wishlistServiceImpl implements wishlistService{
    private wishListRepository wishlistrepository;
    private bookService bookservice;

    @Autowired
    public wishlistServiceImpl(wishListRepository wishlistrepository, bookService bookservice){
        this.wishlistrepository = wishlistrepository;
        this.bookservice = bookservice;
    }

    @Override
    public ResponseEntity<?> addWishList(WishList wishlist){
        this.wishlistrepository.save(wishlist);
        return ResponseEntity.ok("Thêm wishlist thành công");
    }

    @Override
    public ResponseEntity<?> deleteWishList(int wishListID) {
        WishList wishList = this.wishlistrepository.findByWishListID(wishListID);  //xóa book trong wistList
        List<Book> books = wishList.getBookList();
        for(Book book : books){
            book.getListWishList().remove(wishList);
        }

        User user = wishList.getUser();
        if(user != null){                                                           //xóa wishlist trong user
            user.getWishListList().remove(wishList);

            wishList.setUser(null);
        }
        this.wishlistrepository.deleteById(wishListID);
        return ResponseEntity.ok("Xóa wishlist thành công");
    }

    @Override
    public ResponseEntity<?> addBookToWishList(bookToWishListResponse booktowishlistresponse) {
        if(booktowishlistresponse != null){
            WishList wishList = this.wishlistrepository.findByWishListID(booktowishlistresponse.getWishListID());

            boolean inBooks = false;

            for(Book book : wishList.getBookList()){
                if(book.getBookID() == booktowishlistresponse.getBookID()){
                    inBooks = true;
                }
            }

            if(inBooks == false){
                Book book = this.bookservice.findByBookID(booktowishlistresponse.getBookID());
                wishList.getBookList().add(book);
                this.wishlistrepository.save(wishList);
                return ResponseEntity.ok("Thêm thành công");
            }else {
                return ResponseEntity.badRequest().body("Sách đã tồn tại");
            }
        }else {
            return ResponseEntity.badRequest().body("Thêm sách không thành công");
        }
    }

    @Override
    public ResponseEntity<?> RemoveBookInWishList(bookToWishListResponse booktowishlistresponse) {
        if(booktowishlistresponse != null){
            System.out.println("WistListID: "+booktowishlistresponse.getWishListID() + "WistListID: "+booktowishlistresponse.getBookID());
            WishList wishList = this.wishlistrepository.findByWishListID(booktowishlistresponse.getWishListID());

            for(Book book : wishList.getBookList()){
                if(book.getBookID() == booktowishlistresponse.getBookID()){
                    book.getListWishList().remove(wishList);
                    wishList.getBookList().remove(book);
                    this.wishlistrepository.save(wishList);
                    return ResponseEntity.ok("Xóa sách thành công");
                }
            }
            return ResponseEntity.badRequest().body("Xóa sách thất bại");
        }else {
            return ResponseEntity.badRequest().body("Xóa sách không thành công");
        }
    }
}
