package com.phamvantoan.webBanSachBackend.dao;

import com.phamvantoan.webBanSachBackend.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(path = "books")
public interface bookRepository extends JpaRepository<Book, Integer>{
    Page<Book> findByBookNameContaining(@RequestParam("bookName") String bookName, Pageable pageable);

    Page<Book> findByCategoryList_CategoryID(@RequestParam("categoryID") int categoryID, Pageable pageable);

    Page<Book> findByBookNameContainingAndCategoryList_CategoryID(@RequestParam("bookName") String bookName,@RequestParam("categoryID") int  categoryID, Pageable pageable);

    public Book findByBookID(int bookID);
    Page<Book> findByAuthor_AuthorName(@RequestParam("authorName") String authorName, Pageable pageable);
    public boolean existsByBookName(String bookName);
}
