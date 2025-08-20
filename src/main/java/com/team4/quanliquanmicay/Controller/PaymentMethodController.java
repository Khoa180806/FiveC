package com.team4.quanliquanmicay.Controller;

import com.team4.quanliquanmicay.Entity.PaymentMethod;

/**
 * Controller đơn giản cho quản lý phương thức thanh toán
 * Kế thừa CrudController để tái sử dụng create, update, delete, clear
 * @author Team4
 */
public interface PaymentMethodController extends CrudController<PaymentMethod> {
    

    
    /**
     * Lấy danh sách phương thức thanh toán đang hoạt động
     */
    java.util.List<PaymentMethod> getActivePaymentMethods();
    
    /**
     * Tìm kiếm phương thức thanh toán theo tên
     */
    java.util.List<PaymentMethod> searchByName(String name);
    
    // Các method từ CrudController đã có sẵn:
    // void open();           // Hiển thị dialog
    // void create();         // Tạo phương thức thanh toán mới  
    // void update();         // Cập nhật phương thức thanh toán
    // void delete();         // Xóa phương thức thanh toán
    // void clear();          // Làm mới form
} 