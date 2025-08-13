/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.team4.quanliquanmicay.View.management;

import com.team4.quanliquanmicay.util.XTheme;
import com.team4.quanliquanmicay.util.XDialog;
import com.team4.quanliquanmicay.util.XChart;
import com.team4.quanliquanmicay.DAO.BillDAO;
import com.team4.quanliquanmicay.DAO.BillDetailsDAO;
import com.team4.quanliquanmicay.DAO.ProductDAO;
import com.team4.quanliquanmicay.Impl.BillDAOImpl;
import com.team4.quanliquanmicay.Impl.BillDetailsDAOImpl;
import com.team4.quanliquanmicay.Impl.ProductDAOImpl;
import com.team4.quanliquanmicay.Entity.Bill;
import com.team4.quanliquanmicay.Entity.BillDetails;
import com.team4.quanliquanmicay.Entity.Product;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Asus
 */
public class ReportManagement extends javax.swing.JFrame {

    // DAOs for database access
    private BillDAO billDAO;
    private BillDetailsDAO billDetailsDAO;
    private ProductDAO productDAO;

    /**
     * Creates new form ReportJDialog
     */
    public ReportManagement() {
        this.setUndecorated(true);
        XTheme.applyFullTheme();
        initComponents();
        this.setLocationRelativeTo(null);
        
        // Initialize DAOs
        initializeDAOs();
        
        // Create charts
        createProductRevenueChart();
        
        XDialog.success("Chào mừng đến với hệ thống Thống kê & Báo cáo!", "Thông báo");
    }
    
    /**
     * Initialize DAOs
     */
    private void initializeDAOs() {
        billDAO = new BillDAOImpl();
        billDetailsDAO = new BillDetailsDAOImpl();
        productDAO = new ProductDAOImpl();
    }
    
    /**
     * Create product revenue pie chart with REAL database data
     */
    private void createProductRevenueChart() {
        try {
            System.out.println("🔍 Loading real data from database...");
            
            // Get data from database
            List<Bill> bills = billDAO.findAll();
            List<BillDetails> billDetails = billDetailsDAO.findAll();
            List<Product> products = productDAO.findAll();
            
            System.out.println("📊 Database Stats:");
            System.out.println("  - Bills: " + bills.size());
            System.out.println("  - Bill Details: " + billDetails.size());
            System.out.println("  - Products: " + products.size());
            
            // Create map for product revenue calculation
            Map<String, Double> productRevenue = new HashMap<>();
            Map<String, Integer> productQuantity = new HashMap<>(); // Track quantities sold
            
            // Calculate revenue for each product from REAL data
            for (BillDetails detail : billDetails) {
                // Find corresponding bill to check if it's paid
                Bill bill = bills.stream()
                    .filter(b -> b.getBill_id() != null && b.getBill_id().equals(detail.getBill_id()))
                    .findFirst()
                    .orElse(null);
                
                // Only count revenue from paid bills (status = 1 - Đã thanh toán)
                if (bill != null && bill.getStatus() != null && bill.getStatus() == 1) {
                    // Find product name
                    Product product = products.stream()
                        .filter(p -> p.getProductId() != null && p.getProductId().equals(detail.getProduct_id()))
                        .findFirst()
                        .orElse(null);
                    
                    if (product != null) {
                        String productName = product.getProductName();
                        
                        // Use getTotalPrice() method from entity (đã tính discount)
                        double revenue = detail.getTotalPrice();
                        
                        // Accumulate revenue and quantity
                        productRevenue.merge(productName, revenue, Double::sum);
                        productQuantity.merge(productName, detail.getAmount(), Integer::sum);
                        
                        System.out.println("💰 " + productName + ": " + detail.getAmount() + " x " + detail.getPrice() + " = " + revenue);
                    }
                }
            }
            
            System.out.println("📈 Total products with revenue: " + productRevenue.size());
            
            // If no real data found, show sample data with clear indication
            if (productRevenue.isEmpty()) {
                System.out.println("⚠️ No real data found, using sample data");
                productRevenue.put("Mì Cay Hàn Quốc Cấp 1 (Sample)", 2500000.0);
                productRevenue.put("Mì Cay Hàn Quốc Cấp 2 (Sample)", 1800000.0);
                productRevenue.put("Mì Cay Hàn Quốc Cấp 3 (Sample)", 1200000.0);
                productRevenue.put("Mì Cay Đặc Biệt Siêu Cay (Sample)", 900000.0);
                productRevenue.put("Mì Cay Phở Mai (Sample)", 800000.0);
                productRevenue.put("Combo Mì Cay + Nước (Sample)", 600000.0);
                productRevenue.put("Nước Uống Các Loại (Sample)", 400000.0);
            } else {
                // Sort by revenue descending to show top performers
                System.out.println("🏆 Top products by revenue:");
                productRevenue.entrySet().stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .forEach(entry -> {
                        int qty = productQuantity.getOrDefault(entry.getKey(), 0);
                        System.out.println("  " + entry.getKey() + ": " + String.format("%,.0f VNĐ", entry.getValue()) + " (" + qty + " phần)");
                    });
            }
            
            // Create pie dataset with percentage labels - SORTED by revenue descending
            DefaultPieDataset pieDataset = new DefaultPieDataset();
            double totalRevenue = productRevenue.values().stream().mapToDouble(Double::doubleValue).sum();
            
            // Sort products by revenue (highest first) and limit to top 10 for readability
            productRevenue.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue())) // Sort descending
                .limit(10) // Top 10 products only
                .forEach(entry -> {
                    double percentage = (entry.getValue() / totalRevenue) * 100;
                    String label = entry.getKey() + " (" + String.format("%.1f", percentage) + "%)";
                    pieDataset.setValue(label, entry.getValue());
                });
            
