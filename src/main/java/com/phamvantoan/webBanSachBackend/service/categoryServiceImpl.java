package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.categoryRepository;
import com.phamvantoan.webBanSachBackend.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class categoryServiceImpl implements categoryService{
    private categoryRepository categoryrepository;
    @Autowired
    public categoryServiceImpl(categoryRepository categoryrepository){
        this.categoryrepository = categoryrepository;
    }
    @Override
    public Category findByCategoryID(int categoryID) {
        return this.categoryrepository.findByCategoryID(categoryID);
    }
}
