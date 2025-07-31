package com.team4.quanliquanmicay.View.Dialogs;

import com.team4.quanliquanmicay.Entity.Bill;
import com.team4.quanliquanmicay.Entity.UserAccount;
import java.util.Date;

/**
 * Test class cho tính năng đặt món
 */
public class TestDatMon {
    
    public static void main(String[] args) {
        // Tạo bill test
        Bill testBill = new Bill();
        testBill.setBill_id("BILL001");
        testBill.setUser_id("USER001");
        testBill.setTable_number(1);
        testBill.setStatus(true);
        testBill.setCheckin(new Date());
        testBill.setTotal_amount(0.0);
        
        // Mở dialog đặt món
        DatMonJDialog datMonDialog = new DatMonJDialog(null, testBill);
        datMonDialog.setVisible(true);
    }
} 