            // Create pie chart
            JFreeChart chart = XChart.createPieChart(
                "Biểu Đồ Doanh Thu Theo Món Ăn",
                pieDataset
            );
            
            // Create chart panel
            ChartPanel chartPanel = XChart.createChartPanel(chart);
            chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            // Set jPanel4 layout and add chart
            jPanel4.setLayout(new BorderLayout());
            jPanel4.removeAll();
            jPanel4.add(chartPanel, BorderLayout.CENTER);
            
            // Add summary panel with detailed info
            JPanel summaryPanel = createDetailedSummaryPanel(productRevenue, productQuantity, "Doanh thu theo món ăn (Top 10)");
            jPanel4.add(summaryPanel, BorderLayout.SOUTH);
            
            // Refresh panel
            jPanel4.revalidate();
            jPanel4.repaint();
            
            System.out.println("✅ Created product revenue chart with " + productRevenue.size() + " products");
            
        } catch (Exception e) {
            e.printStackTrace();
            showErrorPanel(jPanel4, "Lỗi khi tạo biểu đồ doanh thu theo món: " + e.getMessage());
        }
    }
    
    /**
     * Create detailed summary panel with top product info
     */
    private JPanel createDetailedSummaryPanel(Map<String, Double> revenueData, Map<String, Integer> quantityData, String title) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.setLayout(new java.awt.GridLayout(4, 1));
        
        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Summary info
        double totalRevenue = revenueData.values().stream().mapToDouble(Double::doubleValue).sum();
        int totalItems = revenueData.size();
        int totalQuantity = quantityData.values().stream().mapToInt(Integer::intValue).sum();
        
        JLabel summaryLabel = new JLabel(String.format(
            "Tổng cộng: %d món - %d phần bán - Tổng doanh thu: %,.0f VNĐ", 
            totalItems, totalQuantity, totalRevenue));
        summaryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        summaryLabel.setForeground(new Color(52, 73, 94));
        summaryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Top product info
        String topProduct = revenueData.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("N/A");
        
        double topRevenue = revenueData.getOrDefault(topProduct, 0.0);
        int topQuantity = quantityData.getOrDefault(topProduct, 0);
        
        JLabel topProductLabel = new JLabel(String.format(
            "🏆 Món bán chạy nhất: %s (%,.0f VNĐ - %d phần)", 
            topProduct, topRevenue, topQuantity));
        topProductLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        topProductLabel.setForeground(new Color(231, 76, 60)); // Red highlight
        topProductLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Data source info
        String dataSource = revenueData.isEmpty() ? "Dữ liệu mẫu" : "Dữ liệu thực từ database";
        JLabel sourceLabel = new JLabel("📊 Nguồn: " + dataSource);
        sourceLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        sourceLabel.setForeground(new Color(127, 140, 141));
        sourceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(titleLabel);
        panel.add(summaryLabel);
        panel.add(topProductLabel);
        panel.add(sourceLabel);
        
        return panel;
    }
    
    /**
     * Create summary panel for chart information (fallback method)
     */
    private JPanel createSummaryPanel(Map<String, Double> data, String title) {
        return createDetailedSummaryPanel(data, new HashMap<>(), title);
    }
    
    /**
     * Show error panel when chart creation fails
     */
    private void showErrorPanel(JPanel targetPanel, String errorMessage) {
        targetPanel.removeAll();
        targetPanel.setLayout(new BorderLayout());
        
        JLabel errorLabel = new JLabel("❌ " + errorMessage);
        errorLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
        
        targetPanel.add(errorLabel, BorderLayout.CENTER);
        targetPanel.revalidate();
        targetPanel.repaint();
    }
    
    /**
     * Handle exit with confirmation
     */
    private void handleExit() {
        if (XDialog.confirm("Bạn có chắc chắn muốn thoát khỏi hệ thống báo cáo?", "Xác nhận thoát")) {
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 164, 133));

        jPanel2.setBackground(new java.awt.Color(134, 39, 43));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("THỐNG KÊ & BÁO CÁO");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(498, 498, 498)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jTabbedPane1.setBackground(new java.awt.Color(204, 164, 133));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0), 3));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(245, 30));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1185, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 642, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("DOANH THU TỔNG QUÁT", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1185, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 642, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("DOANH THU THEO MÓN", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1185, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 642, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("DOANH THU THEO NHÂN VIÊN", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1185, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 642, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("XU HƯỚNG DOANH THU", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1185, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 642, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("BÁO CÁO", jPanel7);

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/icon/Exit.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleExit();
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(ReportManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReportManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReportManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReportManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReportManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
