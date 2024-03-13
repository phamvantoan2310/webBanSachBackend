package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.roleRepository;
import com.phamvantoan.webBanSachBackend.dao.userRepository;
import com.phamvantoan.webBanSachBackend.dao.wishListRepository;
import com.phamvantoan.webBanSachBackend.entity.Role;
import com.phamvantoan.webBanSachBackend.entity.User;
import com.phamvantoan.webBanSachBackend.entity.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class userServiceImpl implements userService{
    private userRepository userrepository;
    private roleRepository rolerepository;
    private wishListRepository wishlistrepository;
    @Autowired
    public userServiceImpl(userRepository userrepository, roleRepository rolerepository, wishListRepository wishlistrepository){
        this.rolerepository = rolerepository;
        this.userrepository = userrepository;
        this.wishlistrepository = wishlistrepository;
    }

    @Override
    public User findByUserName(String userName) {
        return this.userrepository.findByUserName(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  //cấu hình người dùng với tên đăng nhập, mật khẩu, role
        User user = findByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("Không tìm thấy tên đăng nhập");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), rolesToAuthorities(user.getRoleList()));
    }

    public Collection<? extends GrantedAuthority> rolesToAuthorities(Collection<Role> Roles){
        return Roles.stream().map(role->new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }
    @Override
    public ResponseEntity<?> addWishList(WishList wishlist){
        this.wishlistrepository.save(wishlist);
        return ResponseEntity.ok("Thêm wishlist thành công");
    }

    @Override
    public ResponseEntity<?> deleteWishList(int wishListID) {
        this.wishlistrepository.deleteById(wishListID);
        return ResponseEntity.ok("Xóa thành công");
    }
}
