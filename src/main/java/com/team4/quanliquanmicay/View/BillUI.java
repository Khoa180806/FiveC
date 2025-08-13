/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.team4.quanliquanmicay.View;

import com.team4.quanliquanmicay.util.XTheme;
import com.team4.quanliquanmicay.util.XDialog;
import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.util.XQuery;
import com.team4.quanliquanmicay.Entity.Bill;
import com.team4.quanliquanmicay.Entity.BillDetails;
import com.team4.quanliquanmicay.Entity.Product;
import com.team4.quanliquanmicay.DAO.BillDAO;
import com.team4.quanliquanmicay.DAO.BillDetailsDAO;
import com.team4.quanliquanmicay.DAO.ProductDAO;
import com.team4.quanliquanmicay.DAO.PaymentHistoryDAO;
import com.team4.quanliquanmicay.Impl.BillDAOImpl;
import com.team4.quanliquanmicay.Impl.BillDetailsDAOImpl;
import com.team4.quanliquanmicay.Impl.ProductDAOImpl;
import com.team4.quanliquanmicay.Impl.PaymentHistoryDAOImpl;

import com.team4.quanliquanmicay.util.XAuth;
import com.team4.quanliquanmicay.Controller.BillController;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Date;
import com.team4.quanliquanmicay.util.XDate;
import com.team4.quanliquanmicay.util.XValidation;
import com.team4.quanliquanmicay.util.XStr;

/**
 * Dialog quản lý hóa đơn - chỉ để xem và quản lý bill
 * @author HP
 */
public class BillUI extends javax.swing.JFrame implements BillController {

    // DAO objects
    private final BillDAO billDAO = new BillDAOImpl();
    private final BillDetailsDAO billDetailsDAO = new BillDetailsDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();

    
    // Current bill data
    private Bill currentBill;
    private List<BillDetails> currentBillDetails;
    
    // Table model
    private DefaultTableModel tableModel;
    
    // Table status constants
    private static final int TABLE_EMPTY = 1;
    private static final int TABLE_SERVING = 2;

    /**
     * Creates new form HoaDonJDialog
     */
    public BillUI() {
        this.setUndecorated(true);
        XTheme.applyFullTheme();
        initComponents();
        this.setLocationRelativeTo(null);
        
        setupTable();
        setupEventHandlers();
        clearForm();
    }
    
