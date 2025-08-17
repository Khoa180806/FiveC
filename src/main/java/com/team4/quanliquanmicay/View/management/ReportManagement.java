

 /* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;

import com.team4.quanliquanmicay.util.XPDF;
import com.team4.quanliquanmicay.util.XExcel;
import com.team4.quanliquanmicay.DAO.UserDAO;
import com.team4.quanliquanmicay.Impl.UserDAOImpl;
import com.team4.quanliquanmicay.Entity.UserAccount;
import com.team4.quanliquanmicay.util.Xmail;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.BoxLayout;
import java.util.TreeMap;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.InputVerifier;
import javax.swing.JComponent;

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
    private JPanel generalChartContainer;
private JComboBox<String> cboGeneralChartType;
private JDateChooser dcGeneralFrom;
private JDateChooser dcGeneralTo;
private JPanel customGeneralDatePanel;
private javax.swing.Timer searchTimer;
    /**N
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
                System.err.println(" Missing resource: /icons_and_images/icon/Exit.png");
            }
        } catch (Exception ignore) { }
        
        // Build General Revenue Dashboard
        initGeneralDashboard();
        
        // Create charts
        // Sử dụng component mới TabProduct để tránh xung đột với logic cũ trong file này
        initProductTabComponent();
        // Build Employee Revenue Dashboard
        initEmployeeRevenueTab();
        // Build Trend Dashboard
        initTrendTab();
        // Build Report Tab
        initReportTab();
        
        // Setup tooltips and validation
        setupTooltipsAndValidation();
        
        // Setup keyboard shortcuts
        setupKeyboardShortcuts();
        
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
    
    
    // TAB 2: Product Revenue (jPanel4)
    private JComboBox<String> cboProdRange;
    private JPanel prodChartContainer;
    private JPanel prodTableContainer;
    private JTable tblProd;
    private DefaultTableModel prodTableModel;
    
    // TAB 2: Employee Revenue (jPanel5) - Enhanced
    private JComboBox<String> cboEmpRange;
    private JComboBox<String> cboEmpCompare; // So sánh với mốc thời gian khác
    private JDateChooser dcEmpFrom; // Chọn khoảng thời gian tùy ý
    private JDateChooser dcEmpTo;
    private JPanel empChartContainer;
    private JPanel empTableContainer;
    private JPanel empSecondaryChartContainer; // Biểu đồ phụ cho số hóa đơn và TB/HĐ
    private JPanel empStatsContainer; // Container cho thống kê và tỷ lệ
    private JTable tblEmp;
    private DefaultTableModel empTableModel;
    private JCheckBox chkShowComparison; // Hiển thị so sánh
    private JCheckBox chkShowProductivity; // Hiển thị năng suất
    
    // TAB 3: Trend (jPanel6) - Enhanced
    private JDateChooser dcFromDate;
    private JDateChooser dcToDate;
    private JPanel trendChartContainer;
    
    // Enhanced trend components
    private JComboBox<String> cboViewType;
    private JComboBox<String> cboChartType;
    private JCheckBox chkShowForecast;
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
    // ========================================

    // Phase 1: New components for enhanced functionality
    private JProgressBar progressBar;
    private JLabel progressLabel;
    private JPanel progressPanel;
    private JPanel quickDatePanel;
    private JButton btnToday, btnThisWeek, btnThisMonth, btnThisQuarter, btnThisYear;
    private JButton btnCustomRange;
    private JButton btnExport, btnEmail, btnRefreshPreview;
    
    // TAB 1: General Dashboard components
    private JComboBox<String> cboFilterType;
    private JPanel kpiContainer;
    private JPanel tableContainer;
    private JTable tblBills;
    private DefaultTableModel billTableModel;

    
    // Report tab components
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

    // Thêm các biến instance mới
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
            btnRefreshPreview = XTheme.createBeButton("Làm mới xem trước", e -> { refreshReportPreview(); e.getSource(); });
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

            // ========================================
            // PHASE 1.1: QUICK DATE PRESETS
            // ========================================
            JPanel dateSection = createSectionPanel("Khoảng thời gian");
            
            // Quick date presets
            quickDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
            quickDatePanel.setOpaque(false);
            
            btnToday = XTheme.createCustomButton("Hôm nay", new Color(40, 167, 69), Color.WHITE, _ -> { setDateRange(TimeRange.today()); });
            btnThisWeek = XTheme.createCustomButton("Tuần này", new Color(255, 193, 7), new Color(33, 37, 41), _ -> { setDateRange(TimeRange.thisWeek()); });
            btnThisMonth = XTheme.createCustomButton("Tháng này", new Color(134, 39, 43), Color.WHITE, _ -> { setDateRange(TimeRange.thisMonth()); });
            btnThisQuarter = XTheme.createCustomButton("Quý này", new Color(52, 144, 220), Color.WHITE, _ -> { setDateRange(TimeRange.thisQuarter()); });
            btnThisYear = XTheme.createCustomButton("Năm nay", new Color(220, 53, 69), Color.WHITE, _ -> { setDateRange(TimeRange.thisYear()); });
            btnCustomRange = XTheme.createCustomButton("Tùy chỉnh", new Color(204, 164, 133), new Color(33, 37, 41), _ -> { enableCustomDateRange(); });
            
            // Set font for buttons
            Font buttonFont = new Font("Tahoma", Font.PLAIN, 11);
            btnToday.setFont(buttonFont);
            btnThisWeek.setFont(buttonFont);
            btnThisMonth.setFont(buttonFont);
            btnThisQuarter.setFont(buttonFont);
            btnThisYear.setFont(buttonFont);
            btnCustomRange.setFont(buttonFont);
            
            // Set button sizes - wider for better text display
            Dimension buttonSize = new Dimension(85, 25);
            btnToday.setPreferredSize(buttonSize);
            btnThisWeek.setPreferredSize(buttonSize);
            btnThisMonth.setPreferredSize(buttonSize);
            btnThisQuarter.setPreferredSize(buttonSize);
            btnThisYear.setPreferredSize(buttonSize);
            btnCustomRange.setPreferredSize(buttonSize);
            
            quickDatePanel.add(btnToday);
            quickDatePanel.add(btnThisWeek);
            quickDatePanel.add(btnThisMonth);
            quickDatePanel.add(btnThisQuarter);
            quickDatePanel.add(btnThisYear);
            quickDatePanel.add(btnCustomRange);
            
            dateSection.add(quickDatePanel);
            dateSection.add(Box.createVerticalStrut(10));

            // Custom date range (initially hidden)
            JPanel customDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
            customDatePanel.setOpaque(false);
            customDatePanel.setName("customDatePanel");
            customDatePanel.setVisible(false);
            
            dcReportFrom = new JDateChooser();
            dcReportTo = new JDateChooser();
            dcReportFrom.setDateFormatString("dd/MM/yyyy");
            dcReportTo.setDateFormatString("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            dcReportFrom.setDate(cal.getTime());
            cal = Calendar.getInstance();
            dcReportTo.setDate(cal.getTime());
            
            // Add change listeners to refresh preview when dates change
            dcReportFrom.addPropertyChangeListener("date", _ -> { refreshReportPreview(); });
            dcReportTo.addPropertyChangeListener("date", _ -> { refreshReportPreview(); });
            
            customDatePanel.add(new JLabel("Từ ngày:"));
            customDatePanel.add(dcReportFrom);
            customDatePanel.add(new JLabel("Đến ngày:"));
            customDatePanel.add(dcReportTo);
            dateSection.add(customDatePanel);



            // Report Template Options section
            JPanel contentSection = createSectionPanel("Nội dung báo cáo");
            chkGeneralRevenue = new JCheckBox("Doanh thu tổng quát", true);
            chkProductRevenue = new JCheckBox("Doanh thu theo món", true);
            chkEmployeeRevenue = new JCheckBox("Doanh thu theo nhân viên", true);
            chkTrendAnalysis = new JCheckBox("Phân tích xu hướng", false);
            chkTopProducts = new JCheckBox("Top sản phẩm bán chạy", true);
            chkHourlyStats = new JCheckBox("Thống kê theo giờ", false);
            
            // Add change listeners to refresh preview when options change
            chkGeneralRevenue.addActionListener(_ -> { refreshReportPreview(); });
            chkProductRevenue.addActionListener(_ -> { refreshReportPreview(); });
            chkEmployeeRevenue.addActionListener(_ -> { refreshReportPreview(); });
            chkTrendAnalysis.addActionListener(_ -> { refreshReportPreview(); });
            chkTopProducts.addActionListener(_ -> { refreshReportPreview(); });
            chkHourlyStats.addActionListener(_ -> { refreshReportPreview(); });

            JPanel templateGrid = new JPanel(new GridLayout(3, 2, 5, 5));
            templateGrid.setOpaque(false);
            templateGrid.add(chkGeneralRevenue);
            templateGrid.add(chkProductRevenue);
            templateGrid.add(chkEmployeeRevenue);
            templateGrid.add(chkTrendAnalysis);
            templateGrid.add(chkTopProducts);
            templateGrid.add(chkHourlyStats);
            contentSection.add(templateGrid);

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

            // ========================================
            // PHASE 1.3: PROGRESS BAR
            // ========================================
            progressPanel = new JPanel(new BorderLayout());
            progressPanel.setBackground(Color.WHITE);
            progressPanel.setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0));
            progressPanel.setVisible(false);
            
            progressLabel = new JLabel("Đang tải dữ liệu...");
            progressLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
            progressLabel.setForeground(new Color(134, 39, 43));
            progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
            
            progressBar = new JProgressBar();
            progressBar.setIndeterminate(true);
            progressBar.setPreferredSize(new Dimension(0, 8));
            progressBar.setStringPainted(false);
            
            progressPanel.add(progressLabel, BorderLayout.NORTH);
            progressPanel.add(progressBar, BorderLayout.CENTER);

            // Action buttons
            JPanel actionSection = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 15));
            actionSection.setOpaque(false);
            btnExport = XTheme.createMiyCayButton("Xuất báo cáo", e -> { handleExportReport(); e.getSource(); });
            btnEmail = XTheme.createBeButton("Gửi email báo cáo", e -> { handleSendEmail(); e.getSource(); });
            actionSection.add(btnExport);
            actionSection.add(btnEmail);

            leftPanel.add(dateSection);
            leftPanel.add(Box.createVerticalStrut(10));
            leftPanel.add(contentSection);
            leftPanel.add(Box.createVerticalStrut(10));
            leftPanel.add(formatSection);
            leftPanel.add(Box.createVerticalStrut(10));
            leftPanel.add(emailSection);
            leftPanel.add(Box.createVerticalStrut(10));
            leftPanel.add(progressPanel);
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

            // Initial preview refresh with async loading
            loadDataAsync();
        } catch (Exception ex) {
            ex.printStackTrace();
            XDialog.error("Lỗi khởi tạo tab báo cáo: " + ex.getMessage(), "Lỗi");
        }
        }
    
    private void handleSendEmail() {
        try {
            String to = txtEmail.getText() != null ? txtEmail.getText().trim() : "";
            
            // Validation đầy đủ hơn
            if (to.isEmpty()) { 
                XDialog.error("Vui lòng nhập địa chỉ email.", "Lỗi"); 
                txtEmail.requestFocus();
                return; 
            }
            
            // Kiểm tra định dạng email
            if (!isValidEmail(to)) {
                XDialog.error("Định dạng email không hợp lệ.", "Lỗi");
                txtEmail.requestFocus();
                txtEmail.selectAll();
                return;
            }
            
            // Kiểm tra file báo cáo tồn tại
            if (lastExportedFile == null || !lastExportedFile.exists()) { 
                XDialog.error("Vui lòng xuất báo cáo trước khi gửi email.", "Lỗi"); 
                return; 
            }
            
            // Kiểm tra kích thước file (giới hạn 25MB cho email)
            long fileSizeInMB = lastExportedFile.length() / (1024 * 1024);
            if (fileSizeInMB > 25) {
                String message = String.format(
                    "File báo cáo quá lớn (%d MB).\n" +
                    "Hầu hết email server giới hạn file đính kèm ≤ 25MB.\n" +
                    "Bạn có muốn tiếp tục gửi?", fileSizeInMB
                );
                if (!XDialog.confirm(message, "Cảnh báo")) {
                    return;
                }
            }
            
            // Tạo nội dung email chi tiết hơn
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String emailSubject = "Báo cáo doanh thu - " + new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
            String emailBody = String.format(
                "Kính gửi Anh/Chị,\n\n" +
                "Đính kèm báo cáo doanh thu được tạo lúc %s.\n" +
                "File: %s (%.1f MB)\n\n" +
                "Trân trọng,\n" +
                "Hệ thống quản lý FiveC",
                sdf.format(new java.util.Date()),
                lastExportedFile.getName(),
                fileSizeInMB + (lastExportedFile.length() % (1024 * 1024)) / (1024.0 * 1024.0)
            );

            Xmail.sendEmailWithAttachment(to, emailSubject, emailBody, lastExportedFile);
            XDialog.success("Đã gửi email báo cáo tới: " + to, "Gửi email thành công");
            
            // Clear email field sau khi gửi thành công
            txtEmail.setText("");
            
        } catch (Exception ex) {
            ex.printStackTrace();
            String errorMessage = "Lỗi gửi email: ";
            if (ex.getMessage().contains("Authentication")) {
                errorMessage += "Lỗi xác thực email server. Vui lòng kiểm tra cấu hình.";
            } else if (ex.getMessage().contains("Connection")) {
                errorMessage += "Không thể kết nối email server. Kiểm tra kết nối mạng.";
            } else {
                errorMessage += ex.getMessage();
            }
            XDialog.error(errorMessage, "Lỗi gửi email");
        }
    }
    
    /**
     * Validate email format
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }
    
    // ========================================
    // SINGLE REPORT EXPORT METHODS
    // ========================================
    
    /**
     * Export single product report to PDF
     */
    private File exportSingleProductReportPDF(List<Bill> paid, String label, java.util.Date from, java.util.Date to) throws Exception {
        // For now, fallback to comprehensive report - these methods need to be implemented
        // TODO: Implement dedicated single product report export
        return createComprehensivePDF(paid, label, from, to);
    }
    
    /**
     * Export single employee report to PDF
     */
    private File exportSingleEmployeeReportPDF(List<Bill> paid, String label, java.util.Date from, java.util.Date to) throws Exception {
        // For now, fallback to comprehensive report - these methods need to be implemented
        // TODO: Implement dedicated single employee report export
        return createComprehensivePDF(paid, label, from, to);
    }
    
    /**
     * Export single trend report to PDF
     */
    private File exportSingleTrendReportPDF(List<Bill> paid, String label, java.util.Date from, java.util.Date to) throws Exception {
        // For now, fallback to comprehensive report - these methods need to be implemented
        // TODO: Implement dedicated single trend report export
        return createComprehensivePDF(paid, label, from, to);
    }
    
    /**
     * Export single top products report to PDF
     */
    private File exportSingleTopProductsReportPDF(List<Bill> paid, String label, java.util.Date from, java.util.Date to) throws Exception {
        // For now, fallback to comprehensive report - these methods need to be implemented
        // TODO: Implement dedicated single top products report export
        return createComprehensivePDF(paid, label, from, to);
    }
    
    /**
     * Export single hourly stats report to PDF
     */
    private File exportSingleHourlyReportPDF(List<Bill> paid, String label, java.util.Date from, java.util.Date to) throws Exception {
        // For now, fallback to comprehensive report - these methods need to be implemented
        // TODO: Implement dedicated single hourly stats report export
        return createComprehensivePDF(paid, label, from, to);
    }
    
    /**
     * Export single product report to Excel
     */
    private File exportSingleProductReportExcel(List<Bill> paid, String label, java.util.Date from, java.util.Date to) throws Exception {
        // For now, fallback to comprehensive report - these methods need to be implemented
        // TODO: Implement dedicated single product report export
        return createComprehensiveExcel(paid, label, from, to);
    }
    
    /**
     * Export single employee report to Excel
     */
    private File exportSingleEmployeeReportExcel(List<Bill> paid, String label, java.util.Date from, java.util.Date to) throws Exception {
        // For now, fallback to comprehensive report - these methods need to be implemented
        // TODO: Implement dedicated single employee report export
        return createComprehensiveExcel(paid, label, from, to);
    }
    
    /**
     * Export single trend report to Excel
     */
    private File exportSingleTrendReportExcel(List<Bill> paid, String label, java.util.Date from, java.util.Date to) throws Exception {
        // For now, fallback to comprehensive report - these methods need to be implemented
        // TODO: Implement dedicated single trend report export
        return createComprehensiveExcel(paid, label, from, to);
    }
    
    /**
     * Export single top products report to Excel
     */
    private File exportSingleTopProductsReportExcel(List<Bill> paid, String label, java.util.Date from, java.util.Date to) throws Exception {
        // For now, fallback to comprehensive report - these methods need to be implemented
        // TODO: Implement dedicated single top products report export
        return createComprehensiveExcel(paid, label, from, to);
    }
    
    /**
     * Export single hourly stats report to Excel
     */
    private File exportSingleHourlyReportExcel(List<Bill> paid, String label, java.util.Date from, java.util.Date to) throws Exception {
        // For now, fallback to comprehensive report - these methods need to be implemented
        // TODO: Implement dedicated single hourly stats report export
        return createComprehensiveExcel(paid, label, from, to);
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
            previewTableModel.addRow(new Object[] { "Doanh thu tổng quát", totalBills, "Sẵn sàng" });
        }
        
        if (chkProductRevenue.isSelected()) {
            // Count products with sales in the period
            int productCount = getProductCountInPeriod();
            previewTableModel.addRow(new Object[] { "Doanh thu theo món", productCount, productCount > 0 ? "Sẵn sàng" : "Không có dữ liệu" });
        }
        
        if (chkEmployeeRevenue.isSelected()) {
            // Count employees with sales in the period
            int empCount = getEmployeeCountInPeriod();
            previewTableModel.addRow(new Object[] { "Doanh thu theo nhân viên", empCount, empCount > 0 ? "Sẵn sàng" : "Không có dữ liệu" });
        }
        
        if (chkTrendAnalysis.isSelected()) {
            long dayCount = getDayCountInPeriod();
            previewTableModel.addRow(new Object[] { "Phân tích xu hướng", (int)dayCount + " ngày", dayCount > 1 ? "Sẵn sàng" : "Cần > 1 ngày" });
        }
        
        if (chkTopProducts.isSelected()) {
            int topProductCount = Math.min(10, getProductCountInPeriod());
            previewTableModel.addRow(new Object[] { "Top sản phẩm bán chạy", topProductCount, topProductCount > 0 ? "Sẵn sàng" : "Không có dữ liệu" });
        }
        
        if (chkHourlyStats.isSelected()) {
            previewTableModel.addRow(new Object[] { "Thống kê theo giờ", totalBills, totalBills > 0 ? "Sẵn sàng" : "Không có dữ liệu" });
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
            
            DefaultPieDataset prodDs = new DefaultPieDataset();
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
            Calendar prevFromCal = Calendar.getInstance();
            prevFromCal.setTime(from);
            prevFromCal.add(Calendar.DAY_OF_MONTH, (int) -days);
            Calendar prevToCal = Calendar.getInstance();
            prevToCal.setTime(from);
            prevToCal.add(Calendar.DAY_OF_MONTH, -1);
            
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
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(b.getCheckout());
                    int hour = cal.get(Calendar.HOUR_OF_DAY);
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
            org.apache.poi.hssf.usermodel.HSSFSheet s3 = wb.createSheet("Theo món ăn");
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
     *
     * Deprecated: Đã thay bằng component chuyên biệt `TabProduct` để gom logic UI + data.
     * Giữ lại để tham chiếu, tránh xung đột khi merge nhánh cũ.
     */
    @Deprecated
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
            JButton             btnRefresh = XTheme.createBeButton("Làm mới", _ -> { refreshProductRevenueData(); });
            cboProdRange.addActionListener(_ -> { refreshProductRevenueData(); });
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

    /**
     * Deprecated: Logic tải dữ liệu tab món đã được đóng gói trong `TabProduct`.
     */
    @Deprecated
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

            // Initialize product metrics
            Map<String, Double> revenueByProductId = new HashMap<>();
            Map<String, Integer> quantityByProductId = new HashMap<>();
            for (Product p : products) {
                if (p != null && p.getProductId() != null) {
                    revenueByProductId.putIfAbsent(p.getProductId(), 0.0);
                    quantityByProductId.putIfAbsent(p.getProductId(), 0);
                }
            }

            // Aggregate data within time range
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

            // Sort and update table
            List<Map.Entry<String, Double>> sorted = new ArrayList<>(revenueByProductId.entrySet());
            sorted.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
            updateProdTable(sorted, quantityByProductId, productIdToName);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            showErrorPanel(jPanel4, "Lỗi tải dữ liệu doanh thu theo món: " + ex.getMessage());
        }
    }
    /**
     * Deprecated: Đã chuyển sang `TabProduct`.
     */
    @Deprecated
    private void updateProdTable(List<Map.Entry<String, Double>> sortedRevenueByProductId, 
                                Map<String, Integer> quantityByProductId, 
                                Map<String, String> productIdToName) {
        prodTableModel.setRowCount(0);
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("vi-VN"));
        
        for (Map.Entry<String, Double> e : sortedRevenueByProductId) {
            String productId = e.getKey();
            String name = productIdToName.getOrDefault(productId, productId);
            int qty = quantityByProductId.getOrDefault(productId, 0);
            double revenue = e.getValue() != null ? e.getValue() : 0.0;
            
            prodTableModel.addRow(new Object[] { 
                name, 
                qty, 
                nf.format(revenue) 
            });
        }
    }
    /**
     * Show comparison error message
     */
    private void showComparisonError(String message) {
        if (comparisonPanel == null) return;
        
        // Find comparison chart container (assuming it's the second component)
        Component[] components = comparisonPanel.getComponents();
        if (components.length > 1 && components[1] instanceof JPanel) {
            JPanel comparisonChartContainer = (JPanel) components[1];
            comparisonChartContainer.removeAll();
            JLabel errorLabel = new JLabel("<html><center>  " + message + "</center></html>");
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            errorLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
            errorLabel.setForeground(Color.RED);
            comparisonChartContainer.add(errorLabel, BorderLayout.CENTER);
            comparisonChartContainer.revalidate();
            comparisonChartContainer.repaint();
        }
    }

    /**
     * Deprecated: Đã chuyển sang `TabProduct`.
     */
    @Deprecated
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

    /**
     * Deprecated: Đã chuyển sang `TabProduct`.
     */
    @Deprecated
    private TimeRange getSelectedProdRange() {
        String selected = cboProdRange != null ? (String) cboProdRange.getSelectedItem() : "Ngày";
        if ("Tháng".equals(selected)) return TimeRange.thisMonth();
        if ("Năm".equals(selected)) return TimeRange.thisYear();
        return TimeRange.today();
    }

    /**
     * Khởi tạo tab Doanh thu theo món bằng component `TabProduct` để tránh trùng lặp/đè UI.
     * - Xóa mọi layout cũ của `jPanel4`
     * - Thêm `TabProduct` (biểu đồ + bảng) dùng chung các DAO đã khởi tạo
     */
    private void initProductTabComponent() {
        try {
            jPanel4.removeAll();
            jPanel4.setLayout(new BorderLayout());
            jPanel4.setBackground(Color.WHITE);

            TabProduct tab = new TabProduct(billDAO, billDetailsDAO, productDAO);
            jPanel4.add(tab, BorderLayout.CENTER);

            jPanel4.revalidate();
            jPanel4.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
            showErrorPanel(jPanel4, "Lỗi khi khởi tạo TabProduct: " + ex.getMessage());
        }
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
                Calendar cursor = Calendar.getInstance();
                cursor.setTime(from);
                Calendar end = Calendar.getInstance();
                end.setTime(to);
                while (!cursor.after(end)) {
                    result.put(sdf.format(cursor.getTime()), 0.0);
                    cursor.add(Calendar.DAY_OF_MONTH, 1);
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
        lbTitle.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lbTitle.setForeground(new Color(108, 117, 125));

        JLabel lbValue = new JLabel(value);
        lbValue.setFont(new Font("Segoe UI", Font.BOLD, 12));
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
            
            // Create dataset
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (Map.Entry<String, Double> e : dataByPeriod.entrySet()) {
                dataset.addValue(e.getValue(), "Doanh thu", e.getKey());
            }
            
            // Add forecast if enabled
            if (chkShowForecast != null && chkShowForecast.isSelected()) {
                List<Double> values = new ArrayList<>(dataByPeriod.values());
                if (values.size() >= 3) {
                    double trend = calculateSimpleTrend(values);
                    double lastValue = values.get(values.size() - 1);
                    
                    // Forecast next 3 periods
                    for (int i = 1; i <= 3; i++) {
                        double forecast = Math.max(0, lastValue + (trend * i));
                        String forecastPeriod = "Dự báo " + i;
                        dataset.addValue(forecast, "Dự báo", forecastPeriod);
                    }
                }
            }
            
            String title = "Xu hướng doanh thu " + viewType.toLowerCase();
            JFreeChart chart;
            
            // Create chart based on type
            if ("Bar Chart".equals(chartType)) {
                chart = XChart.createBarChart(title, getViewTypeLabel(), "VNĐ", dataset);
            } else if ("Area Chart".equals(chartType)) {
                chart = XChart.createAreaChart(title, getViewTypeLabel(), "VNĐ", dataset);
            } else {
                // Default to Line Chart
                chart = XChart.createLineChart(title, getViewTypeLabel(), "VNĐ", dataset);
            }
            
            // Modern styling
            org.jfree.chart.plot.CategoryPlot plot = (org.jfree.chart.plot.CategoryPlot) chart.getPlot();
            plot.setBackgroundPaint(new Color(248, 249, 250));
            plot.setDomainGridlinePaint(new Color(200, 200, 200));
            plot.setRangeGridlinePaint(new Color(200, 200, 200));
            
            // Customize colors for different series
            org.jfree.chart.renderer.category.CategoryItemRenderer renderer = plot.getRenderer();
            if (renderer instanceof org.jfree.chart.renderer.category.LineAndShapeRenderer) {
                org.jfree.chart.renderer.category.LineAndShapeRenderer lineRenderer = 
                    (org.jfree.chart.renderer.category.LineAndShapeRenderer) renderer;
                
                // Set colors for different series
                lineRenderer.setSeriesPaint(0, new Color(134, 39, 43)); // Main revenue - red
                lineRenderer.setSeriesPaint(1, new Color(255, 193, 7));  // Forecast - yellow
                lineRenderer.setSeriesStroke(0, new java.awt.BasicStroke(2.0f));
                lineRenderer.setSeriesStroke(1, new java.awt.BasicStroke(2.0f, java.awt.BasicStroke.CAP_ROUND, 
                    java.awt.BasicStroke.JOIN_ROUND, 0, new float[]{10, 5}, 0)); // Dotted line for forecast
            } else if (renderer instanceof org.jfree.chart.renderer.category.BarRenderer) {
                org.jfree.chart.renderer.category.BarRenderer barRenderer = 
                    (org.jfree.chart.renderer.category.BarRenderer) renderer;
                barRenderer.setSeriesPaint(0, new Color(134, 39, 43));
                barRenderer.setSeriesPaint(1, new Color(255, 193, 7));
            }
            
            ChartPanel panel = XChart.createChartPanel(chart);
            
            trendChartContainer.removeAll();
            trendChartContainer.add(panel, BorderLayout.CENTER);
            
            trendChartContainer.revalidate();
            trendChartContainer.repaint();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            // Show error in chart container
            trendChartContainer.removeAll();
            JLabel errorLabel = new JLabel("  Lỗi tạo biểu đồ: " + ex.getMessage());
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            errorLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
            errorLabel.setForeground(Color.RED);
            trendChartContainer.add(errorLabel, BorderLayout.CENTER);
            trendChartContainer.revalidate();
            trendChartContainer.repaint();
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
     * Calculate growth rate vs previous period
     */
    private double calculateGrowthRate(List<Bill> currentBills) {
        try {
            if (dcFromDate == null || dcToDate == null) return 0.0;
            
            java.util.Date from = dcFromDate.getDate();
            java.util.Date to = dcToDate.getDate();
            if (from == null || to == null) return 0.0;
            
            long days = (to.getTime() - from.getTime()) / (24 * 60 * 60 * 1000) + 1;
            
            Calendar prevFromCal = Calendar.getInstance();
            prevFromCal.setTime(from);
            prevFromCal.add(Calendar.DAY_OF_MONTH, (int) -days);
            
            Calendar prevToCal = Calendar.getInstance();
            prevToCal.setTime(from);
            prevToCal.add(Calendar.DAY_OF_MONTH, -1);
            
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
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(bill.getCheckout());
            
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1; // 0=Sunday
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            
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
        JLabel errorLabel = new JLabel("<html><center>  " + message + "</center></html>");
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
            insights.append(" PHÂN TÍCH THÔNG MINH\n\n");
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
            
            insights.append(String.format("  Thấp điểm: %s với %,.0f VNĐ\n\n", lowPeriod, lowValue));
            
            // Growth insight
            double growthRate = calculateGrowthRate(bills);
            if (growthRate > 0) {
                insights.append(String.format("  Xu hướng tích cực: Tăng %.1f%% so với kỳ trước\n", growthRate));
            } else if (growthRate < 0) {
                insights.append(String.format("  Cần chú ý: Giảm %.1f%% so với kỳ trước\n", Math.abs(growthRate)));
            } else {
                insights.append(" Doanh thu ổn định so với kỳ trước\n");
            }
            
            // Stability analysis
            double variance = calculateVariance(dataByPeriod.values());
            if (variance < totalRevenue * 0.1) {
                insights.append("  Doanh thu khá ổn định trong kỳ\n");
            } else {
                insights.append("  Doanh thu có biến động lớn trong kỳ\n");
            }
            
        } catch (Exception ex) {
            insights.append("  Lỗi khi phân tích dữ liệu");
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
    
    /**
     * Calculate simple trend using linear regression
     */
    private double calculateSimpleTrend(List<Double> values) {
        if (values.size() < 2) return 0;
        
        int n = values.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        
        for (int i = 0; i < n; i++) {
            double x = i;
            double y = values.get(i);
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }
        
        double slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        return slope;
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
    
    // Helper method để tạo KPI card đơn giản
    private JPanel createSimpleKPICard(String label, String value, Color valueColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(label);
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        titleLabel.setForeground(new Color(108, 117, 125));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa title
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        valueLabel.setForeground(valueColor);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    /**
     * Show error panel when chart creation fails
     */
    private void showErrorPanel(JPanel targetPanel, String errorMessage) {
        targetPanel.removeAll();
        targetPanel.setLayout(new BorderLayout());
        
        JLabel errorLabel = new JLabel("  " + errorMessage);
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
    }// </editor-fold>                        

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

    // ========================================
    // PHASE 1: ENHANCED METHODS
    // ========================================

    /**
     * Set date range from TimeRange
     */
    private void setDateRange(TimeRange range) {
        try {
            dcReportFrom.setDate(range.getFrom());
            dcReportTo.setDate(range.getTo());
            
            // Refresh preview
            loadDataAsync();
            
        } catch (Exception ex) {
            XDialog.error("Lỗi khi đặt khoảng thời gian: " + ex.getMessage(), "Lỗi");
        }
    }

    /**
     * Enable custom date range input
     */
    private void enableCustomDateRange() {
        try {
            // Show custom date panel
            for (Component comp : quickDatePanel.getParent().getComponents()) {
                if (comp instanceof JPanel && comp.getName() != null && comp.getName().equals("customDatePanel")) {
                    comp.setVisible(true);
                    break;
                }
            }
            
            // Focus on from date
            dcReportFrom.requestFocus();
            
        } catch (Exception ex) {
            XDialog.error("Lỗi khi bật chế độ tùy chỉnh: " + ex.getMessage(), "Lỗi");
        }
    }



    /**
     * Load data asynchronously with progress bar
     */
    private void loadDataAsync() {
        SwingWorker<List<Bill>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Bill> doInBackground() throws Exception {
                // Simulate loading time for better UX
                Thread.sleep(500);
                
                // Load data in background
                return billDAO.findAll();
            }
            
            @Override
            protected void done() {
                try {
                    List<Bill> bills = get();
                    updateUI(bills);
                    hideProgressBar();
                } catch (Exception ex) {
                    hideProgressBar();
                    showError("Lỗi tải dữ liệu: " + ex.getMessage());
                }
            }
        };
        
        showProgressBar("Đang tải dữ liệu...");
        worker.execute();
    }

    /**
     * Show progress bar
     */
    private void showProgressBar(String message) {
        progressLabel.setText(message);
        progressPanel.setVisible(true);
        progressBar.setIndeterminate(true);
        progressPanel.revalidate();
        progressPanel.repaint();
    }

    /**
     * Hide progress bar
     */
    private void hideProgressBar() {
        progressPanel.setVisible(false);
        progressPanel.revalidate();
        progressPanel.repaint();
    }

    /**
     * Update UI with loaded data
     */
    private void updateUI(List<Bill> bills) {
        try {
            // Update preview with loaded data
            refreshReportPreview();
        } catch (Exception ex) {
            showError("Lỗi cập nhật giao diện: " + ex.getMessage());
        }
    }

    /**
     * Show error with better formatting
     */
    private void showError(String message) {
        XDialog.error(message, "Lỗi");
    }

    /**
     * Enhanced error handling for export
     */
    private void handleExportReport() {
        try {
            // Show progress
            showProgressBar("Đang xuất báo cáo...");
            
            // Use enhanced validation
            if (!validateReportSettings()) {
                hideProgressBar();
                return;
            }
            
            // Get validated dates
            java.util.Date from = normalizeStartOfDay(dcReportFrom.getDate());
            java.util.Date to = normalizeEndOfDay(dcReportTo.getDate());

            String label = new SimpleDateFormat("dd/MM/yyyy").format(from) + " - " + new SimpleDateFormat("dd/MM/yyyy").format(to);

            // Collect data within range
            List<Bill> bills = billDAO.findAll();
            List<Bill> paid = new ArrayList<>();
            for (Bill b : bills) {
                if (b != null && b.getStatus() != null && b.getStatus() == 1 && b.getCheckout() != null) {
                    if (!b.getCheckout().before(from) && !b.getCheckout().after(to)) paid.add(b);
                }
            }

            // Export based on format
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
                    } else if (chkProductRevenue.isSelected()) {
                        lastExportedFile = exportSingleProductReportPDF(paid, label, from, to);
                    } else if (chkEmployeeRevenue.isSelected()) {
                        lastExportedFile = exportSingleEmployeeReportPDF(paid, label, from, to);
                    } else if (chkTrendAnalysis.isSelected()) {
                        lastExportedFile = exportSingleTrendReportPDF(paid, label, from, to);
                    } else if (chkTopProducts.isSelected()) {
                        lastExportedFile = exportSingleTopProductsReportPDF(paid, label, from, to);
                    } else if (chkHourlyStats.isSelected()) {
                        lastExportedFile = exportSingleHourlyReportPDF(paid, label, from, to);
                    } else {
                        // Fallback to comprehensive report
                        lastExportedFile = createComprehensivePDF(paid, label, from, to);
                    }
                } else {
                    // Create comprehensive PDF with selected sections
                    lastExportedFile = createComprehensivePDF(paid, label, from, to);
                }
                
                if (lastExportedFile != null && lastExportedFile.exists()) {
                    hideProgressBar();
                    XDialog.success("Xuất báo cáo PDF thành công!\nFile: " + lastExportedFile.getName(), "Thành công");
                } else {
                    hideProgressBar();
                    XDialog.error("Không thể tạo file PDF. Vui lòng thử lại.", "Lỗi");
                }
            } else { // Excel export logic
                if (!chkMerge.isSelected()) {
                    // Export single Excel sheet based on first selected option
                    if (chkGeneralRevenue.isSelected()) {
                        lastExportedFile = XExcel.exportGeneralRevenueBills(paid, label);
                    } else if (chkProductRevenue.isSelected()) {
                        lastExportedFile = exportSingleProductReportExcel(paid, label, from, to);
                    } else if (chkEmployeeRevenue.isSelected()) {
                        lastExportedFile = exportSingleEmployeeReportExcel(paid, label, from, to);
                    } else if (chkTrendAnalysis.isSelected()) {
                        lastExportedFile = exportSingleTrendReportExcel(paid, label, from, to);
                    } else if (chkTopProducts.isSelected()) {
                        lastExportedFile = exportSingleTopProductsReportExcel(paid, label, from, to);
                    } else if (chkHourlyStats.isSelected()) {
                        lastExportedFile = exportSingleHourlyReportExcel(paid, label, from, to);
                    } else {
                        // Fallback to comprehensive report
                        lastExportedFile = createComprehensiveExcel(paid, label, from, to);
                    }
                } else {
                    // Create comprehensive Excel with selected sections
                    lastExportedFile = createComprehensiveExcel(paid, label, from, to);
                }
                
                if (lastExportedFile != null && lastExportedFile.exists()) {
                    hideProgressBar();
                    XDialog.success("Xuất báo cáo Excel thành công!\nFile: " + lastExportedFile.getName(), "Thành công");
                } else {
                    hideProgressBar();
                    XDialog.error("Không thể tạo file Excel. Vui lòng thử lại.", "Lỗi");
                }
            }
        } catch (Throwable ex) {
            hideProgressBar();
            handleEnhancedError(ex, "xuất báo cáo");
        }
    }
    
    // ========================================
    // MISSING METHODS - STUB IMPLEMENTATIONS
    // ========================================
    
    /**
     * Initialize General Dashboard
     */
    
    // ========================================
    // MISSING METHODS - STUB IMPLEMENTATIONS
    // ========================================
    
    /**
     * Initialize Employee Revenue Tab
     */
    private void initEmployeeRevenueTab() {
        try {
            jPanel5.setLayout(new BorderLayout());
            jPanel5.setBackground(Color.WHITE);

            // Header với tiêu đề và điều khiển nâng cao
            JPanel header = new JPanel(new BorderLayout());
            header.setBackground(Color.WHITE);
            header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel title = new JLabel("Doanh thu theo nhân viên");
            title.setFont(new Font("Tahoma", Font.BOLD, 20));
            title.setForeground(new Color(134, 39, 43));
            header.add(title, BorderLayout.WEST);

            // Panel điều khiển bên phải - NÂNG CAO
            JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            rightHeader.setOpaque(false);
            
            // Combo box khoảng thời gian chính
            cboEmpRange = new JComboBox<>(new String[] { 
                "Hôm nay", "Tuần này", "Tháng này", "Quý này", "Năm nay", "Khoảng tùy ý" 
            });
            cboEmpRange.setSelectedItem("Tháng này");
            
            // Combo box so sánh với mốc thời gian khác
            cboEmpCompare = new JComboBox<>(new String[] { 
                "Không so sánh", "Tháng trước", "Quý trước", "Năm trước" 
            });
            cboEmpCompare.setSelectedItem("Không so sánh");
            
            // Date chooser cho khoảng tùy ý
            dcEmpFrom = new JDateChooser();
            dcEmpTo = new JDateChooser();
            dcEmpFrom.setDateFormatString("dd/MM/yyyy");
            dcEmpTo.setDateFormatString("dd/MM/yyyy");
            dcEmpFrom.setDate(Calendar.getInstance().getTime());
            dcEmpTo.setDate(Calendar.getInstance().getTime());
            dcEmpFrom.setVisible(false);
            dcEmpTo.setVisible(false);
            
            // Checkbox cho các tính năng - MẶC ĐỊNH UNCHECK
            chkShowComparison = new JCheckBox("So sánh", false);
            chkShowProductivity = new JCheckBox("Năng suất", false);
            
            JButton btnRefresh = XTheme.createBeButton("Làm mới", e -> { 
                // HIỂN THỊ XDIALOG XÁC NHẬN TRƯỚC KHI LÀM MỚI
//                boolean confirm = XDialog.confirm(
//                    "Bạn có muốn làm mới dữ liệu không?", 
//                    "Xác nhận làm mới"
//                );
//                
//                if (confirm) {
//                    // NẾU NGƯỜI DÙNG CHỌN CÓ THÌ LÀM MỚI
                    resetEmployeeTabToDefault();
//                    
//                    // HIỂN THỊ XDIALOG THÔNG BÁO HOÀN THÀNH
//                    XDialog.success("Làm mới thành công!", "Thông báo");
//                }
                e.getSource(); 
            });
            
            // Thêm listener cho combo box
            cboEmpRange.addActionListener(e -> {
                if ("Khoảng tùy ý".equals(cboEmpRange.getSelectedItem())) {
                    dcEmpFrom.setVisible(true);
                    dcEmpTo.setVisible(true);
                } else {
                    dcEmpFrom.setVisible(false);
                    dcEmpTo.setVisible(false);
                }
                refreshEmployeeRevenueData();
            });
            
            cboEmpCompare.addActionListener(e -> {
                // KHI CHỌN "KHÔNG SO SÁNH" THÌ TỰ ĐỘNG UNCHECK CHECKBOX SO SÁNH
                if ("Không so sánh".equals(cboEmpCompare.getSelectedItem())) {
                    chkShowComparison.setSelected(false);
                } else {
                    chkShowComparison.setSelected(true);
                }
                refreshEmployeeRevenueData();
            });
            
            // THÊM LISTENER CHO CHECKBOX NĂNG SUẤT - ĐIỀU KHIỂN HIỂN THỊ
            chkShowProductivity.addActionListener(e -> {
                toggleProductivityDisplay();
                refreshEmployeeRevenueData();
            });
            
            chkShowComparison.addActionListener(e -> {
                // KHI UNCHECK CHECKBOX SO SÁNH THÌ TỰ ĐỘNG CHUYỂN COMBO BOX VỀ "KHÔNG SO SÁNH"
                if (!chkShowComparison.isSelected()) {
                    cboEmpCompare.setSelectedItem("Không so sánh");
                }
                refreshEmployeeRevenueData();
            });
            
            rightHeader.add(new JLabel("Khoảng:"));
            rightHeader.add(cboEmpRange);
            rightHeader.add(new JLabel("So sánh:"));
            rightHeader.add(cboEmpCompare);
            rightHeader.add(chkShowComparison);
            rightHeader.add(chkShowProductivity);
            rightHeader.add(new JLabel("Từ:"));
            rightHeader.add(dcEmpFrom);
            rightHeader.add(new JLabel("Đến:"));
            rightHeader.add(dcEmpTo);
            rightHeader.add(btnRefresh);
            header.add(rightHeader, BorderLayout.EAST);

            jPanel5.add(header, BorderLayout.NORTH);

            // Panel chính chứa biểu đồ và bảng - SỬ DỤNG LAYOUT MANAGER TỐT HƠN
            JPanel mainContent = new JPanel();
            mainContent.setBackground(Color.WHITE);
            mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));

            // Container cho biểu đồ chính - LUÔN HIỂN THỊ
            empChartContainer = new JPanel(new BorderLayout());
            empChartContainer.setBorder(BorderFactory.createEmptyBorder(6, 12, 12, 12));
            empChartContainer.setBackground(Color.WHITE);
            empChartContainer.setPreferredSize(new Dimension(10, 400));
            empChartContainer.setMinimumSize(new Dimension(10, 400));
            mainContent.add(empChartContainer);

            // Container cho thống kê và tỷ lệ phần trăm - SỬ DỤNG GRIDLAYOUT CÓ KHẢ NĂNG CO GIÃN
            empStatsContainer = new JPanel(new GridLayout(1, 4, 25, 10));
            empStatsContainer.setBackground(Color.WHITE);
            empStatsContainer.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
            empStatsContainer.setPreferredSize(new Dimension(10, 170)); // TĂNG TỪ 140 LÊN 170
            empStatsContainer.setMinimumSize(new Dimension(10, 170));   // TĂNG TỪ 140 LÊN 170
            mainContent.add(empStatsContainer);

            // Container cho biểu đồ phụ (số hóa đơn và TB/HĐ) - CHỈ HIỂN THỊ KHI TÍCH NĂNG SUẤT
            empSecondaryChartContainer = new JPanel(new BorderLayout());
            empSecondaryChartContainer.setBackground(Color.WHITE);
            empSecondaryChartContainer.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
            empSecondaryChartContainer.setPreferredSize(new Dimension(10, 300));
            empSecondaryChartContainer.setMinimumSize(new Dimension(10, 300));
            empSecondaryChartContainer.setVisible(false); // MẶC ĐỊNH ẨN
            mainContent.add(empSecondaryChartContainer);

            // Container cho bảng - LUÔN HIỂN THỊ
            empTableContainer = new JPanel(new BorderLayout());
            empTableContainer.setBackground(Color.WHITE);
            empTableContainer.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
            initEmpTable();
            empTableContainer.setPreferredSize(new Dimension(10, 250));
            empTableContainer.setMinimumSize(new Dimension(10, 250));
            mainContent.add(empTableContainer);

            // THÊM SCROLL PANE ĐỂ CÓ THANH KÉO
            JScrollPane scrollPane = new JScrollPane(mainContent);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setBorder(null);
            scrollPane.getViewport().setBackground(Color.WHITE);
            
            // Tùy chỉnh scroll pane
            scrollPane.getVerticalScrollBar().setUnitIncrement(20);
            scrollPane.getVerticalScrollBar().setBlockIncrement(100);
            
            jPanel5.add(scrollPane, BorderLayout.CENTER);

            // Tải dữ liệu ban đầu
            refreshEmployeeRevenueData();
            
        } catch (Exception e) {
            e.printStackTrace();
            showErrorPanel(jPanel5, "Lỗi khi tạo giao diện doanh thu theo nhân viên: " + e.getMessage());
        }
    }

    /**
     * Khởi tạo bảng hiển thị doanh thu nhân viên
     */
   

    /**
     * Làm mới dữ liệu doanh thu theo nhân viên
     */

    /**
     * Cập nhật bảng doanh thu nhân viên
     */
    private void updateEmpTable(List<Map.Entry<String, Double>> sortedRevenue, 
                               Map<String, Integer> ordersByEmployee, 
                               Map<String, String> userIdToName) {
        empTableModel.setRowCount(0);
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("vi-VN"));
        
        for (Map.Entry<String, Double> entry : sortedRevenue) {
            String userId = entry.getKey();
            String name = userIdToName.getOrDefault(userId, userId);
            int orders = ordersByEmployee.getOrDefault(userId, 0);
            double revenue = entry.getValue() != null ? entry.getValue() : 0.0;
            
            empTableModel.addRow(new Object[] { 
                userId, 
                name, 
                orders, 
                nf.format(revenue) 
            });
        }
    }

    /**
     * Lấy khoảng thời gian được chọn cho tab nhân viên
     */
    private TimeRange getSelectedEmpRange(String selected) {
        switch (selected) {
            case "Hôm nay":
                return TimeRange.today();
            case "Tuần này":
                return TimeRange.thisWeek();
            case "Năm nay":
                return TimeRange.thisYear();
            case "Khoảng tùy ý":
                if (dcEmpFrom != null && dcEmpTo != null && dcEmpFrom.getDate() != null && dcEmpTo.getDate() != null) {
                    java.util.Date from = normalizeStartOfDay(dcEmpFrom.getDate());
                    java.util.Date to = normalizeEndOfDay(dcEmpTo.getDate());
                    return new TimeRange(from, to);
                }
                // Fallback to this month if date chooser is not set
                return TimeRange.thisMonth();
            default: // "Tháng này"
                return TimeRange.thisMonth();
        }
    }
    
    /**
     * Initialize Trend Tab
     */
    private void initTrendTab() {
        jPanel6.setLayout(new BorderLayout());
        jPanel6.setBackground(Color.WHITE);
        
        // Header đơn giản
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(134, 39, 43));
        header.setPreferredSize(new Dimension(0, 60));
        
        JLabel title = new JLabel("PHÂN TÍCH XU HƯỚNG DOANH THU");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        header.add(title, BorderLayout.CENTER);
        
        // Main content đơn giản
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(Color.WHITE);
        mainContent.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Controls đơn giản
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        controls.setBackground(Color.WHITE);
        
        // Date controls
        JLabel fromLabel = new JLabel("Từ ngày:");
        dcFromDate = new JDateChooser();
        dcFromDate.setDateFormatString("dd/MM/yyyy");
        
        JLabel toLabel = new JLabel("Đến ngày:");
        dcToDate = new JDateChooser();
        dcToDate.setDateFormatString("dd/MM/yyyy");
        
        // View type
        cboViewType = new JComboBox<>(new String[]{"Theo ngày", "Theo tuần", "Theo tháng"});
        
        // Chart type
        cboChartType = new JComboBox<>(new String[]{"Line Chart", "Area Chart", "Bar Chart"});
        
        // Options
        chkShowForecast = new JCheckBox("Hiển thị dự báo");
        
        // Refresh button
        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.setBackground(new Color(134, 39, 43));
        btnRefresh.setForeground(Color.WHITE);
        
        controls.add(fromLabel);
        controls.add(dcFromDate);
        controls.add(toLabel);
        controls.add(dcToDate);
        controls.add(new JLabel("Xem theo:"));
        controls.add(cboViewType);
        controls.add(new JLabel("Loại biểu đồ:"));
        controls.add(cboChartType);
        controls.add(chkShowForecast);
        controls.add(btnRefresh);
        
        // Chart container
        trendChartContainer = new JPanel(new BorderLayout());
        trendChartContainer.setBackground(Color.WHITE);
        trendChartContainer.setBorder(BorderFactory.createTitledBorder("Biểu Đồ Xu Hướng"));
        
        // KPI summary
        kpiGrid = new JPanel(new GridLayout(1, 4, 10, 0));
        kpiGrid.setBackground(Color.WHITE);
        kpiGrid.setBorder(BorderFactory.createTitledBorder("Tóm Tắt"));
        
        // Initialize KPI cards with placeholder data (4 cards to match updateTrendKPIs)
        kpiGrid.add(createTrendKpiCard("Tổng doanh thu", "Đang tải...", new Color(134,39,43)));
        kpiGrid.add(createTrendKpiCard("TB/Ngày", "Đang tải...", new Color(40,167,69)));
        kpiGrid.add(createTrendKpiCard("Tăng trưởng", "Đang tải...", new Color(40,167,69)));
        kpiGrid.add(createTrendKpiCard("Cao điểm", "Đang tải...", new Color(255,193,7)));
        
        mainContent.add(controls, BorderLayout.NORTH);
        mainContent.add(trendChartContainer, BorderLayout.CENTER);
        mainContent.add(kpiGrid, BorderLayout.SOUTH);
        
        jPanel6.add(header, BorderLayout.NORTH);
        jPanel6.add(mainContent, BorderLayout.CENTER);
        
        // Event listeners
        dcFromDate.addPropertyChangeListener("date", _ -> { refreshEnhancedTrendData(); });
        dcToDate.addPropertyChangeListener("date", _ -> { refreshEnhancedTrendData(); });
        cboViewType.addActionListener(_ -> { refreshEnhancedTrendData(); });
        cboChartType.addActionListener(_ -> { refreshEnhancedTrendData(); });
        chkShowForecast.addActionListener(_ -> { refreshEnhancedTrendData(); });
        btnRefresh.addActionListener(_ -> { refreshEnhancedTrendData(); });
        
        // Initial load
        refreshEnhancedTrendData();
    }
    
    
    /**
     * Setup tooltips and validation for better UX
     */
    private void setupTooltipsAndValidation() {
        try {
            // Setup tooltips for report content checkboxes
            if (chkGeneralRevenue != null) {
                chkGeneralRevenue.setToolTipText("Bao gồm tổng doanh thu, số hóa đơn, giá trị trung bình và phân tích tổng quan");
            }
            if (chkProductRevenue != null) {
                chkProductRevenue.setToolTipText("Chi tiết doanh thu theo từng món ăn, sản phẩm bán chạy và phân tích hiệu suất");
            }
            if (chkEmployeeRevenue != null) {
                chkEmployeeRevenue.setToolTipText("Thống kê doanh thu theo nhân viên, hiệu suất bán hàng và xếp hạng");
            }
            if (chkTrendAnalysis != null) {
                chkTrendAnalysis.setToolTipText("Phân tích xu hướng doanh thu theo thời gian, dự báo và biểu đồ tăng trưởng");
            }
            if (chkTopProducts != null) {
                chkTopProducts.setToolTipText("Danh sách top 10 sản phẩm bán chạy nhất với số lượng và doanh thu");
            }
            if (chkHourlyStats != null) {
                chkHourlyStats.setToolTipText("Thống kê doanh thu theo giờ trong ngày, phân tích giờ cao điểm");
            }
            
            // Setup tooltips for time range buttons
            if (btnToday != null) {
                btnToday.setToolTipText("Báo cáo cho ngày hôm nay");
            }
            if (btnThisWeek != null) {
                btnThisWeek.setToolTipText("Báo cáo cho tuần hiện tại (Thứ 2 - Chủ nhật)");
            }
            if (btnThisMonth != null) {
                btnThisMonth.setToolTipText("Báo cáo cho tháng hiện tại");
            }
            if (btnThisQuarter != null) {
                btnThisQuarter.setToolTipText("Báo cáo cho quý hiện tại");
            }
            if (btnThisYear != null) {
                btnThisYear.setToolTipText("Báo cáo cho năm hiện tại");
            }
            if (btnCustomRange != null) {
                btnCustomRange.setToolTipText("Tùy chỉnh khoảng thời gian báo cáo");
            }
            
            // Setup tooltips for export options
            if (rdoPdf != null) {
                rdoPdf.setToolTipText("Xuất báo cáo dưới định dạng PDF (khuyến nghị cho in ấn)");
            }
            if (rdoExcel != null) {
                rdoExcel.setToolTipText("Xuất báo cáo dưới định dạng Excel (dễ chỉnh sửa và phân tích)");
            }
            if (chkMerge != null) {
                chkMerge.setToolTipText("Gộp tất cả các loại báo cáo đã chọn vào một file duy nhất");
            }
            
            // Setup tooltips for email field
            if (txtEmail != null) {
                txtEmail.setToolTipText("Nhập địa chỉ email để gửi báo cáo (ví dụ: example@company.com)");
                setupEmailValidation();
            }
            
            // Setup tooltips for action buttons
            if (btnExport != null) {
                btnExport.setToolTipText("Xuất báo cáo theo định dạng đã chọn (Ctrl+E)");
            }
            if (btnEmail != null) {
                btnEmail.setToolTipText("Gửi báo cáo qua email (Ctrl+S)");
            }
            if (btnRefreshPreview != null) {
                btnRefreshPreview.setToolTipText("Làm mới dữ liệu xem trước (F5)");
            }
            
        } catch (Exception ex) {
            System.err.println("Lỗi setup tooltips: " + ex.getMessage());
        }
    }
    
    /**
     * Setup email validation
     */
    private void setupEmailValidation() {
        if (txtEmail != null) {
            txtEmail.setInputVerifier(new InputVerifier() {
                @Override
                public boolean verify(JComponent input) {
                    String email = txtEmail.getText().trim();
                    if (email.isEmpty()) return true; // Allow empty
                    
                    // Basic email validation
                    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
                    boolean isValid = email.matches(emailRegex);
                    
                    if (!isValid) {
                        txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                        txtEmail.setToolTipText("Email không hợp lệ. Ví dụ: example@company.com");
                    } else {
                        txtEmail.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
                        txtEmail.setToolTipText("Email hợp lệ");
                    }
                    
                    return true; // Always allow input, just show visual feedback
                }
            });
        }
    }
    
    /**
     * Setup keyboard shortcuts for better UX
     */
    private void setupKeyboardShortcuts() {
        try {
            // Add keyboard shortcuts to the frame
            this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    // Ctrl+E for export report
                    if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_E) {
                        e.consume();
                        if (btnExport != null && btnExport.isEnabled()) {
                            btnExport.doClick();
                        }
                    }
                    // Ctrl+S for send email
                    else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S) {
                        e.consume();
                        if (btnEmail != null && btnEmail.isEnabled()) {
                            btnEmail.doClick();
                        }
                    }
                    // F5 for refresh preview
                    else if (e.getKeyCode() == KeyEvent.VK_F5) {
                        e.consume();
                        if (btnRefreshPreview != null && btnRefreshPreview.isEnabled()) {
                            btnRefreshPreview.doClick();
                        }
                    }
                    // Escape to exit
                    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        e.consume();
                        handleExit();
                    }
                }
            });
            
            // Make frame focusable to receive key events
            this.setFocusable(true);
            this.requestFocusInWindow();
            
        } catch (Exception ex) {
            System.err.println("Lỗi setup keyboard shortcuts: " + ex.getMessage());
        }
    }
    
    /**
     * Enhanced validation for report settings
     */
    private boolean validateReportSettings() {
        StringBuilder errors = new StringBuilder();
        
        // Check date range
        java.util.Date from = dcReportFrom != null ? dcReportFrom.getDate() : null;
        java.util.Date to = dcReportTo != null ? dcReportTo.getDate() : null;
        
        if (from == null || to == null) {
            errors.append("• Vui lòng chọn đầy đủ ngày bắt đầu và kết thúc\n");
        } else if (from.after(to)) {
            errors.append("• Ngày bắt đầu không được sau ngày kết thúc\n");
        }
        
        // Check if at least one report type is selected
        boolean hasSelection = false;
        if (chkGeneralRevenue != null && chkGeneralRevenue.isSelected()) hasSelection = true;
        if (chkProductRevenue != null && chkProductRevenue.isSelected()) hasSelection = true;
        if (chkEmployeeRevenue != null && chkEmployeeRevenue.isSelected()) hasSelection = true;
        if (chkTrendAnalysis != null && chkTrendAnalysis.isSelected()) hasSelection = true;
        if (chkTopProducts != null && chkTopProducts.isSelected()) hasSelection = true;
        if (chkHourlyStats != null && chkHourlyStats.isSelected()) hasSelection = true;
        
        if (!hasSelection) {
            errors.append("• Vui lòng chọn ít nhất một loại báo cáo\n");
        }
        
        // Check email if provided
        if (txtEmail != null && !txtEmail.getText().trim().isEmpty()) {
            String email = txtEmail.getText().trim();
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            if (!email.matches(emailRegex)) {
                errors.append("• Email không hợp lệ\n");
            }
        }
        
        // Show errors if any
        if (errors.length() > 0) {
            XDialog.error("Vui lòng sửa các lỗi sau:\n\n" + errors.toString(), "Lỗi cấu hình");
            return false;
        }
        
        return true;
    }
    
    /**
     * Enhanced error handling with specific error types
     */
    private void handleEnhancedError(Throwable ex, String operation) {
        String errorMessage = "Lỗi " + operation + ": ";
        
        if (ex instanceof java.io.IOException) {
            errorMessage += "Lỗi ghi file. Vui lòng kiểm tra:\n" +
                          "• Quyền ghi thư mục\n" +
                          "• Dung lượng ổ đĩa\n" +
                          "• File có đang được mở bởi ứng dụng khác không";
        } else if (ex instanceof java.sql.SQLException) {
            errorMessage += "Lỗi truy cập dữ liệu. Vui lòng kiểm tra:\n" +
                          "• Kết nối database\n" +
                          "• Quyền truy cập dữ liệu\n" +
                          "• Tình trạng server";
        } else if (ex instanceof java.lang.Error) {
            errorMessage += "Lỗi hệ thống nghiêm trọng. Vui lòng:\n" +
                          "• Giảm khoảng thời gian báo cáo\n" +
                          "• Đóng các ứng dụng khác\n" +
                          "• Restart ứng dụng";
        } else if (ex instanceof java.lang.NullPointerException) {
            errorMessage += "Lỗi dữ liệu null. Vui lòng kiểm tra lại cấu hình báo cáo";
        } else {
            errorMessage += ex.getMessage() != null ? ex.getMessage() : "Lỗi không xác định";
        }
        
        XDialog.error(errorMessage, "Lỗi " + operation);
        ex.printStackTrace();
    }
    
    // ========================================
    // UTILITY METHODS FOR DATE HANDLING
    // ========================================
    
    /**
     * Normalize date to start of day (00:00:00)
     */
    private java.util.Date normalizeStartOfDay(java.util.Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    /**
     * Normalize date to end of day (23:59:59)
     */
    private java.util.Date normalizeEndOfDay(java.util.Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
    
    /**
     * Check if a date is within a time range
     */
    private boolean withinRange(java.util.Date date, TimeRange range) {
        if (date == null || range == null) return false;
        return !date.before(range.getFrom()) && !date.after(range.getTo());
    }

    /**
     * Initialize General Dashboard with comprehensive revenue overview
     */
    private void initGeneralDashboard() {
        try {
            pnlGeneral.setLayout(new BorderLayout());
            pnlGeneral.setBackground(Color.WHITE);
    
            // Header với tiêu đề và điều khiển
            JPanel header = new JPanel(new BorderLayout());
            header.setBackground(Color.WHITE);
            header.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    
            JLabel title = new JLabel(" DASHBOARD DOANH THU TỔNG QUÁT");
            title.setFont(new Font("Segoe UI", Font.BOLD, 24));
            title.setForeground(new Color(134, 39, 43));
            header.add(title, BorderLayout.WEST);
    
            // Panel điều khiển bên phải - SỬA ĐỂ THÊM CUSTOM DATE
            JPanel rightHeader = new JPanel();
            rightHeader.setLayout(new BoxLayout(rightHeader, BoxLayout.Y_AXIS));
            rightHeader.setOpaque(false);
            
            // Dòng 1: Combo box khoảng thời gian
            JPanel timeRangePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            timeRangePanel.setOpaque(false);
            
            cboFilterType = new JComboBox<>(new String[] { 
                "Hôm nay", "Tuần này", "Tháng này", "Quý này", "Năm này", "Khoảng tùy chỉnh" 
            });
            cboFilterType.setSelectedItem("Tháng này");
            
            JButton btnRefresh = XTheme.createBeButton(" Làm mới", e -> { 
                // SỬA: Thật sự refresh dữ liệu
                refreshGeneralDashboardWithReset(); 
            });
            
    
            timeRangePanel.add(new JLabel(" Khoảng thời gian:"));
            timeRangePanel.add(cboFilterType);
            timeRangePanel.add(btnRefresh);
            
            // Dòng 2: Custom date range (ẩn mặc định)
            customGeneralDatePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));
            customGeneralDatePanel.setOpaque(false);
            customGeneralDatePanel.setVisible(false);
            
            dcGeneralFrom = new JDateChooser();
            dcGeneralTo = new JDateChooser();
            dcGeneralFrom.setDateFormatString("dd/MM/yyyy");
            dcGeneralTo.setDateFormatString("dd/MM/yyyy");
            
            // Set default dates
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            dcGeneralFrom.setDate(cal.getTime());
            dcGeneralTo.setDate(Calendar.getInstance().getTime());
            
            customGeneralDatePanel.add(new JLabel("Từ ngày:"));
            customGeneralDatePanel.add(dcGeneralFrom);
            customGeneralDatePanel.add(new JLabel("Đến ngày:"));
            customGeneralDatePanel.add(dcGeneralTo);
            
            rightHeader.add(timeRangePanel);
            rightHeader.add(customGeneralDatePanel);
            header.add(rightHeader, BorderLayout.EAST);
            
            // THÊM LISTENER CHO COMBO BOX
            cboFilterType.addActionListener(e -> { 
                if ("Khoảng tùy chỉnh".equals(cboFilterType.getSelectedItem())) {
                    customGeneralDatePanel.setVisible(true);
                } else {
                    customGeneralDatePanel.setVisible(false);
                }
                refreshGeneralDashboard(); 
            });
            
            // THÊM LISTENER CHO DATE CHOOSER
            dcGeneralFrom.addPropertyChangeListener("date", e -> refreshGeneralDashboard());
            dcGeneralTo.addPropertyChangeListener("date", e -> refreshGeneralDashboard());
    
            pnlGeneral.add(header, BorderLayout.NORTH);
    
            // THÊM PHẦN NÀY - TẠO MAIN CONTENT CỦA DASHBOARD
            JPanel mainContent = new JPanel();
            mainContent.setBackground(Color.WHITE);
            mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
    
            // Tạo KPI container
            kpiContainer = new JPanel(new GridLayout(1, 4, 15, 0));
            kpiContainer.setBackground(Color.WHITE);
            kpiContainer.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            kpiContainer.setPreferredSize(new Dimension(0, 120));
            mainContent.add(kpiContainer);
    
            // Tạo chart section
            JPanel chartSection = createGeneralChartSection();
            mainContent.add(chartSection);
    
            // Tạo table section  
            JPanel tableSection = createGeneralTableSection();
            mainContent.add(tableSection);
    
            // Thêm scroll pane
            JScrollPane scrollPane = new JScrollPane(mainContent);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setBorder(null);
            scrollPane.getViewport().setBackground(Color.WHITE);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            
            pnlGeneral.add(scrollPane, BorderLayout.CENTER);
    
            // Load dữ liệu ban đầu
            refreshGeneralDashboard();
            
        } catch (Exception e) {
            e.printStackTrace();
            showErrorPanel(pnlGeneral, "Lỗi khi tạo dashboard tổng quan: " + e.getMessage());
        }
    }

    private JPanel createGeneralChartSection() {
        JPanel chartSection = new JPanel(new BorderLayout());
        chartSection.setBackground(Color.WHITE);
        chartSection.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 144, 220), 2),
            " BIỂU ĐỒ DOANH THU THEO THỜI GIAN",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(52, 144, 220)
        ));
        chartSection.setPreferredSize(new Dimension(0, 450));
        chartSection.setMinimumSize(new Dimension(0, 400));
        
        // Container cho biểu đồ
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setBackground(Color.WHITE);
        chartContainer.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Controls cho biểu đồ
        JPanel chartControls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        chartControls.setBackground(Color.WHITE);
        
        cboGeneralChartType = new JComboBox<>(new String[]{
            "Bar Chart - So sánh theo ngày", 
            "Line Chart - Xu hướng theo thời gian", 
            "Pie Chart - Tỷ lệ theo trạng thái", 
            "Area Chart - Doanh thu cộng dồn"
        });
        cboGeneralChartType.setSelectedItem("Line Chart - Xu hướng theo thời gian");
        
        chartControls.add(new JLabel(" Loại biểu đồ:"));
        chartControls.add(cboGeneralChartType);
        
        // Thêm listener cho biểu đồ
        cboGeneralChartType.addActionListener(e -> refreshGeneralChart());
        
        chartContainer.add(chartControls, BorderLayout.NORTH);
        
        // Container cho biểu đồ chính
        generalChartContainer = new JPanel(new BorderLayout());
        generalChartContainer.setBackground(Color.WHITE);
        generalChartContainer.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        generalChartContainer.setPreferredSize(new Dimension(0, 350));
        generalChartContainer.setMinimumSize(new Dimension(0, 300));
        
        // Thêm biểu đồ placeholder
        JLabel chartPlaceholder = new JLabel("<html><center><br/>Biểu đồ doanh thu sẽ được hiển thị ở đây<br/><small>Chọn khoảng thời gian và nhấn 'Làm mới'</small></center></html>");
        chartPlaceholder.setHorizontalAlignment(SwingConstants.CENTER);
        chartPlaceholder.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        chartPlaceholder.setForeground(new Color(108, 117, 125));
        generalChartContainer.add(chartPlaceholder, BorderLayout.CENTER);
        
        chartContainer.add(generalChartContainer, BorderLayout.CENTER);
        chartSection.add(chartContainer, BorderLayout.CENTER);
        
        return chartSection;
    }

    private void refreshGeneralDashboardWithReset() {
        try {
            // Reset về trạng thái ban đầu
            if (cboFilterType != null) {
                cboFilterType.setSelectedItem("Tháng này");
            }
            
            // Ẩn custom date panel
            if (customGeneralDatePanel != null) {
                customGeneralDatePanel.setVisible(false);
            }
            
            // Reset search và filter - với null check
            resetTableFiltersAndSearch();
            
            // Refresh data
            refreshGeneralDashboard();
            
            XDialog.success("Đã làm mới dữ liệu dashboard!", "Thông báo");
            
        } catch (Exception ex) {
            ex.printStackTrace();
            XDialog.error("Lỗi khi làm mới: " + ex.getMessage(), "Lỗi");
        }
    }
    
    private JPanel createTopProductsStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel titleLabel = new JLabel(" TOP SẢN PHẨM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(new Color(134, 39, 43));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Lấy dữ liệu top sản phẩm
        String topProductsData = getTopProductsData();
        
        JLabel contentLabel = new JLabel("<html><center>" + topProductsData + "</center></html>");
        contentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        contentLabel.setForeground(new Color(108, 117, 125));
        contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(contentLabel, BorderLayout.CENTER);
        
        return panel;
    }

    // THÊM: Phân bố theo giờ với dữ liệu thật
    private JPanel createHourDistributionStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel titleLabel = new JLabel("⏰ PHÂN BỐ THEO GIỜ");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(new Color(134, 39, 43));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Lấy dữ liệu phân bố theo giờ
        String hourDistData = getHourDistributionData();
        
        JLabel contentLabel = new JLabel("<html><center>" + hourDistData + "</center></html>");
        contentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        contentLabel.setForeground(new Color(108, 117, 125));
        contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(contentLabel, BorderLayout.CENTER);
        
        return panel;
    }

    // THÊM: Tỷ lệ thành công với dữ liệu thật
    private JPanel createSuccessRateStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel titleLabel = new JLabel("  TỶ LỆ THÀNH CÔNG");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(new Color(134, 39, 43));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Lấy dữ liệu tỷ lệ thành công
        String successRateData = getSuccessRateData();
        
        JLabel contentLabel = new JLabel("<html><center>" + successRateData + "</center></html>");
        contentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        contentLabel.setForeground(new Color(108, 117, 125));
        contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(contentLabel, BorderLayout.CENTER);
        
        return panel;
    }

    // THÊM: Lấy dữ liệu top sản phẩm thật
    private String getTopProductsData() {
        try {
            String selectedRange = cboFilterType != null ? (String) cboFilterType.getSelectedItem() : "Tháng này";
            TimeRange range = getSelectedGeneralRange(selectedRange);
            
            List<Bill> bills = billDAO.findAll();
            List<BillDetails> billDetails = billDetailsDAO.findAll();
            List<Product> products = productDAO.findAll();
            
            Map<Integer, Bill> billIdToBill = new HashMap<>();
            for (Bill b : bills) {
                if (b != null && b.getBill_id() != null && b.getStatus() != null && b.getStatus() == 1) {
                    java.util.Date when = b.getCheckout() != null ? b.getCheckout() : b.getCheckin();
                    if (when != null && withinRange(when, range)) {
                        billIdToBill.put(b.getBill_id(), b);
                    }
                }
            }
            
            Map<String, String> productIdToName = new HashMap<>();
            for (Product p : products) {
                if (p != null && p.getProductId() != null) {
                    productIdToName.put(p.getProductId(), p.getProductName());
                }
            }
            
            Map<String, Integer> productQuantity = new HashMap<>();
            for (BillDetails d : billDetails) {
                if (d == null || !billIdToBill.containsKey(d.getBill_id())) continue;
                productQuantity.merge(d.getProduct_id(), d.getAmount(), Integer::sum);
            }
            
            // Tìm top 3
            List<Map.Entry<String, Integer>> sorted = new ArrayList<>(productQuantity.entrySet());
            sorted.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));
            
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < Math.min(3, sorted.size()); i++) {
                String productName = productIdToName.getOrDefault(sorted.get(i).getKey(), "N/A");
                if (productName.length() > 15) productName = productName.substring(0, 12) + "...";
                result.append((i + 1)).append(". ").append(productName).append("<br/>");
            }
            
            return result.length() > 0 ? result.toString() : "Không có dữ liệu";
            
        } catch (Exception ex) {
            return "Lỗi tải dữ liệu";
        }
    }

    // THÊM: Lấy dữ liệu phân bố theo giờ thật
    private String getHourDistributionData() {
        try {
            String selectedRange = cboFilterType != null ? (String) cboFilterType.getSelectedItem() : "Tháng này";
            TimeRange range = getSelectedGeneralRange(selectedRange);
            
            List<Bill> bills = billDAO.findAll();
            Map<Integer, Integer> hourCount = new HashMap<>();
            
            for (Bill b : bills) {
                if (b == null || b.getStatus() == null || b.getStatus() != 1) continue;
                java.util.Date when = b.getCheckout() != null ? b.getCheckout() : b.getCheckin();
                if (when == null || !withinRange(when, range)) continue;
                
                Calendar cal = Calendar.getInstance();
                cal.setTime(when);
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                hourCount.merge(hour, 1, Integer::sum);
            }
            
            // Tìm giờ cao điểm
            int peakHour = hourCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(12);
            int peakCount = hourCount.getOrDefault(peakHour, 0);
            
            return String.format("Cao điểm: %02d:00<br/>%d hóa đơn<br/>Tổng: %d giờ hoạt động", 
                peakHour, peakCount, hourCount.size());
            
        } catch (Exception ex) {
            return "Lỗi tải dữ liệu";
        }
    }

    // THÊM: Lấy dữ liệu tỷ lệ thành công thật
    private String getSuccessRateData() {
        try {
            String selectedRange = cboFilterType != null ? (String) cboFilterType.getSelectedItem() : "Tháng này";
            TimeRange range = getSelectedGeneralRange(selectedRange);
            
            List<Bill> bills = billDAO.findAll();
            int totalBills = 0;
            int completedBills = 0;
            int cancelledBills = 0;
            
            for (Bill b : bills) {
                if (b == null) continue;
                java.util.Date when = b.getCheckout() != null ? b.getCheckout() : b.getCheckin();
                if (when == null || !withinRange(when, range)) continue;
                
                totalBills++;
                if (b.getStatus() != null) {
                    if (b.getStatus() == 1) completedBills++;
                    else if (b.getStatus() == 2) cancelledBills++;
                }
            }
            
            double successRate = totalBills > 0 ? (double) completedBills / totalBills * 100 : 0;
            double cancelRate = totalBills > 0 ? (double) cancelledBills / totalBills * 100 : 0;
            
            return String.format("Thành công: %.1f%%<br/>Hủy: %.1f%%<br/>Tổng: %d HĐ", 
                successRate, cancelRate, totalBills);
            
        } catch (Exception ex) {
            return "Lỗi tải dữ liệu";
        }
    }

    private JPanel createMiniStatsPanel(String title, String content) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(new Color(134, 39, 43));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel contentLabel = new JLabel("<html><center>" + content + "</center></html>");
        contentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        contentLabel.setForeground(new Color(108, 117, 125));
        contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(contentLabel, BorderLayout.CENTER);
        
        return panel;
    }

    private void refreshGeneralChart() {
        try {
            String selectedRange = cboFilterType != null ? (String) cboFilterType.getSelectedItem() : "Tháng này";
            TimeRange range = getSelectedGeneralRange(selectedRange);
            
            List<Bill> bills = billDAO.findAll();
            if (bills == null) bills = new ArrayList<>();
            
            // Lọc bills theo range
            List<Bill> filteredBills = new ArrayList<>();
            for (Bill b : bills) {
                if (b == null) continue;
                java.util.Date when = b.getCheckout() != null ? b.getCheckout() : b.getCheckin();
                if (when == null) continue;
                if (withinRange(when, range)) {
                    filteredBills.add(b);
                }
            }
            
            updateGeneralChart(filteredBills, range);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            showErrorPanel(generalChartContainer, "Lỗi tải biểu đồ: " + ex.getMessage());
        }
    }
    private void updateGeneralChart(List<Bill> bills, TimeRange range) {
        try {
            generalChartContainer.removeAll();
            
            String chartType = cboGeneralChartType != null ? 
                (String) cboGeneralChartType.getSelectedItem() : 
                "Line Chart - Xu hướng theo thời gian";
                
            JFreeChart chart = null;
            
            if (chartType.contains("Bar Chart")) {
                chart = createGeneralBarChart(bills, range);
            } else if (chartType.contains("Line Chart")) {
                chart = createGeneralLineChart(bills, range);
            } else if (chartType.contains("Pie Chart")) {
                chart = createGeneralPieChart(bills, range);
            } else if (chartType.contains("Area Chart")) {
                chart = createGeneralAreaChart(bills, range);
            } else {
                chart = createGeneralLineChart(bills, range); // default
            }
            
            if (chart != null) {
                ChartPanel panel = XChart.createChartPanel(chart);
                generalChartContainer.add(panel, BorderLayout.CENTER);
            } else {
                JLabel noDataLabel = new JLabel("<html><center><br/>Không có dữ liệu để hiển thị<br/><small>Thử chọn khoảng thời gian khác</small></center></html>");
                noDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
                noDataLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                noDataLabel.setForeground(new Color(108, 117, 125));
                generalChartContainer.add(noDataLabel, BorderLayout.CENTER);
            }
            
            generalChartContainer.revalidate();
            generalChartContainer.repaint();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            showErrorPanel(generalChartContainer, "Lỗi tạo biểu đồ: " + ex.getMessage());
        }
    }

    // Add these two helper methods before the createGeneralBarChart method (around line 4014)

