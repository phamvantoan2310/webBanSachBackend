package com.phamvantoan.webBanSachBackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
@Entity
@Table(name = "delivery_type")
@Data
public class DeliveryType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_type_id")
    private int deliveryTypeID;

    @Column(name = "delivery_type_name")
    private String deliveryTypeName;

    @Column(name = "decription")
    private String decription;

    @Column(name = "price_of_delivery_type")
    private int priceOfDeliveryType;

    @OneToMany(mappedBy = "deliveryType", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Order> orderList;
}
