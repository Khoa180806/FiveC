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
            JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
            optionPane.setIcon(createDialogIcon("info"));
            JDialog dialog = optionPane.createDialog(parent, title);
            dialog.setVisible(true);
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
            JOptionPane optionPane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, 
                JOptionPane.YES_NO_OPTION);
            optionPane.setIcon(createDialogIcon("question"));
            JDialog dialog = optionPane.createDialog(parent, title);
            dialog.setVisible(true);
            result[0] = (optionPane.getValue() != null && 
                        optionPane.getValue().equals(JOptionPane.YES_OPTION));
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
            JOptionPane optionPane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, 
                JOptionPane.OK_CANCEL_OPTION);
            optionPane.setIcon(createDialogIcon("question"));
            optionPane.setWantsInput(true);
            JDialog dialog = optionPane.createDialog(parent, title);
            dialog.setVisible(true);
            result[0] = (String) optionPane.getInputValue();
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
            JOptionPane optionPane = new JOptionPane(message, JOptionPane.WARNING_MESSAGE);
            optionPane.setIcon(createDialogIcon("warning"));
            JDialog dialog = optionPane.createDialog(parent, title);
            dialog.setVisible(true);
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
            JOptionPane optionPane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
            optionPane.setIcon(createDialogIcon("error"));
            JDialog dialog = optionPane.createDialog(parent, title);
            dialog.setVisible(true);
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
            JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
            optionPane.setIcon(createDialogIcon("success"));
            JDialog dialog = optionPane.createDialog(parent, title);
            dialog.setVisible(true);
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
            JOptionPane optionPane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, 
                JOptionPane.OK_CANCEL_OPTION, createDialogIcon("question"), options, 
                options.length > 0 ? options[0] : null);
            JDialog dialog = optionPane.createDialog(parent, title);
            dialog.setVisible(true);
            result[0] = (String) optionPane.getValue();
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
            
            // Panel chính với theme mì cay
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
            mainPanel.setBackground(new Color(252, 250, 248)); // Nền nhẹ với tông be
            
            // Message label với styling đẹp
            JLabel messageLabel = new JLabel("<html><div style='text-align: center; width: 320px; line-height: 1.4;'>" 
                + message + "</div></html>");
            messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            messageLabel.setForeground(new Color(33, 37, 41));
            messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            messageLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 25, 15));
            
            // Button panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
            buttonPanel.setBackground(new Color(252, 250, 248));
            
            // Tạo các button với màu sắc phù hợp
            for (int i = 0; i < buttonLabels.length && i < buttonActions.length; i++) {
                final int index = i;
                Color bgColor = getButtonColor(i);
                Color textColor = (bgColor.equals(new Color(204, 164, 133))) ? new Color(33, 37, 41) : Color.WHITE;
                JButton button = createStyledButton(buttonLabels[i], bgColor, textColor);
                
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
            
            // Dialog settings với theme mì cay
            dialog.setSize(450, 220);
            dialog.setLocationRelativeTo(parent);
            dialog.setResizable(false);
            
            // Thêm viền đẹp cho dialog
            dialog.getRootPane().setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(204, 164, 133), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            
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
            mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
            mainPanel.setBackground(new Color(252, 250, 248)); // Nền nhẹ với tông be
            
            // Message
            JLabel messageLabel = new JLabel(message);
            messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            messageLabel.setForeground(new Color(33, 37, 41));
            messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
            
            // Input field với theme mì cay
            JTextField textField = new JTextField(defaultValue != null ? defaultValue : "", 20);
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(204, 164, 133), 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
            textField.setBackground(new Color(252, 250, 248));
            
            // Button panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 20));
            buttonPanel.setBackground(new Color(252, 250, 248));
            
            for (int i = 0; i < buttonLabels.length; i++) {
                final int index = i;
                Color bgColor = getButtonColor(i);
                Color textColor = (bgColor.equals(new Color(204, 164, 133))) ? new Color(33, 37, 41) : Color.WHITE;
                JButton button = createStyledButton(buttonLabels[i], bgColor, textColor);
                
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
            
            dialog.setSize(450, 200);
            dialog.setLocationRelativeTo(parent);
            dialog.setResizable(false);
            
            // Thêm viền đẹp cho dialog
            dialog.getRootPane().setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(204, 164, 133), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            
            SwingUtilities.invokeLater(() -> textField.requestFocus());
            dialog.setVisible(true);
        });
        
        return result;
    }
    
    // === HELPER METHODS ===
    
    /**
     * Tạo icon đẹp cho dialog theo theme mì cay
     */
    private static Icon createDialogIcon(String type) {
        Color iconColor;
        String symbol;
        
        switch (type.toLowerCase()) {
            case "info":
                iconColor = new Color(52, 144, 220);
                symbol = "i";
                break;
            case "warning":
                iconColor = new Color(255, 193, 7);
                symbol = "!";
                break;
            case "error":
                iconColor = new Color(220, 53, 69);
                symbol = "X";
                break;
            case "success":
                iconColor = new Color(40, 167, 69);
                symbol = "✓";
                break;
            case "question":
                iconColor = new Color(134, 39, 43); // Đỏ mì cay
                symbol = "?";
                break;
            default:
                iconColor = new Color(134, 39, 43); // Đỏ mì cay
                symbol = "●";
        }
        
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Vẽ hình tròn nền với màu nhạt hơn
                g2.setColor(new Color(iconColor.getRed(), iconColor.getGreen(), iconColor.getBlue(), 15));
                g2.fillOval(x, y, getIconWidth(), getIconHeight());
                
                // Vẽ viền đậm hơn
                g2.setColor(iconColor);
                g2.setStroke(new BasicStroke(3.5f));
                g2.drawOval(x + 2, y + 2, getIconWidth() - 4, getIconHeight() - 4);
                
                // Vẽ symbol với màu đậm và rõ ràng
                g2.setColor(iconColor);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 26));
                FontMetrics fm = g2.getFontMetrics();
                int textX = x + (getIconWidth() - fm.stringWidth(symbol)) / 2;
                int textY = y + (getIconHeight() + fm.getAscent()) / 2 - 4;
                g2.drawString(symbol, textX, textY);
                
                g2.dispose();
            }
            
            @Override
            public int getIconWidth() { return 42; }
            
            @Override
            public int getIconHeight() { return 42; }
        };
    }
    
    /**
     * Tạo icon đơn giản hơn cho trường hợp cần thiết
     */
    private static Icon createSimpleIcon(String type) {
        Color iconColor;
        String symbol;
        
        switch (type.toLowerCase()) {
            case "info":
                iconColor = new Color(52, 144, 220);
                symbol = "i";
                break;
            case "warning":
                iconColor = new Color(255, 193, 7);
                symbol = "!";
                break;
            case "error":
                iconColor = new Color(220, 53, 69);
                symbol = "X";
                break;
            case "success":
                iconColor = new Color(40, 167, 69);
                symbol = "✓";
                break;
            case "question":
                iconColor = new Color(134, 39, 43); // Đỏ mì cay
                symbol = "?";
                break;
            default:
                iconColor = new Color(134, 39, 43); // Đỏ mì cay
                symbol = "●";
        }
        
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Vẽ hình tròn nền nhẹ
                g2.setColor(new Color(iconColor.getRed(), iconColor.getGreen(), iconColor.getBlue(), 20));
                g2.fillOval(x, y, getIconWidth(), getIconHeight());
                
                // Vẽ viền
                g2.setColor(iconColor);
                g2.setStroke(new BasicStroke(2f));
                g2.drawOval(x + 1, y + 1, getIconWidth() - 2, getIconHeight() - 2);
                
                // Vẽ symbol với màu đậm và rõ ràng
                g2.setColor(iconColor);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
                FontMetrics fm = g2.getFontMetrics();
                int textX = x + (getIconWidth() - fm.stringWidth(symbol)) / 2;
                int textY = y + (getIconHeight() + fm.getAscent()) / 2 - 2;
                g2.drawString(symbol, textX, textY);
                
                g2.dispose();
            }
            
            @Override
            public int getIconWidth() { return 32; }
            
            @Override
            public int getIconHeight() { return 32; }
        };
    }
    
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
     * Tạo button với styling đẹp theo theme mì cay
     */
    private static JButton createStyledButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(textColor);
        button.setBackground(bgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 38));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Bo góc hiện đại
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        
        // Hover effect với màu mì cay
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (bgColor.equals(new Color(134, 39, 43))) { // Đỏ mì cay
                    button.setBackground(new Color(154, 49, 53));
                } else if (bgColor.equals(new Color(204, 164, 133))) { // Be
                    button.setBackground(new Color(184, 144, 113));
                } else {
                    button.setBackground(bgColor.darker());
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    /**
     * Lấy màu cho button theo index - Theme mì cay
     */
    private static Color getButtonColor(int index) {
        Color[] colors = {
            new Color(134, 39, 43),     // Đỏ mì cay (Primary)
            new Color(204, 164, 133),   // Be (Secondary)
            new Color(40, 167, 69),     // Xanh lá (Success)
            new Color(255, 193, 7),     // Vàng (Warning)
            new Color(220, 53, 69),     // Đỏ (Danger)
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