package com.team4.quanliquanmicay.Controller;

import com.team4.quanliquanmicay.Entity.Bill;
import com.team4.quanliquanmicay.Entity.Customer;
import com.team4.quanliquanmicay.Entity.BillDetails;
import com.team4.quanliquanmicay.Entity.TableForCustomer;
import java.util.List;

/**
 * Controller interface cho quản lý thanh toán
 * Kế thừa CrudController để tái sử dụng create, update, delete, clear
 * @author Team4
 */
public interface PaymentController extends CrudController<Bill> {
    
    /**
     * Lấy danh sách tất cả bàn
     */
    List<TableForCustomer> getAllTables();
    
    /**
     * Lấy thông tin hóa đơn theo số bàn
     */
    Bill getBillByTableNumber(int tableNumber);
    
    /**
     * Lấy chi tiết hóa đơn
     */
    List<BillDetails> getBillDetails(int billId);
    
    /**
     * Lấy thông tin khách hàng
     */
    Customer getCustomerByPhone(String phoneNumber);
    
    /**
     * Tính tổng tiền hóa đơn
     */
    double calculateTotalAmount(List<BillDetails> billDetails);
    
    /**
     * Xử lý thanh toán
     */
    boolean processPayment(Bill bill, Customer customer, double totalAmount, int tableNumber);
    
    /**
     * Tạo hội viên mới
     */
    boolean createMember(String phoneNumber, String customerName);
    
    /**
     * Tìm kiếm khách hàng theo số điện thoại
     */
    Customer searchCustomer(String phoneNumber);
    
    /**
     * Lấy tên sản phẩm theo product_id
     */
    String getProductName(String productId);
    
    // Các method từ CrudController đã có sẵn:
    // void open();           // Hiển thị dialog
    // void create();         // Tạo hóa đơn mới  
    // void update();         // Cập nhật hóa đơn
    // void delete();         // Xóa hóa đơn
    // void clear();          // Làm mới form
} 