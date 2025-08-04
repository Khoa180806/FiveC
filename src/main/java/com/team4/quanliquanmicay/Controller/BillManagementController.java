package com.team4.quanliquanmicay.Controller;

import com.team4.quanliquanmicay.Entity.Bill;
import java.util.Date;
import java.util.List;

/**
 * Interface controller cho BillManagement
 * Định nghĩa các method cần thiết cho việc quản lý hóa đơn
 */
public interface BillManagementController {
    
    /**
     * Load dữ liệu hóa đơn vào table
     */
    void loadBillData();
    
    /**
     * Load chi tiết hóa đơn theo bill_id
     * @param billId ID của hóa đơn
     */
    void loadBillDetails(Integer billId);
    
    /**
     * Hiển thị thông tin hóa đơn lên form
     * @param bill Hóa đơn cần hiển thị
     */
    void displayBillInfo(Bill bill);
    
    /**
     * Xóa form
     */
    void clearForm();
    
    /**
     * Cập nhật hóa đơn
     */
    void updateBill();
    
    /**
     * Xóa hóa đơn
     */
    void removeBill();
    
    /**
     * Lọc hóa đơn theo thời gian
     */
    void filterBills();
    
    /**
     * Hiển thị dialog chọn ngày sử dụng JCalendar
     * @param title Tiêu đề dialog
     * @param textField Text field để nhận giá trị ngày
     */
    void showDatePickerDialog(String title, javax.swing.JTextField textField);
    
    /**
     * Kiểm tra validation ngày bắt đầu và kết thúc
     * @param textField Text field đang được chọn
     * @param selectedDate Ngày được chọn
     * @return true nếu validation thành công, false nếu thất bại
     */
    boolean validateDateRange(javax.swing.JTextField textField, Date selectedDate);
    
    /**
     * Cập nhật ngày bắt đầu và kết thúc theo combobox thời gian
     */
    void updateDateRangeFromComboBox();
    
    /**
     * Thiết lập khoảng ngày mặc định là hôm nay
     */
    void setDefaultDateRange();
    
    /**
     * Khởi tạo date picker
     */
    void initDatePickers();
    
    /**
     * Thêm các event listeners
     */
    void addEventListeners();
    
    /**
     * Lấy danh sách tất cả hóa đơn
     * @return Danh sách hóa đơn
     */
    List<Bill> getAllBills();
    
    /**
     * Tìm hóa đơn theo ID
     * @param billId ID hóa đơn
     * @return Hóa đơn tìm được
     */
    Bill findBillById(String billId);
    
    /**
     * Lọc hóa đơn theo khoảng thời gian
     * @param beginDate Ngày bắt đầu
     * @param endDate Ngày kết thúc
     * @return Danh sách hóa đơn đã lọc
     */
    List<Bill> filterBillsByDateRange(Date beginDate, Date endDate);
    
    /**
     * Lọc hóa đơn theo thời gian định sẵn
     * @param timeRange Loại thời gian (Hôm nay, Tuần này, Tháng này, Quý này, Năm này)
     * @return Danh sách hóa đơn đã lọc
     */
    List<Bill> filterBillsByTimeRange(String timeRange);
}