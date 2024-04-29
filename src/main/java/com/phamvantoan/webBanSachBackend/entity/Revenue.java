package com.phamvantoan.webBanSachBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "revenue")
@Data
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "revenue_id")
    private int revenueID;

    @Column(name = "total_revenue")
    private int totalRevenue;

    @Column(name = "revenue_date", columnDefinition = "DATE")
    private LocalDate revenueDate;
}
