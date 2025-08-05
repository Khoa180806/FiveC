/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.team4.quanliquanmicay.View.management;

import com.team4.quanliquanmicay.Controller.EmployeeController;
import com.team4.quanliquanmicay.DAO.RoleDAO;
import com.team4.quanliquanmicay.DAO.UserDAO;
import com.team4.quanliquanmicay.Entity.UserAccount;
import com.team4.quanliquanmicay.Entity.UserRole;
import com.team4.quanliquanmicay.Impl.RoleDAOImpl;
import com.team4.quanliquanmicay.Impl.UserDAOImpl;
import com.team4.quanliquanmicay.util.XTheme;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import com.team4.quanliquanmicay.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.event.*;

/**
 *
 * @author HP
 */
public class UserManagement extends javax.swing.JFrame implements EmployeeController {

    /**
     * Creates new form UserManagement
     */
    private UserDAO userDAO;
    private RoleDAO roleDAO; // Thêm RoleDAO
    private Map<String, String> roleMap; // Cache role_id -> name_role

    public UserManagement() {
        this.setUndecorated(true);
        XTheme.applyFullTheme();
        initComponents();
        setLocationRelativeTo(null);
        
        // Initialize DAOs và cache
        userDAO = new UserDAOImpl();
        roleDAO = new RoleDAOImpl();
        roleMap = new HashMap<>();
        
        // ✅ EARLY CAPTURE: Capture initial image size ngay sau initComponents
        captureInitialImageSize();
        
        // Setup all functionality
        loadRoles();
        setupStatusComboBox(); // ✅ SAFE: Protected by disableComboBoxUpdates flag
        setupRoleComboBox();   // ✅ SAFE: Protected by disableComboBoxUpdates flag
        fillToTable();
        setColumnWidths();
        setupEventListeners();
        setupPerformanceOptimizations();
        preloadDefaultImages();
        setupImageSelection();
        setupSearchFunctionality();
        
        // ✅ FINAL: Capture and freeze initial layout size
        javax.swing.SwingUtilities.invokeLater(() -> {
            validate();
            repaint();
            
            // ✅ CAPTURE: Store initial table size to prevent expansion
            if (frozenTableSize == null) {
                frozenTableSize = new java.awt.Dimension(jScrollPane1.getSize());
                System.out.println("📐 Captured initial table size: " + frozenTableSize);
                
                // ✅ VERIFY: Re-capture image size if needed
                if (originalImageSize == null) {
                    captureInitialImageSize();
                }
                
                // ✅ SETUP: Periodic size enforcement timer
                setupSizeEnforcementTimer();
            }
        });
    }
    
