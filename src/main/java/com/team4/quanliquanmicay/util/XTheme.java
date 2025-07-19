package com.team4.quanliquanmicay.util;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class XTheme {
    
    // Định nghĩa bảng màu cho Light Theme
    private static final Color LIGHT_BACKGROUND = new Color(255, 255, 255);
    private static final Color LIGHT_SURFACE = new Color(248, 249, 250);
    private static final Color LIGHT_ACCENT = new Color(102, 0, 0);
    private static final Color LIGHT_TEXT_PRIMARY = new Color(33, 37, 41);
    private static final Color LIGHT_TEXT_SECONDARY = new Color(108, 117, 125);
    private static final Color LIGHT_BORDER = new Color(206, 212, 218);
    private static final Color LIGHT_HOVER = new Color(233, 236, 239);
    private static final Color LIGHT_SUCCESS = new Color(40, 167, 69);
    private static final Color LIGHT_WARNING = new Color(255, 193, 7);
    private static final Color LIGHT_ERROR = new Color(220, 53, 69);
    
    /**
     * Áp dụng theme LIGHT chung cho toàn bộ UI của ứng dụng
     * Hàm này thiết lập một giao diện sáng nhất quán và đẹp mắt
     */
    public static void applyLightTheme() {
        try {
            // Thiết lập FlatLaf Light theme
            UIManager.setLookAndFeel(new FlatLightLaf());
            
            // Áp dụng các tùy chỉnh màu sắc cho Light theme
            customizeLightComponents();
            
            // Thiết lập font chung cho ứng dụng
            setApplicationFonts();
            
            // Cập nhật UI cho tất cả cửa sổ đang mở
            updateAllWindows();
            
            System.out.println("✅ Đã áp dụng thành công Light Theme cho toàn bộ UI");
            
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi áp dụng Light Theme: " + e.getMessage());
            e.printStackTrace();
            // Fallback về theme mặc định
            applyFallbackTheme();
        }
    }
    
    /**
     * Tùy chỉnh các component UI cho Light Theme
     */
    private static void customizeLightComponents() {
        // === BACKGROUND COLORS ===
        UIManager.put("Panel.background", LIGHT_BACKGROUND);
        UIManager.put("Frame.background", LIGHT_BACKGROUND);
        UIManager.put("Dialog.background", LIGHT_BACKGROUND);
        UIManager.put("OptionPane.background", LIGHT_BACKGROUND);
        UIManager.put("RootPane.background", LIGHT_BACKGROUND);
        
        // === TEXT COLORS ===
        UIManager.put("Label.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("Button.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("TextField.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("TextArea.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("ComboBox.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("List.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("Tree.foreground", LIGHT_TEXT_PRIMARY);
        
        // === BUTTON STYLING ===
        UIManager.put("Button.background", LIGHT_SURFACE);
        UIManager.put("Button.hoverBackground", LIGHT_HOVER);
        UIManager.put("Button.pressedBackground", LIGHT_ACCENT);
        UIManager.put("Button.borderColor", LIGHT_BORDER);
        UIManager.put("Button.focusedBorderColor", LIGHT_ACCENT);
        UIManager.put("Button.arc", 8); // Bo góc button
        
        // === INPUT FIELDS ===
        UIManager.put("TextField.background", LIGHT_BACKGROUND);
        UIManager.put("TextArea.background", LIGHT_BACKGROUND);
        UIManager.put("ComboBox.background", LIGHT_BACKGROUND);
        UIManager.put("TextField.borderColor", LIGHT_BORDER);
        UIManager.put("TextField.focusedBorderColor", LIGHT_ACCENT);
        UIManager.put("TextArea.borderColor", LIGHT_BORDER);
        UIManager.put("ComboBox.borderColor", LIGHT_BORDER);
        
        // === TABLE STYLING ===
        UIManager.put("Table.background", LIGHT_BACKGROUND);
        UIManager.put("Table.alternateRowColor", new Color(248, 249, 250));
        UIManager.put("Table.selectionBackground", new Color(232, 62, 140, 30));
        UIManager.put("Table.selectionForeground", LIGHT_TEXT_PRIMARY);
        UIManager.put("Table.gridColor", new Color(230, 230, 230));
        UIManager.put("TableHeader.background", LIGHT_SURFACE);
        UIManager.put("TableHeader.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("TableHeader.separatorColor", LIGHT_BORDER);
        
        // === MENU STYLING ===
        UIManager.put("MenuBar.background", LIGHT_SURFACE);
        UIManager.put("MenuBar.borderColor", LIGHT_BORDER);
        UIManager.put("Menu.background", LIGHT_SURFACE);
        UIManager.put("Menu.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("Menu.hoverBackground", LIGHT_HOVER);
        UIManager.put("MenuItem.background", LIGHT_BACKGROUND);
        UIManager.put("MenuItem.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("MenuItem.hoverBackground", LIGHT_HOVER);
        
        // === SCROLL BAR ===
        UIManager.put("ScrollBar.background", LIGHT_SURFACE);
        UIManager.put("ScrollBar.thumb", new Color(180, 180, 180));
        UIManager.put("ScrollBar.hoverThumbColor", new Color(150, 150, 150));
        UIManager.put("ScrollBar.pressedThumbColor", LIGHT_ACCENT);
        UIManager.put("ScrollBar.width", 12);
        
        // === TABS ===
        UIManager.put("TabbedPane.background", LIGHT_SURFACE);
        UIManager.put("TabbedPane.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("TabbedPane.selectedBackground", LIGHT_BACKGROUND);
        UIManager.put("TabbedPane.selectedForeground", LIGHT_ACCENT);
        UIManager.put("TabbedPane.hoverColor", LIGHT_HOVER);
        
        // === PROGRESS BAR ===
        UIManager.put("ProgressBar.background", LIGHT_SURFACE);
        UIManager.put("ProgressBar.foreground", LIGHT_ACCENT);
        UIManager.put("ProgressBar.selectionBackground", LIGHT_BACKGROUND);
        UIManager.put("ProgressBar.selectionForeground", LIGHT_TEXT_PRIMARY);
        
        // === TOOLTIP ===
        UIManager.put("ToolTip.background", new Color(255, 255, 240));
        UIManager.put("ToolTip.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("ToolTip.border", new LineBorder(LIGHT_BORDER, 1));
        
        // === LIST & TREE ===
        UIManager.put("List.background", LIGHT_BACKGROUND);
        UIManager.put("List.selectionBackground", new Color(232, 62, 140, 30));
        UIManager.put("List.selectionForeground", LIGHT_TEXT_PRIMARY);
        UIManager.put("Tree.background", LIGHT_BACKGROUND);
        UIManager.put("Tree.selectionBackground", new Color(232, 62, 140, 30));
        UIManager.put("Tree.selectionForeground", LIGHT_TEXT_PRIMARY);
        
        // === CHECKBOX & RADIO ===
        UIManager.put("CheckBox.background", LIGHT_BACKGROUND);
        UIManager.put("CheckBox.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("CheckBox.focusedBorderColor", LIGHT_ACCENT);
        UIManager.put("RadioButton.background", LIGHT_BACKGROUND);
        UIManager.put("RadioButton.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("RadioButton.focusedBorderColor", LIGHT_ACCENT);
        
        // === BORDERS ===
        UIManager.put("Component.borderColor", LIGHT_BORDER);
        UIManager.put("Component.focusedBorderColor", LIGHT_ACCENT);
        UIManager.put("Component.arc", 6); // Bo góc chung
    }
    
    /**
     * Thiết lập font chung cho ứng dụng
     */
    private static void setApplicationFonts() {
        try {
            // Font chính cho UI
            Font mainFont = new Font("Segoe UI", Font.PLAIN, 13);
            Font titleFont = new Font("Segoe UI", Font.BOLD, 14);
            Font smallFont = new Font("Segoe UI", Font.PLAIN, 11);
            
            // Áp dụng font cho các component
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
     * Áp dụng theme dự phòng khi có lỗi
     */
    private static void applyFallbackTheme() {
        try {
            // Sử dụng FlatIntelliJ LAF làm theme dự phòng
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            System.out.println("⚠️ Đã chuyển sang theme dự phòng FlatIntelliJ");
        } catch (Exception fallbackError) {
            System.err.println("❌ Không thể áp dụng theme dự phòng: " + fallbackError.getMessage());
        }
    }
    
    /**
     * Hàm cũ - giữ lại để tương thích ngược
     * @deprecated Sử dụng applyLightTheme() thay thế
     */
    @Deprecated
    public static void setLightTheme() {
        applyLightTheme();
    }
    
    /**
     * Kiểm tra xem theme hiện tại có phải là Light không
     */
    public static boolean isLightTheme() {
        return UIManager.getLookAndFeel() instanceof FlatLightLaf;
    }
}
