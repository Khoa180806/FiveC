package com.team4.quanliquanmicay.util;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class XTheme {
    
    // Định nghĩa bảng màu cho Light Theme
    private static final Color LIGHT_BACKGROUND = new Color(255, 255, 255);
    private static final Color LIGHT_SURFACE = new Color(248, 249, 250);
    private static final Color LIGHT_ACCENT = new Color(134, 39, 43); // Đỏ mì cay #86272B
    private static final Color LIGHT_BE = new Color(204, 164, 133); // Be #CCA485
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
        // Màu nền chính cho button
        UIManager.put("Button.background", LIGHT_BE);
        
        // Hiệu ứng hover - màu đậm hơn một chút
        UIManager.put("Button.hoverBackground", new Color(194, 154, 123)); // Be đậm hơn khi hover
        
        // Hiệu ứng click/pressed - màu đậm nhất
        UIManager.put("Button.pressedBackground", new Color(184, 144, 113)); // Be đậm nhất khi click
        
        // Màu viền và focus
        UIManager.put("Button.borderColor", LIGHT_ACCENT);
        UIManager.put("Button.focusedBorderColor", LIGHT_ACCENT);
        
        // Bo góc hiện đại
        UIManager.put("Button.arc", 12);
        
        // Thêm hiệu ứng shadow nhẹ khi hover
        UIManager.put("Button.shadowColor", new Color(0, 0, 0, 20));
        UIManager.put("Button.hoverShadowColor", new Color(0, 0, 0, 30));
        
        // Hiệu ứng transition mượt mà
        UIManager.put("Button.animationDuration", 150);
        
        // === INPUT FIELDS ===
        UIManager.put("TextField.background", new Color(252, 250, 248)); // Nền nhẹ với tông be
        UIManager.put("TextArea.background", new Color(252, 250, 248));
        UIManager.put("ComboBox.background", new Color(252, 250, 248));
        UIManager.put("TextField.borderColor", LIGHT_BE);
        UIManager.put("TextField.focusedBorderColor", LIGHT_ACCENT);
        UIManager.put("TextArea.borderColor", LIGHT_BE);
        UIManager.put("ComboBox.borderColor", LIGHT_BE);
        
        // === TABLE STYLING ===
        UIManager.put("Table.background", LIGHT_BACKGROUND);
        UIManager.put("Table.alternateRowColor", new Color(252, 250, 248)); // Tông be nhẹ
        UIManager.put("Table.selectionBackground", new Color(134, 39, 43, 20)); // Đỏ mì cay với độ trong suốt
        UIManager.put("Table.selectionForeground", LIGHT_TEXT_PRIMARY);
        UIManager.put("Table.gridColor", LIGHT_BE);
        UIManager.put("TableHeader.background", LIGHT_ACCENT);
        UIManager.put("TableHeader.foreground", Color.WHITE);
        UIManager.put("TableHeader.separatorColor", LIGHT_BE);
        
        // === MENU STYLING ===
        UIManager.put("MenuBar.background", LIGHT_ACCENT);
        UIManager.put("MenuBar.borderColor", LIGHT_BE);
        UIManager.put("Menu.background", LIGHT_ACCENT);
        UIManager.put("Menu.foreground", Color.WHITE);
        UIManager.put("Menu.hoverBackground", new Color(154, 49, 53)); // Đỏ đậm hơn khi hover
        UIManager.put("MenuItem.background", LIGHT_BACKGROUND);
        UIManager.put("MenuItem.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("MenuItem.hoverBackground", new Color(252, 250, 248)); // Tông be nhẹ khi hover
        
        // === SCROLL BAR ===
        UIManager.put("ScrollBar.background", new Color(252, 250, 248));
        UIManager.put("ScrollBar.thumb", LIGHT_BE);
        UIManager.put("ScrollBar.hoverThumbColor", new Color(184, 144, 113)); // Be đậm hơn
        UIManager.put("ScrollBar.pressedThumbColor", LIGHT_ACCENT);
        UIManager.put("ScrollBar.width", 14); // Rộng hơn một chút
        
        // === TABS ===
        UIManager.put("TabbedPane.background", new Color(252, 250, 248));
        UIManager.put("TabbedPane.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("TabbedPane.selectedBackground", LIGHT_ACCENT);
        UIManager.put("TabbedPane.selectedForeground", Color.WHITE);
        UIManager.put("TabbedPane.hoverColor", LIGHT_BE); // Màu be khi hover
        
        // === PROGRESS BAR ===
        UIManager.put("ProgressBar.background", new Color(252, 250, 248));
        UIManager.put("ProgressBar.foreground", LIGHT_ACCENT);
        UIManager.put("ProgressBar.selectionBackground", LIGHT_BE);
        UIManager.put("ProgressBar.selectionForeground", LIGHT_TEXT_PRIMARY);
        
        // === TOOLTIP ===
        UIManager.put("ToolTip.background", new Color(252, 250, 248));
        UIManager.put("ToolTip.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("ToolTip.border", new LineBorder(LIGHT_BE, 2));
        
        // === LIST & TREE ===
        UIManager.put("List.background", LIGHT_BACKGROUND);
        UIManager.put("List.selectionBackground", new Color(134, 39, 43, 20)); // Đỏ mì cay với độ trong suốt
        UIManager.put("List.selectionForeground", LIGHT_TEXT_PRIMARY);
        UIManager.put("Tree.background", LIGHT_BACKGROUND);
        UIManager.put("Tree.selectionBackground", new Color(134, 39, 43, 20)); // Đỏ mì cay với độ trong suốt
        UIManager.put("Tree.selectionForeground", LIGHT_TEXT_PRIMARY);
        
        // === CHECKBOX & RADIO ===
        UIManager.put("CheckBox.background", LIGHT_BACKGROUND);
        UIManager.put("CheckBox.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("CheckBox.focusedBorderColor", LIGHT_ACCENT);
        UIManager.put("CheckBox.iconColor", LIGHT_ACCENT);
        UIManager.put("RadioButton.background", LIGHT_BACKGROUND);
        UIManager.put("RadioButton.foreground", LIGHT_TEXT_PRIMARY);
        UIManager.put("RadioButton.focusedBorderColor", LIGHT_ACCENT);
        UIManager.put("RadioButton.iconColor", LIGHT_ACCENT);
        
        // === BORDERS ===
        UIManager.put("Component.borderColor", LIGHT_BE);
        UIManager.put("Component.focusedBorderColor", LIGHT_ACCENT);
        UIManager.put("Component.arc", 8); // Bo góc chung hiện đại hơn
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
    
    /**
     * Tùy chỉnh giao diện cho tất cả các Dialog và OptionPane
     * Làm cho dialog trông đẹp và nhất quán với theme
     */
    public static void customizeDialogs() {
        try {
            // === OPTION PANE STYLING ===
            UIManager.put("OptionPane.background", LIGHT_BACKGROUND);
            UIManager.put("OptionPane.foreground", LIGHT_TEXT_PRIMARY);
            UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 12));
            UIManager.put("OptionPane.border", BorderFactory.createEmptyBorder(20, 20, 15, 20));
            
            // === DIALOG STYLING ===
            UIManager.put("Dialog.background", LIGHT_BACKGROUND);
            UIManager.put("Dialog.foreground", LIGHT_TEXT_PRIMARY);
            
            // === BUTTON TRONG DIALOG ===
            UIManager.put("OptionPane.buttonMinimumWidth", 85);
            UIManager.put("OptionPane.buttonAreaBorder", BorderFactory.createEmptyBorder(15, 0, 0, 0));
            
            // === ICON STYLING ===
            UIManager.put("OptionPane.informationIcon", createCustomIcon("info"));
            UIManager.put("OptionPane.warningIcon", createCustomIcon("warning"));
            UIManager.put("OptionPane.errorIcon", createCustomIcon("error"));
            UIManager.put("OptionPane.questionIcon", createCustomIcon("question"));
            
            System.out.println("✅ Đã tùy chỉnh giao diện Dialog thành công");
            
        } catch (Exception e) {
            System.err.println("⚠️ Lỗi khi tùy chỉnh Dialog: " + e.getMessage());
        }
    }
    
    /**
     * Tạo custom icon cho dialog
     */
    private static Icon createCustomIcon(String type) {
        Color iconColor;
        String symbol;
        
        switch (type.toLowerCase()) {
            case "info":
                iconColor = new Color(52, 144, 220);
                symbol = "ℹ";
                break;
            case "warning":
                iconColor = LIGHT_WARNING;
                symbol = "⚠";
                break;
            case "error":
                iconColor = LIGHT_ERROR;
                symbol = "✖";
                break;
            case "question":
                iconColor = new Color(102, 0, 0);
                symbol = "?";
                break;
            default:
                iconColor = LIGHT_TEXT_SECONDARY;
                symbol = "●";
        }
        
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Vẽ hình tròn nền
                g2.setColor(new Color(iconColor.getRed(), iconColor.getGreen(), iconColor.getBlue(), 30));
                g2.fillOval(x, y, getIconWidth(), getIconHeight());
                
                // Vẽ viền
                g2.setColor(iconColor);
                g2.setStroke(new BasicStroke(2f));
                g2.drawOval(x + 1, y + 1, getIconWidth() - 2, getIconHeight() - 2);
                
                // Vẽ symbol
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
     * Hiển thị thông báo đẹp với theme tùy chỉnh
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
     * Tạo custom JDialog với theme đẹp
     */
    public static JDialog createStyledDialog(String title, int width, int height) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setSize(width, height);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // Áp dụng theme
        dialog.getContentPane().setBackground(LIGHT_BACKGROUND);
        
        return dialog;
    }
    
    /**
     * Tạo button với styling đẹp cho dialog
     */
    public static JButton createDialogButton(String text, Color bgColor, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(90, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect với màu tùy chỉnh
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = bgColor;
            Color hoverColor = createHoverColor(bgColor);
            Color pressedColor = createPressedColor(bgColor);
            
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
        
        if (action != null) {
            button.addActionListener(action);
        }
        
        return button;
    }
    
    /**
     * Tạo màu hover từ màu gốc
     */
    private static Color createHoverColor(Color originalColor) {
        // Làm đậm màu lên 15%
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
        // Làm đậm màu lên 25%
        return new Color(
            Math.min(255, (int)(originalColor.getRed() * 0.75)),
            Math.min(255, (int)(originalColor.getGreen() * 0.75)),
            Math.min(255, (int)(originalColor.getBlue() * 0.75))
        );
    }
    
    /**
     * Tạo button với hiệu ứng hover và click tùy chỉnh
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
        
        // Tạo màu hover và pressed từ màu gốc
        Color hoverColor = createHoverColor(bgColor);
        Color pressedColor = createPressedColor(bgColor);
        
        // Hiệu ứng hover và click
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
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
        
        if (action != null) {
            button.addActionListener(action);
        }
        
        return button;
    }
    
    /**
     * Tạo button với theme mì cay (màu đỏ mì cay)
     */
    public static JButton createMiyCayButton(String text, ActionListener action) {
        return createCustomButton(text, LIGHT_ACCENT, Color.WHITE, action);
    }
    
    /**
     * Tạo button với theme be
     */
    public static JButton createBeButton(String text, ActionListener action) {
        return createCustomButton(text, LIGHT_BE, LIGHT_TEXT_PRIMARY, action);
    }
    
    /**
     * Tạo button thành công (màu xanh)
     */
    public static JButton createSuccessButton(String text, ActionListener action) {
        return createCustomButton(text, LIGHT_SUCCESS, Color.WHITE, action);
    }
    
    /**
     * Tạo button cảnh báo (màu vàng)
     */
    public static JButton createWarningButton(String text, ActionListener action) {
        return createCustomButton(text, LIGHT_WARNING, LIGHT_TEXT_PRIMARY, action);
    }
    
    /**
     * Tạo button lỗi (màu đỏ)
     */
    public static JButton createErrorButton(String text, ActionListener action) {
        return createCustomButton(text, LIGHT_ERROR, Color.WHITE, action);
    }
    
    /**
     * Áp dụng hiệu ứng hover và click cho button hiện có
     * @param button Button cần áp dụng hiệu ứng
     * @param bgColor Màu nền chính
     */
    public static void applyHoverEffect(JButton button, Color bgColor) {
        Color hoverColor = createHoverColor(bgColor);
        Color pressedColor = createPressedColor(bgColor);
        
        // Xóa các listener cũ nếu có
        for (java.awt.event.MouseListener listener : button.getMouseListeners()) {
            button.removeMouseListener(listener);
        }
        
        // Thêm hiệu ứng mới
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
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
        
        // Thiết lập cursor
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    /**
     * Áp dụng hiệu ứng hover và click cho tất cả button trong container
     * @param container Container chứa các button
     */
    public static void applyHoverEffectToAllButtons(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                Color bgColor = button.getBackground();
                applyHoverEffect(button, bgColor);
            } else if (comp instanceof Container) {
                applyHoverEffectToAllButtons((Container) comp);
            }
        }
    }
        
    /**
     * Áp dụng theme đầy đủ bao gồm cả dialog
     * Theme hiện đại với màu chủ đạo đỏ mì cay và be
     */
    public static void applyFullTheme() {
        applyLightTheme();
        customizeDialogs();
        
        // Thêm các tùy chỉnh bổ sung cho theme hiện đại
        try {
            // Tùy chỉnh thêm cho các component đặc biệt
            UIManager.put("Panel.background", new Color(252, 250, 248)); // Nền nhẹ với tông be
            UIManager.put("Frame.background", new Color(252, 250, 248));
            UIManager.put("Dialog.background", new Color(252, 250, 248));
            
            // Tùy chỉnh cho các label quan trọng
            UIManager.put("Label.font", new Font("Segoe UI", Font.BOLD, 13));
            
            // Tùy chỉnh cho các button đặc biệt
            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 12));
            
            // Áp dụng hiệu ứng hover cho tất cả button hiện có
            SwingUtilities.invokeLater(() -> {
                for (Window window : Window.getWindows()) {
                    applyHoverEffectToAllButtons(window);
                }
            });
            
            System.out.println("🎨 Đã áp dụng thành công Modern Mì Cay Theme với màu đỏ #86272B và be #CCA485");
            System.out.println("✨ Hiệu ứng hover và click đã được áp dụng cho tất cả button");
            
        } catch (Exception e) {
            System.err.println("⚠️ Lỗi khi áp dụng theme bổ sung: " + e.getMessage());
        }
    }
}
