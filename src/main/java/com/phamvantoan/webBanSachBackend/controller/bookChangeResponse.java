package com.phamvantoan.webBanSachBackend.controller;

import com.phamvantoan.webBanSachBackend.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class bookChangeResponse {
    private Book book;
    private int authorID;
    private int categoryID;
}
