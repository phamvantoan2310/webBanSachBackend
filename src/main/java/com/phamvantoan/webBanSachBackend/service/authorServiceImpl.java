package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.authorRepository;
import com.phamvantoan.webBanSachBackend.entity.Author;
import com.phamvantoan.webBanSachBackend.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class authorServiceImpl implements authorService{
    private authorRepository authorrepository;
    @Autowired
    public authorServiceImpl(authorRepository authorrepository){
        this.authorrepository = authorrepository;
    }
    @Override
    public Author findByAuthorID(int authorID) {
        return this.authorrepository.findByAuthorID(authorID);
    }

    @Override
    public ResponseEntity<?> createAuthor(Author author) {
        if(this.authorrepository.existsByAuthorName(author.getAuthorName())){
            return ResponseEntity.badRequest().body("Tác giả đã tồn tại");
        }else {
            List<Book> books = new ArrayList<>();
            author.setBookList(books);
            this.authorrepository.save(author);
            return ResponseEntity.ok().body("Thêm tác giả thành công");
        }
    }

    @Override
    public ResponseEntity<?> updateAuthor(Author author) {
        Author author1 = this.authorrepository.findByAuthorID(author.getAuthorID());
        if(author1 != null){
            if(this.authorrepository.existsByAuthorName(author.getAuthorName())){
                return ResponseEntity.badRequest().body("Tác giả đã tồn tại");
            }else {
                if(author.getAuthorName() != ""){
                    author1.setAuthorName(author.getAuthorName());
                }
                if(author.getBirthday() != null){
                    author1.setBirthday(author.getBirthday());
                }
                if(author.getDecription() != ""){
                    author1.setDecription(author.getDecription());
                }
                this.authorrepository.save(author1);
                return ResponseEntity.ok().body("Cập nhật tác giả thành công");
            }
        }
        return ResponseEntity.badRequest().body("Mã tác giả không chính xác!");
    }

    @Override
    public ResponseEntity<?> deleteAuthor(int authorID) {
        Author author = this.authorrepository.findByAuthorID(authorID);
        if(author != null){
            for (Book book: author.getBookList()) {
                book.setAuthor(null);
            }
            author.getBookList().clear();
            this.authorrepository.delete(author);
            return ResponseEntity.ok().body("Xóa tác giả thành công!");
        }else {
            return ResponseEntity.badRequest().body("Mã tác giả không chính xác!");
        }
    }
}
