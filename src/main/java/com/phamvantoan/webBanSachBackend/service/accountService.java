package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.userRepository;
import com.phamvantoan.webBanSachBackend.entity.Cart;
import com.phamvantoan.webBanSachBackend.entity.Notification;
import com.phamvantoan.webBanSachBackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class accountService {
    @Autowired
    private userRepository userrepository;
    @Autowired
    private emailService emailservice;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<?> registerUser(User user){
        if(userrepository.existsByUserName(user.getUserName())){
            return ResponseEntity.badRequest().body(new Notification("Tên đăng nhập đã tồn tại"));
        }
        if(userrepository.existsByEmail(user.getEmail())){
            return  ResponseEntity.badRequest().body(new Notification("Email đã được sử dụng"));
        }
        //mã hóa mật khẩu
        String bcryptEncoder = passwordEncoder.encode(user.getPassword());
        user.setPassword(bcryptEncoder);

        //tạo mã kích hoạt và trạng thái hoạt động của tài khoản
        user.setActivationCode(createActivationCode());
        user.setAccountStatus(false);

        //lưu thông tin vào db
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setDeliveryAddress(user.getAddress());
        user.setCart(cart);
        User registeredUser = userrepository.save(user);

        sendEmail(user.getEmail(), user.getActivationCode());
        return ResponseEntity.ok("Đăng ký thành công");
    }

    public String createActivationCode(){
        return UUID.randomUUID().toString();
    }

    public void sendEmail(String email, String activationCode){
        String subject = "kích hoạt tài khoản tại WebBanSach";
        String content = "Vui lòng sử dụng mã sau để kich hoạt cho tài khoản <"+email+">:<html><body><br/><h1>"+activationCode+"</h1>";
        content+="<br/> Click vào đường link để kích hoạt tài khoản: ";
        String url = "http://localhost:3000/user/activate/"+email+"/"+activationCode;
        content+=("<br/> <a href="+url+">"+url+"</a> </body></html>");
        this.emailservice.sendMessage("hgffhchdu@gmail.com", email, subject, content);
    }

    public ResponseEntity<?> accountActivate(String email, String activationCode){
        User user = this.userrepository.findByEmail(email);

        if(user == null){
            return ResponseEntity.badRequest().body(new Notification("Tài khoản không tồn tại"));
        }
        if(user.isAccountStatus()){
            return ResponseEntity.badRequest().body(new Notification("Tài khoản đã được kích hoạt"));
        }

        if(activationCode.equals(user.getActivationCode())){
            user.setAccountStatus(true);
            this.userrepository.save(user);
            return ResponseEntity.ok("Kích hoạt tài khoản thành công");
        }else {
            return ResponseEntity.badRequest().body(new Notification("Sai mã kích hoạt"));
        }
    }
}