/**
 * Quyết định khi nào nên hiển thị theo tháng thay vì theo ngày
 */
private boolean shouldUseMonthlyFormat(TimeRange range) {
    if (range == null || range.getFrom() == null || range.getTo() == null) return false;
    long days = (range.getTo().getTime() - range.getFrom().getTime()) / (24 * 60 * 60 * 1000) + 1;
    return days >= 90; // Nếu >= 90 ngày thì hiển thị theo tháng
}

private JFreeChart createGeneralLineChart(List<Bill> bills, TimeRange range) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    Map<String, Double> revenueByPeriod = new TreeMap<>();
    
    // Xác định format dựa trên khoảng thời gian
    boolean useMonthly = shouldUseMonthlyFormat(range);
    SimpleDateFormat sdf = useMonthly ? new SimpleDateFormat("MM/yyyy") : new SimpleDateFormat("dd/MM");
    
    // SỬA: Thêm xử lý cho "Quý này" - khởi tạo 3 tháng
    if (useMonthly && isThisQuarterRange(range)) {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH); // 0-based
        int currentQuarter = currentMonth / 3; // 0, 1, 2, 3
        
        // Khởi tạo 3 tháng trong quý
        for (int i = 0; i < 3; i++) {
            int month = currentQuarter * 3 + i + 1; // 1-based
            String monthKey = String.format("%02d/%d", month, currentYear);
            revenueByPeriod.put(monthKey, 0.0);
        }
    }
    // Nếu hiển thị theo tháng và là năm này, khởi tạo 12 tháng
    else if (useMonthly && isThisYearRange(range)) {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        for (int month = 1; month <= 12; month++) {
            String monthKey = String.format("%02d/%d", month, currentYear);
            revenueByPeriod.put(monthKey, 0.0);
        }
    }
    
    for (Bill b : bills) {
        if (b.getStatus() != null && b.getStatus() == 1) {
            java.util.Date dateToUse = b.getCheckout() != null ? b.getCheckout() : b.getCheckin();
            if (dateToUse != null) {
                String periodKey = sdf.format(dateToUse);
                revenueByPeriod.merge(periodKey, b.getTotal_amount(), Double::sum);
            }
        }
    }
    
    if (revenueByPeriod.isEmpty()) return null;
    
    for (Map.Entry<String, Double> entry : revenueByPeriod.entrySet()) {
        dataset.addValue(entry.getValue(), "Doanh thu", entry.getKey());
    }
    
    String periodLabel = useMonthly ? "tháng" : "ngày";
    String axisLabel = useMonthly ? "Tháng" : "Ngày";
    String title = "  Xu hướng doanh thu theo " + periodLabel + " (" + getRangeLabel(range) + ")";
    JFreeChart chart = XChart.createLineChart(title, axisLabel, "VNĐ", dataset);
    
    // SỬA: Để nhãn nằm ngang cho cả tháng và quý
    if (useMonthly) {
        CategoryPlot plot = chart.getCategoryPlot();
        org.jfree.chart.axis.CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setMaximumCategoryLabelWidthRatio(0.8f);
        // SỬA: Để nhãn nằm ngang thay vì xoay 90 độ
        domainAxis.setCategoryLabelPositions(org.jfree.chart.axis.CategoryLabelPositions.STANDARD);
    }
    
    // SỬA: Tự động điều chỉnh chiều cao biểu đồ = max value + 10%
    CategoryPlot plot = chart.getCategoryPlot();
    double maxValue = revenueByPeriod.values().stream()
        .mapToDouble(Double::doubleValue)
        .max()
        .orElse(0.0);
    
    if (maxValue > 0) {
        double upperBound = maxValue * 1.1; // Tăng 10% so với giá trị cao nhất
        plot.getRangeAxis().setUpperBound(upperBound);
        plot.getRangeAxis().setLowerBound(0.0);
    }
    
    // THÊM: Hiển thị số liệu trên từng điểm
    org.jfree.chart.renderer.category.LineAndShapeRenderer renderer = 
        (org.jfree.chart.renderer.category.LineAndShapeRenderer) plot.getRenderer();
    
    renderer.setDefaultItemLabelGenerator(
        new org.jfree.chart.labels.StandardCategoryItemLabelGenerator(
            "{2}", // Pattern để hiển thị giá trị
            new java.text.DecimalFormat("#,##0") // Format số với dấu phẩy ngăn cách hàng nghìn
        )
    );
    renderer.setDefaultItemLabelsVisible(true);
    renderer.setDefaultItemLabelFont(new Font("Tahoma", Font.BOLD, 10));
    renderer.setDefaultItemLabelPaint(Color.BLACK);
    
    // Vị trí label - đặt phía trên điểm
    renderer.setDefaultPositiveItemLabelPosition(
        new org.jfree.chart.labels.ItemLabelPosition(
            org.jfree.chart.labels.ItemLabelAnchor.OUTSIDE12,
            org.jfree.chart.ui.TextAnchor.BOTTOM_CENTER,
            org.jfree.chart.ui.TextAnchor.BOTTOM_CENTER,
            0.0
        )
    );
    
    return chart;
}


