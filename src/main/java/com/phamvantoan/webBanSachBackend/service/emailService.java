package com.phamvantoan.webBanSachBackend.service;

public interface emailService {
    public void sendMessage(String from, String to, String subject, String content);
}
