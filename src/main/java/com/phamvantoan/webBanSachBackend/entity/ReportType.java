package com.phamvantoan.webBanSachBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "report_type")
public class ReportType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_type_id")
    private int reportTypeID;

    @Column(name = "report_type_name")
    private String reportTypeName;

    @OneToMany(mappedBy = "reporttype", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Report> reportList;
}
