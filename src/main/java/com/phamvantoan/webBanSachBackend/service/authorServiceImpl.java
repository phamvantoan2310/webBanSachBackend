package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.authorRepository;
import com.phamvantoan.webBanSachBackend.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class authorServiceImpl implements authorService{
    private authorRepository authorrepository;
    @Autowired
    public authorServiceImpl(authorRepository authorrepository){
        this.authorrepository = authorrepository;
    }
    @Override
    public Author findByAuthorID(int authorID) {
        return this.authorrepository.findByAuthorID(authorID);
    }
}
