/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.team4.quanliquanmicay.Controller;

import com.team4.quanliquanmicay.Entity.Bill;
import com.team4.quanliquanmicay.Entity.BillDetails;

/**
 * Controller cho quản lý hóa đơn
 * Kế thừa CrudController để tái sử dụng create, update, delete, clear
 * @author Team4
 */
public interface BillController extends CrudController<Bill> {
    
    /**
     * Load thông tin hóa đơn theo ID
     */
    void loadBill(String billId);
    
    /**
     * Load chi tiết hóa đơn
     */
    void loadBillDetails(Integer billId);
    
    /**
     * Mở dialog đặt món
     */
    void openDatMonDialog();
    
    /**
     * Xóa món đã chọn khỏi hóa đơn
     */
    void deleteSelectedItem();
    
    /**
     * Hủy hóa đơn hiện tại
     */
    void cancelBill();
    
    /**
     * Xem chi tiết hóa đơn (in hóa đơn)
     */
    void viewBillDetails();
    
    /**
     * Thanh toán hóa đơn
     */
    void payBill();
    
    /**
     * Tải danh sách hóa đơn theo trạng thái
     */
    void loadBillsByStatus(String status);
    
    /**
     * Tải danh sách hóa đơn theo bàn
     */
    void loadBillsByTable(Integer tableNumber);
    
    /**
     * Tải danh sách hóa đơn theo nhân viên
     */
    void loadBillsByEmployee(String userId);
    
    /**
     * Tải danh sách hóa đơn theo khoảng thời gian
     */
    void loadBillsByDateRange(java.util.Date fromDate, java.util.Date toDate);
    
    /**
     * Tính tổng tiền hóa đơn
     */
    double calculateTotalAmount();
    
    /**
     * Cập nhật trạng thái bàn
     */
    void updateTableStatus(Integer tableNumber, Integer status);
    
    /**
     * Lấy tên nhân viên từ user_id
     */
    String getEmployeeName(String userId);
    
    /**
     * Format tiền tệ
     */
    String formatCurrency(double amount);
    
    // Các method từ CrudController đã có sẵn:
    // void open();           // Hiển thị dialog
    // void create();         // Tạo hóa đơn mới  
    // void update();         // Cập nhật hóa đơn
    // void delete();         // Xóa hóa đơn
    // void clear();          // Làm mới form
    // void setForm(Bill entity); // Set dữ liệu lên form
    // Bill getForm();        // Lấy dữ liệu từ form
    // void fillToTable();    // Tải dữ liệu lên bảng
    // void edit();           // Chỉnh sửa hóa đơn
}
