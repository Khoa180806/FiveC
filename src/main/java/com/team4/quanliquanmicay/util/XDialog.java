package com.team4.quanliquanmicay.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class XDialog {
    
    /**
     * Hiển thị thông báo với title mặc định
     */
    public static void alert(String message){
        alert(null, message, "Thông báo!");
    }
    
    /**
     * Hiển thị thông báo với title tùy chỉnh
     */
    public static void alert(String message, String title){
        alert(null, message, title);
    }
    
    /**
     * Hiển thị thông báo với parent window (ẩn cửa sổ cha)
     */
    public static void alert(Window parent, String message, String title){
        showDialogWithParent(parent, () -> {
            XTheme.customizeDialogs();
            JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
        });
    }
    
    /**
     * Hiển thị hộp thoại xác nhận với title mặc định
     */
    public static boolean confirm(String message){
        return confirm(null, message, "Xác nhận!");
    }
    
    /**
     * Hiển thị hộp thoại xác nhận với title tùy chỉnh
     */
    public static boolean confirm(String message, String title){
        return confirm(null, message, title);
    }
    
    /**
     * Hiển thị hộp thoại xác nhận với parent window (ẩn cửa sổ cha)
     */
    public static boolean confirm(Window parent, String message, String title){
        final boolean[] result = {false};
        
        showDialogWithParent(parent, () -> {
            XTheme.customizeDialogs();
            int choice = JOptionPane.showConfirmDialog(parent, message, title, 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            result[0] = (choice == JOptionPane.YES_OPTION);
        });
        
        return result[0];
    }
    
    /**
     * Hiển thị hộp thoại nhập liệu với title mặc định
     */
    public static String prompt(String message){
        return prompt(null, message, "Nhập vào!");
    }
    
    /**
     * Hiển thị hộp thoại nhập liệu với title tùy chỉnh
     */
    public static String prompt(String message, String title){
        return prompt(null, message, title);
    }
    
    /**
     * Hiển thị hộp thoại nhập liệu với parent window (ẩn cửa sổ cha)
     */
    public static String prompt(Window parent, String message, String title){
        final String[] result = {null};
        
        showDialogWithParent(parent, () -> {
            XTheme.customizeDialogs();
            result[0] = JOptionPane.showInputDialog(parent, message, title, JOptionPane.QUESTION_MESSAGE);
        });
        
        return result[0];
    }
    
    // === CÁC METHOD BỔ SUNG VỚI PARENT WINDOW ===
    
    /**
     * Hiển thị cảnh báo với parent window
     */
    public static void warning(Window parent, String message, String title) {
        showDialogWithParent(parent, () -> {
            XTheme.customizeDialogs();
            JOptionPane.showMessageDialog(parent, message, title, JOptionPane.WARNING_MESSAGE);
        });
    }
    
    public static void warning(String message) {
        warning(null, message, "Cảnh báo!");
    }
    
    public static void warning(String message, String title) {
        warning(null, message, title);
    }
    
    /**
     * Hiển thị lỗi với parent window
     */
    public static void error(Window parent, String message, String title) {
        showDialogWithParent(parent, () -> {
            XTheme.customizeDialogs();
            JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
        });
    }
    
    public static void error(String message) {
        error(null, message, "Lỗi!");
    }
    
    public static void error(String message, String title) {
        error(null, message, title);
    }
    
    /**
     * Hiển thị thông báo thành công với parent window
     */
    public static void success(Window parent, String message, String title) {
        showDialogWithParent(parent, () -> {
            XTheme.customizeDialogs();
            JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
        });
    }
    
    public static void success(String message) {
        success(null, message, "Thành công!");
    }
    
    public static void success(String message, String title) {
        success(null, message, title);
    }
    
    /**
     * Hiển thị hộp thoại lựa chọn từ danh sách với parent window
     */
    public static String selection(Window parent, String message, String title, String[] options) {
        final String[] result = {null};
        
        showDialogWithParent(parent, () -> {
            XTheme.customizeDialogs();
            result[0] = (String) JOptionPane.showInputDialog(parent, message, title, 
                JOptionPane.QUESTION_MESSAGE, null, options, options.length > 0 ? options[0] : null);
        });
        
        return result[0];
    }
    
    public static String selection(String message, String[] options) {
        return selection(null, message, "Lựa chọn", options);
    }
    
    public static String selection(String message, String title, String[] options) {
        return selection(null, message, title, options);
    }
    
    // === ADVANCED DIALOG VỚI CUSTOM LAYOUT ===
    
    /**
     * Tạo dialog tùy chỉnh với các nút chức năng
     */
    public static CustomDialogResult showCustomDialog(Window parent, String title, String message, 
                                                     String[] buttonLabels, Runnable[] buttonActions) {
        final CustomDialogResult result = new CustomDialogResult();
        
        showDialogWithParent(parent, () -> {
            // Tạo custom dialog
            JDialog dialog = new JDialog((Frame) parent, title, true);
            dialog.setLayout(new BorderLayout());
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            
            // Panel chính
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            mainPanel.setBackground(Color.WHITE);
            
            // Message label
            JLabel messageLabel = new JLabel("<html><div style='text-align: center; width: 300px;'>" 
                + message + "</div></html>");
            messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            messageLabel.setForeground(new Color(33, 37, 41));
            messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
            
            // Button panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            buttonPanel.setBackground(Color.WHITE);
            
            // Tạo các button
            for (int i = 0; i < buttonLabels.length && i < buttonActions.length; i++) {
                final int index = i;
                JButton button = createStyledButton(buttonLabels[i], getButtonColor(i), Color.WHITE);
                
                button.addActionListener(e -> {
                    result.buttonIndex = index;
                    result.buttonLabel = buttonLabels[index];
                    
                    // Thực hiện action nếu có
                    if (buttonActions[index] != null) {
                        buttonActions[index].run();
                    }
                    
                    dialog.dispose();
                });
                
                buttonPanel.add(button);
            }
            
            // Layout
            mainPanel.add(messageLabel, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            dialog.add(mainPanel);
            
            // Dialog settings
            dialog.setSize(400, 200);
            dialog.setLocationRelativeTo(parent);
            dialog.setResizable(false);
            
            // Đóng dialog khi nhấn X
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    result.buttonIndex = -1;
                    result.buttonLabel = "CANCELLED";
                }
            });
            
            dialog.setVisible(true);
        });
        
        return result;
    }
    
    /**
     * Hiển thị dialog với input field và custom buttons
     */
    public static InputDialogResult showInputDialog(Window parent, String title, String message, 
                                                   String defaultValue, String[] buttonLabels) {
        final InputDialogResult result = new InputDialogResult();
        
        showDialogWithParent(parent, () -> {
            JDialog dialog = new JDialog((Frame) parent, title, true);
            dialog.setLayout(new BorderLayout());
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            mainPanel.setBackground(Color.WHITE);
            
            // Message
            JLabel messageLabel = new JLabel(message);
            messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
            
            // Input field
            JTextField textField = new JTextField(defaultValue != null ? defaultValue : "", 20);
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
            
            // Button panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
            buttonPanel.setBackground(Color.WHITE);
            
            for (int i = 0; i < buttonLabels.length; i++) {
                final int index = i;
                JButton button = createStyledButton(buttonLabels[i], getButtonColor(i), Color.WHITE);
                
                button.addActionListener(e -> {
                    result.buttonIndex = index;
                    result.buttonLabel = buttonLabels[index];
                    result.inputValue = textField.getText();
                    dialog.dispose();
                });
                
                buttonPanel.add(button);
            }
            
            // Enter key để submit
            textField.addActionListener(e -> {
                if (buttonLabels.length > 0) {
                    result.buttonIndex = 0;
                    result.buttonLabel = buttonLabels[0];
                    result.inputValue = textField.getText();
                    dialog.dispose();
                }
            });
            
            mainPanel.add(messageLabel, BorderLayout.NORTH);
            mainPanel.add(textField, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            dialog.add(mainPanel);
            
            dialog.setSize(400, 180);
            dialog.setLocationRelativeTo(parent);
            dialog.setResizable(false);
            
            SwingUtilities.invokeLater(() -> textField.requestFocus());
            dialog.setVisible(true);
        });
        
        return result;
    }
    
    // === HELPER METHODS ===
    
    /**
     * Phương thức chính để ẩn/hiện parent window
     */
    private static void showDialogWithParent(Window parent, Runnable dialogAction) {
        boolean wasVisible = false;
        
        try {
            // Ẩn parent window nếu có
            if (parent != null && parent.isVisible()) {
                wasVisible = true;
                parent.setVisible(false);
            }
            
            // Hiển thị dialog
            dialogAction.run();
            
        } finally {
            // Hiện lại parent window
            if (parent != null && wasVisible) {
                SwingUtilities.invokeLater(() -> {
                    parent.setVisible(true);
                    parent.toFront();
                    parent.requestFocus();
                });
            }
        }
    }
    
    /**
     * Tạo button với styling đẹp
     */
    private static JButton createStyledButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(textColor);
        button.setBackground(bgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(90, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    /**
     * Lấy màu cho button theo index
     */
    private static Color getButtonColor(int index) {
        Color[] colors = {
            new Color(102, 0, 0),      // Đỏ đậm (Primary)
            new Color(40, 167, 69),     // Xanh lá (Success)
            new Color(255, 193, 7),     // Vàng (Warning)
            new Color(220, 53, 69),     // Đỏ (Danger)
            new Color(108, 117, 125),   // Xám (Secondary)
            new Color(52, 144, 220)     // Xanh dương (Info)
        };
        
        return colors[index % colors.length];
    }
    
    // === RESULT CLASSES ===
    
    /**
     * Kết quả trả về từ custom dialog
     */
    public static class CustomDialogResult {
        public int buttonIndex = -1;
        public String buttonLabel = "";
        
        public boolean isButton(int index) {
            return buttonIndex == index;
        }
        
        public boolean isButton(String label) {
            return buttonLabel.equals(label);
        }
    }
    
    /**
     * Kết quả trả về từ input dialog
     */
    public static class InputDialogResult {
        public int buttonIndex = -1;
        public String buttonLabel = "";
        public String inputValue = "";
        
        public boolean isButton(int index) {
            return buttonIndex == index;
        }
        
        public boolean isButton(String label) {
            return buttonLabel.equals(label);
        }
    }
}