/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.team4.quanliquanmicay.View.management;

import com.team4.quanliquanmicay.util.XTheme;
import com.team4.quanliquanmicay.DAO.BillDAO;
import com.team4.quanliquanmicay.DAO.BillDetailsDAO;
import com.team4.quanliquanmicay.DAO.ProductDAO;
import com.team4.quanliquanmicay.Entity.Bill;
import com.team4.quanliquanmicay.Entity.BillDetails;
import com.team4.quanliquanmicay.Entity.Product;
import com.team4.quanliquanmicay.Impl.BillDAOImpl;
import com.team4.quanliquanmicay.Impl.BillDetailsDAOImpl;
import com.team4.quanliquanmicay.Impl.ProductDAOImpl;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.toedter.calendar.JDateChooser;
import com.team4.quanliquanmicay.Controller.BillManagementController;

/**
 *
 * @author Asus
 */
public class BillManagement extends javax.swing.JFrame implements BillManagementController {

    private BillDAO billDAO;
    private BillDetailsDAO billDetailsDAO;
    private ProductDAO productDAO;
    private SimpleDateFormat dateFormat;
    private Bill currentBill;

    /**
     * Creates new form BillManagement
     */
    public BillManagement() {
        this.setUndecorated(true);
        XTheme.applyFullTheme();
        initComponents();
        this.setLocationRelativeTo(null);
        
        // Khởi tạo các DAO
        billDAO = new BillDAOImpl();
        billDetailsDAO = new BillDetailsDAOImpl();
        productDAO = new ProductDAOImpl();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        // Khởi tạo date picker
        initDatePickers();
        
        // Load dữ liệu ban đầu
        loadBillData();
        addEventListeners();
    }

    /**
     * Khởi tạo date picker
     */
    @Override
    public void initDatePickers() {
        // Khởi tạo ngày mặc định là hôm nay
        setDefaultDateRange();
    }
    
    /**
     * Thiết lập khoảng ngày mặc định là hôm nay
     */
    @Override
    public void setDefaultDateRange() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date today = new java.util.Date();
            
            // Điền ngày hôm nay cho cả txtBegin và txtEnd
            txtBegin.setText(sdf.format(today));
            txtEnd.setText(sdf.format(today));
            
