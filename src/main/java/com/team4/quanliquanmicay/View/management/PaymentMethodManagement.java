/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.team4.quanliquanmicay.View.management;

import com.team4.quanliquanmicay.util.XTheme;
import com.team4.quanliquanmicay.util.XDialog;
import com.team4.quanliquanmicay.Entity.PaymentMethod;
import com.team4.quanliquanmicay.DAO.PaymentMethodDAO;
import com.team4.quanliquanmicay.Impl.PaymentMethodDAOImpl;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Asus
 */
public class PaymentMethodManagement extends javax.swing.JFrame {

    private PaymentMethodDAO paymentMethodDAO;
    private DefaultTableModel tableModel;
    private boolean hasUnsavedChanges = false; // Theo dõi thay đổi chưa lưu

    /**
     * Creates new form PaymentMethodManagement
     */
    public PaymentMethodManagement() {
        this.setUndecorated(true);
        XTheme.applyFullTheme();
        initComponents();
        this.setLocationRelativeTo(null);
        
        // Khởi tạo DAO
        paymentMethodDAO = new PaymentMethodDAOImpl();
        
        // Khởi tạo table model
        initTableModel();
        
        // Load dữ liệu từ database
        loadPaymentMethods();
        
        // Thêm listeners cho việc theo dõi thay đổi
        addChangeListeners();
    }

