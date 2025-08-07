/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.team4.quanliquanmicay.View.management;

import com.team4.quanliquanmicay.util.XTheme;
import com.team4.quanliquanmicay.util.XDialog;
import com.team4.quanliquanmicay.util.XValidation;
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
public class CustomerManagement extends javax.swing.JFrame {

    private CustomerManagementController customerController;
    private DefaultTableModel tableModel;
    private Customer selectedCustomer; // Store currently selected customer
    
    /**
     * Creates new form CustomerManagementJDialog
     */
    public CustomerManagement() {
        this.setUndecorated(true);
        XTheme.applyFullTheme();
        initComponents();
        this.setLocationRelativeTo(null);
        
        // Initialize controller and table model
        customerController = new CustomerManagementController();
        initializeTable();
        setupEventListeners();
        loadAllCustomers();
        
        // Debug: Show all customers in database
        debugShowAllCustomers();
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
        btn_exit.addActionListener(e -> handleExit());
    }
    
    /**
     * Load all customers from database
     */
    private void loadAllCustomers() {
        try {
            List<Customer> customers = customerController.getAllCustomers();
            updateTableData(customers);
        } catch (Exception e) {
            XDialog.error("Lỗi khi tải dữ liệu khách hàng: " + e.getMessage(), "Lỗi hệ thống");
        }
    }
    
    /**
     * Update table with customer data
     */
    private void updateTableData(List<Customer> customers) {
        tableModel.setRowCount(0);
        if (customers != null) {
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
    }
    
    /**
     * Display selected customer data in form fields
     */
    private void displaySelectedCustomerData(int selectedRow) {
        if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
            String phoneNumber = (String) tableModel.getValueAt(selectedRow, 0);
            String customerName = (String) tableModel.getValueAt(selectedRow, 1);
            Integer pointLevel = (Integer) tableModel.getValueAt(selectedRow, 2);
            String levelRanking = (String) tableModel.getValueAt(selectedRow, 3);
            
            // Update form fields
            txt_phone_number.setText(phoneNumber);
            txt_customer_name.setText(customerName);
            txt_point_level.setText(pointLevel != null ? pointLevel.toString() : "");
            txt_level_ranking.setText(levelRanking);
            
                         // Store selected customer for update/delete operations
             selectedCustomer = customerController.findCustomerByPhone(phoneNumber);
        }
    }
    
    /**
     * Clear all form fields
     */
    private void clearForm() {
        txt_phone_number.setText("");
        txt_customer_name.setText("");
        txt_point_level.setText("");
        txt_level_ranking.setText("");
        selectedCustomer = null;
    }
    
    /**
     * Validate phone number format
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^0\\d{9}$");
    }
    
    /**
     * Debug method to show all customers in database
     */
    private void debugShowAllCustomers() {
        try {
            List<Customer> allCustomers = customerController.getAllCustomers();
            if (allCustomers.isEmpty()) {
                XDialog.warning("Không có khách hàng nào trong cơ sở dữ liệu!", "Thông báo");
            } else {
                XDialog.success("Đã tải " + allCustomers.size() + " khách hàng từ cơ sở dữ liệu!", "Thành công");
            }
        } catch (Exception e) {
            XDialog.error("Lỗi khi debug: " + e.getMessage(), "Lỗi debug");
        }
    }
    
    /**
     * Update customer data with validation
     */
    private void updateCustomerData() {
        try {
            if (selectedCustomer == null) {
                XDialog.warning("Vui lòng chọn một khách hàng để cập nhật!", "Thông báo");
                return;
            }
            
            // Validate form data
            if (!validateFormData()) {
                return;
            }
            
            // Update customer object
            selectedCustomer.setCustomer_name(txt_customer_name.getText().trim());
            selectedCustomer.setPoint_level(Integer.parseInt(txt_point_level.getText().trim()));
            selectedCustomer.setLevel_ranking(txt_level_ranking.getText().trim());
            
            // Update in database
            customerController.setForm(selectedCustomer);
            customerController.update();
            
            XDialog.success("Cập nhật thông tin khách hàng thành công!", "Thành công");
            loadAllCustomers(); // Refresh table
            clearForm();
            
        } catch (NumberFormatException e) {
            XDialog.error("Điểm phải là số nguyên!", "Lỗi định dạng");
        } catch (Exception e) {
            XDialog.error("Lỗi khi cập nhật khách hàng: " + e.getMessage(), "Lỗi hệ thống");
        }
    }
    
