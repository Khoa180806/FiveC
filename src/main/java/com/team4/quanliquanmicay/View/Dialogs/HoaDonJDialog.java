/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.team4.quanliquanmicay.View.Dialogs;

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
import com.team4.quanliquanmicay.Impl.BillDAOImpl;
import com.team4.quanliquanmicay.Impl.BillDetailsDAOImpl;
import com.team4.quanliquanmicay.Impl.ProductDAOImpl;
import com.team4.quanliquanmicay.util.XAuth;
import com.team4.quanliquanmicay.Controller.BillController;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author HP
 */
public class HoaDonJDialog extends javax.swing.JFrame implements BillController {

    // DAO objects
    private BillDAO billDAO = new BillDAOImpl();
    private BillDetailsDAO billDetailsDAO = new BillDetailsDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();
    
    // Current bill
    private Bill currentBill;
    private List<BillDetails> currentBillDetails;
    
    // Table model
    private DefaultTableModel tableModel;
    
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * Creates new form HoaDonJDialog
     */
    public HoaDonJDialog() {
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
            boolean[] canEdit = new boolean[] {
                false, false, false, false, false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
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
        // Nút Đặt Món
        btnOrder.addActionListener(e -> openDatMonDialog());
        
        // Nút Xóa Món
        btnUnOrder.addActionListener(e -> deleteSelectedItem());
        
        // Nút Hủy Hóa Đơn
        btnUnBill.addActionListener(e -> cancelBill());
        
        // Nút Exit
        btnExit.addActionListener(e -> dispose());
        
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
     * Mở dialog đặt món
     */
    @Override
    public void openDatMonDialog() {
        if (currentBill == null) {
            XDialog.alert("Vui lòng chọn hóa đơn trước khi đặt món!");
            return;
        }
        
        OrderJDialog datMonDialog = new OrderJDialog(this, currentBill);
        datMonDialog.setVisible(true);
    }
    
    /**
     * Xóa món đã chọn
     */
    @Override
    public void deleteSelectedItem() {
        int selectedRow = tbBill.getSelectedRow();
        if (selectedRow == -1) {
            XDialog.alert("Vui lòng chọn món cần xóa!");
            return;
        }
        
        if (XDialog.confirm("Bạn có chắc muốn xóa món này?")) {
            try {
                String productName = (String) tbBill.getValueAt(selectedRow, 1);
                int amount = Integer.parseInt(tbBill.getValueAt(selectedRow, 3).toString());
                
                // Tìm và xóa bill detail
                for (BillDetails detail : currentBillDetails) {
                    Product product = productDAO.findById(detail.getProduct_id());
                    if (product != null && product.getProductName().equals(productName) 
                        && detail.getAmount() == amount) {
                        billDetailsDAO.deleteById(detail.getBill_details_id());
                        break;
                    }
                }
                
                // Refresh table
                loadBillDetails(Integer.parseInt(currentBill.getBill_id()));
                XDialog.alert("Đã xóa món thành công!");
                
            } catch (Exception e) {
                XDialog.alert("Lỗi khi xóa món: " + e.getMessage());
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
                // Cập nhật trạng thái hóa đơn
                currentBill.setStatus(false); // false = Đã hủy
                currentBill.setCheckout(new Date());
                billDAO.update(currentBill);
                
                // Cập nhật trạng thái bàn
                updateTableStatus(currentBill.getTable_number(), 1); // 1 = Trống
                
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
        
        // TODO: Implement InHoaDonJDialog constructor
        // InHoaDonJDialog detailDialog = new InHoaDonJDialog(this, currentBill);
        // detailDialog.setVisible(true);
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
                loadBillDetails(Integer.parseInt(billId));
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
        txtStatus.setText(bill.getStatus() ? "Đang phục vụ" : "Đã hủy");
        txtBegin.setText(dateFormat.format(bill.getCheckin()));
        
        if (bill.getCheckout() != null) {
            txtEnd.setText(dateFormat.format(bill.getCheckout()));
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
            currentBillDetails = billDetailsDAO.findByBillId(String.valueOf(billId));
            fillTableWithBillDetails(currentBillDetails);
        } catch (Exception e) {
            XDialog.alert("Lỗi khi tải chi tiết hóa đơn: " + e.getMessage());
        }
    }
    
    /**
     * Fill table với chi tiết hóa đơn
     */
    private void fillTableWithBillDetails(List<BillDetails> details) {
        tableModel.setRowCount(0);
        
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
            loadBillDetails(Integer.parseInt(currentBill.getBill_id()));
        }
    }
    
    @Override
    public void edit() {
        // Chỉnh sửa hóa đơn hiện tại
        if (currentBill != null) {
            // TODO: Implement edit functionality
            XDialog.alert("Chức năng chỉnh sửa hóa đơn sẽ được implement sau!");
        }
    }
    
    @Override
    public void create() {
        // Tạo hóa đơn mới
        // TODO: Implement create functionality
        XDialog.alert("Chức năng tạo hóa đơn mới sẽ được implement sau!");
    }
    
    @Override
    public void update() {
        // Cập nhật hóa đơn
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
        // Xóa hóa đơn
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
        // TODO: Implement setEditable functionality
    }
    
    @Override
    public void checkAll() {
        // TODO: Implement checkAll functionality
    }
    
    @Override
    public void uncheckAll() {
        // TODO: Implement uncheckAll functionality
    }
    
    @Override
    public void deleteCheckedItems() {
        // TODO: Implement deleteCheckedItems functionality
    }
    
    @Override
    public void payBill() {
        if (currentBill == null) {
            XDialog.alert("Vui lòng chọn hóa đơn để thanh toán!");
            return;
        }
        
        // TODO: Implement payment functionality
        XDialog.alert("Chức năng thanh toán sẽ được implement sau!");
    }
    
    @Override
    public void loadBillsByStatus(String status) {
        // TODO: Implement loadBillsByStatus functionality
        XDialog.alert("Chức năng load hóa đơn theo trạng thái sẽ được implement sau!");
    }
    
    @Override
    public void loadBillsByTable(Integer tableNumber) {
        // TODO: Implement loadBillsByTable functionality
        XDialog.alert("Chức năng load hóa đơn theo bàn sẽ được implement sau!");
    }
    
    @Override
    public void loadBillsByEmployee(String userId) {
        // TODO: Implement loadBillsByEmployee functionality
        XDialog.alert("Chức năng load hóa đơn theo nhân viên sẽ được implement sau!");
    }
    
    @Override
    public void loadBillsByDateRange(Date fromDate, Date toDate) {
        // TODO: Implement loadBillsByDateRange functionality
        XDialog.alert("Chức năng load hóa đơn theo khoảng thời gian sẽ được implement sau!");
    }
    
    @Override
    public double calculateTotalAmount() {
        if (currentBillDetails == null || currentBillDetails.isEmpty()) {
            return 0.0;
        }
        
        double total = 0.0;
        for (BillDetails detail : currentBillDetails) {
            double itemTotal = detail.getPrice() * detail.getAmount() * (1 - detail.getDiscount());
            total += itemTotal;
        }
        return total;
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
        return String.format("%,.0f VNĐ", amount);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbBill = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtBill_Id = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtName_Employee = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTable_Name = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        txtBegin = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtEnd = new javax.swing.JTextField();
        btnUnOrder = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnExit = new javax.swing.JButton();
        btnOrder = new javax.swing.JButton();
        btnUnBill = new javax.swing.JButton();
        lblImage = new javax.swing.JLabel();

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
        jScrollPane1.setViewportView(tbBill);
        if (tbBill.getColumnModel().getColumnCount() > 0) {
            tbBill.getColumnModel().getColumn(0).setResizable(false);
            tbBill.getColumnModel().getColumn(1).setResizable(false);
            tbBill.getColumnModel().getColumn(2).setResizable(false);
            tbBill.getColumnModel().getColumn(3).setResizable(false);
            tbBill.getColumnModel().getColumn(4).setResizable(false);
            tbBill.getColumnModel().getColumn(5).setResizable(false);
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

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("MÃ HÓA ĐƠN :");

        txtBill_Id.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("NHÂN VIÊN :");

        txtName_Employee.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("BÀN SỐ :");

        txtTable_Name.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("TRẠNG THÁI");

        txtStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtBegin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("THỜI ĐIỂM TẠO :");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("THỜI ĐIỂM THANH TOÁN :");

        txtEnd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnUnOrder.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUnOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/trash.png"))); // NOI18N
        btnUnOrder.setText("XÓA MÓN");

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Exit.png"))); // NOI18N
        btnExit.setText("EXIT");

        btnOrder.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Add to basket.png"))); // NOI18N
        btnOrder.setText("ĐẶT MÓN");

        btnUnBill.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUnBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/delete.png"))); // NOI18N
        btnUnBill.setText("HỦY HÓA ĐƠN");

        lblImage.setText("Image");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addComponent(btnExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUnBill, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                            .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtBill_Id)
                            .addComponent(txtName_Employee, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTable_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(74, 74, 74)
                                .addComponent(txtBegin))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnOrder)
                            .addComponent(btnUnOrder))
                        .addGap(28, 28, 28))))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtBill_Id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtTable_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtBegin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOrder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtName_Employee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txtEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUnOrder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUnBill)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(btnExit)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(HoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HoaDonJDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnOrder;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblImage;
    private javax.swing.JTable tbBill;
    private javax.swing.JTextField txtBegin;
    private javax.swing.JTextField txtBill_Id;
    private javax.swing.JTextField txtEnd;
    private javax.swing.JTextField txtName_Employee;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtTable_Name;
    // End of variables declaration//GEN-END:variables
}