    /**
     * Khởi tạo table model
     */
    private void initTableModel() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép edit trực tiếp trên table
            }
        };
        
        // Thêm các cột
        tableModel.addColumn("Mã phương thức");
        tableModel.addColumn("Tên phương thức");
        tableModel.addColumn("Trạng thái");
        
        // Gán model cho table
        tblPaymentMethod.setModel(tableModel);
        
        // Thêm listener cho việc click vào table
        tblPaymentMethod.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPaymentMethodMouseClicked(evt);
            }
        });
    }

    /**
     * Thêm listeners để theo dõi thay đổi trong form
     */
    private void addChangeListeners() {
        // Theo dõi thay đổi trong text field ID
        txtPayMethod_Id.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                hasUnsavedChanges = true;
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                hasUnsavedChanges = true;
            }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                hasUnsavedChanges = true;
            }
        });
        
        // Theo dõi thay đổi trong text field Name
        txtPayMethod_Name.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                hasUnsavedChanges = true;
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                hasUnsavedChanges = true;
            }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                hasUnsavedChanges = true;
            }
        });
        
        // Theo dõi thay đổi trong radio buttons
        rdoActive.addActionListener(e -> hasUnsavedChanges = true);
        rdoStop.addActionListener(e -> hasUnsavedChanges = true);
    }

    /**
     * Xử lý sự kiện click vào table
     */
    private void tblPaymentMethodMouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = tblPaymentMethod.getSelectedRow();
        if (selectedRow != -1) {
            // Lấy dữ liệu từ dòng được chọn
            Object paymentMethodId = tableModel.getValueAt(selectedRow, 0);
            Object methodName = tableModel.getValueAt(selectedRow, 1);
            Object status = tableModel.getValueAt(selectedRow, 2);
            
            // Fill dữ liệu vào form
            txtPayMethod_Id.setText(paymentMethodId.toString());
            txtPayMethod_Name.setText(methodName.toString());
            
            // Set trạng thái radio button
            if ("Hoạt động".equals(status.toString())) {
                rdoActive.setSelected(true);
            } else {
                rdoStop.setSelected(true);
            }
            
            // Reset trạng thái thay đổi
            hasUnsavedChanges = false;
        }
    }

    /**
     * Load dữ liệu phương thức thanh toán từ database
     */
    private void loadPaymentMethods() {
        try {
            // Xóa dữ liệu cũ
            tableModel.setRowCount(0);
            
            // Lấy danh sách từ database
            List<PaymentMethod> paymentMethods = paymentMethodDAO.findAll();
            
            // Thêm dữ liệu vào table
            for (PaymentMethod pm : paymentMethods) {
                String status = pm.getIs_enable() == 1 ? "Hoạt động" : "Tạm dừng";
                tableModel.addRow(new Object[]{
                    pm.getPayment_method_id(),
                    pm.getMethod_name(),
                    status
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            XDialog.error("Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    /**
     * Kiểm tra tên phương thức thanh toán đã tồn tại chưa
     */
    private boolean isMethodNameExists(String methodName, int excludeId) {
        try {
            List<PaymentMethod> allMethods = paymentMethodDAO.findAll();
            for (PaymentMethod pm : allMethods) {
                if (pm.getMethod_name().equalsIgnoreCase(methodName) && pm.getPayment_method_id() != excludeId) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Kiểm tra mã phương thức thanh toán đã tồn tại chưa
     */
    private boolean isMethodIdExists(int methodId, int excludeId) {
        try {
            List<PaymentMethod> allMethods = paymentMethodDAO.findAll();
            for (PaymentMethod pm : allMethods) {
                if (pm.getPayment_method_id() == methodId && pm.getPayment_method_id() != excludeId) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lấy thông tin phương thức thanh toán hiện tại từ form
     */
    private PaymentMethod getCurrentFormData() {
        String methodName = txtPayMethod_Name.getText().trim();
        int isEnable = rdoActive.isSelected() ? 1 : 0;
        
        if (!txtPayMethod_Id.getText().trim().isEmpty()) {
            int paymentMethodId = Integer.parseInt(txtPayMethod_Id.getText().trim());
            return PaymentMethod.builder()
                    .payment_method_id(paymentMethodId)
                    .method_name(methodName)
                    .is_enable(isEnable)
                    .build();
        } else {
            return PaymentMethod.builder()
                    .method_name(methodName)
                    .is_enable(isEnable)
                    .build();
        }
    }

    /**
     * Tạo chuỗi thông tin để hiển thị
     */
    private String formatPaymentMethodInfo(PaymentMethod pm) {
        return String.format("Mã: %d\nTên: %s\nTrạng thái: %s", 
            pm.getPayment_method_id(), 
            pm.getMethod_name(), 
            pm.getIs_enable() == 1 ? "Hoạt động" : "Tạm dừng");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrpStatus = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        pnlTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jspPaymentMethod = new javax.swing.JScrollPane();
        tblPaymentMethod = new javax.swing.JTable();
        pnlFeature = new javax.swing.JPanel();
        lblPayMethod_Id = new javax.swing.JLabel();
        txtPayMethod_Id = new javax.swing.JTextField();
        lblPayMethod_Name = new javax.swing.JLabel();
        txtPayMethod_Name = new javax.swing.JTextField();
        lblPaymentStatus = new javax.swing.JLabel();
        rdoActive = new javax.swing.JRadioButton();
        rdoStop = new javax.swing.JRadioButton();
        btnCreate = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 164, 133));

        pnlTitle.setBackground(new java.awt.Color(134, 39, 43));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("PHƯƠNG THỨC THANH TOÁN");

        javax.swing.GroupLayout pnlTitleLayout = new javax.swing.GroupLayout(pnlTitle);
        pnlTitle.setLayout(pnlTitleLayout);
        pnlTitleLayout.setHorizontalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTitleLayout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(lblTitle)
                .addContainerGap(129, Short.MAX_VALUE))
        );
        pnlTitleLayout.setVerticalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        tblPaymentMethod.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phương thức", "Tên phương thức", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jspPaymentMethod.setViewportView(tblPaymentMethod);
        if (tblPaymentMethod.getColumnModel().getColumnCount() > 0) {
            tblPaymentMethod.getColumnModel().getColumn(0).setResizable(false);
            tblPaymentMethod.getColumnModel().getColumn(1).setResizable(false);
            tblPaymentMethod.getColumnModel().getColumn(2).setResizable(false);
        }

        pnlFeature.setBackground(new java.awt.Color(204, 164, 133));
        pnlFeature.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        lblPayMethod_Id.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPayMethod_Id.setForeground(new java.awt.Color(255, 255, 255));
        lblPayMethod_Id.setText("MÃ PHƯƠNG THỨC :");

        txtPayMethod_Id.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblPayMethod_Name.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPayMethod_Name.setForeground(new java.awt.Color(255, 255, 255));
        lblPayMethod_Name.setText("TÊN PHƯƠNG THỨC :");

        txtPayMethod_Name.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblPaymentStatus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPaymentStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblPaymentStatus.setText("TRẠNG THÁI :");

        btnGrpStatus.add(rdoActive);
        rdoActive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoActive.setForeground(new java.awt.Color(255, 255, 255));
        rdoActive.setText("HOẠT ĐỘNG");

        btnGrpStatus.add(rdoStop);
        rdoStop.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoStop.setForeground(new java.awt.Color(255, 255, 255));
        rdoStop.setText("TẠM DỪNG");

        btnCreate.setBackground(new java.awt.Color(183, 239, 197));
        btnCreate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Create.png"))); // NOI18N
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(255, 231, 153));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/edit.png"))); // NOI18N
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnRemove.setBackground(new java.awt.Color(255, 179, 179));
        btnRemove.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/delete.png"))); // NOI18N
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(164, 216, 255));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/refresh.png"))); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Exit.png"))); // NOI18N
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFeatureLayout = new javax.swing.GroupLayout(pnlFeature);
        pnlFeature.setLayout(pnlFeatureLayout);
        pnlFeatureLayout.setHorizontalGroup(
            pnlFeatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFeatureLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFeatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPayMethod_Name)
                    .addComponent(lblPayMethod_Id)
                    .addComponent(lblPaymentStatus))
                .addGap(18, 18, 18)
                .addGroup(pnlFeatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPayMethod_Id)
                    .addGroup(pnlFeatureLayout.createSequentialGroup()
                        .addComponent(rdoActive)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoStop)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtPayMethod_Name))
                .addContainerGap())
            .addGroup(pnlFeatureLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFeatureLayout.setVerticalGroup(
            pnlFeatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFeatureLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFeatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPayMethod_Id)
                    .addComponent(txtPayMethod_Id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFeatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPayMethod_Name)
                    .addComponent(txtPayMethod_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFeatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPaymentStatus)
                    .addComponent(rdoActive)
                    .addComponent(rdoStop))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlFeatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCreate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlFeature, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jspPaymentMethod, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                    .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jspPaymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExit))
                    .addComponent(pnlFeature, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // ==================== EVENT HANDLERS ====================
    
    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        createPaymentMethod();
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        updatePaymentMethod();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        deletePaymentMethod();
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        refreshData();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        handleExit();
    }//GEN-LAST:event_btnExitActionPerformed

    // ==================== BUSINESS LOGIC METHODS ====================

    /**
     * Tạo phương thức thanh toán mới
     */
    private void createPaymentMethod() {
        try {
            // Validate dữ liệu cho chức năng tạo mới
            if (!validateFormForCreate()) {
                return;
            }
            
            String methodName = txtPayMethod_Name.getText().trim();
            
            // Kiểm tra tên phương thức đã tồn tại chưa
            if (isMethodNameExists(methodName, 0)) {
                XDialog.error("Tên phương thức thanh toán '" + methodName + "' đã tồn tại!\nVui lòng nhập tên khác.", "Dữ liệu đã tồn tại");
                txtPayMethod_Name.requestFocus();
                return;
            }
            
            // Tạo đối tượng PaymentMethod mới
            PaymentMethod newMethod = getCurrentFormData();
            
            // Lưu vào database
            paymentMethodDAO.create(newMethod);
            
            // Refresh dữ liệu và thông báo
            loadPaymentMethods();
            clearForm();
            hasUnsavedChanges = false;
            XDialog.success("Thêm phương thức thanh toán thành công!");
            
        } catch (Exception e) {
            e.printStackTrace();
            XDialog.error("Lỗi: " + e.getMessage());
        }
    }

    /**
     * Cập nhật phương thức thanh toán
     */
    private void updatePaymentMethod() {
        try {
            // Kiểm tra có dòng nào được chọn không
            if (tblPaymentMethod.getSelectedRow() == -1) {
                XDialog.error("Vui lòng chọn một phương thức thanh toán để cập nhật!");
                return;
            }
            
            // Validate dữ liệu cho chức năng cập nhật
            if (!validateForm()) {
                return;
            }
            
            int selectedRow = tblPaymentMethod.getSelectedRow();
            int paymentMethodId = (Integer) tableModel.getValueAt(selectedRow, 0);
            String oldMethodName = (String) tableModel.getValueAt(selectedRow, 1);
            String oldStatus = (String) tableModel.getValueAt(selectedRow, 2);
            
            String newMethodName = txtPayMethod_Name.getText().trim();
            
            // Kiểm tra tên phương thức đã tồn tại chưa (trừ chính nó)
            if (isMethodNameExists(newMethodName, paymentMethodId)) {
                XDialog.error("Tên phương thức thanh toán '" + newMethodName + "' đã tồn tại!\nVui lòng nhập tên khác.", "Dữ liệu đã tồn tại");
                txtPayMethod_Name.requestFocus();
                return;
            }
            
            // Lấy thông tin mới
            PaymentMethod newData = getCurrentFormData();
            
            // Tạo thông tin cũ để so sánh
            PaymentMethod oldData = PaymentMethod.builder()
                    .payment_method_id(paymentMethodId)
                    .method_name(oldMethodName)
                    .is_enable("Hoạt động".equals(oldStatus) ? 1 : 0)
                    .build();
            
            // Hiển thị dialog xác nhận với thông tin so sánh
            String message = "Bạn có muốn thay đổi thông tin phương thức thanh toán?\n\n" +
                           "THÔNG TIN HIỆN TẠI:\n" + formatPaymentMethodInfo(oldData) + "\n\n" +
                           "THÔNG TIN MỚI:\n" + formatPaymentMethodInfo(newData);
            
            if (XDialog.confirm(message, "Xác nhận cập nhật")) {
                // Cập nhật vào database
                paymentMethodDAO.update(newData);
                
                // Refresh dữ liệu và thông báo
                loadPaymentMethods();
                clearForm();
                hasUnsavedChanges = false;
                XDialog.success("Cập nhật phương thức thanh toán thành công!");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            XDialog.error("Lỗi: " + e.getMessage());
        }
    }

    /**
     * Xóa phương thức thanh toán
     */
    private void deletePaymentMethod() {
        try {
            // Kiểm tra có dòng nào được chọn không
            if (tblPaymentMethod.getSelectedRow() == -1) {
                XDialog.error("Vui lòng chọn một phương thức thanh toán để xóa!");
                return;
            }
            
            int selectedRow = tblPaymentMethod.getSelectedRow();
            int paymentMethodId = (Integer) tableModel.getValueAt(selectedRow, 0);
            String methodName = (String) tableModel.getValueAt(selectedRow, 1);
            String status = (String) tableModel.getValueAt(selectedRow, 2);
            
            // Tạo thông tin để hiển thị
            PaymentMethod toDelete = PaymentMethod.builder()
                    .payment_method_id(paymentMethodId)
                    .method_name(methodName)
                    .is_enable("Hoạt động".equals(status) ? 1 : 0)
                    .build();
            
            // Hiển thị dialog xác nhận với thông tin chi tiết
            String message = "Bạn có chắc chắn muốn xóa phương thức thanh toán này?\n\n" +
                           "THÔNG TIN SẼ BỊ XÓA:\n" + formatPaymentMethodInfo(toDelete);
            
            if (XDialog.confirm(message, "Xác nhận xóa")) {
                // Xóa từ database
                paymentMethodDAO.deleteById(paymentMethodId);
                
                // Refresh dữ liệu và thông báo
                loadPaymentMethods();
                clearForm();
                hasUnsavedChanges = false;
                XDialog.success("Xóa phương thức thanh toán thành công!");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            XDialog.error("Lỗi: " + e.getMessage());
        }
    }

    /**
     * Refresh dữ liệu với kiểm tra thay đổi chi tiết
     */
    private void refreshData() {
        if (hasUnsavedChanges) {
            String message = "Bạn có thay đổi chưa lưu trên form.\n\n" +
                           "Các thay đổi có thể bao gồm:\n" +
                           "• Thay đổi mã phương thức\n" +
                           "• Thay đổi tên phương thức\n" +
                           "• Thay đổi trạng thái\n\n" +
                           "Bạn có muốn làm mới dữ liệu?\n" +
                           "Tất cả thay đổi sẽ bị mất.";
            
            if (XDialog.confirm(message, "Xác nhận làm mới")) {
                loadPaymentMethods();
                clearForm();
                hasUnsavedChanges = false;
                XDialog.success("Đã làm mới dữ liệu thành công!");
            }
        } else {
            loadPaymentMethods();
            clearForm();
            XDialog.success("Đã làm mới dữ liệu thành công!");
        }
    }

    /**
     * Xử lý thoát ứng dụng với kiểm tra thay đổi chi tiết
     */
    private void handleExit() {
        if (hasUnsavedChanges) {
            String message = "Bạn có thay đổi chưa lưu trên form.\n\n" +
                           "Các thay đổi có thể bao gồm:\n" +
                           "• Thay đổi mã phương thức\n" +
                           "• Thay đổi tên phương thức\n" +
                           "• Thay đổi trạng thái\n\n" +
                           "Bạn có muốn:\n" +
                           "• Hủy bỏ thay đổi và thoát\n" +
                           "• Tiếp tục chỉnh sửa";
            
            String[] options = {"Hủy bỏ thay đổi", "Tiếp tục chỉnh sửa"};
            String choice = XDialog.selection(message, "Xác nhận thoát", options);
            
            if ("Hủy bỏ thay đổi".equals(choice)) {
                this.dispose();
            }
            // Nếu chọn "Tiếp tục chỉnh sửa" thì không làm gì cả
        } else {
            if (XDialog.confirm("Bạn có chắc chắn muốn thoát?", "Xác nhận thoát")) {
                this.dispose();
            }
        }
    }

    /**
     * Validate form trước khi lưu với thông báo chi tiết
     */
    private boolean validateForm() {
        StringBuilder errorMessage = new StringBuilder();
        boolean hasError = false;
        
        // Kiểm tra mã phương thức
        String paymentMethodId = txtPayMethod_Id.getText().trim();
        if (paymentMethodId.isEmpty()) {
            errorMessage.append("• Mã phương thức thanh toán không được để trống\n");
            hasError = true;
        } else {
            try {
                Integer.parseInt(paymentMethodId);
            } catch (NumberFormatException e) {
                errorMessage.append("• Mã phương thức thanh toán phải là số\n");
                hasError = true;
            }
        }
        
        // Kiểm tra tên phương thức
        String methodName = txtPayMethod_Name.getText().trim();
        if (methodName.isEmpty()) {
            errorMessage.append("• Tên phương thức thanh toán không được để trống\n");
            hasError = true;
        } else if (methodName.length() > 50) {
            errorMessage.append("• Tên phương thức thanh toán không được quá 50 ký tự\n");
            hasError = true;
        }
        
        // Kiểm tra trạng thái
        if (!rdoActive.isSelected() && !rdoStop.isSelected()) {
            errorMessage.append("• Vui lòng chọn trạng thái (Hoạt động hoặc Tạm dừng)\n");
            hasError = true;
        }
        
        // Hiển thị lỗi nếu có
        if (hasError) {
            String title = "Lỗi nhập liệu";
            String message = "Vui lòng kiểm tra và sửa các lỗi sau:\n\n" + errorMessage.toString();
            XDialog.error(message, title);
            
            // Focus vào trường đầu tiên có lỗi
            if (paymentMethodId.isEmpty()) {
                txtPayMethod_Id.requestFocus();
            } else if (methodName.isEmpty()) {
                txtPayMethod_Name.requestFocus();
            } else if (!rdoActive.isSelected() && !rdoStop.isSelected()) {
                rdoActive.requestFocus();
            }
            
            return false;
        }
        
        return true;
    }

    /**
     * Validate form cho chức năng tạo mới (cần mã)
     */
    private boolean validateFormForCreate() {
        StringBuilder errorMessage = new StringBuilder();
        boolean hasError = false;
        
        // Kiểm tra mã phương thức
        String paymentMethodIdStr = txtPayMethod_Id.getText().trim();
        if (paymentMethodIdStr.isEmpty()) {
            errorMessage.append("• Mã phương thức thanh toán không được để trống\n");
            hasError = true;
        } else {
            try {
                int paymentMethodId = Integer.parseInt(paymentMethodIdStr);
                
                // Kiểm tra mã có hợp lệ không (phải là số dương)
                if (paymentMethodId <= 0) {
                    errorMessage.append("• Mã phương thức thanh toán phải là số dương\n");
                    hasError = true;
                } else {
                    // Kiểm tra mã có trùng với phương thức khác không
                    if (isMethodIdExists(paymentMethodId, 0)) {
                        errorMessage.append("• Mã phương thức thanh toán '" + paymentMethodId + "' đã tồn tại\n");
                        hasError = true;
                    }
                }
            } catch (NumberFormatException e) {
                errorMessage.append("• Mã phương thức thanh toán phải là số\n");
                hasError = true;
            }
        }
        
        // Kiểm tra tên phương thức
        String methodName = txtPayMethod_Name.getText().trim();
        if (methodName.isEmpty()) {
            errorMessage.append("• Tên phương thức thanh toán không được để trống\n");
            hasError = true;
        } else if (methodName.length() > 50) {
            errorMessage.append("• Tên phương thức thanh toán không được quá 50 ký tự\n");
            hasError = true;
        }
        
        // Kiểm tra trạng thái
        if (!rdoActive.isSelected() && !rdoStop.isSelected()) {
            errorMessage.append("• Vui lòng chọn trạng thái (Hoạt động hoặc Tạm dừng)\n");
            hasError = true;
        }
        
        // Hiển thị lỗi nếu có
        if (hasError) {
            String title = "Lỗi nhập liệu";
            String message = "Vui lòng kiểm tra và sửa các lỗi sau:\n\n" + errorMessage.toString();
            XDialog.error(message, title);
            
            // Focus vào trường đầu tiên có lỗi
            if (paymentMethodIdStr.isEmpty() || !paymentMethodIdStr.matches("\\d+") || 
                (paymentMethodIdStr.matches("\\d+") && Integer.parseInt(paymentMethodIdStr) <= 0) ||
                (paymentMethodIdStr.matches("\\d+") && isMethodIdExists(Integer.parseInt(paymentMethodIdStr), 0))) {
                txtPayMethod_Id.requestFocus();
            } else if (methodName.isEmpty()) {
                txtPayMethod_Name.requestFocus();
            } else if (!rdoActive.isSelected() && !rdoStop.isSelected()) {
                rdoActive.requestFocus();
            }
            
            return false;
        }
        
        return true;
    }

    /**
     * Clear form
     */
    private void clearForm() {
        txtPayMethod_Id.setText("");
        txtPayMethod_Name.setText("");
        rdoActive.setSelected(true);
        tblPaymentMethod.clearSelection();
        hasUnsavedChanges = false;
    }

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
            java.util.logging.Logger.getLogger(PaymentMethodManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaymentMethodManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaymentMethodManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaymentMethodManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaymentMethodManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnExit;
    private javax.swing.ButtonGroup btnGrpStatus;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jspPaymentMethod;
    private javax.swing.JLabel lblPayMethod_Id;
    private javax.swing.JLabel lblPayMethod_Name;
    private javax.swing.JLabel lblPaymentStatus;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlFeature;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JRadioButton rdoActive;
    private javax.swing.JRadioButton rdoStop;
    private javax.swing.JTable tblPaymentMethod;
    private javax.swing.JTextField txtPayMethod_Id;
    private javax.swing.JTextField txtPayMethod_Name;
    // End of variables declaration//GEN-END:variables
}
