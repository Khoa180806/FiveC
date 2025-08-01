package com.team4.quanliquanmicay.Impl;

import com.team4.quanliquanmicay.DAO.BillDAO;
import com.team4.quanliquanmicay.Entity.Bill;
import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.util.XQuery;
import java.util.List;
import java.sql.Timestamp;

public class BillDAOImpl implements BillDAO {

    // SQL queries - static final để tối ưu performance
    private static final String CREATE_SQL = "INSERT INTO BILL(user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE BILL SET user_id=?, phone_number=?, payment_history_id=?, table_number=?, total_amount=?, checkin=?, checkout=?, status=? WHERE bill_id=?";
    private static final String DELETE_SQL = "DELETE FROM BILL WHERE bill_id=?";
    private static final String FIND_ALL_SQL = "SELECT bill_id, user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status FROM BILL";
    private static final String FIND_BY_ID_SQL = "SELECT bill_id, user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status FROM BILL WHERE bill_id=?";
    private static final String FIND_BY_TABLE_NUMBER_SQL = "SELECT bill_id, user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status FROM BILL WHERE table_number = ? AND status = N'Đang phục vụ'";

    @Override
    public Bill create(Bill entity) {
        if (entity == null || !entity.isValid()) return null;
        
        Object[] values = {
            entity.getUser_id(),
            entity.getPhone_number(),
            entity.getPayment_history_id(),
            entity.getTable_number(),
            entity.getTotal_amount(),
            convertToTimestamp(entity.getCheckin()),
            convertToTimestamp(entity.getCheckout()),
            entity.getStatus()
        };
        
        XJdbc.executeUpdate(CREATE_SQL, values);
        return entity;
    }

    @Override
    public void update(Bill entity) {
        if (entity == null || entity.getBill_id() == null || !entity.isValid()) return;
        
        Object[] values = {
            entity.getUser_id(),
            entity.getPhone_number(),
            entity.getPayment_history_id(),
            entity.getTable_number(),
            entity.getTotal_amount(),
            convertToTimestamp(entity.getCheckin()),
            convertToTimestamp(entity.getCheckout()),
            entity.getStatus(),
            entity.getBill_id()
        };
        
        XJdbc.executeUpdate(UPDATE_SQL, values);
    }

    @Override
    public void deleteById(String billId) {
        if (billId == null || billId.trim().isEmpty()) return;
        XJdbc.executeUpdate(DELETE_SQL, billId);
    }

    @Override
    public List<Bill> findAll() {
        return XQuery.getBeanList(Bill.class, FIND_ALL_SQL);
    }

    @Override
    public Bill findById(String billId) {
        if (billId == null || billId.trim().isEmpty()) return null;
        return XQuery.getSingleBean(Bill.class, FIND_BY_ID_SQL, billId);
    }
    
    @Override
    public Bill findByTableNumber(int tableNumber) {
        try {
            return XJdbc.executeQuery(FIND_BY_TABLE_NUMBER_SQL, rs -> {
                if (rs.next()) {
                    Bill b = new Bill();
                    b.setBill_id(rs.getInt("bill_id"));
                    b.setUser_id(rs.getString("user_id"));
                    b.setPhone_number(rs.getString("phone_number"));
                    b.setPayment_history_id(rs.getInt("payment_history_id"));
                    b.setTable_number(rs.getInt("table_number"));
                    b.setTotal_amount(rs.getDouble("total_amount"));
                    b.setCheckin(rs.getDate("checkin"));
                    b.setCheckout(rs.getDate("checkout"));
                    // Status là NVARCHAR2, không phải Boolean
                    String status = rs.getString("status");
                    b.setStatus("Đang phục vụ".equals(status));
                    return b;
                }
                return null;
            }, tableNumber);
        } catch (Exception e) {
            return null;
        }
    }
    
    // Helper method để convert Date to Timestamp
    private Timestamp convertToTimestamp(java.util.Date date) {
        return date != null ? new Timestamp(date.getTime()) : null;
    }
}