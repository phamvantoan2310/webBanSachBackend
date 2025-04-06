package com.phamvantoan.webBanSachBackend.controller;

import com.phamvantoan.webBanSachBackend.entity.Bill;
import com.phamvantoan.webBanSachBackend.entity.BillItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class createBillResponse {
    private Bill bill;
    private List<BillItemResponse> billItemResponses;
}
