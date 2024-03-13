package com.phamvantoan.webBanSachBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
@Entity
@Table(name = "wish_list")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_list_id")
    private int wishListID;

    @Column(name = "wish_list_name")
    private String wishlistName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "book_wishlist", joinColumns = @JoinColumn(name = "wish_list_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    @JsonIgnore
    private List<Book> bookList;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
