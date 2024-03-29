package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.bookRepository;
import com.phamvantoan.webBanSachBackend.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class bookServiceImpl implements bookService{
    private bookRepository bookrepository;
    @Autowired
    public bookServiceImpl(bookRepository bookrepository){
        this.bookrepository = bookrepository;
    }

    @Override
    public Book findByBookID(int bookID) {
        return this.bookrepository.findByBookID(bookID);
    }

    @Override
    public Book save(Book book) {
        return this.bookrepository.save(book);
    }
}
