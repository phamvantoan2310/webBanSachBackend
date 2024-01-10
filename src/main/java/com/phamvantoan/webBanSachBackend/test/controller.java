package com.phamvantoan.webBanSachBackend.test;

import com.phamvantoan.webBanSachBackend.dao.authorRepository;
import com.phamvantoan.webBanSachBackend.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
public class controller {
    private authorRepository authorrepository;
    @Autowired
    public controller(authorRepository au){
        this.authorrepository = au;
    }
    @GetMapping
    public void test(){
        Optional<Author> listAuthor = authorrepository.findById(1);
    }

}
