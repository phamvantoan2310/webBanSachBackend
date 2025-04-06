package com.phamvantoan.webBanSachBackend.service;

import com.phamvantoan.webBanSachBackend.controller.BillItemResponse;
import com.phamvantoan.webBanSachBackend.dao.billRepository;
import com.phamvantoan.webBanSachBackend.entity.Bill;
import com.phamvantoan.webBanSachBackend.entity.BillItem;
import com.phamvantoan.webBanSachBackend.entity.Book;
import com.phamvantoan.webBanSachBackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class billServiceImpl implements billService{
    private billItemService billItemService;
    private userService userService;
    private bookService bookService;
    private billRepository billRepository;

    @Autowired
    public billServiceImpl(bookService bookService, billRepository billRepository, userService userService, billItemService billItemService) {
        this.bookService = bookService;
        this.billRepository = billRepository;
        this.userService = userService;
        this.billItemService = billItemService;
    }

    @Override
    public ResponseEntity save(Bill bill, List<BillItemResponse> billItemResponses, int userID) {
        Bill bill1 = new Bill();
        bill1.setBillID(0);
        bill1.setCreationDate(bill.getCreationDate());
        bill1.setCustomer(bill.getCustomer());
        bill1.setCustomerAddress(bill.getCustomerAddress());
        bill1.setCustomerPhoneNumber(bill.getCustomerPhoneNumber());
        bill1.setSuppliers(bill.getSuppliers());
        bill1.setSuppliersAddress(bill.getSuppliersAddress());
        bill1.setSuppliersPhoneNumber(bill.getSuppliersPhoneNumber());
        bill1.setTotalPrice(bill.getTotalPrice());
        bill1.setCustomerTax(bill.getCustomerTax());
        bill1.setSuppliersTax(bill.getSuppliersTax());

        List<BillItem> billItems = new ArrayList<>();

        for (BillItemResponse billItemResponse : billItemResponses) {
            Book book = this.bookService.findByBookID(billItemResponse.getBookID());

            BillItem billItem = new BillItem();
            billItem.setBook(book);
            billItem.setNumberOfBillItem(billItemResponse.getNumberOfBooks());
            billItem.setPrice(billItemResponse.getImportPrice());
            billItem.setBill(bill1);
            billItems.add(billItem);
        }

        User user = this.userService.findByUserID(userID);

        bill1.setUser(user);
        bill1.setBillItemList(billItems);

        this.billRepository.save(bill1);
        return ResponseEntity.ok("Tạo hóa đơn thành công");
    }

    @Override
    public ResponseEntity delete(int billID) {
        try {
            Bill bill = this.billRepository.findByBillID(billID);

            List<BillItem> billItems = new ArrayList<>(bill.getBillItemList());
            for (BillItem billItem : billItems) {
                this.billItemService.delete(billItem);
            }
            bill.getBillItemList().clear(); // Xóa toàn bộ danh sách sau khi duyệt

            bill.getUser().getBillList().remove(bill);

            this.billRepository.delete(bill);
            return ResponseEntity.ok("Xóa bill thành công");
        }catch (Exception e){
            throw e;
        }
    }
}
