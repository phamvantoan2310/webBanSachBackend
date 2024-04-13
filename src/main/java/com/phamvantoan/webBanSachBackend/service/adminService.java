package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.entity.Book;
import com.phamvantoan.webBanSachBackend.entity.Image;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface adminService {
    public ResponseEntity<?> addBook(Book book, List<Image> images,int categoryID, String email);
}
