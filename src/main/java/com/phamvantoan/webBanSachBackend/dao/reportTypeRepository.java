package com.phamvantoan.webBanSachBackend.dao;

import com.phamvantoan.webBanSachBackend.entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "reporttypes")
public interface reportTypeRepository extends JpaRepository<ReportType, Integer> {
    public ReportType findByReportTypeID(int reportType);
}
