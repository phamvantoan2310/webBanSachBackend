package com.phamvantoan.webBanSachBackend.dao;

import com.phamvantoan.webBanSachBackend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "categorys")
public interface categoryRepository extends JpaRepository<Category, Integer> {
    public Category findByCategoryID(int categoryID);
    public boolean existsByCategoryName(String categoryName);
}
