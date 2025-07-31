/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.team4.quanliquanmicay.View.Dialogs;

import com.team4.quanliquanmicay.util.XTheme;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.team4.quanliquanmicay.util.XDialog;
import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.Entity.Product;
import com.team4.quanliquanmicay.Entity.Bill;
import com.team4.quanliquanmicay.Entity.BillDetails;
import com.team4.quanliquanmicay.DAO.ProductDAO;
import com.team4.quanliquanmicay.Impl.ProductDAOImpl;
import com.team4.quanliquanmicay.DAO.BillDetailsDAO;
import com.team4.quanliquanmicay.Impl.BillDetailsDAOImpl;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 *
 * @author HP
 */
public class DatMonJDialog extends javax.swing.JFrame {

    // DAO objects
    private ProductDAO productDAO = new ProductDAOImpl();
    private BillDetailsDAO billDetailsDAO = new BillDetailsDAOImpl();
    
    // Current bill
    private Bill currentBill;
    private HoaDonJDialog parentDialog;
    
    // Cart items
    private List<CartItem> cartItems = new ArrayList<>();
    
    // Table models
    private DefaultTableModel cartTableModel;
    private DefaultTableModel productTableModel;
    
    // Product lists
    private List<Product> miCayProducts = new ArrayList<>();
    private List<Product> drinkProducts = new ArrayList<>();
    
    // Search functionality
    private String currentSearch = "";
    private String currentCategory = "mi"; // "mi" or "drink"

    /**
     * Creates new form DatMonJDialog
     */
    public DatMonJDialog(HoaDonJDialog parent, Bill bill) {
        this.parentDialog = parent;
        this.currentBill = bill;
        
        this.setUndecorated(true);
        XTheme.applyFullTheme();
        initComponents();
        this.setLocationRelativeTo(null);
        
        setupTables();
        setupEventHandlers();
        loadProducts();
        updateCartDisplay();
    }
    
    /**
     * Constructor đơn giản cho testing
     */
    public DatMonJDialog() {
        this(null, null);
    }
    
    /**
     * Thiết lập bảng
     */
    private void setupTables() {
        // Cart table
        cartTableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Tên món", "Số lượng", "Đơn giá", "Thành tiền"}
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false};
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        jTable1.setModel(cartTableModel);
        
