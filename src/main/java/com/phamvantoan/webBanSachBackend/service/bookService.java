package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.controller.numberOfBookChangeRespponse;
import com.phamvantoan.webBanSachBackend.entity.Book;
import org.springframework.http.ResponseEntity;

public interface bookService {
    public Book findByBookID(int bookID);
    public Book save(Book book);
    public ResponseEntity<?> bookChange(Book book, int authorID, int categoryID);
    public ResponseEntity<?> numberOfBookChange(int bookID, int numberOfBook);
    public ResponseEntity<?> deleteBook(int bookID);
}
