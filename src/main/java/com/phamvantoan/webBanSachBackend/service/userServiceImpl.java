package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.controller.bookToWishListResponse;
import com.phamvantoan.webBanSachBackend.controller.changeInformationUserResponse;
import com.phamvantoan.webBanSachBackend.dao.bookRepository;
import com.phamvantoan.webBanSachBackend.dao.roleRepository;
import com.phamvantoan.webBanSachBackend.dao.userRepository;
import com.phamvantoan.webBanSachBackend.dao.wishListRepository;
import com.phamvantoan.webBanSachBackend.entity.Book;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class userServiceImpl implements userService{
    private userRepository userrepository;
    @Autowired
    public userServiceImpl(userRepository userrepository){
        this.userrepository = userrepository;
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
    public ResponseEntity<?> changeInformationUser(User user, changeInformationUserResponse changeinformationuserresponse) {
        if(changeinformationuserresponse.getPhoneNumber()!=""){
            user.setPhoneNumber(changeinformationuserresponse.getPhoneNumber());
        }
        if(changeinformationuserresponse.getEmail() != ""){
            user.setEmail(changeinformationuserresponse.getEmail());
        }
        if(changeinformationuserresponse.getAddress() != ""){
            user.setAddress(changeinformationuserresponse.getAddress());
        }
        this.userrepository.save(user);
        return ResponseEntity.ok("Đổi thông tin thành công");
    }

    @Override
    public User save(User user) {
        return this.userrepository.save(user);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return this.userrepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.userrepository.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        return this.userrepository.findByEmail(email);
    }
}
