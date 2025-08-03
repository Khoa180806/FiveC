/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.team4.quanliquanmicay.View.Dialogs;

import com.team4.quanliquanmicay.util.XTheme;
import com.team4.quanliquanmicay.Controller.CustomerManagementController;
import com.team4.quanliquanmicay.Entity.Customer;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *
 * @author Asus
 */
public class CustomerManagementJDialog extends javax.swing.JFrame {

    private CustomerManagementController customerController;
    private DefaultTableModel tableModel;
    private Customer selectedCustomer; // Store currently selected customer
    
    /**
     * Creates new form CustomerManagementJDialog
     */
    public CustomerManagementJDialog() {
        this.setUndecorated(true);
        XTheme.applyFullTheme();
        initComponents();
        this.setLocationRelativeTo(null);
        
        // Initialize controller and table model
        customerController = new CustomerManagementController();
        initializeTable();
        setupEventListeners();
        loadAllCustomers();
    }
    
    /**
     * Initialize the table with proper column headers
     */
    private void initializeTable() {
        String[] columnNames = {"Số điện thoại", "Tên", "Điểm", "Hạng"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        tbl_customer.setModel(tableModel);
    }
    
    /**
     * Setup event listeners for search and sort functionality
     */
    private void setupEventListeners() {
        // Search functionality - search as user types
        txt_search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                refreshTableData();
            }
        });
        
        // Sort functionality - sort when combo box selection changes
        cbo_SortPoint.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    refreshTableData();
                }
            }
        });
        
        // Table row selection functionality
        tbl_customer.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tbl_customer.getSelectedRow();
                if (selectedRow >= 0 && tableModel.getRowCount() > 0) {
                    displaySelectedCustomerData(selectedRow);
                }
            }
        });
        
        // Update button functionality
        btn_update.addActionListener(e -> updateCustomerData());
        
        // Delete button functionality
        btn_delete.addActionListener(e -> deleteCustomerData());
        
        // Exit button functionality
        btn_exit.addActionListener(e -> dispose());
    }
    
    /**
     * Load all customers into the table
     */
    private void loadAllCustomers() {
        List<Customer> customers = customerController.getAllCustomers();
        if (customers != null) {
            updateTableData(customers);
        }
    }
    

    
    /**
     * Update table data with customer list
     */
    private void updateTableData(List<Customer> customers) {
        tableModel.setRowCount(0); // Clear existing data
        
        for (Customer customer : customers) {
            Object[] row = {
                customer.getPhone_number(),
                customer.getCustomer_name(),
                customer.getPoint_level(),
                customer.getLevel_ranking()
            };
            tableModel.addRow(row);
        }
    }
    
    /**
     * Display selected customer data in text fields
     */
    private void displaySelectedCustomerData(int selectedRow) {
        try {
            // Validate row index
            if (selectedRow < 0 || selectedRow >= tableModel.getRowCount()) {
                return;
            }
            
            // Get data from the selected row with null checks
            Object phoneNumberObj = tableModel.getValueAt(selectedRow, 0);
            Object customerNameObj = tableModel.getValueAt(selectedRow, 1);
            Object pointLevelObj = tableModel.getValueAt(selectedRow, 2);
            Object levelRankingObj = tableModel.getValueAt(selectedRow, 3);
            
            // Validate data is not null
            if (phoneNumberObj == null || customerNameObj == null || 
                pointLevelObj == null || levelRankingObj == null) {
                return;
            }
            
            String phoneNumber = phoneNumberObj.toString();
            String customerName = customerNameObj.toString();
            Integer pointLevel = (Integer) pointLevelObj;
            String levelRanking = levelRankingObj.toString();
            
            // Create customer object for the selected row
            selectedCustomer = new Customer();
            selectedCustomer.setPhone_number(phoneNumber);
            selectedCustomer.setCustomer_name(customerName);
            selectedCustomer.setPoint_level(pointLevel);
            selectedCustomer.setLevel_ranking(levelRanking);
            
            // Display data in text fields
            txt_phone_number.setText(phoneNumber);
            txt_customer_name.setText(customerName);
            txt_point_level.setText(String.valueOf(pointLevel));
            txt_level_ranking.setText(levelRanking);
            
        } catch (Exception e) {
            // Log error and clear form
            System.err.println("Error displaying customer data: " + e.getMessage());
            clearForm();
        }
    }
    
    /**
     * Clear form fields
     */
    private void clearForm() {
        selectedCustomer = null;
        txt_phone_number.setText("");
        txt_customer_name.setText("");
        txt_point_level.setText("");
        txt_level_ranking.setText("");
    }
    
    /**
     * Validate phone number format
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        // Vietnamese phone number format: 10-11 digits starting with 0
        return phoneNumber.matches("^0[0-9]{9,10}$");
    }
    
    /**
     * Update customer data from text fields
     */
    private void updateCustomerData() {
        if (selectedCustomer == null) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn một khách hàng để cập nhật!", 
                "Thông báo", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Get updated data from text fields
            String phoneNumber = txt_phone_number.getText().trim();
            String customerName = txt_customer_name.getText().trim();
            String pointLevelStr = txt_point_level.getText().trim();
            String levelRanking = txt_level_ranking.getText().trim();
            
            // Validate input
            if (phoneNumber.isEmpty() || customerName.isEmpty() || pointLevelStr.isEmpty() || levelRanking.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, 
                    "Vui lòng điền đầy đủ thông tin!", 
                    "Lỗi", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validate phone number format
            if (!isValidPhoneNumber(phoneNumber)) {
                javax.swing.JOptionPane.showMessageDialog(this, 
                    "Số điện thoại không hợp lệ! (Định dạng: 0xxxxxxxxx)", 
                    "Lỗi", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validate point level is a number
            int pointLevel;
            try {
                pointLevel = Integer.parseInt(pointLevelStr);
                if (pointLevel < 0) {
                    javax.swing.JOptionPane.showMessageDialog(this, 
                        "Điểm phải là số không âm!", 
                        "Lỗi", 
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(this, 
                    "Điểm phải là số!", 
                    "Lỗi", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Update customer object
            selectedCustomer.setPhone_number(phoneNumber);
            selectedCustomer.setCustomer_name(customerName);
            selectedCustomer.setPoint_level(pointLevel);
            selectedCustomer.setLevel_ranking(levelRanking);
            
            // Update in database
            customerController.setForm(selectedCustomer);
            customerController.update();
            
            // Refresh table data
            refreshTableData();
            
            // Clear form
            clearForm();
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Lỗi khi cập nhật: " + e.getMessage(), 
                "Lỗi", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Delete customer data
     */
    private void deleteCustomerData() {
        if (selectedCustomer == null) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn một khách hàng để xóa!", 
                "Thông báo", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Show confirmation dialog
            int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn xóa khách hàng: " + selectedCustomer.getCustomer_name() + "?", 
                "Xác nhận xóa", 
                javax.swing.JOptionPane.YES_NO_OPTION);
            
            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                // Delete from database
                customerController.setForm(selectedCustomer);
                customerController.delete();
                
                // Refresh table data
                refreshTableData();
                
                // Clear form
                clearForm();
                
                // Show success message
                javax.swing.JOptionPane.showMessageDialog(this, 
                    "Dữ liệu đã được xóa thành công!", 
                    "Thông báo", 
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Lỗi khi xóa: " + e.getMessage(), 
                "Lỗi", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Refresh the table data based on current search and sort
     */
    private void refreshTableData() {
        try {
            String searchText = txt_search.getText().trim();
            String selectedSort = (String) cbo_SortPoint.getSelectedItem();
            
            List<Customer> results = null;
            
            // Get base data
            if (searchText.isEmpty()) {
                results = customerController.getAllCustomers();
            } else {
                // Search by phone number - create a list with single result
                Customer foundCustomer = customerController.findCustomerByPhone(searchText);
                if (foundCustomer != null) {
                    results = new java.util.ArrayList<>();
                    results.add(foundCustomer);
                } else {
                    results = new java.util.ArrayList<>();
                }
            }
            
            // Apply sorting
            if (results != null && selectedSort != null) {
                if ("Tăng dần".equals(selectedSort)) {
                    results.sort((c1, c2) -> Integer.compare(c1.getPoint_level(), c2.getPoint_level()));
                } else if ("Giảm dần".equals(selectedSort)) {
                    results.sort((c1, c2) -> Integer.compare(c2.getPoint_level(), c1.getPoint_level()));
                }
            }
            
            if (results != null) {
                updateTableData(results);
            }
        } catch (Exception e) {
            System.err.println("Error refreshing table data: " + e.getMessage());
            // Load all customers as fallback
            loadAllCustomers();
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

        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_customer = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        btn_exit = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_search = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbo_SortPoint = new javax.swing.JComboBox<>();
        txt_phone_number = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_customer_name = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_point_level = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_level_ranking = new javax.swing.JTextField();
        btn_update = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 164, 133));

        jPanel2.setBackground(new java.awt.Color(134, 39, 43));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("QUẢN LÍ KHÁCH HÀNG");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(86, 86, 86))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        tbl_customer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Số điện thoại", "Tên", "Điểm", "Hạng"
            }
        ));
        jScrollPane1.setViewportView(tbl_customer);

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        btn_exit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Exit.png"))); // NOI18N
        btn_exit.setText("EXIT");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("SỐ ĐIỆN THOẠI :");

        jPanel3.setBackground(new java.awt.Color(204, 164, 133));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tìm kiếm :");

        txt_search.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Lọc :");

        cbo_SortPoint.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cbo_SortPoint.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Giảm dần", "Tăng dần" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbo_SortPoint, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_SortPoint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txt_phone_number.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("TÊN :");

        txt_customer_name.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ĐIỂM :");

        txt_point_level.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("HẠNG :");

        txt_level_ranking.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/edit.png"))); // NOI18N

        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/trash.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(btn_exit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_phone_number, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(77, 77, 77)
                                .addComponent(txt_point_level, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_level_ranking, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_customer_name, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_update, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                            .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_phone_number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_point_level, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_customer_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(txt_level_ranking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_exit)
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

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchActionPerformed

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
            java.util.logging.Logger.getLogger(CustomerManagementJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerManagementJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerManagementJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerManagementJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerManagementJDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_exit;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox<String> cbo_SortPoint;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tbl_customer;
    private javax.swing.JTextField txt_customer_name;
    private javax.swing.JTextField txt_level_ranking;
    private javax.swing.JTextField txt_phone_number;
    private javax.swing.JTextField txt_point_level;
    private javax.swing.JTextField txt_search;
    // End of variables declaration//GEN-END:variables
}
