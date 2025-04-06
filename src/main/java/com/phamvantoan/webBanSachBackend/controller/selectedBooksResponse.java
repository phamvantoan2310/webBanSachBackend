package com.phamvantoan.webBanSachBackend.controller;

import com.phamvantoan.webBanSachBackend.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class selectedBooksResponse {
    private int bookID;
    private int numberOfBooks;
}
