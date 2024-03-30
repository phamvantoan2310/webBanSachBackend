package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.controller.createEvaluateResponse;
import com.phamvantoan.webBanSachBackend.dao.evaluateRepository;
import com.phamvantoan.webBanSachBackend.entity.Book;
import com.phamvantoan.webBanSachBackend.entity.Evaluate;
import com.phamvantoan.webBanSachBackend.entity.OrderItem;
import com.phamvantoan.webBanSachBackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class evaluateServiceImpl implements evaluateService{
    private evaluateRepository evaluaterepository;
    private orderService orderservice;
    @Autowired
    public evaluateServiceImpl(evaluateRepository evaluaterepository, orderService orderservice){
        this.evaluaterepository = evaluaterepository;
        this.orderservice = orderservice;
    }
    @Override
    public ResponseEntity<?> addEvaluate(User user, createEvaluateResponse createevaluateresponse) {
        OrderItem orderItem = this.orderservice.findByOrderItemID(createevaluateresponse.getOrderItemID());
        Book book = orderItem.getBook();
        Evaluate evaluate = new Evaluate();
        evaluate.setPoint(createevaluateresponse.getPoint());
        evaluate.setDecription(createevaluateresponse.getDecription());
        evaluate.setUser(user);
        user.getEvaluateList().add(evaluate);
        evaluate.setBook(book);
        book.getEvaluateList().add(evaluate);

        this.evaluaterepository.save(evaluate);
        return ResponseEntity.ok("thêm evaluate thành công");
    }
}
