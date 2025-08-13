package com.team4.quanliquanmicay.Impl;

import com.team4.quanliquanmicay.DAO.BillDAO;
import com.team4.quanliquanmicay.Entity.Bill;
import com.team4.quanliquanmicay.util.XJdbc;
import java.util.List;
import java.sql.Timestamp;
import java.util.ArrayList;

public class BillDAOImpl implements BillDAO {

    // SQL queries - static final để tối ưu performance
    private static final String CREATE_SQL = "INSERT INTO BILL(user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String CREATE_SQL_NO_PAYMENT = "INSERT INTO BILL(user_id, phone_number, table_number, total_amount, checkin, checkout, status) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE BILL SET user_id=?, phone_number=?, payment_history_id=?, table_number=?, total_amount=?, checkin=?, checkout=?, status=? WHERE bill_id=?";
    private static final String DELETE_SQL = "DELETE FROM BILL WHERE bill_id=?";
    private static final String FIND_ALL_SQL = "SELECT bill_id, user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status FROM BILL";
    private static final String FIND_BY_ID_SQL = "SELECT bill_id, user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status FROM BILL WHERE bill_id=?";
    private static final String FIND_BY_TABLE_NUMBER_SQL = "SELECT bill_id, user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status FROM BILL WHERE table_number = ? AND status = ?";
    private static final String FIND_ALL_BY_TABLE_NUMBER_SQL = "SELECT bill_id, user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status FROM BILL WHERE table_number = ?";

    @Override
    public Bill create(Bill entity) {
        if (entity == null || !entity.isValid()) return null;
        
        // Use String status directly - no conversion needed
        String statusValue = entity.getStatus();
        if (statusValue == null) {
            statusValue = "Đang phục vụ"; // Default value
        }
        
        // Sử dụng SQL phù hợp tùy theo có payment_history_id hay không
        if (entity.getPayment_history_id() != null) {
            Object[] values = {
                entity.getUser_id(),
                entity.getPhone_number(),
                entity.getPayment_history_id(),
                entity.getTable_number(),
                entity.getTotal_amount(),
                convertToTimestamp(entity.getCheckin()),
                convertToTimestamp(entity.getCheckout()),
                statusValue
            };
            XJdbc.executeUpdate(CREATE_SQL, values);
        } else {
            Object[] values = {
                entity.getUser_id(),
                entity.getPhone_number(),
                entity.getTable_number(),
                entity.getTotal_amount(),
                convertToTimestamp(entity.getCheckin()),
                convertToTimestamp(entity.getCheckout()),
                statusValue
            };
            XJdbc.executeUpdate(CREATE_SQL_NO_PAYMENT, values);
        }
        return entity;
    }

    @Override
    public void update(Bill entity) {
        if (entity == null || entity.getBill_id() == null || !entity.isValid()) return;
        
        // Use String status directly - no conversion needed
        String statusValue = entity.getStatus();
        if (statusValue == null) {
            statusValue = "Đang phục vụ"; // Default value
        }
        
        Object[] values = {
            entity.getUser_id(),
            entity.getPhone_number(),
            entity.getPayment_history_id(),
            entity.getTable_number(),
            entity.getTotal_amount(),
            convertToTimestamp(entity.getCheckin()),
            convertToTimestamp(entity.getCheckout()),
            statusValue,
            entity.getBill_id()
        };
        
        // Debug: In ra thông tin trước khi update
        System.out.println("DEBUG BillDAO Update:");
        System.out.println("  Bill ID: " + entity.getBill_id());
        System.out.println("  Status: " + entity.getStatus());
        System.out.println("  Status Text: " + entity.getStatusText());
        System.out.println("  SQL: " + UPDATE_SQL);
        
        int rowsAffected = XJdbc.executeUpdate(UPDATE_SQL, values);
        
        System.out.println("DEBUG: Update executed successfully");
        System.out.println("DEBUG: Rows affected: " + rowsAffected);
    }

