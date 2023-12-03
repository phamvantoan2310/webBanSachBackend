package com.phamvantoan.webBanSachBackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
@Entity
@Table(name = "payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int paymentID;

    @Column(name = "payment_name")
    private String paymentName;

    @Column(name = "decription")
    private String decription;

    @Column(name = "price_of_payment")
    private int priceOfPayment;

    @OneToMany(mappedBy = "payment", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Orders> orderList;
}
