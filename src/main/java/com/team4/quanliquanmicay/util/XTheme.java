package com.team4.quanliquanmicay.util;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class XTheme {
    
    /**
     * ========================================
     * PHẦN 1: KIỂU DÁNG COMPONENT
     * ========================================
     */
    
    /**
     * Áp dụng kiểu dáng component cơ bản
     */
    public static void applyComponentStyle() {
        try {
            // Thiết lập FlatLaf Light theme
            UIManager.setLookAndFeel(new FlatLightLaf());
            
            // Áp dụng kiểu dáng component
            customizeComponentAppearance();
            
            // Thiết lập font chung cho ứng dụng
            setApplicationFonts();
            
            // Cập nhật UI cho tất cả cửa sổ đang mở
            updateAllWindows();
            
            System.out.println("✅ Đã áp dụng thành công kiểu dáng component");
            
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi áp dụng kiểu dáng component: " + e.getMessage());
            e.printStackTrace();
            applyFallbackStyle();
        }
    }
    
    /**
     * Tùy chỉnh kiểu dáng component
     */
    private static void customizeComponentAppearance() {
        // === BACKGROUND STYLING ===
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("Frame.background", Color.WHITE);
        UIManager.put("Dialog.background", Color.WHITE);
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("RootPane.background", Color.WHITE);
        
        // === TEXT STYLING ===
        UIManager.put("Label.foreground", new Color(33, 37, 41));
        UIManager.put("Button.foreground", new Color(33, 37, 41));
        UIManager.put("TextField.foreground", new Color(33, 37, 41));
        UIManager.put("TextArea.foreground", new Color(33, 37, 41));
        UIManager.put("ComboBox.foreground", new Color(33, 37, 41));
        UIManager.put("List.foreground", new Color(33, 37, 41));
        UIManager.put("Tree.foreground", new Color(33, 37, 41));
        
        // === BUTTON STYLING ===
        UIManager.put("Button.background", new Color(248, 249, 250));
        UIManager.put("Button.borderColor", new Color(206, 212, 218));
        UIManager.put("Button.focusedBorderColor", new Color(134, 39, 43));
        UIManager.put("Button.arc", 12);
        UIManager.put("Button.animationDuration", 150);
        
        // === INPUT FIELDS STYLING ===
        UIManager.put("TextField.background", new Color(252, 252, 252));
        UIManager.put("TextArea.background", new Color(252, 252, 252));
        UIManager.put("ComboBox.background", new Color(252, 252, 252));
        UIManager.put("TextField.borderColor", new Color(206, 212, 218));
        UIManager.put("TextField.focusedBorderColor", new Color(134, 39, 43));
        UIManager.put("TextArea.borderColor", new Color(206, 212, 218));
        UIManager.put("ComboBox.borderColor", new Color(206, 212, 218));
        
        // === TABLE STYLING ===
        UIManager.put("Table.background", Color.WHITE);
        UIManager.put("Table.alternateRowColor", new Color(248, 249, 250));
        UIManager.put("Table.selectionBackground", new Color(134, 39, 43, 20));
        UIManager.put("Table.selectionForeground", new Color(33, 37, 41));
        UIManager.put("Table.gridColor", new Color(206, 212, 218));
        UIManager.put("TableHeader.background", new Color(134, 39, 43));
        UIManager.put("TableHeader.foreground", Color.WHITE);
        UIManager.put("TableHeader.separatorColor", new Color(206, 212, 218));
        
        // === MENU STYLING ===
        UIManager.put("MenuBar.background", new Color(134, 39, 43));
        UIManager.put("MenuBar.borderColor", new Color(206, 212, 218));
        UIManager.put("Menu.background", new Color(134, 39, 43));
        UIManager.put("Menu.foreground", Color.WHITE);
        UIManager.put("Menu.hoverBackground", new Color(154, 49, 53));
        UIManager.put("MenuItem.background", Color.WHITE);
        UIManager.put("MenuItem.foreground", new Color(33, 37, 41));
        UIManager.put("MenuItem.hoverBackground", new Color(248, 249, 250));
        
        // === SCROLL BAR STYLING ===
        UIManager.put("ScrollBar.background", new Color(248, 249, 250));
        UIManager.put("ScrollBar.thumb", new Color(206, 212, 218));
        UIManager.put("ScrollBar.hoverThumbColor", new Color(184, 144, 113));
        UIManager.put("ScrollBar.pressedThumbColor", new Color(134, 39, 43));
        UIManager.put("ScrollBar.width", 14);
        
        // === TABS STYLING ===
        UIManager.put("TabbedPane.background", new Color(248, 249, 250));
        UIManager.put("TabbedPane.foreground", new Color(33, 37, 41));
        UIManager.put("TabbedPane.selectedBackground", new Color(134, 39, 43));
        UIManager.put("TabbedPane.selectedForeground", Color.WHITE);
        UIManager.put("TabbedPane.hoverColor", new Color(204, 164, 133));
        
        // === PROGRESS BAR STYLING ===
        UIManager.put("ProgressBar.background", new Color(248, 249, 250));
        UIManager.put("ProgressBar.foreground", new Color(134, 39, 43));
        UIManager.put("ProgressBar.selectionBackground", new Color(204, 164, 133));
        UIManager.put("ProgressBar.selectionForeground", new Color(33, 37, 41));
        
        // === TOOLTIP STYLING ===
        UIManager.put("ToolTip.background", new Color(248, 249, 250));
        UIManager.put("ToolTip.foreground", new Color(33, 37, 41));
        UIManager.put("ToolTip.border", new LineBorder(new Color(206, 212, 218), 2));
        
        // === LIST & TREE STYLING ===
        UIManager.put("List.background", Color.WHITE);
        UIManager.put("List.selectionBackground", new Color(134, 39, 43, 20));
        UIManager.put("List.selectionForeground", new Color(33, 37, 41));
        UIManager.put("Tree.background", Color.WHITE);
        UIManager.put("Tree.selectionBackground", new Color(134, 39, 43, 20));
        UIManager.put("Tree.selectionForeground", new Color(33, 37, 41));
        
        // === CHECKBOX & RADIO STYLING ===
        UIManager.put("CheckBox.background", Color.WHITE);
        UIManager.put("CheckBox.foreground", new Color(33, 37, 41));
        UIManager.put("CheckBox.focusedBorderColor", new Color(134, 39, 43));
        UIManager.put("CheckBox.iconColor", new Color(134, 39, 43));
        UIManager.put("RadioButton.background", Color.WHITE);
        UIManager.put("RadioButton.foreground", new Color(33, 37, 41));
        UIManager.put("RadioButton.focusedBorderColor", new Color(134, 39, 43));
        UIManager.put("RadioButton.iconColor", new Color(134, 39, 43));
        
        // === BORDERS STYLING ===
        UIManager.put("Component.borderColor", new Color(206, 212, 218));
        UIManager.put("Component.focusedBorderColor", new Color(134, 39, 43));
        UIManager.put("Component.arc", 8);
    }
    
    /**
     * Thiết lập font chung cho ứng dụng
     */
    private static void setApplicationFonts() {
        try {
            Font mainFont = new Font("Segoe UI", Font.PLAIN, 13);
            Font titleFont = new Font("Segoe UI", Font.BOLD, 14);
            Font smallFont = new Font("Segoe UI", Font.PLAIN, 11);
            
            UIManager.put("Label.font", mainFont);
            UIManager.put("Button.font", mainFont);
            UIManager.put("TextField.font", mainFont);
            UIManager.put("TextArea.font", mainFont);
            UIManager.put("ComboBox.font", mainFont);
            UIManager.put("List.font", mainFont);
            UIManager.put("Table.font", mainFont);
            UIManager.put("TableHeader.font", titleFont);
            UIManager.put("Menu.font", mainFont);
            UIManager.put("MenuItem.font", mainFont);
            UIManager.put("TabbedPane.font", mainFont);
            UIManager.put("ToolTip.font", smallFont);
            
        } catch (Exception e) {
            System.err.println("⚠️ Không thể thiết lập font tùy chỉnh: " + e.getMessage());
        }
    }
    
    /**
     * Cập nhật UI cho tất cả cửa sổ đang mở
     */
    private static void updateAllWindows() {
        SwingUtilities.invokeLater(() -> {
            try {
                for (Window window : Window.getWindows()) {
                    SwingUtilities.updateComponentTreeUI(window);
                    window.repaint();
                }
            } catch (Exception e) {
                System.err.println("⚠️ Lỗi khi cập nhật UI: " + e.getMessage());
            }
        });
    }
    
    /**
     * Áp dụng kiểu dáng dự phòng khi có lỗi
     */
    private static void applyFallbackStyle() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            System.out.println("⚠️ Đã chuyển sang kiểu dáng dự phòng FlatIntelliJ");
        } catch (Exception fallbackError) {
            System.err.println("❌ Không thể áp dụng kiểu dáng dự phòng: " + fallbackError.getMessage());
        }
    }
    
    /**
     * ========================================
     * PHẦN 2: HIỆU ỨNG HOVER
     * ========================================
     */
    
    /**
     * Áp dụng hiệu ứng hover cho button
     * @param button Button cần áp dụng hiệu ứng hover
     */
    public static void applyHoverEffect(JButton button) {
        // Kiểm tra xem button này đã được áp dụng hover chưa
        if (button.getClientProperty("XThemeHoverApplied") != null) {
            return;
        }
        
        // Lấy màu hiện tại của button
        Color currentColor = button.getBackground();
        Color hoverColor = createHoverColor(currentColor);
        Color pressedColor = createPressedColor(currentColor);
        
        // Lưu màu gốc để restore khi mouse exit
        final Color originalColor = currentColor;
        
        // Thêm hiệu ứng hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
            
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(pressedColor);
            }
            
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
        });
        
        // Đánh dấu button này đã được áp dụng hover
        button.putClientProperty("XThemeHoverApplied", true);
        
        // Thiết lập cursor
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    /**
     * Áp dụng hiệu ứng hover cho tất cả button trong container
     * @param container Container chứa các button
     */
    public static void applyHoverEffectToAllButtons(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                applyHoverEffect(button);
            } else if (comp instanceof Container) {
                applyHoverEffectToAllButtons((Container) comp);
            }
        }
    }
    
    /**
     * Tạo màu hover từ màu gốc
     */
    private static Color createHoverColor(Color originalColor) {
        return new Color(
            Math.min(255, (int)(originalColor.getRed() * 0.85)),
            Math.min(255, (int)(originalColor.getGreen() * 0.85)),
            Math.min(255, (int)(originalColor.getBlue() * 0.85))
        );
    }
    
    /**
     * Tạo màu pressed từ màu gốc
     */
    private static Color createPressedColor(Color originalColor) {
        return new Color(
            Math.min(255, (int)(originalColor.getRed() * 0.75)),
            Math.min(255, (int)(originalColor.getGreen() * 0.75)),
            Math.min(255, (int)(originalColor.getBlue() * 0.75))
        );
    }
    
    /**
     * ========================================
     * PHẦN 3: HIỆU ỨNG CLICK
     * ========================================
     */
    
    /**
     * Áp dụng hiệu ứng click cho button
     * @param button Button cần áp dụng hiệu ứng click
     */
    public static void applyClickEffect(JButton button) {
        // Kiểm tra xem button này đã được áp dụng click chưa
        if (button.getClientProperty("XThemeClickApplied") != null) {
            return;
        }
        
        // Lấy màu hiện tại của button
        Color currentColor = button.getBackground();
        Color clickColor = createClickColor(currentColor);
        
        // Lưu màu gốc để restore khi mouse release
        final Color originalColor = currentColor;
        
        // Thêm hiệu ứng click
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(clickColor);
            }
            
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
        
        // Đánh dấu button này đã được áp dụng click
        button.putClientProperty("XThemeClickApplied", true);
    }
    
    /**
     * Áp dụng hiệu ứng click cho tất cả button trong container
     * @param container Container chứa các button
     */
    public static void applyClickEffectToAllButtons(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                applyClickEffect(button);
            } else if (comp instanceof Container) {
                applyClickEffectToAllButtons((Container) comp);
            }
        }
    }
    
    /**
     * Tạo màu click từ màu gốc
     */
    private static Color createClickColor(Color originalColor) {
        return new Color(
            Math.min(255, (int)(originalColor.getRed() * 0.7)),
            Math.min(255, (int)(originalColor.getGreen() * 0.7)),
            Math.min(255, (int)(originalColor.getBlue() * 0.7))
        );
    }
    
    /**
     * ========================================
     * PHẦN 4: TẠO BUTTON VỚI HIỆU ỨNG
     * ========================================
     */
    
    /**
     * Tạo button với hiệu ứng hover và click
     * @param text Text hiển thị trên button
     * @param bgColor Màu nền chính
     * @param textColor Màu chữ
     * @param action Action khi click
     * @return JButton với hiệu ứng đẹp
     */
    public static JButton createCustomButton(String text, Color bgColor, Color textColor, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(textColor);
        button.setBackground(bgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Áp dụng hiệu ứng hover và click
        applyHoverEffect(button);
        applyClickEffect(button);
        
        if (action != null) {
            button.addActionListener(action);
        }
        
        return button;
    }
    
    /**
     * Tạo button với theme mì cay
     */
    public static JButton createMiyCayButton(String text, ActionListener action) {
        return createCustomButton(text, new Color(134, 39, 43), Color.WHITE, action);
    }
    
    /**
     * Tạo button với theme be
     */
    public static JButton createBeButton(String text, ActionListener action) {
        return createCustomButton(text, new Color(204, 164, 133), new Color(33, 37, 41), action);
    }
    
    /**
     * Tạo button thành công
     */
    public static JButton createSuccessButton(String text, ActionListener action) {
        return createCustomButton(text, new Color(40, 167, 69), Color.WHITE, action);
    }
    
    /**
     * Tạo button cảnh báo
     */
    public static JButton createWarningButton(String text, ActionListener action) {
        return createCustomButton(text, new Color(255, 193, 7), new Color(33, 37, 41), action);
    }
    
    /**
     * Tạo button lỗi
     */
    public static JButton createErrorButton(String text, ActionListener action) {
        return createCustomButton(text, new Color(220, 53, 69), Color.WHITE, action);
    }
    
    /**
     * ========================================
     * PHẦN 5: DIALOG STYLING
     * ========================================
     */
    
    /**
     * Tùy chỉnh giao diện cho Dialog và OptionPane
     */
    public static void customizeDialogs() {
        try {
            // === OPTION PANE STYLING ===
            UIManager.put("OptionPane.background", Color.WHITE);
            UIManager.put("OptionPane.foreground", new Color(33, 37, 41));
            UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 12));
            UIManager.put("OptionPane.border", BorderFactory.createEmptyBorder(20, 20, 15, 20));
            
            // === DIALOG STYLING ===
            UIManager.put("Dialog.background", Color.WHITE);
            UIManager.put("Dialog.foreground", new Color(33, 37, 41));
            
            // === BUTTON TRONG DIALOG ===
            UIManager.put("OptionPane.buttonMinimumWidth", 85);
            UIManager.put("OptionPane.buttonAreaBorder", BorderFactory.createEmptyBorder(15, 0, 0, 0));
            
            System.out.println("✅ Đã tùy chỉnh giao diện Dialog thành công");
            
        } catch (Exception e) {
            System.err.println("⚠️ Lỗi khi tùy chỉnh Dialog: " + e.getMessage());
        }
    }
    
    /**
     * Tạo custom JDialog với kiểu dáng đẹp
     */
    public static JDialog createStyledDialog(String title, int width, int height) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setSize(width, height);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // Áp dụng kiểu dáng
        dialog.getContentPane().setBackground(Color.WHITE);
        
        return dialog;
    }
    
    /**
     * ========================================
     * PHẦN 6: THÔNG BÁO STYLING
     * ========================================
     */
    
    /**
     * Hiển thị thông báo đẹp với kiểu dáng tùy chỉnh
     */
    public static void showAlert(String message, String title) {
        customizeDialogs();
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Hiển thị thông báo đẹp (với title mặc định)
     */
    public static void showAlert(String message) {
        showAlert(message, "Thông báo");
    }
    
    /**
     * Hiển thị hộp thoại xác nhận đẹp
     */
    public static boolean showConfirm(String message, String title) {
        customizeDialogs();
        int result = JOptionPane.showConfirmDialog(null, message, title, 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }
    
    /**
     * Hiển thị hộp thoại xác nhận đẹp (với title mặc định)
     */
    public static boolean showConfirm(String message) {
        return showConfirm(message, "Xác nhận");
    }
    
    /**
     * Hiển thị hộp thoại cảnh báo đẹp
     */
    public static void showWarning(String message, String title) {
        customizeDialogs();
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Hiển thị hộp thoại cảnh báo đẹp (với title mặc định)
     */
    public static void showWarning(String message) {
        showWarning(message, "Cảnh báo");
    }
    
    /**
     * Hiển thị hộp thoại lỗi đẹp
     */
    public static void showError(String message, String title) {
        customizeDialogs();
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Hiển thị hộp thoại lỗi đẹp (với title mặc định)
     */
    public static void showError(String message) {
        showError(message, "Lỗi");
    }
    
    /**
     * Hiển thị hộp thoại nhập liệu đẹp
     */
    public static String showInput(String message, String title) {
        customizeDialogs();
        return JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Hiển thị hộp thoại nhập liệu đẹp (với title mặc định)
     */
    public static String showInput(String message) {
        return showInput(message, "Nhập dữ liệu");
    }
    
    /**
     * Hiển thị hộp thoại nhập liệu với giá trị mặc định
     */
    public static String showInput(String message, String title, String defaultValue) {
        customizeDialogs();
        return (String) JOptionPane.showInputDialog(null, message, title, 
            JOptionPane.QUESTION_MESSAGE, null, null, defaultValue);
    }
    
    /**
     * Hiển thị hộp thoại chọn từ danh sách
     */
    public static String showSelection(String message, String title, String[] options) {
        customizeDialogs();
        return (String) JOptionPane.showInputDialog(null, message, title, 
            JOptionPane.QUESTION_MESSAGE, null, options, options.length > 0 ? options[0] : null);
    }
    
    /**
     * ========================================
     * PHẦN 7: ÁP DỤNG TOÀN BỘ THEME
     * ========================================
     */
    
    /**
     * Áp dụng toàn bộ theme bao gồm kiểu dáng component, dialog và hiệu ứng
     */
    public static void applyFullTheme() {
        applyComponentStyle();
        customizeDialogs();
        
        System.out.println("🎨 Đã áp dụng thành công toàn bộ theme");
        System.out.println("📋 Bao gồm: Kiểu dáng component + Dialog styling + Hiệu ứng hover/click");
    }
    
    /**
     * Kiểm tra xem theme hiện tại có phải là Light không
     */
    public static boolean isLightTheme() {
        return UIManager.getLookAndFeel() instanceof FlatLightLaf;
    }
    
    /**
     * Hàm cũ - giữ lại để tương thích ngược
     * @deprecated Sử dụng applyComponentStyle() thay thế
     */
    @Deprecated
    public static void setLightTheme() {
        applyComponentStyle();
    }
    
    /**
     * Hàm cũ - giữ lại để tương thích ngược
     * @deprecated Sử dụng applyFullTheme() thay thế
     */
    @Deprecated
    public static void applyLightTheme() {
        applyFullTheme();
    }
}