    /**
     * Thiết lập bảng
     */
    private void setupTable() {
        tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Mã hóa đơn", "Tên Món", "Đơn Giá", "Số Lượng", "Thành Tiền", "Ghi chú"
            }
        ) {
            private static final boolean[] CAN_EDIT = {false, false, false, false, false, false};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return CAN_EDIT[columnIndex];
            }
        };
        tbBill.setModel(tableModel);
        
        // Thiết lập độ rộng cột
        tbBill.getColumnModel().getColumn(0).setPreferredWidth(100);
        tbBill.getColumnModel().getColumn(1).setPreferredWidth(200);
        tbBill.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbBill.getColumnModel().getColumn(3).setPreferredWidth(80);
        tbBill.getColumnModel().getColumn(4).setPreferredWidth(120);
        tbBill.getColumnModel().getColumn(5).setPreferredWidth(150);
    }
    
    /**
     * Thiết lập event handlers
     */
    private void setupEventHandlers() {
        btnOrder.addActionListener(e -> openDatMonDialog());
        btnUnOrder.addActionListener(e -> deleteSelectedItem());
        btnUnBill.addActionListener(e -> cancelBill());

        btnPayment.addActionListener(e -> openPaymentDialog());
        
        // Double click để xem chi tiết
        tbBill.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    viewBillDetails();
                }
            }
        });
    }
    
    /**
     * Mở dialog thanh toán
     */
    private void openPaymentDialog() {
        if (currentBill == null) {
            XDialog.alert("Vui lòng chọn hóa đơn trước khi thanh toán!");
            return;
        }
        
        PayUI paymentDialog = new PayUI();
        paymentDialog.setVisible(true);
        
        // Truyền thông tin bill hiện tại cho dialog thanh toán
        paymentDialog.loadBillDirectly(currentBill);
        
        // Đóng dialog hiện tại
        this.dispose();
    }
    
    /**
     * Mở dialog đặt món
     */
    @Override
    public void openDatMonDialog() {
        if (currentBill == null) {
            XDialog.alert("Vui lòng chọn hóa đơn trước khi đặt món!");
            return;
        }
        
        OrderUI datMonDialog = new OrderUI(this, currentBill);
        
        // Thêm window listener để refresh khi dialog đóng
        datMonDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                refreshBillDetails();
            }
        });
        
        datMonDialog.setVisible(true);
    }
    
    /**
     * Xóa món đã chọn
     */
    @Override
    public void deleteSelectedItem() {
        int selectedRow = tbBill.getSelectedRow();
        if (selectedRow == -1) {
            XDialog.warning("Vui lòng chọn món cần xóa!", "Cảnh báo");
            return;
        }
        if (XDialog.confirm("Bạn có chắc muốn xóa món này?", "Xác nhận")) {
            try {
                Object productNameObj = tbBill.getValueAt(selectedRow, 1);
                Object amountObj = tbBill.getValueAt(selectedRow, 3);
                if (productNameObj == null || amountObj == null) {
                    XDialog.error("Dữ liệu không hợp lệ!", "Lỗi");
                    return;
                }
                String productName = XStr.valueOf(productNameObj);
                if (XValidation.isEmpty(productName)) {
                    XDialog.error("Tên món không hợp lệ!", "Lỗi");
                    return;
                }
                int amount;
                try {
                    amount = Integer.parseInt(XStr.valueOf(amountObj));
                    if (amount <= 0) {
                        XDialog.warning("Số lượng phải lớn hơn 0!", "Cảnh báo");
                        return;
                    }
                } catch (NumberFormatException e) {
                    XDialog.error("Số lượng không hợp lệ!", "Lỗi");
                    return;
                }
                
                // Tìm và xóa bill detail
                boolean found = false;
                for (BillDetails detail : currentBillDetails) {
                    Product product = productDAO.findById(detail.getProduct_id());
                    if (product != null && product.getProductName().equals(productName) 
                        && detail.getAmount() == amount) {
                        billDetailsDAO.deleteById(String.valueOf(detail.getBill_detail_id()));
                        found = true;
                        break;
                    }
                }
                
                if (found) {
                    loadBillDetails(currentBill.getBill_id());
                    XDialog.alert("Đã xóa món thành công!");
                } else {
                    XDialog.alert("Không tìm thấy món để xóa!");
                }
                
            } catch (Exception e) {
                XDialog.error("Lỗi khi xóa món: " + e.getMessage(), "Lỗi");
            }
        }
    }
    
    /**
     * Hủy hóa đơn
     */
    @Override
    public void cancelBill() {
        if (currentBill == null) {
            XDialog.alert("Vui lòng chọn hóa đơn cần hủy!");
            return;
        }
        
        if (XDialog.confirm("Bạn có chắc muốn hủy hóa đơn này?")) {
            try {
                currentBill.setStatus("Hủy");
                currentBill.setCheckout(new Date());
                billDAO.update(currentBill);
                
                updateTableStatus(currentBill.getTable_number(), TABLE_EMPTY);
                
                XDialog.alert("Đã hủy hóa đơn thành công!");
                clearForm();
                
            } catch (Exception e) {
                XDialog.alert("Lỗi khi hủy hóa đơn: " + e.getMessage());
            }
        }
    }
    
    /**
     * Xem chi tiết hóa đơn
     */
    @Override
    public void viewBillDetails() {
        if (currentBill == null) {
            XDialog.alert("Vui lòng chọn hóa đơn để xem chi tiết!");
            return;
        }
        
        XDialog.alert("Chức năng xem chi tiết sẽ được implement sau!");
    }
    
    /**
     * Load thông tin hóa đơn
     */
    @Override
    public void loadBill(String billId) {
        try {
            currentBill = billDAO.findById(billId);
            if (currentBill != null) {
                setBillInfo(currentBill);
                loadBillDetails(currentBill.getBill_id());
            }
        } catch (Exception e) {
            XDialog.alert("Lỗi khi tải hóa đơn: " + e.getMessage());
        }
    }
    
    /**
     * Set thông tin hóa đơn vào form
     */
    private void setBillInfo(Bill bill) {
        txtBill_Id.setText(String.valueOf(bill.getBill_id()));
        txtName_Employee.setText(getEmployeeName(bill.getUser_id()));
        txtTable_Name.setText("Bàn " + bill.getTable_number());
        txtStatus.setText(bill.getStatusText());
        
        // Hiển thị thời gian tạo
        if (bill.getCheckin() != null) {
            txtBegin.setText(XDate.format(bill.getCheckin(), "HH:mm:ss dd/MM/yyyy"));
        } else {
            txtBegin.setText("Chưa có thông tin");
        }
        
        // Hiển thị thời gian thanh toán
        if (bill.getCheckout() != null) {
            txtEnd.setText(XDate.format(bill.getCheckout(), "HH:mm:ss dd/MM/yyyy"));
        } else {
            txtEnd.setText("Chưa thanh toán");
        }
    }
    
    /**
     * Load chi tiết hóa đơn
     */
    @Override
    public void loadBillDetails(Integer billId) {
        try {
            currentBillDetails = billDetailsDAO.findByBillId(billId);
            fillTableWithBillDetails(currentBillDetails);
        } catch (Exception e) {
            XDialog.alert("Lỗi khi tải chi tiết hóa đơn: " + e.getMessage());
        }
    }
    
    /**
     * Refresh bill details - reload lại chi tiết hóa đơn hiện tại
     */
    public void refreshBillDetails() {
        if (currentBill != null) {
            loadBillDetails(currentBill.getBill_id());
        }
    }
    
    /**
     * Fill table với chi tiết hóa đơn
     */
    private void fillTableWithBillDetails(List<BillDetails> details) {
        tableModel.setRowCount(0);
        
        if (details == null) return;
        
        for (BillDetails detail : details) {
            Product product = productDAO.findById(detail.getProduct_id());
            if (product != null) {
                double totalPrice = detail.getPrice() * detail.getAmount() * (1 - detail.getDiscount());
                
                Object[] row = {
                    detail.getBill_id(),
                    product.getProductName(),
                    formatCurrency(detail.getPrice()),
                    detail.getAmount(),
                    formatCurrency(totalPrice),
                    product.getNote() != null ? product.getNote() : ""
                };
                tableModel.addRow(row);
            }
        }
    }
    
    /**
     * Làm mới form
     */
    private void clearForm() {
        txtBill_Id.setText("");
        txtName_Employee.setText("");
        txtTable_Name.setText("");
        txtStatus.setText("");
        txtBegin.setText("");
        txtEnd.setText("");
        tableModel.setRowCount(0);
        currentBill = null;
        currentBillDetails = null;
    }
    
    // ========== IMPLEMENT BILLCONTROLLER METHODS ==========
    
    @Override
    public void open() {
        this.setVisible(true);
    }
    
    @Override
    public void setForm(Bill entity) {
        if (entity != null) {
            setBillInfo(entity);
        }
    }
    
    @Override
    public Bill getForm() {
        return currentBill;
    }
    
    @Override
    public void fillToTable() {
        if (currentBill != null) {
            loadBillDetails(currentBill.getBill_id());
        }
    }
    
    @Override
    public void edit() {
        if (currentBill == null) {
            XDialog.alert("Vui lòng chọn hóa đơn để chỉnh sửa!");
            return;
        }
        
        try {
            setEditable(true);
            XDialog.alert("Chế độ chỉnh sửa đã được bật.");
        } catch (Exception e) {
            XDialog.alert("Lỗi khi chỉnh sửa hóa đơn: " + e.getMessage());
        }
    }
    
    @Override
    public void create() {
        XDialog.alert("Chức năng tạo hóa đơn mới sẽ được implement sau!");
    }
    
    @Override
    public void update() {
        if (currentBill != null) {
            try {
                billDAO.update(currentBill);
                XDialog.alert("Cập nhật hóa đơn thành công!");
            } catch (Exception e) {
                XDialog.alert("Lỗi khi cập nhật hóa đơn: " + e.getMessage());
            }
        }
    }
    
    @Override
    public void delete() {
        if (currentBill != null) {
            cancelBill();
        }
    }
    
    @Override
    public void clear() {
        clearForm();
    }
    
    @Override
    public void setEditable(boolean editable) {
        txtBill_Id.setEditable(editable);
        txtName_Employee.setEditable(editable);
        txtTable_Name.setEditable(editable);
        txtStatus.setEditable(editable);
        txtBegin.setEditable(editable);
        txtEnd.setEditable(editable);
        
        if (editable) {
            XDialog.alert("Chế độ chỉnh sửa đã được bật.");
        } else {
            XDialog.alert("Chế độ chỉnh sửa đã được tắt.");
        }
    }
    
    @Override
    public void checkAll() {
        for (int i = 0; i < tbBill.getRowCount(); i++) {
            tbBill.setRowSelectionInterval(i, i);
        }
        XDialog.alert("Đã chọn tất cả các món trong hóa đơn!");
    }
    
    @Override
    public void uncheckAll() {
        tbBill.clearSelection();
        XDialog.alert("Đã bỏ chọn tất cả các món!");
    }
    
    @Override
    public void deleteCheckedItems() {
        int[] selectedRows = tbBill.getSelectedRows();
        if (selectedRows.length == 0) {
            XDialog.alert("Vui lòng chọn món cần xóa!");
            return;
        }
        
        if (XDialog.confirm("Bạn có chắc muốn xóa " + selectedRows.length + " món đã chọn?")) {
            try {
                int deletedCount = 0;
                
                // Xóa từ dưới lên để tránh lỗi index
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int rowIndex = selectedRows[i];
                    if (rowIndex >= 0 && rowIndex < currentBillDetails.size()) {
                        BillDetails detail = currentBillDetails.get(rowIndex);
                        if (detail != null && detail.getBill_detail_id() != null) {
                            billDetailsDAO.deleteById(String.valueOf(detail.getBill_detail_id()));
                            deletedCount++;
                        }
                    }
                }
                
                if (deletedCount > 0) {
                    loadBillDetails(currentBill.getBill_id());
                    XDialog.alert("Đã xóa thành công " + deletedCount + " món!");
                } else {
                    XDialog.alert("Không có món nào được xóa!");
                }
                
            } catch (Exception e) {
                XDialog.alert("Lỗi khi xóa món: " + e.getMessage());
            }
        }
    }
    
    @Override
    public void payBill() {
        if (currentBill == null) {
            XDialog.alert("Vui lòng chọn hóa đơn để xem thông tin thanh toán!");
            return;
        }
        
        if (currentBillDetails == null || currentBillDetails.isEmpty()) {
            XDialog.alert("Hóa đơn chưa có món nào!");
            return;
        }
        
        try {
            double totalAmount = calculateTotalAmount();
            
            StringBuilder sb = new StringBuilder();
            sb.append("=== THÔNG TIN THANH TOÁN ===\n\n");
            sb.append("Mã hóa đơn: ").append(currentBill.getBill_id()).append("\n");
            sb.append("Bàn số: ").append(currentBill.getTable_number()).append("\n");
            sb.append("Nhân viên: ").append(getEmployeeName(currentBill.getUser_id())).append("\n");
            sb.append("Số món: ").append(currentBillDetails.size()).append("\n");
            sb.append("Tổng tiền: ").append(formatCurrency(totalAmount)).append("\n\n");
            sb.append("Vui lòng chuyển đến trang thanh toán để hoàn tất!");
            
            XDialog.alert(sb.toString());
            
        } catch (Exception e) {
            XDialog.alert("Lỗi khi xem thông tin thanh toán: " + e.getMessage());
        }
    }
    
    @Override
    public void loadBillsByStatus(String status) {
        try {
            List<Bill> bills;
            if ("Đang phục vụ".equals(status)) {
                bills = billDAO.findAll().stream()
                    .filter(bill -> "Đang phục vụ".equals(bill.getStatus()))
                    .collect(java.util.stream.Collectors.toList());
            } else if ("Đã thanh toán".equals(status)) {
                bills = billDAO.findAll().stream()
                    .filter(bill -> "Đã thanh toán".equals(bill.getStatus()))
                    .collect(java.util.stream.Collectors.toList());
            } else {
                bills = billDAO.findAll();
            }
            
            if (bills.isEmpty()) {
                XDialog.alert("Không có hóa đơn nào với trạng thái: " + status);
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("Danh sách hóa đơn ").append(status).append(":\n\n");
            
            for (Bill bill : bills) {
                sb.append("Mã HD: ").append(bill.getBill_id())
                  .append(" | Bàn: ").append(bill.getTable_number())
                  .append(" | Tổng: ").append(formatCurrency(bill.getTotal_amount()))
                  .append("\n");
            }
            
            XDialog.alert(sb.toString());
            
        } catch (Exception e) {
            XDialog.alert("Lỗi khi load hóa đơn theo trạng thái: " + e.getMessage());
        }
    }
    
    @Override
    public void loadBillsByTable(Integer tableNumber) {
        try {
            List<Bill> bills = billDAO.findAll().stream()
                .filter(bill -> bill.getTable_number() == tableNumber)
                .collect(java.util.stream.Collectors.toList());
            
            if (bills.isEmpty()) {
                XDialog.alert("Không có hóa đơn nào cho bàn " + tableNumber);
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("Danh sách hóa đơn bàn ").append(tableNumber).append(":\n\n");
            
            for (Bill bill : bills) {
                String status = bill.getStatusText();
                sb.append("Mã HD: ").append(bill.getBill_id())
                  .append(" | Trạng thái: ").append(status)
                  .append(" | Tổng: ").append(formatCurrency(bill.getTotal_amount()))
                  .append("\n");
            }
            
            XDialog.alert(sb.toString());
            
        } catch (Exception e) {
            XDialog.alert("Lỗi khi load hóa đơn theo bàn: " + e.getMessage());
        }
    }
    
    @Override
    public void loadBillsByEmployee(String userId) {
        try {
            List<Bill> bills = billDAO.findAll().stream()
                .filter(bill -> bill.getUser_id().equals(userId))
                .collect(java.util.stream.Collectors.toList());
            
            if (bills.isEmpty()) {
                XDialog.alert("Không có hóa đơn nào của nhân viên này!");
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("Danh sách hóa đơn của nhân viên:\n\n");
            
            for (Bill bill : bills) {
                String status = bill.getStatusText();
                sb.append("Mã HD: ").append(bill.getBill_id())
                  .append(" | Bàn: ").append(bill.getTable_number())
                  .append(" | Trạng thái: ").append(status)
                  .append(" | Tổng: ").append(formatCurrency(bill.getTotal_amount()))
                  .append("\n");
            }
            
            XDialog.alert(sb.toString());
            
        } catch (Exception e) {
            XDialog.alert("Lỗi khi load hóa đơn theo nhân viên: " + e.getMessage());
        }
    }
    
    @Override
    public void loadBillsByDateRange(Date fromDate, Date toDate) {
        try {
            List<Bill> bills = billDAO.findAll().stream()
                .filter(bill -> {
                    if (bill.getCheckin() == null) return false;
                    return bill.getCheckin().after(fromDate) && bill.getCheckin().before(toDate);
                })
                .collect(java.util.stream.Collectors.toList());
            
            if (bills.isEmpty()) {
                XDialog.alert("Không có hóa đơn nào trong khoảng thời gian này!");
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("Danh sách hóa đơn từ ").append(XDate.format(fromDate, "HH:mm:ss dd/MM/yyyy"))
              .append(" đến ").append(XDate.format(toDate, "HH:mm:ss dd/MM/yyyy")).append(":\n\n");
            
            for (Bill bill : bills) {
                String status = bill.getStatusText();
                sb.append("Mã HD: ").append(bill.getBill_id())
                  .append(" | Bàn: ").append(bill.getTable_number())
                  .append(" | Trạng thái: ").append(status)
                  .append(" | Tổng: ").append(formatCurrency(bill.getTotal_amount()))
                  .append("\n");
            }
            
            XDialog.alert(sb.toString());
            
        } catch (Exception e) {
            XDialog.alert("Lỗi khi load hóa đơn theo khoảng thời gian: " + e.getMessage());
        }
    }
    
    @Override
    public double calculateTotalAmount() {
        if (currentBillDetails == null || currentBillDetails.isEmpty()) {
            return 0.0;
        }
        
        return currentBillDetails.stream()
            .mapToDouble(detail -> detail.getPrice() * detail.getAmount() * (1 - detail.getDiscount()))
            .sum();
    }
    
    @Override
    public void updateTableStatus(Integer tableNumber, Integer status) {
        try {
            String sql = "UPDATE TABLE_FOR_CUSTOMER SET status = ? WHERE table_number = ?";
            XJdbc.executeUpdate(sql, status, tableNumber);
        } catch (Exception e) {
            System.err.println("Lỗi cập nhật trạng thái bàn: " + e.getMessage());
        }
    }
    
    @Override
    public String getEmployeeName(String userId) {
        try {
            String sql = "SELECT fullName FROM USER_ACCOUNT WHERE user_id = ?";
            return XJdbc.getValue(sql, String.class, userId);
        } catch (Exception e) {
            return "Unknown";
        }
    }
    
    @Override
    public String formatCurrency(double amount) {
        if (amount < 0) return "0 VNĐ";
        return String.format("%,.0f VNĐ", amount);
    }

    /**
     * Set thông tin bàn vào form
     */
    public void setTableInfo(int tableNumber) {
        try {
            txtTable_Name.setText("Bàn " + tableNumber);
            Bill currentBillForTable = findCurrentBillByTable(tableNumber);
            if (currentBillForTable != null) {
                loadBill(String.valueOf(currentBillForTable.getBill_id()));
            } else {
                createNewBillForTable(tableNumber, XAuth.user.getUser_id());
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi set thông tin bàn: " + e.getMessage());
        }
    }
    
    /**
     * Tìm hóa đơn hiện tại của bàn
     */
    private Bill findCurrentBillByTable(int tableNumber) {
        try {
            List<Bill> allBills = billDAO.findAll();
            return allBills.stream()
                .filter(bill -> bill.getTable_number() == tableNumber && "Đang phục vụ".equals(bill.getStatus()))
                .findFirst()
                .orElse(null);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm hóa đơn của bàn: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Tạo hóa đơn mới cho bàn
     */
    public void createNewBillForTable(int tableNumber, String userId) {
        try {
            // Tạo PaymentHistory tạm thời để có payment_history_id hợp lệ
            String createPaymentSql = "INSERT INTO PAYMENT_HISTORY(payment_method_id, total_amount, status, note) VALUES(?, ?, ?, ?)";
            Object[] paymentValues = {
                1, // Tiền mặt (default)
                0.0,
                "Chưa thanh toán",
                "Hóa đơn bàn " + tableNumber + " - Chưa thanh toán"
            };
            XJdbc.executeUpdate(createPaymentSql, paymentValues);
            
            // Lấy payment_history_id vừa tạo (ID cao nhất)
            String getLastIdSql = "SELECT MAX(payment_history_id) FROM PAYMENT_HISTORY";
            Integer lastPaymentId = XJdbc.getValue(getLastIdSql, Integer.class);
            
            // Tạo Bill mới với payment_history_id vừa tạo
            Bill newBill = new Bill();
            newBill.setUser_id(userId);
            newBill.setTable_number(tableNumber);
            newBill.setStatus("Đang phục vụ");
            newBill.setCheckin(new Date());
            newBill.setTotal_amount(0.0);
            newBill.setPhone_number(null);
            newBill.setPayment_history_id(lastPaymentId);
            
            billDAO.create(newBill);
            loadBillFromDatabase(tableNumber, userId);
            updateTableStatus(tableNumber, TABLE_SERVING);
            
        } catch (Exception e) {
            XDialog.alert("Lỗi khi tạo hóa đơn mới: " + e.getMessage());
        }
    }
    

    
    /**
     * Load hóa đơn từ database sau khi tạo
     */
    private void loadBillFromDatabase(int tableNumber, String userId) {
        try {
            String sql = "SELECT * FROM BILL WHERE table_number = ? AND user_id = ? ORDER BY bill_id DESC";
            Bill latestBill = XQuery.getSingleBean(Bill.class, sql, tableNumber, userId);
            if (latestBill != null) {
                loadBill(String.valueOf(latestBill.getBill_id()));
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi load hóa đơn từ database: " + e.getMessage());
        }
    }

    // Generated code - không sửa đổi
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbBill = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnUnOrder = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnExit = new javax.swing.JButton();
        btnOrder = new javax.swing.JButton();
        btnUnBill = new javax.swing.JButton();
        btnPayment = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtName_Employee = new javax.swing.JTextField();
        txtBill_Id = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        txtTable_Name = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtEnd = new javax.swing.JTextField();
        txtBegin = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 51, 51));

        jPanel3.setBackground(new java.awt.Color(204, 164, 133));

        tbBill.setForeground(new java.awt.Color(153, 153, 153));
        tbBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Tên Món", "Đơn Giá", "Số Lượng", "Thành Tiền", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbBill.getTableHeader().setResizingAllowed(false);
        tbBill.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbBill);
        if (tbBill.getColumnModel().getColumnCount() > 0) {
            tbBill.getColumnModel().getColumn(0).setResizable(false);
            tbBill.getColumnModel().getColumn(1).setResizable(false);
            tbBill.getColumnModel().getColumn(2).setMinWidth(80);
            tbBill.getColumnModel().getColumn(2).setMaxWidth(100);
            tbBill.getColumnModel().getColumn(3).setMinWidth(80);
            tbBill.getColumnModel().getColumn(3).setMaxWidth(100);
            tbBill.getColumnModel().getColumn(4).setMinWidth(120);
            tbBill.getColumnModel().getColumn(4).setMaxWidth(140);
            tbBill.getColumnModel().getColumn(5).setMinWidth(300);
            tbBill.getColumnModel().getColumn(5).setMaxWidth(500);
        }

        jPanel1.setBackground(new java.awt.Color(134, 39, 43));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Hóa Đơn");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(439, 439, 439)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnUnOrder.setBackground(new java.awt.Color(204, 204, 204));
        btnUnOrder.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUnOrder.setForeground(new java.awt.Color(102, 102, 102));
        btnUnOrder.setText("Xóa món");
        btnUnOrder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        btnUnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnOrderActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        btnExit.setBackground(new java.awt.Color(204, 204, 204));
        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnExit.setForeground(new java.awt.Color(153, 0, 0));
        btnExit.setText("Thoát");
        btnExit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnOrder.setBackground(new java.awt.Color(204, 204, 204));
        btnOrder.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnOrder.setForeground(new java.awt.Color(0, 102, 51));
        btnOrder.setText("Thêm món");
        btnOrder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        btnUnBill.setBackground(new java.awt.Color(204, 204, 204));
        btnUnBill.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUnBill.setForeground(new java.awt.Color(102, 102, 102));
        btnUnBill.setText("Hủy Hóa Đơn");
        btnUnBill.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        btnPayment.setBackground(new java.awt.Color(204, 204, 204));
        btnPayment.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPayment.setForeground(new java.awt.Color(0, 51, 51));
        btnPayment.setText("Thanh Toán");
        btnPayment.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        jPanel2.setBackground(new java.awt.Color(204, 164, 133));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("MÃ HÓA ĐƠN :");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("NHÂN VIÊN :");

        txtName_Employee.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtBill_Id.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("BÀN SỐ :");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("TRẠNG THÁI");

        txtStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtTable_Name.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("THỜI ĐIỂM TẠO :");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("THỜI ĐIỂM THANH TOÁN :");

        txtEnd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtBegin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtBill_Id)
                    .addComponent(txtName_Employee, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTable_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(74, 74, 74)
                        .addComponent(txtBegin))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtBill_Id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtTable_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtBegin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtName_Employee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txtEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnPayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUnOrder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUnBill, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnExit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUnBill, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExit))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnOrderActionPerformed
        // Gọi method deleteSelectedItem() thay vì để trống
        deleteSelectedItem();
    }//GEN-LAST:event_btnUnOrderActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
      if (XDialog.confirm("Bạn có chắc chắn muốn thoát khỏi ứng dụng không?", "Xác nhận thoát")) {
          this.dispose(); // Đóng cửa sổ hiện tại

        }
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
            java.util.logging.Logger.getLogger(BillUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BillUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BillUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BillUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BillUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnOrder;
    private javax.swing.JButton btnPayment;
    private javax.swing.JButton btnUnBill;
    private javax.swing.JButton btnUnOrder;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tbBill;
    private javax.swing.JTextField txtBegin;
    private javax.swing.JTextField txtBill_Id;
    private javax.swing.JTextField txtEnd;
    private javax.swing.JTextField txtName_Employee;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtTable_Name;
    // End of variables declaration//GEN-END:variables
}
