/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.team4.quanliquanmicay.View;

import com.team4.quanliquanmicay.util.XTheme;
import com.team4.quanliquanmicay.util.XDialog;
import com.team4.quanliquanmicay.Entity.Customer;
import com.team4.quanliquanmicay.DAO.CustomerDAO;
import com.team4.quanliquanmicay.Impl.CustomerDAOImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.team4.quanliquanmicay.Controller.CustomerController;

/**
 *
 * @author Asus
 */
public class Customer extends javax.swing.JFrame {

    
    private CustomerDAO customerDAO;
    private Customer createdCustomer;
    private CustomerController customerController;
    
    /**
     * Creates new form CustomerJDialog
     */
    public Customer() {
        this.setUndecorated(true);
        XTheme.applyFullTheme();
        initComponents();
        this.setLocationRelativeTo(null);
        
        // Khởi tạo DAO và Controller
        customerDAO = new CustomerDAOImpl();
        customerController = new CustomerController();
        
        // Setup event handlers
        setupEventHandlers();
        
        // Setup text field placeholders
        setupTextFieldPlaceholders();
    }
    
    /**
     * Creates new form CustomerJDialog with phone number
     */
    public Customer(String phoneNumber) {
        this.setUndecorated(true);
        XTheme.applyFullTheme();
        initComponents();
        this.setLocationRelativeTo(null);
        
        // Khởi tạo DAO và Controller
        customerDAO = new CustomerDAOImpl();
        customerController = new CustomerController();
        
        // Set số điện thoại nếu có
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            txt_phone.setText(phoneNumber.trim());
        }
        
        // Setup event handlers
        setupEventHandlers();
        
        // Setup text field placeholders
        setupTextFieldPlaceholders();
    }
    
    private void setupEventHandlers() {
        // jButton1 là nút Create/Update
        btn_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCustomerAction();
            }
        });
        
        // jButton2 là nút Exit
        btn_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void handleCustomerAction() {
        String phoneNumber = txt_phone.getText().trim();
        String customerName = txt_name.getText().trim();
        
        // Validate input
        if (phoneNumber.isEmpty()) {
            XDialog.alert("Vui lòng nhập số điện thoại!");
            txt_phone.requestFocus();
            return;
        }
        
        if (customerName.isEmpty()) {
            XDialog.alert("Vui lòng nhập tên khách hàng!");
            txt_name.requestFocus();
            return;
        }
        
        // Validate phone number format
        if (!isValidPhoneNumber(phoneNumber)) {
            XDialog.alert("Số điện thoại không hợp lệ! Vui lòng nhập số điện thoại 10-11 số bắt đầu bằng 0.");
            txt_phone.requestFocus();
            return;
        }
        
        // Validate customer name
        if (!isValidCustomerName(customerName)) {
            XDialog.alert("Tên khách hàng không hợp lệ! Vui lòng nhập tên có ít nhất 2 ký tự.");
            txt_name.requestFocus();
            return;
        }
        
        try {
            // Kiểm tra xem số điện thoại đã tồn tại chưa
            Customer existingCustomer = customerDAO.findById(phoneNumber);
            if (existingCustomer != null) {
                // Thông báo lỗi khi số điện thoại đã tồn tại
                XDialog.alert("Số điện thoại đã tồn tại vui lòng chọn số điện thoại khác!");
                txt_phone.requestFocus();
                return;
            }
            
            // Tạo khách hàng mới
            Customer customer = new Customer();
            customer.setPhone_number(phoneNumber);
            customer.setCustomer_name(customerName);
            customer.setPoint_level(0); // Điểm mặc định là 0
            customer.setLevel_ranking("Thường"); // Hạng mặc định là "Thường"
            customer.setCreated_date(new java.util.Date()); // Ngày tạo
            
            customerDAO.create(customer);
            createdCustomer = customer;
            
            XDialog.alert("Tạo khách hàng thành công!");
            dispose();
            
        } catch (Exception e) {
            XDialog.alert("Lỗi khi tạo khách hàng: " + e.getMessage());
        }
    }
    
    /**
     * Validate phone number format
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^0[0-9]{9,10}$");
    }
    
    /**
     * Validate customer name
     */
    private boolean isValidCustomerName(String customerName) {
        return customerName.length() >= 2;
    }
    
    public Customer getCustomer() {
        return createdCustomer;
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
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_phone = new javax.swing.JTextField();
        txt_name = new javax.swing.JTextField();
        btn_add = new javax.swing.JButton();
        btn_exit = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel4.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(204, 164, 133));

        jPanel3.setBackground(new java.awt.Color(134, 39, 43));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("KHÁCH HÀNG");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SỐ ĐIỆN THOẠI :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("TÊN :");

        txt_phone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txt_name.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nameActionPerformed(evt);
            }
        });

        btn_add.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Create.png"))); // NOI18N
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_exit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Exit.png"))); // NOI18N
        btn_exit.setText("EXIT");
        btn_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_phone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_add, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                        .addGap(6, 6, 6)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(btn_exit)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Clear all form fields
     */
    private void clearForm() {
        txt_phone.setText("");
        txt_name.setText("");
        txt_phone.requestFocus();
    }
    
    /**
     * Load customer data by phone number
     */
    public void loadCustomerByPhone(String phoneNumber) {
        try {
            Customer customer = customerDAO.findById(phoneNumber);
            if (customer != null) {
                // Nếu khách hàng đã tồn tại, thông báo và clear form
                XDialog.alert("Số điện thoại đã tồn tại vui lòng chọn số điện thoại khác!");
                clearForm();
            } else {
                // Nếu khách hàng chưa tồn tại, chỉ set số điện thoại
                txt_phone.setText(phoneNumber);
                txt_name.setText("");
            }
        } catch (Exception e) {
            XDialog.alert("Lỗi khi tải thông tin khách hàng: " + e.getMessage());
        }
    }
    
    /**
     * Set text field labels for better UX
     */
    private void setupTextFieldPlaceholders() {
        txt_phone.setToolTipText("Nhập số điện thoại khách hàng (VD: 0123456789)");
        txt_name.setToolTipText("Nhập tên khách hàng");
    }
    
    /**
     * Show customer statistics
     */
    public void showCustomerStatistics() {
        try {
            // This would need to be implemented in CustomerDAO
            // For now, just show a basic message
            XDialog.alert("Chức năng thống kê đang được phát triển.");
        } catch (Exception e) {
            XDialog.alert("Lỗi khi hiển thị thống kê: " + e.getMessage());
        }
    }
    
    /**
     * Search customer by phone number
     */
    public void searchCustomer() {
        String phoneNumber = txt_phone.getText().trim();
        if (phoneNumber.isEmpty()) {
            XDialog.alert("Vui lòng nhập số điện thoại để tìm kiếm!");
            return;
        }
        
        loadCustomerByPhone(phoneNumber);
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
            java.util.logging.Logger.getLogger(Customer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Customer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Customer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Customer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Customer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_exit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_phone;
    // End of variables declaration//GEN-END:variables
}
