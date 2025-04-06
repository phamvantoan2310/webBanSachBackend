package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.bookRepository;
import com.phamvantoan.webBanSachBackend.entity.Author;
import com.phamvantoan.webBanSachBackend.entity.Book;
import com.phamvantoan.webBanSachBackend.entity.Category;
import com.phamvantoan.webBanSachBackend.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class adminServiceImpl implements adminService{
    private bookService bookservice;
    private emailService  emailservice;
    private categoryService categoryservice;
    private authorService authorservice;
    @Autowired
    public adminServiceImpl(bookService bookservice, emailService emailservice, categoryService categoryservice, authorService authorservice){
        this.bookservice = bookservice;
        this.emailservice = emailservice;
        this.categoryservice = categoryservice;
        this.authorservice = authorservice;
    }
    @Value("${email.sender}")
    private String emailSender;
    @Override
    public ResponseEntity<?> addBook(Book book, List<Image> images,int categoryID ,String email, int authorID){
        for (Image image : images) {
            image.setBook(book);
        }
        book.setImageList(images);
        Category category = this.categoryservice.findByCategoryID(categoryID);

        if(book.getCategoryList() != null){
            book.getCategoryList().add(category);
            for(Category category1 : book.getCategoryList()){
                category1.getBookList().add(book);
            }
        }else {
            List<Category> categoryList = new ArrayList<Category>();
            categoryList.add(category);
            book.setCategoryList(categoryList);
        }
        Author author = this.authorservice.findByAuthorID(authorID);
        book.setAuthor(author);
        author.getBookList().add(book);

        this.bookservice.save(book);
        this.emailservice.sendMessage(emailSender, email,"thêm sách", "Thêm cuốn sách: "+book.getBookName()+" thành công");
        return ResponseEntity.ok("Thêm sách thành công");
    }
}
