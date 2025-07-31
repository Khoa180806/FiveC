package com.team4.quanliquanmicay.Impl;

import com.team4.quanliquanmicay.DAO.BillDAO;
import com.team4.quanliquanmicay.Entity.Bill;
import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.util.XQuery;
import java.util.List;
import java.sql.Timestamp;

public class BillDAOImpl implements BillDAO{

    String createSql = "INSERT INTO BILL(bill_id, user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE BILL SET user_id=?, phone_number=?, payment_history_id=?, table_number=?, total_amount=?, checkin=?, checkout=?, status=? WHERE bill_id=?";
    String deleteSql = "DELETE FROM BILL WHERE bill_id=?";
    String findAllSql = "SELECT bill_id, user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status FROM BILL";
    String findByIdSql = "SELECT bill_id, user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status FROM BILL WHERE bill_id=?";

    @Override
    public Bill create(Bill entity) {
        Object[] values = {
           entity.getBill_id(),
           entity.getUser_id(),
           entity.getPhone_number(),
           entity.getPayment_history_id(),
           entity.getTable_number(),
           entity.getTotal_amount(),
           entity.getCheckin() != null ? new Timestamp(entity.getCheckin().getTime()) : null,
           entity.getCheckout() != null ? new Timestamp(entity.getCheckout().getTime()) : null,
           entity.getStatus(),
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(Bill entity) {
        Object[] values = {
            entity.getUser_id(),
            entity.getPhone_number(),
            entity.getPayment_history_id(),
            entity.getTable_number(),
            entity.getTotal_amount(),
            entity.getCheckin() != null ? new Timestamp(entity.getCheckin().getTime()) : null,
            entity.getCheckout() != null ? new Timestamp(entity.getCheckout().getTime()) : null,
            entity.getStatus(),
            entity.getBill_id(),
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(String productId) {
        XJdbc.executeUpdate(deleteSql, productId);
    }

    @Override
    public List<Bill> findAll() {
        return XQuery.getBeanList(Bill.class, findAllSql);
    }

    @Override
    public Bill findById(String bill_id) {
        return XQuery.getSingleBean(Bill.class, findByIdSql, bill_id);
    }
}