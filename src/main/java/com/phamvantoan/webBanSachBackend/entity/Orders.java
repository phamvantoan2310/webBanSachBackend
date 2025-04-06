package com.phamvantoan.webBanSachBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;
import java.util.List;
@Entity
@Table(name = "orders")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderID;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "delivery_date")
    private Date deliveryDate;

    @Column(name = "delivery_phone_number")
    private String deliveryPhoneNumber;

    @Column(name = "delivery_user_name")
    private String deliveryUserName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "delivery_type_id")
    private DeliveryType deliveryType;

    @OneToMany(mappedBy = "orders", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "report_id")
    private Report report;

    @Override
    public String toString() {
        return "Orders{" +
                "orderID=" + orderID +
                ", totalPrice=" + totalPrice +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", user=" + user +
                ", payment=" + payment +
                ", deliveryType=" + deliveryType +
                ", orderItemList=" + orderItemList +
                '}';
    }
}
