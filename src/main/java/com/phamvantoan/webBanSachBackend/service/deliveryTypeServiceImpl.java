package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.deliveryTypeRepository;
import com.phamvantoan.webBanSachBackend.entity.DeliveryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class deliveryTypeServiceImpl implements deliveryTypeService{
    private deliveryTypeRepository deliverytyperepository;
    @Autowired
    public deliveryTypeServiceImpl(deliveryTypeRepository deliverytyperepository){
        this.deliverytyperepository = deliverytyperepository;
    }
    @Override
    public DeliveryType findByDeliveryTypeID(int deliveryTypeID) {
        return this.deliverytyperepository.findByDeliveryTypeID(deliveryTypeID);
    }
}
