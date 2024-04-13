package com.phamvantoan.webBanSachBackend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class reportResponse {
    private String reportDetail;
    private String reportImageDetail;
    private int reportTypeID;
    private int orderID;
}
