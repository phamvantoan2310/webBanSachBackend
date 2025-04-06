package com.phamvantoan.webBanSachBackend.controller;

import com.phamvantoan.webBanSachBackend.entity.Book;
import com.phamvantoan.webBanSachBackend.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class addBookResponse {
    private Book book;
    private List<Image> images;
    private int categoryID;
    private int authorID;
}