    /**
     * Validate form data using XValidation
     */
    private boolean validateFormData() {
        // Validate customer name
        if (XValidation.isEmpty(txt_customer_name.getText().trim())) {
            XDialog.error("Tên khách hàng không được để trống!", "Lỗi validation");
            txt_customer_name.requestFocus();
            return false;
        }
        
        // Validate point level
        if (XValidation.isEmpty(txt_point_level.getText().trim())) {
            XDialog.error("Điểm không được để trống!", "Lỗi validation");
            txt_point_level.requestFocus();
            return false;
        }
        
        // Validate level ranking
        if (XValidation.isEmpty(txt_level_ranking.getText().trim())) {
            XDialog.error("Hạng không được để trống!", "Lỗi validation");
            txt_level_ranking.requestFocus();
            return false;
        }
        
        // Validate point level is number
        try {
            int pointLevel = Integer.parseInt(txt_point_level.getText().trim());
            if (pointLevel < 0) {
                XDialog.error("Điểm phải là số không âm!", "Lỗi validation");
                txt_point_level.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            XDialog.error("Điểm phải là số nguyên!", "Lỗi validation");
            txt_point_level.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Delete customer data with confirmation
     */
    private void deleteCustomerData() {
        try {
            if (selectedCustomer == null) {
                XDialog.warning("Vui lòng chọn một khách hàng để xóa!", "Thông báo");
                return;
            }
            
            // Show confirmation dialog
            String message = "Bạn có chắc chắn muốn xóa khách hàng này?\n\n" +
                           "Số điện thoại: " + selectedCustomer.getPhone_number() + "\n" +
                           "Tên: " + selectedCustomer.getCustomer_name() + "\n" +
                           "Điểm: " + selectedCustomer.getPoint_level() + "\n" +
                           "Hạng: " + selectedCustomer.getLevel_ranking();
            
            if (XDialog.confirm(message, "Xác nhận xóa")) {
                customerController.setForm(selectedCustomer);
                customerController.delete();
                
                XDialog.success("Xóa khách hàng thành công!", "Thành công");
                loadAllCustomers(); // Refresh table
                clearForm();
            }
            
        } catch (Exception e) {
            XDialog.error("Lỗi khi xóa khách hàng: " + e.getMessage(), "Lỗi hệ thống");
        }
    }
    
    /**
     * Refresh table data based on search and sort criteria
     */
    private void refreshTableData() {
        try {
            String searchText = txt_search.getText().trim().toLowerCase();
            String sortOption = (String) cbo_SortPoint.getSelectedItem();
            
            List<Customer> filteredCustomers;
            
            // Search by phone number
            if (!searchText.isEmpty()) {
                filteredCustomers = customerController.searchCustomersByPhone(searchText);
            } else {
                filteredCustomers = customerController.getAllCustomers();
            }
            
            // Apply sorting
            if (filteredCustomers != null && sortOption != null) {
                if ("Tăng dần".equals(sortOption)) {
                    filteredCustomers = customerController.sortCustomersByPointAsc();
                } else if ("Giảm dần".equals(sortOption)) {
                    filteredCustomers = customerController.sortCustomersByPointDesc();
                }
            }
            
            updateTableData(filteredCustomers);
            
        } catch (Exception e) {
            XDialog.error("Lỗi khi lọc dữ liệu: " + e.getMessage(), "Lỗi hệ thống");
        }
    }
    
    /**
     * Handle exit with confirmation
     */
    private void handleExit() {
        if (XDialog.confirm("Bạn có chắc chắn muốn thoát khỏi quản lý khách hàng?", "Xác nhận thoát")) {
            this.dispose();
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
        if (tbl_customer.getColumnModel().getColumnCount() > 0) {
            tbl_customer.getColumnModel().getColumn(2).setMinWidth(100);
            tbl_customer.getColumnModel().getColumn(2).setMaxWidth(120);
        }

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        btn_exit.setBackground(new java.awt.Color(185, 163, 147));
        btn_exit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_exit.setText("Thoát");
        btn_exit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

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

        cbo_SortPoint.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbo_SortPoint.setForeground(new java.awt.Color(204, 204, 204));
        cbo_SortPoint.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Giảm dần", "Tăng dần" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
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
                .addGap(10, 10, 10)
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
        jLabel6.setText("TÊN KH :");

        txt_customer_name.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ĐIỂM :");

        txt_point_level.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("HẠNG :");

        txt_level_ranking.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btn_update.setBackground(new java.awt.Color(185, 163, 147));
        btn_update.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_update.setText("Cập Nhật");
        btn_update.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

        btn_delete.setBackground(new java.awt.Color(185, 163, 147));
        btn_delete.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_delete.setText("Xóa");
        btn_delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

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
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_point_level, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_phone_number, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_level_ranking, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_customer_name, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_update, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                            .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 6, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_update, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_customer_name, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_phone_number)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_delete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_level_ranking, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(txt_point_level))
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
            java.util.logging.Logger.getLogger(CustomerManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerManagement().setVisible(true);
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
