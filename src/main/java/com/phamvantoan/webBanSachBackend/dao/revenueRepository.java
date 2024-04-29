package com.phamvantoan.webBanSachBackend.dao;

import com.phamvantoan.webBanSachBackend.entity.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;

@RepositoryRestResource(path = "sales")
public interface revenueRepository extends JpaRepository<Revenue, Integer> {
    public Revenue getByRevenueDate(LocalDate salesDate);
}
