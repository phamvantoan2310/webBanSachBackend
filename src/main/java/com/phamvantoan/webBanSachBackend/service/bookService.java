package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.entity.Book;

public interface bookService {
    public Book findByBookID(int bookID);
    public Book save(Book book);
}
