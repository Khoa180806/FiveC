package com.team4.quanliquanmicay.util;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.*;
import java.awt.*;

public class XTheme {
    
    // Enum để định nghĩa các theme
    public enum Theme {
        LIGHT, DARK, INTELLIJ, DARCULA
    }
    
    // Theme hiện tại
    private static Theme currentTheme = Theme.INTELLIJ;
    
    /**
     * Khởi tạo theme mặc định cho ứng dụng
     */
    public static void init() {
        setTheme(currentTheme);
    }
    
    /**
     * Thiết lập theme cho ứng dụng
     * @param theme Theme muốn áp dụng
     */
    public static void setTheme(Theme theme) {
        try {
            switch (theme) {
                case LIGHT:
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    break;
                case DARK:
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                    break;
                case INTELLIJ:
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                    break;
                case DARCULA:
                    UIManager.setLookAndFeel(new FlatDarculaLaf());
                    break;
            }
            currentTheme = theme;
            
            // Cập nhật tất cả cửa sổ đang mở
            SwingUtilities.updateComponentTreeUI(null);
            updateAllWindows();
            
        } catch (Exception e) {
            System.err.println("Lỗi khi thiết lập theme: " + e.getMessage());
        }
    }
    
    /**
     * Áp dụng theme cho JFrame
     * @param frame JFrame cần áp dụng theme
     */
    public static void applyTheme(JFrame frame) {
        if (frame != null) {
            SwingUtilities.updateComponentTreeUI(frame);
            frame.repaint();
        }
    }
    
    /**
     * Áp dụng theme cho JDialog
     * @param dialog JDialog cần áp dụng theme
     */
    public static void applyTheme(JDialog dialog) {
        if (dialog != null) {
            SwingUtilities.updateComponentTreeUI(dialog);
            dialog.repaint();
        }
    }
    
    /**
     * Áp dụng theme cho Component bất kỳ
     * @param component Component cần áp dụng theme
     */
    public static void applyTheme(Component component) {
        if (component != null) {
            SwingUtilities.updateComponentTreeUI(component);
            component.repaint();
        }
    }
    
    /**
     * Chuyển đổi giữa Light và Dark theme
     */
    public static void toggleTheme() {
        Theme newTheme = (currentTheme == Theme.LIGHT) ? Theme.DARK : Theme.LIGHT;
        setTheme(newTheme);
    }
    
    /**
     * Lấy theme hiện tại
     * @return Theme hiện tại
     */
    public static Theme getCurrentTheme() {
        return currentTheme;
    }
    
    /**
     * Kiểm tra xem có phải dark theme không
     * @return true nếu là dark theme
     */
    public static boolean isDarkTheme() {
        return currentTheme == Theme.DARK || currentTheme == Theme.DARCULA;
    }
    
    /**
     * Cấu hình bổ sung cho giao diện hiện đại
     */
    public static void setupModernUI() {
        // Bật khả năng tùy chỉnh title bar
        System.setProperty("flatlaf.useWindowDecorations", "true");
        
        // Bật menu bar tùy chỉnh trên macOS
        System.setProperty("flatlaf.menuBarEmbedded", "true");
        
        // Tối ưu hiệu suất
        System.setProperty("sun.awt.noerasebackground", "true");
        
        // Cải thiện font rendering
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        // Cấu hình màu sắc tùy chỉnh
        UIManager.put("Button.arc", 8);
        UIManager.put("TextComponent.arc", 8);
        UIManager.put("CheckBox.arc", 4);
        UIManager.put("RadioButton.arc", 4);
    }
    
    /**
     * Cập nhật tất cả cửa sổ đang mở
     */
    private static void updateAllWindows() {
        for (Window window : Window.getWindows()) {
            SwingUtilities.updateComponentTreeUI(window);
            window.repaint();
        }
    }
    
    /**
     * Tạo JMenuBar với theme switcher
     * @return JMenuBar có sẵn menu chuyển đổi theme
     */
    public static JMenuBar createThemeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu themeMenu = new JMenu("Theme");
        
        // Tạo menu items cho từng theme
        JMenuItem lightItem = new JMenuItem("Light");
        JMenuItem darkItem = new JMenuItem("Dark");
        JMenuItem intellijItem = new JMenuItem("IntelliJ");
        JMenuItem darculaItem = new JMenuItem("Darcula");
        
        // Thêm action listeners
        lightItem.addActionListener(e -> setTheme(Theme.LIGHT));
        darkItem.addActionListener(e -> setTheme(Theme.DARK));
        intellijItem.addActionListener(e -> setTheme(Theme.INTELLIJ));
        darculaItem.addActionListener(e -> setTheme(Theme.DARCULA));
        
        // Thêm vào menu
        themeMenu.add(lightItem);
        themeMenu.add(darkItem);
        themeMenu.add(intellijItem);
        themeMenu.add(darculaItem);
        
        menuBar.add(themeMenu);
        return menuBar;
    }
}
