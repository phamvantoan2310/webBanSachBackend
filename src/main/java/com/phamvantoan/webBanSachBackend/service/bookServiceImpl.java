package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.controller.numberOfBookChangeRespponse;
import com.phamvantoan.webBanSachBackend.dao.bookRepository;
import com.phamvantoan.webBanSachBackend.dao.categoryRepository;
import com.phamvantoan.webBanSachBackend.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class bookServiceImpl implements bookService{
    private bookRepository bookrepository;
    private authorService authorservice;
    private imageService imageservice;
    private evaluateService evaluateservice;
    private orderService orderservice;
    private cartService cartservice;
    private categoryService categoryservice;


    @Autowired
    public bookServiceImpl(bookRepository bookrepository, @Lazy authorService authorservice, @Lazy imageService imageservice,@Lazy evaluateService evaluateservice,@Lazy orderService orderservice,@Lazy cartService cartservice, @Lazy categoryService categoryservice){
        this.bookrepository = bookrepository;
        this.authorservice = authorservice;
        this.imageservice = imageservice;
        this.evaluateservice = evaluateservice;
        this.orderservice = orderservice;
        this.cartservice = cartservice;
        this.categoryservice = categoryservice;
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
    public ResponseEntity<?> bookChange(Book book, int authorID, int categoryID) {
        Author author = this.authorservice.findByAuthorID(authorID);
        Category category = this.categoryservice.findByCategoryID(categoryID);
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
        if(category != null){
            category.getBookList().clear();
            category.getBookList().add(book1);
            book1.getCategoryList().clear();
            book1.getCategoryList().add(category);
        }
        if(book.getDecription() != ""){
            book1.setDecription(book.getDecription());
        }
        if(book.getContent() != ""){
            book1.setContent(book.getContent());
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

            if(book == null){
                return ResponseEntity.badRequest().body("Không tìm thấy sách");
            }


            // Xóa orderItemList
            if (book.getOrderItemList() != null) {
                Iterator<OrderItem> orderItemIterator = book.getOrderItemList().iterator();
                while (orderItemIterator.hasNext()) {
                    OrderItem orderItem = orderItemIterator.next();
                    this.orderservice.deleteOrderItem(orderItem.getOrderItemID());
                    orderItemIterator.remove();
                }
            }

            // Cập nhật categoryList
            if (book.getCategoryList() != null) {
                for (Category category : book.getCategoryList()) {
                    category.getBookList().remove(book);
                }
                book.getCategoryList().clear();
            }

            // Xóa cartItemList
            if (book.getCartItemList() != null) {
                Iterator<CartItem> cartItemIterator = book.getCartItemList().iterator();
                while (cartItemIterator.hasNext()) {
                    CartItem cartItem = cartItemIterator.next();
                    this.cartservice.deleteCartItem(cartItem.getCartItemID());
                    cartItemIterator.remove();
                }
            }

            // Cập nhật listWishList
            if (book.getListWishList() != null) {
                for (WishList wishList : book.getListWishList()) {
                    wishList.getBookList().remove(book);
                }
                book.getBillItemList().clear();
            }

            // Cập nhật author
            if (book.getAuthor() != null) {
                book.getAuthor().getBookList().remove(book);
            }

            // Xóa evaluateList
            if (book.getEvaluateList() != null) {
                Iterator<Evaluate> evaluateIterator = book.getEvaluateList().iterator();
                while (evaluateIterator.hasNext()) {
                    Evaluate evaluate = evaluateIterator.next();
                    this.evaluateservice.deleteEvaluate(evaluate.getEvaluateID());
                    evaluateIterator.remove();
                }
            }

            // Xóa imageList
            if (book.getImageList() != null) {
                Iterator<Image> imageIterator = book.getImageList().iterator();
                while (imageIterator.hasNext()) {
                    Image image = imageIterator.next();
                    this.imageservice.deleteBookImage(image.getImageID());
                    imageIterator.remove(); // Xóa an toàn khi đang duyệt
                }
            }

            // Xóa sách
            this.bookrepository.deleteById(book.getBookID());

            return ResponseEntity.ok("Xóa sách thành công");
        } catch (Exception e) {
            throw e;
        }
    }
}
