package com.phamvantoan.webBanSachBackend.dao;

import com.phamvantoan.webBanSachBackend.entity.DeliveryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "delivery-types")
public interface deliveryTypeRepository extends JpaRepository<DeliveryType, Integer> {
    public DeliveryType findByDeliveryTypeID(int deliveryTypeID);
}