/**
 * Kiểm tra xem có phải khoảng thời gian "Năm nay" không
 */
private boolean isThisYearRange(TimeRange range) {
    if (range == null || range.getFrom() == null || range.getTo() == null) return false;
    
    Calendar cal = Calendar.getInstance();
    int currentYear = cal.get(Calendar.YEAR);
    
    Calendar yearStart = Calendar.getInstance();
    yearStart.set(currentYear, Calendar.JANUARY, 1, 0, 0, 0);
    yearStart.set(Calendar.MILLISECOND, 0);
    
    Calendar yearEnd = Calendar.getInstance();
    yearEnd.set(currentYear, Calendar.DECEMBER, 31, 23, 59, 59);
    yearEnd.set(Calendar.MILLISECOND, 999);
    
    long rangeDays = (range.getTo().getTime() - range.getFrom().getTime()) / (24 * 60 * 60 * 1000) + 1;
    long yearDays = (yearEnd.getTimeInMillis() - yearStart.getTimeInMillis()) / (24 * 60 * 60 * 1000) + 1;
    
    return Math.abs(rangeDays - yearDays) <= 5; // Cho phép sai số 5 ngày
}


private boolean isThisQuarterRange(TimeRange range) {
    if (range == null || range.getFrom() == null || range.getTo() == null) return false;
    
    Calendar cal = Calendar.getInstance();
    int currentYear = cal.get(Calendar.YEAR);
    int currentMonth = cal.get(Calendar.MONTH); // 0-based
    
    // Xác định quý hiện tại
    int currentQuarter = currentMonth / 3; // 0, 1, 2, 3
    
    Calendar quarterStart = Calendar.getInstance();
    quarterStart.set(currentYear, currentQuarter * 3, 1, 0, 0, 0);
    quarterStart.set(Calendar.MILLISECOND, 0);
    
    Calendar quarterEnd = Calendar.getInstance();
    quarterEnd.set(currentYear, currentQuarter * 3 + 2, 1, 0, 0, 0); // Tháng cuối quý
    quarterEnd.set(Calendar.DAY_OF_MONTH, quarterEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
    quarterEnd.set(Calendar.HOUR_OF_DAY, 23);
    quarterEnd.set(Calendar.MINUTE, 59);
    quarterEnd.set(Calendar.SECOND, 59);
    quarterEnd.set(Calendar.MILLISECOND, 999);
    
    long rangeDays = (range.getTo().getTime() - range.getFrom().getTime()) / (24 * 60 * 60 * 1000) + 1;
    long quarterDays = (quarterEnd.getTimeInMillis() - quarterStart.getTimeInMillis()) / (24 * 60 * 60 * 1000) + 1;
    
    return Math.abs(rangeDays - quarterDays) <= 5; // Cho phép sai số 5 ngày
}

// SỬA: Cập nhật logic cho cả Bar Chart và Line Chart
private JFreeChart createGeneralBarChart(List<Bill> bills, TimeRange range) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    Map<String, Double> revenueByPeriod = new TreeMap<>();
    
    // Xác định format dựa trên khoảng thời gian
    boolean useMonthly = shouldUseMonthlyFormat(range);
    SimpleDateFormat sdf = useMonthly ? new SimpleDateFormat("MM/yyyy") : new SimpleDateFormat("dd/MM");
    
    // SỬA: Thêm xử lý cho "Quý này" - khởi tạo 3 tháng
    if (useMonthly && isThisQuarterRange(range)) {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH); // 0-based
        int currentQuarter = currentMonth / 3; // 0, 1, 2, 3
        
        // Khởi tạo 3 tháng trong quý
        for (int i = 0; i < 3; i++) {
            int month = currentQuarter * 3 + i + 1; // 1-based
            String monthKey = String.format("%02d/%d", month, currentYear);
            revenueByPeriod.put(monthKey, 0.0);
        }
    }
    // Nếu hiển thị theo tháng và là năm này, khởi tạo 12 tháng
    else if (useMonthly && isThisYearRange(range)) {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        for (int month = 1; month <= 12; month++) {
            String monthKey = String.format("%02d/%d", month, currentYear);
            revenueByPeriod.put(monthKey, 0.0);
        }
    }
    
    for (Bill b : bills) {
        if (b.getStatus() != null && b.getStatus() == 1) { // Chỉ tính hóa đơn hoàn thành
            java.util.Date dateToUse = b.getCheckout() != null ? b.getCheckout() : b.getCheckin();
            if (dateToUse != null) {
                String periodKey = sdf.format(dateToUse);
                revenueByPeriod.merge(periodKey, b.getTotal_amount(), Double::sum);
            }
        }
    }
    
    if (revenueByPeriod.isEmpty()) return null;
    
    for (Map.Entry<String, Double> entry : revenueByPeriod.entrySet()) {
        dataset.addValue(entry.getValue(), "Doanh thu", entry.getKey());
    }
    
    String periodLabel = useMonthly ? "tháng" : "ngày";
    String axisLabel = useMonthly ? "Tháng" : "Ngày";
    String title = "  Doanh thu theo " + periodLabel + " (" + getRangeLabel(range) + ")";
    JFreeChart chart = XChart.createBarChart(title, axisLabel, "VNĐ", dataset);
    
    CategoryPlot plot = chart.getCategoryPlot();
    BarRenderer renderer = (BarRenderer) plot.getRenderer();
    renderer.setSeriesPaint(0, new Color(134, 39, 43));
    renderer.setItemMargin(0.1);
    
    // SỬA: Để nhãn nằm ngang cho cả tháng và quý
    if (useMonthly) {
        org.jfree.chart.axis.CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setMaximumCategoryLabelWidthRatio(0.8f);
        // SỬA: Để nhãn nằm ngang thay vì xoay 90 độ
        domainAxis.setCategoryLabelPositions(org.jfree.chart.axis.CategoryLabelPositions.STANDARD);
    }
    
    // SỬA: Tự động điều chỉnh chiều cao biểu đồ = max value + 10%
    double maxValue = revenueByPeriod.values().stream()
        .mapToDouble(Double::doubleValue)
        .max()
        .orElse(0.0);
    
    if (maxValue > 0) {
        double upperBound = maxValue * 1.1; // Tăng 10% so với giá trị cao nhất
        plot.getRangeAxis().setUpperBound(upperBound);
        plot.getRangeAxis().setLowerBound(0.0);}
    return chart;
    }

    private JFreeChart createGeneralPieChart(List<Bill> bills, TimeRange range) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Integer> statusCount = new HashMap<>();
        
    for (Bill b : bills) {
            String status = getStatusText(b.getStatus());
            statusCount.merge(status, 1, Integer::sum);
        }
        
        if (statusCount.isEmpty()) return null;
        
        for (Map.Entry<String, Integer> entry : statusCount.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        
        String title = "🥧 Phân bố trạng thái hóa đơn (" + getRangeLabel(range) + ")";
        JFreeChart chart = XChart.createPieChart(title, dataset);
    
    return chart;
}
    
    private JFreeChart createGeneralAreaChart(List<Bill> bills, TimeRange range) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Double> cumulativeRevenue = new TreeMap<>();
        
        // Xác định format dựa trên khoảng thời gian
        boolean useMonthly = shouldUseMonthlyFormat(range);
        SimpleDateFormat sdf = useMonthly ? new SimpleDateFormat("MM/yyyy") : new SimpleDateFormat("dd/MM");
        
        // Nếu hiển thị theo tháng và là năm này, khởi tạo 12 tháng
        if (useMonthly && isThisYearRange(range)) {
            Calendar cal = Calendar.getInstance();
            int currentYear = cal.get(Calendar.YEAR);
            for (int month = 1; month <= 12; month++) {
                String monthKey = String.format("%02d/%d", month, currentYear);
                cumulativeRevenue.put(monthKey, 0.0);
            }
        }
        
        // Tính doanh thu cộng dồn
        double runningTotal = 0;
        for (Bill b : bills) {
            if (b.getStatus() != null && b.getStatus() == 1) {
                java.util.Date dateToUse = b.getCheckout() != null ? b.getCheckout() : b.getCheckin();
                if (dateToUse != null) {
                    String periodKey = sdf.format(dateToUse);
                    runningTotal += b.getTotal_amount();
                    cumulativeRevenue.put(periodKey, runningTotal);
                }
            }
        }
        
        if (cumulativeRevenue.isEmpty()) return null;
        
        for (Map.Entry<String, Double> entry : cumulativeRevenue.entrySet()) {
            dataset.addValue(entry.getValue(), "Doanh thu cộng dồn", entry.getKey());
        }
        
        String periodLabel = useMonthly ? "tháng" : "ngày";
        String axisLabel = useMonthly ? "Tháng" : "Ngày";
        String title = "Doanh thu cộng dồn theo " + periodLabel + " (" + getRangeLabel(range) + ")";
        JFreeChart chart = XChart.createAreaChart(title, axisLabel, "VNĐ", dataset);
        
        // Điều chỉnh hiển thị nhãn trục X khi có nhiều tháng
        if (useMonthly) {
            CategoryPlot plot = chart.getCategoryPlot();
            org.jfree.chart.axis.CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setCategoryLabelPositions(org.jfree.chart.axis.CategoryLabelPositions.UP_45);
            domainAxis.setMaximumCategoryLabelWidthRatio(0.8f);
        }
        
        return chart;
    }
    private JPanel createGeneralTableSection() {
        JPanel tableSection = new JPanel(new BorderLayout());
        tableSection.setBackground(Color.WHITE);
        tableSection.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(40, 167, 69), 2),
            " CHI TIẾT HÓA ĐƠN",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(40, 167, 69)
        ));
        tableSection.setPreferredSize(new Dimension(0, 350));
        tableSection.setMinimumSize(new Dimension(0, 300));
        
        // Controls cho bảng
        JPanel tableControls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        tableControls.setBackground(Color.WHITE);
        
        JComboBox<String> cboTableFilter = new JComboBox<>(new String[]{
            "Tất cả hóa đơn", "Chỉ hoàn thành", "Chỉ đã hủy", "Chỉ đang xử lý"
        });
        
        JTextField txtSearch = new JTextField(15);
        txtSearch.setToolTipText("Tìm kiếm theo mã hóa đơn, bàn, hoặc nhân viên...");
        
        JButton btnClearSearch = new JButton("");
        btnClearSearch.setToolTipText("Xóa tìm kiếm");
        btnClearSearch.addActionListener(e -> {
            txtSearch.setText("");
            refreshGeneralTableWithFilters("", "Tất cả hóa đơn");
        });
        
        tableControls.add(new JLabel(" Lọc:"));
        tableControls.add(cboTableFilter);
        tableControls.add(Box.createHorizontalStrut(15));
        tableControls.add(new JLabel("Tìm kiếm:"));
        tableControls.add(txtSearch);
        tableControls.add(btnClearSearch);
        
        tableSection.add(tableControls, BorderLayout.NORTH);
        
        tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(Color.WHITE);
        initGeneralTable(); 
        tableSection.add(tableContainer, BorderLayout.CENTER);
        
        // SỬA: Thêm listeners với timer để tránh spam
        cboTableFilter.addActionListener(e -> {
            String selectedFilter = (String) cboTableFilter.getSelectedItem();
            String searchText = txtSearch.getText().trim();
            refreshGeneralTableWithFilters(searchText, selectedFilter);
        });
        
        txtSearch.addActionListener(e -> {
            String searchText = txtSearch.getText().trim();
            String selectedFilter = (String) cboTableFilter.getSelectedItem();
            refreshGeneralTableWithFilters(searchText, selectedFilter);
        });
        
        // SỬA: Thêm KeyListener với timer để tránh spam thông báo
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                // Hủy timer cũ nếu có
                if (searchTimer != null && searchTimer.isRunning()) {
                    searchTimer.stop();
                }
                
                // Tạo timer mới với delay 800ms để tránh spam
                searchTimer = new javax.swing.Timer(800, e -> {
                    String searchText = txtSearch.getText().trim();
                    String selectedFilter = (String) cboTableFilter.getSelectedItem();
                    refreshGeneralTableWithFiltersSilent(searchText, selectedFilter); // Dùng phiên bản silent
                });
                searchTimer.setRepeats(false);
                searchTimer.start();
            }
        });
        
        return tableSection;
    }

    private void refreshGeneralTableWithFiltersSilent(String searchText, String filterType) {
        try {
            String selectedRange = cboFilterType != null ? (String) cboFilterType.getSelectedItem() : "Tháng này";
            TimeRange range = getSelectedGeneralRange(selectedRange);

            List<Bill> bills = billDAO.findAll();
            List<UserAccount> users = userDAO.findAll();

            if (bills == null) bills = new ArrayList<>();
            if (users == null) users = new ArrayList<>();

            Map<String, String> userIdToName = new HashMap<>();
            for (UserAccount u : users) {
                if (u != null && u.getUser_id() != null) {
                    String name = (u.getFullName() != null && !u.getFullName().trim().isEmpty()) ? 
                        u.getFullName() : u.getUsername();
                    userIdToName.put(u.getUser_id(), name);
                }
            }

            List<Bill> filteredBills = new ArrayList<>();
            
            for (Bill b : bills) {
                if (b == null) continue;
                
                java.util.Date when = b.getCheckout() != null ? b.getCheckout() : b.getCheckin();
                if (when == null) continue;
                if (!withinRange(when, range)) continue;

                // Áp dụng filter theo trạng thái
                boolean passFilter = true;
                if ("Chỉ hoàn thành".equals(filterType) && (b.getStatus() == null || b.getStatus() != 1)) {
                    passFilter = false;
                } else if ("Chỉ đã hủy".equals(filterType) && (b.getStatus() == null || b.getStatus() != 2)) {
                    passFilter = false;
                } else if ("Chỉ đang xử lý".equals(filterType) && (b.getStatus() == null || b.getStatus() != 0)) {
                    passFilter = false;
                }
                
                if (!passFilter) continue;

                // Áp dụng search
                if (!searchText.isEmpty()) {
                    String billId = String.valueOf(b.getBill_id());
                    String tableNumber = String.valueOf(b.getTable_number());
                    String employeeName = userIdToName.getOrDefault(b.getUser_id(), b.getUser_id() != null ? b.getUser_id() : "");
                    
                    boolean matchSearch = billId.toLowerCase().contains(searchText.toLowerCase()) ||
                                        tableNumber.toLowerCase().contains(searchText.toLowerCase()) ||
                                        employeeName.toLowerCase().contains(searchText.toLowerCase());
                    
                    if (!matchSearch) continue;
                }

                filteredBills.add(b);
            }
            
            // SỬA: CHỈ HIỂN THỊ THÔNG BÁO KHI SEARCH TEXT DÀI HƠN 2 KÝ TỰ VÀ KHÔNG CÓ KẾT QUẢ
            if (filteredBills.isEmpty() && searchText.length() > 2 && !searchText.isEmpty()) {
                // Chỉ hiển thị thông báo khi gõ đủ ít nhất 3 ký tự
                XDialog.warning("Không tìm thấy dữ liệu phù hợp với từ khóa: \"" + searchText + "\"", "Không có dữ liệu");
            }

            updateGeneralTable(filteredBills, userIdToName);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            XDialog.error("Lỗi khi lọc/tìm kiếm dữ liệu: " + ex.getMessage(), "Lỗi");
        }
    }



 
    /**
     * Khởi tạo bảng tổng quan
     */
    private void initGeneralTable() {
        billTableModel = new DefaultTableModel(new Object[] { 
            "Mã HĐ", "Bàn", "Nhân viên", "Check-in", "Check-out", "Tổng tiền", "Trạng thái" 
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tblBills = new JTable(billTableModel);
        tblBills.setRowHeight(25);
        tblBills.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tblBills.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));

        // Căn giữa các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tblBills.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblBills.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblBills.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tblBills.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tblBills.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        
        // Căn phải cột tiền
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        tblBills.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);

        // Set độ rộng cột
        tblBills.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblBills.getColumnModel().getColumn(1).setPreferredWidth(50);
        tblBills.getColumnModel().getColumn(2).setPreferredWidth(120);
        tblBills.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblBills.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblBills.getColumnModel().getColumn(5).setPreferredWidth(120);
        tblBills.getColumnModel().getColumn(6).setPreferredWidth(100);

        JScrollPane sp = new JScrollPane(tblBills);
        tableContainer.add(sp, BorderLayout.CENTER);
    }

    /**
     * Làm mới dashboard tổng quan
     */
       private void refreshGeneralTableWithFilters(String searchText, String filterType) {
        try {
            String selectedRange = cboFilterType != null ? (String) cboFilterType.getSelectedItem() : "Tháng này";
            TimeRange range = getSelectedGeneralRange(selectedRange);

            List<Bill> bills = billDAO.findAll();
            List<UserAccount> users = userDAO.findAll();

            if (bills == null) bills = new ArrayList<>();
            if (users == null) users = new ArrayList<>();

            Map<String, String> userIdToName = new HashMap<>();
            for (UserAccount u : users) {
                if (u != null && u.getUser_id() != null) {
                    String name = (u.getFullName() != null && !u.getFullName().trim().isEmpty()) ? 
                        u.getFullName() : u.getUsername();
                    userIdToName.put(u.getUser_id(), name);
                }
            }

            List<Bill> filteredBills = new ArrayList<>();
            
            for (Bill b : bills) {
                if (b == null) continue;
                
                java.util.Date when = b.getCheckout() != null ? b.getCheckout() : b.getCheckin();
                if (when == null) continue;
                if (!withinRange(when, range)) continue;

                // Áp dụng filter theo trạng thái
                boolean passFilter = true;
                if ("Chỉ hoàn thành".equals(filterType) && (b.getStatus() == null || b.getStatus() != 1)) {
                    passFilter = false;
                } else if ("Chỉ đã hủy".equals(filterType) && (b.getStatus() == null || b.getStatus() != 2)) {
                    passFilter = false;
                } else if ("Chỉ đang xử lý".equals(filterType) && (b.getStatus() == null || b.getStatus() != 0)) {
                    passFilter = false;
                }
                
                if (!passFilter) continue;

                // Áp dụng search
                if (!searchText.isEmpty()) {
                    String billId = String.valueOf(b.getBill_id());
                    String tableNumber = String.valueOf(b.getTable_number());
                    String employeeName = userIdToName.getOrDefault(b.getUser_id(), b.getUser_id() != null ? b.getUser_id() : "");
                    
                    boolean matchSearch = billId.toLowerCase().contains(searchText.toLowerCase()) ||
                                        tableNumber.toLowerCase().contains(searchText.toLowerCase()) ||
                                        employeeName.toLowerCase().contains(searchText.toLowerCase());
                    
                    if (!matchSearch) continue;
                }

                filteredBills.add(b);
            }
            
            // SỬA: CHỈ HIỂN THỊ THÔNG BÁO KHI THỰC SỰ CẦN THIẾT
            // Không hiển thị thông báo khi chỉ mới bắt đầu gõ
            
            updateGeneralTable(filteredBills, userIdToName);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            XDialog.error("Lỗi khi lọc/tìm kiếm dữ liệu: " + ex.getMessage(), "Lỗi");
        }
    }

    private void resetTableFiltersAndSearch() {
        try {
            // Kiểm tra null trước khi sử dụng
            if (tableContainer == null || tableContainer.getParent() == null) {
                return;
            }
            
            // Reset search và filter trong general table section
            for (Component comp : tableContainer.getParent().getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel panel = (JPanel) comp;
                    for (Component subComp : panel.getComponents()) {
                        if (subComp instanceof JTextField) {
                            ((JTextField) subComp).setText("");
                        } else if (subComp instanceof JComboBox) {
                            ((JComboBox<?>) subComp).setSelectedItem("Tất cả hóa đơn");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            // Bỏ qua lỗi reset - không quan trọng
            System.err.println("Reset filters warning: " + ex.getMessage());
        }
    }


    private void refreshGeneralDashboard() {
        try {
            String selectedRange = cboFilterType != null ? (String) cboFilterType.getSelectedItem() : "Tháng này";
            TimeRange range = getSelectedGeneralRange(selectedRange);

            List<Bill> bills = billDAO.findAll();
            List<UserAccount> users = userDAO.findAll();

            if (bills == null) bills = new ArrayList<>();
            if (users == null) users = new ArrayList<>();

            Map<String, String> userIdToName = new HashMap<>();
            for (UserAccount u : users) {
                if (u != null && u.getUser_id() != null) {
                    String name = (u.getFullName() != null && !u.getFullName().trim().isEmpty()) ? 
                        u.getFullName() : u.getUsername();
                    userIdToName.put(u.getUser_id(), name);
                }
            }

            List<Bill> filteredBills = new ArrayList<>();
            double totalRevenue = 0;
            int totalOrders = 0;
            int completedOrders = 0;
            int cancelledOrders = 0;
            
            // SỬA: Thêm dấu { bị thiếu ở đây
            for (Bill b : bills) {
                if (b == null) continue;
                
                java.util.Date when = b.getCheckout() != null ? b.getCheckout() : b.getCheckin();
                if (when == null) continue;
                if (!withinRange(when, range)) continue;

                filteredBills.add(b);
                totalRevenue += b.getTotal_amount();
                totalOrders++;
                
                if (b.getStatus() != null) {
                    if (b.getStatus() == 1) {
                        completedOrders++;
                    } else if (b.getStatus() == 2) {
                        cancelledOrders++;
                    }
                }
            }

            // Debug info
            System.out.println("DEBUG: Filtered bills: " + filteredBills.size());
            System.out.println("DEBUG: Total revenue: " + totalRevenue);

            updateGeneralKPIs(totalRevenue, totalOrders, completedOrders, cancelledOrders);
            updateGeneralChart(filteredBills, range);
            updateGeneralTable(filteredBills, userIdToName);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            showErrorPanel(pnlGeneral, "Lỗi tải dữ liệu tổng quan: " + ex.getMessage());
        }
    }

    private void updateGeneralStatsSection() {
        try {
            // Kiểm tra null trước khi sử dụng
            if (pnlGeneral == null) {
                return;
            }
            
            // Tìm và cập nhật các panel thống kê
            for (Component comp : pnlGeneral.getComponents()) {
                if (comp instanceof JScrollPane) {
                    JScrollPane scrollPane = (JScrollPane) comp;
                    Component view = scrollPane.getViewport().getView();
                    if (view instanceof JPanel) {
                        updateStatsInPanel((JPanel) view);
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Update stats warning: " + ex.getMessage());
        }
    }

    // THÊM: Helper method để cập nhật stats
    private void updateStatsInPanel(JPanel mainPanel) {
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                if (panel.getBorder() != null && 
                    panel.getBorder().toString().contains("THỐNG KÊ BỔ SUNG")) {
                    
                    // Cập nhật nội dung thống kê bổ sung
                    for (Component subComp : panel.getComponents()) {
                        if (subComp instanceof JPanel) {
                            JPanel statsContent = (JPanel) subComp;
                            Component[] statsPanels = statsContent.getComponents();
                            
                            if (statsPanels.length >= 3) {
                                // Cập nhật top products
                                updateStatsPanelContent((JPanel) statsPanels[0], getTopProductsData());
                                // Cập nhật hour distribution
                                updateStatsPanelContent((JPanel) statsPanels[1], getHourDistributionData());
                                // Cập nhật success rate
                                updateStatsPanelContent((JPanel) statsPanels[2], getSuccessRateData());
                            }
                        }
                    }
                }
            }
        }
    }

    // THÊM: Helper method để cập nhật nội dung panel
    private void updateStatsPanelContent(JPanel panel, String newContent) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JLabel && ((JLabel) comp).getText().startsWith("<html>")) {
                ((JLabel) comp).setText("<html><center>" + newContent + "</center></html>");
                break;
            }
        }
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Cập nhật KPI cards cho dashboard tổng quan
     */
    private void updateGeneralKPIs(double totalRevenue, int totalOrders, int completedOrders, int cancelledOrders) {
        try {
            // Kiểm tra null trước khi sử dụng
            if (kpiContainer == null) {
                return;
            }
            
            kpiContainer.removeAll();
            
            // XÓA THAM SỐ ICON CUỐI CÙNG
            kpiContainer.add(createKPICard("Tổng doanh thu", 
                String.format("%,.0f VNĐ", totalRevenue), 
                new Color(134, 39, 43)));
            
            kpiContainer.add(createKPICard("Tổng hóa đơn", 
                String.valueOf(totalOrders), 
                new Color(52, 144, 220)));
            
            kpiContainer.add(createKPICard("Hoàn thành", 
                String.valueOf(completedOrders), 
                new Color(40, 167, 69)));
            
            kpiContainer.add(createKPICard("Đã hủy", 
                String.valueOf(cancelledOrders), 
                new Color(220, 53, 69)));
            
            kpiContainer.revalidate();
            kpiContainer.repaint();
            
        } catch (Exception ex) {
            System.err.println("Update KPIs warning: " + ex.getMessage());
        }
    }

    /**
     * Tạo KPI card với icon và màu sắc
     */
    private JPanel createKPICard(String label, String value, Color valueColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(label);
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        titleLabel.setForeground(new Color(108, 117, 125));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa title
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        valueLabel.setForeground(valueColor);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }

    /**
     * Cập nhật bảng tổng quan
     */
    private void updateGeneralTable(List<Bill> bills, Map<String, String> userIdToName) {
        billTableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("vi-VN"));
        
        for (Bill b : bills) {
            if (b == null) continue;
            
            String employeeName = userIdToName.getOrDefault(b.getUser_id(), b.getUser_id());
            String checkinStr = b.getCheckin() != null ? sdf.format(b.getCheckin()) : "";
            String checkoutStr = b.getCheckout() != null ? sdf.format(b.getCheckout()) : "";
            String statusText = getStatusText(b.getStatus());
            
            billTableModel.addRow(new Object[] { 
                b.getBill_id(),
                b.getTable_number(),
                employeeName,
                checkinStr,
                checkoutStr,
                nf.format(b.getTotal_amount()),
                statusText
            });
        }
    }

    /**
     * Lấy khoảng thời gian được chọn cho dashboard tổng quan
     */
    private TimeRange getSelectedGeneralRange(String selected) {
        switch (selected) {
            case "Hôm nay":
                return TimeRange.today();
            case "Tuần này":
                return TimeRange.thisWeek();
            case "Quý này":
                return TimeRange.thisQuarter();
            case "Năm này":
                return TimeRange.thisYear();
            case "Khoảng tùy chỉnh":
                if (dcGeneralFrom != null && dcGeneralTo != null && 
                    dcGeneralFrom.getDate() != null && dcGeneralTo.getDate() != null) {
                    java.util.Date from = normalizeStartOfDay(dcGeneralFrom.getDate());
                    java.util.Date to = normalizeEndOfDay(dcGeneralTo.getDate());
                    return new TimeRange(from, to);
                }
                return TimeRange.thisMonth(); // Fallback
            default:
                return TimeRange.thisMonth();
        }
    }


    /**
     * Lấy label cho khoảng thời gian
     */
    private String getRangeLabel(TimeRange range) {
        if (range == null) return "";
        
        // KIỂM TRA XEM KHOẢNG THỜI GIAN CÓ PHẢI LÀ CÁC KHOẢNG ĐẶC BIỆT KHÔNG
        Calendar cal = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        
        Calendar tomorrow = (Calendar) today.clone();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        
        Calendar weekStart = (Calendar) today.clone();
        weekStart.set(Calendar.DAY_OF_WEEK, weekStart.getFirstDayOfWeek());
        
        Calendar weekEnd = (Calendar) weekStart.clone();
        weekEnd.add(Calendar.DAY_OF_WEEK, 6);
        
        Calendar monthStart = (Calendar) today.clone();
        monthStart.set(Calendar.DAY_OF_MONTH, 1);
        
        Calendar monthEnd = (Calendar) monthStart.clone();
        monthEnd.add(Calendar.MONTH, 1);
        monthEnd.add(Calendar.DAY_OF_MONTH, -1);
        
        Calendar yearStart = (Calendar) today.clone();
        yearStart.set(Calendar.DAY_OF_YEAR, 1);
        
        Calendar yearEnd = (Calendar) yearStart.clone();
        yearEnd.add(Calendar.YEAR, 1);
        yearEnd.add(Calendar.DAY_OF_YEAR, -1);
        
        // So sánh với các khoảng thời gian đặc biệt
        if (range.getFrom().equals(today.getTime()) && range.getTo().equals(tomorrow.getTime())) {
            return "Hôm nay";
        } else if (range.getFrom().equals(weekStart.getTime()) && range.getTo().equals(weekEnd.getTime())) {
            return "Tuần này";
        } else if (range.getFrom().equals(monthStart.getTime()) && range.getTo().equals(monthEnd.getTime())) {
            return "Tháng này";
        } else if (range.getFrom().equals(yearStart.getTime()) && range.getTo().equals(yearEnd.getTime())) {
            return "Năm nay";
        } else {
            // Nếu không phải khoảng đặc biệt thì trả về ngày tháng
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fromStr = sdf.format(range.getFrom());
            String toStr = sdf.format(range.getTo());
            
            if (fromStr.equals(toStr)) {
                return fromStr;
            } else {
                return fromStr + " - " + toStr;
            }
        }
    }

    /**
     * Lấy text trạng thái hóa đơn
     */
    private String getStatusText(Integer status) {
        if (status == null) return "Không xác định";
        switch (status) {
            case 0: return "Đang phục vụ";
            case 1: return "Đã thanh toán";
            case 2: return "Đã hủy";
            default: return "Không xác định";
        }
    }

    /**
     * Cập nhật bảng doanh thu nhân viên với thông tin chi tiết
     */
   
    /**
     * Khởi tạo bảng hiển thị doanh thu nhân viên - Enhanced version
     */
  
    /**
     * Làm mới dữ liệu doanh thu theo nhân viên - Enhanced version
     */
    private void refreshEmployeeRevenueData() {
        try {
            String selectedRange = cboEmpRange != null ? (String) cboEmpRange.getSelectedItem() : "Tháng này";
            TimeRange range = getSelectedEmpRange(selectedRange);

            // Lấy dữ liệu cho khoảng thời gian chính
            List<Bill> bills = billDAO.findAll();
            List<UserAccount> users = userDAO.findAll();

            if (bills == null) bills = new ArrayList<>();
            if (users == null) users = new ArrayList<>();

            Map<String, String> userIdToName = new HashMap<>();
            Map<String, String> userIdToRole = new HashMap<>();
            for (UserAccount u : users) {
                if (u != null && u.getUser_id() != null) {
                    String name = (u.getFullName() != null && !u.getFullName().trim().isEmpty()) ? 
                        u.getFullName() : u.getUsername();
                    userIdToName.put(u.getUser_id(), name);
                    userIdToRole.put(u.getUser_id(), u.getRole_id());
                }
            }

            Map<String, Double> revenueByEmployee = new HashMap<>();
            Map<String, Integer> ordersByEmployee = new HashMap<>();
            Map<String, Double> avgOrderValue = new HashMap<>();
            
            // Tính toán dữ liệu cho khoảng thời gian chính
            for (Bill b : bills) {
                if (b == null) continue;
                if (b.getStatus() == null || b.getStatus() != 1) continue;
                
                java.util.Date when = b.getCheckout() != null ? b.getCheckout() : b.getCheckin();
                if (when == null) continue;
                if (!withinRange(when, range)) continue;

                String userId = b.getUser_id();
                if (userId == null) userId = "N/A";
                
                double revenue = b.getTotal_amount();
                revenueByEmployee.merge(userId, revenue, Double::sum);
                ordersByEmployee.merge(userId, 1, Integer::sum);
            }

            // KIỂM TRA XEM CÓ DỮ LIỆU TRONG KHOẢNG THỜI GIAN ĐÃ CHỌN KHÔNG
            if (revenueByEmployee.isEmpty()) {
                // Không có dữ liệu - hiển thị thông báo và reset về mặc định
                XDialog.warning("Không có dữ liệu biểu đồ trong khoảng thời gian đã chọn.\nHệ thống sẽ reset về khoảng thời gian mặc định.", "Thông báo");
                
                // Reset về mặc định
                resetEmployeeTabToDefault();
                return;
            }

            // TÍNH NĂNG SO SÁNH THẬT SỰ - CHỈ KHI TÍCH CHECKBOX SO SÁNH
            Map<String, Double> comparisonRevenueByEmployee = new HashMap<>();
            String comparisonLabel = "";
            boolean hasComparisonData = false;
            
            if (chkShowComparison != null && chkShowComparison.isSelected()) {
                // Lấy khoảng thời gian so sánh dựa trên khoảng thời gian hiện tại
                TimeRange comparisonRange = getComparisonTimeRange(range);
                
                if (comparisonRange != null) {
                    // Tính toán dữ liệu cho khoảng thời gian so sánh
                    for (Bill b : bills) {
                        if (b == null) continue;
                        if (b.getStatus() == null || b.getStatus() != 1) continue;
                        
                        java.util.Date when = b.getCheckout() != null ? b.getCheckout() : b.getCheckin();
                        if (when == null) continue;
                        if (!withinRange(when, comparisonRange)) continue;

                        String userId = b.getUser_id();
                        if (userId == null) userId = "N/A";
                        
                        double revenue = b.getTotal_amount();
                        comparisonRevenueByEmployee.merge(userId, revenue, Double::sum);
                    }
                    
                    // Kiểm tra xem có dữ liệu so sánh không
                    if (!comparisonRevenueByEmployee.isEmpty()) {
                        hasComparisonData = true;
                        comparisonLabel = getComparisonLabel(range);
                    } else {
                        // Không có dữ liệu so sánh - hiển thị thông báo
                        XDialog.warning("Không có dữ liệu để so sánh trong khoảng thời gian trước đó.\nVui lòng chọn khoảng thời gian khác.", "Thông báo so sánh");
                        
                        // Tắt checkbox so sánh
                        chkShowComparison.setSelected(false);
                        hasComparisonData = false;
                    }
                }
            }

            // Tính doanh thu trung bình trên hóa đơn
            for (String userId : revenueByEmployee.keySet()) {
                double revenue = revenueByEmployee.get(userId);
                int orders = ordersByEmployee.get(userId);
                if (orders > 0) {
                    avgOrderValue.put(userId, revenue / orders);
                }
            }

            // Tính tổng doanh thu để tính tỷ lệ phần trăm
            double totalRevenue = revenueByEmployee.values().stream().mapToDouble(Double::doubleValue).sum();

            List<Map.Entry<String, Double>> sortedByRevenue = new ArrayList<>(revenueByEmployee.entrySet());
            sortedByRevenue.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

            // Tạo biểu đồ chính với màu sắc đa dạng
            if (hasComparisonData) {
                // Tạo biểu đồ so sánh THẬT SỰ
                createComparisonEmployeeChart(sortedByRevenue, userIdToName, range, 
                    comparisonRevenueByEmployee, comparisonLabel);
            } else {
                // Tạo biểu đồ đơn giản
                createMainEmployeeChart(sortedByRevenue, userIdToName, range);
            }
            
            // Tạo thống kê và tỷ lệ phần trăm
            createEmployeeStats(sortedByRevenue, userIdToName, totalRevenue);
            
            // TẠO BIỂU ĐỒ PHỤ CHỈ KHI TÍCH CHECKBOX NĂNG SUẤT
            if (chkShowProductivity != null && chkShowProductivity.isSelected()) {
                createSecondaryEmployeeCharts(sortedByRevenue, userIdToName, ordersByEmployee, avgOrderValue);
            }
            
            // Cập nhật bảng với thông tin chi tiết
            updateEmpTableEnhanced(sortedByRevenue, ordersByEmployee, avgOrderValue, userIdToName, userIdToRole);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            showErrorPanel(jPanel5, "Lỗi tải dữ liệu doanh thu theo nhân viên: " + ex.getMessage());
        }
    }

    /**
     * Khởi tạo bảng hiển thị doanh thu nhân viên - Enhanced version với border
     */
    private void initEmpTable() {
        empTableModel = new DefaultTableModel(new Object[] { 
            "Mã NV", "Tên nhân viên", "Vai trò", "Số hóa đơn", "Doanh thu (VNĐ)", "TB/HĐ (VNĐ)" 
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tblEmp = new JTable(empTableModel);
        tblEmp.setRowHeight(32); // Tăng chiều cao hàng để dễ đọc
        tblEmp.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tblEmp.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        
        // TẮT KHẢ NĂNG DI CHUYỂN CỘT
        tblEmp.getTableHeader().setReorderingAllowed(false);
        
        // Thêm border cho bảng
        tblEmp.setShowGrid(true);
        tblEmp.setGridColor(new Color(200, 200, 200));
        tblEmp.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        
        // Tùy chỉnh header - ĐỔI MÀU GIỐNG TAB DOANH THU THEO MÓN
        tblEmp.getTableHeader().setBackground(new Color(134, 39, 43)); // Màu đỏ đậm FiveC theme
        tblEmp.getTableHeader().setForeground(Color.WHITE); // Chữ trắng để dễ đọc
        tblEmp.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));

        // Căn giữa các cột - GIỮ NGUYÊN GRID
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        tblEmp.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Mã NV
        tblEmp.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Vai trò
        tblEmp.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Số hóa đơn
        
        // Căn phải các cột tiền - GIỮ NGUYÊN GRID
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        rightRenderer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        tblEmp.getColumnModel().getColumn(4).setCellRenderer(rightRenderer); // Doanh thu
        tblEmp.getColumnModel().getColumn(5).setCellRenderer(rightRenderer); // TB/HĐ
        
        // Căn trái cho tên nhân viên - GIỮ NGUYÊN GRID
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        leftRenderer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        tblEmp.getColumnModel().getColumn(1).setCellRenderer(leftRenderer); // Tên nhân viên

        // Set độ rộng cột
        tblEmp.getColumnModel().getColumn(0).setPreferredWidth(80);   // Mã NV
        tblEmp.getColumnModel().getColumn(1).setPreferredWidth(180);  // Tên nhân viên
        tblEmp.getColumnModel().getColumn(2).setPreferredWidth(100);  // Vai trò (tăng để hiển thị "Admin"/"Nhân viên")
        tblEmp.getColumnModel().getColumn(3).setPreferredWidth(100);  // Số hóa đơn
        tblEmp.getColumnModel().getColumn(4).setPreferredWidth(140);  // Doanh thu
        tblEmp.getColumnModel().getColumn(5).setPreferredWidth(140);  // TB/HĐ

        JScrollPane sp = new JScrollPane(tblEmp);
        sp.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        empTableContainer.add(sp, BorderLayout.CENTER);
    }

    /**
     * Cập nhật bảng doanh thu nhân viên với thông tin chi tiết - Enhanced version
     */
    private void updateEmpTableEnhanced(List<Map.Entry<String, Double>> sortedRevenue, 
                               Map<String, Integer> ordersByEmployee, 
                                      Map<String, Double> avgOrderValue,
                                      Map<String, String> userIdToName,
                                      Map<String, String> userIdToRole) {
        empTableModel.setRowCount(0);
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("vi-VN"));
        
        for (Map.Entry<String, Double> entry : sortedRevenue) {
            String userId = entry.getKey();
            String name = userIdToName.getOrDefault(userId, userId);
            
            // Đổi vai trò từ R001/R002 thành Admin/Nhân viên
            String roleDisplay = getRoleDisplayName(userIdToRole.getOrDefault(userId, "N/A"));
            
            int orders = ordersByEmployee.getOrDefault(userId, 0);
            double revenue = entry.getValue() != null ? entry.getValue() : 0.0;
            double avgValue = avgOrderValue.getOrDefault(userId, 0.0);
            
            // LÀM TRÒN SỐ THEO QUY TẮC 0.5
            long roundedRevenue = Math.round(revenue);
            
            // LÀM TRÒN TB/HĐ TỚI HÀNG NGHÌN THEO QUY TẮC 0.5
            // Ví dụ: 165.631,579 -> 165.632, 165.500 -> 165.500
            long roundedAvgValue = Math.round(avgValue / 1000.0) * 1000;
            
            // Format số tiền với dấu phẩy ngăn cách hàng nghìn và làm tròn
            String formattedRevenue = nf.format(roundedRevenue);
            String formattedAvgValue = nf.format(roundedAvgValue);
            
            // ĐỊNH DẠNG TÊN NHÂN VIÊN ĐẸP HƠN VỚI HTML
            String formattedName = String.format(
                "<html><div style='text-align: left; line-height: 1.3; padding: 5px;'>%s</div></html>",
                name
            );
            
            empTableModel.addRow(new Object[] { 
                userId, 
                formattedName, // SỬ DỤNG TÊN ĐÃ ĐỊNH DẠNG
                roleDisplay,
                orders, 
                formattedRevenue,
                formattedAvgValue
            });
        }
        
        // Tự động điều chỉnh độ rộng cột sau khi có dữ liệu
        for (int i = 0; i < tblEmp.getColumnCount(); i++) {
            tblEmp.getColumnModel().getColumn(i).setPreferredWidth(
                tblEmp.getColumnModel().getColumn(i).getPreferredWidth()
            );
        }
    }
    
    /**
     * Chuyển đổi mã vai trò thành tên hiển thị
     */
    private String getRoleDisplayName(String roleId) {
        if (roleId == null) return "N/A";
        switch (roleId) {
            case "R001": return "Admin";
            case "R002": return "Nhân viên";
            default: return roleId;
        }
    }

    // Thêm method mới để tạo biểu đồ chính (dòng 3750-3800):
    private void createMainEmployeeChart(List<Map.Entry<String, Double>> sortedByRevenue, 
                                       Map<String, String> userIdToName, TimeRange range) {
        try {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            
            // Tạo dataset với TẤT CẢ nhân viên trong 1 series duy nhất để sửa lệch cột
            for (int i = 0; i < sortedByRevenue.size(); i++) {
                Map.Entry<String, Double> entry = sortedByRevenue.get(i);
                String userId = entry.getKey();
                String displayName = userIdToName.getOrDefault(userId, userId);
                double revenue = entry.getValue();
                
                // TẤT CẢ nhân viên vào 1 series "Doanh thu" - KHÔNG tạo series riêng
                dataset.addValue(revenue, "Doanh thu", displayName);
            }

            String title = "Doanh thu theo nhân viên (" + getRangeLabel(range) + ")";
            JFreeChart chart = XChart.createBarChart(title, "Nhân viên", "VNĐ", dataset);
            
            CategoryPlot plot = chart.getCategoryPlot();
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            
            // Tự động scale trục Y theo max data - TĂNG MARGIN ĐỂ SỐ KHÔNG BỊ CẮT
            double maxValue = sortedByRevenue.stream()
                .mapToDouble(e -> e.getValue() != null ? e.getValue() : 0.0)
                .max()
                .orElse(0.0);
            
            if (maxValue > 0) {
                double upperBound = maxValue * 1.2; // TĂNG TỪ 1.1 LÊN 1.2 (20% thay vì 10%)
                plot.getRangeAxis().setUpperBound(upperBound);
                plot.getRangeAxis().setLowerBound(0.0);
            }
            
            // CĂN GIỮA CÁC CỘT TRONG CATEGORY - SỬA LỆCH CỘT
            org.jfree.chart.axis.CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setCategoryMargin(0.1);
            plot.setDomainGridlinesVisible(true);
            
            // MÀU SẮC ĐA DẠNG CHO TỪNG NHÂN VIÊN - TẤT CẢ CÁC KHOẢNG THỜI GIAN ĐỀU CÙNG MÀU
            // SỬ DỤNG MÀU XANH CHO TẤT CẢ CÁC KHOẢNG THỜI GIAN (HÔM NAY, TUẦN NÀY, THÁNG NÀY, QUÝ NÀY, NĂM NÀY, KHOẢNG TÙY Ý)
            Color[] colors = {
                new Color(52, 144, 220),  // Xanh dương
                new Color(40, 167, 69),   // Xanh lá
                new Color(0, 123, 255),   // Xanh dương sáng
                new Color(52, 144, 220),  // Xanh dương
                new Color(40, 167, 69),   // Xanh lá
                new Color(0, 123, 255),   // Xanh dương sáng
                new Color(52, 144, 220),  // Xanh dương
                new Color(40, 167, 69),   // Xanh lá
                new Color(0, 123, 255),   // Xanh dương sáng
                new Color(52, 144, 220)   // Xanh dương
            };
            
            // GIỮ LẠI MÀU SẮC ĐA DẠNG - Sử dụng GradientBarPainter để tạo màu khác nhau cho từng cột
            for (int i = 0; i < sortedByRevenue.size(); i++) {
                Color color = colors[i % colors.length];
                
                // Tạo custom renderer cho từng cột để có màu khác nhau
                org.jfree.chart.renderer.category.GradientBarPainter painter = new org.jfree.chart.renderer.category.GradientBarPainter();
                renderer.setBarPainter(painter);
                
                // Set màu cho từng item (cột) trong series
                renderer.setSeriesPaint(0, color);
            }
            
            // CĂN CHỈNH CỘT ĐỂ NẰM Ở GIỮA TÊN NHÂN VIÊN
            renderer.setItemMargin(0.1);
            renderer.setMaximumBarWidth(0.8);
            
            // Tùy chỉnh khoảng cách và hiển thị để số không bị che
            renderer.setDefaultItemLabelGenerator(
                new StandardCategoryItemLabelGenerator(
                    "{2}", // Pattern để hiển thị giá trị - KHÔNG ĐƯỢC NULL
                    new java.text.DecimalFormat("#,##0") // Format làm tròn về số nguyên, dấu phẩy ngăn cách hàng nghìn
                )
            );
            renderer.setDefaultItemLabelsVisible(true);
            
            // Tùy chỉnh font và vị trí label để số hiển thị rõ ràng
            renderer.setDefaultItemLabelFont(new Font("Tahoma", Font.BOLD, 11));
            renderer.setDefaultItemLabelPaint(Color.BLACK);
            
            // Tùy chỉnh vị trí label - đặt phía trên cột và số nằm NGANG
            renderer.setDefaultPositiveItemLabelPosition(
                new org.jfree.chart.labels.ItemLabelPosition(
                    org.jfree.chart.labels.ItemLabelAnchor.OUTSIDE12
                    , org.jfree.chart.ui.TextAnchor.BOTTOM_CENTER
                    , org.jfree.chart.ui.TextAnchor.BOTTOM_CENTER
                    , 0.0
                )
            );
            
            // Tùy chỉnh màu nền cho biểu đồ
            plot.setBackgroundPaint(Color.WHITE);
            plot.setDomainGridlinePaint(new Color(200, 200, 200));
            plot.setRangeGridlinePaint(new Color(200, 200, 200));
            
            // TĂNG MARGIN CHO BIỂU ĐỒ ĐỂ SỐ KHÔNG BỊ CẮT
            plot.setAxisOffset(new org.jfree.chart.ui.RectangleInsets(40, 20, 20, 20)); // TĂNG TỪ (20,10,10,10) LÊN (40,20,20,20)
            
            // Tùy chỉnh legend để hiển thị rõ ràng
            chart.getLegend().setPosition(org.jfree.chart.ui.RectangleEdge.BOTTOM);
            chart.getLegend().setBackgroundPaint(Color.WHITE);
            chart.getLegend().setBorder(1.0, 1.0, 1.0, 1.0);
            chart.getLegend().setItemFont(new Font("Tahoma", Font.PLAIN, 11));
            
            ChartPanel panel = XChart.createChartPanel(chart);
            empChartContainer.removeAll();
            empChartContainer.add(panel, BorderLayout.CENTER);
            empChartContainer.revalidate();
            empChartContainer.repaint();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Thêm method mới để tạo thống kê và tỷ lệ phần trăm (dòng 3800-3850):
    private void createEmployeeStats(List<Map.Entry<String, Double>> sortedByRevenue, 
                                   Map<String, String> userIdToName, double totalRevenue) {
        try {
            empStatsContainer.removeAll();
            
            if (sortedByRevenue.isEmpty()) {
                empStatsContainer.add(createStatCard("Không có dữ liệu", "0", new Color(108, 117, 125)));
                empStatsContainer.revalidate();
                empStatsContainer.repaint();
                return;
            }

            // Top 1 nhân viên - SỬA ĐỂ TẤT CẢ TEXT CÓ CÙNG ĐỘ ĐẬM
            Map.Entry<String, Double> topEmployee = sortedByRevenue.get(0);
            String topName = userIdToName.getOrDefault(topEmployee.getKey(), topEmployee.getKey());
            double topRevenue = topEmployee.getValue();
            double topPercentage = totalRevenue > 0 ? (topRevenue / totalRevenue) * 100 : 0;
            
            // TẠO TEXT ĐỊNH DẠNG - TẤT CẢ ĐỀU CÓ CÙNG FONT WEIGHT VÀ SIZE
            // TĂNG KÍCH THƯỚC CHỮ VÀ ĐIỀU CHỈNH KHOẢNG CÁCH ĐỂ TEXT KHÔNG BỊ CẮT
            String topContent = String.format(
                "<html><div style='text-align: center; line-height: 1.8; width: 100%%; padding: 10px;'>" +
                "<div style='font-size: 16px; font-weight: bold; margin-bottom: 10px; color: #FFC107; word-wrap: break-word;'>%s</div>" +
                "<div style='font-size: 16px; font-weight: bold; margin-bottom: 8px; color: #FFC107;'>%,.0f VNĐ</div>" +
                "<div style='font-size: 16px; font-weight: bold; color: #FFC107;'>%.1f%%</div>" +
                "</div></html>",
                topName, topRevenue, topPercentage
            );
            
            empStatsContainer.add(createStatCard(" Top 1", topContent, new Color(255, 193, 7)));
            
            // Tổng doanh thu - TĂNG KÍCH THƯỚC CHỮ CHO ĐỘ RỘNG MỚI
            empStatsContainer.add(createStatCard(" Tổng doanh thu", 
                String.format("<html><div style='text-align: center; font-size: 16px; font-weight: bold; line-height: 1.8; padding: 10px; width: 100%%;'>%,.0f VNĐ</div></html>", totalRevenue), 
                new Color(134, 39, 43)));
            
            // Số nhân viên - TĂNG KÍCH THƯỚC CHỮ CHO ĐỘ RỘNG MỚI
            empStatsContainer.add(createStatCard(" Số nhân viên", 
                String.format("<html><div style='text-align: center; font-size: 16px; font-weight: bold; line-height: 1.8; padding: 5px;'>%d</div></html>", sortedByRevenue.size()), 
                new Color(52, 144, 220)));
            
            // Doanh thu trung bình - TĂNG KÍCH THƯỚC CHỮ CHO ĐỘ RỘNG MỚI
            double avgRevenue = totalRevenue / sortedByRevenue.size();
            empStatsContainer.add(createStatCard("  TB/Nhân viên", 
                String.format("<html><div style='text-align: center; font-size: 16px; font-weight: bold; line-height: 1.8; padding: 5px;'>%,.0f VNĐ</div></html>", avgRevenue), 
                new Color(40, 167, 69)));
            
            empStatsContainer.revalidate();
            empStatsContainer.repaint();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Thêm method mới để tạo biểu đồ phụ (dòng 3850-3900):
    private void createSecondaryEmployeeCharts(List<Map.Entry<String, Double>> sortedByRevenue, 
                                             Map<String, String> userIdToName,
                               Map<String, Integer> ordersByEmployee, 
                                             Map<String, Double> avgOrderValue) {
        try {
            empSecondaryChartContainer.removeAll();
            
            // Tạo tabbed pane cho 2 biểu đồ phụ
            javax.swing.JTabbedPane secondaryTabs = new javax.swing.JTabbedPane();
            secondaryTabs.setFont(new Font("Tahoma", Font.BOLD, 12));
            
            // Tab 1: Số hóa đơn
            JPanel ordersPanel = new JPanel(new BorderLayout());
            ordersPanel.setBackground(Color.WHITE);
            
            DefaultCategoryDataset ordersDataset = new DefaultCategoryDataset();
            for (Map.Entry<String, Double> entry : sortedByRevenue) {
            String userId = entry.getKey();
                String displayName = userIdToName.getOrDefault(userId, userId);
            int orders = ordersByEmployee.getOrDefault(userId, 0);
                ordersDataset.addValue(orders, "Số hóa đơn", displayName);
            }
            
            JFreeChart ordersChart = XChart.createBarChart("Số hóa đơn theo nhân viên", "Nhân viên", "Số hóa đơn", ordersDataset);
            CategoryPlot ordersPlot = ordersChart.getCategoryPlot();
            BarRenderer ordersRenderer = (BarRenderer) ordersPlot.getRenderer();
            ordersRenderer.setSeriesPaint(0, new Color(52, 144, 220)); // Màu xanh dương
            
            // Tự động scale trục Y cho biểu đồ số hóa đơn
            double maxOrders = ordersByEmployee.values().stream().mapToDouble(Integer::doubleValue).max().orElse(0.0);
            if (maxOrders > 0) {
                double upperBound = maxOrders * 1.2; // Thêm 20% margin
                ordersPlot.getRangeAxis().setUpperBound(upperBound);
                ordersPlot.getRangeAxis().setLowerBound(0.0);
            }
            
            // Căn giữa các cột
            org.jfree.chart.axis.CategoryAxis ordersDomainAxis = ordersPlot.getDomainAxis();
            ordersDomainAxis.setCategoryMargin(0.1);
            ordersPlot.setDomainGridlinesVisible(true);
            
            // Tùy chỉnh khoảng cách cột
            ordersRenderer.setItemMargin(0.1);
            ordersRenderer.setMaximumBarWidth(0.8);
            
            ChartPanel ordersChartPanel = XChart.createChartPanel(ordersChart);
            ordersPanel.add(ordersChartPanel, BorderLayout.CENTER);
            
            // Tab 2: Doanh thu trung bình trên hóa đơn
            JPanel avgPanel = new JPanel(new BorderLayout());
            avgPanel.setBackground(Color.WHITE);
            
            DefaultCategoryDataset avgDataset = new DefaultCategoryDataset();
            for (Map.Entry<String, Double> entry : sortedByRevenue) {
                String userId = entry.getKey();
                String displayName = userIdToName.getOrDefault(userId, userId);
                double avgValue = avgOrderValue.getOrDefault(userId, 0.0);
                
                // LÀM TRÒN SỐ TB/HĐ TỚI HÀNG NGHÌN THEO QUY TẮC 0.5
                long roundedAvgValue = Math.round(avgValue / 1000.0) * 1000;
                avgDataset.addValue(roundedAvgValue, "TB/HĐ", displayName);
            }
            
            JFreeChart avgChart = XChart.createBarChart("Doanh thu TB/HĐ theo nhân viên", "Nhân viên", "VNĐ", avgDataset);
            CategoryPlot avgPlot = avgChart.getCategoryPlot();
            BarRenderer avgRenderer = (BarRenderer) avgPlot.getRenderer();
            avgRenderer.setSeriesPaint(0, new Color(40, 167, 69)); // Màu xanh lá
            
            // THÊM SỐ LIỆU TRÊN CÁC CỘT CHO BIỂU ĐỒ TB/HĐ - LÀM TRÒN SỐ
            avgRenderer.setDefaultItemLabelGenerator(
                new StandardCategoryItemLabelGenerator(
                    "{2}", // Pattern để hiển thị giá trị
                    new java.text.DecimalFormat("#,##0") // Format làm tròn về số nguyên
                )
            );
            avgRenderer.setDefaultItemLabelsVisible(true);
            
            // Vị trí label - đặt phía trên cột và số nằm NGANG
            avgRenderer.setDefaultPositiveItemLabelPosition(
                new org.jfree.chart.labels.ItemLabelPosition(
                    org.jfree.chart.labels.ItemLabelAnchor.OUTSIDE12,
                    org.jfree.chart.ui.TextAnchor.BOTTOM_CENTER,
                    org.jfree.chart.ui.TextAnchor.BOTTOM_CENTER,
                    0.0
                )
            );
            
            // Tự động scale trục Y cho biểu đồ TB/HĐ
            double maxAvgValue = avgOrderValue.values().stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
            if (maxAvgValue > 0) {
                double upperBound = maxAvgValue * 1.2; // Thêm 20% margin
                avgPlot.getRangeAxis().setUpperBound(upperBound);
                avgPlot.getRangeAxis().setLowerBound(0.0);
            }
            
            // Căn giữa các cột
            org.jfree.chart.axis.CategoryAxis avgDomainAxis = avgPlot.getDomainAxis();
            avgDomainAxis.setCategoryMargin(0.1);
            avgPlot.setDomainGridlinesVisible(true);
            
            // Tùy chỉnh khoảng cách cột
            avgRenderer.setItemMargin(0.1);
            avgRenderer.setMaximumBarWidth(0.8);
            
            ChartPanel avgChartPanel = XChart.createChartPanel(avgChart);
            avgPanel.add(avgChartPanel, BorderLayout.CENTER);
            
            // Thêm các tab vào tabbed pane
            secondaryTabs.addTab("  Số hóa đơn", ordersPanel);
            secondaryTabs.addTab("  TB/HĐ", avgPanel);
            
            empSecondaryChartContainer.add(secondaryTabs, BorderLayout.CENTER);
            empSecondaryChartContainer.revalidate();
            empSecondaryChartContainer.repaint();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Thêm method mới để tạo stat card (dòng 3900-3950):
    private JPanel createStatCard(String title, String value, Color valueColor) {
        JPanel card = new JPanel(new BorderLayout());
   
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10) // TĂNG PADDING ĐỂ TEXT CÓ KHÔNG GIAN TỐT HƠN
        ));
        
        // TĂNG CHIỀU CAO VÀ CHIỀU RỘNG CỦA CARD ĐỂ TEXT KHÔNG BỊ CẮT
        card.setPreferredSize(new Dimension(400, 250)); // TĂNG CHIỀU RỘNG TỪ 350 LÊN 400, CHIỀU CAO TỪ 140 LÊN 160
        card.setMinimumSize(new Dimension(380, 230));   // TĂNG CHIỀU RỘNG TỪ 330 LÊN 380, CHIỀU CAO TỪ 130 LÊN 150
        card.setMaximumSize(new Dimension(400, 250));
   
        // KÍCH THƯỚC TITLE VỪA PHẢI - ĐẢM BẢO CÙNG ĐỘ ĐẬM
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 13)); // LUÔN BOLD
        titleLabel.setForeground(new Color(108, 117, 125));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER); // CĂN GIỮA THEO CHIỀU DỌC
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0)); // THÊM PADDING TRÊN VÀ TĂNG KHOẢNG CÁCH

        // KÍCH THƯỚC VALUE VỪA PHẢI - ĐẢM BẢO CÙNG ĐỘ ĐẬM
        JLabel valueLabel = new JLabel(value);
        // KIỂM TRA NẾU LÀ HTML THÌ KHÔNG SET FONT, NGƯỢC LẠI THÌ SET FONT MẶC ĐỊNH
        if (value.startsWith("<html>")) {
            // HTML content - không set font để HTML có thể điều khiển hoàn toàn
            valueLabel.setForeground(valueColor);
        } else {
            // Plain text - set font mặc định
            valueLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
            valueLabel.setForeground(valueColor);
        }
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valueLabel.setVerticalAlignment(SwingConstants.CENTER);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        // SỬA: Tăng height của value label từ 120 lên 140
        valueLabel.setPreferredSize(new Dimension(300, 140)); // Tăng từ 120 lên 140
        valueLabel.setMaximumSize(new Dimension(300, 140));  // Tăng từ 120 lên 140
        valueLabel.setMinimumSize(new Dimension(280, 140));  // Tăng từ 120 lên 140

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card; 
    }

    // Thêm method mới để reset tab nhân viên về mặc định (dòng 3950-4000):
    /**
     * Reset tab nhân viên về mặc định khi không có dữ liệu hoặc khi bấm nút làm mới
     */
    private void resetEmployeeTabToDefault() {
        try {
            // Reset combo box về mặc định
            if (cboEmpRange != null) {
                cboEmpRange.setSelectedItem("Tháng này");
            }
            if (cboEmpCompare != null) {
                cboEmpCompare.setSelectedItem("Không so sánh");
            }
            
            // Reset date chooser
            if (dcEmpFrom != null && dcEmpTo != null) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, 1);
                dcEmpFrom.setDate(cal.getTime());
                dcEmpTo.setDate(Calendar.getInstance().getTime());
                dcEmpFrom.setVisible(false);
                dcEmpTo.setVisible(false);
            }
            
            // RESET CHECKBOX VỀ TRẠNG THÁI UNCHECK - TRẠNG THÁI BAN ĐẦU
            if (chkShowComparison != null) {
                chkShowComparison.setSelected(false);
            }
            if (chkShowProductivity != null) {
                chkShowProductivity.setSelected(false);
            }
            
            // Ẩn các thành phần năng suất khi reset
            if (empSecondaryChartContainer != null) {
                empSecondaryChartContainer.setVisible(false);
            }
            
            // Cập nhật layout
            if (empSecondaryChartContainer != null) {
                empSecondaryChartContainer.getParent().revalidate();
                empSecondaryChartContainer.getParent().repaint();
            }
            
            // KHÔNG XÓA BẢNG - CHỈ RESET CÁC CONTROL VỀ MẶC ĐỊNH
            // showNoDataMessage(); // BỎ DÒNG NÀY
            
            // TẢI LẠI DỮ LIỆU VỚI KHOẢNG THỜI GIAN MẶC ĐỊNH
            refreshEmployeeRevenueData();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Thêm method mới để hiển thị thông báo "Không có dữ liệu" (dòng 4000-4050):
    /**
     * Hiển thị thông báo "Không có dữ liệu" trong các container
     */
    private void showNoDataMessage() {
        try {
            // Hiển thị thông báo trong biểu đồ chính
            empChartContainer.removeAll();
            JLabel noDataLabel = new JLabel("<html><center>  Không có dữ liệu biểu đồ<br/>trong khoảng thời gian đã chọn</center></html>");
            noDataLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
            noDataLabel.setForeground(new Color(108, 117, 125));
            noDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noDataLabel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
            empChartContainer.add(noDataLabel, BorderLayout.CENTER);
            empChartContainer.revalidate();
            empChartContainer.repaint();
            
            // Hiển thị thông báo trong thống kê
            empStatsContainer.removeAll();
            empStatsContainer.add(createStatCard(" Không có dữ liệu", "Vui lòng chọn\nkhoảng thời gian khác", new Color(108, 117, 125)));
            empStatsContainer.revalidate();
            empStatsContainer.repaint();
            
            // Hiển thị thông báo trong biểu đồ phụ
            empSecondaryChartContainer.removeAll();
            JLabel noDataSecondaryLabel = new JLabel("<html><center>  Không có dữ liệu biểu đồ phụ<br/>trong khoảng thời gian đã chọn</center></html>");
            noDataSecondaryLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
            noDataSecondaryLabel.setForeground(new Color(108, 117, 125));
            noDataSecondaryLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noDataSecondaryLabel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
            empSecondaryChartContainer.add(noDataSecondaryLabel, BorderLayout.CENTER);
            empSecondaryChartContainer.revalidate();
            empSecondaryChartContainer.repaint();
            
            // Hiển thị thông báo trong bảng
            empTableContainer.removeAll();
            JLabel noDataTableLabel = new JLabel("<html><center> Không có dữ liệu bảng<br/>trong khoảng thời gian đã chọn</center></html>");
            noDataTableLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
            noDataTableLabel.setForeground(new Color(108, 117, 125));
            noDataTableLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noDataTableLabel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
            empTableContainer.add(noDataTableLabel, BorderLayout.CENTER);
            empTableContainer.revalidate();
            empTableContainer.repaint();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Sửa lại method getSelectedEmpRange() để xử lý khoảng thời gian tùy ý tốt hơn (dòng 4050-4100):
  
    /**
     * Lấy khoảng thời gian so sánh dựa trên khoảng thời gian hiện tại
     */
    private TimeRange getComparisonTimeRange(TimeRange currentRange) {
        try {
            if (currentRange == null) return null;
            
        Calendar cal = Calendar.getInstance();
            java.util.Date from, to;
            
            // Tính toán khoảng thời gian so sánh dựa trên khoảng thời gian hiện tại
            long currentDays = (currentRange.getTo().getTime() - currentRange.getFrom().getTime()) / (24 * 60 * 60 * 1000) + 1;
            
            // Nếu khoảng thời gian hiện tại là 1 tháng, so sánh với tháng trước
            if (currentDays >= 28 && currentDays <= 31) {
                cal.setTime(currentRange.getFrom());
                cal.add(Calendar.MONTH, -1);
                from = cal.getTime();
                cal.setTime(currentRange.getTo());
                cal.add(Calendar.MONTH, -1);
                to = cal.getTime();
            }
            // Nếu khoảng thời gian hiện tại là 1 tuần, so sánh với tuần trước
            else if (currentDays >= 7 && currentDays <= 8) {
                cal.setTime(currentRange.getFrom());
                cal.add(Calendar.WEEK_OF_YEAR, -1);
                from = cal.getTime();
                cal.setTime(currentRange.getTo());
                cal.add(Calendar.WEEK_OF_YEAR, -1);
                to = cal.getTime();
            }
            // Nếu khoảng thời gian hiện tại là 1 năm, so sánh với năm trước
            else if (currentDays >= 365 && currentDays <= 366) {
                cal.setTime(currentRange.getFrom());
                cal.add(Calendar.YEAR, -1);
                from = cal.getTime();
                cal.setTime(currentRange.getTo());
                cal.add(Calendar.YEAR, -1);
                to = cal.getTime();
            }
            // Mặc định so sánh với khoảng thời gian tương tự trước đó
            else {
                cal.setTime(currentRange.getFrom());
                cal.add(Calendar.DAY_OF_MONTH, (int) -currentDays);
                from = cal.getTime();
                cal.setTime(currentRange.getTo());
                cal.add(Calendar.DAY_OF_MONTH, (int) -currentDays);
                to = cal.getTime();
            }
            
            return new TimeRange(normalizeStartOfDay(from), normalizeEndOfDay(to));
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy label hiển thị cho khoảng thời gian so sánh
     */
    private String getComparisonLabel(TimeRange currentRange) {
        try {
            if (currentRange == null) return "So sánh";
            
            long currentDays = (currentRange.getTo().getTime() - currentRange.getFrom().getTime()) / (24 * 60 * 60 * 1000) + 1;
            
            if (currentDays >= 28 && currentDays <= 31) {
                return "Tháng trước";
            } else if (currentDays >= 7 && currentDays <= 8) {
                return "Tuần trước";
            } else if (currentDays >= 365 && currentDays <= 366) {
                return "Năm trước";
            } else {
                return "Kỳ trước";
            }
        } catch (Exception ex) {
            return "So sánh";
        }
    }

    /**
     * Tạo biểu đồ so sánh doanh thu nhân viên giữa 2 khoảng thời gian
     */
    private void createComparisonEmployeeChart(List<Map.Entry<String, Double>> currentRevenue, 
                                             Map<String, String> userIdToName, 
                                             TimeRange currentRange,
                                             Map<String, Double> comparisonRevenue,
                                             String comparisonLabel) {
        try {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            
            // Tạo dataset với 2 series: hiện tại và so sánh
            for (Map.Entry<String, Double> entry : currentRevenue) {
                String userId = entry.getKey();
                String displayName = userIdToName.getOrDefault(userId, userId);
                double currentValue = entry.getValue();
                double comparisonValue = comparisonRevenue.getOrDefault(userId, 0.0);
                
                // Thêm dữ liệu hiện tại
                dataset.addValue(currentValue, "Kỳ hiện tại", displayName);
                
                // Thêm dữ liệu so sánh
                dataset.addValue(comparisonValue, comparisonLabel, displayName);
            }
            
            // Thêm dữ liệu so sánh cho các nhân viên không có trong kỳ hiện tại
            for (String userId : comparisonRevenue.keySet()) {
                if (currentRevenue.stream().noneMatch(e -> e.getKey().equals(userId))) {
                    String displayName = userIdToName.getOrDefault(userId, userId);
                    double comparisonValue = comparisonRevenue.get(userId);
                    dataset.addValue(0.0, "Kỳ hiện tại", displayName);
                    dataset.addValue(comparisonValue, comparisonLabel, displayName);
                }
            }

            String title = "So sánh doanh thu theo nhân viên (" + getRangeLabel(currentRange) + " vs " + comparisonLabel + ")";
            JFreeChart chart = XChart.createBarChart(title, "Nhân viên", "VNĐ", dataset);
            
            CategoryPlot plot = chart.getCategoryPlot();
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            
            // Tự động scale trục Y theo max data
            double maxValue = Math.max(
                currentRevenue.stream().mapToDouble(e -> e.getValue() != null ? e.getValue() : 0.0).max().orElse(0.0),
                comparisonRevenue.values().stream().mapToDouble(Double::doubleValue).max().orElse(0.0)
            );
            
            if (maxValue > 0) {
                double upperBound = maxValue * 1.2;
                plot.getRangeAxis().setUpperBound(upperBound);
                plot.getRangeAxis().setLowerBound(0.0);
            }
            
            // Căn giữa các cột
            org.jfree.chart.axis.CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setCategoryMargin(0.2);
            plot.setDomainGridlinesVisible(true);
            
            // Màu sắc cho 2 series
            renderer.setSeriesPaint(0, new Color(134, 39, 43)); // Đỏ đậm cho kỳ hiện tại
            renderer.setSeriesPaint(1, new Color(52, 144, 220)); // Xanh dương cho kỳ so sánh
            
            // Căn chỉnh cột
            renderer.setItemMargin(0.1);
            renderer.setMaximumBarWidth(0.8);
            
            // Hiển thị label trên cột
            // Sử dụng constructor hợp lệ của StandardCategoryItemLabelGenerator vì constructor với pattern, locale, decimalformat không tồn tại
            // Chỉ cần truyền vào DecimalFormat cho giá trị số, không cần pattern và locale
            // Dùng DecimalFormat("#,##0") để hiển thị số nguyên với dấu phẩy ngăn cách hàng nghìn
            renderer.setDefaultItemLabelGenerator(
                new StandardCategoryItemLabelGenerator(
                    "{2}", // Pattern để hiển thị giá trị - KHÔNG ĐƯỢC NULL
                    new java.text.DecimalFormat("#,##0") // Format làm tròn về số nguyên, dấu phẩy ngăn cách hàng nghìn
                )
            );
            renderer.setDefaultItemLabelsVisible(true);
            renderer.setDefaultItemLabelFont(new Font("Tahoma", Font.BOLD, 10));
            renderer.setDefaultItemLabelPaint(Color.BLACK);
            
            // Vị trí label
            renderer.setDefaultPositiveItemLabelPosition(
                new org.jfree.chart.labels.ItemLabelPosition(
                    org.jfree.chart.labels.ItemLabelAnchor.OUTSIDE12,
                    org.jfree.chart.ui.TextAnchor.BOTTOM_CENTER,
                    org.jfree.chart.ui.TextAnchor.BOTTOM_CENTER,
                    0.0
                )
            );
            
            // Tùy chỉnh màu nền
            plot.setBackgroundPaint(Color.WHITE);
            plot.setDomainGridlinePaint(new Color(200, 200, 200));
            plot.setRangeGridlinePaint(new Color(200, 200, 200));
            
            // Tăng margin để số không bị cắt
            plot.setAxisOffset(new org.jfree.chart.ui.RectangleInsets(40, 20, 20, 20));
            
            // Tùy chỉnh legend
            chart.getLegend().setPosition(org.jfree.chart.ui.RectangleEdge.BOTTOM);
            chart.getLegend().setBackgroundPaint(Color.WHITE);
            chart.getLegend().setBorder(1.0, 1.0, 1.0, 1.0);
            chart.getLegend().setItemFont(new Font("Tahoma", Font.PLAIN, 11));
            
            ChartPanel panel = XChart.createChartPanel(chart);
            empChartContainer.removeAll();
            empChartContainer.add(panel, BorderLayout.CENTER);
            empChartContainer.revalidate();
            empChartContainer.repaint();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Thêm method mới để điều khiển hiển thị các thành phần năng suất (dòng 4100-4150):
    /**
     * Điều khiển hiển thị các thành phần năng suất dựa trên checkbox
     */
    private void toggleProductivityDisplay() {
        try {
            if (chkShowProductivity != null && chkShowProductivity.isSelected()) {
                // Hiển thị các thành phần năng suất
                if (empSecondaryChartContainer != null) {
                    empSecondaryChartContainer.setVisible(true);
                }
            } else {
                // Ẩn các thành phần năng suất
                if (empSecondaryChartContainer != null) {
                    empSecondaryChartContainer.setVisible(false);
                }
            }
            
            // Cập nhật layout
            if (empSecondaryChartContainer != null) {
                empSecondaryChartContainer.getParent().revalidate();
                empSecondaryChartContainer.getParent().repaint();
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


