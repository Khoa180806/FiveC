/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.team4.quanliquanmicay.View.management;

import com.team4.quanliquanmicay.Controller.PaymentHistoryController;
import com.team4.quanliquanmicay.util.XTheme;
import com.team4.quanliquanmicay.util.XDialog;
import com.team4.quanliquanmicay.DAO.BillDAO;
import com.team4.quanliquanmicay.DAO.PaymentHistoryDAO;
import com.team4.quanliquanmicay.DAO.PaymentMethodDAO;
import com.team4.quanliquanmicay.DAO.UserDAO;
import com.team4.quanliquanmicay.Entity.Bill;
import com.team4.quanliquanmicay.Entity.PaymentHistory;
import com.team4.quanliquanmicay.Entity.PaymentMethod;
import com.team4.quanliquanmicay.Entity.UserAccount;
import com.team4.quanliquanmicay.Impl.BillDAOImpl;
import com.team4.quanliquanmicay.Impl.PaymentHistoryDAOImpl;
import com.team4.quanliquanmicay.Impl.PaymentMethodDAOImpl;
import com.team4.quanliquanmicay.Impl.UserDAOImpl;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.toedter.calendar.JCalendar;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author HP
 */
public class HistoryManagement extends javax.swing.JFrame implements PaymentHistoryController {

    // DAO objects
    private final BillDAO billDAO = new BillDAOImpl();
    private final PaymentHistoryDAO paymentHistoryDAO = new PaymentHistoryDAOImpl();
    private final PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl(); // Thêm UserDAO
    
    // Date formatter
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * Creates new form LichSuJDialog
     */
    public HistoryManagement() {
        this.setUndecorated(true);
        XTheme.applyFullTheme();
        initComponents();
        this.setLocationRelativeTo(null);
        
        setupTable();
        setupEventHandlers();
        // BỎ DÒNG loadAllData() - KHÔNG TỰ ĐỘNG LOAD DỮ LIỆU KHI MỞ FORM
    }
    
