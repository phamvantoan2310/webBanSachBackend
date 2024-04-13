package com.phamvantoan.webBanSachBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "report")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private int reportID;

    @Column(name = "report_detail")
    private String reportDetail;

    @Column(name = "report_image", columnDefinition = "LONGTEXT")
    @Lob
    private String reportImage;

    @Column(name = "report_response")
    private String reportResponse;

    @Column(name = "create_report_date")
    private Date createReportDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "report_type_id")
    private ReportType reporttype;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Orders orders;
}
