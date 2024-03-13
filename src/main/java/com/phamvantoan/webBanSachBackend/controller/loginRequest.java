package com.phamvantoan.webBanSachBackend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class loginRequest {
    private String userName;
    private String password;
}