            // Chọn "Hôm nay" trong combobox
            cboTime.setSelectedItem("Hôm nay");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi thiết lập ngày mặc định: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Load dữ liệu hóa đơn vào table
     */
    @Override
    public void loadBillData() {
        try {
            List<Bill> bills = billDAO.findAll();
            DefaultTableModel model = (DefaultTableModel) tblBill.getModel();
            model.setRowCount(0);
            
            for (Bill bill : bills) {
                // Chuyển đổi Boolean status sang String để hiển thị
                String statusText = "Đang phục vụ";
                if (bill.getStatus() != null) {
                    if (bill.getStatus()) {
                        statusText = "Đã thanh toán";
                    } else {
                        statusText = "Đang phục vụ";
                    }
                }
                
                Object[] row = {
                    bill.getBill_id(),
                    bill.getUser_id(),
                    bill.getTable_number(),
                    String.format("%,.0f VNĐ", bill.getTotal_amount()),
                    bill.getCheckin() != null ? dateFormat.format(bill.getCheckin()) : "",
                    bill.getCheckout() != null ? dateFormat.format(bill.getCheckout()) : "",
                    statusText
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage());
        }
    }

    /**
     * Load chi tiết hóa đơn theo bill_id
     */
    @Override
    public void loadBillDetails(Integer billId) {
        try {
            List<BillDetails> billDetails = billDetailsDAO.findByBillId(billId);
            DefaultTableModel model = (DefaultTableModel) tblBillDetail.getModel();
            model.setRowCount(0);
            
            for (BillDetails detail : billDetails) {
                Product product = productDAO.findById(detail.getProduct_id());
                String productName = product != null ? product.getProductName() : "Không xác định";
                
                Object[] row = {
                    productName,
                    detail.getAmount(),
                    String.format("%,.0f VNĐ", detail.getPrice()),
                    String.format("%.0f%%", detail.getDiscount() * 100),
                    String.format("%,.0f VNĐ", detail.getTotalPrice())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải chi tiết hóa đơn: " + e.getMessage());
        }
    }

    /**
     * Hiển thị thông tin hóa đơn lên form
     */
    @Override
    public void displayBillInfo(Bill bill) {
        if (bill == null) {
            clearForm();
            return;
        }
        
        currentBill = bill;
        txtBillId.setText(String.valueOf(bill.getBill_id()));
        txtUser.setText(bill.getUser_id());
        txtTable.setText(String.valueOf(bill.getTable_number()));
        txtAmount.setText(String.format("%,.0f VNĐ", bill.getTotal_amount()));
        txtCheckin.setText(bill.getCheckin() != null ? dateFormat.format(bill.getCheckin()) : "");
        txtCheckout.setText(bill.getCheckout() != null ? dateFormat.format(bill.getCheckout()) : "");
        
        // Set status trong combobox - chuyển đổi Boolean sang String
        Boolean status = bill.getStatus();
        if (status != null) {
            if (status) {
                cboStatus.setSelectedIndex(1); // "Đã thanh toán"
            } else {
                cboStatus.setSelectedIndex(0); // "Đang phục vụ"
            }
        } else {
            cboStatus.setSelectedIndex(0); // Mặc định "Đang phục vụ"
        }
        
        // Load chi tiết hóa đơn
        loadBillDetails(bill.getBill_id());
    }

    /**
     * Xóa form
     */
    @Override
    public void clearForm() {
        currentBill = null;
        txtBillId.setText("");
        txtUser.setText("");
        txtTable.setText("");
        txtAmount.setText("");
        txtCheckin.setText("");
        txtCheckout.setText("");
        cboStatus.setSelectedIndex(0);
        
        // Xóa chi tiết hóa đơn
        DefaultTableModel model = (DefaultTableModel) tblBillDetail.getModel();
        model.setRowCount(0);
    }

    /**
     * Thêm các event listeners
     */
    @Override
    public void addEventListeners() {
        // Sự kiện click vào table hóa đơn
        tblBill.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblBill.getSelectedRow();
                if (selectedRow >= 0) {
                    Integer billId = (Integer) tblBill.getValueAt(selectedRow, 0);
                    Bill bill = billDAO.findById(String.valueOf(billId));
                    displayBillInfo(bill);
                }
            }
        });

        // Sự kiện nút Update
        btnUpdate.addActionListener(e -> updateBill());

        // Sự kiện nút Remove
        btnRemove.addActionListener(e -> removeBill());

        // Sự kiện nút Exit
        btnExit.addActionListener(e -> dispose());

        // Sự kiện nút Filter
        btnFilter.addActionListener(e -> filterBills());
        
        // Sự kiện nút Begin (chọn ngày bắt đầu)
        btnBegin.addActionListener(e -> showDatePickerDialog("Chọn ngày bắt đầu", txtBegin));
        
        // Sự kiện nút End (chọn ngày kết thúc)
        btnEnd.addActionListener(e -> showDatePickerDialog("Chọn ngày kết thúc", txtEnd));
        
        // Sự kiện combobox thời gian
        cboTime.addActionListener(e -> updateDateRangeFromComboBox());
    }

    /**
     * Cập nhật hóa đơn
     */
    @Override
    public void updateBill() {
        if (currentBill == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần cập nhật!");
            return;
        }

        try {
            // Cập nhật trạng thái - chuyển đổi String sang Boolean
            String selectedStatus = (String) cboStatus.getSelectedItem();
            Boolean status = false; // Mặc định "Đang phục vụ"
            
            if (selectedStatus.equals("Đã thanh toán")) {
                status = true;
            } else if (selectedStatus.equals("Hủy")) {
                status = null; // Hoặc có thể set một giá trị khác tùy logic
            }
            
            currentBill.setStatus(status);
            
            // Nếu trạng thái là "Đã thanh toán" thì set checkout time
            if (selectedStatus.equals("Đã thanh toán") && currentBill.getCheckout() == null) {
                currentBill.setCheckout(new java.util.Date());
                txtCheckout.setText(dateFormat.format(currentBill.getCheckout()));
            }

            billDAO.update(currentBill);
            JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thành công!");
            loadBillData();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật hóa đơn: " + e.getMessage());
        }
    }

