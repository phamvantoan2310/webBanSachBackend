package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.controller.numberOfBookChangeRespponse;
import com.phamvantoan.webBanSachBackend.dao.bookRepository;
import com.phamvantoan.webBanSachBackend.dao.categoryRepository;
import com.phamvantoan.webBanSachBackend.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class bookServiceImpl implements bookService{
    private bookRepository bookrepository;
    private authorService authorservice;
    private imageService imageservice;
    private evaluateService evaluateservice;
    private orderService orderservice;
    private cartService cartservice;


    @Autowired
    public bookServiceImpl(bookRepository bookrepository, @Lazy authorService authorservice, @Lazy imageService imageservice,@Lazy evaluateService evaluateservice,@Lazy orderService orderservice,@Lazy cartService cartservice){
        this.bookrepository = bookrepository;
        this.authorservice = authorservice;
        this.imageservice = imageservice;
        this.evaluateservice = evaluateservice;
        this.orderservice = orderservice;
        this.cartservice = cartservice;
    }

    @Override
    public Book findByBookID(int bookID) {
        return this.bookrepository.findByBookID(bookID);
    }

    @Override
    public Book save(Book book) {
        return this.bookrepository.save(book);
    }

    @Override
    public ResponseEntity<?> bookChange(Book book, int authorID) {
        Author author = this.authorservice.findByAuthorID(authorID);
        Book book1 = findByBookID(book.getBookID());

        if(book.getBookName() != ""){
            book1.setBookName(book.getBookName());
        }
        if(book.getListedPrice() != 0){
            book1.setListedPrice(book.getListedPrice());
        }
        if(book.getPrice() != 0){
            book1.setPrice(book.getPrice());
        }
        if(author != null){
            author.getBookList().add(book1);
            book1.setAuthor(author);
        }
        if(book.getDecription() != ""){
            book1.setDecription(book.getDecription());
        }

        System.out.println(authorID);

        this.bookrepository.save(book1);

        return ResponseEntity.ok("Lưu thay đổi thành công");
    }

    @Override
    public ResponseEntity<?> numberOfBookChange(int bookID, int numberOfBook) {
        Book book = this.bookrepository.findByBookID(bookID);
        book.setNumberOfBooks(book.getNumberOfBooks() + numberOfBook);
        this.bookrepository.save(book);

        return ResponseEntity.ok("Đổi số lượng thành công");
    }

    @Override
    public ResponseEntity<?> deleteBook(int bookID) {
        try {
            Book book = this.bookrepository.findByBookID(bookID);
            if(book.getImageList()!=null){
                for(Image image : book.getImageList()){
                    this.imageservice.deleteBookImage(image.getImageID());
                }
            }
            if(book.getEvaluateList() != null){
                for(Evaluate evaluate : book.getEvaluateList()){
                    this.evaluateservice.deleteEvaluate(evaluate.getEvaluateID());
                }
            }
            if(book.getOrderItemList() != null){
                for(OrderItem orderItem: book.getOrderItemList()){
                    this.orderservice.deleteOrderItem(orderItem.getOrderItemID());
                }
            }
            if(book.getCategoryList() != null){
                for(Category category: book.getCategoryList()){
                    category.getBookList().remove(book);
                }
            }
            if(book.getCartItemList()!= null){
                for(CartItem cartItem : book.getCartItemList()){
                    this.cartservice.deleteCartItem(cartItem.getCartItemID());
                }
            }
            if(book.getListWishList() != null){
                for(WishList wishList : book.getListWishList()){
                    wishList.getBookList().remove(book);
                }
            }
            if(book.getAuthor() != null){
                book.getAuthor().getBookList().remove(book);
            }
            this.bookrepository.deleteById(book.getBookID());
        }catch (Exception e){
            throw  e;
        }
        return ResponseEntity.ok("Xóa sách thành công");
    }
}
