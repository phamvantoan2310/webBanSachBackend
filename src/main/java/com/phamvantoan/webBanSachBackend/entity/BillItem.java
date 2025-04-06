package com.phamvantoan.webBanSachBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bill_item")
@Data
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_item_id")
    private int billItemID;

    @Column(name = "number_of_order_item")
    private int numberOfBillItem;

    @Column(name = "price")
    private double price;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;
}
