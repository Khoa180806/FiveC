package com.team4.quanliquanmicay.Impl;

import com.team4.quanliquanmicay.DAO.BillDetailsDAO;
import com.team4.quanliquanmicay.DAO.CategoryDAO;
import com.team4.quanliquanmicay.Entity.BillDetails;

import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.util.XQuery;
import java.util.List;

public class BillDetailsDAOImpl implements BillDetailsDAO{

    String createSql = "INSERT INTO BILL_DETAIL(bill_id, product_id, amount, price, discount) VALUES(?, ?, ?, ?, ?)";
    String updateSql = "UPDATE BILL_DETAIL SET bill_id=?, product_id=?, amount=?, price=?, discount=? WHERE bill_detail_id=?";
    String deleteSql = "DELETE FROM BILL_DETAIL WHERE bill_detail_id=?";
    String findAllSql = "SELECT bill_detail_id, bill_id, product_id, amount, price, discount FROM BILL_DETAIL";
    String findByIdSql = "SELECT bill_detail_id, bill_id, product_id, amount, price, discount FROM BILL_DETAIL WHERE bill_detail_id=?";
    String findByBillIdSql = "SELECT bill_detail_id, bill_id, product_id, amount, price, discount FROM BILL_DETAIL WHERE bill_id=?";

    @Override
    public BillDetails create(BillDetails entity) {
        Object[] values = {
            entity.getBill_id(),
            entity.getProduct_id(),
            entity.getAmount(),
            entity.getPrice(),
            entity.getDiscount(),
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(BillDetails entity) {
        Object[] values = {
            entity.getBill_detail_id(),
            entity.getBill_id(),
            entity.getProduct_id(),
            entity.getAmount(),
            entity.getPrice(),
            entity.getDiscount(),
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(String categoryId) {
        XJdbc.executeUpdate(deleteSql, categoryId);
    }

    @Override
    public List<BillDetails> findAll() {
        return XQuery.getBeanList(BillDetails.class, findAllSql);
    }

    @Override
    public BillDetails findById(String bill_detail_id) {
        return XQuery.getSingleBean(BillDetails.class, findByIdSql, bill_detail_id);
    }
    
    @Override
    public List<BillDetails> findByBillId(Integer billId) {
        return XQuery.getBeanList(BillDetails.class, findByBillIdSql, billId);
    }
}
