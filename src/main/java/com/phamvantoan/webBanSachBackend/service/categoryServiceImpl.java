package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.categoryRepository;
import com.phamvantoan.webBanSachBackend.entity.Book;
import com.phamvantoan.webBanSachBackend.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public ResponseEntity<?> createCategory(String categoryName) {
        if(!this.categoryrepository.existsByCategoryName(categoryName)){
            Category category = new Category();
            category.setCategoryName(categoryName);

            List<Book> books = new ArrayList<>();
            category.setBookList(books);

            this.categoryrepository.save(category);
            return ResponseEntity.ok("Tạo thể loại thành công");
        }else {
            return ResponseEntity.badRequest().body("Thể loại đã tồn tại");
        }
    }

    @Override
    public ResponseEntity<?> deleteCategory(int categoryID) {
        Category category = this.categoryrepository.findByCategoryID(categoryID);
        if(category != null){
            category.getBookList().clear();
            for (Book book: category.getBookList()) {
                book.getCategoryList().remove(category);
            }
            this.categoryrepository.delete(category);
            return ResponseEntity.ok("Xóa thể loại thành công");
        }else {
            return ResponseEntity.badRequest().body("Thể loại không tồn tại!");
        }
    }
}
