package com.phamvantoan.webBanSachBackend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class changeWishListNameResponse {
    private int wishlistID;
    private String wishlistName;
}
