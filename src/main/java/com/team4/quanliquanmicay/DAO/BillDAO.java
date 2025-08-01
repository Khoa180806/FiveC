package com.team4.quanliquanmicay.DAO;

import com.team4.quanliquanmicay.Entity.Bill;

public interface BillDAO extends CrudDAO<Bill, String> {
    
    /**
     * Tìm bill theo số bàn
     */
    Bill findByTableNumber(int tableNumber);
    
}