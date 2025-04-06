package com.phamvantoan.webBanSachBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;
@Entity
@Table(name = "book")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookID;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "price")
    private double price;

    @Column(name = "listed_price")
    private double listedPrice;

    @Column(name = "decription", columnDefinition = "LONGTEXT")
    @Lob
    private String decription;

    @Column(name = "number_of_book")
    private int numberOfBooks;
    @Column(name = "quantity_sold")
    private int quantitySold;

    @Column(name = "point")
    private int point;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publication_year")
    private Date publicationYear;

    @Column(name = "language")
    private String language;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    @Lob
    private String content;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> imageList;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "book_wishlist", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "wish_list_id"))
    private List<WishList> listWishList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "book_category", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categoryList;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Evaluate> evaluateList;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CartItem> cartItemList;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> orderItemList;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BillItem> billItemList;
}
