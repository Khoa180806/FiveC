package com.team4.quanliquanmicay.View;

import com.team4.quanliquanmicay.util.XTheme;
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
        
        // Thêm event listener cho các button
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        
        // Thêm ActionListener cho các button management
        btnCategoryManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoryManagementActionPerformed(evt);
            }
        });
        
        btnBillManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBillManagementActionPerformed(evt);
            }
        });
        
        btnTableManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTableManagementActionPerformed(evt);
            }
        });
        
        btnUserManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserManagementActionPerformed(evt);
            }
        });
        
        btnCustomerManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerManagementActionPerformed(evt);
            }
        });
        
        // Thêm ActionListener cho jButton13 (Thống kê) và jButton14 (Đổi mật khẩu)
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
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
        itemLoaiMon.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        itemSanPham.setBackground(new java.awt.Color(134, 39, 43));
        itemSanPham.setForeground(new java.awt.Color(255, 255, 255));
        itemSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12));
        itemSanPham.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

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
                new com.team4.quanliquanmicay.View.management.CategoryManagement().setVisible(true);
            }
        });
        
        // Thêm ActionListener cho menu item "Sản phẩm"
        itemSanPham.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new com.team4.quanliquanmicay.View.management.ProductManagement().setVisible(true);
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
        itemHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        itemPhuongThucThanhToan.setBackground(new java.awt.Color(134, 39, 43));
        itemPhuongThucThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        itemPhuongThucThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12));
        itemPhuongThucThanhToan.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        itemLichSuThanhToan.setBackground(new java.awt.Color(134, 39, 43));
        itemLichSuThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        itemLichSuThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12));
        itemLichSuThanhToan.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

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
                new com.team4.quanliquanmicay.View.management.BillManagement().setVisible(true);
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDay = new javax.swing.JTextField();
        txtTime = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnCategoryManagement = new javax.swing.JButton();
        btnBillManagement = new javax.swing.JButton();
        btnTableManagement = new javax.swing.JButton();
        btnUserManagement = new javax.swing.JButton();
        btnCustomerManagement = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(134, 39, 43));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("HỆ THỐNG QUẢN LÍ MỲ CAY  FIVE C");

        jPanel4.setBackground(new java.awt.Color(204, 164, 133));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(134, 39, 43));
        jLabel6.setText("“Hệ thống thông minh, vận hành mượt – phục vụ khách chuẩn từng giây!”");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jButton13.setBackground(new java.awt.Color(248, 248, 248));
        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton13.setForeground(new java.awt.Color(134, 39, 43));
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Statistics.png"))); // NOI18N
        jButton13.setText("Thống kê");
        jButton13.setToolTipText("");
        jButton13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jButton14.setBackground(new java.awt.Color(248, 248, 248));
        jButton14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton14.setForeground(new java.awt.Color(134, 39, 43));
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Unlock.png"))); // NOI18N
        jButton14.setText("Đổi mật khẩu");
        jButton14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0), 2));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(134, 39, 43));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Calendar.png"))); // NOI18N
        jLabel2.setText("Ngày :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(134, 39, 43));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Alarm.png"))); // NOI18N
        jLabel3.setText("Giờ :");

        txtDay.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

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

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(191, 139, 98));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        btnCategoryManagement.setBackground(new java.awt.Color(134, 39, 43));
        btnCategoryManagement.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCategoryManagement.setForeground(new java.awt.Color(255, 255, 255));
        btnCategoryManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Closed folder.png"))); // NOI18N
        btnCategoryManagement.setText("Quản Lí Thực Phẩm");
        btnCategoryManagement.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btnCategoryManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCategoryManagementMouseEntered(evt);
            }
        });

        btnBillManagement.setBackground(new java.awt.Color(134, 39, 43));
        btnBillManagement.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBillManagement.setForeground(new java.awt.Color(255, 255, 255));
        btnBillManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Closed folder.png"))); // NOI18N
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
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
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCategoryManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBillManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTableManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUserManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomerManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTextArea2.setText("                                                                                                                 FIVE C – Vị cay đậm chất, hương vị đậm đà!\n\n                                                        Chào mừng bạn đến với FIVE C, quán mì cay mang đậm phong cách Hàn Quốc, nơi mỗi tô mì là một trải nghiệm khó quên!\n\n\n                  Chúng tôi không chỉ mang đến món ăn ngon mà còn mang đến cảm xúc – từ mức cay nhẹ nhàng đến cay xé lưỡi, mỗi khách hàng đều có thể tìm thấy cấp độ dành riêng cho mình.\n\n Vì sao chọn FIVE C ?\n\n             🔥 Mì cay 7 cấp độ – Thử thách vị giác, chinh phục đỉnh cay\n             🥩 Nguyên liệu tươi sạch – Đảm bảo vệ sinh, an toàn sức khỏe\n             👨‍ Không gian ấm cúng, hiện đại – Phù hợp nhóm bạn, gia đình, cặp đôi\n             📱 Phục vụ nhanh – Đặt bàn tiện lợi – Quản lý chuyên nghiệp\n\n THỰC ĐƠN ĐA DẠNG\n\n               Mì cay bò Mỹ, hải sản, kimchi, phô mai\n               Trà sữa, soda Hàn, nước ép trái cay \n                                                                                                                                   Thông điệp từ FIVE C\n\n                                                                                                                                 “Spicy code. Hotter bowl.”\n\n                                                                                              Mỗi ngày là một tô cay mới – bùng nổ vị giác, tiếp năng lượng cho tâm hồn ");
        jScrollPane2.setViewportView(jTextArea2);

        jPanel6.setBackground(new java.awt.Color(191, 139, 98));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        jButton7.setBackground(new java.awt.Color(255, 217, 102));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(122, 82, 0));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Address book.png"))); // NOI18N
        jButton7.setText("CHỌN BÀN");
        jButton7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        jButton9.setBackground(new java.awt.Color(167, 239, 163));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(30, 122, 30));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Price list.png"))); // NOI18N
        jButton9.setText("THANH TOÁN");
        jButton9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(134, 39, 43));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/icons8-five-64.png"))); // NOI18N
        jLabel5.setText("FIVE C ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jButton11.setBackground(new java.awt.Color(204, 204, 204));
        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(102, 102, 102));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Help.png"))); // NOI18N
        jButton11.setText("Hỗ trợ");
        jButton11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        jButton12.setBackground(new java.awt.Color(119, 50, 5));
        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton12.setForeground(new java.awt.Color(153, 0, 0));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Exit.png"))); // NOI18N
        jButton12.setToolTipText("");
        jButton12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(120, 120, 120))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(442, 442, 442)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

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

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // Mở ChooseTableUI
        new ChooseTableUI().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // Hiển thị dialog hỗ trợ
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Hệ thống quản lý nhà hàng mì cay\n" +
            "Phiên bản: 1.0\n" +
            "Liên hệ hỗ trợ: support@fivec.com", 
            "Hỗ trợ", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // Thoát ứng dụng
        System.exit(0);
    }//GEN-LAST:event_jButton12ActionPerformed
    
    private void btnCategoryManagementActionPerformed(java.awt.event.ActionEvent evt) {
        // Mở CategoryManagement
        new com.team4.quanliquanmicay.View.management.CategoryManagement().setVisible(true);
    }
    
    private void btnBillManagementActionPerformed(java.awt.event.ActionEvent evt) {
        // Mở BillManagement
        new com.team4.quanliquanmicay.View.management.BillManagement().setVisible(true);
    }
    
    private void btnTableManagementActionPerformed(java.awt.event.ActionEvent evt) {
        // Mở TableManagement
        new com.team4.quanliquanmicay.View.management.TableManagement().setVisible(true);
    }
    
    private void btnUserManagementActionPerformed(java.awt.event.ActionEvent evt) {
        // Mở UserManagement
        new com.team4.quanliquanmicay.View.management.UserManagement().setVisible(true);
    }
    
    private void btnCustomerManagementActionPerformed(java.awt.event.ActionEvent evt) {
        // Mở CustomerManagement
        new com.team4.quanliquanmicay.View.management.CustomerManagement().setVisible(true);
    }
    
    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {
        // Mở HistoryManagement (Thống kê)
        new com.team4.quanliquanmicay.View.management.HistoryManagement().setVisible(true);
    }
    
    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {
        // Mở ChangePassword (Đổi mật khẩu)
        new com.team4.quanliquanmicay.View.ChangePassword().setVisible(true);
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
    private javax.swing.JButton btnCustomerManagement;
    private javax.swing.JButton btnTableManagement;
    private javax.swing.JButton btnUserManagement;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField txtDay;
    private javax.swing.JTextField txtTime;
    // End of variables declaration//GEN-END:variables
}