        // Product table
        productTableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Mã SP", "Tên món", "Đơn giá", "Trạng thái"}
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false};
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
    }
    
    /**
     * Thiết lập event handlers
     */
    private void setupEventHandlers() {
        // Nút Exit
        jButton7.addActionListener(e -> dispose());
        
        // Nút Xóa món
        jButton6.addActionListener(e -> removeSelectedItem());
        
        // Nút Đặt món
        jButton8.addActionListener(e -> placeOrder());
        
        // Search functionality
        jTextField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                currentSearch = jTextField1.getText().toLowerCase();
                filterProducts();
            }
        });
        
        // Tab change
        jTabbedPane1.addChangeListener(e -> {
            if (jTabbedPane1.getSelectedIndex() == 0) {
                currentCategory = "mi";
            } else {
                currentCategory = "drink";
            }
            filterProducts();
        });
        
        // Product buttons
        setupProductButtons();
    }
    
    /**
     * Thiết lập các nút sản phẩm
     */
    private void setupProductButtons() {
        // Mi cay buttons
        jButton3.addActionListener(e -> addToCart(getProductFromButton(jButton3)));
        jButton5.addActionListener(e -> addToCart(getProductFromButton(jButton5)));
        jButton9.addActionListener(e -> addToCart(getProductFromButton(jButton9)));
        jButton10.addActionListener(e -> addToCart(getProductFromButton(jButton10)));
        jButton11.addActionListener(e -> addToCart(getProductFromButton(jButton11)));
        jButton12.addActionListener(e -> addToCart(getProductFromButton(jButton12)));
        jButton13.addActionListener(e -> addToCart(getProductFromButton(jButton13)));
        jButton14.addActionListener(e -> addToCart(getProductFromButton(jButton14)));
    }
    
    /**
     * Lấy sản phẩm từ button
     */
    private Product getProductFromButton(JButton button) {
        String productName = button.getText();
        if (currentCategory.equals("mi")) {
            return findProductByName(miCayProducts, productName);
        } else {
            return findProductByName(drinkProducts, productName);
        }
    }
    
    /**
     * Tìm sản phẩm theo tên
     */
    private Product findProductByName(List<Product> products, String name) {
        for (Product product : products) {
            if (product.getProductName().equals(name)) {
                return product;
            }
        }
        return null;
    }
    
    /**
     * Load sản phẩm từ database
     */
    private void loadProducts() {
        try {
            // Load mi cay products
            String miSql = "SELECT * FROM PRODUCT WHERE category_id = 'MI' AND is_available = 1";
            miCayProducts = loadProductsFromSQL(miSql);
            
            // Load drink products
            String drinkSql = "SELECT * FROM PRODUCT WHERE category_id = 'DRINK' AND is_available = 1";
            drinkProducts = loadProductsFromSQL(drinkSql);
            
            // Update product buttons
            updateProductButtons();
            
        } catch (Exception e) {
            XDialog.alert("Lỗi khi tải sản phẩm: " + e.getMessage());
            // Khởi tạo list rỗng nếu có lỗi
            miCayProducts = new ArrayList<>();
            drinkProducts = new ArrayList<>();
        }
    }
    
    /**
     * Load sản phẩm từ SQL
     */
    private List<Product> loadProductsFromSQL(String sql) {
        return XJdbc.executeQuery(sql, rs -> {
            List<Product> products = new ArrayList<>();
            try {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getString("product_id"));
                    product.setProductName(rs.getString("product_name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setDiscount(rs.getDouble("discount"));
                    product.setUnit(rs.getString("unit"));
                    product.setImage(rs.getString("image"));
                    product.setIsAvailable(rs.getInt("is_available"));
                    product.setNote(rs.getString("note"));
                    product.setCategoryId(rs.getString("category_id"));
                    products.add(product);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return products;
        });
    }
    
    /**
     * Cập nhật các nút sản phẩm
     */
    private void updateProductButtons() {
        // Update mi cay buttons
        updateProductButton(jButton3, jLabel5, miCayProducts, 0);
        updateProductButton(jButton5, jLabel6, miCayProducts, 1);
        updateProductButton(jButton9, jLabel7, miCayProducts, 2);
        updateProductButton(jButton10, jLabel8, miCayProducts, 3);
        updateProductButton(jButton11, jLabel9, miCayProducts, 4);
        updateProductButton(jButton12, jLabel10, miCayProducts, 5);
        updateProductButton(jButton13, jLabel11, miCayProducts, 6);
        updateProductButton(jButton14, jLabel12, miCayProducts, 7);
    }
    
    /**
     * Cập nhật nút sản phẩm
     */
    private void updateProductButton(JButton button, JLabel label, List<Product> products, int index) {
        if (index < products.size()) {
            Product product = products.get(index);
            button.setText(product.getProductName());
            label.setText(product.getProductName());
            button.setEnabled(product.isAvailable());
            
            // Set image if available
            if (product.getImage() != null && !product.getImage().isEmpty()) {
                try {
                    ImageIcon icon = new ImageIcon(getClass().getResource("/icons_and_images/product/" + product.getImage()));
                    Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(img));
                } catch (Exception e) {
                    // Use default image
                    button.setIcon(new ImageIcon(getClass().getResource("/icons_and_images/icons8-plate-50.png")));
                }
            }
        } else {
            button.setText("");
            label.setText("");
            button.setEnabled(false);
            button.setIcon(null);
        }
    }
    
    /**
     * Lọc sản phẩm theo tìm kiếm
     */
    private void filterProducts() {
        List<Product> filteredProducts;
        if (currentCategory.equals("mi")) {
            filteredProducts = filterProductsByName(miCayProducts);
        } else {
            filteredProducts = filterProductsByName(drinkProducts);
        }
        
        // Update buttons with filtered products
        updateProductButtonsWithFilter(filteredProducts);
    }
    
    /**
     * Lọc sản phẩm theo tên
     */
    private List<Product> filterProductsByName(List<Product> products) {
        if (currentSearch.isEmpty()) {
            return products;
        }
        
        List<Product> filtered = new ArrayList<>();
        for (Product product : products) {
            if (product.getProductName().toLowerCase().contains(currentSearch)) {
                filtered.add(product);
            }
        }
        return filtered;
    }
    
    /**
     * Cập nhật nút với sản phẩm đã lọc
     */
    private void updateProductButtonsWithFilter(List<Product> products) {
        updateProductButton(jButton3, jLabel5, products, 0);
        updateProductButton(jButton5, jLabel6, products, 1);
        updateProductButton(jButton9, jLabel7, products, 2);
        updateProductButton(jButton10, jLabel8, products, 3);
        updateProductButton(jButton11, jLabel9, products, 4);
        updateProductButton(jButton12, jLabel10, products, 5);
        updateProductButton(jButton13, jLabel11, products, 6);
        updateProductButton(jButton14, jLabel12, products, 7);
    }
    
    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    private void addToCart(Product product) {
        if (product == null) {
            XDialog.alert("Sản phẩm không tồn tại!");
            return;
        }
        
        if (!product.isAvailable()) {
            XDialog.alert("Sản phẩm hiện không có sẵn!");
            return;
        }
        
        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId().equals(product.getProductId())) {
                item.setQuantity(item.getQuantity() + 1);
                updateCartDisplay();
                return;
            }
        }
        
        // Thêm sản phẩm mới vào giỏ hàng
        cartItems.add(new CartItem(product, 1));
        updateCartDisplay();
    }
    
    /**
     * Xóa món đã chọn khỏi giỏ hàng
     */
    private void removeSelectedItem() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            XDialog.alert("Vui lòng chọn món cần xóa!");
            return;
        }
        
        cartItems.remove(selectedRow);
        updateCartDisplay();
    }
    
    /**
     * Cập nhật hiển thị giỏ hàng
     */
    private void updateCartDisplay() {
        cartTableModel.setRowCount(0);
        int totalQuantity = 0;
        
        for (CartItem item : cartItems) {
            double totalPrice = item.getProduct().getPrice() * item.getQuantity() * (1 - item.getProduct().getDiscount());
            
            Object[] row = {
                item.getProduct().getProductName(),
                item.getQuantity(),
                formatCurrency(item.getProduct().getPrice()),
                formatCurrency(totalPrice)
            };
            cartTableModel.addRow(row);
            totalQuantity += item.getQuantity();
        }
        
        jLabel3.setText(String.valueOf(totalQuantity));
    }
    
    /**
     * Đặt món
     */
    private void placeOrder() {
        if (cartItems.isEmpty()) {
            XDialog.alert("Giỏ hàng trống! Vui lòng chọn món ăn.");
            return;
        }
        
        if (currentBill == null) {
            XDialog.alert("Không tìm thấy hóa đơn!");
            return;
        }
        
        try {
            // Thêm từng món vào bill details
            for (CartItem item : cartItems) {
                BillDetails billDetail = new BillDetails();
                billDetail.setBill_detail_id(null); // Để database tự tạo
                billDetail.setBill_id(currentBill.getBill_id());
                billDetail.setProduct_id(item.getProduct().getProductId());
                billDetail.setAmount(item.getQuantity());
                billDetail.setPrice(item.getProduct().getPrice());
                billDetail.setDiscount(item.getProduct().getDiscount());
                
                billDetailsDAO.create(billDetail);
            }
            
            XDialog.alert("Đặt món thành công!");
            
            // Refresh parent dialog
            if (parentDialog != null) {
                parentDialog.loadBillDetails(currentBill.getBill_id());
            }
            
            // Clear cart and close dialog
            cartItems.clear();
            updateCartDisplay();
            dispose();
            
        } catch (Exception e) {
            XDialog.alert("Lỗi khi đặt món: " + e.getMessage());
        }
    }
    
    /**
     * Tạo ID cho bill details
     */
    private String generateBillDetailsId() {
        return "BD" + System.currentTimeMillis();
    }
    
    /**
     * Format tiền tệ
     */
    private String formatCurrency(double amount) {
        return String.format("%,.0f VNĐ", amount);
    }
    
    /**
     * Inner class cho giỏ hàng
     */
    private static class CartItem {
        private Product product;
        private int quantity;
        
        public CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
        
        public Product getProduct() { return product; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();

        jButton4.setText("jButton4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Đặt Món");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(426, 426, 426))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên món", "Số lượng"
            }
        ));
        jScrollPane3.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
        }

        jButton6.setBackground(new java.awt.Color(204, 204, 204));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(153, 153, 153));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/delete.png"))); // NOI18N
        jButton6.setText("Xóa");

        jButton7.setBackground(new java.awt.Color(204, 204, 204));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(102, 102, 102));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/icons8-exit-32.png"))); // NOI18N
        jButton7.setText("Exit");

        jButton8.setBackground(new java.awt.Color(204, 204, 204));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(0, 102, 51));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Add to basket.png"))); // NOI18N
        jButton8.setText("Đặt món");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Tổng số lượng :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8))
                .addGap(30, 30, 30)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(204, 0, 0));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Tìm kiếm :");

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jButton3.setText("jButton2");

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anhnen1.PNG"))); // NOI18N
        jButton5.setText("jButton2");

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/product/drink/3e76d40a-3ecc-438a-b0c9-9124baba47b9.jpg"))); // NOI18N
        jButton9.setText("jButton2");

        jButton10.setText("jButton2");

        jLabel5.setText("Tên món ăn");

        jLabel6.setText("Tên món ăn");

        jLabel7.setText("Tên món ăn");

        jLabel8.setText("Tên món ăn");

        jButton11.setText("jButton2");

        jLabel9.setText("Tên món ăn");

        jButton12.setText("jButton2");

        jLabel10.setText("Tên món ăn");

        jButton13.setText("jButton2");

        jLabel11.setText("Tên món ăn");

        jButton14.setText("jButton2");

        jLabel12.setText("Tên món ăn");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(108, 108, 108)
                                .addComponent(jLabel10)
                                .addGap(97, 97, 97)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addGap(43, 43, 43))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(108, 108, 108)
                                .addComponent(jLabel6)
                                .addGap(97, 97, 97)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8)
                                .addGap(68, 68, 68)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addContainerGap())
        );

        jScrollPane4.setViewportView(jPanel2);

        jTabbedPane1.addTab("Mì cay", jScrollPane4);
        jTabbedPane1.addTab("Nước", jScrollPane5);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(199, 199, 199)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
            java.util.logging.Logger.getLogger(DatMonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DatMonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DatMonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DatMonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DatMonJDialog(null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
