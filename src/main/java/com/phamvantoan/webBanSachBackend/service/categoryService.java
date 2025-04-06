package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.entity.Category;
import org.springframework.http.ResponseEntity;

public interface categoryService {
    public Category findByCategoryID(int categoryID);
    public ResponseEntity<?> createCategory(String categoryName);
    public ResponseEntity<?> deleteCategory(int categoryID);
}
