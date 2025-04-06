package com.phamvantoan.webBanSachBackend.dao;

import com.phamvantoan.webBanSachBackend.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(path = "wish-lists")
public interface wishListRepository extends JpaRepository<WishList, Integer> {
    public List<WishList> findByWishlistName(String wishListName);
    public WishList findByWishListID(int wishListID);
    public boolean existsByWishlistName(String wishlistName);
}
