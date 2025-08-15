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
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.Box;
import com.toedter.calendar.JDateChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.text.NumberFormat;
import java.util.Locale;
import java.io.File;
import java.io.FileOutputStream;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.team4.quanliquanmicay.util.XPDF;
import com.team4.quanliquanmicay.util.XExcel;
import com.team4.quanliquanmicay.DAO.UserDAO;
import com.team4.quanliquanmicay.Impl.UserDAOImpl;
import com.team4.quanliquanmicay.Entity.UserAccount;
import com.team4.quanliquanmicay.util.Xmail;

/**
 *
 * @author Asus
 */
public class ReportManagement extends javax.swing.JFrame {

    // DAOs for database access
    private BillDAO billDAO;
    private BillDetailsDAO billDetailsDAO;
    private ProductDAO productDAO;
    private UserDAO userDAO;

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
        // Build Employee Revenue Dashboard
        initEmployeeRevenueTab();
        // Build Trend Dashboard
        initTrendTab();
        // Build Report Tab
        initReportTab();
        
        // Exit action
        jButton1.addActionListener(e -> { handleExit(); e.getSource(); });
        
        XDialog.success("Chào mừng đến với hệ thống Thống kê & Báo cáo!", "Thông báo");
    }
    
    /**
     * Initialize DAOs
     */
    private void initializeDAOs() {
        billDAO = new BillDAOImpl();
        billDetailsDAO = new BillDetailsDAOImpl();
        productDAO = new ProductDAOImpl();
        userDAO = new UserDAOImpl();
    }
    
    // ========================================
    // TAB 1: DOANH THU TỔNG QUÁT (pnlGeneral)
    // ========================================
    
    // New filtering controls for quick revenue view
    private JComboBox<String> cboFilterType; // Ngày/Tuần/Tháng/Quý/Năm/Khoảng
    private JDateChooser dcFilterSingle;     // Only for Ngày
    private JDateChooser dcFilterFromRange;  // For Khoảng - from
    private JDateChooser dcFilterToRange;    // For Khoảng - to
    private JLabel lbNgayLabel;
    private JLabel lbTuLabel;
    private JLabel lbDenLabel;
    private JPanel kpiContainer;
    private JPanel tableContainer;
    private JTable tblBills;
    private DefaultTableModel billTableModel;
    
    // TAB 2: Product Revenue (jPanel4)
    private JComboBox<String> cboProdRange;
    private JPanel prodChartContainer;
    private JPanel prodTableContainer;
    private JTable tblProd;
    private DefaultTableModel prodTableModel;
    
    // TAB 2: Employee Revenue (jPanel5)
    private JComboBox<String> cboEmpRange;
    private JPanel empChartContainer;
    private JPanel empTableContainer;
    private JTable tblEmp;
    private DefaultTableModel empTableModel;
    
    // TAB 3: Trend (jPanel6) - Enhanced
    private JDateChooser dcFromDate;
    private JDateChooser dcToDate;
    private JPanel trendChartContainer;
    private JLabel trendInsightLabel;
    
    // Enhanced trend components
    private JComboBox<String> cboViewType;
    private JComboBox<String> cboChartType;
    private JCheckBox chkShowForecast;
    private JCheckBox chkShowMovingAvg;
    private JTabbedPane chartTabs;
    private JPanel mainChartPanel;
    private JPanel heatmapPanel;
    private JPanel statsPanel;
    private JPanel forecastPanel;
    private JTextArea advancedInsights;
    private JPanel kpiGrid;
    
    // Heatmap components
    private JPanel heatmapChartContainer;
    private JComboBox<String> cboHeatmapType;
    
    // Comparison components  
    private JPanel comparisonPanel;
    private JCheckBox chkCompareLastMonth;
    private JCheckBox chkCompareLastQuarter;
    private JCheckBox chkCompareLastYear;
    
    // TAB 4: REPORT (jPanel7)
    private JDateChooser dcReportFrom;
    private JDateChooser dcReportTo;
    private JRadioButton rdoPdf;
    private JRadioButton rdoExcel;
    private JCheckBox chkMerge;
    private JTextField txtEmail;
    private File lastExportedFile;
    
    // Preview Section components
    private JPanel previewStatsContainer;
    private JPanel previewTableContainer;
    private JTable tblPreview;
    private DefaultTableModel previewTableModel;
    
    // Report Template Options
    private JCheckBox chkGeneralRevenue;
    private JCheckBox chkProductRevenue;
    private JCheckBox chkEmployeeRevenue;
    private JCheckBox chkTrendAnalysis;
    private JCheckBox chkTopProducts;
    private JCheckBox chkHourlyStats;
    
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
            // New filter widgets
            cboFilterType = new JComboBox<>(new String[] { "Ngày", "Tuần", "Tháng", "Quý", "Năm", "Khoảng" });
            dcFilterSingle = new JDateChooser();
            dcFilterSingle.setDateFormatString("dd/MM/yyyy");
            dcFilterFromRange = new JDateChooser();
            dcFilterFromRange.setDateFormatString("dd/MM/yyyy");
            dcFilterToRange = new JDateChooser();
            dcFilterToRange.setDateFormatString("dd/MM/yyyy");

            // Defaults
            java.util.Calendar calNow = java.util.Calendar.getInstance();
            dcFilterSingle.setDate(calNow.getTime());
            java.util.Calendar calFrom = java.util.Calendar.getInstance();
            calFrom.set(java.util.Calendar.DAY_OF_MONTH, 1);
            dcFilterFromRange.setDate(calFrom.getTime());
            dcFilterToRange.setDate(calNow.getTime());

            JButton btnFilter = XTheme.createMiyCayButton("Lọc", e -> { refreshGeneralData(); e.getSource(); });

            // Toggle date inputs per type
            cboFilterType.addActionListener(e -> { toggleGeneralFilterInputs(); e.getSource(); });
            toggleGeneralFilterInputs();

            rightHeader.add(new JLabel("Loại:"));
            rightHeader.add(cboFilterType);
            lbNgayLabel = new JLabel("Ngày:");
            lbTuLabel = new JLabel("Từ:");
            lbDenLabel = new JLabel("Đến:");
            rightHeader.add(lbNgayLabel);
            rightHeader.add(dcFilterSingle);
            rightHeader.add(lbTuLabel);
            rightHeader.add(dcFilterFromRange);
            rightHeader.add(lbDenLabel);
            rightHeader.add(dcFilterToRange);
            rightHeader.add(btnFilter);
            header.add(rightHeader, BorderLayout.EAST);
            
            pnlGeneral.add(header, BorderLayout.NORTH);
            
            // Center section
            JPanel center = new JPanel();
            center.setBackground(Color.WHITE);
            center.setLayout(new java.awt.BorderLayout());
            
            // KPI cards row
            kpiContainer = new JPanel(new GridLayout(1, 4, 12, 12));
            kpiContainer.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 6));
            kpiContainer.setBackground(Color.WHITE);
            center.add(kpiContainer, BorderLayout.NORTH);
            
            // No chart container for this tab per new requirements
            
            // Table container (list bills)
            tableContainer = new JPanel(new BorderLayout());
            tableContainer.setBackground(Color.WHITE);
            tableContainer.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
            initBillTable();
            center.add(tableContainer, BorderLayout.CENTER);
            
            // Footer spacing only
            // No footer spacing needed
            
            pnlGeneral.add(center, BorderLayout.CENTER);
            
            refreshGeneralData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void refreshGeneralData() {
        try {
            TimeRange range = getSelectedGeneralRange();
            
            // Load data
            List<Bill> bills = billDAO.findAll();
            List<Bill> displayBills = new ArrayList<>();
            double totalRevenue = 0;
            
            for (Bill b : bills) {
                if (b == null) continue;
                if (b.getStatus() != null && b.getStatus() == 1 && withinRange(b.getCheckout(), range)) {
                    displayBills.add(b);
                    totalRevenue += b.getTotal_amount();
                }
            }
            
            // KPI cards
            int totalOrders = displayBills.size();
            double avgOrderValue = totalOrders > 0 ? (totalRevenue / totalOrders) : 0.0;
            kpiContainer.removeAll();
            kpiContainer.add(createIconKpiCard("/icons_and_images/icon/Price list.png", "Doanh thu", String.format("%,.0f VNĐ", totalRevenue), new Color(134,39,43)));
            kpiContainer.add(createIconKpiCard("/icons_and_images/icon/Notes.png", "Số hóa đơn", String.valueOf(totalOrders), new Color(204,164,133)));
            kpiContainer.add(createIconKpiCard("/icons_and_images/icon/Statistics.png", "Giá trị TB/HĐ", String.format("%,.0f VNĐ", avgOrderValue), new Color(40,167,69)));
            // Optional: unpaid/serving orders count within range (checkout null or status!=1)
            int servingOrders = 0;
            for (Bill b : bills) {
                if (b == null) continue;
                if (b.getStatus() != null && b.getStatus() == 0 && withinRange(b.getCheckin(), range)) servingOrders++;
            }
            kpiContainer.add(createIconKpiCard("/icons_and_images/icon/Task list.png", "HĐ đang phục vụ", String.valueOf(servingOrders), new Color(52,73,94)));
            kpiContainer.revalidate();
            kpiContainer.repaint();
            
            // No chart rendering required per simplified requirements

            // Update table data
            updateBillTable(displayBills);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toggleGeneralFilterInputs() {
        if (cboFilterType == null) return;
        String type = (String) cboFilterType.getSelectedItem();
        boolean isDay = "Ngày".equals(type);
        boolean isRange = "Khoảng".equals(type);
        if (dcFilterSingle != null) dcFilterSingle.setVisible(isDay);
        if (lbNgayLabel != null) lbNgayLabel.setVisible(isDay);
        if (dcFilterFromRange != null) dcFilterFromRange.setVisible(isRange);
        if (dcFilterToRange != null) dcFilterToRange.setVisible(isRange);
        if (lbTuLabel != null) lbTuLabel.setVisible(isRange);
        if (lbDenLabel != null) lbDenLabel.setVisible(isRange);
    }

    private TimeRange getSelectedGeneralRange() {
        try {
            String type = cboFilterType != null ? (String) cboFilterType.getSelectedItem() : "Ngày";
            java.util.Calendar cal = java.util.Calendar.getInstance();
            // Determine base date from the visible control
            java.util.Date base = (dcFilterSingle != null && dcFilterSingle.isVisible()) ? dcFilterSingle.getDate() : new java.util.Date();
            cal.setTime(base);
            if ("Ngày".equals(type)) {
                return new TimeRange(normalizeStartOfDay(base), normalizeEndOfDay(base));
            } else if ("Khoảng".equals(type)) {
                java.util.Date from = normalizeStartOfDay(dcFilterFromRange.getDate());
                java.util.Date to = normalizeEndOfDay(dcFilterToRange.getDate());
                if (from.after(to)) { java.util.Date tmp = from; from = to; to = tmp; }
                return new TimeRange(from, to);
            } else if ("Tháng".equals(type)) {
                cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
                java.util.Date begin = normalizeStartOfDay(cal.getTime());
                cal.add(java.util.Calendar.MONTH, 1);
                cal.add(java.util.Calendar.DAY_OF_MONTH, -1);
                java.util.Date end = normalizeEndOfDay(cal.getTime());
                return new TimeRange(begin, end);
            } else if ("Tuần".equals(type)) {
                cal.set(java.util.Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
                java.util.Date begin = normalizeStartOfDay(cal.getTime());
                cal.add(java.util.Calendar.DAY_OF_WEEK, 6);
                java.util.Date end = normalizeEndOfDay(cal.getTime());
                return new TimeRange(begin, end);
            } else if ("Năm".equals(type)) {
                cal.set(java.util.Calendar.MONTH, 0);
                cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
                java.util.Date begin = normalizeStartOfDay(cal.getTime());
                cal.set(java.util.Calendar.MONTH, 11);
                cal.set(java.util.Calendar.DAY_OF_MONTH, 31);
                java.util.Date end = normalizeEndOfDay(cal.getTime());
                return new TimeRange(begin, end);
            } else if ("Quý".equals(type)) {
                int month = cal.get(java.util.Calendar.MONTH); // 0-based
                int quarterStartMonth = (month / 3) * 3; // 0,3,6,9
                cal.set(java.util.Calendar.MONTH, quarterStartMonth);
                cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
                java.util.Date begin = normalizeStartOfDay(cal.getTime());
                cal.add(java.util.Calendar.MONTH, 3);
                cal.add(java.util.Calendar.DAY_OF_MONTH, -1);
                java.util.Date end = normalizeEndOfDay(cal.getTime());
                return new TimeRange(begin, end);
            }
        } catch (Exception ignore) { }
        return TimeRange.today();
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
    
    private void initBillTable() {
        billTableModel = new DefaultTableModel(new Object[] { "Mã HĐ", "Bàn", "Checkout", "Tổng tiền (VNĐ)" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblBills = new JTable(billTableModel);
        tblBills.setRowHeight(28);
        tblBills.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tblBills.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        tblBills.getColumnModel().getColumn(3).setCellRenderer(right);
        JScrollPane sp = new JScrollPane(tblBills);
        tableContainer.add(sp, BorderLayout.CENTER);
    }
    
    private void updateBillTable(List<Bill> bills) {
        billTableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("vi-VN"));
        for (Bill b : bills) {
            billTableModel.addRow(new Object[] {
                b.getBill_id(),
                b.getTable_number(),
                b.getCheckout() != null ? sdf.format(b.getCheckout()) : "",
                nf.format(b.getTotal_amount())
            });
        }
    }
    
    private boolean withinRange(java.util.Date date, TimeRange range) {
        if (date == null || range == null) return false;
        return !date.before(range.getBegin()) && !date.after(range.getEnd());
    }
    
    // YoY/MoM helpers removed as requested
    
    // ========================================
    // TAB 2: DOANH THU THEO NHÂN VIÊN (jPanel5)
    // ========================================

    private void initEmployeeRevenueTab() {
        try {
            jPanel5.setLayout(new BorderLayout());
            jPanel5.setBackground(Color.WHITE);

            // Header
            JPanel header = new JPanel(new BorderLayout());
            header.setBackground(Color.WHITE);
            header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel title = new JLabel("Doanh thu theo nhân viên");
            title.setFont(new Font("Tahoma", Font.BOLD, 20));
            title.setForeground(new Color(134, 39, 43));
            header.add(title, BorderLayout.WEST);

            JPanel rightHeader = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 0));
            rightHeader.setOpaque(false);
            cboEmpRange = new JComboBox<>(new String[] { "Hôm nay", "Tuần này", "Tháng này", "Quý này", "Năm nay" });
            JButton btnRefresh = XTheme.createBeButton("Làm mới", e -> { refreshEmployeeRevenueData(); e.getSource(); });
            cboEmpRange.addActionListener(e -> { refreshEmployeeRevenueData(); e.getSource(); });
            rightHeader.add(cboEmpRange);
            rightHeader.add(btnRefresh);
            header.add(rightHeader, BorderLayout.EAST);

            jPanel5.add(header, BorderLayout.NORTH);

            // Center content
            JPanel center = new JPanel();
            center.setBackground(Color.WHITE);
            center.setLayout(new javax.swing.BoxLayout(center, javax.swing.BoxLayout.Y_AXIS));

            empChartContainer = new JPanel(new BorderLayout());
            empChartContainer.setBorder(BorderFactory.createEmptyBorder(6, 12, 12, 12));
            empChartContainer.setBackground(Color.WHITE);
            empChartContainer.setPreferredSize(new Dimension(10, 360));
            center.add(empChartContainer);

            empTableContainer = new JPanel(new BorderLayout());
            empTableContainer.setBackground(Color.WHITE);
            empTableContainer.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
            initEmpTable();
            empTableContainer.setPreferredSize(new Dimension(10, 240));
            center.add(empTableContainer, BorderLayout.SOUTH);

            jPanel5.add(center, BorderLayout.CENTER);

            refreshEmployeeRevenueData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initEmpTable() {
        empTableModel = new DefaultTableModel(new Object[] { "Nhân viên", "Số hóa đơn", "Doanh thu (VNĐ)" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblEmp = new JTable(empTableModel);
        tblEmp.setRowHeight(28);
        tblEmp.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tblEmp.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));

        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        tblEmp.getColumnModel().getColumn(1).setCellRenderer(right);
        tblEmp.getColumnModel().getColumn(2).setCellRenderer(right);

        JScrollPane sp = new JScrollPane(tblEmp);
        empTableContainer.add(sp, BorderLayout.CENTER);
    }

    private TimeRange getSelectedEmpRange() {
        String selected = cboEmpRange != null ? (String) cboEmpRange.getSelectedItem() : "Hôm nay";
        if ("Tuần này".equals(selected)) return TimeRange.thisWeek();
        if ("Tháng này".equals(selected)) return TimeRange.thisMonth();
        if ("Quý này".equals(selected)) return TimeRange.thisQuarter();
        if ("Năm nay".equals(selected)) return TimeRange.thisYear();
        return TimeRange.today();
    }

    private void refreshEmployeeRevenueData() {
        try {
            TimeRange range = getSelectedEmpRange();

            // Prepare userId -> name map
            Map<String, String> userIdToName = new HashMap<>();
            try {
                List<UserAccount> users = userDAO.findAll();
                for (UserAccount u : users) {
                    if (u != null && u.getUser_id() != null) {
                        String name = (u.getFullName() != null && !u.getFullName().trim().isEmpty()) ? u.getFullName() : u.getUsername();
                        userIdToName.put(u.getUser_id(), name);
                    }
                }
            } catch (Exception ignore) { }

            List<Bill> bills = billDAO.findAll();
            Map<String, Double> empRevenue = new HashMap<>();
            Map<String, Integer> empOrders = new HashMap<>();

            for (Bill b : bills) {
                if (b == null) continue;
                if (b.getStatus() != null && b.getStatus() == 1 && withinRange(b.getCheckout(), range)) {
                    String uid = b.getUser_id();
                    if (uid == null) uid = "N/A";
                    empRevenue.merge(uid, b.getTotal_amount(), Double::sum);
                    empOrders.merge(uid, 1, Integer::sum);
                }
            }

            // Sort employees by revenue desc
            List<Map.Entry<String, Double>> sorted = new ArrayList<>(empRevenue.entrySet());
            sorted.sort((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));

            // Build chart dataset
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (Map.Entry<String, Double> e : sorted) {
                String name = userIdToName.getOrDefault(e.getKey(), e.getKey());
                dataset.addValue(e.getValue(), "Doanh thu", name);
            }

            String chartTitle = "Doanh thu theo nhân viên (" + (cboEmpRange != null ? (String) cboEmpRange.getSelectedItem() : "") + ")";
            JFreeChart chart = XChart.createBarChart(chartTitle, "Nhân viên", "VNĐ", dataset);
            ChartPanel chartPanel = XChart.createChartPanel(chart);

            empChartContainer.removeAll();
            empChartContainer.add(chartPanel, BorderLayout.CENTER);
            empChartContainer.revalidate();
            empChartContainer.repaint();

            // Update table
            updateEmpTable(sorted, userIdToName, empOrders);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateEmpTable(List<Map.Entry<String, Double>> sortedRevenue,
                                Map<String, String> userIdToName,
                                Map<String, Integer> empOrders) {
        empTableModel.setRowCount(0);
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("vi-VN"));
        for (Map.Entry<String, Double> e : sortedRevenue) {
            String uid = e.getKey();
            String name = userIdToName.getOrDefault(uid, uid);
            int orders = empOrders.getOrDefault(uid, 0);
            empTableModel.addRow(new Object[] { name, orders, nf.format(e.getValue()) });
        }
    }

    // ========================================
    // TAB 3: XU HƯỚNG DOANH THU (jPanel6)
    // ========================================

    private void initTrendTab() {
        try {
            jPanel6.setLayout(new BorderLayout());
            jPanel6.setBackground(Color.WHITE);

            // Enhanced Header with advanced controls
            JPanel header = new JPanel(new BorderLayout());
            header.setBackground(Color.WHITE);
            header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel title = new JLabel("Phân tích xu hướng doanh thu nâng cao");
            title.setFont(new Font("Tahoma", Font.BOLD, 20));
            title.setForeground(new Color(134, 39, 43));
            header.add(title, BorderLayout.WEST);

            // Enhanced controls panel
            JPanel controlsPanel = createEnhancedControlsPanel();
            header.add(controlsPanel, BorderLayout.EAST);

            jPanel6.add(header, BorderLayout.NORTH);

            // Main content with split layout
            JPanel mainContent = new JPanel(new BorderLayout());
            mainContent.setBackground(Color.WHITE);

            // Left side: Advanced insights and KPIs
            JPanel leftPanel = new JPanel();
            leftPanel.setBackground(Color.WHITE);
            leftPanel.setLayout(new javax.swing.BoxLayout(leftPanel, javax.swing.BoxLayout.Y_AXIS));
            leftPanel.setPreferredSize(new Dimension(300, 0));
            leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));

            // KPI Grid
            kpiGrid = new JPanel(new GridLayout(2, 2, 8, 8));
            kpiGrid.setBorder(BorderFactory.createTitledBorder("Chỉ số chính"));
            kpiGrid.setBackground(Color.WHITE);
            leftPanel.add(kpiGrid);

            leftPanel.add(Box.createVerticalStrut(10));

            // Advanced Insights Panel
            JPanel insightsContainer = new JPanel(new BorderLayout());
            insightsContainer.setBorder(BorderFactory.createTitledBorder("Phân tích thông minh"));
            insightsContainer.setBackground(Color.WHITE);
            
            advancedInsights = new JTextArea(8, 25);
            advancedInsights.setEditable(false);
            advancedInsights.setLineWrap(true);
            advancedInsights.setWrapStyleWord(true);
            advancedInsights.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            advancedInsights.setBackground(new Color(248, 249, 250));
            advancedInsights.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            
            JScrollPane insightScroll = new JScrollPane(advancedInsights);
            insightScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            insightsContainer.add(insightScroll, BorderLayout.CENTER);
            leftPanel.add(insightsContainer);

            leftPanel.add(Box.createVerticalStrut(10));

            // Forecast Panel
            forecastPanel = new JPanel();
            forecastPanel.setBorder(BorderFactory.createTitledBorder("Dự báo 7 ngày tới"));
            forecastPanel.setBackground(Color.WHITE);
            forecastPanel.setLayout(new javax.swing.BoxLayout(forecastPanel, javax.swing.BoxLayout.Y_AXIS));
            leftPanel.add(forecastPanel);

            mainContent.add(leftPanel, BorderLayout.WEST);

            // Right side: Multi-chart tabs
            chartTabs = new JTabbedPane(JTabbedPane.TOP);
            chartTabs.setFont(new Font("Tahoma", Font.BOLD, 12));

            // Tab 1: Main Trend Chart
            mainChartPanel = new JPanel(new BorderLayout());
            mainChartPanel.setBackground(Color.WHITE);
            trendChartContainer = new JPanel(new BorderLayout());
            trendChartContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            trendChartContainer.setBackground(Color.WHITE);
            mainChartPanel.add(trendChartContainer, BorderLayout.CENTER);
            chartTabs.addTab("📈 Xu hướng chính", mainChartPanel);

            // Tab 2: Heatmap View
            heatmapPanel = new JPanel(new BorderLayout());
            heatmapPanel.setBackground(Color.WHITE);
            heatmapPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            // Heatmap controls
            JPanel heatmapControls = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
            heatmapControls.setOpaque(false);
            cboHeatmapType = new JComboBox<>(new String[] { "Giờ x Ngày trong tuần", "Ngày x Tháng", "Nhân viên x Giờ" });
            JButton btnRefreshHeatmap = XTheme.createBeButton("Cập nhật", e -> { updateHeatmapView(getBillsInRange(normalizeStartOfDay(dcFromDate.getDate()), normalizeEndOfDay(dcToDate.getDate()))); e.getSource(); });
            
            heatmapControls.add(new JLabel("Loại heatmap:"));
            heatmapControls.add(cboHeatmapType);
            heatmapControls.add(btnRefreshHeatmap);
            heatmapPanel.add(heatmapControls, BorderLayout.NORTH);
            
            heatmapChartContainer = new JPanel(new BorderLayout());
            heatmapChartContainer.setBackground(Color.WHITE);
            heatmapPanel.add(heatmapChartContainer, BorderLayout.CENTER);
            
            chartTabs.addTab("🔥 Bản đồ nhiệt", heatmapPanel);

            // Tab 3: Comparison View
            comparisonPanel = new JPanel(new BorderLayout());
            comparisonPanel.setBackground(Color.WHITE);
            comparisonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            // Comparison controls
            JPanel comparisonControls = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
            comparisonControls.setOpaque(false);
            chkCompareLastMonth = new JCheckBox("So với tháng trước", true);
            chkCompareLastQuarter = new JCheckBox("So với quý trước");
            chkCompareLastYear = new JCheckBox("So với năm trước");
            JButton btnRefreshComparison = XTheme.createBeButton("So sánh", e -> { updateComparisonView(); e.getSource(); });
            
            comparisonControls.add(chkCompareLastMonth);
            comparisonControls.add(chkCompareLastQuarter);
            comparisonControls.add(chkCompareLastYear);
            comparisonControls.add(btnRefreshComparison);
            comparisonPanel.add(comparisonControls, BorderLayout.NORTH);
            
            JPanel comparisonChartContainer = new JPanel(new BorderLayout());
            comparisonChartContainer.setBackground(Color.WHITE);
            comparisonPanel.add(comparisonChartContainer, BorderLayout.CENTER);
            
            chartTabs.addTab("⚖️ So sánh", comparisonPanel);

            mainContent.add(chartTabs, BorderLayout.CENTER);
            jPanel6.add(mainContent, BorderLayout.CENTER);

            // Initialize with default data
            refreshEnhancedTrendData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Create enhanced controls panel for trend analysis
     */
    private JPanel createEnhancedControlsPanel() {
        JPanel controlsPanel = new JPanel();
        controlsPanel.setOpaque(false);
        controlsPanel.setLayout(new javax.swing.BoxLayout(controlsPanel, javax.swing.BoxLayout.Y_AXIS));

        // Row 1: Date range
        JPanel row1 = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 2));
        row1.setOpaque(false);
        
        dcFromDate = new JDateChooser();
        dcToDate = new JDateChooser();
        dcFromDate.setDateFormatString("dd/MM/yyyy");
        dcToDate.setDateFormatString("dd/MM/yyyy");
        dcFromDate.setPreferredSize(new Dimension(100, 25));
        dcToDate.setPreferredSize(new Dimension(100, 25));

        // Defaults: this month
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
        dcFromDate.setDate(cal.getTime());
        cal = java.util.Calendar.getInstance();
        dcToDate.setDate(cal.getTime());

        row1.add(new JLabel("Từ:"));
        row1.add(dcFromDate);
        row1.add(new JLabel("Đến:"));
        row1.add(dcToDate);

        // Row 2: View and chart type controls
        JPanel row2 = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 2));
        row2.setOpaque(false);

        cboViewType = new JComboBox<>(new String[] { "Theo ngày", "Theo tuần", "Theo tháng", "Theo giờ" });
        cboViewType.setPreferredSize(new Dimension(90, 25));
        
        cboChartType = new JComboBox<>(new String[] { "Line Chart", "Area Chart", "Bar Chart" });
        cboChartType.setPreferredSize(new Dimension(90, 25));

        row2.add(new JLabel("Hiển thị:"));
        row2.add(cboViewType);
        row2.add(new JLabel("Dạng:"));
        row2.add(cboChartType);

        // Row 3: Advanced options
        JPanel row3 = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 2));
        row3.setOpaque(false);

        chkCompareLastYear = new JCheckBox("So sánh năm trước");
        chkShowForecast = new JCheckBox("Hiển thị dự báo");
        chkShowMovingAvg = new JCheckBox("Đường MA7");
        
        // Make checkboxes smaller
        chkCompareLastYear.setFont(new Font("Tahoma", Font.PLAIN, 10));
        chkShowForecast.setFont(new Font("Tahoma", Font.PLAIN, 10));
        chkShowMovingAvg.setFont(new Font("Tahoma", Font.PLAIN, 10));

        row3.add(chkCompareLastYear);
        row3.add(chkShowForecast);
        row3.add(chkShowMovingAvg);

        // Row 4: Action button
        JPanel row4 = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 2));
        row4.setOpaque(false);
        
        JButton btnRefresh = XTheme.createMiyCayButton("Phân tích", e -> { refreshEnhancedTrendData(); e.getSource(); });
        btnRefresh.setPreferredSize(new Dimension(80, 28));
        row4.add(btnRefresh);

        // Add change listeners
        cboViewType.addActionListener(e -> refreshEnhancedTrendData());
        cboChartType.addActionListener(e -> refreshEnhancedTrendData());
        chkCompareLastYear.addActionListener(e -> refreshEnhancedTrendData());
        chkShowForecast.addActionListener(e -> refreshEnhancedTrendData());
        chkShowMovingAvg.addActionListener(e -> refreshEnhancedTrendData());

        controlsPanel.add(row1);
        controlsPanel.add(row2);
        controlsPanel.add(row3);
        controlsPanel.add(row4);

        return controlsPanel;
    }

    private void refreshTrendData() {
        try {
            java.util.Date from = normalizeStartOfDay(dcFromDate.getDate());
            java.util.Date to = normalizeEndOfDay(dcToDate.getDate());
            if (from.after(to)) {
                // Swap if invalid
                java.util.Date tmp = from; from = to; to = tmp;
                dcFromDate.setDate(from);
                dcToDate.setDate(to);
            }

            // Aggregate revenue by day within range
            Map<String, Double> byDay = new java.util.LinkedHashMap<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

            java.util.Calendar cursor = java.util.Calendar.getInstance();
            cursor.setTime(from);
            java.util.Calendar end = java.util.Calendar.getInstance();
            end.setTime(to);

            while (!cursor.after(end)) {
                byDay.put(sdf.format(cursor.getTime()), 0.0);
                cursor.add(java.util.Calendar.DAY_OF_MONTH, 1);
            }

            List<Bill> bills = billDAO.findAll();
            for (Bill b : bills) {
                if (b == null) continue;
                if (b.getStatus() != null && b.getStatus() == 1 && b.getCheckout() != null) {
                    java.util.Date d = b.getCheckout();
                    if (!d.before(from) && !d.after(to)) {
                        String key = sdf.format(d);
                        byDay.merge(key, b.getTotal_amount(), Double::sum);
                    }
                }
            }

            // Build XY dataset (x = index, y = revenue)
            org.jfree.data.xy.XYSeries series = new org.jfree.data.xy.XYSeries("Doanh thu");
            int index = 1;
            for (Map.Entry<String, Double> e : byDay.entrySet()) {
                series.add(index++, e.getValue());
            }
            org.jfree.data.xy.XYSeriesCollection ds = new org.jfree.data.xy.XYSeriesCollection(series);

            String title = "Doanh thu theo ngày (" + new SimpleDateFormat("dd/MM/yyyy").format(from)
                         + " - " + new SimpleDateFormat("dd/MM/yyyy").format(to) + ")";
            JFreeChart chart = XChart.createLineChart(title, "Ngày (chỉ số)", "VNĐ", ds);
            ChartPanel panel = XChart.createChartPanel(chart);

            trendChartContainer.removeAll();
            trendChartContainer.add(panel, BorderLayout.CENTER);
            trendChartContainer.revalidate();
            trendChartContainer.repaint();

            // Generate insight vs previous period
            String insight = generateTrendInsight(byDay, from, to);
            trendInsightLabel.setText(insight);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String generateTrendInsight(Map<String, Double> currentByDay, java.util.Date from, java.util.Date to) {
        double currentTotal = currentByDay.values().stream().mapToDouble(Double::doubleValue).sum();

        long days = Math.max(1L, (to.getTime() - from.getTime()) / (24L * 60L * 60L * 1000L) + 1);
        java.util.Calendar prevFromCal = java.util.Calendar.getInstance();
        prevFromCal.setTime(from);
        prevFromCal.add(java.util.Calendar.DAY_OF_MONTH, (int) -days);
        java.util.Calendar prevToCal = java.util.Calendar.getInstance();
        prevToCal.setTime(from);
        prevToCal.add(java.util.Calendar.DAY_OF_MONTH, -1);

        double prevTotal = 0.0;
        try {
            List<Bill> bills = billDAO.findAll();
            java.util.Date prevFrom = normalizeStartOfDay(prevFromCal.getTime());
            java.util.Date prevTo = normalizeEndOfDay(prevToCal.getTime());
            for (Bill b : bills) {
                if (b == null) continue;
                if (b.getStatus() != null && b.getStatus() == 1 && b.getCheckout() != null) {
                    java.util.Date d = b.getCheckout();
                    if (!d.before(prevFrom) && !d.after(prevTo)) {
                        prevTotal += b.getTotal_amount();
                    }
                }
            }
        } catch (Exception ignore) { }

        if (prevTotal <= 0 && currentTotal <= 0) return "Chưa có doanh thu trong các khoảng thời gian đã chọn.";
        if (prevTotal <= 0) return "Doanh thu đã phát sinh trong giai đoạn này (không có dữ liệu kỳ trước để so sánh).";

        double change = (currentTotal - prevTotal) / prevTotal * 100.0;
        String sign = change >= 0 ? "tăng" : "giảm";
        return String.format("Doanh thu giai đoạn hiện tại %s %.1f%% so với giai đoạn trước.", sign, Math.abs(change));
    }

    private java.util.Date normalizeStartOfDay(java.util.Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private java.util.Date normalizeEndOfDay(java.util.Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
        cal.set(java.util.Calendar.MINUTE, 59);
        cal.set(java.util.Calendar.SECOND, 59);
        cal.set(java.util.Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    // ========================================
    // TAB 4: BÁO CÁO (jPanel7)
    // ========================================

    private void initReportTab() {
        try {
            jPanel7.setLayout(new BorderLayout());
            jPanel7.setBackground(Color.WHITE);

            // Header
            JPanel header = new JPanel(new BorderLayout());
            header.setBackground(Color.WHITE);
            header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel title = new JLabel("Xuất báo cáo quản lý");
            title.setFont(new Font("Tahoma", Font.BOLD, 20));
            title.setForeground(new Color(134, 39, 43));
            header.add(title, BorderLayout.WEST);

            // Add refresh preview button to header
            JButton btnRefreshPreview = XTheme.createBeButton("Làm mới xem trước", e -> { refreshReportPreview(); e.getSource(); });
            header.add(btnRefreshPreview, BorderLayout.EAST);

            jPanel7.add(header, BorderLayout.NORTH);

            // Main content with split layout
            JPanel mainContent = new JPanel(new BorderLayout());
            mainContent.setBackground(Color.WHITE);

            // Left side: Controls
            JPanel leftPanel = new JPanel();
            leftPanel.setBackground(Color.WHITE);
            leftPanel.setLayout(new javax.swing.BoxLayout(leftPanel, javax.swing.BoxLayout.Y_AXIS));
            leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            leftPanel.setPreferredSize(new Dimension(400, 0));

            // Date range section
            JPanel dateSection = createSectionPanel("Khoảng thời gian");
            JPanel row1 = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 8));
            row1.setOpaque(false);
            dcReportFrom = new JDateChooser();
            dcReportTo = new JDateChooser();
            dcReportFrom.setDateFormatString("dd/MM/yyyy");
            dcReportTo.setDateFormatString("dd/MM/yyyy");
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
            dcReportFrom.setDate(cal.getTime());
            cal = java.util.Calendar.getInstance();
            dcReportTo.setDate(cal.getTime());
            
            // Add change listeners to refresh preview when dates change
            dcReportFrom.addPropertyChangeListener("date", e -> refreshReportPreview());
            dcReportTo.addPropertyChangeListener("date", e -> refreshReportPreview());
            
            row1.add(new JLabel("Từ ngày:"));
            row1.add(dcReportFrom);
            row1.add(new JLabel("Đến ngày:"));
            row1.add(dcReportTo);
            dateSection.add(row1);

            // Report Template Options section
            JPanel templateSection = createSectionPanel("Nội dung báo cáo");
            chkGeneralRevenue = new JCheckBox("Doanh thu tổng quát", true);
            chkProductRevenue = new JCheckBox("Doanh thu theo món", true);
            chkEmployeeRevenue = new JCheckBox("Doanh thu theo nhân viên", true);
            chkTrendAnalysis = new JCheckBox("Phân tích xu hướng", false);
            chkTopProducts = new JCheckBox("Top sản phẩm bán chạy", true);
            chkHourlyStats = new JCheckBox("Thống kê theo giờ", false);
            
            // Add change listeners to refresh preview when options change
            chkGeneralRevenue.addActionListener(e -> refreshReportPreview());
            chkProductRevenue.addActionListener(e -> refreshReportPreview());
            chkEmployeeRevenue.addActionListener(e -> refreshReportPreview());
            chkTrendAnalysis.addActionListener(e -> refreshReportPreview());
            chkTopProducts.addActionListener(e -> refreshReportPreview());
            chkHourlyStats.addActionListener(e -> refreshReportPreview());

            JPanel templateGrid = new JPanel(new GridLayout(3, 2, 5, 5));
            templateGrid.setOpaque(false);
            templateGrid.add(chkGeneralRevenue);
            templateGrid.add(chkProductRevenue);
            templateGrid.add(chkEmployeeRevenue);
            templateGrid.add(chkTrendAnalysis);
            templateGrid.add(chkTopProducts);
            templateGrid.add(chkHourlyStats);
            templateSection.add(templateGrid);

            // Export format section
            JPanel formatSection = createSectionPanel("Định dạng xuất");
            JPanel row2 = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 8));
            row2.setOpaque(false);
            rdoPdf = new JRadioButton("PDF", true);
            rdoExcel = new JRadioButton("Excel");
            ButtonGroup bg = new ButtonGroup();
            bg.add(rdoPdf);
            bg.add(rdoExcel);
            chkMerge = new JCheckBox("Gộp nhiều thống kê vào 1 file", true);
            row2.add(rdoPdf);
            row2.add(rdoExcel);
            row2.add(chkMerge);
            formatSection.add(row2);

            // Email section
            JPanel emailSection = createSectionPanel("Gửi email");
            JPanel row3 = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 8));
            row3.setOpaque(false);
            txtEmail = new JTextField(25);
            row3.add(new JLabel("Email:"));
            row3.add(txtEmail);
            emailSection.add(row3);

            // Action buttons
            JPanel actionSection = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 15));
            actionSection.setOpaque(false);
            JButton btnExport = XTheme.createMiyCayButton("Xuất báo cáo", e -> { handleExportReport(); e.getSource(); });
            JButton btnEmail = XTheme.createBeButton("Gửi email báo cáo", e -> { handleSendEmail(); e.getSource(); });
            actionSection.add(btnExport);
            actionSection.add(btnEmail);

            leftPanel.add(dateSection);
            leftPanel.add(Box.createVerticalStrut(10));
            leftPanel.add(templateSection);
            leftPanel.add(Box.createVerticalStrut(10));
            leftPanel.add(formatSection);
            leftPanel.add(Box.createVerticalStrut(10));
            leftPanel.add(emailSection);
            leftPanel.add(Box.createVerticalStrut(15));
            leftPanel.add(actionSection);
            leftPanel.add(Box.createVerticalGlue());

            // Right side: Preview Section
            JPanel rightPanel = new JPanel(new BorderLayout());
            rightPanel.setBackground(Color.WHITE);
            rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JLabel previewTitle = new JLabel("Xem trước báo cáo");
            previewTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
            previewTitle.setForeground(new Color(134, 39, 43));
            previewTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            rightPanel.add(previewTitle, BorderLayout.NORTH);

            JPanel previewContent = new JPanel();
            previewContent.setBackground(Color.WHITE);
            previewContent.setLayout(new javax.swing.BoxLayout(previewContent, javax.swing.BoxLayout.Y_AXIS));

            // Preview stats cards
            previewStatsContainer = new JPanel(new GridLayout(2, 2, 10, 10));
            previewStatsContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
            previewStatsContainer.setBackground(Color.WHITE);
            previewContent.add(previewStatsContainer);

            // Preview table
            previewTableContainer = new JPanel(new BorderLayout());
            previewTableContainer.setBackground(Color.WHITE);
            previewTableContainer.setBorder(BorderFactory.createTitledBorder("Dữ liệu mẫu"));
            initPreviewTable();
            previewContent.add(previewTableContainer);

            rightPanel.add(previewContent, BorderLayout.CENTER);

            mainContent.add(leftPanel, BorderLayout.WEST);
            mainContent.add(rightPanel, BorderLayout.CENTER);
            jPanel7.add(mainContent, BorderLayout.CENTER);

            // Initial preview refresh
            refreshReportPreview();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleExportReport() {
        try {
            java.util.Date from = normalizeStartOfDay(dcReportFrom.getDate());
            java.util.Date to = normalizeEndOfDay(dcReportTo.getDate());
            if (from == null || to == null) { XDialog.error("Vui lòng chọn đủ ngày.", "Lỗi"); return; }
            if (from.after(to)) { java.util.Date tmp = from; from = to; to = tmp; dcReportFrom.setDate(from); dcReportTo.setDate(to); }

            // Check if at least one report type is selected
            if (!chkGeneralRevenue.isSelected() && !chkProductRevenue.isSelected() && 
                !chkEmployeeRevenue.isSelected() && !chkTrendAnalysis.isSelected() &&
                !chkTopProducts.isSelected() && !chkHourlyStats.isSelected()) {
                XDialog.error("Vui lòng chọn ít nhất một loại báo cáo.", "Lỗi");
                return;
            }

            String label = new SimpleDateFormat("dd/MM/yyyy").format(from) + " - " + new SimpleDateFormat("dd/MM/yyyy").format(to);

            // Collect data within range
            List<Bill> bills = billDAO.findAll();
            List<Bill> paid = new ArrayList<>();
            for (Bill b : bills) {
                if (b != null && b.getStatus() != null && b.getStatus() == 1 && b.getCheckout() != null) {
                    if (!b.getCheckout().before(from) && !b.getCheckout().after(to)) paid.add(b);
                }
            }

            if (rdoPdf.isSelected()) {
                if (!chkMerge.isSelected()) {
                    // Export single section based on first selected option
                    if (chkGeneralRevenue.isSelected()) {
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                Map<String, Double> byDay = new java.util.TreeMap<>();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
                for (Bill b : paid) byDay.merge(sdf.format(b.getCheckout()), b.getTotal_amount(), Double::sum);
                for (Map.Entry<String, Double> e : byDay.entrySet()) dataset.addValue(e.getValue(), "Doanh thu", e.getKey());
                JFreeChart dayChart = XChart.createBarChart("Doanh thu theo ngày", "Ngày", "VNĐ", dataset);
                    lastExportedFile = XPDF.exportGeneralRevenue(dayChart, label);
                    }
                } else {
                    // Create comprehensive PDF with selected sections
                    lastExportedFile = createComprehensivePDF(paid, label, from, to);
                }
                XDialog.success("Đã xuất PDF: " + lastExportedFile.getAbsolutePath(), "Xuất báo cáo");
            } else {
                if (!chkMerge.isSelected()) {
                    // Export single Excel sheet based on first selected option
                    if (chkGeneralRevenue.isSelected()) {
                        lastExportedFile = XExcel.exportGeneralRevenueBills(paid, label);
                    }
                } else {
                    // Create comprehensive Excel with selected sections
                    lastExportedFile = createComprehensiveExcel(paid, label, from, to);
                }
                XDialog.success("Đã xuất Excel: " + (lastExportedFile != null ? lastExportedFile.getAbsolutePath() : ""), "Xuất báo cáo");
            }
        } catch (Exception ex) {
            XDialog.error("Lỗi xuất báo cáo: " + ex.getMessage(), "Lỗi");
        }
    }

    private void handleSendEmail() {
        try {
            String to = txtEmail.getText() != null ? txtEmail.getText().trim() : "";
            if (to.isEmpty()) { XDialog.error("Vui lòng nhập email.", "Lỗi"); return; }
            if (lastExportedFile == null || !lastExportedFile.exists()) { XDialog.error("Vui lòng xuất báo cáo trước.", "Lỗi"); return; }

            Xmail.sendEmailWithAttachment(to, "Báo cáo doanh thu", "Đính kèm báo cáo doanh thu.", lastExportedFile);
            XDialog.success("Đã gửi email tới: " + to, "Gửi email");
        } catch (Exception ex) {
            XDialog.error("Lỗi gửi email: " + ex.getMessage(), "Lỗi");
        }
    }
    
    // ========================================
    // PREVIEW SECTION METHODS
    // ========================================
    
    /**
     * Create a section panel with title border
     */
    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            title,
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Tahoma", Font.BOLD, 12),
            new Color(134, 39, 43)
        ));
        return panel;
    }
    
    /**
     * Initialize preview table
     */
    private void initPreviewTable() {
        previewTableModel = new DefaultTableModel(new Object[] { "Loại báo cáo", "Số lượng dữ liệu", "Trạng thái" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblPreview = new JTable(previewTableModel);
        tblPreview.setRowHeight(25);
        tblPreview.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tblPreview.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));
        
        // Set column widths
        tblPreview.getColumnModel().getColumn(0).setPreferredWidth(150);
        tblPreview.getColumnModel().getColumn(1).setPreferredWidth(80);
        tblPreview.getColumnModel().getColumn(2).setPreferredWidth(80);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tblPreview.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblPreview.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        
        JScrollPane sp = new JScrollPane(tblPreview);
        sp.setPreferredSize(new Dimension(0, 150));
        previewTableContainer.add(sp, BorderLayout.CENTER);
    }
    
    /**
     * Refresh the report preview based on current selections
     */
    private void refreshReportPreview() {
        try {
            if (dcReportFrom == null || dcReportTo == null) return;
            
            java.util.Date from = dcReportFrom.getDate();
            java.util.Date to = dcReportTo.getDate();
            if (from == null || to == null) return;
            
            from = normalizeStartOfDay(from);
            to = normalizeEndOfDay(to);
            if (from.after(to)) {
                java.util.Date tmp = from; from = to; to = tmp;
            }
            
            // Calculate preview stats
            TimeRange range = new TimeRange(from, to);
            List<Bill> bills = billDAO.findAll();
            List<Bill> paidBills = new ArrayList<>();
            double totalRevenue = 0;
            
            for (Bill b : bills) {
                if (b == null) continue;
                if (b.getStatus() != null && b.getStatus() == 1 && withinRange(b.getCheckout(), range)) {
                    paidBills.add(b);
                    totalRevenue += b.getTotal_amount();
                }
            }
            
            int totalOrders = paidBills.size();
            double avgOrderValue = totalOrders > 0 ? (totalRevenue / totalOrders) : 0.0;
            
            // Count unique employees
            java.util.Set<String> uniqueEmployees = new java.util.HashSet<>();
            for (Bill b : paidBills) {
                if (b.getUser_id() != null) uniqueEmployees.add(b.getUser_id());
            }
            int employeeCount = uniqueEmployees.size();
            
            // Update preview stats cards
            updatePreviewStats(totalRevenue, totalOrders, avgOrderValue, employeeCount);
            
            // Update preview table
            updatePreviewTable(paidBills.size());
            
            // Update checkbox states
            updateCheckboxStates();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Update preview statistics cards
     */
    private void updatePreviewStats(double totalRevenue, int totalOrders, double avgOrderValue, int employeeCount) {
        previewStatsContainer.removeAll();
        
        // Create mini KPI cards
        previewStatsContainer.add(createMiniKpiCard("Tổng doanh thu", 
            String.format("%,.0f VNĐ", totalRevenue), new Color(134,39,43)));
        previewStatsContainer.add(createMiniKpiCard("Số hóa đơn", 
            String.valueOf(totalOrders), new Color(204,164,133)));
        previewStatsContainer.add(createMiniKpiCard("Giá trị TB/HĐ", 
            String.format("%,.0f VNĐ", avgOrderValue), new Color(40,167,69)));
        previewStatsContainer.add(createMiniKpiCard("Nhân viên", 
            String.valueOf(employeeCount), new Color(52,73,94)));
        
        previewStatsContainer.revalidate();
        previewStatsContainer.repaint();
    }
    
    /**
     * Create mini KPI card for preview
     */
    private JPanel createMiniKpiCard(String label, String value, Color valueColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JLabel lbTitle = new JLabel(label);
        lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 10));
        lbTitle.setForeground(new Color(108, 117, 125));

        JLabel lbValue = new JLabel(value);
        lbValue.setFont(new Font("Tahoma", Font.BOLD, 14));
        lbValue.setForeground(valueColor);
        lbValue.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(lbTitle, BorderLayout.NORTH);
        card.add(lbValue, BorderLayout.CENTER);
        return card;
    }
    
    /**
     * Update preview table with report sections
     */
    private void updatePreviewTable(int totalBills) {
        previewTableModel.setRowCount(0);
        
        if (chkGeneralRevenue.isSelected()) {
            previewTableModel.addRow(new Object[] { "Doanh thu tổng quát", totalBills, "✓ Sẵn sàng" });
        }
        
        if (chkProductRevenue.isSelected()) {
            // Count products with sales in the period
            int productCount = getProductCountInPeriod();
            previewTableModel.addRow(new Object[] { "Doanh thu theo món", productCount, productCount > 0 ? "✓ Sẵn sàng" : "⚠ Không có dữ liệu" });
        }
        
        if (chkEmployeeRevenue.isSelected()) {
            // Count employees with sales in the period
            int empCount = getEmployeeCountInPeriod();
            previewTableModel.addRow(new Object[] { "Doanh thu theo nhân viên", empCount, empCount > 0 ? "✓ Sẵn sàng" : "⚠ Không có dữ liệu" });
        }
        
        if (chkTrendAnalysis.isSelected()) {
            long dayCount = getDayCountInPeriod();
            previewTableModel.addRow(new Object[] { "Phân tích xu hướng", (int)dayCount + " ngày", dayCount > 1 ? "✓ Sẵn sàng" : "⚠ Cần > 1 ngày" });
        }
        
        if (chkTopProducts.isSelected()) {
            int topProductCount = Math.min(10, getProductCountInPeriod());
            previewTableModel.addRow(new Object[] { "Top sản phẩm bán chạy", topProductCount, topProductCount > 0 ? "✓ Sẵn sàng" : "⚠ Không có dữ liệu" });
        }
        
        if (chkHourlyStats.isSelected()) {
            previewTableModel.addRow(new Object[] { "Thống kê theo giờ", totalBills, totalBills > 0 ? "✓ Sẵn sàng" : "⚠ Không có dữ liệu" });
        }
    }
    
    /**
     * Get count of products that have sales in the selected period
     */
    private int getProductCountInPeriod() {
        try {
            java.util.Date from = normalizeStartOfDay(dcReportFrom.getDate());
            java.util.Date to = normalizeEndOfDay(dcReportTo.getDate());
            TimeRange range = new TimeRange(from, to);
            
            List<Bill> bills = billDAO.findAll();
            List<BillDetails> billDetails = billDetailsDAO.findAll();
            
            Map<Integer, Bill> billIdToBill = new HashMap<>();
            for (Bill b : bills) {
                if (b != null && b.getBill_id() != null) billIdToBill.put(b.getBill_id(), b);
            }
            
            java.util.Set<String> productIds = new java.util.HashSet<>();
            for (BillDetails d : billDetails) {
                if (d == null) continue;
                Bill bill = billIdToBill.get(d.getBill_id());
                if (bill == null || bill.getStatus() == null || bill.getStatus() != 1) continue;
                java.util.Date when = bill.getCheckout() != null ? bill.getCheckout() : bill.getCheckin();
                if (when == null || !withinRange(when, range)) continue;
                if (d.getProduct_id() != null) productIds.add(d.getProduct_id());
            }
            
            return productIds.size();
        } catch (Exception ex) {
            return 0;
        }
    }
    
    /**
     * Get count of employees that have sales in the selected period
     */
    private int getEmployeeCountInPeriod() {
        try {
            java.util.Date from = normalizeStartOfDay(dcReportFrom.getDate());
            java.util.Date to = normalizeEndOfDay(dcReportTo.getDate());
            TimeRange range = new TimeRange(from, to);
            
            List<Bill> bills = billDAO.findAll();
            java.util.Set<String> employeeIds = new java.util.HashSet<>();
            
            for (Bill b : bills) {
                if (b == null) continue;
                if (b.getStatus() != null && b.getStatus() == 1 && withinRange(b.getCheckout(), range)) {
                    if (b.getUser_id() != null) employeeIds.add(b.getUser_id());
                }
            }
            
            return employeeIds.size();
        } catch (Exception ex) {
            return 0;
        }
    }
    
    /**
     * Get count of days in the selected period
     */
    private long getDayCountInPeriod() {
        try {
            java.util.Date from = dcReportFrom.getDate();
            java.util.Date to = dcReportTo.getDate();
            if (from == null || to == null) return 0;
            
            long diffMs = Math.abs(to.getTime() - from.getTime());
            return diffMs / (24 * 60 * 60 * 1000) + 1;
        } catch (Exception ex) {
                        return 0;
        }
    }
    
    /**
     * Update checkbox enabled/disabled states based on available data
     */
    private void updateCheckboxStates() {
        try {
            if (dcReportFrom == null || dcReportTo == null) return;
            
            java.util.Date from = dcReportFrom.getDate();
            java.util.Date to = dcReportTo.getDate();
            if (from == null || to == null) return;
            
            from = normalizeStartOfDay(from);
            to = normalizeEndOfDay(to);
            TimeRange range = new TimeRange(from, to);
            
            // Check if we have bills in the period
            List<Bill> bills = billDAO.findAll();
            boolean hasBills = bills.stream().anyMatch(b -> 
                b != null && b.getStatus() != null && b.getStatus() == 1 && 
                b.getCheckout() != null && withinRange(b.getCheckout(), range));
            
            // General revenue - always available if there are bills
            chkGeneralRevenue.setEnabled(hasBills);
            if (!hasBills && chkGeneralRevenue.isSelected()) {
                chkGeneralRevenue.setSelected(false);
            }
            
            // Employee revenue - check if there are employees with sales
            int empCount = getEmployeeCountInPeriod();
            chkEmployeeRevenue.setEnabled(empCount > 0);
            if (empCount == 0 && chkEmployeeRevenue.isSelected()) {
                chkEmployeeRevenue.setSelected(false);
            }
            
            // Product revenue - check if there are products with sales
            int prodCount = getProductCountInPeriod();
            chkProductRevenue.setEnabled(prodCount > 0);
            if (prodCount == 0 && chkProductRevenue.isSelected()) {
                chkProductRevenue.setSelected(false);
            }
            
            // Trend analysis - need at least 2 days
            long dayCount = getDayCountInPeriod();
            chkTrendAnalysis.setEnabled(dayCount > 1);
            if (dayCount <= 1 && chkTrendAnalysis.isSelected()) {
                chkTrendAnalysis.setSelected(false);
            }
            
            // Top products - same as product revenue
            chkTopProducts.setEnabled(prodCount > 0);
            if (prodCount == 0 && chkTopProducts.isSelected()) {
                chkTopProducts.setSelected(false);
            }
            
            // Hourly stats - available if there are bills
            chkHourlyStats.setEnabled(hasBills);
            if (!hasBills && chkHourlyStats.isSelected()) {
                chkHourlyStats.setSelected(false);
            }
            
        } catch (Exception ex) {
            // If error, enable all checkboxes
            chkGeneralRevenue.setEnabled(true);
            chkEmployeeRevenue.setEnabled(true);
            chkProductRevenue.setEnabled(true);
            chkTrendAnalysis.setEnabled(true);
            chkTopProducts.setEnabled(true);
            chkHourlyStats.setEnabled(true);
        }
    }
    
    /**
     * Create comprehensive PDF report with selected sections
     */
    private File createComprehensivePDF(List<Bill> paid, String label, java.util.Date from, java.util.Date to) throws Exception {
        File out = new File("bao_cao_tong_hop.pdf");
        com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
        com.itextpdf.text.pdf.PdfWriter.getInstance(doc, new FileOutputStream(out));
        doc.open();

        // Vietnamese-friendly fonts
        com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 16, com.itextpdf.text.Font.BOLD);
        com.itextpdf.text.Font sectionFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 14, com.itextpdf.text.Font.BOLD);
        com.itextpdf.text.Font normalFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.NORMAL);
        
        // Try to load Vietnamese font
        try (java.io.InputStream is = getClass().getResourceAsStream("/fonts/DejaVuSans.ttf")) {
            if (is != null) {
                File tmpFont = File.createTempFile("dejavu-", ".ttf");
                java.nio.file.Files.copy(is, tmpFont.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                com.itextpdf.text.pdf.BaseFont bf = com.itextpdf.text.pdf.BaseFont.createFont(tmpFont.getAbsolutePath(), com.itextpdf.text.pdf.BaseFont.IDENTITY_H, com.itextpdf.text.pdf.BaseFont.EMBEDDED);
                titleFont = new com.itextpdf.text.Font(bf, 16, com.itextpdf.text.Font.BOLD);
                sectionFont = new com.itextpdf.text.Font(bf, 14, com.itextpdf.text.Font.BOLD);
                normalFont = new com.itextpdf.text.Font(bf, 12, com.itextpdf.text.Font.NORMAL);
                tmpFont.delete();
            }
        } catch (Exception ignore) {}
        
        doc.add(new com.itextpdf.text.Paragraph("BÁO CÁO TỔNG HỢP DOANH THU", titleFont));
        doc.add(new com.itextpdf.text.Paragraph("Thời gian: " + label, normalFont));
        doc.add(new com.itextpdf.text.Paragraph("Ngày xuất: " + new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()), normalFont));
        doc.add(new com.itextpdf.text.Paragraph("\n"));

        if (chkGeneralRevenue.isSelected()) {
            doc.add(new com.itextpdf.text.Paragraph("1. DOANH THU TỔNG QUÁT", sectionFont));
            
            // Summary stats
            double totalRevenue = paid.stream().mapToDouble(Bill::getTotal_amount).sum();
            int totalBills = paid.size();
            double avgBill = totalBills > 0 ? totalRevenue / totalBills : 0;
            
            doc.add(new com.itextpdf.text.Paragraph("• Tổng doanh thu: " + String.format("%,.0f VNĐ", totalRevenue), normalFont));
            doc.add(new com.itextpdf.text.Paragraph("• Số hóa đơn: " + totalBills, normalFont));
            doc.add(new com.itextpdf.text.Paragraph("• Giá trị trung bình/hóa đơn: " + String.format("%,.0f VNĐ", avgBill), normalFont));
            doc.add(new com.itextpdf.text.Paragraph("\n"));
            
            // Chart
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            Map<String, Double> byDay = new java.util.TreeMap<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
            for (Bill b : paid) byDay.merge(sdf.format(b.getCheckout()), b.getTotal_amount(), Double::sum);
            for (Map.Entry<String, Double> e : byDay.entrySet()) dataset.addValue(e.getValue(), "Doanh thu", e.getKey());
            JFreeChart dayChart = XChart.createBarChart("Doanh thu theo ngày", "Ngày", "VNĐ", dataset);
            addChartToPDF(doc, dayChart);
        }

                if (chkEmployeeRevenue.isSelected()) {
            doc.add(new com.itextpdf.text.Paragraph("2. DOANH THU THEO NHÂN VIÊN", sectionFont));
            
            Map<String, Double> empRevenue = new HashMap<>();
            Map<String, Integer> empOrders = new HashMap<>();
            Map<String, String> userIdToName = new HashMap<>();
            try {
                List<UserAccount> users = userDAO.findAll();
                for (UserAccount u : users) {
                    if (u != null && u.getUser_id() != null) {
                        String name = (u.getFullName() != null && !u.getFullName().trim().isEmpty()) ? u.getFullName() : u.getUsername();
                        userIdToName.put(u.getUser_id(), name);
                    }
                }
            } catch (Exception ignore) { }
            for (Bill b : paid) {
                String uid = b.getUser_id();
                if (uid == null) uid = "N/A";
                empRevenue.merge(uid, b.getTotal_amount(), Double::sum);
                empOrders.merge(uid, 1, Integer::sum);
            }
            
            // Top performers
            List<Map.Entry<String, Double>> sortedEmp = new ArrayList<>(empRevenue.entrySet());
            sortedEmp.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
            
            doc.add(new com.itextpdf.text.Paragraph("Top 5 nhân viên có doanh thu cao nhất:", normalFont));
            int count = 0;
            for (Map.Entry<String, Double> e : sortedEmp) {
                if (count++ >= 5) break;
                String name = userIdToName.getOrDefault(e.getKey(), e.getKey());
                int orders = empOrders.getOrDefault(e.getKey(), 0);
                doc.add(new com.itextpdf.text.Paragraph("• " + name + ": " + String.format("%,.0f VNĐ", e.getValue()) + " (" + orders + " HĐ)", normalFont));
            }
            doc.add(new com.itextpdf.text.Paragraph("\n"));
            
            DefaultCategoryDataset empDs = new DefaultCategoryDataset();
            for (Map.Entry<String, Double> e : empRevenue.entrySet()) {
                String name = userIdToName.getOrDefault(e.getKey(), e.getKey());
                empDs.addValue(e.getValue(), "Doanh thu", name);
            }
            JFreeChart empChart = XChart.createBarChart("Doanh thu theo nhân viên", "Nhân viên", "VNĐ", empDs);
            addChartToPDF(doc, empChart);
        }

        if (chkProductRevenue.isSelected()) {
            doc.add(new com.itextpdf.text.Paragraph("3. DOANH THU THEO MÓN", sectionFont));
            
            TimeRange range = new TimeRange(from, to);
            List<BillDetails> billDetails = billDetailsDAO.findAll();
            List<Product> products = productDAO.findAll();
            
            Map<Integer, Bill> billIdToBill = new HashMap<>();
            for (Bill b : paid) {
                if (b != null && b.getBill_id() != null) billIdToBill.put(b.getBill_id(), b);
            }
            Map<String, String> productIdToName = new HashMap<>();
            for (Product p : products) {
                if (p != null && p.getProductId() != null) productIdToName.put(p.getProductId(), p.getProductName());
            }
            
            Map<String, Double> revenueByProductId = new HashMap<>();
            Map<String, Integer> quantityByProductId = new HashMap<>();
            for (BillDetails d : billDetails) {
                if (d == null) continue;
                Bill bill = billIdToBill.get(d.getBill_id());
                if (bill == null || bill.getStatus() == null || bill.getStatus() != 1) continue;
                java.util.Date when = bill.getCheckout() != null ? bill.getCheckout() : bill.getCheckin();
                if (when == null || !withinRange(when, range)) continue;
                String productId = d.getProduct_id();
                if (productId != null) {
                    revenueByProductId.merge(productId, d.getTotalPrice(), Double::sum);
                    quantityByProductId.merge(productId, d.getAmount(), Integer::sum);
                }
            }
            
            List<Map.Entry<String, Double>> sortedProducts = new ArrayList<>(revenueByProductId.entrySet());
            sortedProducts.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
            
            doc.add(new com.itextpdf.text.Paragraph("Top 10 món bán chạy nhất:", normalFont));
            int count = 0;
            for (Map.Entry<String, Double> e : sortedProducts) {
                if (count++ >= 10) break;
                String name = productIdToName.getOrDefault(e.getKey(), e.getKey());
                int qty = quantityByProductId.getOrDefault(e.getKey(), 0);
                doc.add(new com.itextpdf.text.Paragraph("• " + name + ": " + String.format("%,.0f VNĐ", e.getValue()) + " (" + qty + " phần)", normalFont));
            }
            doc.add(new com.itextpdf.text.Paragraph("\n"));
            
            DefaultPieDataset<String> prodDs = new DefaultPieDataset<>();
            count = 0;
            for (Map.Entry<String, Double> e : sortedProducts) {
                if (count++ >= 10) break;
                String name = productIdToName.getOrDefault(e.getKey(), e.getKey());
                prodDs.setValue(name, e.getValue());
            }
            JFreeChart prodChart = XChart.createPieChart("Top 10 món bán chạy", prodDs);
            addChartToPDF(doc, prodChart);
        }
        
        // Add more sections based on selected checkboxes
        if (chkTrendAnalysis.isSelected()) {
            doc.add(new com.itextpdf.text.Paragraph("4. PHÂN TÍCH XU HƯỚNG", sectionFont));
            
            // Calculate trend vs previous period
            long days = Math.max(1L, (to.getTime() - from.getTime()) / (24L * 60L * 60L * 1000L) + 1);
            java.util.Calendar prevFromCal = java.util.Calendar.getInstance();
            prevFromCal.setTime(from);
            prevFromCal.add(java.util.Calendar.DAY_OF_MONTH, (int) -days);
            java.util.Calendar prevToCal = java.util.Calendar.getInstance();
            prevToCal.setTime(from);
            prevToCal.add(java.util.Calendar.DAY_OF_MONTH, -1);
            
            double currentRevenue = paid.stream().mapToDouble(Bill::getTotal_amount).sum();
            double prevRevenue = 0.0;
            try {
                List<Bill> allBills = billDAO.findAll();
                java.util.Date prevFrom = normalizeStartOfDay(prevFromCal.getTime());
                java.util.Date prevTo = normalizeEndOfDay(prevToCal.getTime());
                for (Bill b : allBills) {
                    if (b != null && b.getStatus() != null && b.getStatus() == 1 && b.getCheckout() != null) {
                        if (!b.getCheckout().before(prevFrom) && !b.getCheckout().after(prevTo)) {
                            prevRevenue += b.getTotal_amount();
                        }
                    }
                }
            } catch (Exception ignore) { }
            
            if (prevRevenue > 0) {
                double change = (currentRevenue - prevRevenue) / prevRevenue * 100.0;
                String trend = change >= 0 ? "tăng" : "giảm";
                doc.add(new com.itextpdf.text.Paragraph("• So với cùng kỳ trước: " + trend + " " + String.format("%.1f%%", Math.abs(change)), normalFont));
            }
            doc.add(new com.itextpdf.text.Paragraph("• Doanh thu trung bình/ngày: " + String.format("%,.0f VNĐ", currentRevenue / days), normalFont));
            doc.add(new com.itextpdf.text.Paragraph("\n"));
        }
        
        if (chkTopProducts.isSelected()) {
            doc.add(new com.itextpdf.text.Paragraph("5. THỐNG KÊ KHÁC", sectionFont));
            
            // Peak hours analysis
            Map<Integer, Integer> hourlyOrders = new HashMap<>();
            for (Bill b : paid) {
                if (b.getCheckout() != null) {
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.setTime(b.getCheckout());
                    int hour = cal.get(java.util.Calendar.HOUR_OF_DAY);
                    hourlyOrders.merge(hour, 1, Integer::sum);
                }
            }
            
            // Find peak hour
            int peakHour = hourlyOrders.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(12);
            int peakOrders = hourlyOrders.getOrDefault(peakHour, 0);
            
            doc.add(new com.itextpdf.text.Paragraph("• Giờ cao điểm: " + peakHour + ":00 (" + peakOrders + " hóa đơn)", normalFont));
            doc.add(new com.itextpdf.text.Paragraph("• Tổng số bàn phục vụ: " + paid.stream().mapToInt(Bill::getTable_number).distinct().count(), normalFont));
            doc.add(new com.itextpdf.text.Paragraph("\n"));
        }

                    doc.close();
        return out;
    }
    
    /**
     * Helper method to add chart to PDF
     */
    private void addChartToPDF(com.itextpdf.text.Document doc, JFreeChart chart) throws Exception {
        java.awt.image.BufferedImage img = chart.createBufferedImage(900, 500);
        File tmp = File.createTempFile("chart-", ".png");
        javax.imageio.ImageIO.write(img, "png", tmp);
        com.itextpdf.text.Image cimg = com.itextpdf.text.Image.getInstance(tmp.getAbsolutePath());
        cimg.scaleToFit(520, 320);
        doc.add(cimg);
        doc.add(new com.itextpdf.text.Paragraph("\n"));
        tmp.delete();
    }
    
        /**
     * Create comprehensive Excel report with selected sections
     */
    private File createComprehensiveExcel(List<Bill> paid, String label, java.util.Date from, java.util.Date to) throws Exception {
        org.apache.poi.hssf.usermodel.HSSFWorkbook wb = new org.apache.poi.hssf.usermodel.HSSFWorkbook();
        
        if (chkGeneralRevenue.isSelected()) {
            org.apache.poi.hssf.usermodel.HSSFSheet s1 = wb.createSheet("Doanh thu tong quat");
            int r = 0;
            s1.createRow(r++).createCell(0).setCellValue("BÁO CÁO DOANH THU TỔNG QUÁT");
            org.apache.poi.hssf.usermodel.HSSFRow row1 = s1.createRow(r++);
            row1.createCell(0).setCellValue("Thời gian:");
            row1.createCell(1).setCellValue(label);
            org.apache.poi.hssf.usermodel.HSSFRow row2 = s1.createRow(r++);
            row2.createCell(0).setCellValue("Ngày xuất:");
            row2.createCell(1).setCellValue(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()));
            
            r++; // Empty row
            
            // Summary stats
            double totalRevenue = paid.stream().mapToDouble(Bill::getTotal_amount).sum();
            int totalBills = paid.size();
            double avgBill = totalBills > 0 ? totalRevenue / totalBills : 0;
            
            s1.createRow(r++).createCell(0).setCellValue("TỔNG QUAN:");
            org.apache.poi.hssf.usermodel.HSSFRow summaryRow1 = s1.createRow(r++);
            summaryRow1.createCell(0).setCellValue("Tổng doanh thu:");
            summaryRow1.createCell(1).setCellValue(totalRevenue);
            org.apache.poi.hssf.usermodel.HSSFRow summaryRow2 = s1.createRow(r++);
            summaryRow2.createCell(0).setCellValue("Số hóa đơn:");
            summaryRow2.createCell(1).setCellValue(totalBills);
            org.apache.poi.hssf.usermodel.HSSFRow summaryRow3 = s1.createRow(r++);
            summaryRow3.createCell(0).setCellValue("Giá trị TB/HĐ:");
            summaryRow3.createCell(1).setCellValue(avgBill);
            
            r++; // Empty row
            
            // Detailed bills
            s1.createRow(r++).createCell(0).setCellValue("CHI TIẾT HÓA ĐƠN:");
            org.apache.poi.hssf.usermodel.HSSFRow header = s1.createRow(r++);
            header.createCell(0).setCellValue("Bill ID");
            header.createCell(1).setCellValue("Bàn");
            header.createCell(2).setCellValue("Checkout");
            header.createCell(3).setCellValue("Tổng tiền");
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            for (Bill b : paid) {
                org.apache.poi.hssf.usermodel.HSSFRow rr = s1.createRow(r++);
                rr.createCell(0).setCellValue(b.getBill_id());
                rr.createCell(1).setCellValue(b.getTable_number());
                rr.createCell(2).setCellValue(b.getCheckout() != null ? sdf2.format(b.getCheckout()) : "");
                rr.createCell(3).setCellValue(b.getTotal_amount());
            }
            for (int i = 0; i < 4; i++) s1.autoSizeColumn(i);
        }

        if (chkEmployeeRevenue.isSelected()) {
            org.apache.poi.hssf.usermodel.HSSFSheet s2 = wb.createSheet("Theo nhan vien");
            int r2 = 0;
            s2.createRow(r2++).createCell(0).setCellValue("DOANH THU THEO NHÂN VIÊN");
            s2.createRow(r2++).createCell(0).setCellValue("Thời gian: " + label);
            r2++; // Empty row
            
            org.apache.poi.hssf.usermodel.HSSFRow h2 = s2.createRow(r2++);
            h2.createCell(0).setCellValue("Nhân viên");
            h2.createCell(1).setCellValue("Số HĐ");
            h2.createCell(2).setCellValue("Doanh thu");
            
            Map<String, Double> empRevenue = new HashMap<>();
            Map<String, Integer> empOrders = new HashMap<>();
            Map<String, String> userIdToName = new HashMap<>();
            try {
                List<UserAccount> users = userDAO.findAll();
                for (UserAccount u : users) {
                    if (u != null && u.getUser_id() != null) {
                        String name = (u.getFullName() != null && !u.getFullName().trim().isEmpty()) ? u.getFullName() : u.getUsername();
                        userIdToName.put(u.getUser_id(), name);
                    }
                }
            } catch (Exception ignore) { }
            for (Bill b : paid) {
                String uid = b.getUser_id();
                if (uid == null) uid = "N/A";
                empRevenue.merge(uid, b.getTotal_amount(), Double::sum);
                empOrders.merge(uid, 1, Integer::sum);
            }
            
            // Sort by revenue desc
            List<Map.Entry<String, Double>> sortedEmp = new ArrayList<>(empRevenue.entrySet());
            sortedEmp.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
            
            for (Map.Entry<String, Double> e : sortedEmp) {
                org.apache.poi.hssf.usermodel.HSSFRow row = s2.createRow(r2++);
                String name = userIdToName.getOrDefault(e.getKey(), e.getKey());
                row.createCell(0).setCellValue(name);
                row.createCell(1).setCellValue(empOrders.getOrDefault(e.getKey(), 0));
                row.createCell(2).setCellValue(e.getValue());
            }
            for (int i = 0; i < 3; i++) s2.autoSizeColumn(i);
        }
        
        if (chkProductRevenue.isSelected()) {
            org.apache.poi.hssf.usermodel.HSSFSheet s3 = wb.createSheet("Theo mon an");
            int r3 = 0;
            s3.createRow(r3++).createCell(0).setCellValue("DOANH THU THEO MÓN");
            s3.createRow(r3++).createCell(0).setCellValue("Thời gian: " + label);
            r3++; // Empty row
            
            // Get product data
            TimeRange range = new TimeRange(from, to);
            List<BillDetails> billDetails = billDetailsDAO.findAll();
            List<Product> products = productDAO.findAll();
            
            Map<Integer, Bill> billIdToBill = new HashMap<>();
            for (Bill b : paid) {
                if (b != null && b.getBill_id() != null) billIdToBill.put(b.getBill_id(), b);
            }
            Map<String, String> productIdToName = new HashMap<>();
            for (Product p : products) {
                if (p != null && p.getProductId() != null) productIdToName.put(p.getProductId(), p.getProductName());
            }
            
            Map<String, Double> revenueByProductId = new HashMap<>();
            Map<String, Integer> quantityByProductId = new HashMap<>();
            for (BillDetails d : billDetails) {
                if (d == null) continue;
                Bill bill = billIdToBill.get(d.getBill_id());
                if (bill == null || bill.getStatus() == null || bill.getStatus() != 1) continue;
                java.util.Date when = bill.getCheckout() != null ? bill.getCheckout() : bill.getCheckin();
                if (when == null || !withinRange(when, range)) continue;
                String productId = d.getProduct_id();
                if (productId != null) {
                    revenueByProductId.merge(productId, d.getTotalPrice(), Double::sum);
                    quantityByProductId.merge(productId, d.getAmount(), Integer::sum);
                }
            }
            
            List<Map.Entry<String, Double>> sortedProducts = new ArrayList<>(revenueByProductId.entrySet());
            sortedProducts.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
            
            org.apache.poi.hssf.usermodel.HSSFRow h3 = s3.createRow(r3++);
            h3.createCell(0).setCellValue("Tên món");
            h3.createCell(1).setCellValue("Số lượng");
            h3.createCell(2).setCellValue("Doanh thu");
            
            for (Map.Entry<String, Double> e : sortedProducts) {
                org.apache.poi.hssf.usermodel.HSSFRow row = s3.createRow(r3++);
                String name = productIdToName.getOrDefault(e.getKey(), e.getKey());
                int qty = quantityByProductId.getOrDefault(e.getKey(), 0);
                row.createCell(0).setCellValue(name);
                row.createCell(1).setCellValue(qty);
                row.createCell(2).setCellValue(e.getValue());
            }
            for (int i = 0; i < 3; i++) s3.autoSizeColumn(i);
        }

        File out = new File("bao_cao_tong_hop.xls");
        try (FileOutputStream fos = new FileOutputStream(out)) { wb.write(fos); }
        wb.close();
        return out;
    }
    
    // Export / Print actions
    // ========================================
    
    /**
     * Create product revenue pie chart with REAL database data
     */
    private void createProductRevenueChart() {
        try {
            jPanel4.setLayout(new BorderLayout());
            jPanel4.setBackground(Color.WHITE);

            // Header with range selector
            JPanel header = new JPanel(new BorderLayout());
            header.setBackground(Color.WHITE);
            header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel title = new JLabel("Doanh thu theo món");
            title.setFont(new Font("Tahoma", Font.BOLD, 20));
            title.setForeground(new Color(134, 39, 43));
            header.add(title, BorderLayout.WEST);

            JPanel rightHeader = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 0));
            rightHeader.setOpaque(false);
            cboProdRange = new JComboBox<>(new String[] { "Ngày", "Tháng", "Năm" });
            // Default to Tháng to increase chance of visible data
            cboProdRange.setSelectedItem("Tháng");
            JButton btnRefresh = XTheme.createBeButton("Làm mới", e -> { refreshProductRevenueData(); e.getSource(); });
            cboProdRange.addActionListener(e -> { refreshProductRevenueData(); e.getSource(); });
            rightHeader.add(new JLabel("Khoảng:"));
            rightHeader.add(cboProdRange);
            rightHeader.add(btnRefresh);
            header.add(rightHeader, BorderLayout.EAST);

            jPanel4.add(header, BorderLayout.NORTH);

            // Center with chart and table
            JPanel center = new JPanel();
            center.setBackground(Color.WHITE);
            center.setLayout(new javax.swing.BoxLayout(center, javax.swing.BoxLayout.Y_AXIS));

            prodChartContainer = new JPanel(new BorderLayout());
            prodChartContainer.setBorder(BorderFactory.createEmptyBorder(6, 12, 12, 12));
            prodChartContainer.setBackground(Color.WHITE);
            prodChartContainer.setPreferredSize(new Dimension(10, 360));
            center.add(prodChartContainer);

            prodTableContainer = new JPanel(new BorderLayout());
            prodTableContainer.setBackground(Color.WHITE);
            prodTableContainer.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
            initProdTable();
            prodTableContainer.setPreferredSize(new Dimension(10, 240));
            center.add(prodTableContainer);

            jPanel4.add(center, BorderLayout.CENTER);

            refreshProductRevenueData();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorPanel(jPanel4, "Lỗi khi tạo giao diện doanh thu theo món: " + e.getMessage());
        }
    }

    private void initProdTable() {
        prodTableModel = new DefaultTableModel(new Object[] { "Món", "Số lượng bán", "Doanh thu (VNĐ)" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblProd = new JTable(prodTableModel);
        tblProd.setRowHeight(28);
        tblProd.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tblProd.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));

        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        tblProd.getColumnModel().getColumn(1).setCellRenderer(right);
        tblProd.getColumnModel().getColumn(2).setCellRenderer(right);

        JScrollPane sp = new JScrollPane(tblProd);
        prodTableContainer.add(sp, BorderLayout.CENTER);
    }

    private TimeRange getSelectedProdRange() {
        String selected = cboProdRange != null ? (String) cboProdRange.getSelectedItem() : "Ngày";
        if ("Tháng".equals(selected)) return TimeRange.thisMonth();
        if ("Năm".equals(selected)) return TimeRange.thisYear();
        return TimeRange.today();
    }
    
    /**
     * Enhanced trend data refresh with advanced analytics
     */
    private void refreshEnhancedTrendData() {
        try {
            if (dcFromDate == null || dcToDate == null) return;
            
            java.util.Date from = normalizeStartOfDay(dcFromDate.getDate());
            java.util.Date to = normalizeEndOfDay(dcToDate.getDate());
            if (from == null || to == null) return;
            
            if (from.after(to)) {
                java.util.Date tmp = from; from = to; to = tmp;
                dcFromDate.setDate(from);
                dcToDate.setDate(to);
            }
            
            // Get data based on view type
            Map<String, Double> dataByPeriod = getDataByViewType(from, to);
            List<Bill> bills = getBillsInRange(from, to);
            
            // Update KPI Grid
            updateTrendKPIs(bills, dataByPeriod);
            
            // Update main chart
            updateMainTrendChart(dataByPeriod, from, to);
            
            // Update heatmap
            updateHeatmapView(bills);
            
            // Update comparison view
            updateComparisonView();
            
            // Generate advanced insights
            generateAdvancedInsights(bills, dataByPeriod, from, to);
            
            // Update forecast if enabled
            if (chkShowForecast != null && chkShowForecast.isSelected()) {
                updateForecastPanel(dataByPeriod);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Get data aggregated by selected view type
     */
    private Map<String, Double> getDataByViewType(java.util.Date from, java.util.Date to) {
        Map<String, Double> result = new java.util.LinkedHashMap<>();
        String viewType = cboViewType != null ? (String) cboViewType.getSelectedItem() : "Theo ngày";
        
        List<Bill> bills = billDAO.findAll();
        SimpleDateFormat sdf;
        
        switch (viewType) {
            case "Theo giờ":
                sdf = new SimpleDateFormat("HH");
                // Initialize hours 0-23
                for (int h = 0; h < 24; h++) {
                    result.put(String.format("%02d:00", h), 0.0);
                }
                break;
            case "Theo tuần":
                sdf = new SimpleDateFormat("'Tuần' w");
                break;
            case "Theo tháng":
                sdf = new SimpleDateFormat("MM/yyyy");
                break;
            default: // Theo ngày
                sdf = new SimpleDateFormat("dd/MM");
                // Initialize all days in range
                java.util.Calendar cursor = java.util.Calendar.getInstance();
                cursor.setTime(from);
                java.util.Calendar end = java.util.Calendar.getInstance();
                end.setTime(to);
                while (!cursor.after(end)) {
                    result.put(sdf.format(cursor.getTime()), 0.0);
                    cursor.add(java.util.Calendar.DAY_OF_MONTH, 1);
                }
                break;
        }
        
        // Aggregate data
        for (Bill b : bills) {
            if (b == null || b.getStatus() == null || b.getStatus() != 1 || b.getCheckout() == null) continue;
            if (b.getCheckout().before(from) || b.getCheckout().after(to)) continue;
            
            String key = sdf.format(b.getCheckout());
            result.merge(key, b.getTotal_amount(), Double::sum);
        }
        
        return result;
    }
    
    /**
     * Get bills within date range
     */
    private List<Bill> getBillsInRange(java.util.Date from, java.util.Date to) {
        List<Bill> result = new ArrayList<>();
        List<Bill> allBills = billDAO.findAll();
        
        for (Bill b : allBills) {
            if (b == null || b.getStatus() == null || b.getStatus() != 1 || b.getCheckout() == null) continue;
            if (!b.getCheckout().before(from) && !b.getCheckout().after(to)) {
                result.add(b);
            }
        }
        
        return result;
    }
    
    /**
     * Update KPI grid with trend metrics
     */
    private void updateTrendKPIs(List<Bill> bills, Map<String, Double> dataByPeriod) {
        if (kpiGrid == null) return;
        
        kpiGrid.removeAll();
        
        // Total Revenue
        double totalRevenue = bills.stream().mapToDouble(Bill::getTotal_amount).sum();
        
        // Average per period
        double avgPerPeriod = dataByPeriod.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        
        // Growth rate (vs previous period)
        double growthRate = calculateGrowthRate(bills);
        
        // Peak period
        String peakPeriod = dataByPeriod.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("N/A");
        
        kpiGrid.add(createTrendKpiCard("Tổng doanh thu", String.format("%,.0f VNĐ", totalRevenue), new Color(134,39,43)));
        kpiGrid.add(createTrendKpiCard("TB/" + getViewTypeLabel(), String.format("%,.0f VNĐ", avgPerPeriod), new Color(40,167,69)));
        kpiGrid.add(createTrendKpiCard("Tăng trưởng", String.format("%.1f%%", growthRate), growthRate >= 0 ? new Color(40,167,69) : new Color(220,53,69)));
        kpiGrid.add(createTrendKpiCard("Cao điểm", peakPeriod, new Color(255,193,7)));
        
        kpiGrid.revalidate();
        kpiGrid.repaint();
    }
    
    /**
     * Create KPI card for trend tab
     */
    private JPanel createTrendKpiCard(String label, String value, Color valueColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JLabel lbTitle = new JLabel(label);
        lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 10));
        lbTitle.setForeground(new Color(108, 117, 125));

        JLabel lbValue = new JLabel(value);
        lbValue.setFont(new Font("Tahoma", Font.BOLD, 12));
        lbValue.setForeground(valueColor);
        lbValue.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(lbTitle, BorderLayout.NORTH);
        card.add(lbValue, BorderLayout.CENTER);
        return card;
    }
    
    /**
     * Update main trend chart
     */
    private void updateMainTrendChart(Map<String, Double> dataByPeriod, java.util.Date from, java.util.Date to) {
        try {
            if (trendChartContainer == null) return;
            
            String chartType = cboChartType != null ? (String) cboChartType.getSelectedItem() : "Line Chart";
            String viewType = cboViewType != null ? (String) cboViewType.getSelectedItem() : "Theo ngày";
            
            if ("Line Chart".equals(chartType) || "Area Chart".equals(chartType)) {
                // XY Chart for line/area
                org.jfree.data.xy.XYSeries series = new org.jfree.data.xy.XYSeries("Doanh thu");
                int index = 1;
                for (Map.Entry<String, Double> e : dataByPeriod.entrySet()) {
                    series.add(index++, e.getValue());
                }
                
                org.jfree.data.xy.XYSeriesCollection ds = new org.jfree.data.xy.XYSeriesCollection(series);
                
                // Add moving average if enabled
                if (chkShowMovingAvg != null && chkShowMovingAvg.isSelected()) {
                    org.jfree.data.xy.XYSeries maSeries = calculateMovingAverage(dataByPeriod, 7);
                    ds.addSeries(maSeries);
                }
                
                String title = "Xu hướng doanh thu " + viewType.toLowerCase();
                JFreeChart chart = XChart.createLineChart(title, getViewTypeLabel(), "VNĐ", ds);
                ChartPanel panel = XChart.createChartPanel(chart);
                
                trendChartContainer.removeAll();
                trendChartContainer.add(panel, BorderLayout.CENTER);
            } else {
                // Bar Chart
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                for (Map.Entry<String, Double> e : dataByPeriod.entrySet()) {
                    dataset.addValue(e.getValue(), "Doanh thu", e.getKey());
                }
                
                String title = "Doanh thu " + viewType.toLowerCase();
                JFreeChart chart = XChart.createBarChart(title, getViewTypeLabel(), "VNĐ", dataset);
                ChartPanel panel = XChart.createChartPanel(chart);
                
                trendChartContainer.removeAll();
                trendChartContainer.add(panel, BorderLayout.CENTER);
            }
            
            trendChartContainer.revalidate();
            trendChartContainer.repaint();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Get view type label for chart axis
     */
    private String getViewTypeLabel() {
        if (cboViewType == null) return "Ngày";
        String viewType = (String) cboViewType.getSelectedItem();
        switch (viewType) {
            case "Theo giờ": return "Giờ";
            case "Theo tuần": return "Tuần";
            case "Theo tháng": return "Tháng";
            default: return "Ngày";
        }
    }
    
    /**
     * Calculate simple moving average
     */
    private org.jfree.data.xy.XYSeries calculateMovingAverage(Map<String, Double> data, int window) {
        org.jfree.data.xy.XYSeries maSeries = new org.jfree.data.xy.XYSeries("MA" + window);
        
        List<Double> values = new ArrayList<>(data.values());
        for (int i = window - 1; i < values.size(); i++) {
            double sum = 0;
            for (int j = i - window + 1; j <= i; j++) {
                sum += values.get(j);
            }
            double ma = sum / window;
            maSeries.add(i + 1, ma);
        }
        
        return maSeries;
    }
    
    /**
     * Calculate growth rate vs previous period
     */
    private double calculateGrowthRate(List<Bill> currentBills) {
        try {
            if (dcFromDate == null || dcToDate == null) return 0.0;
            
            java.util.Date from = dcFromDate.getDate();
            java.util.Date to = dcToDate.getDate();
            if (from == null || to == null) return 0.0;
            
            long days = (to.getTime() - from.getTime()) / (24 * 60 * 60 * 1000) + 1;
            
            java.util.Calendar prevFromCal = java.util.Calendar.getInstance();
            prevFromCal.setTime(from);
            prevFromCal.add(java.util.Calendar.DAY_OF_MONTH, (int) -days);
            
            java.util.Calendar prevToCal = java.util.Calendar.getInstance();
            prevToCal.setTime(from);
            prevToCal.add(java.util.Calendar.DAY_OF_MONTH, -1);
            
            List<Bill> prevBills = getBillsInRange(prevFromCal.getTime(), prevToCal.getTime());
            
            double currentRevenue = currentBills.stream().mapToDouble(Bill::getTotal_amount).sum();
            double prevRevenue = prevBills.stream().mapToDouble(Bill::getTotal_amount).sum();
            
            if (prevRevenue == 0) return currentRevenue > 0 ? 100.0 : 0.0;
            
            return ((currentRevenue - prevRevenue) / prevRevenue) * 100.0;
            
        } catch (Exception ex) {
            return 0.0;
        }
    }
    
    /**
     * Real heatmap implementation
     */
    private void updateHeatmapView(List<Bill> bills) {
        if (heatmapChartContainer == null) return;
        
        try {
            String heatmapType = cboHeatmapType != null ? (String) cboHeatmapType.getSelectedItem() : "Giờ x Ngày trong tuần";
            
            switch (heatmapType) {
                case "Giờ x Ngày trong tuần":
                    createHourDayHeatmap(bills);
                    break;
                case "Ngày x Tháng":
                    createDayMonthHeatmap(bills);
                    break;
                case "Nhân viên x Giờ":
                    createEmployeeHourHeatmap(bills);
                    break;
                default:
                    createHourDayHeatmap(bills);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showHeatmapError("Lỗi tạo heatmap: " + ex.getMessage());
        }
    }
    
    /**
     * Create Hour x Day of Week heatmap
     */
    private void createHourDayHeatmap(List<Bill> bills) {
        // Create data matrix: 7 days x 24 hours
        double[][] heatmapData = new double[7][24];
        String[] dayLabels = {"CN", "T2", "T3", "T4", "T5", "T6", "T7"};
        String[] hourLabels = new String[24];
        
        // Initialize hour labels
        for (int h = 0; h < 24; h++) {
            hourLabels[h] = String.format("%02d", h);
        }
        
        // Populate data matrix
        for (Bill bill : bills) {
            if (bill.getCheckout() == null) continue;
            
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(bill.getCheckout());
            
            int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK) - 1; // 0=Sunday
            int hour = cal.get(java.util.Calendar.HOUR_OF_DAY);
            
            heatmapData[dayOfWeek][hour] += bill.getTotal_amount();
        }
        
        // Create visual heatmap
        JPanel heatmapVisual = createHeatmapVisual(heatmapData, dayLabels, hourLabels, "Doanh thu theo Giờ x Ngày trong tuần");
        
        heatmapChartContainer.removeAll();
        heatmapChartContainer.add(heatmapVisual, BorderLayout.CENTER);
        heatmapChartContainer.revalidate();
        heatmapChartContainer.repaint();
    }
    
    /**
     * Create Day x Month heatmap
     */
    private void createDayMonthHeatmap(List<Bill> bills) {
        // Create data matrix: 12 months x 31 days
        double[][] heatmapData = new double[12][31];
        String[] monthLabels = {"T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11", "T12"};
        String[] dayLabels = new String[31];
        
        // Initialize day labels
        for (int d = 0; d < 31; d++) {
            dayLabels[d] = String.valueOf(d + 1);
        }
        
        // Populate data matrix
        for (Bill bill : bills) {
            if (bill.getCheckout() == null) continue;
            
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(bill.getCheckout());
            
            int month = cal.get(java.util.Calendar.MONTH); // 0-based
            int day = cal.get(java.util.Calendar.DAY_OF_MONTH) - 1; // 0-based
            
            if (day < 31) {
                heatmapData[month][day] += bill.getTotal_amount();
            }
        }
        
        // Create visual heatmap
        JPanel heatmapVisual = createHeatmapVisual(heatmapData, monthLabels, dayLabels, "Doanh thu theo Ngày x Tháng");
        
        heatmapChartContainer.removeAll();
        heatmapChartContainer.add(heatmapVisual, BorderLayout.CENTER);
        heatmapChartContainer.revalidate();
        heatmapChartContainer.repaint();
    }
    
    /**
     * Create Employee x Hour heatmap
     */
    private void createEmployeeHourHeatmap(List<Bill> bills) {
        // Get unique employees
        Map<String, String> userIdToName = new HashMap<>();
        try {
            List<UserAccount> users = userDAO.findAll();
            for (UserAccount u : users) {
                if (u != null && u.getUser_id() != null) {
                    String name = (u.getFullName() != null && !u.getFullName().trim().isEmpty()) ? u.getFullName() : u.getUsername();
                    userIdToName.put(u.getUser_id(), name);
                }
            }
        } catch (Exception ignore) { }
        
        // Get employees who have bills
        Set<String> activeEmployees = new HashSet<>();
        for (Bill bill : bills) {
            if (bill.getUser_id() != null) {
                activeEmployees.add(bill.getUser_id());
            }
        }
        
        List<String> employeeList = new ArrayList<>(activeEmployees);
        if (employeeList.isEmpty()) {
            showHeatmapError("Không có dữ liệu nhân viên trong khoảng thời gian này");
            return;
        }
        
        // Create data matrix: employees x 24 hours
        double[][] heatmapData = new double[employeeList.size()][24];
        String[] employeeLabels = new String[employeeList.size()];
        String[] hourLabels = new String[24];
        
        // Initialize labels
        for (int i = 0; i < employeeList.size(); i++) {
            String empId = employeeList.get(i);
            employeeLabels[i] = userIdToName.getOrDefault(empId, empId);
            if (employeeLabels[i].length() > 8) {
                employeeLabels[i] = employeeLabels[i].substring(0, 8) + "...";
            }
        }
        
        for (int h = 0; h < 24; h++) {
            hourLabels[h] = String.format("%02d", h);
        }
        
        // Populate data matrix
        for (Bill bill : bills) {
            if (bill.getCheckout() == null || bill.getUser_id() == null) continue;
            
            int empIndex = employeeList.indexOf(bill.getUser_id());
            if (empIndex == -1) continue;
            
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(bill.getCheckout());
            int hour = cal.get(java.util.Calendar.HOUR_OF_DAY);
            
            heatmapData[empIndex][hour] += bill.getTotal_amount();
        }
        
        // Create visual heatmap
        JPanel heatmapVisual = createHeatmapVisual(heatmapData, employeeLabels, hourLabels, "Doanh thu theo Nhân viên x Giờ");
        
        heatmapChartContainer.removeAll();
        heatmapChartContainer.add(heatmapVisual, BorderLayout.CENTER);
        heatmapChartContainer.revalidate();
        heatmapChartContainer.repaint();
    }
    
    /**
     * Create visual heatmap representation
     */
    private JPanel createHeatmapVisual(double[][] data, String[] rowLabels, String[] colLabels, String title) {
        JPanel heatmapPanel = new JPanel(new BorderLayout());
        heatmapPanel.setBackground(Color.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        heatmapPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Find min/max values for color scaling
        double minValue = Double.MAX_VALUE;
        double maxValue = Double.MIN_VALUE;
        
        for (double[] row : data) {
            for (double value : row) {
                if (value > 0) {
                    minValue = Math.min(minValue, value);
                    maxValue = Math.max(maxValue, value);
                }
            }
        }
        
        if (minValue == Double.MAX_VALUE) {
            minValue = 0;
            maxValue = 1;
        }
        
        // Create heatmap grid
        JPanel gridPanel = new JPanel();
        gridPanel.setBackground(Color.WHITE);
        gridPanel.setLayout(new GridLayout(rowLabels.length + 1, colLabels.length + 1, 1, 1));
        
        // Top-left corner (empty)
        JLabel corner = new JLabel("");
        corner.setOpaque(true);
        corner.setBackground(Color.LIGHT_GRAY);
        corner.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        gridPanel.add(corner);
        
        // Column headers
        for (String colLabel : colLabels) {
            JLabel header = new JLabel(colLabel);
            header.setOpaque(true);
            header.setBackground(new Color(240, 240, 240));
            header.setHorizontalAlignment(SwingConstants.CENTER);
            header.setFont(new Font("Tahoma", Font.BOLD, 9));
            header.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            gridPanel.add(header);
        }
        
        // Data rows
        for (int i = 0; i < rowLabels.length; i++) {
            // Row header
            JLabel rowHeader = new JLabel(rowLabels[i]);
            rowHeader.setOpaque(true);
            rowHeader.setBackground(new Color(240, 240, 240));
            rowHeader.setHorizontalAlignment(SwingConstants.CENTER);
            rowHeader.setFont(new Font("Tahoma", Font.BOLD, 9));
            rowHeader.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            gridPanel.add(rowHeader);
            
            // Data cells
            for (int j = 0; j < colLabels.length; j++) {
                double value = (j < data[i].length) ? data[i][j] : 0;
                
                JLabel cell = new JLabel();
                cell.setOpaque(true);
                cell.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                cell.setHorizontalAlignment(SwingConstants.CENTER);
                cell.setFont(new Font("Tahoma", Font.PLAIN, 8));
                
                if (value > 0) {
                    // Color intensity based on value
                    double intensity = (value - minValue) / (maxValue - minValue);
                    int red = (int) (255 - (intensity * 120)); // From white to red
                    int green = (int) (255 - (intensity * 180));
                    int blue = (int) (255 - (intensity * 180));
                    
                    cell.setBackground(new Color(red, green, blue));
                    cell.setText(String.format("%.0f", value / 1000) + "K");
                    cell.setForeground(intensity > 0.6 ? Color.WHITE : Color.BLACK);
                } else {
                    cell.setBackground(Color.WHITE);
                    cell.setText("");
                }
                
                // Tooltip with full value
                if (value > 0) {
                    cell.setToolTipText(String.format("%s - %s: %,.0f VNĐ", rowLabels[i], colLabels[j], value));
                }
                
                gridPanel.add(cell);
            }
        }
        
        // Wrap in scroll pane
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        heatmapPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Legend
        JPanel legendPanel = createHeatmapLegend(minValue, maxValue);
        heatmapPanel.add(legendPanel, BorderLayout.SOUTH);
        
        return heatmapPanel;
    }
    
    /**
     * Create heatmap legend
     */
    private JPanel createHeatmapLegend(double minValue, double maxValue) {
        JPanel legendPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5));
        legendPanel.setBackground(Color.WHITE);
        
        JLabel legendLabel = new JLabel("Cường độ: ");
        legendLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
        legendPanel.add(legendLabel);
        
        // Color gradient
        for (int i = 0; i < 10; i++) {
            double intensity = i / 9.0;
            int red = (int) (255 - (intensity * 120));
            int green = (int) (255 - (intensity * 180));
            int blue = (int) (255 - (intensity * 180));
            
            JLabel colorBox = new JLabel("  ");
            colorBox.setOpaque(true);
            colorBox.setBackground(new Color(red, green, blue));
            colorBox.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            legendPanel.add(colorBox);
        }
        
        JLabel minLabel = new JLabel(String.format("%.0fK", minValue / 1000));
        JLabel maxLabel = new JLabel(String.format("%.0fK", maxValue / 1000));
        minLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
        maxLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
        
        legendPanel.add(minLabel);
        legendPanel.add(new JLabel(" → "));
        legendPanel.add(maxLabel);
        
        return legendPanel;
    }
    
    /**
     * Show heatmap error message
     */
    private void showHeatmapError(String message) {
        heatmapChartContainer.removeAll();
        JLabel errorLabel = new JLabel("<html><center>❌ " + message + "</center></html>");
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        errorLabel.setForeground(Color.RED);
        heatmapChartContainer.add(errorLabel, BorderLayout.CENTER);
        heatmapChartContainer.revalidate();
        heatmapChartContainer.repaint();
    }
    
    private void generateAdvancedInsights(List<Bill> bills, Map<String, Double> dataByPeriod, java.util.Date from, java.util.Date to) {
        if (advancedInsights == null) return;
        
        StringBuilder insights = new StringBuilder();
        
        try {
            double totalRevenue = bills.stream().mapToDouble(Bill::getTotal_amount).sum();
            int totalOrders = bills.size();
            double avgOrderValue = totalOrders > 0 ? totalRevenue / totalOrders : 0;
            
            // Basic insights
            insights.append("📊 PHÂN TÍCH THÔNG MINH\n\n");
            insights.append(String.format("• Tổng cộng %d hóa đơn với doanh thu %,.0f VNĐ\n", totalOrders, totalRevenue));
            insights.append(String.format("• Giá trị trung bình mỗi hóa đơn: %,.0f VNĐ\n\n", avgOrderValue));
            
            // Peak analysis
            String peakPeriod = dataByPeriod.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
            double peakValue = dataByPeriod.values().stream().mapToDouble(Double::doubleValue).max().orElse(0);
            
            insights.append(String.format("🏆 Cao điểm: %s với %,.0f VNĐ\n", peakPeriod, peakValue));
            
            // Low analysis
            String lowPeriod = dataByPeriod.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
            double lowValue = dataByPeriod.values().stream().filter(v -> v > 0).mapToDouble(Double::doubleValue).min().orElse(0);
            
            insights.append(String.format("📉 Thấp điểm: %s với %,.0f VNĐ\n\n", lowPeriod, lowValue));
            
            // Growth insight
            double growthRate = calculateGrowthRate(bills);
            if (growthRate > 0) {
                insights.append(String.format("📈 Xu hướng tích cực: Tăng %.1f%% so với kỳ trước\n", growthRate));
            } else if (growthRate < 0) {
                insights.append(String.format("📉 Cần chú ý: Giảm %.1f%% so với kỳ trước\n", Math.abs(growthRate)));
            } else {
                insights.append("📊 Doanh thu ổn định so với kỳ trước\n");
            }
            
            // Stability analysis
            double variance = calculateVariance(dataByPeriod.values());
            if (variance < totalRevenue * 0.1) {
                insights.append("🎯 Doanh thu khá ổn định trong kỳ\n");
            } else {
                insights.append("⚡ Doanh thu có biến động lớn trong kỳ\n");
            }
            
        } catch (Exception ex) {
            insights.append("❌ Lỗi khi phân tích dữ liệu");
        }
        
        advancedInsights.setText(insights.toString());
    }
    
    private void updateForecastPanel(Map<String, Double> dataByPeriod) {
        if (forecastPanel == null) return;
        
        forecastPanel.removeAll();
        
        // Simple linear forecast
        List<Double> values = new ArrayList<>(dataByPeriod.values());
        if (values.size() < 3) {
            JLabel noData = new JLabel("Cần ít nhất 3 điểm dữ liệu");
            noData.setHorizontalAlignment(SwingConstants.CENTER);
            forecastPanel.add(noData);
        } else {
            double trend = calculateSimpleTrend(values);
            double lastValue = values.get(values.size() - 1);
            
            for (int i = 1; i <= 7; i++) {
                double forecast = lastValue + (trend * i);
                JLabel forecastLabel = new JLabel(String.format("Ngày +%d: %,.0f VNĐ", i, Math.max(0, forecast)));
                forecastLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
                forecastLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
                forecastPanel.add(forecastLabel);
            }
        }
        
        forecastPanel.revalidate();
        forecastPanel.repaint();
    }
    
    private double calculateVariance(java.util.Collection<Double> values) {
        double mean = values.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double variance = values.stream().mapToDouble(v -> Math.pow(v - mean, 2)).average().orElse(0);
        return variance;
    }
    
    private double calculateSimpleTrend(List<Double> values) {
        if (values.size() < 2) return 0;
        
        // Simple linear regression slope
        int n = values.size();
        double sumX = n * (n + 1) / 2.0;
        double sumY = values.stream().mapToDouble(Double::doubleValue).sum();
        double sumXY = 0;
        double sumX2 = 0;
        
        for (int i = 0; i < n; i++) {
            sumXY += (i + 1) * values.get(i);
            sumX2 += (i + 1) * (i + 1);
        }
        
        return (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
    }
    
    /**
     * Update comparison view with multiple time periods
     */
    private void updateComparisonView() {
        if (comparisonPanel == null) return;
        
        try {
            // Get current period data
            java.util.Date currentFrom = normalizeStartOfDay(dcFromDate.getDate());
            java.util.Date currentTo = normalizeEndOfDay(dcToDate.getDate());
            Map<String, Double> currentData = getDataByViewType(currentFrom, currentTo);
            
            // Create comparison chart
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            
            // Add current period
            for (Map.Entry<String, Double> entry : currentData.entrySet()) {
                dataset.addValue(entry.getValue(), "Kỳ hiện tại", entry.getKey());
            }
            
            // Add comparison periods based on selected checkboxes
            if (chkCompareLastMonth != null && chkCompareLastMonth.isSelected()) {
                Map<String, Double> lastMonthData = getComparisonData(currentFrom, currentTo, "month");
                for (Map.Entry<String, Double> entry : lastMonthData.entrySet()) {
                    dataset.addValue(entry.getValue(), "Tháng trước", entry.getKey());
                }
            }
            
            if (chkCompareLastQuarter != null && chkCompareLastQuarter.isSelected()) {
                Map<String, Double> lastQuarterData = getComparisonData(currentFrom, currentTo, "quarter");
                for (Map.Entry<String, Double> entry : lastQuarterData.entrySet()) {
                    dataset.addValue(entry.getValue(), "Quý trước", entry.getKey());
                }
            }
            
            if (chkCompareLastYear != null && chkCompareLastYear.isSelected()) {
                Map<String, Double> lastYearData = getComparisonData(currentFrom, currentTo, "year");
                for (Map.Entry<String, Double> entry : lastYearData.entrySet()) {
                    dataset.addValue(entry.getValue(), "Năm trước", entry.getKey());
                }
            }
            
            // Create comparison chart
            String title = "So sánh doanh thu theo " + getViewTypeLabel().toLowerCase();
            JFreeChart chart = XChart.createBarChart(title, getViewTypeLabel(), "VNĐ", dataset);
            ChartPanel chartPanel = XChart.createChartPanel(chart);
            
            // Update comparison panel
            JPanel comparisonChartContainer = (JPanel) comparisonPanel.getComponent(1);
            comparisonChartContainer.removeAll();
            comparisonChartContainer.add(chartPanel, BorderLayout.CENTER);
            comparisonChartContainer.revalidate();
            comparisonChartContainer.repaint();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            showComparisonError("Lỗi tạo biểu đồ so sánh: " + ex.getMessage());
        }
    }
    
    /**
     * Get comparison data for a specific period type
     */
    private Map<String, Double> getComparisonData(java.util.Date currentFrom, java.util.Date currentTo, String periodType) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        
        java.util.Date compareFrom, compareTo;
        
        switch (periodType) {
            case "month":
                cal.setTime(currentFrom);
                cal.add(java.util.Calendar.MONTH, -1);
                compareFrom = cal.getTime();
                cal.setTime(currentTo);
                cal.add(java.util.Calendar.MONTH, -1);
                compareTo = cal.getTime();
                break;
                
            case "quarter":
                cal.setTime(currentFrom);
                cal.add(java.util.Calendar.MONTH, -3);
                compareFrom = cal.getTime();
                cal.setTime(currentTo);
                cal.add(java.util.Calendar.MONTH, -3);
                compareTo = cal.getTime();
                break;
                
            case "year":
                cal.setTime(currentFrom);
                cal.add(java.util.Calendar.YEAR, -1);
                compareFrom = cal.getTime();
                cal.setTime(currentTo);
                cal.add(java.util.Calendar.YEAR, -1);
                compareTo = cal.getTime();
                break;
                
            default:
                return new HashMap<>();
        }
        
        return getDataByViewType(normalizeStartOfDay(compareFrom), normalizeEndOfDay(compareTo));
    }
    
    /**
     * Show comparison error message
     */
    private void showComparisonError(String message) {
        if (comparisonPanel == null) return;
        
        JPanel comparisonChartContainer = (JPanel) comparisonPanel.getComponent(1);
        comparisonChartContainer.removeAll();
        JLabel errorLabel = new JLabel("<html><center>❌ " + message + "</center></html>");
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        errorLabel.setForeground(Color.RED);
        comparisonChartContainer.add(errorLabel, BorderLayout.CENTER);
        comparisonChartContainer.revalidate();
        comparisonChartContainer.repaint();
    }

    private void refreshProductRevenueData() {
        try {
            TimeRange range = getSelectedProdRange();

            List<Bill> bills = billDAO.findAll();
            List<BillDetails> billDetails = billDetailsDAO.findAll();
            List<Product> products = productDAO.findAll();

            if (bills == null) bills = new ArrayList<>();
            if (billDetails == null) billDetails = new ArrayList<>();
            if (products == null) products = new ArrayList<>();

            // Build maps for quick lookup
            Map<Integer, Bill> billIdToBill = new HashMap<>();
            for (Bill b : bills) {
                if (b != null && b.getBill_id() != null) billIdToBill.put(b.getBill_id(), b);
            }
            Map<String, String> productIdToName = new HashMap<>();
            for (Product p : products) {
                if (p != null && p.getProductId() != null) productIdToName.put(p.getProductId(), p.getProductName());
            }

            // Always enumerate all products from DB with zero-initialized metrics
            Map<String, Double> revenueByProductId = new HashMap<>();
            Map<String, Integer> quantityByProductId = new HashMap<>();
            for (Product p : products) {
                if (p != null && p.getProductId() != null) {
                    revenueByProductId.putIfAbsent(p.getProductId(), 0.0);
                    quantityByProductId.putIfAbsent(p.getProductId(), 0);
                }
            }

            // Aggregate revenue/quantity by productId within the selected time range
            for (BillDetails d : billDetails) {
                if (d == null) continue;
                Bill bill = billIdToBill.get(d.getBill_id());
                if (bill == null) continue;
                if (bill.getStatus() == null || bill.getStatus() != 1) continue;
                java.util.Date when = bill.getCheckout() != null ? bill.getCheckout() : bill.getCheckin();
                if (when == null) continue;
                if (!withinRange(when, range)) continue;

                String productId = d.getProduct_id();
                if (productId == null) continue;
                double revenue = d.getTotalPrice();
                int qty = d.getAmount();
                revenueByProductId.merge(productId, revenue, Double::sum);
                quantityByProductId.merge(productId, qty, Integer::sum);
            }

            // Sort products by revenue desc to get top performers
            List<Map.Entry<String, Double>> sortedByRevenue = new ArrayList<>(revenueByProductId.entrySet());
            sortedByRevenue.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
            
            // Build pie dataset: only include top 10 products with revenue > 0
            DefaultPieDataset<String> ds = new DefaultPieDataset<>();
            int nonZeroCount = 0;
            int top10Count = 0;
            double otherRevenue = 0.0;
            
            for (Map.Entry<String, Double> entry : sortedByRevenue) {
                double value = entry.getValue() != null ? entry.getValue() : 0.0;
                if (value > 0) {
                    if (top10Count < 10) {
                        String productId = entry.getKey();
                        String name = productIdToName.getOrDefault(productId, productId);
                        // Use a unique label to avoid overriding slices when names duplicate
                        String uniqueLabel = name + " [" + productId + "]";
                        ds.setValue(uniqueLabel, value);
                        top10Count++;
                    } else {
                        // Add remaining products to "Others" category
                        otherRevenue += value;
                    }
                    nonZeroCount++;
                }
            }
            
            // Add "Others" slice if there are more than 10 products with revenue
            if (otherRevenue > 0) {
                ds.setValue("Khác (" + (nonZeroCount - 10) + " món)", otherRevenue);
            }
            
            if (nonZeroCount == 0) {
                ds.setValue("Không có dữ liệu", 1);
            }

            String title = "Top 10 món bán chạy nhất (" + (cboProdRange != null ? (String) cboProdRange.getSelectedItem() : "") + ")";
            JFreeChart chart = XChart.createPieChart(title, ds);
            ChartPanel panel = XChart.createChartPanel(chart);

            prodChartContainer.removeAll();
            prodChartContainer.add(panel, BorderLayout.CENTER);
            prodChartContainer.revalidate();
            prodChartContainer.repaint();

            // Update table sorted by revenue desc (still list all products including zero)
            List<Map.Entry<String, Double>> sorted = new ArrayList<>(revenueByProductId.entrySet());
            sorted.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
            updateProdTable(sorted, quantityByProductId, productIdToName);
        } catch (Exception ex) {
            ex.printStackTrace();
            showErrorPanel(jPanel4, "Lỗi tải dữ liệu doanh thu theo món: " + ex.getMessage());
        }
    }

    private void updateProdTable(List<Map.Entry<String, Double>> sortedRevenueByProductId, Map<String, Integer> quantityByProductId, Map<String, String> productIdToName) {
        prodTableModel.setRowCount(0);
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("vi-VN"));
        for (Map.Entry<String, Double> e : sortedRevenueByProductId) {
            String productId = e.getKey();
            String name = productIdToName.getOrDefault(productId, productId);
            int qty = quantityByProductId.getOrDefault(productId, 0);
            double revenue = e.getValue() != null ? e.getValue() : 0.0;
            prodTableModel.addRow(new Object[] { name, qty, nf.format(revenue) });
        }
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
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlTrendy = new javax.swing.JTabbedPane();
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

        pnlTrendy.setBackground(new java.awt.Color(204, 164, 133));
        pnlTrendy.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0), 3));
        pnlTrendy.setForeground(new java.awt.Color(255, 255, 255));
        pnlTrendy.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        pnlTrendy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        pnlTrendy.setMinimumSize(new java.awt.Dimension(245, 30));

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

        pnlTrendy.addTab("DOANH THU TỔNG QUÁT", pnlGeneral);

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

        pnlTrendy.addTab("DOANH THU THEO MÓN", jPanel4);

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

        pnlTrendy.addTab("DOANH THU THEO NHÂN VIÊN", jPanel5);

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

        pnlTrendy.addTab("XU HƯỚNG DOANH THU", jPanel6);

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

        pnlTrendy.addTab("BÁO CÁO", jPanel7);

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/icon/Exit.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlTrendy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(pnlTrendy, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    private javax.swing.JPanel pnlGeneral;
    private javax.swing.JTabbedPane pnlTrendy;
    // End of variables declaration//GEN-END:variables
}
