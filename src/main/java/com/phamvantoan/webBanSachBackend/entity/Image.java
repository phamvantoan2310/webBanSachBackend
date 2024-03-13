package com.phamvantoan.webBanSachBackend.entity;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "image")
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int imageID;

    @Column(name = "image_name")
    private  String imageName;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "data", columnDefinition = "LONGTEXT")
    @Lob
    private String data;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
