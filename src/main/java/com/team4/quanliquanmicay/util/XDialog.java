package com.team4.quanliquanmicay.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class XDialog {
    
    // Áp dụng theme trước khi hiển thị dialog
    private static void ensureThemeApplied() {
        if (!XTheme.isLightTheme()) {
            XTheme.applyLightTheme();
        }
    }
    
    public static void alert(String message){
        XDialog.alert(message, "Thông báo!");
    }
    
    public static void alert(String message, String title){
        ensureThemeApplied();
        
        // Custom OptionPane với icon đẹp hơn
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        
        // Tùy chỉnh font và màu sắc
        optionPane.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        // Tạo dialog với thiết lập tùy chỉnh
        JDialog dialog = optionPane.createDialog(null, title);
        dialog.setModal(true);
        dialog.setResizable(false);
        
        // Áp dụng icon tùy chỉnh nếu có
        try {
            ImageIcon icon = new ImageIcon(XDialog.class.getResource("/icons_and_images/icons8-alert-48.png"));
            if (icon != null) {
                dialog.setIconImage(icon.getImage());
            }
        } catch (Exception e) {
            // Không có icon thì bỏ qua
        }
        
        // Cải thiện giao diện button
        customizeDialogButtons(optionPane);
        
        dialog.setVisible(true);
    }
    
    public static boolean confirm(String message){
        return XDialog.confirm(message, "Xác nhận!");
    }
    
    public static boolean confirm(String message, String title){
        ensureThemeApplied();
        
        // Tạo custom dialog với styling đẹp hơn
        JDialog dialog = new JDialog((Frame)null, title, true);
        dialog.setLayout(new BorderLayout());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // Panel chính với padding
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        
        // Label thông điệp
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(new Color(33, 37, 41));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        
        // Panel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        // Button Có với styling đẹp
        JButton yesButton = createStyledButton("Có", new Color(40, 167, 69), Color.WHITE);
        JButton noButton = createStyledButton("Không", new Color(220, 53, 69), Color.WHITE);
        
        final boolean[] result = {false};
        
        yesButton.addActionListener(e -> {
            result[0] = true;
            dialog.dispose();
        });
        
        noButton.addActionListener(e -> {
            result[0] = false;
            dialog.dispose();
        });
        
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        
        // Thêm components vào dialog
        mainPanel.add(messageLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(mainPanel);
        
        // Thiết lập dialog
        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        
        // Icon cho dialog
        try {
            ImageIcon icon = new ImageIcon(XDialog.class.getResource("/icons_and_images/icons8-question-48.png"));
            if (icon != null) {
                dialog.setIconImage(icon.getImage());
            }
        } catch (Exception e) {
            // Không có icon thì bỏ qua
        }
        
        dialog.setVisible(true);
        return result[0];
    }
    
    public static String prompt(String message){
        return XDialog.prompt(message, "Nhập vào!");
    }
    
    public static String prompt(String message, String title){
        ensureThemeApplied();
        
        // Tạo custom input dialog
        JDialog dialog = new JDialog((Frame)null, title, true);
        dialog.setLayout(new BorderLayout());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        
        // Label thông điệp
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(new Color(33, 37, 41));
        messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        // Text field với styling đẹp
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        // Panel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton okButton = createStyledButton("OK", new Color(102, 0, 0), Color.WHITE);
        JButton cancelButton = createStyledButton("Hủy", new Color(108, 117, 125), Color.WHITE);
        
        final String[] result = {null};
        
        okButton.addActionListener(e -> {
            result[0] = textField.getText();
            dialog.dispose();
        });
        
        cancelButton.addActionListener(e -> {
            result[0] = null;
            dialog.dispose();
        });
        
        // Enter key submit
        textField.addActionListener(e -> okButton.doClick());
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        // Layout
        mainPanel.add(messageLabel, BorderLayout.NORTH);
        mainPanel.add(textField, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(mainPanel);
        
        // Thiết lập dialog
        dialog.setSize(400, 180);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        
        // Focus vào text field
        SwingUtilities.invokeLater(() -> textField.requestFocus());
        
        dialog.setVisible(true);
        return result[0];
    }
    
    // Tạo button với styling đẹp
    private static JButton createStyledButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(textColor);
        button.setBackground(bgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(80, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    // Cải thiện giao diện các button trong OptionPane
    private static void customizeDialogButtons(JOptionPane optionPane) {
        // Tùy chỉnh thêm các thuộc tính UI cho OptionPane
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 12));
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("OptionPane.buttonMinimumWidth", 80);
    }
    
    // Thêm method để hiển thị dialog với custom icon
    public static void alertWithIcon(String message, String title, String iconPath) {
        ensureThemeApplied();
        
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(XDialog.class.getResource(iconPath));
        } catch (Exception e) {
            // Fallback to default
        }
        
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE, icon);
    }
}