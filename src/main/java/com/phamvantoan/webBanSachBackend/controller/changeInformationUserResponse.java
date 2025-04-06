package com.phamvantoan.webBanSachBackend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class changeInformationUserResponse {
    private String userName;
    private String phoneNumber;
    private String email;
    private String address;
}
