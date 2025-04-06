package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.controller.bookToWishListResponse;
import com.phamvantoan.webBanSachBackend.controller.changeInformationUserResponse;
import com.phamvantoan.webBanSachBackend.dao.bookRepository;
import com.phamvantoan.webBanSachBackend.dao.roleRepository;
import com.phamvantoan.webBanSachBackend.dao.userRepository;
import com.phamvantoan.webBanSachBackend.dao.wishListRepository;
import com.phamvantoan.webBanSachBackend.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class userServiceImpl implements userService{
    private userRepository userrepository;
    private roleService roleservice;
    private BCryptPasswordEncoder passwordEncoder;
    private wishlistService wishlistservice;
    private evaluateService evaluateservice;
    private cartService cartservice;
    private orderService orderservice;
    private reportService reportservice;
    private emailService emailService;
    @Autowired
    public userServiceImpl(userRepository userrepository, roleService roleservice,@Lazy BCryptPasswordEncoder passwordEncoder,@Lazy wishlistService wishlistservice,@Lazy evaluateService evaluateservice,@Lazy cartService cartservice,@Lazy orderService orderservice,@Lazy reportService reportservice, @Lazy emailService emailService){
        this.userrepository = userrepository;
        this.roleservice = roleservice;
        this.passwordEncoder = passwordEncoder;
        this.wishlistservice = wishlistservice;
        this.cartservice = cartservice;
        this.evaluateservice = evaluateservice;
        this.orderservice = orderservice;
        this.reportservice = reportservice;
        this.emailService = emailService;
    }

    @Value("${email.sender}")
    private String emailSender;

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
        if(changeinformationuserresponse.getUserName()!=""){
            user.setUserName(changeinformationuserresponse.getUserName());
        }
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
    public User save(User user, int roleUser) {
        if(roleUser != 0){
            Role role = this.roleservice.findByRoleID(roleUser);
            if(user.getRoleList() == null){
                List<Role> roles = new ArrayList<>();
                user.setRoleList(roles);
            }
            user.getRoleList().add(role);
        }
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

    @Override
    public ResponseEntity<?> staffUpdateUser(User user) {
        try {
            if(user != null){
                User user1 = this.userrepository.findByUserID(user.getUserID());
                if(user.getUserName() != ""){
                    user1.setUserName(user.getUserName());
                }
                if(user.getPassword() != ""){
                    String bcryptEncoder = this.passwordEncoder.encode(user.getPassword());
                    user1.setPassword(bcryptEncoder);
                }
                if(user.getPhoneNumber() != ""){
                    user1.setPhoneNumber(user.getPhoneNumber());
                }
                if(user.getEmail() != ""){
                    user1.setEmail(user.getEmail());
                }
                if(user.getAddress() != ""){
                    user1.setAddress(user.getAddress());
                }
                if(user.isSex()){
                    user1.setSex(true);
                }else {
                    user1.setSex(false);
                }
                this.userrepository.save(user1);
                this.emailService.sendMessage(emailSender, user1.getEmail(), "Thông báo thay đổi thông tin tài khoản", "Quản trị viên đã thay đổi thông tin tài khoản của bạn tại Bookstore, đăng nhập để xem thay đổi mới nhất.");
                return ResponseEntity.ok("Update user thành công");
            }else {
                return ResponseEntity.badRequest().body("Update thất bại do không có user");
            }
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public ResponseEntity<?> deleteUser(int userID, int userIDInToken) {
        try {
            User user = this.userrepository.findByUserID(userID);

            List<WishList> wishLists = new ArrayList<>();
            List<Evaluate> evaluates = new ArrayList<>();
            List<Report> reports =new ArrayList<>() ;
            List<Orders> orders = new ArrayList<>();

            for(WishList wishList : user.getWishListList()){
                wishLists.add(wishList);
            }
            for(Evaluate evaluate : user.getEvaluateList()){
                evaluates.add(evaluate);
            }
            for(Orders order : user.getOrderList()){
                orders.add(order);
            }
            for(Report report : user.getReportList()){
                reports.add(report);
            }


            for(WishList wishList : wishLists){
                this.wishlistservice.deleteWishList(wishList.getWishListID(), userID);
            }
            for(Evaluate evaluate : evaluates){
                this.evaluateservice.deleteEvaluate(evaluate.getEvaluateID());
            }
            if(user.getCart() != null){
                this.cartservice.deleteCart(user.getCart().getCartID(), userIDInToken);
            }
            for(Orders order : orders){
                this.orderservice.deleteOrder(order.getOrderID(), userID);
            }
            for(Report report : reports){
                this.reportservice.deleteReport(report);
            }
            Iterator<Role> iterator = user.getRoleList().iterator();
            while (iterator.hasNext()) {
                Role role = iterator.next();
                role.getUserList().remove(user);

                iterator.remove();
            }
            this.emailService.sendMessage(emailSender, user.getEmail(), "Thông báo cập nhật thông tin tài khoản.", "Quản trị viên đã xóa tài khoản của bạn tại Bookstore, vui lòng liên hệ với chúng tôi qua số điện thoại để biết thêm chi tiết.");
            this.userrepository.delete(user);
            return ResponseEntity.ok("Xóa khách hàng thành công");
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public User findByUserID(int userID) {
        return this.userrepository.findByUserID(userID);
    }

    @Override
    public ResponseEntity<?> adminUpdateStaff(User user) {
        try {
            if(user != null){
                User user1 = this.userrepository.findByUserID(user.getUserID());
                if(user.getUserName() != ""){
                    user1.setUserName(user.getUserName());
                }
                if(user.getPhoneNumber() != ""){
                    user1.setPhoneNumber(user.getPhoneNumber());
                }
                if(user.getEmail() != ""){
                    user1.setEmail(user.getEmail());
                }
                if(user.getAddress() != ""){
                    user1.setAddress(user.getAddress());
                }
                if(user.getAvatar() != ""){
                    user1.setAvatar(user.getAvatar());
                }

                this.userrepository.save(user1);
                this.emailService.sendMessage(emailSender, user1.getEmail(), "Thông báo thay đổi thông tin tài khoản", "Quản trị viên đã thay đổi thông tin tài khoản của bạn tại Bookstore, đăng nhập để xem thay đổi mới nhất.");
                return ResponseEntity.ok("Update staff thành công");
            }else {
                return ResponseEntity.badRequest().body("Update thất bại do không có staff");
            }
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public ResponseEntity<?> changePassword(String oldPassword, String newPassword, int userID) {
        User user = this.userrepository.findByUserID(userID);
        if(user != null){
            if(passwordEncoder.matches(oldPassword, user.getPassword())){
                String bcryptEncoder = passwordEncoder.encode(newPassword);
                user.setPassword(bcryptEncoder);
                this.userrepository.save(user);
                return ResponseEntity.ok("Cập nhật mật khẩu thành công");
            }else {
                return ResponseEntity.badRequest().body("Sai mật khẩu!");
            }
        }else {
            return ResponseEntity.badRequest().body("Mã người dùng không hợp lệ!");
        }
    }

    @Override
    public ResponseEntity<?> changePasswordWhenForgotPassword(String newPassword, int userID) {
        User user = this.userrepository.findByUserID(userID);
        user.setPassword(this.passwordEncoder.encode(newPassword));
        this.userrepository.save(user);
        return ResponseEntity.ok("Đổi mật khẩu thành công");
    }
}
