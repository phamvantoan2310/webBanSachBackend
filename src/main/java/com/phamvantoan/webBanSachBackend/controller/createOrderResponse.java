package com.phamvantoan.webBanSachBackend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class createOrderResponse {
    private int deliveryTypeID;
    private int paymentID;
    private List<selectedBooksResponse> selectedBooksResponse;
    private String deliveryAddress;
    private String deliveryPhoneNumber;
    private String deliveryUserName;
    private int bookID;      //mua môt sách
    private int numberOfBook;//mua môt sách
}
