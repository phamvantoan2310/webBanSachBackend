package com.phamvantoan.webBanSachBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "bill")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private int billID;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "suppliers")
    private String suppliers;

    @Column(name = "suppliers_address")
    private String suppliersAddress;

    @Column(name = "suppliers_phone_number")
    private String suppliersPhoneNumber;

    @Column(name = "suppliers_tax")
    private String suppliersTax;

    @Column(name = "customer")
    private String customer;

    @Column(name = "customer_address")
    private String customerAddress;

    @Column(name = "customer_phone_number")
    private String customerPhoneNumber;

    @Column(name = "customer_tax")
    private String customerTax;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "bill", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<BillItem> billItemList;
}
