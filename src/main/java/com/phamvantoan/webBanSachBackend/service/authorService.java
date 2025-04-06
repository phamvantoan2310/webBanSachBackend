package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.entity.Author;
import org.springframework.http.ResponseEntity;

public interface authorService {
    public Author findByAuthorID(int authorID);
    public ResponseEntity<?> createAuthor(Author author);
    public ResponseEntity<?> updateAuthor(Author author);
    public ResponseEntity<?> deleteAuthor(int authorID);
}
