package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.bookRepository;
import com.phamvantoan.webBanSachBackend.entity.Book;
import com.phamvantoan.webBanSachBackend.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class adminServiceImpl implements adminService{
    @Autowired
    private bookService bookservice;
    @Autowired
    private emailService  emailservice;
    @Value("${email.sender}")
    private String emailSender;
    @Override
    public ResponseEntity<?> addBook(Book book, List<Image> images, String email){
        for (Image image : images) {
            image.setBook(book);
        }
        book.setImageList(images);
        this.bookservice.save(book);
        this.emailservice.sendMessage(emailSender, email,"thêm sách", "Thêm cuốn sách: "+book.getBookName()+" thành công");
        return ResponseEntity.ok("Thêm sách thành công");
    }
}
