package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.imageRepository;
import com.phamvantoan.webBanSachBackend.entity.Book;
import com.phamvantoan.webBanSachBackend.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class imageServiceImpl implements imageService{
    private imageRepository imagerepository;
    private bookService bookservice;
    @Autowired
    public imageServiceImpl(imageRepository imagerepository, bookService bookservice){
        this.imagerepository = imagerepository;
        this.bookservice = bookservice;
    }
    @Override
    public ResponseEntity<?> save(Image image, int bookID) {
        Book book = this.bookservice.findByBookID(bookID);
        book.getImageList().add(image);

        image.setBook(book);
        this.imagerepository.save(image);
        return ResponseEntity.ok("Thêm ảnh thành công");
    }

    @Override
    public ResponseEntity<?> deleteBookImage(int imageID) {
        Image image = this.imagerepository.findByImageID(imageID);
        image.getBook().getImageList().remove(image);
        image.setBook(null);
        this.imagerepository.delete(image);
        return ResponseEntity.ok("Xóa bookImage thành công");
    }
}
