package com.team4.quanliquanmicay.View;

import com.team4.quanliquanmicay.util.XTheme;
import com.team4.quanliquanmicay.util.XDialog;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author HP
 */
public class MainUI extends javax.swing.JFrame {

    // Thêm các biến để lưu trữ các UI con
    private com.team4.quanliquanmicay.View.management.CategoryManagement categoryManagementUI;
    private com.team4.quanliquanmicay.View.management.ProductManagement productManagementUI;
    private com.team4.quanliquanmicay.View.management.BillManagement billManagementUI;
    private com.team4.quanliquanmicay.View.management.PaymentMethodManagement paymentMethodManagementUI;
    private com.team4.quanliquanmicay.View.management.HistoryManagement historyManagementUI;
    private com.team4.quanliquanmicay.View.management.TableManagement tableManagementUI;
    private com.team4.quanliquanmicay.View.management.UserManagement userManagementUI;
    private com.team4.quanliquanmicay.View.management.CustomerManagement customerManagementUI;
    private com.team4.quanliquanmicay.View.management.ReportManagement reportManagementUI;

    private com.team4.quanliquanmicay.View.ChooseTableUI chooseTableUI;
    private com.team4.quanliquanmicay.View.PayUI payUI;
    private com.team4.quanliquanmicay.View.ChangePassword changePasswordUI;

    /**
     * Creates new form MainForm
     */
    public MainUI() {
        this.setUndecorated(true); // Ẩn thanh tiêu đề
        XTheme.applyFullTheme(); // Thay đổi từ applyLightTheme()
        initComponents();
        this.setLocationRelativeTo(null);
        
        setupCategoryManagementPopup();
        setupBillManagementPopup();
        setupDateTimeDisplay();
        removeAllIcons();
        
        SwingUtilities.invokeLater(() -> {
            String themePath = "/icons_and_images/icon/Theme.jpg";
            if (getClass().getResource(themePath) != null) {
                com.team4.quanliquanmicay.util.XImage.setImageToLabel(lblTheme, themePath);
            }
        });
    }
    
