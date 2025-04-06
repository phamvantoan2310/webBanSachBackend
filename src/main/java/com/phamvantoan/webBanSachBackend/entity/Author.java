package com.phamvantoan.webBanSachBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;
@Entity
@Table(name = "author")
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private int authorID;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "decription", columnDefinition = "LONGTEXT")
    @Lob
    private String decription;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Book> bookList;
}