    /**
     * Thiết lập bảng
     */
    private void setupTable() {
        // Thiết lập bảng Bills - chỉ 6 cột như yêu cầu
        DefaultTableModel billsModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Mã hóa đơn", "Tên nhân viên", "Bàn số", "Thời gian vào", "Thời gian ra", "Trạng thái"
            }
        ) {
            private static final boolean[] CAN_EDIT = {false, false, false, false, false, false};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return CAN_EDIT[columnIndex];
            }
        };
        tblBills.setModel(billsModel);
        
        // TẮT KHẢ NĂNG DI CHUYỂN CỘT CHO BẢNG BILLS
        tblBills.getTableHeader().setReorderingAllowed(false);
        
        // Thiết lập bảng Payment - sửa lại thứ tự cột
        DefaultTableModel paymentModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Mã hóa đơn", "Phương thức thanh toán", "Tổng tiền", "Thời gian thanh toán", "Trạng thái"
            }
        ) {
            private static final boolean[] CAN_EDIT = {false, false, false, false, false};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return CAN_EDIT[columnIndex];
            }
        };
        tblPayment.setModel(paymentModel);
        
        // TẮT KHẢ NĂNG DI CHUYỂN CỘT CHO BẢNG PAYMENT
        tblPayment.getTableHeader().setReorderingAllowed(false);
        
        // Thiết lập độ rộng cột cho bảng Bills (6 cột)
        tblBills.getColumnModel().getColumn(0).setPreferredWidth(100); // Mã hóa đơn
        tblBills.getColumnModel().getColumn(1).setPreferredWidth(150); // Tên nhân viên
        tblBills.getColumnModel().getColumn(2).setPreferredWidth(80);  // Bàn số
        tblBills.getColumnModel().getColumn(3).setPreferredWidth(150); // Thời gian vào
        tblBills.getColumnModel().getColumn(4).setPreferredWidth(150); // Thời gian ra
        tblBills.getColumnModel().getColumn(5).setPreferredWidth(120); // Trạng thái
        
        // Thiết lập độ rộng cột cho bảng Payment (5 cột)
        tblPayment.getColumnModel().getColumn(0).setPreferredWidth(100); // Mã hóa đơn
        tblPayment.getColumnModel().getColumn(1).setPreferredWidth(150); // Phương thức thanh toán
        tblPayment.getColumnModel().getColumn(2).setPreferredWidth(120); // Tổng tiền
        tblPayment.getColumnModel().getColumn(3).setPreferredWidth(150); // Thời gian thanh toán
        tblPayment.getColumnModel().getColumn(4).setPreferredWidth(120); // Trạng thái
    }
    
    /**
     * Thiết lập event handlers
     */
    private void setupEventHandlers() {
        // Nút chọn ngày từ
        btnCheckin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDatePicker(txtCheckin);
            }
        });
        
        // Nút chọn ngày đến
        btnCheckout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDatePicker(txtCheckout);
            }
        });
        
        // Nút lọc
        btnFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterData();
            }
        });
        
        // ComboBox thời gian
        cboFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleTimeRangeSelection();
            }
        });
        
        // THÊM EVENT HANDLER CHO NÚT EXIT
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExit();
            }
        });
    }
    
    /**
     * Hiển thị JCalendar để chọn ngày
     */
    private void showDatePicker(javax.swing.JTextField textField) {
        JDialog dialog = new JDialog(this, "Chọn ngày", true);
        JCalendar calendar = new JCalendar();
        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Hủy");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(calendar, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnOk);
        btnPanel.add(btnCancel);
        panel.add(btnPanel, BorderLayout.SOUTH);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        btnOk.addActionListener(e -> {
            java.util.Date selectedDate = calendar.getDate();
            if (selectedDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                textField.setText(sdf.format(selectedDate));
            }
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
    
    /**
     * Validate khoảng thời gian checkin và checkout
     */
    private boolean validateDateRange(String fromDateStr, String toDateStr) {
        try {
            if (fromDateStr.isEmpty() || toDateStr.isEmpty()) {
                return true; // Cho phép để trống
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            
            Date fromDate = sdf.parse(fromDateStr);
            Date toDate = sdf.parse(toDateStr);
            
            // Kiểm tra checkin có lớn hơn checkout không
            if (fromDate.after(toDate)) {
                XDialog.error(
                    "Ngày bắt đầu không thể lớn hơn ngày kết thúc!\n" +
                    "Vui lòng nhập lại khoảng thời gian hợp lệ.",
                    "Lỗi khoảng thời gian"
                );
                return false;
            }
            
            return true;
        } catch (Exception e) {
            XDialog.error("Lỗi khi kiểm tra khoảng thời gian: " + e.getMessage(), "Lỗi validation");
            return false;
        }
    }
    
    /**
     * Validate và format ngày tháng
     */
    private Date parseDate(String dateStr) {
        try {
            if (dateStr == null || dateStr.trim().isEmpty()) {
                return null;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            return sdf.parse(dateStr.trim());
        } catch (Exception e) {
            XDialog.error("Định dạng ngày không hợp lệ: " + dateStr + "\nVui lòng nhập theo định dạng dd/MM/yyyy", "Lỗi định dạng");
            return null;
        }
    }

    /**
     * Xử lý khi chọn khoảng thời gian từ ComboBox
     */
    private void handleTimeRangeSelection() {
        try {
            String selected = (String) cboFilter.getSelectedItem();
            Date fromDate = null;
            Date toDate = null;
            
            switch (selected) {
                case "Hôm nay":
                    fromDate = getStartOfDay(new Date());
                    toDate = getEndOfDay(new Date()); // Cùng ngày hôm nay
                    break;
                case "Tuần này":
                    fromDate = getStartOfWeek(new Date());
                    toDate = getEndOfWeek(new Date()); // Đến cuối tuần
                    break;
                case "Tháng này":
                    fromDate = getStartOfMonth(new Date());
                    toDate = getEndOfMonth(new Date()); // Đến cuối tháng
                    break;
                case "Quý này":
                    fromDate = getStartOfQuarter(new Date());
                    toDate = getEndOfQuarter(new Date()); // Đến cuối quý
                    break;
                case "Năm này":
                    fromDate = getStartOfYear(new Date());
                    toDate = getEndOfYear(new Date()); // Đến cuối năm
                    break;
            }
            
            if (fromDate != null && toDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                txtCheckin.setText(sdf.format(fromDate));
                txtCheckout.setText(sdf.format(toDate));
                
                // BỎ DÒNG TỰ ĐỘNG LỌC DỮ LIỆU - CHỈ FILL NGÀY THÁNG
                // filterDataByDateRange(fromDate, toDate);
            }
        } catch (Exception e) {
            XDialog.error("Lỗi khi xử lý khoảng thời gian: " + e.getMessage(), "Lỗi hệ thống");
        }
    }
    
    /**
     * Lọc dữ liệu theo điều kiện
     */
    private void filterData() {
        try {
            String fromDateStr = txtCheckin.getText().trim();
            String toDateStr = txtCheckout.getText().trim();
            
            // Validate khoảng thời gian trước khi lọc
            if (!validateDateRange(fromDateStr, toDateStr)) {
                return; // Dừng nếu validation thất bại
            }
            
            if (fromDateStr.isEmpty() && toDateStr.isEmpty()) {
                // Nếu không có điều kiện lọc, load tất cả
                loadAllData();
                return;
            }
            
            // Parse ngày tháng
            Date fromDate = parseDate(fromDateStr);
            Date toDate = parseDate(toDateStr);
            
            if (fromDate == null && !fromDateStr.isEmpty()) {
                return; // Dừng nếu parse thất bại
            }
            
            if (toDate == null && !toDateStr.isEmpty()) {
                return; // Dừng nếu parse thất bại
            }
            
            if (toDate != null) {
                // Set thời gian cuối ngày
                toDate = getEndOfDay(toDate);
            }
            
            filterDataByDateRange(fromDate, toDate);
            
        } catch (Exception e) {
            XDialog.error("Lỗi khi lọc dữ liệu: " + e.getMessage(), "Lỗi hệ thống");
        }
    }
    
    /**
     * Lọc dữ liệu theo khoảng thời gian
     */
    private void filterDataByDateRange(Date fromDate, Date toDate) {
        try {
            // Load cả Bill và PaymentHistory
            List<Bill> bills = billDAO.findAll();
            List<PaymentHistory> paymentHistoryList = paymentHistoryDAO.findAll();
            
            // Lọc Bill theo khoảng thời gian
            List<Bill> filteredBills = bills.stream()
                .filter(bill -> {
                    if (bill.getCheckin() == null) return false;
                    
                    boolean afterFromDate = fromDate == null || bill.getCheckin().after(fromDate) || bill.getCheckin().equals(fromDate);
                    boolean beforeToDate = toDate == null || bill.getCheckin().before(toDate) || bill.getCheckin().equals(toDate);
                    
                    return afterFromDate && beforeToDate;
                })
                .collect(java.util.stream.Collectors.toList());
            
            // Lọc PaymentHistory theo khoảng thời gian
            List<PaymentHistory> filteredHistory = paymentHistoryList.stream()
                .filter(history -> {
                    if (history.getPayment_date() == null) return false;
                    
                    boolean afterFromDate = fromDate == null || history.getPayment_date().after(fromDate) || history.getPayment_date().equals(fromDate);
                    boolean beforeToDate = toDate == null || history.getPayment_date().before(toDate) || history.getPayment_date().equals(toDate);
                    
                    return afterFromDate && beforeToDate;
                })
                .collect(java.util.stream.Collectors.toList());
            
            fillTableWithData(filteredBills, filteredHistory);
            
            // HIỂN THỊ THÔNG BÁO SỐ LƯỢNG RECORD ĐƯỢC LỌC
            String message = String.format(
                "Tìm thấy %d hóa đơn và %d giao dịch thanh toán trong khoảng thời gian đã chọn!",
                filteredBills.size(),
                filteredHistory.size()
            );
            
            XDialog.success(message, "Kết quả lọc dữ liệu");
            
        } catch (Exception e) {
            XDialog.error("Lỗi khi lọc dữ liệu: " + e.getMessage(), "Lỗi hệ thống");
        }
    }
    
    /**
     * Load tất cả dữ liệu
     */
    private void loadAllData() {
        try {
            List<Bill> bills = billDAO.findAll();
            List<PaymentHistory> paymentHistoryList = paymentHistoryDAO.findAll();
            fillTableWithData(bills, paymentHistoryList);
            
            // HIỂN THỊ THÔNG BÁO KHI LOAD TẤT CẢ DỮ LIỆU
            String message = String.format(
                "Đã load %d hóa đơn và %d giao dịch thanh toán!",
                bills.size(),
                paymentHistoryList.size()
            );
            
            XDialog.success(message, "Thành công");
        } catch (Exception e) {
            XDialog.error("Lỗi khi load dữ liệu: " + e.getMessage(), "Lỗi hệ thống");
        }
    }
    
    /**
     * Fill table với cả Bill và PaymentHistory
     */
    private void fillTableWithData(List<Bill> bills, List<PaymentHistory> paymentHistoryList) {
        // Fill bảng Bills
        DefaultTableModel billsModel = (DefaultTableModel) tblBills.getModel();
        billsModel.setRowCount(0);
        
        if (bills != null) {
            for (Bill bill : bills) {
                String checkinTime = bill.getCheckin() != null ? dateFormat.format(bill.getCheckin()) : "N/A";
                String checkoutTime = bill.getCheckout() != null ? dateFormat.format(bill.getCheckout()) : "N/A";
                
                // Sửa lại logic xử lý status - status là Boolean, cần convert sang String
                String status = "N/A";
                if (bill.getStatus() != null) {
                    status = bill.getStatus() ? "Đã thanh toán" : "Đang phục vụ";
                }
                
                String employeeName = getEmployeeName(bill.getUser_id()); // Lấy tên nhân viên
                
                Object[] row = {
                    bill.getBill_id(),
                    employeeName,
                    bill.getTable_number() > 0 ? "Bàn " + bill.getTable_number() : "N/A",
                    checkinTime,
                    checkoutTime,
                    status
                };
                billsModel.addRow(row);
            }
        }
        
        // Fill bảng Payment
        DefaultTableModel paymentModel = (DefaultTableModel) tblPayment.getModel();
        paymentModel.setRowCount(0);
        
        if (paymentHistoryList != null) {
            for (PaymentHistory payment : paymentHistoryList) {
                try {
                    // Tìm bill_id tương ứng
                    Integer billId = findBillIdByPaymentHistoryId(payment.getPayment_history_id());
                    
                    // Lấy thông tin phương thức thanh toán
                    PaymentMethod paymentMethod = paymentMethodDAO.findById(payment.getPayment_method_id());
                    String methodName = paymentMethod != null ? paymentMethod.getMethod_name() : "N/A";
                    
                    // Format thời gian thanh toán
                    String paymentTime = payment.getPayment_date() != null ? dateFormat.format(payment.getPayment_date()) : "N/A";
                    
                    // Format số tiền
                    String totalAmount = formatCurrency(payment.getTotal_amount());
                    
                    // Xử lý trạng thái - status là String trong database
                    String status = payment.getStatus() != null ? payment.getStatus() : "N/A";
                    
                    Object[] row = {
                        billId != null ? billId : "N/A", // Mã hóa đơn
                        methodName, // Phương thức thanh toán
                        totalAmount, // Tổng tiền
                        paymentTime, // Thời gian thanh toán
                        status // Trạng thái
                    };
                    paymentModel.addRow(row);
                } catch (Exception e) {
                    System.err.println("Lỗi khi xử lý payment: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Lấy tên phương thức thanh toán theo ID
     */
    private String getPaymentMethodName(Integer paymentMethodId) {
        try {
            if (paymentMethodId == null) return "N/A";
            
            PaymentMethod method = paymentMethodDAO.findById(paymentMethodId);
            return method != null ? method.getMethod_name() : "N/A";
        } catch (Exception e) {
            return "N/A";
        }
    }
    
    /**
     * Lấy tên nhân viên theo user_id
     */
    private String getEmployeeName(String userId) {
        try {
            if (userId == null || userId.trim().isEmpty()) return "N/A";
            
            UserAccount user = userDAO.findById(userId);
            return user != null ? user.getFullName() : "N/A";
        } catch (Exception e) {
            return "N/A";
        }
    }
    
    /**
     * Format tiền tệ
     */
    private String formatCurrency(double amount) {
        if (amount < 0) return "0 VNĐ";
        return String.format("%,.0f VNĐ", amount);
    }
    
    /**
     * Tìm bill_id dựa trên payment_history_id
     */
    private Integer findBillIdByPaymentHistoryId(Integer paymentHistoryId) {
        if (paymentHistoryId == null) return null;
        
        try {
            List<Bill> allBills = billDAO.findAll();
            for (Bill bill : allBills) {
                if (bill.getPayment_history_id() != null && 
                    bill.getPayment_history_id().equals(paymentHistoryId)) {
                    return bill.getBill_id();
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm bill_id: " + e.getMessage());
        }
        return null;
    }
    
    // ========== HELPER METHODS FOR DATE RANGES ==========
    
    /**
     * Lấy đầu ngày
     */
    private Date getStartOfDay(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    /**
     * Lấy cuối ngày
     */
    private Date getEndOfDay(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
        cal.set(java.util.Calendar.MINUTE, 59);
        cal.set(java.util.Calendar.SECOND, 59);
        cal.set(java.util.Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
    
    /**
     * Lấy đầu tuần
     */
    private Date getStartOfWeek(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    /**
     * Lấy đầu tháng
     */
    private Date getStartOfMonth(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    /**
     * Lấy đầu quý
     */
    private Date getStartOfQuarter(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(java.util.Calendar.MONTH);
        int quarter = (month / 3) * 3;
        cal.set(java.util.Calendar.MONTH, quarter);
        cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    /**
     * Lấy đầu năm
     */
    private Date getStartOfYear(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.DAY_OF_YEAR, 1);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // ========== THÊM CÁC HÀM HELPER MỚI ==========
    
    /**
     * Lấy cuối tuần
     */
    private Date getEndOfWeek(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6); // Chủ nhật
        cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
        cal.set(java.util.Calendar.MINUTE, 59);
        cal.set(java.util.Calendar.SECOND, 59);
        cal.set(java.util.Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
    
    /**
     * Lấy cuối tháng
     */
    private Date getEndOfMonth(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.DAY_OF_MONTH, cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
        cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
        cal.set(java.util.Calendar.MINUTE, 59);
        cal.set(java.util.Calendar.SECOND, 59);
        cal.set(java.util.Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
    
    /**
     * Lấy cuối quý
     */
    private Date getEndOfQuarter(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(java.util.Calendar.MONTH);
        int quarter = (month / 3) * 3 + 2; // Tháng cuối của quý
        cal.set(java.util.Calendar.MONTH, quarter);
        cal.set(java.util.Calendar.DAY_OF_MONTH, cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
        cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
        cal.set(java.util.Calendar.MINUTE, 59);
        cal.set(java.util.Calendar.SECOND, 59);
        cal.set(java.util.Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
    
    /**
     * Lấy cuối năm
     */
    private Date getEndOfYear(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.DAY_OF_YEAR, cal.getActualMaximum(java.util.Calendar.DAY_OF_YEAR));
        cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
        cal.set(java.util.Calendar.MINUTE, 59);
        cal.set(java.util.Calendar.SECOND, 59);
        cal.set(java.util.Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        pnlUi = new javax.swing.JPanel();
        pnlTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblCheckin = new javax.swing.JLabel();
        txtCheckin = new javax.swing.JTextField();
        lblCheckout = new javax.swing.JLabel();
        txtCheckout = new javax.swing.JTextField();
        btnCheckin = new javax.swing.JButton();
        btnCheckout = new javax.swing.JButton();
        cboFilter = new javax.swing.JComboBox<>();
        btnFilter = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlBills = new javax.swing.JPanel();
        jpsBIlls = new javax.swing.JScrollPane();
        tblBills = new javax.swing.JTable();
        pnlPayment = new javax.swing.JPanel();
        jspPayment = new javax.swing.JScrollPane();
        tblPayment = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        btnExit = new javax.swing.JButton();

        jLabel3.setText("jLabel3");

        jButton3.setText("jButton3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 51, 51));

        pnlUi.setBackground(new java.awt.Color(204, 164, 133));

        pnlTitle.setBackground(new java.awt.Color(134, 39, 43));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("LỊCH SỬ");

        javax.swing.GroupLayout pnlTitleLayout = new javax.swing.GroupLayout(pnlTitle);
        pnlTitle.setLayout(pnlTitleLayout);
        pnlTitleLayout.setHorizontalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTitleLayout.createSequentialGroup()
                .addGap(238, 238, 238)
                .addComponent(lblTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTitleLayout.setVerticalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        lblCheckin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCheckin.setForeground(new java.awt.Color(255, 255, 255));
        lblCheckin.setText("TỪ :");

        txtCheckin.setEditable(false);
        txtCheckin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblCheckout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCheckout.setForeground(new java.awt.Color(255, 255, 255));
        lblCheckout.setText("ĐẾN :");

        txtCheckout.setEditable(false);
        txtCheckout.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnCheckin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCheckin.setText("...");

        btnCheckout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCheckout.setText("...");

        cboFilter.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboFilter.setForeground(new java.awt.Color(204, 204, 204));
        cboFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hôm nay", "Tuần này", "Tháng này", "Quý này", "Năm này" }));

        btnFilter.setBackground(new java.awt.Color(204, 204, 204));
        btnFilter.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnFilter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/icon/icons8-sort-24.png"))); // NOI18N
        btnFilter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        tblBills.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Tên nhân viên", "Bàn số", "Thời gian vào", "Thời gian ra", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBills.getTableHeader().setResizingAllowed(false);
        tblBills.getTableHeader().setReorderingAllowed(false);
        jpsBIlls.setViewportView(tblBills);
        if (tblBills.getColumnModel().getColumnCount() > 0) {
            tblBills.getColumnModel().getColumn(0).setResizable(false);
            tblBills.getColumnModel().getColumn(1).setResizable(false);
            tblBills.getColumnModel().getColumn(2).setMinWidth(40);
            tblBills.getColumnModel().getColumn(2).setMaxWidth(60);
            tblBills.getColumnModel().getColumn(3).setResizable(false);
            tblBills.getColumnModel().getColumn(4).setResizable(false);
            tblBills.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout pnlBillsLayout = new javax.swing.GroupLayout(pnlBills);
        pnlBills.setLayout(pnlBillsLayout);
        pnlBillsLayout.setHorizontalGroup(
            pnlBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpsBIlls, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
        );
        pnlBillsLayout.setVerticalGroup(
            pnlBillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpsBIlls, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Hóa đơn", pnlBills);

        tblPayment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Cách thức thanh toán", "Tổng tiền", "Thời gian thanh toán", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jspPayment.setViewportView(tblPayment);
        if (tblPayment.getColumnModel().getColumnCount() > 0) {
            tblPayment.getColumnModel().getColumn(0).setResizable(false);
            tblPayment.getColumnModel().getColumn(1).setMinWidth(130);
            tblPayment.getColumnModel().getColumn(1).setMaxWidth(150);
            tblPayment.getColumnModel().getColumn(2).setMinWidth(60);
            tblPayment.getColumnModel().getColumn(2).setMaxWidth(70);
            tblPayment.getColumnModel().getColumn(3).setMinWidth(120);
            tblPayment.getColumnModel().getColumn(3).setMaxWidth(140);
            tblPayment.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout pnlPaymentLayout = new javax.swing.GroupLayout(pnlPayment);
        pnlPayment.setLayout(pnlPaymentLayout);
        pnlPaymentLayout.setHorizontalGroup(
            pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspPayment, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
        );
        pnlPaymentLayout.setVerticalGroup(
            pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspPayment, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Thanh toán", pnlPayment);

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        btnExit.setBackground(new java.awt.Color(185, 163, 147));
        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("Thoát");
        btnExit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlUiLayout = new javax.swing.GroupLayout(pnlUi);
        pnlUi.setLayout(pnlUiLayout);
        pnlUiLayout.setHorizontalGroup(
            pnlUiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlUiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUiLayout.createSequentialGroup()
                        .addComponent(lblCheckin, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(txtCheckin, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCheckin)
                        .addGap(18, 18, 18)
                        .addComponent(lblCheckout)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCheckout)
                        .addGap(24, 24, 24)
                        .addComponent(cboFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jTabbedPane1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlUiLayout.createSequentialGroup()
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlUiLayout.setVerticalGroup(
            pnlUiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUiLayout.createSequentialGroup()
                .addComponent(pnlTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCheckin)
                    .addComponent(txtCheckin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCheckin)
                    .addComponent(lblCheckout)
                    .addComponent(txtCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFilter)
                    .addComponent(btnCheckout))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlUi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlUi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
       if (XDialog.confirm("Bạn có chắc chắn muốn thoát khỏi ứng dụng không?", "Xác nhận thoát")) 
          this.dispose(); // Đóng cửa sổ hiện tại

    }//GEN-LAST:event_btnExitActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HistoryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HistoryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HistoryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HistoryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HistoryManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckin;
    private javax.swing.JButton btnCheckout;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnFilter;
    private javax.swing.JComboBox<String> cboFilter;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JScrollPane jpsBIlls;
    private javax.swing.JScrollPane jspPayment;
    private javax.swing.JLabel lblCheckin;
    private javax.swing.JLabel lblCheckout;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlBills;
    private javax.swing.JPanel pnlPayment;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JPanel pnlUi;
    private javax.swing.JTable tblBills;
    private javax.swing.JTable tblPayment;
    private javax.swing.JTextField txtCheckin;
    private javax.swing.JTextField txtCheckout;
    // End of variables declaration//GEN-END:variables

    @Override
    public void open() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'open'");
    }

    @Override
    public void setForm(PaymentHistory entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setForm'");
    }

    @Override
    public PaymentHistory getForm() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getForm'");
    }

    @Override
    public void fillToTable() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillToTable'");
    }

    @Override
    public void edit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'edit'");
    }

    @Override
    public void create() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    @Override
    public void setEditable(boolean editable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEditable'");
    }

    @Override
    public void checkAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkAll'");
    }

    @Override
    public void uncheckAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'uncheckAll'");
    }

    @Override
    public void deleteCheckedItems() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCheckedItems'");
    }

    /**
     * Xử lý sự kiện exit với XDialog
     */
    private void handleExit() {
        if (XDialog.confirm("Bạn có chắc chắn muốn thoát khỏi ứng dụng?", "Xác nhận thoát")) {
            this.dispose();
        }
        // Nếu chọn NO thì không làm gì cả, tiếp tục sử dụng ứng dụng
    }
}
