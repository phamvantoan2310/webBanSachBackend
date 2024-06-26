package com.phamvantoan.webBanSachBackend.dao;

import com.phamvantoan.webBanSachBackend.entity.Evaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "evaluates")
public interface evaluateRepository extends JpaRepository<Evaluate, Integer> {
    public Evaluate findByEvaluateID(int evaluateID);
}