    /**
     * Xóa hóa đơn
     */
    @Override
    public void removeBill() {
        if (currentBill == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa hóa đơn này?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Xóa chi tiết hóa đơn trước
                List<BillDetails> billDetails = billDetailsDAO.findByBillId(currentBill.getBill_id());
                for (BillDetails detail : billDetails) {
                    billDetailsDAO.deleteById(String.valueOf(detail.getBill_detail_id()));
                }
                
                // Xóa hóa đơn
                billDAO.deleteById(String.valueOf(currentBill.getBill_id()));
                JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công!");
                
                clearForm();
                loadBillData();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa hóa đơn: " + e.getMessage());
            }
        }
    }

    /**
     * Hiển thị dialog chọn ngày sử dụng JCalendar
     */
    @Override
    public void showDatePickerDialog(String title, javax.swing.JTextField textField) {
        // Tạo dialog chọn ngày sử dụng JCalendar
        javax.swing.JDialog dialog = new javax.swing.JDialog(this, title, true);
        dialog.setLayout(new java.awt.BorderLayout());
        
        // Tạo JDateChooser với kích thước nhỏ hơn
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        
        // Thiết lập kích thước cho text field trong JDateChooser
        dateChooser.setPreferredSize(new java.awt.Dimension(150, 25));
        dateChooser.setSize(new java.awt.Dimension(150, 25));
        
        // Panel chính chứa date chooser với padding
        javax.swing.JPanel mainPanel = new javax.swing.JPanel();
        mainPanel.setLayout(new java.awt.BorderLayout());
        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(dateChooser, java.awt.BorderLayout.CENTER);
        
        // Panel nút
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
        buttonPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 5, 0));
        javax.swing.JButton okButton = new javax.swing.JButton("OK");
        javax.swing.JButton cancelButton = new javax.swing.JButton("Hủy");
        
        // Thiết lập kích thước cho các nút
        okButton.setPreferredSize(new java.awt.Dimension(60, 25));
        cancelButton.setPreferredSize(new java.awt.Dimension(60, 25));
        
        okButton.addActionListener(e -> {
            if (dateChooser.getDate() != null) {
                // Format ngày thành dd/MM/yyyy
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = sdf.format(dateChooser.getDate());
                
                // Kiểm tra validation ngày
                if (validateDateRange(textField, dateChooser.getDate())) {
                    textField.setText(formattedDate);
                }
            }
            dialog.dispose();
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        dialog.add(mainPanel, java.awt.BorderLayout.CENTER);
        dialog.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        
        // Hiển thị dialog với kích thước nhỏ hơn
        dialog.setSize(250, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }
    
    /**
     * Kiểm tra validation ngày bắt đầu và kết thúc
     */
    @Override
    public boolean validateDateRange(javax.swing.JTextField textField, java.util.Date selectedDate) {
        try {
            // Nếu đang chọn ngày bắt đầu
            if (textField == txtBegin) {
                // Kiểm tra ngày kết thúc đã được chọn chưa
                if (!txtEnd.getText().isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date endDate = sdf.parse(txtEnd.getText());
                    
                    if (selectedDate.after(endDate)) {
                        JOptionPane.showMessageDialog(this, 
                            "Ngày bắt đầu không được lớn hơn ngày kết thúc!", 
                            "Lỗi Validation", 
                            JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }
            // Nếu đang chọn ngày kết thúc
            else if (textField == txtEnd) {
                // Kiểm tra ngày bắt đầu đã được chọn chưa
                if (!txtBegin.getText().isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date beginDate = sdf.parse(txtBegin.getText());
                    
                    if (selectedDate.before(beginDate)) {
                        JOptionPane.showMessageDialog(this, 
                            "Ngày kết thúc phải lớn hơn ngày bắt đầu!", 
                            "Lỗi Validation", 
                            JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }
            
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi kiểm tra ngày: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Cập nhật ngày bắt đầu và kết thúc theo combobox thời gian
     */
    @Override
    public void updateDateRangeFromComboBox() {
        try {
            String selectedTime = (String) cboTime.getSelectedItem();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date now = new java.util.Date();
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(now);
            
            java.util.Date beginDate = null;
            java.util.Date endDate = null;
            
            switch (selectedTime) {
                case "Hôm nay":
                    // Ngày bắt đầu: 00:00:00 hôm nay
                    cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                    cal.set(java.util.Calendar.MINUTE, 0);
                    cal.set(java.util.Calendar.SECOND, 0);
                    beginDate = cal.getTime();
                    
                    // Ngày kết thúc: 23:59:59 hôm nay
                    cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
                    cal.set(java.util.Calendar.MINUTE, 59);
                    cal.set(java.util.Calendar.SECOND, 59);
                    endDate = cal.getTime();
                    break;
                    
                case "Tuần này":
                    // Ngày bắt đầu: đầu tuần (Chủ nhật)
                    cal.set(java.util.Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
                    cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                    cal.set(java.util.Calendar.MINUTE, 0);
                    cal.set(java.util.Calendar.SECOND, 0);
                    beginDate = cal.getTime();
                    
                    // Ngày kết thúc: cuối tuần (Thứ 7)
                    cal.add(java.util.Calendar.DATE, 6);
                    cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
                    cal.set(java.util.Calendar.MINUTE, 59);
                    cal.set(java.util.Calendar.SECOND, 59);
                    endDate = cal.getTime();
                    break;
                    
                case "Tháng này":
                    // Ngày bắt đầu: ngày 1 của tháng
                    cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
                    cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                    cal.set(java.util.Calendar.MINUTE, 0);
                    cal.set(java.util.Calendar.SECOND, 0);
                    beginDate = cal.getTime();
                    
                    // Ngày kết thúc: ngày cuối của tháng
                    cal.set(java.util.Calendar.DAY_OF_MONTH, cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
                    cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
                    cal.set(java.util.Calendar.MINUTE, 59);
                    cal.set(java.util.Calendar.SECOND, 59);
                    endDate = cal.getTime();
                    break;
                    
                case "Quý này":
                    // Ngày bắt đầu: tháng đầu của quý
                    int currentMonth = cal.get(java.util.Calendar.MONTH);
                    int quarterStartMonth = (currentMonth / 3) * 3;
                    cal.set(java.util.Calendar.MONTH, quarterStartMonth);
                    cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
                    cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                    cal.set(java.util.Calendar.MINUTE, 0);
                    cal.set(java.util.Calendar.SECOND, 0);
                    beginDate = cal.getTime();
                    
                    // Ngày kết thúc: tháng cuối của quý
                    cal.add(java.util.Calendar.MONTH, 2);
                    cal.set(java.util.Calendar.DAY_OF_MONTH, cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
                    cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
                    cal.set(java.util.Calendar.MINUTE, 59);
                    cal.set(java.util.Calendar.SECOND, 59);
                    endDate = cal.getTime();
                    break;
                    
                case "Năm này":
                    // Ngày bắt đầu: ngày 1 tháng 1
                    cal.set(java.util.Calendar.DAY_OF_YEAR, 1);
                    cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                    cal.set(java.util.Calendar.MINUTE, 0);
                    cal.set(java.util.Calendar.SECOND, 0);
                    beginDate = cal.getTime();
                    
                    // Ngày kết thúc: ngày 31 tháng 12
                    cal.set(java.util.Calendar.DAY_OF_YEAR, cal.getActualMaximum(java.util.Calendar.DAY_OF_YEAR));
                    cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
                    cal.set(java.util.Calendar.MINUTE, 59);
                    cal.set(java.util.Calendar.SECOND, 59);
                    endDate = cal.getTime();
                    break;
                    
                default:
                    // Không làm gì nếu không có lựa chọn hợp lệ
                    return;
            }
            
            // Cập nhật text fields
            if (beginDate != null && endDate != null) {
                txtBegin.setText(sdf.format(beginDate));
                txtEnd.setText(sdf.format(endDate));
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi cập nhật khoảng thời gian: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Lọc hóa đơn theo thời gian
     */
    @Override
    public void filterBills() {
        try {
            String selectedTime = (String) cboTime.getSelectedItem();
            List<Bill> bills = billDAO.findAll();
            
            List<Bill> filteredBills = new java.util.ArrayList<>();
            
            // Kiểm tra nếu có ngày cụ thể được chọn
            boolean hasCustomDateRange = !txtBegin.getText().isEmpty() && !txtEnd.getText().isEmpty();
            
            if (hasCustomDateRange) {
                // Lọc theo ngày cụ thể
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date beginDate = sdf.parse(txtBegin.getText());
                java.util.Date endDate = sdf.parse(txtEnd.getText());
                
                // Thêm 1 ngày vào endDate để bao gồm cả ngày kết thúc
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(endDate);
                cal.add(java.util.Calendar.DATE, 1);
                endDate = cal.getTime();
                
                for (Bill bill : bills) {
                    if (bill.getCheckin() != null) {
                        // Kiểm tra ngày checkin có trong khoảng thời gian không
                        if (bill.getCheckin().after(beginDate) && bill.getCheckin().before(endDate)) {
                            filteredBills.add(bill);
                        }
                    }
                }
            } else {
                // Lọc theo thời gian được chọn từ combobox
                java.util.Date now = new java.util.Date();
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(now);
                
                for (Bill bill : bills) {
                    if (bill.getCheckin() != null) {
                        boolean include = false;
                        
                        switch (selectedTime) {
                            case "Hôm nay":
                                cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                                cal.set(java.util.Calendar.MINUTE, 0);
                                cal.set(java.util.Calendar.SECOND, 0);
                                java.util.Date startOfDay = cal.getTime();
                                include = bill.getCheckin().after(startOfDay);
                                break;
                            case "Tuần này":
                                cal.set(java.util.Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
                                cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                                cal.set(java.util.Calendar.MINUTE, 0);
                                cal.set(java.util.Calendar.SECOND, 0);
                                java.util.Date startOfWeek = cal.getTime();
                                include = bill.getCheckin().after(startOfWeek);
                                break;
                            case "Tháng này":
                                cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
                                cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                                cal.set(java.util.Calendar.MINUTE, 0);
                                cal.set(java.util.Calendar.SECOND, 0);
                                java.util.Date startOfMonth = cal.getTime();
                                include = bill.getCheckin().after(startOfMonth);
                                break;
                            case "Quý này":
                                int currentMonth = cal.get(java.util.Calendar.MONTH);
                                int quarterStartMonth = (currentMonth / 3) * 3;
                                cal.set(java.util.Calendar.MONTH, quarterStartMonth);
                                cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
                                cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                                cal.set(java.util.Calendar.MINUTE, 0);
                                cal.set(java.util.Calendar.SECOND, 0);
                                java.util.Date startOfQuarter = cal.getTime();
                                include = bill.getCheckin().after(startOfQuarter);
                                break;
                            case "Năm này":
                                cal.set(java.util.Calendar.DAY_OF_YEAR, 1);
                                cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                                cal.set(java.util.Calendar.MINUTE, 0);
                                cal.set(java.util.Calendar.SECOND, 0);
                                java.util.Date startOfYear = cal.getTime();
                                include = bill.getCheckin().after(startOfYear);
                                break;
                            default:
                                include = true;
                                break;
                        }
                        
                        if (include) {
                            filteredBills.add(bill);
                        }
                    }
                }
            }
            
            // Hiển thị kết quả lọc
            DefaultTableModel model = (DefaultTableModel) tblBill.getModel();
            model.setRowCount(0);
            
            for (Bill bill : filteredBills) {
                // Chuyển đổi Boolean status sang String để hiển thị
                String statusText = "Đang phục vụ";
                if (bill.getStatus() != null) {
                    if (bill.getStatus()) {
                        statusText = "Đã thanh toán";
                    } else {
                        statusText = "Đang phục vụ";
                    }
                }
                
                Object[] row = {
                    bill.getBill_id(),
                    bill.getUser_id(),
                    bill.getTable_number(),
                    String.format("%,.0f VNĐ", bill.getTotal_amount()),
                    bill.getCheckin() != null ? dateFormat.format(bill.getCheckin()) : "",
                    bill.getCheckout() != null ? dateFormat.format(bill.getCheckout()) : "",
                    statusText
                };
                model.addRow(row);
            }
            
            String message;
            if (hasCustomDateRange) {
                message = "Đã lọc được " + filteredBills.size() + " hóa đơn từ " + txtBegin.getText() + " đến " + txtEnd.getText() + "!";
            } else {
                message = "Đã lọc được " + filteredBills.size() + " hóa đơn cho " + selectedTime + "!";
            }
            JOptionPane.showMessageDialog(this, message);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc hóa đơn: " + e.getMessage());
        }
    }
    
    /**
     * Lấy danh sách tất cả hóa đơn
     * @return Danh sách hóa đơn
     */
    @Override
    public List<Bill> getAllBills() {
        try {
            return billDAO.findAll();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
    
    /**
     * Tìm hóa đơn theo ID
     * @param billId ID hóa đơn
     * @return Hóa đơn tìm được
     */
    @Override
    public Bill findBillById(String billId) {
        try {
            return billDAO.findById(billId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm hóa đơn: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Lọc hóa đơn theo khoảng thời gian
     * @param beginDate Ngày bắt đầu
     * @param endDate Ngày kết thúc
     * @return Danh sách hóa đơn đã lọc
     */
    @Override
    public List<Bill> filterBillsByDateRange(Date beginDate, Date endDate) {
        try {
            List<Bill> allBills = billDAO.findAll();
            List<Bill> filteredBills = new java.util.ArrayList<>();
            
            // Thêm 1 ngày vào endDate để bao gồm cả ngày kết thúc
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(java.util.Calendar.DATE, 1);
            Date adjustedEndDate = cal.getTime();
            
            for (Bill bill : allBills) {
                if (bill.getCheckin() != null) {
                    // Kiểm tra ngày checkin có trong khoảng thời gian không
                    if (bill.getCheckin().after(beginDate) && bill.getCheckin().before(adjustedEndDate)) {
                        filteredBills.add(bill);
                    }
                }
            }
            
            return filteredBills;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc hóa đơn theo ngày: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
    
    /**
     * Lọc hóa đơn theo thời gian định sẵn
     * @param timeRange Loại thời gian (Hôm nay, Tuần này, Tháng này, Quý này, Năm này)
     * @return Danh sách hóa đơn đã lọc
     */
    @Override
    public List<Bill> filterBillsByTimeRange(String timeRange) {
        try {
            List<Bill> allBills = billDAO.findAll();
            List<Bill> filteredBills = new java.util.ArrayList<>();
            
            java.util.Date now = new java.util.Date();
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(now);
            
            for (Bill bill : allBills) {
                if (bill.getCheckin() != null) {
                    boolean include = false;
                    
                    switch (timeRange) {
                        case "Hôm nay":
                            cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                            cal.set(java.util.Calendar.MINUTE, 0);
                            cal.set(java.util.Calendar.SECOND, 0);
                            java.util.Date startOfDay = cal.getTime();
                            include = bill.getCheckin().after(startOfDay);
                            break;
                        case "Tuần này":
                            cal.set(java.util.Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
                            cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                            cal.set(java.util.Calendar.MINUTE, 0);
                            cal.set(java.util.Calendar.SECOND, 0);
                            java.util.Date startOfWeek = cal.getTime();
                            include = bill.getCheckin().after(startOfWeek);
                            break;
                        case "Tháng này":
                            cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
                            cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                            cal.set(java.util.Calendar.MINUTE, 0);
                            cal.set(java.util.Calendar.SECOND, 0);
                            java.util.Date startOfMonth = cal.getTime();
                            include = bill.getCheckin().after(startOfMonth);
                            break;
                        case "Quý này":
                            int currentMonth = cal.get(java.util.Calendar.MONTH);
                            int quarterStartMonth = (currentMonth / 3) * 3;
                            cal.set(java.util.Calendar.MONTH, quarterStartMonth);
                            cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
                            cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                            cal.set(java.util.Calendar.MINUTE, 0);
                            cal.set(java.util.Calendar.SECOND, 0);
                            java.util.Date startOfQuarter = cal.getTime();
                            include = bill.getCheckin().after(startOfQuarter);
                            break;
                        case "Năm này":
                            cal.set(java.util.Calendar.DAY_OF_YEAR, 1);
                            cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                            cal.set(java.util.Calendar.MINUTE, 0);
                            cal.set(java.util.Calendar.SECOND, 0);
                            java.util.Date startOfYear = cal.getTime();
                            include = bill.getCheckin().after(startOfYear);
                            break;
                        default:
                            include = true;
                            break;
                    }
                    
                    if (include) {
                        filteredBills.add(bill);
                    }
                }
            }
            
            return filteredBills;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc hóa đơn theo thời gian: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtBillId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        txtTable = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCheckin = new javax.swing.JTextField();
        txtCheckout = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cboStatus = new javax.swing.JComboBox<>();
        btnUpdate = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtBegin = new javax.swing.JTextField();
        btnBegin = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtEnd = new javax.swing.JTextField();
        btnEnd = new javax.swing.JButton();
        cboTime = new javax.swing.JComboBox<>();
        btnFilter = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBill = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBillDetail = new javax.swing.JTable();
        btnExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 164, 133));

        jPanel2.setBackground(new java.awt.Color(134, 39, 43));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("QUẢN LÍ HÓA ĐƠN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(380, 380, 380)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel3.setBackground(new java.awt.Color(204, 164, 133));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Mã Hóa Đơn:");

        txtBillId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nhân Viên:");

        txtUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Bàn Số:");

        txtAmount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Tổng Tiền:");

        txtCheckin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtCheckout.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Giờ Vào:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Giờ Ra:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Trạng Thái :");

        cboStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboStatus.setForeground(new java.awt.Color(204, 204, 204));
        cboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang Phục Vụ ", "Đã Thanh Toán", "Hủy" }));

        btnUpdate.setBackground(new java.awt.Color(204, 204, 204));
        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(153, 153, 153));
        btnUpdate.setText("Cập Nhật");
        btnUpdate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        btnRemove.setBackground(new java.awt.Color(204, 204, 204));
        btnRemove.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnRemove.setForeground(new java.awt.Color(102, 102, 102));
        btnRemove.setText("Xóa");
        btnRemove.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUser)
                            .addComponent(txtCheckin)
                            .addComponent(txtCheckout)
                            .addComponent(cboStatus, 0, 175, Short.MAX_VALUE)
                            .addComponent(txtAmount)
                            .addComponent(txtTable)
                            .addComponent(txtBillId, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtBillId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCheckin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRemove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 164, 133));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("TỪ :");

        txtBegin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnBegin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBegin.setText("...");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("ĐẾN :");

        txtEnd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnEnd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEnd.setText("...");

        cboTime.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboTime.setForeground(new java.awt.Color(102, 51, 0));
        cboTime.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hôm nay", "Tuần này", "Tháng này", "Quý này", "Năm này" }));

        btnFilter.setBackground(new java.awt.Color(204, 204, 204));
        btnFilter.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnFilter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-sort-24.png"))); // NOI18N
        btnFilter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBegin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBegin)
                .addGap(29, 29, 29)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEnd)
                .addGap(28, 28, 28)
                .addComponent(cboTime, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(btnFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnEnd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtEnd, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBegin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtBegin, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnFilter)
                        .addComponent(cboTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setBackground(new java.awt.Color(204, 164, 133));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.RIGHT);
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        tblBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Nhân viên", "Bàn số", "Tổng tiền", "Giờ vào", "Giờ ra", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblBill);
        if (tblBill.getColumnModel().getColumnCount() > 0) {
            tblBill.getColumnModel().getColumn(0).setResizable(false);
            tblBill.getColumnModel().getColumn(1).setResizable(false);
            tblBill.getColumnModel().getColumn(2).setResizable(false);
            tblBill.getColumnModel().getColumn(3).setResizable(false);
            tblBill.getColumnModel().getColumn(4).setResizable(false);
            tblBill.getColumnModel().getColumn(5).setResizable(false);
            tblBill.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Hóa đơn", jPanel5);

        tblBillDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên món", "Số lượng", "Giá", "Giảm giá", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblBillDetail);
        if (tblBillDetail.getColumnModel().getColumnCount() > 0) {
            tblBillDetail.getColumnModel().getColumn(0).setResizable(false);
            tblBillDetail.getColumnModel().getColumn(1).setMinWidth(40);
            tblBillDetail.getColumnModel().getColumn(1).setMaxWidth(60);
            tblBillDetail.getColumnModel().getColumn(2).setMinWidth(40);
            tblBillDetail.getColumnModel().getColumn(2).setMaxWidth(60);
            tblBillDetail.getColumnModel().getColumn(3).setMinWidth(40);
            tblBillDetail.getColumnModel().getColumn(3).setMaxWidth(60);
            tblBillDetail.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Chi tiết", jPanel6);

        btnExit.setBackground(new java.awt.Color(204, 204, 204));
        btnExit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnExit.setForeground(new java.awt.Color(102, 102, 102));
        btnExit.setText("Thoát");
        btnExit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFilterActionPerformed

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
            java.util.logging.Logger.getLogger(BillManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BillManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BillManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BillManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BillManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBegin;
    private javax.swing.JButton btnEnd;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboStatus;
    private javax.swing.JComboBox<String> cboTime;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblBill;
    private javax.swing.JTable tblBillDetail;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtBegin;
    private javax.swing.JTextField txtBillId;
    private javax.swing.JTextField txtCheckin;
    private javax.swing.JTextField txtCheckout;
    private javax.swing.JTextField txtEnd;
    private javax.swing.JTextField txtTable;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
