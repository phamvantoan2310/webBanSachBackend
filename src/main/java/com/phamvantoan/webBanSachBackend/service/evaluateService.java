package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.controller.createEvaluateResponse;
import com.phamvantoan.webBanSachBackend.entity.Evaluate;
import com.phamvantoan.webBanSachBackend.entity.User;
import org.springframework.http.ResponseEntity;

public interface evaluateService {
    public ResponseEntity<?> addEvaluate(User user, createEvaluateResponse createevaluateresponse);
}
