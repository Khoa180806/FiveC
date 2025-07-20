package com.team4.quanliquanmicay.Impl;

import com.team4.quanliquanmicay.DAO.BillDetailsDAO;
import com.team4.quanliquanmicay.DAO.CategoryDAO;
import com.team4.quanliquanmicay.Entity.BillDetails;

import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.util.XQuery;
import java.util.List;

public class BillDetailsDAOImpl implements BillDetailsDAO{

    String createSql = "INSERT INTO BILL_DETAILS(bill_details_id, bill_id, product_id, amount, price, discount) VALUES(?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE BILL_DETAILS SET bill_id=?, product_id=?, amount=?, price=?, discount=? WHERE bill_details_id=?";
    String deleteSql = "DELETE FROM BILL_DETAILS WHERE bill_details_id=?";
    String findAllSql = "SELECT bill_details_id, bill_id, product_id, amount, price, discount FROM BILL_DETAILS";
    String findByIdSql = "SELECT bill_details_id, bill_id, product_id, amount, price, discount FROM BILL_DETAILS WHERE bill_details_id=?";

    @Override
    public BillDetails create(BillDetails entity) {
        Object[] values = {
            entity.getBill_details_id(),
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
            entity.getBill_details_id(),
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
    public BillDetails findById(String bill_details_id) {
        return XQuery.getSingleBean(BillDetails.class, findByIdSql, bill_details_id);
    }
}