    @Override
    public void deleteById(String billId) {
        if (billId == null || billId.trim().isEmpty()) return;
        XJdbc.executeUpdate(DELETE_SQL, billId);
    }

    @Override
    public List<Bill> findAll() {
        try {
            return XJdbc.executeQuery(FIND_ALL_SQL, rs -> {
                List<Bill> bills = new ArrayList<>();
                while (rs.next()) {
                    Bill bill = new Bill();
                    bill.setBill_id(rs.getInt("bill_id"));
                    bill.setUser_id(rs.getString("user_id"));
                    bill.setPhone_number(rs.getString("phone_number"));
                    bill.setPayment_history_id(rs.getInt("payment_history_id"));
                    bill.setTable_number(rs.getInt("table_number"));
                    bill.setTotal_amount(rs.getDouble("total_amount"));
                    bill.setCheckin(rs.getDate("checkin"));
                    bill.setCheckout(rs.getDate("checkout"));
                    
                    // Get String status from database directly
                    bill.setStatus(rs.getString("status"));
                    
                    bills.add(bill);
                }
                return bills;
            });
        } catch (Exception e) {
            System.err.println("Error in findAll: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Bill findById(String billId) {
        if (billId == null || billId.trim().isEmpty()) return null;
        
        try {
            return XJdbc.executeQuery(FIND_BY_ID_SQL, rs -> {
                if (rs.next()) {
                    Bill bill = new Bill();
                    bill.setBill_id(rs.getInt("bill_id"));
                    bill.setUser_id(rs.getString("user_id"));
                    bill.setPhone_number(rs.getString("phone_number"));
                    bill.setPayment_history_id(rs.getInt("payment_history_id"));
                    bill.setTable_number(rs.getInt("table_number"));
                    bill.setTotal_amount(rs.getDouble("total_amount"));
                    bill.setCheckin(rs.getDate("checkin"));
                    bill.setCheckout(rs.getDate("checkout"));
                    
                    // Get String status from database directly
                    bill.setStatus(rs.getString("status"));
                    
                    return bill;
                }
                return null;
            }, billId);
        } catch (Exception e) {
            System.err.println("Error in findById for billId " + billId + ": " + e.getMessage());
            return null;
        }
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
                    // Get Integer status from database directly
                    // Get String status from database directly
                    b.setStatus(rs.getString("status"));
                    return b;
                }
                return null;
            }, tableNumber, "Đang phục vụ");
        } catch (Exception e) {
            System.err.println("Error in findByTableNumber for table " + tableNumber + ": " + e.getMessage());
            return null;
        }
    }
    
    // Method để debug - tìm tất cả bill của một bàn
    public List<Bill> findAllByTableNumber(int tableNumber) {
        try {
            return XJdbc.executeQuery(FIND_ALL_BY_TABLE_NUMBER_SQL, rs -> {
                List<Bill> bills = new ArrayList<>();
                while (rs.next()) {
                    Bill b = new Bill();
                    b.setBill_id(rs.getInt("bill_id"));
                    b.setUser_id(rs.getString("user_id"));
                    b.setPhone_number(rs.getString("phone_number"));
                    b.setPayment_history_id(rs.getInt("payment_history_id"));
                    b.setTable_number(rs.getInt("table_number"));
                    b.setTotal_amount(rs.getDouble("total_amount"));
                    b.setCheckin(rs.getDate("checkin"));
                    b.setCheckout(rs.getDate("checkout"));
                    // Get String status from database directly
                    b.setStatus(rs.getString("status"));
                    bills.add(b);
                }
                return bills;
            }, tableNumber);
        } catch (Exception e) {
            System.err.println("Error in findAllByTableNumber for table " + tableNumber + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    // Helper method để convert Date to Timestamp
    private Timestamp convertToTimestamp(java.util.Date date) {
        return date != null ? new Timestamp(date.getTime()) : null;
    }
}