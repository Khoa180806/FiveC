/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.team4.quanliquanmicay.View.management;

import com.team4.quanliquanmicay.util.XTheme;
import com.team4.quanliquanmicay.util.XDialog;
import com.team4.quanliquanmicay.util.XChart;
import com.team4.quanliquanmicay.util.TimeRange;
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
import java.awt.GridLayout;
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.io.File;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.team4.quanliquanmicay.util.XPDF;
import com.team4.quanliquanmicay.util.XExcel;

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
        
        // Safe-load exit icon after components are created
        try {
            java.net.URL exitUrl = getClass().getResource("/icons_and_images/icon/Exit.png");
            if (exitUrl != null) {
                jButton1.setIcon(new javax.swing.ImageIcon(exitUrl));
            } else {
                System.err.println("⚠️ Missing resource: /icons_and_images/icon/Exit.png");
            }
        } catch (Exception ignore) { }
        
        // Build General Revenue Dashboard
        initGeneralDashboard();
        
        // Create charts
        createProductRevenueChart();
        
        // Exit action
        jButton1.addActionListener(e -> handleExit());
        
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
    
    // ========================================
    // TAB 1: DOANH THU TỔNG QUÁT (pnlGeneral)
    // ========================================
    
    private JComboBox<String> cboRange;
    private JPanel kpiContainer;
    private JPanel generalChartContainer;
    private JPanel actionsContainer;
    
    private void initGeneralDashboard() {
        try {
            pnlGeneral.setLayout(new BorderLayout());
            
            // Header with filters + actions
            JPanel header = new JPanel(new BorderLayout());
            header.setBackground(Color.WHITE);
            header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JLabel title = new JLabel("Tổng quan doanh thu");
            title.setFont(new Font("Tahoma", Font.BOLD, 20));
            title.setForeground(new Color(134, 39, 43));
            title.setHorizontalAlignment(SwingConstants.LEFT);
            header.add(title, BorderLayout.WEST);
            
            JPanel rightHeader = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 0));
            rightHeader.setOpaque(false);
            cboRange = new JComboBox<>(new String[] { "Hôm nay", "Tuần này", "Tháng này", "Quý này", "Năm nay" });
            JButton btnRefresh = XTheme.createBeButton("Làm mới", e -> refreshGeneralData());
            JButton btnExportPDF = XTheme.createMiyCayButton("Xuất PDF", e -> exportPdfReport());
            JButton btnExportExcel = XTheme.createBeButton("Xuất Excel", e -> exportExcelReport());
            cboRange.addActionListener(e -> refreshGeneralData());
            rightHeader.add(cboRange);
            rightHeader.add(btnRefresh);
            rightHeader.add(btnExportPDF);
            rightHeader.add(btnExportExcel);
            header.add(rightHeader, BorderLayout.EAST);
            
            pnlGeneral.add(header, BorderLayout.NORTH);
            
            // Center section
            JPanel center = new JPanel(new BorderLayout());
            center.setBackground(Color.WHITE);
            
            // KPI cards row
            kpiContainer = new JPanel(new GridLayout(1, 4, 12, 12));
            kpiContainer.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 6));
            kpiContainer.setBackground(Color.WHITE);
            center.add(kpiContainer, BorderLayout.NORTH);
            
            // Chart container
            generalChartContainer = new JPanel(new BorderLayout());
            generalChartContainer.setBorder(BorderFactory.createEmptyBorder(6, 12, 12, 12));
            generalChartContainer.setBackground(Color.WHITE);
            center.add(generalChartContainer, BorderLayout.CENTER);
            
            // Footer spacing only
            actionsContainer = new JPanel();
            actionsContainer.setOpaque(false);
            actionsContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
            center.add(actionsContainer, BorderLayout.SOUTH);
            
            pnlGeneral.add(center, BorderLayout.CENTER);
            
            refreshGeneralData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void refreshGeneralData() {
        try {
            TimeRange range = getSelectedRange();
            
            // Load data
            List<Bill> bills = billDAO.findAll();
            List<Bill> paidBillsInRange = new ArrayList<>();
            double totalRevenue = 0;
            int totalOrders = 0;
            int servingOrders = 0;
            double avgOrderValue = 0;
            
            for (Bill b : bills) {
                if (b == null) continue;
                if (b.getStatus() != null && b.getStatus() == 0) servingOrders++;
                if (b.getStatus() != null && b.getStatus() == 1 && withinRange(b.getCheckout(), range)) {
                    paidBillsInRange.add(b);
                    totalRevenue += b.getTotal_amount();
                    totalOrders++;
                }
            }
            if (totalOrders > 0) {
                avgOrderValue = totalRevenue / totalOrders;
            }
            
            // Update KPI cards
            kpiContainer.removeAll();
            kpiContainer.add(createIconKpiCard("/icons_and_images/icon/Price list.png", "Doanh thu", String.format("%,.0f VNĐ", totalRevenue), new Color(134,39,43)));
            kpiContainer.add(createIconKpiCard("/icons_and_images/icon/Notes.png", "Số hóa đơn", String.valueOf(totalOrders), new Color(204,164,133)));
            kpiContainer.add(createIconKpiCard("/icons_and_images/icon/Task list.png", "HĐ đang phục vụ", String.valueOf(servingOrders), new Color(52,73,94)));
            kpiContainer.add(createIconKpiCard("/icons_and_images/icon/Statistics.png", "Giá trị TB/HĐ", String.format("%,.0f VNĐ", avgOrderValue), new Color(40,167,69)));
            kpiContainer.revalidate();
            kpiContainer.repaint();
            
            // Build chart: only daily
            DefaultCategoryDataset dayDataset = buildRevenueByDayDataset(paidBillsInRange);
            String title = "Doanh thu theo ngày (" + (String) cboRange.getSelectedItem() + ")";
            JFreeChart dayChart = XChart.createBarChart(title, "Ngày", "VNĐ", dayDataset);
            ChartPanel dayPanel = XChart.createChartPanel(dayChart);

            generalChartContainer.removeAll();
            generalChartContainer.add(dayPanel, BorderLayout.CENTER);
            generalChartContainer.revalidate();
            generalChartContainer.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private JPanel createKpiCard(String label, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        
        JLabel lbTitle = new JLabel(label);
        lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbTitle.setForeground(new Color(108, 117, 125));
        
        JLabel lbValue = new JLabel(value);
        lbValue.setFont(new Font("Tahoma", Font.BOLD, 18));
        lbValue.setForeground(color);
        lbValue.setHorizontalAlignment(SwingConstants.RIGHT);
        
        card.add(lbTitle, BorderLayout.NORTH);
        card.add(lbValue, BorderLayout.CENTER);
        return card;
    }

    private JPanel createIconKpiCard(String iconPath, String label, String value, Color valueColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel lbTitle = new JLabel(label);
        lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbTitle.setForeground(new Color(108, 117, 125));

        JLabel lbIcon = new JLabel();
        lbIcon.setHorizontalAlignment(SwingConstants.RIGHT);
        ImageIcon raw = loadIcon(iconPath, 26, 26);
        if (raw != null) lbIcon.setIcon(raw);

        top.add(lbTitle, BorderLayout.WEST);
        top.add(lbIcon, BorderLayout.EAST);

        JLabel lbValue = new JLabel(value);
        lbValue.setFont(new Font("Tahoma", Font.BOLD, 18));
        lbValue.setForeground(valueColor);
        lbValue.setHorizontalAlignment(SwingConstants.LEFT);

        card.add(top, BorderLayout.NORTH);
        card.add(lbValue, BorderLayout.CENTER);
        return card;
    }

    private ImageIcon loadIcon(String path, int w, int h) {
        try {
            java.net.URL url = getClass().getResource(path);
            if (url == null) return null;
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception ex) {
            return null;
        }
    }
    
    private boolean withinRange(java.util.Date date, TimeRange range) {
        if (date == null || range == null) return false;
        return !date.before(range.getBegin()) && date.before(range.getEnd());
    }
    
    private TimeRange getSelectedRange() {
        String selected = cboRange != null ? (String) cboRange.getSelectedItem() : "Hôm nay";
        if ("Tuần này".equals(selected)) return TimeRange.thisWeek();
        if ("Tháng này".equals(selected)) return TimeRange.thisMonth();
        if ("Quý này".equals(selected)) return TimeRange.thisQuarter();
        if ("Năm nay".equals(selected)) return TimeRange.thisYear();
        return TimeRange.today();
    }
    
    private Map<String, Double> aggregateRevenueByDay(List<Bill> paidBills) {
        Map<String, Double> map = new java.util.TreeMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        for (Bill b : paidBills) {
            String key = sdf.format(b.getCheckout() != null ? b.getCheckout() : b.getCheckin());
            map.merge(key, b.getTotal_amount(), Double::sum);
        }
        return map;
    }

    private DefaultCategoryDataset buildRevenueByDayDataset(List<Bill> paidBills) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Double> dateToRevenue = aggregateRevenueByDay(paidBills);
        for (Map.Entry<String, Double> e : dateToRevenue.entrySet()) {
            dataset.addValue(e.getValue(), "Doanh thu", e.getKey());
        }
        return dataset;
    }

    // YoY/MoM helpers removed as requested
    
    // ========================================
    // Export / Print actions
    // ========================================
    
    private void exportPdfReport() {
        try {
            if (generalChartContainer.getComponentCount() == 0) return;
            ChartPanel chartPanel = (ChartPanel) generalChartContainer.getComponent(0);
            JFreeChart chart = chartPanel.getChart();
            File out = XPDF.exportGeneralRevenue(chart, (String) cboRange.getSelectedItem());
            XDialog.success("Đã xuất PDF: " + out.getAbsolutePath(), "Xuất báo cáo");
        } catch (Exception ex) {
            XDialog.error("Lỗi xuất PDF: " + ex.getMessage(), "Lỗi");
        }
    }
    
    private void exportExcelReport() {
        try {
            TimeRange range = getSelectedRange();
            List<Bill> bills = billDAO.findAll();
            List<Bill> paid = new ArrayList<>();
            for (Bill b : bills) {
                if (b.getStatus() != null && b.getStatus() == 1 && withinRange(b.getCheckout(), range)) paid.add(b);
            }
            File out = XExcel.exportGeneralRevenueBills(paid, (String) cboRange.getSelectedItem());
            XDialog.success("Đã xuất Excel: " + out.getAbsolutePath(), "Xuất báo cáo");
        } catch (Exception ex) {
            XDialog.error("Lỗi xuất Excel: " + ex.getMessage(), "Lỗi");
        }
    }
    
    // Removed printCurrentChart per request
    
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
            DefaultPieDataset<String> pieDataset = new DefaultPieDataset<>();
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
        pnlGeneral = new javax.swing.JPanel();
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

        pnlGeneral.setBackground(new java.awt.Color(255, 255, 255));
        pnlGeneral.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        pnlGeneral.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlGeneralLayout = new javax.swing.GroupLayout(pnlGeneral);
        pnlGeneral.setLayout(pnlGeneralLayout);
        pnlGeneralLayout.setHorizontalGroup(
            pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1185, Short.MAX_VALUE)
        );
        pnlGeneralLayout.setVerticalGroup(
            pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 642, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("DOANH THU TỔNG QUÁT", pnlGeneral);

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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnlGeneral;
    // End of variables declaration//GEN-END:variables
}
