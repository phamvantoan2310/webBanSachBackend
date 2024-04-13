package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.entity.Image;
import org.springframework.http.ResponseEntity;

public interface imageService {
    public ResponseEntity<?> save(Image image, int bookID);
    public ResponseEntity<?> deleteBookImage(int imageID);
}
