package com.phamvantoan.webBanSachBackend.dao;

import com.phamvantoan.webBanSachBackend.entity.User;
import com.phamvantoan.webBanSachBackend.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(path = "users")
public interface userRepository extends JpaRepository<User, Integer> {
    public boolean existsByUserName(String userName);
    public boolean existsByEmail(String email);
    public User findByUserName(String userName);
    public User findByEmail(String email);
}
