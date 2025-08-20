package com.team4.quanliquanmicay.Impl;

import com.team4.quanliquanmicay.DAO.PaymentMethodDAO;
import com.team4.quanliquanmicay.Entity.PaymentMethod;
import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.util.XQuery;
import java.util.List;

public class PaymentMethodDAOImpl implements PaymentMethodDAO {

    String createSql = "INSERT INTO PAYMENT_METHOD(payment_method_id, method_name, is_enable) VALUES(?, ?, ?)";
    String updateSql = "UPDATE PAYMENT_METHOD SET method_name=?, is_enable=? WHERE payment_method_id=?";
    String deleteSql = "DELETE FROM PAYMENT_METHOD WHERE payment_method_id=?";
    String softDisableSql = "UPDATE PAYMENT_METHOD SET is_enable = 0 WHERE payment_method_id = ?";
    String checkInUseSql = "SELECT 1 FROM PAYMENT_HISTORY WHERE payment_method_id = ? FETCH FIRST 1 ROWS ONLY";
    String findAllSql = "SELECT payment_method_id, method_name, is_enable FROM PAYMENT_METHOD";
    String findByIdSql = "SELECT payment_method_id, method_name, is_enable FROM PAYMENT_METHOD WHERE payment_method_id=?";

    @Override
    public PaymentMethod create(PaymentMethod entity) {
        Object[] values = {
            entity.getPayment_method_id(),
            entity.getMethod_name(),
            entity.getIs_enable(),
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(PaymentMethod entity) {
        Object[] values = {
            entity.getMethod_name(),
            entity.getIs_enable(),
            entity.getPayment_method_id(),
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(Integer payment_method_id) {
        try {
            // Nếu phương thức đang được sử dụng trong PAYMENT_HISTORY, không xóa cứng
            Integer inUse = XJdbc.getValue(checkInUseSql, Integer.class, payment_method_id);
            if (inUse != null) {
                // Soft delete: chuyển sang Tạm dừng để tránh lỗi FK và mất dữ liệu lịch sử
                XJdbc.executeUpdate(softDisableSql, payment_method_id);
                return;
            }
            // Không được sử dụng: cho phép xóa cứng
            XJdbc.executeUpdate(deleteSql, payment_method_id);
        } catch (RuntimeException ex) {
            // Fallback an toàn: nếu có bất kỳ lỗi FK/lock, chuyển sang soft-disable
            try { XJdbc.executeUpdate(softDisableSql, payment_method_id); } catch (Exception ignore) {}
            throw ex;
        }
    }

    @Override
    public List<PaymentMethod> findAll() {
        return XQuery.getBeanList(PaymentMethod.class, findAllSql);
    }

    @Override
    public PaymentMethod findById(Integer payment_method_id) {
        return XQuery.getSingleBean(PaymentMethod.class, findByIdSql, payment_method_id);
    }

} 