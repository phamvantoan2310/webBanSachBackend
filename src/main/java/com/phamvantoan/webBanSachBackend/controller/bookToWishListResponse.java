package com.phamvantoan.webBanSachBackend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class bookToWishListResponse {
    private int bookID;
    private int wishListID;
}
