package com.team4.quanliquanmicay.Impl;


import com.team4.quanliquanmicay.DAO.PaymentHistoryDAO;
import com.team4.quanliquanmicay.Entity.PaymentHistory;
import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.util.XQuery;
import java.util.List;

public class PaymentHistoryDAOImpl implements PaymentHistoryDAO{

    String createSql = "INSERT INTO PAYMENT_HISTORY(payment_history_id, payment_method_id, payment_date, total_amount, status, note) VALUES(?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE PAYMENT_HISTORY SET payment_method_id=?, payment_date=?, total_amount=?, status=?, note=? WHERE payment_history_id=?";
    String deleteSql = "DELETE FROM PAYMENT_HISTORY WHERE payment_history_id=?";
    String findAllSql = "SELECT payment_history_id, payment_method_id, payment_date, total_amount, status, note FROM PAYMENT_HISTORY";
    String findByIdSql = "SELECT product_id, name_product, price, discount, unit, image, is_available, note, created_date, category_id FROM PRODUCT WHERE product_id=?";

    @Override
    public PaymentHistory create(PaymentHistory entity) {
        Object[] values = {
           entity.getPayment_history_id(),
           entity.getPayment_method_id(),
           entity.getPayment_date(),
           entity.getTotal_amount(),
           entity.getStatus(),
           entity.getNote(),
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(PaymentHistory entity) {
        Object[] values = {
            entity.getPayment_history_id(),
            entity.getPayment_method_id(),
            entity.getPayment_date(),
            entity.getTotal_amount(),
            entity.getStatus(),
            entity.getNote(),
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(String productId) {
        XJdbc.executeUpdate(deleteSql, productId);
    }

    @Override
    public List<PaymentHistory> findAll() {
        return XQuery.getBeanList(PaymentHistory.class, findAllSql);
    }

    @Override
    public PaymentHistory findById(String payment_history_id) {
        return XQuery.getSingleBean(PaymentHistory.class, findByIdSql, payment_history_id);
    }
}