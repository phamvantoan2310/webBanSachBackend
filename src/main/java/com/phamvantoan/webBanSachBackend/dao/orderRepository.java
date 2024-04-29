package com.phamvantoan.webBanSachBackend.dao;

import com.phamvantoan.webBanSachBackend.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

@RepositoryRestResource(path = "orders")
public interface orderRepository extends JpaRepository<Orders, Integer> {
    public Orders findOrdersByOrderID(int orderID);
    Page<Orders> findByOrderStatus(@RequestParam("orderStatus") String orderStatus, Pageable pageable);

    public List<Orders> findByDeliveryDateAndOrderStatus(Date deleveryDate, String orderStatus);
}
