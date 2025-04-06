package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.dao.billItemRepository;
import com.phamvantoan.webBanSachBackend.entity.Bill;
import com.phamvantoan.webBanSachBackend.entity.BillItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class billItemServiceImpl implements billItemService{
    private billItemRepository billItemRepository;

    @Autowired
    public billItemServiceImpl(billItemRepository billItemRepository) {
        this.billItemRepository = billItemRepository;
    }

    @Override
    public BillItem save(BillItem billItem) {
        return this.billItemRepository.save(billItem);
    }

    @Override
    public ResponseEntity delete(BillItem billItem) {
        billItem.getBill().getBillItemList().remove(billItem);
        billItem.setBill(null);

        billItem.getBook().getBillItemList().remove(billItem);
        billItem.setBook(null);

        this.billItemRepository.delete(billItem);
        return ResponseEntity.ok("Xóa billitem thành công");
    }
}
