package com.team4.quanliquanmicay.Controller;

import com.team4.quanliquanmicay.Entity.*;
import com.team4.quanliquanmicay.DAO.*;
import com.team4.quanliquanmicay.Impl.*;
import com.team4.quanliquanmicay.util.XDialog;
import com.team4.quanliquanmicay.util.XJdbc;
import java.util.*;
import java.util.List;

/**
 * Controller xử lý business logic cho thanh toán
 * @author FiveC
 */
public class PaymentController {
    
    // DAO Objects
    private BillDetailsDAO billDetailsDAO;
    private CustomerDAO customerDAO;
    private PaymentHistoryDAO paymentHistoryDAO;
    private TableForCustomerDAO tableDAO;
    private BillDAO billDAO;
    
    public PaymentController() {
        billDetailsDAO = new BillDetailsDAOImpl();
        customerDAO = new CustomerDAOImpl();
        paymentHistoryDAO = new PaymentHistoryDAOImpl();
        tableDAO = new TableForCustomerDAOImpl();
        billDAO = new BillDAOImpl();
    }
    
    /**
     * Lấy danh sách tất cả bàn
     */
    public List<TableForCustomer> getAllTables() {
        try {
            return tableDAO.findAll();
        } catch (Exception e) {
            XDialog.alert("Lỗi khi load danh sách bàn: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Lấy thông tin hóa đơn theo số bàn
     */
    public Bill getBillByTableNumber(int tableNumber) {
        try {
            String sql = "SELECT * FROM BILL WHERE table_number = ? AND status = 1";
            Bill bill = XJdbc.executeQuery(sql, rs -> {
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
                    b.setStatus(rs.getBoolean("status"));
                    return b;
                }
                return null;
            }, tableNumber);
            return bill;
        } catch (Exception e) {
            XDialog.alert("Lỗi khi load hóa đơn: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Lấy chi tiết hóa đơn
     */
    public List<BillDetails> getBillDetails(int billId) {
        try {
            return billDetailsDAO.findByBillId(billId);
        } catch (Exception e) {
            XDialog.alert("Lỗi khi load chi tiết hóa đơn: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Lấy thông tin khách hàng
     */
    public Customer getCustomerByPhone(String phoneNumber) {
        try {
            return customerDAO.findById(phoneNumber);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Tính tổng tiền hóa đơn
     */
    public double calculateTotalAmount(List<BillDetails> billDetails) {
        double total = 0.0;
        if (billDetails != null) {
            for (BillDetails detail : billDetails) {
                total += (detail.getPrice() - detail.getDiscount()) * detail.getAmount();
            }
        }
        return total;
    }
    
    /**
     * Xử lý thanh toán
     */
    public boolean processPayment(Bill bill, Customer customer, double totalAmount, int tableNumber) {
        try {
            // Validate input
            if (bill == null) {
                XDialog.alert("Không có hóa đơn để thanh toán!");
                return false;
            }
            
            if (totalAmount <= 0) {
                XDialog.alert("Hóa đơn không có món nào để thanh toán!");
                return false;
            }
            
            // Tạo payment history
            PaymentHistory payment = new PaymentHistory();
            payment.setPayment_history_id(generatePaymentId());
            payment.setPayment_method_id(1); // Tiền mặt
            payment.setPayment_date(new Date());
            payment.setTotal_amount(totalAmount);
            payment.setStatus(true);
            payment.setNote("Thanh toán hóa đơn bàn " + tableNumber);
            
            paymentHistoryDAO.create(payment);
            
            // Cập nhật bill
            bill.setPayment_history_id(Integer.parseInt(payment.getPayment_history_id()));
            bill.setStatus(false); // Đã thanh toán
            bill.setCheckout(new Date());
            bill.setTotal_amount(totalAmount);
            billDAO.update(bill);
            
            // Cập nhật điểm khách hàng
            if (customer != null) {
                int currentPoints = customer.getPoint_level();
                int newPoints = currentPoints + (int)(totalAmount / 1000); // 1 điểm cho mỗi 1000đ
                customer.setPoint_level(newPoints);
                customerDAO.update(customer);
            }
            
            // Cập nhật trạng thái bàn
            TableForCustomer table = tableDAO.findById(tableNumber);
            if (table != null) {
                table.setStatus(0); // Trống
                tableDAO.update(table);
            }
            
            return true;
            
        } catch (Exception e) {
            XDialog.alert("Lỗi khi thanh toán: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Tạo hội viên mới
     */
    public boolean createMember(String phoneNumber, String customerName) {
        try {
            // Validate input
            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                XDialog.alert("Vui lòng nhập số điện thoại!");
                return false;
            }
            
            if (!phoneNumber.matches("\\d{10,11}")) {
                XDialog.alert("Số điện thoại không hợp lệ! Vui lòng nhập 10-11 chữ số.");
                return false;
            }
            
            if (customerName == null || customerName.trim().isEmpty()) {
                XDialog.alert("Vui lòng nhập tên khách hàng!");
                return false;
            }
            
            // Kiểm tra khách hàng đã tồn tại
            Customer existingCustomer = customerDAO.findById(phoneNumber);
            if (existingCustomer != null) {
                XDialog.alert("Khách hàng này đã tồn tại trong hệ thống!");
                return false;
            }
            
            // Tạo khách hàng mới
            Customer newCustomer = new Customer();
            newCustomer.setPhone_number(phoneNumber);
            newCustomer.setCustomer_name(customerName);
            newCustomer.setPoint_level(0);
            newCustomer.setLevel_ranking("Bronze");
            newCustomer.setCreated_date(new Date());
            
            customerDAO.create(newCustomer);
            
            return true;
            
        } catch (Exception e) {
            XDialog.alert("Lỗi khi tạo hội viên: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Tìm kiếm khách hàng theo số điện thoại
     */
    public Customer searchCustomer(String phoneNumber) {
        try {
            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                return null;
            }
            
            return customerDAO.findById(phoneNumber);
            
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Tạo ID cho payment history
     */
    private String generatePaymentId() {
        return "PAY" + System.currentTimeMillis();
    }
    
    /**
     * Lấy tên sản phẩm theo product_id
     */
    public String getProductName(String productId) {
        try {
            // Có thể tạo ProductDAO để lấy thông tin sản phẩm
            // Tạm thời return productId
            return productId;
        } catch (Exception e) {
            return "Unknown";
        }
    }
} 