    /**
     * Hàm tạo popup cho Quản Lý Danh Mục
     */
    private void setupCategoryManagementPopup() {
        JPopupMenu popupQuanLyDanhMuc = new JPopupMenu();
        JMenuItem itemLoaiMon = new JMenuItem("Loại món ăn");
        JMenuItem itemSanPham = new JMenuItem("Sản phẩm");

        // CSS cho popup menu
        popupQuanLyDanhMuc.setBackground(new java.awt.Color(134, 39, 43));
        popupQuanLyDanhMuc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        // CSS cho các menu item
        itemLoaiMon.setBackground(new java.awt.Color(134, 39, 43));
        itemLoaiMon.setForeground(new java.awt.Color(255, 255, 255));
        itemLoaiMon.setFont(new java.awt.Font("Segoe UI", 1, 12));
        itemLoaiMon.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 15, 8, 15));
        itemLoaiMon.setToolTipText("Quản lý các loại món ăn trong hệ thống");

        itemSanPham.setBackground(new java.awt.Color(134, 39, 43));
        itemSanPham.setForeground(new java.awt.Color(255, 255, 255));
        itemSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12));
        itemSanPham.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 15, 8, 15));
        itemSanPham.setToolTipText("Quản lý các sản phẩm/món ăn trong hệ thống");

        // Thêm hover effect
        itemLoaiMon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                itemLoaiMon.setBackground(new java.awt.Color(180, 50, 50));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                itemLoaiMon.setBackground(new java.awt.Color(134, 39, 43));
            }
        });

        itemSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                itemSanPham.setBackground(new java.awt.Color(180, 50, 50)); 
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                itemSanPham.setBackground(new java.awt.Color(134, 39, 43));
            }
        });

        popupQuanLyDanhMuc.add(itemLoaiMon);
        popupQuanLyDanhMuc.add(itemSanPham);
        
        // Thêm ActionListener cho menu item "Loại món ăn"
        itemLoaiMon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("🔄 Đang mở CategoryManagement...");
                    if (categoryManagementUI == null || !categoryManagementUI.isVisible()) {
                        categoryManagementUI = new com.team4.quanliquanmicay.View.management.CategoryManagement();
                        categoryManagementUI.setVisible(true);
                    } else {
                        categoryManagementUI.toFront();
                        categoryManagementUI.requestFocus();
                    }
                    System.out.println("✅ CategoryManagement đã được mở thành công!");
                } catch (Exception ex) {
                    System.err.println("❌ Lỗi khi mở CategoryManagement: " + ex.getMessage());
                    XDialog.error("Lỗi khi mở Quản lý Loại món: " + ex.getMessage(), "Lỗi hệ thống");
                }
            }
        });
        
        // Thêm ActionListener cho menu item "Sản phẩm"
        itemSanPham.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("🔄 Đang mở ProductManagement...");
                    if (productManagementUI == null || !productManagementUI.isVisible()) {
                        productManagementUI = new com.team4.quanliquanmicay.View.management.ProductManagement();
                        productManagementUI.setVisible(true);
                    } else {
                        productManagementUI.toFront();
                        productManagementUI.requestFocus();
                    }
                    System.out.println("✅ CategoryManagement đã được mở thành công!");
                } catch (Exception ex) {
                    System.err.println("❌ Lỗi khi mở ProductManagement: " + ex.getMessage());
                    XDialog.error("Lỗi khi mở Quản lý Sản phẩm: " + ex.getMessage(), "Lỗi hệ thống");
                }
            }
        });
        
        btnCategoryManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int width = btnCategoryManagement.getWidth();
                for (Component comp : popupQuanLyDanhMuc.getComponents()) {
                    if (comp instanceof JMenuItem) {
                        JMenuItem item = (JMenuItem) comp;
                        item.setPreferredSize(new Dimension(width, 25));
                    }
                }
                popupQuanLyDanhMuc.show(btnCategoryManagement, 0, btnCategoryManagement.getHeight());
            }
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCategoryManagement.setToolTipText("Click để mở menu quản lý danh mục");
            }
        });
    }

    private void setupBillManagementPopup() {
        JPopupMenu popupQuanLyHoaDon = new JPopupMenu();
        JMenuItem itemHoaDon = new JMenuItem("Hóa đơn");
        JMenuItem itemPhuongThucThanhToan = new JMenuItem("Phương thức thanh toán");
        JMenuItem itemLichSuThanhToan = new JMenuItem("Lịch sử");

        // CSS cho popup menu
        popupQuanLyHoaDon.setBackground(new java.awt.Color(134, 39, 43));
        popupQuanLyHoaDon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        // CSS cho các menu item
        itemHoaDon.setBackground(new java.awt.Color(134, 39, 43));
        itemHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        itemHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12));
        itemHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 15, 8, 15));
        itemHoaDon.setToolTipText("Quản lý hóa đơn và chi tiết hóa đơn");

        itemPhuongThucThanhToan.setBackground(new java.awt.Color(134, 39, 43));
        itemPhuongThucThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        itemPhuongThucThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12));
        itemPhuongThucThanhToan.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 15, 8, 15));
        itemPhuongThucThanhToan.setToolTipText("Quản lý các phương thức thanh toán");

        itemLichSuThanhToan.setBackground(new java.awt.Color(134, 39, 43));
        itemLichSuThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        itemLichSuThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12));
        itemLichSuThanhToan.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 15, 8, 15));
        itemLichSuThanhToan.setToolTipText("Xem lịch sử thanh toán và hóa đơn");

        // Thêm hover effect
        itemHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                itemHoaDon.setBackground(new java.awt.Color(180, 50, 50));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                itemHoaDon.setBackground(new java.awt.Color(134, 39, 43));
            }
        });

        itemPhuongThucThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                itemPhuongThucThanhToan.setBackground(new java.awt.Color(180, 50, 50));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                itemPhuongThucThanhToan.setBackground(new java.awt.Color(134, 39, 43));
            }
        });

        itemLichSuThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                itemLichSuThanhToan.setBackground(new java.awt.Color(180, 50, 50));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                itemLichSuThanhToan.setBackground(new java.awt.Color(134, 39, 43));
            }
        });

        popupQuanLyHoaDon.add(itemHoaDon);
        popupQuanLyHoaDon.add(itemPhuongThucThanhToan);
        popupQuanLyHoaDon.add(itemLichSuThanhToan);
        
        // Thêm ActionListener cho menu item "Hóa đơn"
        itemHoaDon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("🔄 Đang mở BillManagement...");
                    if (billManagementUI == null || !billManagementUI.isVisible()) {
                        billManagementUI = new com.team4.quanliquanmicay.View.management.BillManagement();
                        billManagementUI.setVisible(true);
                    } else {
                        billManagementUI.toFront();
                        billManagementUI.requestFocus();
                    }
                    System.out.println("✅ BillManagement đã được mở thành công!");
                } catch (Exception ex) {
                    System.err.println("❌ Lỗi khi mở BillManagement: " + ex.getMessage());
                    XDialog.error("Lỗi khi mở Quản lý Hóa đơn: " + ex.getMessage(), "Lỗi hệ thống");
                }
            }
        });
        
        // Thêm ActionListener cho menu item "Phương thức thanh toán"
        itemPhuongThucThanhToan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("🔄 Đang mở PaymentMethodManagement...");
                    if (paymentMethodManagementUI == null || !paymentMethodManagementUI.isVisible()) {
                        paymentMethodManagementUI = new com.team4.quanliquanmicay.View.management.PaymentMethodManagement();
                        paymentMethodManagementUI.setVisible(true);
                    } else {
                        paymentMethodManagementUI.toFront();
                        paymentMethodManagementUI.requestFocus();
                    }
                    System.out.println("✅ PaymentMethodManagement đã được mở thành công!");
                } catch (Exception ex) {
                    System.err.println("❌ Lỗi khi mở PaymentMethodManagement: " + ex.getMessage());
                    XDialog.error("Lỗi khi mở Quản lý Phương thức thanh toán: " + ex.getMessage(), "Lỗi hệ thống");
                }
            }
        });
        
        // Thêm ActionListener cho menu item "Lịch sử"
        itemLichSuThanhToan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("🔄 Đang mở HistoryManagement...");
                    if (historyManagementUI == null || !historyManagementUI.isVisible()) {
                        historyManagementUI = new com.team4.quanliquanmicay.View.management.HistoryManagement();
                        historyManagementUI.setVisible(true);
                    } else {
                        historyManagementUI.toFront();
                        historyManagementUI.requestFocus();
                    }
                    System.out.println("✅ HistoryManagement đã được mở thành công!");
                } catch (Exception ex) {
                    System.err.println("❌ Lỗi khi mở HistoryManagement: " + ex.getMessage());
                    XDialog.error("Lỗi khi mở Quản lý Lịch sử: " + ex.getMessage(), "Lỗi hệ thống");
                }
            }
        });
        
        btnBillManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int width = btnBillManagement.getWidth();
                for (Component comp : popupQuanLyHoaDon.getComponents()) {
                    if (comp instanceof JMenuItem) {
                        JMenuItem item = (JMenuItem) comp;
                        item.setPreferredSize(new Dimension(width, 25));
                    }
                }
                popupQuanLyHoaDon.show(btnBillManagement, 0, btnBillManagement.getHeight());
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBillManagement.setToolTipText("Click để mở menu quản lý hóa đơn");
            }
        });
    }
    
    /**
     * Hàm thiết lập hiển thị ngày giờ
     */
    private void setupDateTimeDisplay() {
        // Cập nhật thời gian ngay lập tức
        updateDateTime();
        
        // Tạo timer để cập nhật thời gian mỗi giây
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDateTime();
            }
        });
        timer.start();
    }
    
    /**
     * Hàm cập nhật ngày giờ
     */
    private void updateDateTime() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        
        txtDay.setText(dateFormat.format(now));
        txtTime.setText(timeFormat.format(now));
    }
    
    /**
     * Loại bỏ toàn bộ icon trên UI để đồng nhất thiết kế theo yêu cầu
     */
    private void removeAllIcons() {
        try {
            // Label và Button chính
            lblLogo.setIcon(null);
            btnReport.setIcon(null);
            btnChangePassWord.setIcon(null);
            btnCategoryManagement.setIcon(null);
            btnBillManagement.setIcon(null);
            btnTableManagement.setIcon(null);
            btnUserManagement.setIcon(null);
            btnCustomerManagement.setIcon(null);
            btnChooseTable.setIcon(null);
            btnPayUi.setIcon(null);
            btnHelp.setIcon(null);

            // Các label hiển thị ngày/giờ
            jLabel2.setIcon(null);
            jLabel3.setIcon(null);
        } catch (Exception ignore) {
            // Bỏ qua nếu có control chưa được khởi tạo
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

        pnlTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        pnlMainTitle = new javax.swing.JPanel();
        pnSlogan = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        pnlFeature = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        btnReport = new javax.swing.JButton();
        btnChangePassWord = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDay = new javax.swing.JTextField();
        txtTime = new javax.swing.JTextField();
        pnlAdminFeature = new javax.swing.JPanel();
        btnCategoryManagement = new javax.swing.JButton();
        btnBillManagement = new javax.swing.JButton();
        btnTableManagement = new javax.swing.JButton();
        btnUserManagement = new javax.swing.JButton();
        btnCustomerManagement = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnChooseTable = new javax.swing.JButton();
        btnPayUi = new javax.swing.JButton();
        pnlLogo = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        btnHelp = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblTheme = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        pnlTitle.setBackground(new java.awt.Color(134, 39, 43));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("HỆ THỐNG QUẢN LÍ MỲ CAY  FIVE C");

        pnlMainTitle.setBackground(new java.awt.Color(204, 164, 133));

        pnSlogan.setBackground(new java.awt.Color(255, 255, 255));
        pnSlogan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(134, 39, 43));
        jLabel6.setText("“Hệ thống thông minh, vận hành mượt – phục vụ khách chuẩn từng giây!”");

        javax.swing.GroupLayout pnSloganLayout = new javax.swing.GroupLayout(pnSlogan);
        pnSlogan.setLayout(pnSloganLayout);
        pnSloganLayout.setHorizontalGroup(
            pnSloganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSloganLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addContainerGap())
        );
        pnSloganLayout.setVerticalGroup(
            pnSloganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSloganLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlFeature.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnReport.setBackground(new java.awt.Color(248, 248, 248));
        btnReport.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnReport.setForeground(new java.awt.Color(134, 39, 43));
        btnReport.setText("Thống kê");
        btnReport.setToolTipText("");
        btnReport.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });

        btnChangePassWord.setBackground(new java.awt.Color(248, 248, 248));
        btnChangePassWord.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChangePassWord.setForeground(new java.awt.Color(134, 39, 43));
        btnChangePassWord.setText("Đổi mật khẩu");
        btnChangePassWord.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btnChangePassWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePassWordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnChangePassWord, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChangePassWord, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0), 2));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(134, 39, 43));
        jLabel2.setText("Ngày :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(134, 39, 43));
        jLabel3.setText("Giờ :");

        txtDay.setEditable(false);
        txtDay.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtTime.setEditable(false);
        txtTime.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDay, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlFeatureLayout = new javax.swing.GroupLayout(pnlFeature);
        pnlFeature.setLayout(pnlFeatureLayout);
        pnlFeatureLayout.setHorizontalGroup(
            pnlFeatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFeatureLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlFeatureLayout.setVerticalGroup(
            pnlFeatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFeatureLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlFeatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlAdminFeature.setBackground(new java.awt.Color(191, 139, 98));
        pnlAdminFeature.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        btnCategoryManagement.setBackground(new java.awt.Color(134, 39, 43));
        btnCategoryManagement.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCategoryManagement.setForeground(new java.awt.Color(255, 255, 255));
        btnCategoryManagement.setText("Quản Lí Thực Phẩm");
        btnCategoryManagement.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnCategoryManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCategoryManagementMouseEntered(evt);
            }
        });
        btnCategoryManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoryManagementActionPerformed(evt);
            }
        });

        btnBillManagement.setBackground(new java.awt.Color(134, 39, 43));
        btnBillManagement.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBillManagement.setForeground(new java.awt.Color(255, 255, 255));
        btnBillManagement.setText("Quản Lí Hóa Đơn");
        btnBillManagement.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnBillManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBillManagementMouseEntered(evt);
            }
        });

        btnTableManagement.setBackground(new java.awt.Color(134, 39, 43));
        btnTableManagement.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnTableManagement.setForeground(new java.awt.Color(255, 255, 255));
        btnTableManagement.setText("Quản Lí Bàn");
        btnTableManagement.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnTableManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTableManagementMouseEntered(evt);
            }
        });
        btnTableManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTableManagementActionPerformed(evt);
            }
        });

        btnUserManagement.setBackground(new java.awt.Color(134, 39, 43));
        btnUserManagement.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnUserManagement.setForeground(new java.awt.Color(255, 255, 255));
        btnUserManagement.setText("Quản Lí Người Dùng");
        btnUserManagement.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnUserManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnUserManagementMouseEntered(evt);
            }
        });
        btnUserManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserManagementActionPerformed(evt);
            }
        });

        btnCustomerManagement.setBackground(new java.awt.Color(134, 39, 43));
        btnCustomerManagement.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCustomerManagement.setForeground(new java.awt.Color(255, 255, 255));
        btnCustomerManagement.setText("Quản Lí Khách Hàng");
        btnCustomerManagement.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnCustomerManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCustomerManagementMouseEntered(evt);
            }
        });
        btnCustomerManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerManagementActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAdminFeatureLayout = new javax.swing.GroupLayout(pnlAdminFeature);
        pnlAdminFeature.setLayout(pnlAdminFeatureLayout);
        pnlAdminFeatureLayout.setHorizontalGroup(
            pnlAdminFeatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdminFeatureLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCategoryManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBillManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTableManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCustomerManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAdminFeatureLayout.setVerticalGroup(
            pnlAdminFeatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdminFeatureLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAdminFeatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCategoryManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBillManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTableManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUserManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomerManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(191, 139, 98));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        btnChooseTable.setBackground(new java.awt.Color(204, 204, 204));
        btnChooseTable.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChooseTable.setForeground(new java.awt.Color(122, 82, 0));
        btnChooseTable.setText("CHỌN BÀN");
        btnChooseTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        btnChooseTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseTableActionPerformed(evt);
            }
        });

        btnPayUi.setBackground(new java.awt.Color(204, 204, 204));
        btnPayUi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPayUi.setForeground(new java.awt.Color(30, 122, 30));
        btnPayUi.setText("THANH TOÁN");
        btnPayUi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        btnPayUi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayUiActionPerformed(evt);
            }
        });

        pnlLogo.setBackground(new java.awt.Color(255, 255, 255));
        pnlLogo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        lblLogo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(134, 39, 43));
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/icon/icons8-five-64.png"))); // NOI18N
        lblLogo.setText("FIVE C ");

        javax.swing.GroupLayout pnlLogoLayout = new javax.swing.GroupLayout(pnlLogo);
        pnlLogo.setLayout(pnlLogoLayout);
        pnlLogoLayout.setHorizontalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLogoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblLogo)
                .addGap(121, 121, 121))
        );
        pnlLogoLayout.setVerticalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnHelp.setBackground(new java.awt.Color(204, 204, 204));
        btnHelp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHelp.setForeground(new java.awt.Color(102, 102, 102));
        btnHelp.setText("Hỗ trợ");
        btnHelp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        btnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHelpActionPerformed(evt);
            }
        });

        btnExit.setBackground(new java.awt.Color(119, 50, 5));
        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnExit.setForeground(new java.awt.Color(204, 204, 204));
        btnExit.setText("Thoát ");
        btnExit.setToolTipText("");
        btnExit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnPayUi, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnChooseTable, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(100, 100, 100))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87)
                .addComponent(btnChooseTable, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPayUi, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlMainTitleLayout = new javax.swing.GroupLayout(pnlMainTitle);
        pnlMainTitle.setLayout(pnlMainTitleLayout);
        pnlMainTitleLayout.setHorizontalGroup(
            pnlMainTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMainTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainTitleLayout.createSequentialGroup()
                        .addComponent(pnSlogan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlFeature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlMainTitleLayout.createSequentialGroup()
                        .addGroup(pnlMainTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlAdminFeature, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTheme, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMainTitleLayout.setVerticalGroup(
            pnlMainTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMainTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlFeature, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnSlogan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlMainTitleLayout.createSequentialGroup()
                        .addComponent(pnlAdminFeature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTheme, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlTitleLayout = new javax.swing.GroupLayout(pnlTitle);
        pnlTitle.setLayout(pnlTitleLayout);
        pnlTitleLayout.setHorizontalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTitleLayout.createSequentialGroup()
                .addGap(442, 442, 442)
                .addComponent(lblTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pnlMainTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlTitleLayout.setVerticalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTitleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlMainTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPayUiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayUiActionPerformed
        try {
            if (payUI == null || !payUI.isVisible()) {
                payUI = new com.team4.quanliquanmicay.View.PayUI();
                payUI.setVisible(true);
            } else {
                payUI.toFront();
                payUI.requestFocus();
            }
        } catch (Exception ex) {
            System.err.println("❌ Lỗi khi mở PayUI: " + ex.getMessage());
            XDialog.error("Lỗi khi mở Thanh toán: " + ex.getMessage(), "Lỗi hệ thống");
        }
    }//GEN-LAST:event_btnPayUiActionPerformed

    private void btnCategoryManagementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCategoryManagementMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCategoryManagementMouseEntered

    private void btnBillManagementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBillManagementMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBillManagementMouseEntered

    private void btnTableManagementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTableManagementMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTableManagementMouseEntered

    private void btnUserManagementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserManagementMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserManagementMouseEntered

    private void btnCustomerManagementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerManagementMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCustomerManagementMouseEntered

    private void btnTableManagementActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (tableManagementUI == null || !tableManagementUI.isVisible()) {
                tableManagementUI = new com.team4.quanliquanmicay.View.management.TableManagement();
                tableManagementUI.setVisible(true);
            } else {
                tableManagementUI.toFront();
                tableManagementUI.requestFocus();
            }
        } catch (Exception ex) {
            System.err.println("❌ Lỗi khi mở TableManagement: " + ex.getMessage());
            XDialog.error("Lỗi khi mở Quản lý Bàn: " + ex.getMessage(), "Lỗi hệ thống");
        }
    }

    private void btnUserManagementActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (userManagementUI == null || !userManagementUI.isVisible()) {
                userManagementUI = new com.team4.quanliquanmicay.View.management.UserManagement();
                userManagementUI.setVisible(true);
            } else {
                userManagementUI.toFront();
                userManagementUI.requestFocus();
            }
        } catch (Exception ex) {
            System.err.println("❌ Lỗi khi mở UserManagement: " + ex.getMessage());
            XDialog.error("Lỗi khi mở Quản lý Người dùng: " + ex.getMessage(), "Lỗi hệ thống");
        }
    }

    private void btnCustomerManagementActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (customerManagementUI == null || !customerManagementUI.isVisible()) {
                customerManagementUI = new com.team4.quanliquanmicay.View.management.CustomerManagement();
                customerManagementUI.setVisible(true);
            } else {
                customerManagementUI.toFront();
                customerManagementUI.requestFocus();
            }
        } catch (Exception ex) {
            System.err.println("❌ Lỗi khi mở CustomerManagement: " + ex.getMessage());
            XDialog.error("Lỗi khi mở Quản lý Khách hàng: " + ex.getMessage(), "Lỗi hệ thống");
        }
    }

    private void btnChooseTableActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (chooseTableUI == null || !chooseTableUI.isVisible()) {
                chooseTableUI = new com.team4.quanliquanmicay.View.ChooseTableUI();
                chooseTableUI.setVisible(true);
            } else {
                chooseTableUI.toFront();
                chooseTableUI.requestFocus();
            }
        } catch (Exception ex) {
            System.err.println("❌ Lỗi khi mở ChooseTableUI: " + ex.getMessage());
            XDialog.error("Lỗi khi mở Chọn bàn: " + ex.getMessage(), "Lỗi hệ thống");
        }
    }



    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
         if (XDialog.confirm("Bạn có chắc chắn muốn thoát khỏi ứng dụng không?", "Xác nhận thoát")) 
          this.dispose(); // Đóng cửa sổ hiện tại
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHelpActionPerformed
        // TODO add your handling code here:
        String helpMessage = "=== THÔNG TIN LIÊN HỆ HỖ TRỢ PHẦN MỀM ===\n\n" +
                           "[KỸ THUẬT] Bộ phận Kỹ thuật & Hỗ trợ\n" +
                           "[CÔNG TY] Công ty TNHH FiveC\n" +
                           "[ĐỊA CHỈ] 123 Đường ABC, Quận XYZ, TP.HCM\n\n" +
                           "[ĐIỆN THOẠI] Hotline: 1900-1234\n" +
                           "[DI ĐỘNG] Di động: 0909-123-456\n" +
                           "[EMAIL] Email: support@fivec.com.vn\n" +
                           "[WEBSITE] Website: www.fivec.com.vn\n\n" +
                           "[GIỜ LÀM VIỆC]\n" +
                           "   • Thứ 2 - Thứ 6: 8:00 - 17:30\n" +
                           "   • Thứ 7: 8:00 - 12:00\n" +
                           "   • Chủ nhật: Nghỉ\n\n" +
                           "[HƯỚNG DẪN] Khi gặp sự cố, vui lòng cung cấp:\n" +
                           "   • Mô tả chi tiết lỗi\n" +
                           "   • Thời gian xảy ra lỗi\n" +
                           "   • Tên người dùng đang sử dụng\n" +
                           "   • Phiên bản phần mềm hiện tại";
        
        XDialog.alert(helpMessage, "Hỗ trợ kỹ thuật");
    }//GEN-LAST:event_btnHelpActionPerformed

    private void btnCategoryManagementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoryManagementActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCategoryManagementActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (changePasswordUI == null || !changePasswordUI.isVisible()) {
                changePasswordUI = new com.team4.quanliquanmicay.View.ChangePassword();
                changePasswordUI.setVisible(true);
            } else {
                changePasswordUI.toFront();
                changePasswordUI.requestFocus();
            }
        } catch (Exception ex) {
            System.err.println("❌ Lỗi khi mở ChangePassword: " + ex.getMessage());
            XDialog.error("Lỗi khi mở Đổi mật khẩu: " + ex.getMessage(), "Lỗi hệ thống");
        }
    }

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (reportManagementUI == null || !reportManagementUI.isVisible()) {
                reportManagementUI = new com.team4.quanliquanmicay.View.management.ReportManagement();
                reportManagementUI.setVisible(true);
            } else {
                reportManagementUI.toFront();
                reportManagementUI.requestFocus();
            }
        } catch (Exception ex) {
            System.err.println("❌ Lỗi khi mở ReportManagement: " + ex.getMessage());
            XDialog.error("Lỗi khi mở Thống kê & Báo cáo: " + ex.getMessage(), "Lỗi hệ thống");
        }
    }
    
    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (reportManagementUI == null || !reportManagementUI.isVisible()) {
                reportManagementUI = new com.team4.quanliquanmicay.View.management.ReportManagement();
                reportManagementUI.setVisible(true);
            } else {
                reportManagementUI.toFront();
                reportManagementUI.requestFocus();
            }
        } catch (Exception ex) {
            System.err.println("❌ Lỗi khi mở ReportManagement: " + ex.getMessage());
            XDialog.error("Lỗi khi mở Thống kê & Báo cáo: " + ex.getMessage(), "Lỗi hệ thống");
        }
    }
    
    private void btnChangePassWordActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (changePasswordUI == null || !changePasswordUI.isVisible()) {
                changePasswordUI = new com.team4.quanliquanmicay.View.ChangePassword();
                changePasswordUI.setVisible(true);
            } else {
                changePasswordUI.toFront();
                changePasswordUI.requestFocus();
            }
        } catch (Exception ex) {
            System.err.println("❌ Lỗi khi mở ChangePassword: " + ex.getMessage());
            XDialog.error("Lỗi khi mở Đổi mật khẩu: " + ex.getMessage(), "Lỗi hệ thống");
        }
    }
    
    /**
     * Cập nhật giao diện dựa trên role của người dùng
     * @param roleId ADMIN hoặc STAFF
     */
    /**
     * Cập nhật giao diện dựa trên role của người dùng
     * @param roleId R001 (Admin) hoặc R002 (Staff)
     */
    public void updateUIByRole(String roleId) {
        boolean isAdmin = "R001".equals(roleId);
        
        // Ẩn/hiện panel chức năng admin
        pnlAdminFeature.setVisible(isAdmin);
        
        // Cập nhật lại layout để điều chỉnh khoảng trống
        pnlMainTitle.revalidate();
        pnlMainTitle.repaint();
        
        // Nếu là admin, khôi phục màu sắc gốc cho các nút
        if (isAdmin) {
            java.awt.Color adminColor = new java.awt.Color(134, 39, 43);
            btnCategoryManagement.setBackground(adminColor);
            btnBillManagement.setBackground(adminColor);
            btnTableManagement.setBackground(adminColor);
            btnUserManagement.setBackground(adminColor);
            btnCustomerManagement.setBackground(adminColor);
        }
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
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBillManagement;
    private javax.swing.JButton btnCategoryManagement;
    private javax.swing.JButton btnChangePassWord;
    private javax.swing.JButton btnChooseTable;
    private javax.swing.JButton btnCustomerManagement;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHelp;
    private javax.swing.JButton btnPayUi;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnTableManagement;
    private javax.swing.JButton btnUserManagement;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblTheme;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnSlogan;
    private javax.swing.JPanel pnlAdminFeature;
    private javax.swing.JPanel pnlFeature;
    private javax.swing.JPanel pnlLogo;
    private javax.swing.JPanel pnlMainTitle;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JTextField txtDay;
    private javax.swing.JTextField txtTime;
    // End of variables declaration//GEN-END:variables
}
