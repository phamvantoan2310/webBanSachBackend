package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.orderItemRepository;
import com.phamvantoan.webBanSachBackend.dao.orderRepository;
import com.phamvantoan.webBanSachBackend.dao.userRepository;
import com.phamvantoan.webBanSachBackend.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class orderService {
    @Autowired
    private orderRepository orderrepository;
    @Autowired
    private orderItemRepository orderitemrepository;
    @Autowired
    private userRepository userrepository;

    public ResponseEntity<?> deleteOrder(Orders orders){
        orders.getUser().getOrderList().remove(orders);

        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderItem orderItem : orders.getOrderItemList()){
            orderItem.getBook().getOrderItemList().remove(orderItem);
            orderItems.add(orderItem);
        }

        for(OrderItem orderItem : orderItems){
            orders.getOrderItemList().remove(orderItem);
            this.orderitemrepository.delete(orderItem);
        }

        this.orderrepository.delete(orders);
        return ResponseEntity.ok("ok");
    }

    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
    @Transactional
    public ResponseEntity<?> createOrder(User user, DeliveryType deliveryType, Payment payment){
        try{
            Orders orders = new Orders();                                             //set thông tin cơ bản order
            orders.setOrderDate(java.sql.Date.valueOf(getCurrentDate()));
            orders.setOrderStatus("Đang Chờ");
            orders.setUser(user);
            orders.setDeliveryAddress(user.getAddress());
            orders.setDeliveryType(deliveryType);
            orders.setPayment(payment);

            user.getOrderList().add(orders);                                        //set Order cho user

            int totalPrice = 0;
            List<OrderItem> orderItems = new ArrayList<>();                         //set OrderItem và totalPrice từ cartItem cho order
            for(CartItem cartItem : user.getCart().getCartItemList()){
                OrderItem orderItem = new OrderItem();
                orderItem.setOrders(orders);
                orderItem.setNumberOfOrderItem(cartItem.getNumberOfCartItem());
                orderItem.setPrice(cartItem.getPrice());
                orderItem.setBook(cartItem.getBook());
                cartItem.getBook().getOrderItemList().add(orderItem);
                orderItems.add(orderItem);

                totalPrice += cartItem.getPrice();
            }
            orders.setOrderItemList(orderItems);
            orders.setTotalPrice(totalPrice);

            System.out.println(orders.getOrderDate() + orders.getOrderStatus() + "1");
            this.orderrepository.save(orders);
            return ResponseEntity.ok("Tạo order thành công");
        }catch (Exception e){
            throw e;
        }
    }
}
