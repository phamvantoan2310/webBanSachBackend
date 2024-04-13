package com.phamvantoan.webBanSachBackend.controller;


import com.phamvantoan.webBanSachBackend.entity.Book;
import com.phamvantoan.webBanSachBackend.entity.User;
import com.phamvantoan.webBanSachBackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/staff")
public class staffController {
    private imageService imageservice;
    private bookService bookservice;
    private reportService reportservice;
    private orderService orderservice;
    private userService userservice;
    @Autowired
    public staffController(imageService imageservice,bookService bookservice, reportService reportservice, orderService orderservice, userService userservice){
        this.imageservice = imageservice;
        this.bookservice = bookservice;
        this.reportservice = reportservice;
        this.orderservice = orderservice;
        this.userservice = userservice;
    }
    @PostMapping("/addbookimage")
    public ResponseEntity<?> addBookImage(@RequestBody addBookImageResponse addbookimageresponse, @RequestHeader("Authorization") String authorizationHeader){
        return this.imageservice.save(addbookimageresponse.getImage(), addbookimageresponse.getBookID());
    }
    @DeleteMapping("/deletebookimage")
    public ResponseEntity<?> deleteBookImage(@RequestBody int imageID){
        return this.imageservice.deleteBookImage(imageID);
    }
    @PostMapping("/bookchange")
    public ResponseEntity<?> bookChange(@RequestBody bookChangeResponse bookchangeresponse){
        return this.bookservice.bookChange(bookchangeresponse.getBook(), bookchangeresponse.getAuthorID());
    }
    @PostMapping("/numberofbookchange")
    public ResponseEntity<?> numberOfBookChange(@RequestBody numberOfBookChangeRespponse numberofbookchangerespponse){
        return bookservice.numberOfBookChange(numberofbookchangerespponse.getBookID(), numberofbookchangerespponse.getNumberOfBook());
    }
    @DeleteMapping("/deletebook")
    public ResponseEntity<?> deleteBook(@RequestBody int bookID){
        return this.bookservice.deleteBook(bookID);
    }
    @PostMapping("/sendreportresponse")
    public ResponseEntity<?> sendReportResponse(@RequestBody responseSendData responsesenddata){
        return this.reportservice.addReportResponse(responsesenddata.getReportID(), responsesenddata.getResponseReport());
    }
    @PostMapping("/confirmorder")
    public ResponseEntity<?> confirmOrder(@RequestBody int orderID){
        return this.orderservice.confirmOrder(orderID);
    }
    @PutMapping("/updateuser")
    public ResponseEntity<?> updateUser(@Validated @RequestBody User user){
        return this.userservice.staffUpdateUser(user);
    }
    @DeleteMapping("/deleteuser")
    public ResponseEntity<?> deleteUser(@Validated @RequestBody int userID){
        return this.userservice.deleteUser(userID);
    }
}
