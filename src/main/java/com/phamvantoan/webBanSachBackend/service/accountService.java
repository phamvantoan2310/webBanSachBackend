package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.entity.User;
import org.springframework.http.ResponseEntity;

public interface accountService {
    public ResponseEntity<?> registerUser(User user);
    public String createActivationCode();
    public void sendEmail(String email, String activationCode);
    public ResponseEntity<?> accountActivate(String email, String activationCode);
}
