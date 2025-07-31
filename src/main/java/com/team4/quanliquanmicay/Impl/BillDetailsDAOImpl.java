package com.team4.quanliquanmicay.Impl;

import com.team4.quanliquanmicay.DAO.BillDetailsDAO;
import com.team4.quanliquanmicay.Entity.BillDetails;
import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.util.XQuery;
import java.util.List;

public class BillDetailsDAOImpl implements BillDetailsDAO {

    // SQL queries - static final để tối ưu performance
    private static final String CREATE_SQL = "INSERT INTO BILL_DETAIL(bill_id, product_id, amount, price, discount) VALUES(?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE BILL_DETAIL SET bill_id=?, product_id=?, amount=?, price=?, discount=? WHERE bill_detail_id=?";
    private static final String DELETE_SQL = "DELETE FROM BILL_DETAIL WHERE bill_detail_id=?";
    private static final String FIND_ALL_SQL = "SELECT bill_detail_id, bill_id, product_id, amount, price, discount FROM BILL_DETAIL";
    private static final String FIND_BY_ID_SQL = "SELECT bill_detail_id, bill_id, product_id, amount, price, discount FROM BILL_DETAIL WHERE bill_detail_id=?";
    private static final String FIND_BY_BILL_ID_SQL = "SELECT bill_detail_id, bill_id, product_id, amount, price, discount FROM BILL_DETAIL WHERE bill_id=?";

    @Override
    public BillDetails create(BillDetails entity) {
        if (entity == null || !entity.isValid()) return null;
        
        Object[] values = {
            entity.getBill_id(),
            entity.getProduct_id(),
            entity.getAmount(),
            entity.getPrice(),
            entity.getDiscount()
        };
        XJdbc.executeUpdate(CREATE_SQL, values);
        return entity;
    }

    @Override
    public void update(BillDetails entity) {
        if (entity == null || !entity.isValid()) return;
        
        Object[] values = {
            entity.getBill_id(),
            entity.getProduct_id(),
            entity.getAmount(),
            entity.getPrice(),
            entity.getDiscount(),
            entity.getBill_detail_id()
        };
        XJdbc.executeUpdate(UPDATE_SQL, values);
    }

    @Override
    public void deleteById(String billDetailId) {
        if (billDetailId == null || billDetailId.trim().isEmpty()) return;
        XJdbc.executeUpdate(DELETE_SQL, billDetailId);
    }

    @Override
    public List<BillDetails> findAll() {
        return XQuery.getBeanList(BillDetails.class, FIND_ALL_SQL);
    }

    @Override
    public BillDetails findById(String billDetailId) {
        if (billDetailId == null || billDetailId.trim().isEmpty()) return null;
        return XQuery.getSingleBean(BillDetails.class, FIND_BY_ID_SQL, billDetailId);
    }
    
    @Override
    public List<BillDetails> findByBillId(Integer billId) {
        if (billId == null) return null;
        return XQuery.getBeanList(BillDetails.class, FIND_BY_BILL_ID_SQL, billId);
    }
}