    /**
     * ✅ NEW: Capture initial image label size immediately
     */
    private void captureInitialImageSize() {
        try {
            // Force layout validation to get correct size
            lblImage.validate();
            java.awt.Dimension currentSize = lblImage.getSize();
            
            // Set default size if not properly initialized
            if (currentSize.width <= 0 || currentSize.height <= 0) {
                currentSize = new java.awt.Dimension(116, 167); // From layout manager
            }
            
            originalImageSize = new java.awt.Dimension(currentSize);
            System.out.println("🖼️ Captured initial image size: " + originalImageSize);
            
            // Set border to indicate clickable area với size cố định
            lblImage.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 149, 237), 2),
                "Click để chọn ảnh",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.BOTTOM,
                new java.awt.Font("Arial", java.awt.Font.ITALIC, 10),
                new java.awt.Color(100, 149, 237)
            ));
            
        } catch (Exception e) {
            // Fallback to default size
            originalImageSize = new java.awt.Dimension(116, 167);
            System.out.println("⚠️ Using fallback image size: " + originalImageSize);
        }
    }
    
    /**
     * ✅ OPTIMIZED: Setup all event listeners với debouncing
     */
    private void setupEventListeners() {
        tableInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            private long lastClickTime = 0;
            
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                long currentTime = System.currentTimeMillis();
                // Debounce clicks - chỉ cho phép 1 click mỗi 300ms
                if (evt.getClickCount() == 1 && (currentTime - lastClickTime) > 300) {
                    lastClickTime = currentTime;
                    
                    // ✅ PROTECT: Enforce table size before edit
                    enforceTableSize();
                    
                    edit();
                    
                    // ✅ PROTECT: Enforce table size after edit
                    javax.swing.SwingUtilities.invokeLater(() -> enforceTableSize());
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupGioiTinh = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtIdEmployee = new javax.swing.JTextField();
        txtNameEmployee = new javax.swing.JTextField();
        chkMale = new javax.swing.JCheckBox();
        chkFemale = new javax.swing.JCheckBox();
        txtPhoneNumber = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNameAccount = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cboStatus = new javax.swing.JComboBox<>();
        cboRole = new javax.swing.JComboBox<>();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableInfo = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(134, 39, 43));

        jLabel1.setBackground(new java.awt.Color(153, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("QUẢN LÍ NHÂN VIÊN FIVE C");

        jPanel3.setBackground(new java.awt.Color(204, 164, 133));

        jPanel5.setBackground(new java.awt.Color(204, 164, 133));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tên đăng nhập :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Họ và tên :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Giới tính :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Số điện thoại :");

        txtIdEmployee.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtNameEmployee.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        groupGioiTinh.add(chkMale);
        chkMale.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chkMale.setForeground(new java.awt.Color(255, 255, 255));
        chkMale.setText("Nam");

        groupGioiTinh.add(chkFemale);
        chkFemale.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chkFemale.setForeground(new java.awt.Color(255, 255, 255));
        chkFemale.setText("Nữ");

        txtPhoneNumber.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(204, 164, 133));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Mã nhân viên :");

        txtNameAccount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Mật khẩu :");

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Email :");

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Trạng thái :");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Vai trò :");

        cboStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cboRole.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSave.setBackground(new java.awt.Color(183, 239, 197));
        btnSave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(102, 102, 102));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/add.png"))); // NOI18N
        btnSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(164, 216, 255));
        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(102, 102, 102));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/refresh.png"))); // NOI18N
        btnUpdate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(255, 179, 179));
        btnClear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(0, 102, 51));
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/trash.png"))); // NOI18N
        btnClear.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 231, 153));
        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/edit.png"))); // NOI18N
        btnDelete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Search.png"))); // NOI18N
        jLabel6.setText("jLabel6");

        btnExit.setBackground(new java.awt.Color(119, 50, 5));
        btnExit.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Log out.png"))); // NOI18N
        btnExit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel8)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel10)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(chkMale)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkFemale))
                    .addComponent(txtPhoneNumber)
                    .addComponent(txtIdEmployee)
                    .addComponent(txtNameEmployee)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel3)
                            .addComponent(jLabel13))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPassword)
                                    .addComponent(txtNameAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(cboRole, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtNameAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtIdEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtNameEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(chkMale)
                                    .addComponent(chkFemale)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(182, 182, 182)
                .addComponent(jLabel8))
        );

        tableInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Tài khoản", "Mật khẩu", "Họ và tên", "Giới tính", "SĐT", "Email", "Trạng thái", "Vai trò", "Ngày tạo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableInfo);
        if (tableInfo.getColumnModel().getColumnCount() > 0) {
            tableInfo.getColumnModel().getColumn(0).setResizable(false);
            tableInfo.getColumnModel().getColumn(1).setResizable(false);
            tableInfo.getColumnModel().getColumn(2).setResizable(false);
            tableInfo.getColumnModel().getColumn(3).setResizable(false);
            tableInfo.getColumnModel().getColumn(4).setResizable(false);
            tableInfo.getColumnModel().getColumn(5).setResizable(false);
            tableInfo.getColumnModel().getColumn(6).setResizable(false);
            tableInfo.getColumnModel().getColumn(7).setResizable(false);
            tableInfo.getColumnModel().getColumn(8).setResizable(false);
            tableInfo.getColumnModel().getColumn(9).setResizable(false);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1364, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(424, 424, 424))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Event handler cho nút LÀM MỚI
     */
    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {
        clearForNewEntry();
    }

    /**
     * Clear form và chuẩn bị cho việc tạo nhân viên mới
     */
    private void clearForNewEntry() {
        // 1. Clear toàn bộ dữ liệu trên form
        txtIdEmployee.setText("");
        txtNameAccount.setText("");
        txtPassword.setText("");
        txtNameEmployee.setText("");
        txtPhoneNumber.setText("");
        txtEmail.setText("");
        
        // 2. Reset giới tính
        groupGioiTinh.clearSelection();
        
        // 3. Reset hình ảnh với khả năng click
        setDefaultImageWithClickable();
        
        // 4. Clear selection trong bảng
        tableInfo.clearSelection();
        
        // 5. ✅ QUAN TRỌNG: Enable mã nhân viên để có thể tạo mới
        txtIdEmployee.setEditable(true);
        
        // 6. Enable tất cả các trường khác
        setAllFieldsEditable(true);
        
        // 7. Clear search box về trạng thái ban đầu
        clearSearch();
        
        // 8. Focus vào mã nhân viên để bắt đầu nhập
        txtIdEmployee.requestFocus();
        
        System.out.println("✅ Đã clear form và sẵn sàng tạo nhân viên mới!");
    }

    /**
     * Set editable cho tất cả các trường (trừ ID nếu cần)
     */
    private void setAllFieldsEditable(boolean editable) {
        txtNameAccount.setEditable(editable);
        txtPassword.setEditable(editable);
        txtNameEmployee.setEditable(editable);
        txtPhoneNumber.setEditable(editable);
        txtEmail.setEditable(editable);
        
        chkMale.setEnabled(editable);
        chkFemale.setEnabled(editable);
        
        // Setup ComboBoxes cho Status và Role
        cboStatus.setEnabled(editable);
        cboRole.setEnabled(editable);
    }

    /**
     * Event handler cho nút LƯU (TẠO)
     */
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        create();
    }

    /**
     * Event handler cho nút UPDATE
     */
    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        update();
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * Event handler cho nút EXIT ở dòng 496
     */
    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {
        exitApplication();
    }

    /**
     * ✅ OPTIMIZED: Thoát ứng dụng đơn giản - chỉ 1 dialog duy nhất
     */
    private void exitApplication() {
        try {
            boolean hasData = hasUnsavedChanges();
            String message = hasData ? 
                "⚠️ Còn dữ liệu trong form!\n\nBạn có muốn thoát không?\n(Dữ liệu sẽ bị mất nếu chưa lưu)" :
                "Bạn có muốn thoát ứng dụng không?";
            
            if (XDialog.confirm(message, "Xác nhận thoát")) {
                System.out.println("Đang thoát ứng dụng Quản lý Nhân viên...");
                System.exit(0);
            }
        } catch (Exception e) {
            XDialog.alert("Lỗi khi thoát ứng dụng: " + e.getMessage(), "Lỗi hệ thống");
            e.printStackTrace();
        }
    }

    /**
     * ✅ OPTIMIZED: Kiểm tra có dữ liệu trong form không
     */
    private boolean hasUnsavedChanges() {
        return !isBlank(txtIdEmployee.getText()) || !isBlank(txtNameAccount.getText()) ||
               !isBlank(txtPassword.getText()) || !isBlank(txtNameEmployee.getText()) ||
               !isBlank(txtPhoneNumber.getText()) || !isBlank(txtEmail.getText()) ||
               chkMale.isSelected() || chkFemale.isSelected();
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
            java.util.logging.Logger.getLogger(UserManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboRole;
    private javax.swing.JComboBox<String> cboStatus;
    private javax.swing.JCheckBox chkFemale;
    private javax.swing.JCheckBox chkMale;
    private javax.swing.ButtonGroup groupGioiTinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblImage;
    private javax.swing.JTable tableInfo;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtIdEmployee;
    private javax.swing.JTextField txtNameAccount;
    private javax.swing.JTextField txtNameEmployee;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtPhoneNumber;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

    @Override
    public void loadRoles() {
        try {
            // Load các vai trò từ database để map tên
            roleMap = new HashMap<>();
            
            List<UserRole> roles = roleDAO.findAll();
            for (UserRole role : roles) {
                // Cache role mapping
                roleMap.put(role.getRole_id(), role.getName_role());
            }

            System.out.println("Đã load " + roles.size() + " roles từ database");

        } catch (Exception e) {
            XDialog.alert("Lỗi khi load roles: " + e.getMessage());
            e.printStackTrace();
            loadRolesFallback();
        }
    }

    /**
     * Fallback method nếu không load được từ DB
     */
    private void loadRolesFallback() {
        // Fallback data
        roleMap.clear();
        roleMap.put("R001", "Manager");
        roleMap.put("R002", "Staff");
    }

    /**
     * Load tất cả dữ liệu nhân viên lên bảng (không filter)
     */
    @Override
    public void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tableInfo.getModel();
        model.setRowCount(0);

        try {
            // Lấy tất cả nhân viên từ database
            List<UserAccount> employees = userDAO.findAll();

            // Đổ tất cả dữ liệu vào bảng
            for (UserAccount emp : employees) {
                Object[] row = {
                    emp.getUser_id(), // Mã nhân viên
                    emp.getUsername(), // Tài khoản
                    emp.getPass(), // Mật khẩu
                    emp.getFullName(), // Họ và tên
                    emp.getGender() != null ? (emp.getGender() == 1 ? "Nam" : "Nữ") : "N/A", // Giới tính
                    emp.getPhone_number(), // SĐT
                    emp.getEmail(), // Email
                    emp.getIs_enabled() == 1 ? "Hoạt động" : "Không hoạt động", // Trạng thái
                    getRoleName(emp.getRole_id()), // Vai trò
                    formatDate(emp.getCreated_date()) // Ngày tạo
                };
                model.addRow(row);
            }

            System.out.println("Đã load " + employees.size() + " nhân viên lên bảng");

        } catch (Exception e) {
            XDialog.alert("Lỗi khi load dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Filter danh sách nhân viên theo Status và Role
     */
    private List<UserAccount> filterEmployees(List<UserAccount> employees, String selectedStatus, String selectedRole) {
        List<UserAccount> filtered = new ArrayList<>();

        for (UserAccount emp : employees) {
            boolean matchStatus = true;
            boolean matchRole = true;

            // Filter theo Status
            if (selectedStatus != null && !selectedStatus.equals("Tất cả")) {
                if (selectedStatus.equals("Hoạt động")) {
                    matchStatus = (emp.getIs_enabled() == 1);
                } else if (selectedStatus.equals("Không hoạt động")) {
                    matchStatus = (emp.getIs_enabled() == 0); 
                }
            }

            // Filter theo Role - cần extract role_id từ display text
            if (selectedRole != null && !selectedRole.equals("Tất cả")) {
                String roleId = extractRoleId(selectedRole);
                matchRole = roleId.equals(emp.getRole_id());
            }

            // Chỉ thêm vào danh sách nếu thỏa mãn cả 2 điều kiện
            if (matchStatus && matchRole) {
                filtered.add(emp);
            }
        }

        return filtered;
    }

    /**
     * Extract role_id từ display text (VD: "R001 - Manager" -> "R001")
     */
    private String extractRoleId(String displayText) {
        if (displayText != null && displayText.contains(" - ")) {
            return displayText.split(" - ")[0];
        }
        return displayText; // Fallback
    }

    /**
     * ✅ OPTIMIZED: Lấy tên vai trò từ cache
     */
    private String getRoleName(String roleId) {
        return (roleId != null && roleMap.containsKey(roleId)) ? roleMap.get(roleId) : "N/A";
    }

    /**
     * ✅ OPTIMIZED: Format ngày tháng để hiển thị trong bảng
     */
    private String formatDate(java.util.Date date) {
        if (date == null) return "N/A";
        try {
            return new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
        } catch (Exception e) {
            System.err.println("Lỗi format date: " + e.getMessage());
            return date.toString();
        }
    }

    /**
     * Set độ rộng cột cho bảng
     */
    private void setColumnWidths() {
        try {
            tableInfo.getColumnModel().getColumn(0).setPreferredWidth(120);  // Mã NV
            tableInfo.getColumnModel().getColumn(1).setPreferredWidth(100); // Tài khoản
            tableInfo.getColumnModel().getColumn(2).setPreferredWidth(80);  // Mật khẩu
            tableInfo.getColumnModel().getColumn(3).setPreferredWidth(150); // Họ tên
            tableInfo.getColumnModel().getColumn(4).setPreferredWidth(100);  // Giới tính
            tableInfo.getColumnModel().getColumn(5).setPreferredWidth(100); // SĐT
            tableInfo.getColumnModel().getColumn(6).setPreferredWidth(110); // Email
            tableInfo.getColumnModel().getColumn(7).setPreferredWidth(100); // Trạng thái
            tableInfo.getColumnModel().getColumn(8).setPreferredWidth(70);  // Vai trò
            tableInfo.getColumnModel().getColumn(9).setPreferredWidth(130); // Ngày tạo
        } catch (Exception e) {
            System.err.println("Lỗi set column width: " + e.getMessage());
        }
    }

    @Override
    public void open() {
    }



    /**
     * Hiển thị thông tin role (chỉ để xem, không ảnh hưởng filter)
     */
    private void displayRoleInfo(String roleId) {
        try {
            if (roleId != null) {
                UserRole role = roleDAO.findById(roleId);
                if (role != null) {
                    // Hiển thị trong label hoặc text field chỉ đọc
                    System.out.println("Employee role: " + role.getRole_id() + " - " + role.getName_role());
                    // Có thể thêm label để hiển thị: lblRoleInfo.setText(role.getName_role());
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi load role info: " + e.getMessage());
        }
    }

    @Override
    public UserAccount getForm() {
        // Lấy dữ liệu từ form tạo thành entity
        UserAccount entity = new UserAccount();
        
        // Đảm bảo không NULL
        String userId = txtIdEmployee.getText();
        if (userId == null || userId.trim().isEmpty()) {
            throw new RuntimeException("Mã nhân viên không được để trống!");
        }
        entity.setUser_id(userId.trim());
        
        String username = txtNameAccount.getText();
        if (username == null || username.trim().isEmpty()) 
            throw new RuntimeException("Tên đăng nhập không được để trống!");
        entity.setUsername(username.trim());
        
        String password = txtPassword.getText();
        if (password == null || password.trim().isEmpty()) 
            throw new RuntimeException("Mật khẩu không được để trống!");
        entity.setPass(password.trim());
        
        String fullName = txtNameEmployee.getText();
        if (fullName == null || fullName.trim().isEmpty()) 
            throw new RuntimeException("Họ tên không được để trống!");
        entity.setFullName(fullName.trim());
        
        // Xử lý giới tính
        if (chkMale.isSelected()) {
            entity.setGender(1);  // Nam
        } else if (chkFemale.isSelected()) {
            entity.setGender(0);  // Nữ
        } else {
            throw new RuntimeException("Vui lòng chọn giới tính!");
        }
        
        // Đảm bảo email và phone không NULL
        String email = txtEmail.getText();
        entity.setEmail(email != null ? email.trim() : "");
        
        String phone = txtPhoneNumber.getText();
        entity.setPhone_number(phone != null ? phone.trim() : "");
        
        // Status và Role từ ComboBox (hoặc mặc định)
        entity.setIs_enabled(getStatusFromComboBox()); // Từ ComboBox Status
        entity.setRole_id(getRoleIdFromComboBox()); // Từ ComboBox Role
        
        // Xử lý hình ảnh
        if (lblImage.getIcon() != null) {
            String currentImageText = lblImage.getText();
            if (currentImageText != null && !currentImageText.equals("No Image")
                    && !currentImageText.equals("Error") && !currentImageText.trim().isEmpty()) {
                entity.setImage(currentImageText);
            } else {
                entity.setImage("default.jpg");
            }
        } else {
            entity.setImage("default.jpg");
        }

        return entity;
    }

    @Override
    public void edit() {
        editWithCache(); // Thay vì code cũ
    }

    /**
     * Load và hiển thị ảnh nhân viên sử dụng XImage utility
     */
    private void loadEmployeeImage(String imageName) {
        try {
            if (imageName != null && !imageName.trim().isEmpty()) {
                // Đường dẫn ảnh trong resources/icons_and_images/imageEmployee/
                String imagePath = "/icons_and_images/imageEmployee/" + imageName;

                System.out.println("Trying to load image: " + imagePath);

                // Kiểm tra ảnh có tồn tại không
                java.net.URL imageURL = getClass().getResource(imagePath);

                if (imageURL != null) {
                    // Sử dụng XImage utility để set ảnh
                    XImage.setImageToLabel(lblImage, imagePath);
                    lblImage.setText(""); // Xóa text nếu có ảnh

                    System.out.println("✅ Successfully loaded image: " + imageName);
                } else {
                    // Thử load từ thư mục gốc icons_and_images
                    String fallbackPath = "/icons_and_images/" + imageName;
                    System.out.println("Image not found in imageEmployee, trying: " + fallbackPath);

                    java.net.URL fallbackURL = getClass().getResource(fallbackPath);
                    if (fallbackURL != null) {
                        XImage.setImageToLabel(lblImage, fallbackPath);
                        lblImage.setText("");

                        System.out.println("✅ Successfully loaded image from fallback: " + imageName);
                    } else {
                        // Sử dụng placeholder
                        setPlaceholderImage(imageName);
                    }
                }
            } else {
                // Không có tên ảnh - sử dụng ảnh mặc định
                setDefaultImage();
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi load ảnh: " + e.getMessage());
            setPlaceholderImage(imageName);
        }
    }

    /**
     * ✅ OPTIMIZED: Hiển thị ảnh placeholder khi không tìm thấy ảnh
     */
    private void setPlaceholderImage(String imageName) {
        try {
            String placeholderPath = "/icons_and_images/Unknown person.png";
            if (getClass().getResource(placeholderPath) != null) {
                XImage.setImageToLabel(lblImage, placeholderPath);
                lblImage.setText("");
                System.out.println("📷 Using placeholder image for: " + imageName);
            } else {
                lblImage.setIcon(null);
                lblImage.setText(imageName != null ? imageName : "No Image");
            }
        } catch (Exception e) {
            lblImage.setIcon(null);
            lblImage.setText("Error");
        }
    }

    /**
     * ✅ OPTIMIZED: Hiển thị ảnh mặc định khi không có tên ảnh với size cố định
     */
    private void setDefaultImage() { 
        setDefaultImageWithClickable(); // Sử dụng phương thức đã được cải thiện
    }
    
    /**
     * ✅ ENHANCED: Set default image but keep clickable với size cố định
     */
    private void setDefaultImageWithClickable() {
        try {
            // ✅ ENFORCE SIZE: Sử dụng phương thức với size cố định
            if (originalImageSize != null) {
                setImageWithFixedSize("/icons_and_images/User.png");
            } else {
                // Fallback nếu originalImageSize chưa sẵn sàng
                XImage.setImageToLabel(lblImage, "/icons_and_images/User.png");
                // Force capture size ngay sau khi set image
                captureInitialImageSize();
                // Set lại image với size cố định
                setImageWithFixedSize("/icons_and_images/User.png");
            }
            
            lblImage.setText("");
            
            // Ensure tooltip is set
            lblImage.setToolTipText("Click để chọn ảnh nhân viên");
            
        } catch (Exception e) {
            lblImage.setIcon(null);
            lblImage.setText("Click để chọn ảnh");
            lblImage.setToolTipText("Click để chọn ảnh nhân viên");
            
            // ✅ ENFORCE: Keep size even on error
            if (originalImageSize != null) {
                lblImage.setSize(originalImageSize);
                lblImage.setPreferredSize(originalImageSize);
                lblImage.setMinimumSize(originalImageSize);
                lblImage.setMaximumSize(originalImageSize);
            }
        }
    }

    /**
     * Method để test load ảnh (có thể gọi để kiểm tra)
     */
    public void testLoadImage() {
        // Test với một số ảnh có sẵn
        String[] testImages = {"admin01.jpg", "admin02.jpg", "trump.png", "User.png"};

        for (String imageName : testImages) {
            System.out.println("Testing image: " + imageName);
            loadEmployeeImage(imageName);

            try {
                Thread.sleep(2000); // Delay 2 giây để xem ảnh
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ✅ CREATE: Tạo nhân viên mới với validation đầy đủ
     */
    private boolean isProcessing = false; // Flag để prevent double click

    @Override
    public void create() {
        createWithCache(); // Thay vì code cũ
    }

    /**
     * ✅ UPDATE: Cập nhật nhân viên với preserve important fields
     */
    @Override
    public void update() {
        updateWithCache(); // Thay vì code cũ
    }

    /**
     * ✅ CLEAR: Clear form và prepare cho new entry
     */
    private void clearFormButKeepImage() {
        txtIdEmployee.setText("");
        txtNameAccount.setText("");
        txtPassword.setText("");
        txtNameEmployee.setText("");
        txtPhoneNumber.setText("");
        txtEmail.setText("");

        // Reset gender selection
        groupGioiTinh.clearSelection();

        // Clear table selection và reset tracking
        tableInfo.clearSelection();
        lastSelectedRow = -1; // ✅ RESET: Clear row tracking
        
        // Enable ID field for next create
        txtIdEmployee.setEditable(true);
        
        // Note: Image is preserved intentionally
    }

    /**
     * ✅ LOG: Professional logging for operations
     */
    private void logEmployeeInfo(String operation, UserAccount employee) {
        System.out.println("=== " + operation + " EMPLOYEE ===");
        System.out.println("ID: " + employee.getUser_id());
        System.out.println("Username: " + employee.getUsername());
        System.out.println("Full Name: " + employee.getFullName());
        System.out.println("Email: " + employee.getEmail());
        System.out.println("Phone: " + employee.getPhone_number());
        System.out.println("Gender: " + (employee.getGender() == 1 ? "Nam" : "Nữ"));
        System.out.println("Role: " + employee.getRole_id());
        System.out.println("Time: " + new java.util.Date());
        System.out.println("========================");
    }

    /**
     * ✅ OPTIMIZED: Utility methods
     */
    private boolean isBlank(String str) { return str == null || str.trim().isEmpty(); }
    
    private boolean isFormEmpty() {
        return isBlank(txtIdEmployee.getText()) && isBlank(txtNameAccount.getText()) &&
               isBlank(txtPassword.getText()) && isBlank(txtNameEmployee.getText()) &&
               isBlank(txtPhoneNumber.getText()) && isBlank(txtEmail.getText()) &&
               !chkMale.isSelected() && !chkFemale.isSelected();
    }

    @Override
    public void delete() {
        try {
            // 1. Kiểm tra có dòng nào được chọn không
            int selectedRow = tableInfo.getSelectedRow();
            if (selectedRow < 0) {
                XDialog.alert("Vui lòng chọn một nhân viên để xóa!", "Thông báo");
                return;
            }

            // 2. Lấy thông tin nhân viên được chọn
            String userId = (String) tableInfo.getValueAt(selectedRow, 0);
            String fullName = (String) tableInfo.getValueAt(selectedRow, 3);

            // 3. Xác nhận xóa
            boolean confirmed = XDialog.confirm(
                    "Bạn có chắc chắn muốn xóa nhân viên:\n"
                    + "Mã: " + userId + "\n"
                    + "Tên: " + fullName + "\n\n"
                    + "⚠️ Hành động này không thể hoàn tác!",
                    "Xác nhận xóa"
            );

            if (!confirmed) {
                return; // Người dùng chọn "No" hoặc đóng dialog
            }

            // 4. Kiểm tra nhân viên có tồn tại không
            UserAccount employee = userDAO.findById(userId);
            if (employee == null) {
                XDialog.alert("Không tìm thấy nhân viên với mã: " + userId, "Lỗi");
                return;
            }

            // 5. Kiểm tra ràng buộc nghiệp vụ (tùy chọn)
            // Ví dụ: Không cho xóa Manager cuối cùng
            if ("R001".equals(employee.getRole_id())) {
                List<UserAccount> allManagers = userDAO.findAll().stream()
                        .filter(u -> "R001".equals(u.getRole_id()))
                        .collect(java.util.stream.Collectors.toList());

                if (allManagers.size() <= 1) {
                    XDialog.alert(
                            "Không thể xóa Manager cuối cùng trong hệ thống!",
                            "Lỗi ràng buộc nghiệp vụ"
                    );
                    return;
                }
            }

            // 6. Thực hiện xóa
            userDAO.deleteById(userId);

            // 7. Refresh bảng
            invalidateCache(); // Invalidate cache after deletion
            fillToTableWithCache();

            // 8. Clear form
            clear();

            // 9. Thông báo thành công
            XDialog.alert(
                    "✅ Đã xóa nhân viên thành công!\n"
                    + "Mã: " + userId + "\n"
                    + "Tên: " + fullName,
                    "Xóa thành công"
            );

        } catch (Exception e) {
            // 10. Xử lý lỗi
            XDialog.alert(
                    "❌ Lỗi khi xóa nhân viên: " + e.getMessage(),
                    "Lỗi hệ thống"
            );
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        // Method clear() gốc - chỉ clear dữ liệu, không thay đổi editable
        txtIdEmployee.setText("");
        txtNameAccount.setText("");
        txtPassword.setText("");
        txtNameEmployee.setText("");
        txtPhoneNumber.setText("");
        txtEmail.setText("");
        
        // Reset giới tính
        groupGioiTinh.clearSelection();
        
        // Reset hình ảnh với khả năng click
        setDefaultImageWithClickable();
        
        // Reset ComboBoxes về mặc định
        cboStatus.setSelectedIndex(0); // Hoạt động
        if (cboRole.getItemCount() > 1) cboRole.setSelectedIndex(1); // Staff
    }

    /**
     * Method riêng để reset filter về "Tất cả" - không cần nữa vì không filter
     */
    public void resetFilter() {
        // Reset ComboBoxes về mặc định
        cboStatus.setSelectedIndex(0); // Hoạt động
        if (cboRole.getItemCount() > 1) cboRole.setSelectedIndex(1); // Staff
        fillToTable(); // Reload toàn bộ dữ liệu
    }

    @Override
    public void setEditable(boolean editable) {
        // Cho phép/không cho phép chỉnh sửa form
        txtIdEmployee.setEditable(!editable); // ID không được sửa
        txtNameAccount.setEditable(editable);
        txtPassword.setEditable(editable);
        txtNameEmployee.setEditable(editable);
        txtPhoneNumber.setEditable(editable);
        txtEmail.setEditable(editable);

        chkMale.setEnabled(editable);
        chkFemale.setEnabled(editable);
        
        // cboStatus và cboRole setup (sẽ được gọi từ setupStatusComboBox và setupRoleComboBox)
        // ComboBox không cần setEditable(false) vì user vẫn có thể chọn
    }

    @Override
    public void checkAll() {
    }

    @Override
    public void uncheckAll() {
    }

    @Override
    public void deleteCheckedItems() {
    }

// =============================================================================
// PERFORMANCE OPTIMIZATION - THÊM VÀO CUỐI FILE (KHÔNG ĐỘNG VÀO CODE CŨ)
// =============================================================================

    // ✅ CACHE: Performance variables
    private List<UserAccount> employeeCache = new ArrayList<>();
    private boolean isCacheValid = false;
    private javax.swing.Timer debounceTimer;
    private boolean isProcessingEdit = false; // ✅ PROTECT: Prevent multiple edit calls
    private int lastSelectedRow = -1; // ✅ TRACK: Last selected row to avoid redundant calls
    private boolean layoutFrozen = false; // ✅ FREEZE: Layout protection flag
    private java.awt.Dimension frozenTableSize = null; // ✅ STORE: Original table size
    private javax.swing.Timer sizeEnforcementTimer; // ✅ PERIODIC: Size enforcement timer
    private java.awt.Dimension originalImageSize = null; // ✅ STORE: Original image label size

    /**
     * ✅ OPTIMIZED: Initialize performance cache
     */
    private void initializePerformanceCache() {
        // Setup debounce timer for filtering
        debounceTimer = new javax.swing.Timer(300, e -> performFilterAndFill());
        debounceTimer.setRepeats(false);
        
        // Pre-size cache
        employeeCache = new ArrayList<>(100);
    }

    /**
     * ✅ FAST: Enhanced loadRoles với caching
     */
    private void loadRolesWithCache() {
        if (roleMap.isEmpty()) {
            try {
                List<UserRole> roles = roleDAO.findAll();
                for (UserRole role : roles) {
                    roleMap.put(role.getRole_id(), role.getName_role());
                }
            } catch (Exception e) {
                System.err.println("Load roles error: " + e.getMessage());
            }
        }
    }

    /**
     * ✅ FAST: Enhanced fillToTable với smart caching và search support
     */
    private void fillToTableWithCache() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                // Use cache if valid
                if (!isCacheValid || employeeCache.isEmpty()) {
                    employeeCache = userDAO.findAll();
                    isCacheValid = true;
                    System.out.println("✅ Loaded " + employeeCache.size() + " employees to cache");
                }
                
                // Check if there's an active search
                String currentSearch = getCurrentSearchKeyword();
                if (!currentSearch.isEmpty()) {
                    // Apply current search filter
                    filterEmployeesByName(currentSearch);
                } else {
                    // Fast table population (show all)
                    populateTableFromCache();
                }
                
            } catch (Exception e) {
                System.err.println("Fill table error: " + e.getMessage());
                XDialog.alert("Lỗi load dữ liệu: " + e.getMessage());
            }
        });
    }

    /**
     * ✅ FAST: Populate table from cache
     */
    private void populateTableFromCache() {
        DefaultTableModel model = (DefaultTableModel) tableInfo.getModel();
        model.setRowCount(0);

        // Không filter nữa, hiển thị tất cả
        for (UserAccount emp : employeeCache) {
            model.addRow(createRowData(emp));
        }
    }

    /**
     * ✅ FAST: Filter matching logic
     */
    private boolean matchesFilters(UserAccount emp, String status, String role) {
        // Status filter
        if (status != null && !status.equals("Tất cả")) {
            if (status.equals("Hoạt động") && (emp.getIs_enabled() != 1)) {
                return false;
            }
            if (status.equals("Không hoạt động") && (emp.getIs_enabled() != 0)) {
                return false;
            }
        }

        // Role filter
        if (role != null && !role.equals("Tất cả")) {
            String roleId = role.contains(" - ") ? role.split(" - ")[0] : role;
            if (!roleId.equals(emp.getRole_id())) {
                return false;
            }
        }

        return true;
    }

    /**
     * ✅ FAST: Create row data
     */
    private Object[] createRowData(UserAccount emp) {
        return new Object[]{
            emp.getUser_id(),
            emp.getUsername(),
            emp.getPass(),
            emp.getFullName(),
            emp.getGender() != null ? (emp.getGender() == 1 ? "Nam" : "Nữ") : "N/A",
            emp.getPhone_number(),
            emp.getEmail(),
            emp.getIs_enabled() == 1 ? "Hoạt động" : "Không hoạt động",
            roleMap.getOrDefault(emp.getRole_id(), "N/A"),
            formatDateFast(emp.getCreated_date())
        };
    }

    /**
     * ✅ OPTIMIZED: Fast date formatting
     */
    private String formatDateFast(java.util.Date date) {
        return date == null ? "N/A" : new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
    }

    /**
     * ✅ OPTIMIZED: Enhanced edit với cache lookup và protection
     */
    private void editWithCache() {
        if (isProcessingEdit) return; // ✅ PROTECT: Tránh multiple calls
        
        int selectedRow = tableInfo.getSelectedRow();
        if (selectedRow < 0) {
            XDialog.alert("Vui lòng chọn một dòng để chỉnh sửa!");
            return;
        }
        
        // ✅ OPTIMIZE: Skip nếu click cùng row liên tục
        if (selectedRow == lastSelectedRow) {
            return;
        }
        
        isProcessingEdit = true; // ✅ LOCK: Set flag
        lastSelectedRow = selectedRow; // ✅ TRACK: Remember selected row

        String userId = (String) tableInfo.getValueAt(selectedRow, 0);

        // Try cache first (much faster)
        UserAccount entity = null;
        for (UserAccount emp : employeeCache) {
            if (userId.equals(emp.getUser_id())) {
                entity = emp;
                break;
            }
        }

        // Fallback to database if not in cache
        if (entity == null) {
            entity = userDAO.findById(userId);
        }

        if (entity != null) {
            final UserAccount finalEntity = entity; // ✅ FIX: Make final for lambda
            // ✅ SAFE: Run on EDT để tránh layout issues
            javax.swing.SwingUtilities.invokeLater(() -> {
                try {
                    setForm(finalEntity);
                    txtIdEmployee.setEditable(false);
                    setAllFieldsEditable(true);
                } finally {
                    isProcessingEdit = false; // ✅ UNLOCK: Release flag
                }
            });
        } else {
            XDialog.alert("Không tìm thấy thông tin nhân viên!");
            isProcessingEdit = false; // ✅ UNLOCK: Release flag
        }
    }

    /**
     * ✅ FAST: Enhanced image loading
     */
    private void loadImageFast(String imageName) {
        if (imageName == null || imageName.trim().isEmpty()) {
            setDefaultImageFast();
            return;
        }

        // Try multiple paths quickly
        String[] paths = {
            "/icons_and_images/imageEmployee/" + imageName,
                "/icons_and_images/" + imageName
        };

        for (String path : paths) {
            try {
                if (getClass().getResource(path) != null) {
                    XImage.setImageToLabel(lblImage, path);
                    lblImage.setText("");
                    return;
                }
            } catch (Exception e) {
                // Continue to next path
            }
        }

        // Fallback
        setPlaceholderImageFast(imageName);
    }

    /**
     * ✅ FAST: Default and placeholder images
     */
    private void setDefaultImageFast() {
        try {
            XImage.setImageToLabel(lblImage, "/icons_and_images/User.png");
            lblImage.setText("");
        } catch (Exception e) {
            lblImage.setIcon(null);
            lblImage.setText("No Image");
        }
    }

    private void setPlaceholderImageFast(String imageName) {
        try {
            XImage.setImageToLabel(lblImage, "/icons_and_images/Unknown person.png");
            lblImage.setText("");
        } catch (Exception e) {
            lblImage.setIcon(null);
            lblImage.setText(imageName);
        }
    }

    /**
     * ✅ CACHE: Invalidate cache after CRUD operations
     */
    private void invalidateCache() {
        isCacheValid = false;
        employeeCache.clear();
    }

    /**
     * ✅ OPTIMIZED: Enhanced create với cache management
     */
    private void createWithCache() {
        if (isProcessing) return;
        isProcessing = true;

        try {
            if (isFormEmpty()) {
                XDialog.alert("⚠️ Vui lòng nhập thông tin nhân viên!");
                return;
            }

            validateEmployee();
            UserAccount newEmployee = getForm();
            validateUniqueEmployeeId(newEmployee.getUser_id());
            validateUniqueUsername(newEmployee.getUsername());
            validateBusinessRules(newEmployee);

            // Create in database
            userDAO.create(newEmployee);

            // Invalidate cache and refresh
            invalidateCache();
            fillToTableWithCache();
            clearForNewEntry();

            XDialog.alert("✅ Tạo nhân viên thành công!\nMã: " + newEmployee.getUser_id());

        } catch (RuntimeException e) {
            XDialog.alert("❌ " + e.getMessage());
        } catch (Exception e) {
            XDialog.alert("❌ Lỗi hệ thống: " + e.getMessage());
            e.printStackTrace();
        } finally {
            isProcessing = false;
        }
    }

    /**
     * ✅ OPTIMIZED: Enhanced update với cache management
     */
    private void updateWithCache() {
        if (isProcessing) return;
        isProcessing = true;

        try {
            validateEmployee();
            UserAccount updatedEmployee = getForm();

            UserAccount existingEmployee = userDAO.findById(updatedEmployee.getUser_id());
            if (existingEmployee == null) {
                XDialog.alert("Không tìm thấy nhân viên với mã: " + updatedEmployee.getUser_id());
                return;
            }

            // Validate username không trùng với nhân viên khác
            validateUniqueUsernameForUpdate(updatedEmployee.getUsername(), updatedEmployee.getUser_id());
            validateBusinessRules(updatedEmployee);

            // Preserve important fields
            updatedEmployee.setCreated_date(existingEmployee.getCreated_date());
            if (updatedEmployee.getImage() == null || 
                updatedEmployee.getImage().equals("default.jpg") ||
                updatedEmployee.getImage().trim().isEmpty()) {
                updatedEmployee.setImage(existingEmployee.getImage());
            }

            // Update in database
            userDAO.update(updatedEmployee);

            // Invalidate cache and refresh
            invalidateCache();
            fillToTableWithCache();
            clearFormButKeepImage();

            XDialog.alert("✅ Cập nhật nhân viên thành công!");

        } catch (RuntimeException e) {
            XDialog.alert("❌ " + e.getMessage());
        } catch (Exception e) {
            XDialog.alert("❌ Lỗi khi cập nhật: " + e.getMessage());
            e.printStackTrace();
        } finally {
            isProcessing = false;
        }
    }

    /**
     * ✅ DEBOUNCED: Filter với debouncing để tránh lag - updated for search support
     */
    private void performFilterAndFill() {
        // Check if there's an active search
        String currentSearch = getCurrentSearchKeyword();
        if (!currentSearch.isEmpty()) {
            // Apply search filter instead of normal filter
            if (debounceTimer != null && debounceTimer.isRunning()) {
                debounceTimer.restart();
            } else {
                filterEmployeesByName(currentSearch);
            }
        } else {
            // Normal table population
            if (debounceTimer != null && debounceTimer.isRunning()) {
                debounceTimer.restart();
            } else {
                populateTableFromCache();
            }
        }
    }

    /**
     * ✅ INITIALIZE: Call this in constructor to setup performance
     */
    private void setupPerformanceOptimizations() {
        initializePerformanceCache();
        loadRolesWithCache();
        
        // Không cần filtering nữa vì txtStatus và txtRole chỉ để hiển thị
        // txtStatus.addActionListener(e -> debounceTimer.restart());
        // txtRole.addActionListener(e -> debounceTimer.restart());
    }

    /**
     * ✅ PRODUCTION: Load image instantly (thay thế testLoadImage)
     */
    private void preloadDefaultImages() {
        // Preload các ảnh mặc định để performance tốt hơn
        javax.swing.SwingUtilities.invokeLater(() -> {
            setDefaultImageFast();
        });
    }

    /**
     * ✅ OPTIMIZED: Validate employee (required by EmployeeController)
     */
    @Override
    public void validateEmployee() {
        // Early return nếu form trống
        if (isBlank(txtIdEmployee.getText()) && isBlank(txtNameAccount.getText()) &&
            isBlank(txtPassword.getText()) && isBlank(txtNameEmployee.getText())) return;
        
        // Validate các trường bắt buộc - consolidated
        String[] fields = {txtIdEmployee.getText(), txtNameAccount.getText(), 
                          txtPassword.getText(), txtNameEmployee.getText()};
        String[] fieldNames = {"Mã nhân viên", "Tên đăng nhập", "Mật khẩu", "Họ tên nhân viên"};
        
        for (int i = 0; i < fields.length; i++) {
            if (isBlank(fields[i])) 
                throw new RuntimeException(fieldNames[i] + " không được để trống!");
        }
        
        // Validate username format
        validateUsernameFormat(txtNameAccount.getText().trim());
        
        // Validate giới tính
        if (!chkMale.isSelected() && !chkFemale.isSelected()) 
            throw new RuntimeException("Vui lòng chọn giới tính!");
    }

    /**
     * Validate business rules (phone + email format)
     */
    private void validateBusinessRules(UserAccount employee) {
        // 1. Validate số điện thoại
        validatePhoneNumberFormat(employee.getPhone_number());
        validateEmailFormat(employee.getEmail());
    }

    /**
     * Phone validation với đầu số Việt Nam
     */
    private void validatePhoneNumberFormat(String phone) {
        if (phone == null || phone.trim().isEmpty()) 
            throw new RuntimeException("Số điện thoại không được để trống!");
        
        String cleanPhone = phone.trim();
        
        // Trường hợp 1: Số quốc tế +84
        if (cleanPhone.startsWith("+84")) {
            if (cleanPhone.length() < 12 || cleanPhone.length() > 13) 
                throw new RuntimeException("Số điện thoại +84 phải có 12-13 số (VD: +84901234567)!");
            
            String numberPart = cleanPhone.substring(3);
            if (!numberPart.matches("\\d+")) 
                throw new RuntimeException("Số điện thoại chỉ được chứa số sau +84!");
            
            // Check đầu số Việt Nam hợp lệ
            if (numberPart.length() >= 2) {
                String prefix = numberPart.substring(0, 2);
                if (!isValidVietnamesePrefix(prefix)) 
                    throw new RuntimeException("Đầu số " + prefix + " không hợp lệ cho điện thoại Việt Nam!");
            }
        }
        // Trường hợp 2: Số nội địa 0x
        else {
            if (cleanPhone.length() != 10) 
                throw new RuntimeException("Số điện thoại phải có đúng 10 số (VD: 0901234567)!");
            
            if (!cleanPhone.matches("\\d+")) 
                throw new RuntimeException("Số điện thoại chỉ được chứa các chữ số!");
            
            if (!cleanPhone.startsWith("0")) 
                throw new RuntimeException("Số điện thoại phải bắt đầu bằng số 0!");
            
            // Check đầu số Việt Nam
            String prefix = cleanPhone.substring(1, 3); // Lấy 2 số sau 0
            if (!isValidVietnamesePrefix(prefix)) 
                throw new RuntimeException("Đầu số " + cleanPhone.substring(0, 3) + " không hợp lệ!");
        }
    }

    /**
     * Đầu số điện thoại Việt Nam hợp lệ (2024)
     */
    private boolean isValidVietnamesePrefix(String prefix) {
        String[] validPrefixes = {
            // Viettel
            "96", "97", "98", "32", "33", "34", "35", "36", "37", "38", "39",
            // MobiFone  
            "90", "93", "70", "79", "77", "76", "78", "89",
            // VinaPhone
            "91", "94", "83", "84", "85", "81", "82", "88",
            // Vietnamobile
            "92", "56", "58", "99",
            // Gmobile & Others
            "87", "86"
        };
        
        for (String valid : validPrefixes) {
            if (prefix.equals(valid)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Email validation - chỉ cho phép gmail.com và fivec.com
     */
    private void validateEmailFormat(String email) {
        if (email == null || email.trim().isEmpty()) 
            throw new RuntimeException("Email không được để trống!");
        
        String cleanEmail = email.trim().toLowerCase();
        
        // Basic format check
        if (!cleanEmail.contains("@")) 
            throw new RuntimeException("Email phải chứa ký tự @!");
        
        String[] parts = cleanEmail.split("@");
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) 
            throw new RuntimeException("Email không đúng định dạng!");
        
        String localPart = parts[0];
        String domain = parts[1];
        
        // Check local part format
        if (!localPart.matches("[a-zA-Z0-9._-]+")) 
            throw new RuntimeException("Phần trước @ chỉ được chứa chữ, số, dấu chấm, gạch dưới và gạch ngang!");
        
        if (localPart.length() < 1 || localPart.length() > 50) 
            throw new RuntimeException("Phần trước @ phải có độ dài từ 1-50 ký tự!");
        
        // Chỉ cho phép domain cụ thể
        if (!domain.equals("gmail.com") && !domain.equals("fivec.com")) 
            throw new RuntimeException("Email chỉ được sử dụng domain @gmail.com hoặc @fivec.com!");
    }

    /**
     * ✅ VALIDATION: Check ID trùng lặp
     */
    private void validateUniqueEmployeeId(String userId) {
        try {
            UserAccount existing = userDAO.findById(userId);
            if (existing != null) 
                throw new RuntimeException("Mã nhân viên '" + userId + "' đã tồn tại!");
        } catch (RuntimeException e) {
            throw e; // Re-throw validation errors
        } catch (Exception e) {
            throw new RuntimeException("Lỗi kiểm tra dữ liệu: " + e.getMessage());
        }
    }

    /**
     * ✅ VALIDATION: Check username trùng lặp (for CREATE) - optimized with cache
     */
    private void validateUniqueUsername(String username) {
        try {
            // Sử dụng cache để tối ưu hiệu suất
            List<UserAccount> allEmployees = getEmployeesFromCacheOrDB();
            
            for (UserAccount emp : allEmployees) {
                if (emp.getUsername() != null && emp.getUsername().equalsIgnoreCase(username.trim())) 
                    throw new RuntimeException("Tên đăng nhập '" + username + "' đã được sử dụng!");
            }
            
        } catch (RuntimeException e) {
            throw e; // Re-throw validation errors
        } catch (Exception e) {
            throw new RuntimeException("Lỗi kiểm tra tên đăng nhập: " + e.getMessage());
        }
    }

    /**
     * ✅ VALIDATION: Check username trùng lặp (for UPDATE) - optimized with cache
     */
    private void validateUniqueUsernameForUpdate(String username, String currentUserId) {
        try {
            // Sử dụng cache để tối ưu hiệu suất  
            List<UserAccount> allEmployees = getEmployeesFromCacheOrDB();
            
            for (UserAccount emp : allEmployees) {
                if (emp.getUsername() != null && 
                    emp.getUsername().equalsIgnoreCase(username.trim()) &&
                    !emp.getUser_id().equals(currentUserId)) // Loại trừ chính nhân viên đang sửa
                    throw new RuntimeException("Tên đăng nhập '" + username + "' đã được sử dụng bởi nhân viên khác!");
            }
            
        } catch (RuntimeException e) {
            throw e; // Re-throw validation errors
        } catch (Exception e) {
            throw new RuntimeException("Lỗi kiểm tra tên đăng nhập: " + e.getMessage());
        }
    }

    /**
     * ✅ OPTIMIZED: Get employees from cache hoặc DB nếu cache empty
     */
    private List<UserAccount> getEmployeesFromCacheOrDB() {
        return (employeeCache != null && !employeeCache.isEmpty() && isCacheValid) ? 
            employeeCache : userDAO.findAll();
    }

    /**
     * ✅ VALIDATION: Validate username format và quy tắc
     */
    private void validateUsernameFormat(String username) {
        if (username == null || username.trim().isEmpty()) 
            throw new RuntimeException("Tên đăng nhập không được để trống!");
        
        String cleanUsername = username.trim();
        
        // 1. Kiểm tra độ dài
        if (cleanUsername.length() < 3) 
            throw new RuntimeException("Tên đăng nhập phải có ít nhất 3 ký tự!");
        
        if (cleanUsername.length() > 20) 
            throw new RuntimeException("Tên đăng nhập không được vượt quá 20 ký tự!");
        
        // 2. Kiểm tra ký tự hợp lệ (chữ, số, dấu gạch dưới, chấm)
        if (!cleanUsername.matches("^[a-zA-Z0-9._]+$")) 
            throw new RuntimeException("Tên đăng nhập chỉ được chứa chữ cái, số, dấu chấm (.) và gạch dưới (_)!");
        
        // 3. Không được bắt đầu bằng số
        if (Character.isDigit(cleanUsername.charAt(0))) 
            throw new RuntimeException("Tên đăng nhập không được bắt đầu bằng số!");
        
        // 4. Không được bắt đầu hoặc kết thúc bằng dấu chấm hoặc gạch dưới
        if (cleanUsername.startsWith(".") || cleanUsername.startsWith("_") ||
            cleanUsername.endsWith(".") || cleanUsername.endsWith("_")) 
            throw new RuntimeException("Tên đăng nhập không được bắt đầu hoặc kết thúc bằng dấu chấm (.) hoặc gạch dưới (_)!");
        
        // 5. Không được có 2 dấu chấm hoặc gạch dưới liên tiếp
        if (cleanUsername.contains("..") || cleanUsername.contains("__") || 
            cleanUsername.contains("._") || cleanUsername.contains("_.")) 
            throw new RuntimeException("Tên đăng nhập không được có các ký tự đặc biệt liên tiếp!");
        
        // 6. Kiểm tra từ khóa bị cấm
        String[] forbiddenWords = {"admin", "root", "administrator", "system", "test", "demo", "guest"};
        for (String forbidden : forbiddenWords) {
            if (cleanUsername.toLowerCase().contains(forbidden)) 
                throw new RuntimeException("Tên đăng nhập không được chứa từ khóa: " + forbidden);
        }
    }

    // =============================================================================
    // IMAGE SELECTION FUNCTIONALITY - CHỌN ẢNH CHO NHÂN VIÊN
    // =============================================================================
    
    /**
     * ✅ SETUP: Initialize image selection functionality
     */
    private void setupImageSelection() {
        // Add mouse listener to lblImage for image selection
        lblImage.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    selectEmployeeImage();
                }
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Change cursor to hand when hovering
                lblImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                // Add tooltip
                lblImage.setToolTipText("Click để chọn ảnh nhân viên");
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Reset cursor
                lblImage.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        });
        
        // Set border to indicate clickable area
        lblImage.setBorder(javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 149, 237), 2),
            "Click để chọn ảnh",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.BOTTOM,
            new java.awt.Font("Arial", java.awt.Font.ITALIC, 10),
            new java.awt.Color(100, 149, 237)
        ));
    }
    
    /**
     * ✅ IMAGE SELECTION: Open file chooser to select employee image
     */
    private void selectEmployeeImage() {
        try {
            // Create file chooser
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            
            // Set file filter for images
            javax.swing.filechooser.FileNameExtensionFilter imageFilter = 
                new javax.swing.filechooser.FileNameExtensionFilter(
                    "Image Files (*.jpg, *.jpeg, *.png, *.gif)", 
                    "jpg", "jpeg", "png", "gif"
                );
            fileChooser.setFileFilter(imageFilter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            
            // Set dialog title
            fileChooser.setDialogTitle("Chọn ảnh nhân viên");
            
            // Set default directory (Desktop hoặc Pictures)
            try {
                java.io.File userHome = new java.io.File(System.getProperty("user.home"));
                java.io.File picturesDir = new java.io.File(userHome, "Pictures");
                if (picturesDir.exists()) {
                    fileChooser.setCurrentDirectory(picturesDir);
                } else {
                    java.io.File desktopDir = new java.io.File(userHome, "Desktop");
                    if (desktopDir.exists()) {
                        fileChooser.setCurrentDirectory(desktopDir);
                    }
                }
            } catch (Exception e) {
                // Use default directory
            }
            
            // Show dialog
            int result = fileChooser.showOpenDialog(this);
            
            if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                java.io.File selectedFile = fileChooser.getSelectedFile();
                
                // Validate file
                if (validateImageFile(selectedFile)) {
                    // Process and save image
                    String savedImageName = processSelectedImage(selectedFile);
                    
                    if (savedImageName != null) {
                        // Load and display the new image
                        loadEmployeeImage(savedImageName);
                        
                        // Show success message
                        XDialog.alert(
                            "✅ Đã chọn ảnh thành công!\n" +
                            "File: " + savedImageName,
                            "Thông báo"
                        );
                    }
                }
            }
            
        } catch (Exception e) {
            XDialog.alert(
                "❌ Lỗi khi chọn ảnh: " + e.getMessage(),
                "Lỗi"
            );
            e.printStackTrace();
        }
    }
    
    /**
     * ✅ VALIDATION: Validate selected image file
     */
    private boolean validateImageFile(java.io.File file) {
        try {
            // Check file exists
            if (file == null || !file.exists()) {
                XDialog.alert("File không tồn tại!", "Lỗi");
                return false;
            }
            
            // Check file size (max 5MB)
            long fileSizeInMB = file.length() / (1024 * 1024);
            if (fileSizeInMB > 5) {
                XDialog.alert(
                    "File ảnh quá lớn!\n" +
                    "Kích thước hiện tại: " + fileSizeInMB + "MB\n" +
                    "Kích thước tối đa: 5MB",
                    "Lỗi"
                );
                return false;
            }
            
            // Check file extension
            String fileName = file.getName().toLowerCase();
            if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg") && 
                !fileName.endsWith(".png") && !fileName.endsWith(".gif")) {
                XDialog.alert(
                    "Định dạng file không được hỗ trợ!\n" +
                    "Chỉ chấp nhận: .jpg, .jpeg, .png, .gif",
                    "Lỗi"
                );
                return false;
            }
            
            // Try to read as image
            java.awt.image.BufferedImage image = javax.imageio.ImageIO.read(file);
            if (image == null) {
                XDialog.alert("File không phải là ảnh hợp lệ!", "Lỗi");
                return false;
            }
            
            return true;
            
        } catch (Exception e) {
            XDialog.alert("Lỗi kiểm tra file: " + e.getMessage(), "Lỗi");
            return false;
        }
    }
    
    /**
     * ✅ PROCESSING: Process and save selected image
     */
    private String processSelectedImage(java.io.File sourceFile) {
        try {
            // Generate unique filename
            String fileExtension = getFileExtension(sourceFile.getName());
            String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String newFileName = "emp_" + timestamp + "." + fileExtension;
            
            // Create target directory in resources (for development)
            // Note: In production, you might want to save to external directory
            String resourcePath = "src/main/resources/icons_and_images/imageEmployee/";
            java.io.File targetDir = new java.io.File(resourcePath);
            
            if (!targetDir.exists()) {
                boolean created = targetDir.mkdirs();
                if (!created) {
                    // Try alternative path
                    targetDir = new java.io.File("resources/icons_and_images/imageEmployee/");
                    if (!targetDir.exists()) {
                        targetDir.mkdirs();
                    }
                }
            }
            
            // Target file
            java.io.File targetFile = new java.io.File(targetDir, newFileName);
            
            // Read and resize image if needed
            java.awt.image.BufferedImage originalImage = javax.imageio.ImageIO.read(sourceFile);
            java.awt.image.BufferedImage processedImage = resizeImageIfNeeded(originalImage);
            
            // Save processed image
            javax.imageio.ImageIO.write(processedImage, fileExtension, targetFile);
            
            System.out.println("✅ Saved image: " + targetFile.getAbsolutePath());
            return newFileName;
            
        } catch (Exception e) {
            XDialog.alert("❌ Lỗi lưu ảnh: " + e.getMessage(), "Lỗi");
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * ✅ RESIZE: Resize image if too large (maintain aspect ratio)
     */
    private java.awt.image.BufferedImage resizeImageIfNeeded(java.awt.image.BufferedImage originalImage) {
        int maxWidth = 300;
        int maxHeight = 300;
        
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        
        // Check if resize needed
        if (originalWidth <= maxWidth && originalHeight <= maxHeight) {
            return originalImage; // No resize needed
        }
        
        // Calculate new dimensions (maintain aspect ratio)
        double widthRatio = (double) maxWidth / originalWidth;
        double heightRatio = (double) maxHeight / originalHeight;
        double ratio = Math.min(widthRatio, heightRatio);
        
        int newWidth = (int) (originalWidth * ratio);
        int newHeight = (int) (originalHeight * ratio);
        
        // Create resized image
        java.awt.image.BufferedImage resizedImage = new java.awt.image.BufferedImage(
            newWidth, newHeight, java.awt.image.BufferedImage.TYPE_INT_RGB
        );
        
        java.awt.Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, 
                            java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        
        System.out.println("📏 Resized image: " + originalWidth + "x" + originalHeight + 
                          " -> " + newWidth + "x" + newHeight);
        
        return resizedImage;
    }
    
    /**
     * ✅ OPTIMIZED: Get file extension
     */
    private String getFileExtension(String fileName) {
        return (fileName != null && fileName.contains(".")) ? 
            fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase() : "jpg";
    }
    
    /**
     * ✅ FROZEN LAYOUT: Enhanced setForm to preserve size and prevent layout changes
     */
    @Override
    public void setForm(UserAccount entity) {
        if (entity == null) return;
        
        try {
            // ✅ FREEZE: Prevent any layout changes during form update
            freezeLayout();
            setIgnoreRepaint(true);
            
            // ✅ DISABLE: ComboBox repaint to prevent layout triggers
            cboStatus.setIgnoreRepaint(true);
            cboRole.setIgnoreRepaint(true);
            
            // Basic info - lightweight operations only
            txtIdEmployee.setText(entity.getUser_id());
            txtNameAccount.setText(entity.getUsername());
            txtPassword.setText(entity.getPass());
            txtNameEmployee.setText(entity.getFullName());
            txtPhoneNumber.setText(entity.getPhone_number());
            txtEmail.setText(entity.getEmail());

            // Gender handling - optimized
            if (entity.getGender() != null) {
                boolean isMale = entity.getGender() == 1;
                chkMale.setSelected(isMale);
                chkFemale.setSelected(!isMale);
            } else {
                groupGioiTinh.clearSelection();
            }

            // Status và role - silent updates
            setStatusComboBoxSilent(entity.getIs_enabled());
            setRoleComboBoxSilent(entity.getRole_id());

            // Image handling - defer to later
            if (entity.getImage() != null && !entity.getImage().trim().isEmpty()) {
                loadEmployeeImageSilent(entity.getImage());
            } else {
                setDefaultImageSilent();
            }

            // Skip displayRoleInfo to avoid console spam
            
        } finally {
            // ✅ RESTORE: Re-enable layout and maintain frozen size
            setIgnoreRepaint(false);
            
            // ✅ RESTORE: ComboBox repaint
            cboStatus.setIgnoreRepaint(false);
            cboRole.setIgnoreRepaint(false);
            
            // ✅ ENFORCE: Keep table at frozen size
            if (frozenTableSize != null) {
                jScrollPane1.setSize(frozenTableSize);
                jScrollPane1.setPreferredSize(frozenTableSize);
            }
            
            // Keep layout frozen after form update
            repaint();
        }
    }
    
    /**
     * ✅ SILENT: Load image without affecting layout - FIXED SIZE
     */
    private void loadEmployeeImageSilent(String imageName) {
        try {
            if (imageName != null && !imageName.trim().isEmpty()) {
                String imagePath = "/icons_and_images/imageEmployee/" + imageName;
                if (getClass().getResource(imagePath) != null) {
                    // Only update if different
                    if (!imageName.equals(lblImage.getToolTipText())) {
                        setImageWithFixedSize(imagePath);
                        lblImage.setToolTipText(imageName); // Store for comparison
                    }
                } else {
                    setDefaultImageSilent();
                }
            } else {
                setDefaultImageSilent();
            }
        } catch (Exception e) {
            // Silent fail
        }
    }
    
    /**
     * ✅ SILENT: Set default image without layout changes - FIXED SIZE
     */
    private void setDefaultImageSilent() {
        try {
            if (!"default".equals(lblImage.getToolTipText())) {
                setImageWithFixedSize("/icons_and_images/User.png");
                lblImage.setToolTipText("default"); // Mark as default
            }
        } catch (Exception e) {
            lblImage.setIcon(null);
            lblImage.setText("No Image");
            lblImage.setToolTipText("error");
            
            // ✅ ENFORCE: Keep size even on error
            if (originalImageSize != null) {
                lblImage.setSize(originalImageSize);
                lblImage.setPreferredSize(originalImageSize);
            }
        }
    }
    
    /**
     * ✅ FIXED SIZE: Set image to label without changing label dimensions
     */
    private void setImageWithFixedSize(String imagePath) {
        try {
            // ✅ SAFETY: Ensure originalImageSize is available
            if (originalImageSize == null) {
                captureInitialImageSize();
                if (originalImageSize == null) {
                    // Ultimate fallback
                    originalImageSize = new java.awt.Dimension(116, 167);
                    System.out.println("⚠️ Using ultimate fallback image size: " + originalImageSize);
                }
            }
            
            // Load and scale image to fit the fixed label size
            java.net.URL imageURL = getClass().getResource(imagePath);
            if (imageURL != null) {
                javax.swing.ImageIcon originalIcon = new javax.swing.ImageIcon(imageURL);
                
                // ✅ VALIDATION: Check if image loaded successfully
                if (originalIcon.getIconWidth() > 0 && originalIcon.getIconHeight() > 0) {
                    // Scale image to fit the original label size
                    java.awt.Image scaledImage = originalIcon.getImage().getScaledInstance(
                        originalImageSize.width, 
                        originalImageSize.height, 
                        java.awt.Image.SCALE_SMOOTH
                    );
                    
                    javax.swing.ImageIcon scaledIcon = new javax.swing.ImageIcon(scaledImage);
                    
                    // Set the scaled icon
                    lblImage.setIcon(scaledIcon);
                    lblImage.setText("");
                } else {
                    // Image không load được
                    lblImage.setIcon(null);
                    lblImage.setText("No Image");
                }
                
                // ✅ ENFORCE: Keep the original size regardless of image content
                lblImage.setSize(originalImageSize);
                lblImage.setPreferredSize(originalImageSize);
                lblImage.setMinimumSize(originalImageSize);
                lblImage.setMaximumSize(originalImageSize);
                
                System.out.println("🖼️ Set image with fixed size: " + originalImageSize + " for path: " + imagePath);
                
            } else {
                // Fallback to text if image not found
                lblImage.setIcon(null);
                lblImage.setText("No Image");
                
                // ✅ STILL ENFORCE: Keep size even when no image
                lblImage.setSize(originalImageSize);
                lblImage.setPreferredSize(originalImageSize);
                lblImage.setMinimumSize(originalImageSize);
                lblImage.setMaximumSize(originalImageSize);
            }
        } catch (Exception e) {
            lblImage.setIcon(null);
            lblImage.setText("Error");
            
            // ✅ ENFORCE: Keep size even on error
            if (originalImageSize != null) {
                lblImage.setSize(originalImageSize);
                lblImage.setPreferredSize(originalImageSize);
                lblImage.setMinimumSize(originalImageSize);
                lblImage.setMaximumSize(originalImageSize);
            }
            
            System.err.println("❌ Error setting image: " + e.getMessage());
        }
    }
    
    // =============================================================================
    // LAYOUT FREEZE PROTECTION
    // =============================================================================
    
    /**
     * ✅ FREEZE: Prevent any layout changes
     */
    private void freezeLayout() {
        if (layoutFrozen) return;
        
        try {
            layoutFrozen = true;
            
            // ✅ DISABLE: Auto-resize capabilities
            if (frozenTableSize != null) {
                jScrollPane1.setPreferredSize(frozenTableSize);
                jScrollPane1.setMinimumSize(frozenTableSize);
                jScrollPane1.setMaximumSize(frozenTableSize);
                tableInfo.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            }
            
            // ✅ LOCK: Window resize
            setResizable(false);
            
        } catch (Exception e) {
            System.err.println("Error freezing layout: " + e.getMessage());
        }
    }
    
    /**
     * ✅ ENFORCE: Force table to stay at frozen size
     */
    private void enforceTableSize() {
        if (frozenTableSize != null && !layoutFrozen) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                try {
                    if (!jScrollPane1.getSize().equals(frozenTableSize)) {
                        jScrollPane1.setSize(frozenTableSize);
                        jScrollPane1.setPreferredSize(frozenTableSize);
                        System.out.println("🔒 Enforced table size: " + frozenTableSize);
                    }
                } catch (Exception e) {
                    // Silent fail
                }
            });
        }
    }
    
    /**
     * ✅ SETUP: Periodic size enforcement to prevent table expansion
     */
    private void setupSizeEnforcementTimer() {
        try {
            // Create timer that runs every 500ms to check and enforce table size
            sizeEnforcementTimer = new javax.swing.Timer(500, e -> {
                if (frozenTableSize != null) {
                    java.awt.Dimension currentSize = jScrollPane1.getSize();
                    if (!currentSize.equals(frozenTableSize)) {
                        System.out.println("⚠️ Table size drift detected: " + currentSize + " -> " + frozenTableSize);
                        enforceTableSize();
                    }
                }
            });
            
            sizeEnforcementTimer.setRepeats(true);
            sizeEnforcementTimer.start();
            
            System.out.println("✅ Size enforcement timer started - will prevent table expansion");
            
        } catch (Exception e) {
            System.err.println("Error setting up size enforcement timer: " + e.getMessage());
        }
    }
    

    // =============================================================================
    // COMBOBOX SETUP AND SEARCH FUNCTIONALITY
    // =============================================================================
    
    /**
     * ✅ SETUP: Initialize Status ComboBox với các tùy chọn
     */
    private void setupStatusComboBox() {
        cboStatus.removeAllItems();
        cboStatus.addItem("Hoạt động");
        cboStatus.addItem("Không hoạt động");
        cboStatus.setSelectedIndex(0); // Default: Hoạt động
        
        // ✅ FIX: Set fixed size để không bị tràn layout
        java.awt.Dimension fixedSize = new java.awt.Dimension(150, 25);
        cboStatus.setPreferredSize(fixedSize);
        cboStatus.setMinimumSize(fixedSize);
        cboStatus.setMaximumSize(fixedSize);
    }
    
    /**
     * ✅ SETUP: Initialize Role ComboBox với data từ database
     */
    private void setupRoleComboBox() {
        cboRole.removeAllItems();
        try {
            List<UserRole> roles = roleDAO.findAll();
            for (UserRole role : roles) {
                cboRole.addItem(role.getRole_id() + " - " + role.getName_role());
            }
            if (cboRole.getItemCount() > 0) cboRole.setSelectedIndex(0);
        } catch (Exception e) {
            // Fallback data nếu không load được từ DB
            cboRole.addItem("R001 - Manager");
            cboRole.addItem("R002 - Staff");
            cboRole.setSelectedIndex(1); // Default: Staff
        }
        
        // ✅ FIX: Set fixed size để không bị tràn layout
        java.awt.Dimension fixedSize = new java.awt.Dimension(150, 25);
        cboRole.setPreferredSize(fixedSize);
        cboRole.setMinimumSize(fixedSize);
        cboRole.setMaximumSize(fixedSize);
    }
    
    /**
     * ✅ GETTER: Lấy giá trị Status từ ComboBox (1=Hoạt động, 0=Không hoạt động)
     */
    private Integer getStatusFromComboBox() {
        String selected = (String) cboStatus.getSelectedItem();
        return "Hoạt động".equals(selected) ? 1 : 0;
    }
    
    /**
     * ✅ GETTER: Lấy role_id từ ComboBox (VD: "R001 - Manager" -> "R001")
     */
    private String getRoleIdFromComboBox() {
        String selected = (String) cboRole.getSelectedItem();
        if (selected != null && selected.contains(" - ")) {
            return selected.split(" - ")[0];
        }
        return "R002"; // Fallback: Staff
    }
    
    /**
     * ✅ SETUP: Initialize real-time search functionality
     */
    private void setupSearchFunctionality() {
        // Set placeholder text cho search box
        txtSearch.setText("Tìm theo tên nhân viên...");
        txtSearch.setForeground(java.awt.Color.GRAY);
        
        // Add focus listener để clear placeholder
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtSearch.getText().equals("Tìm theo tên nhân viên...")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(java.awt.Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtSearch.getText().trim().isEmpty()) {
                    txtSearch.setText("Tìm theo tên nhân viên...");
                    txtSearch.setForeground(java.awt.Color.GRAY);
                    // Reset về hiển thị tất cả khi không có từ khóa
                    filterEmployeesByName("");
                }
            }
        });
        
        // Add document listener để search real-time khi gõ
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                performSearch();
            }
            
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                performSearch();
            }
            
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                performSearch();
            }
        });
        
        System.out.println("✅ Setup search functionality - ready to search by employee name!");
    }
    
    /**
     * ✅ SEARCH: Perform search with debouncing
     */
    private void performSearch() {
        // Chỉ search nếu không phải placeholder text
        String searchText = txtSearch.getText();
        if (!searchText.equals("Tìm theo tên nhân viên...")) {
            // Debounce search để tránh lag khi gõ nhanh
            if (debounceTimer != null) debounceTimer.stop();
            
            debounceTimer = new javax.swing.Timer(200, e -> {
                filterEmployeesByName(searchText.trim());
            });
            debounceTimer.setRepeats(false);
            debounceTimer.start();
        }
    }
    
    /**
     * ✅ FILTER: Filter employees by name (basic version)
     */
    private void filterEmployeesByName(String searchKeyword) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                DefaultTableModel model = (DefaultTableModel) tableInfo.getModel();
                model.setRowCount(0);
                
                // Get all employees
                List<UserAccount> employees = userDAO.findAll();
                
                if (searchKeyword.isEmpty()) {
                    // Hiển thị tất cả nếu không có từ khóa
                    for (UserAccount emp : employees) {
                        model.addRow(createRowData(emp));
                    }
                } else {
                    // Filter theo tên
                    for (UserAccount emp : employees) {
                        if (emp.getFullName() != null && 
                            emp.getFullName().toLowerCase().contains(searchKeyword.toLowerCase())) {
                            model.addRow(createRowData(emp));
                        }
                    }
                }
                
            } catch (Exception e) {
                System.err.println("❌ Search error: " + e.getMessage());
            }
        });
    }
    
    /**
     * ✅ OPTIMIZED: Search utility methods
     */
    public void clearSearch() {
        txtSearch.setText("Tìm theo tên nhân viên...");
        txtSearch.setForeground(java.awt.Color.GRAY);
        filterEmployeesByName("");
    }
    
    public String getCurrentSearchKeyword() {
        String text = txtSearch.getText();
        return text.equals("Tìm theo tên nhân viên...") ? "" : text.trim();
    }
    
    /**
     * ✅ ULTRA SILENT: Set Status ComboBox without ANY layout changes
     */
    private void setStatusComboBoxSilent(Integer isEnabled) {
        try {
            // ✅ DISABLE: All events and repaints
            java.awt.event.ActionListener[] listeners = cboStatus.getActionListeners();
            for (java.awt.event.ActionListener listener : listeners) {
                cboStatus.removeActionListener(listener);
            }
            
            // ✅ FREEZE: Current size before change
            java.awt.Dimension currentSize = cboStatus.getSize();
            cboStatus.setIgnoreRepaint(true);
            
            if (isEnabled != null) {
                String targetValue = isEnabled == 1 ? "Hoạt động" : "Không hoạt động";
                if (!targetValue.equals(cboStatus.getSelectedItem())) {
                    cboStatus.setSelectedItem(targetValue);
                }
            } else {
                if (cboStatus.getSelectedIndex() != 0) {
                    cboStatus.setSelectedIndex(0);
                }
            }
            
            // ✅ ENFORCE: Restore exact size
            cboStatus.setSize(currentSize);
            cboStatus.setPreferredSize(currentSize);
            
            // ✅ RESTORE: Events and repaint
            cboStatus.setIgnoreRepaint(false);
            for (java.awt.event.ActionListener listener : listeners) {
                cboStatus.addActionListener(listener);
            }
        } catch (Exception e) {
            // Silent fail - restore repaint anyway
            cboStatus.setIgnoreRepaint(false);
        }
    }
    
    /**
     * ✅ ULTRA SILENT: Set Role ComboBox without ANY layout changes
     */
    private void setRoleComboBoxSilent(String roleId) {
        try {
            // ✅ DISABLE: All events and repaints
            java.awt.event.ActionListener[] listeners = cboRole.getActionListeners();
            for (java.awt.event.ActionListener listener : listeners) {
                cboRole.removeActionListener(listener);
            }
            
            // ✅ FREEZE: Current size before change
            java.awt.Dimension currentSize = cboRole.getSize();
            cboRole.setIgnoreRepaint(true);
            
            if (roleId != null) {
                for (int i = 0; i < cboRole.getItemCount(); i++) {
                    String item = cboRole.getItemAt(i);
                    if (item.startsWith(roleId + " - ")) {
                        if (cboRole.getSelectedIndex() != i) {
                            cboRole.setSelectedIndex(i);
                        }
                        break;
                    }
                }
            } else {
                if (cboRole.getItemCount() > 0 && cboRole.getSelectedIndex() != 0) {
                    cboRole.setSelectedIndex(0);
                }
            }
            
            // ✅ ENFORCE: Restore exact size
            cboRole.setSize(currentSize);
            cboRole.setPreferredSize(currentSize);
            
            // ✅ RESTORE: Events and repaint
            cboRole.setIgnoreRepaint(false);
            for (java.awt.event.ActionListener listener : listeners) {
                cboRole.addActionListener(listener);
            }
        } catch (Exception e) {
            // Silent fail - restore repaint anyway
            cboRole.setIgnoreRepaint(false);
        }
    }

}
