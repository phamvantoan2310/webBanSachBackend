package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.bookRepository;
import com.phamvantoan.webBanSachBackend.entity.Book;
import com.phamvantoan.webBanSachBackend.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;

@Service
public class adminService {
    @Autowired
    private bookRepository bookrepository;
    @Autowired
    private emailService  emailservice;

    public ResponseEntity<?> addBook(Book book, List<Image> images, String email){
        for (Image image : images) {
            image.setBook(book);
        }
        book.setImageList(images);
        this.bookrepository.save(book);
        this.emailservice.sendMessage("hgffhchdu@gmail.com", email,"thêm sách", "Thêm cuốn sách: "+book.getBookName()+" thành công");
        return ResponseEntity.ok("Thêm sách thành công");
    }
}
