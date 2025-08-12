/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.team4.quanliquanmicay.View;

import com.team4.quanliquanmicay.util.XTheme;
import com.team4.quanliquanmicay.util.XDialog;
import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.Entity.Product;
import com.team4.quanliquanmicay.Entity.Bill;
import com.team4.quanliquanmicay.Entity.BillDetails;
import com.team4.quanliquanmicay.DAO.ProductDAO;
import com.team4.quanliquanmicay.Impl.ProductDAOImpl;
import com.team4.quanliquanmicay.DAO.BillDetailsDAO;
import com.team4.quanliquanmicay.Impl.BillDetailsDAOImpl;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.BoxLayout;
import com.team4.quanliquanmicay.Entity.Category;
import com.team4.quanliquanmicay.DAO.CategoryDAO;
import com.team4.quanliquanmicay.Impl.CategoryDAOImpl;
import javax.swing.SwingConstants;


/**
 *
 * @author HP
 */
public class OrderUI extends javax.swing.JFrame {

    // DAO objects
    private ProductDAO productDAO = new ProductDAOImpl();
    private BillDetailsDAO billDetailsDAO = new BillDetailsDAOImpl();
    private CategoryDAO categoryDAO = new CategoryDAOImpl();
    
    // Current bill
    private Bill currentBill;
    private BillUI parentDialog;
    
    // Cart items
    private List<CartItem> cartItems = new ArrayList<>();
    
    // Table models
    private DefaultTableModel cartTableModel;
    private DefaultTableModel productTableModel;
    
    // Search functionality
    private String currentSearch = "";
    private String currentCategory = ""; // Current selected category ID

    // For loading all products
    private List<Category> categories = new ArrayList<>();
    private Map<String, List<Product>> productsByCategory = new HashMap<>();
    private Map<String, JPanel> categoryPanels = new HashMap<>(); // Added for dynamic updates

    /**
     * Creates new form DatMonJDialog
     */
    public OrderUI(BillUI parent, Bill bill) {
        this.parentDialog = parent;
        this.currentBill = bill;
        
        this.setUndecorated(true);
        XTheme.applyFullTheme();
        initComponents();
        this.setLocationRelativeTo(null);
        
        setupTables();
        setupEventHandlers();
        loadProducts();
        updateCartDisplay();
        
        // Thiết lập placeholder cho ô tìm kiếm
        setupSearchPlaceholder();
        
        // Đảm bảo cập nhật lại layout sau khi dialog được hiển thị
        SwingUtilities.invokeLater(() -> {
            setupTabsAndFillProducts();
        });
    }
    
    /**
     * Constructor đơn giản cho testing
     */
    public OrderUI() {
        this(null, null);
    }
    
    /**
     * Thiết lập bảng
     */
    private void setupTables() {
        // Cart table
        cartTableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Tên món", "Số lượng", "Ghi chú"}
        ) {
            boolean[] canEdit = new boolean[] {false, false, false};
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        tblOrder.setModel(cartTableModel);
        
        // Product table
        productTableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Mã SP", "Tên món", "Đơn giá", "Trạng thái"}
        ) {
            boolean[] canEdit = new boolean[] {false, false, false, false};
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
    }
    
    /**
     * Thiết lập event handlers
     */
    private void setupEventHandlers() {
        // Nút Exit
        btnExit.addActionListener(e -> dispose());
        
        // Nút Xóa món
        btnRemove.addActionListener(e -> removeSelectedItem());
        
        // Nút Đặt món
        btnAdd.addActionListener(e -> placeOrder());
        
        // Search functionality
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                currentSearch = txtSearch.getText().toLowerCase();
                searchAndSwitchTab();
            }
        });
        

        
        // Tab change
        jTabbedPane1.addChangeListener(e -> {
            int selectedIndex = jTabbedPane1.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < categories.size()) {
                currentCategory = categories.get(selectedIndex).getCategory_id();
            }
            filterProducts();
        });
        
        // Component listener để tự động cập nhật kích thước button khi tabbed pane thay đổi kích thước
        jTabbedPane1.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Cập nhật lại tất cả các tab với kích thước mới
                SwingUtilities.invokeLater(() -> {
                    setupTabsAndFillProducts();
                });
            }
            
            @Override
            public void componentShown(ComponentEvent e) {
                // Đảm bảo cập nhật layout khi component được hiển thị
                SwingUtilities.invokeLater(() -> {
                    setupTabsAndFillProducts();
                });
            }
        });
    }
    

    
    /**
     * Load sản phẩm từ database
     */
    private void loadProducts() {
        try {
            // Load all categories
            categories = categoryDAO.findAll();
            
            // Load products for each category
            productsByCategory.clear();
            for (Category category : categories) {
                String sql = "SELECT * FROM PRODUCT WHERE category_id = ? AND is_available = 1";
                List<Product> products = XJdbc.executeQuery(sql, rs -> {
                    List<Product> productList = new ArrayList<>();
            try {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getString("product_id"));
                    product.setProductName(rs.getString("product_name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setDiscount(rs.getDouble("discount"));
                    product.setUnit(rs.getString("unit"));
                    product.setImage(rs.getString("image"));
                    product.setIsAvailable(rs.getInt("is_available"));
                    product.setNote(rs.getString("note"));
                    product.setCategoryId(rs.getString("category_id"));
                            productList.add(product);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
                    return productList;
                }, category.getCategory_id());
                
                productsByCategory.put(category.getCategory_id(), products);
            }
            
            // Setup tabs and fill products
            setupTabsAndFillProducts();
            
        } catch (Exception e) {
            XDialog.alert("Lỗi khi tải sản phẩm: " + e.getMessage());
            // Khởi tạo list rỗng nếu có lỗi
            categories = new ArrayList<>();
            productsByCategory.clear();
        }
    }
    

    
    /**
     * Cập nhật các nút sản phẩm
     */
    private void updateProductButtons() {
        // This method is now replaced by setupTabsAndFillProducts()
        // which is called from loadProducts()
    }

    /**
     * Tính toán kích thước button động dựa trên kích thước tabbed pane
     */
    private Dimension calculateButtonSize() {
        // Lấy kích thước của tabbed pane
        Dimension tabbedPaneSize = jTabbedPane1.getSize();
        
        // Nếu chưa có kích thước, sử dụng kích thước mặc định
        if (tabbedPaneSize.width <= 0 || tabbedPaneSize.height <= 0) {
            return new Dimension(120, 120); // Giảm kích thước mặc định
        }
        
        // Tính toán kích thước button
        // 3 button mỗi hàng, trừ đi padding và gap
        int availableWidth = tabbedPaneSize.width - 60; // Giảm padding để có nhiều không gian hơn
        int buttonWidth = Math.max(100, Math.min(150, availableWidth / 3 - 15)); // Giảm kích thước button
        int buttonHeight = buttonWidth; // Giữ tỷ lệ 1:1
        
        return new Dimension(buttonWidth, buttonHeight);
    }
    
    /**
     * Tạo panel item cho sản phẩm (button + label)
     */
    private JPanel createProductItemPanel(Product product, Dimension buttonSize, int imageSize) {
        // Create container panel for button and label
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3)); // Giảm padding hơn nữa
        itemPanel.setBackground(Color.WHITE);
        itemPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create button with calculated size
        JButton button = createProductButton(product, buttonSize, imageSize);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create label for product name
        JLabel label = new JLabel(product.getProductName());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Tăng font size lên 14, đậm
        label.setForeground(Color.BLACK);
        label.setMaximumSize(new Dimension(buttonSize.width + 10, 50)); // Giảm chiều cao label
        label.setPreferredSize(new Dimension(buttonSize.width + 10, 50));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add components to item panel
        itemPanel.add(button);
        itemPanel.add(Box.createVerticalStrut(3)); // Giảm khoảng cách hơn nữa
        itemPanel.add(label);
        
        return itemPanel;
    }
    
    /**
     * Setup tabs and fill products for each category.
     */
    private void setupTabsAndFillProducts() {
        jTabbedPane1.removeAll(); // Clear existing tabs
        categoryPanels.clear();

        // Tính toán kích thước button
        Dimension buttonSize = calculateButtonSize();
        int imageSize = Math.min(buttonSize.width - 10, buttonSize.height - 10);

        for (Category category : categories) {
            String categoryId = category.getCategory_id();
            List<Product> products = productsByCategory.get(categoryId);

            // Create main panel for this category
            JPanel categoryPanel = new JPanel();
            categoryPanel.setLayout(new BorderLayout()); // Sử dụng BorderLayout thay vì BoxLayout
            categoryPanel.setBackground(Color.WHITE);

            // Create product panel with GridLayout
            JPanel productPanel = new JPanel();
            productPanel.setLayout(new GridLayout(0, 3, 15, 20)); // Giảm gap hơn nữa
            productPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Giảm padding hơn nữa
            productPanel.setBackground(Color.WHITE);
            productPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Fill products into the panel
            for (Product product : products) {
                // Create product item panel using helper method
                JPanel itemPanel = createProductItemPanel(product, buttonSize, imageSize);
                
                // Add to product panel
                productPanel.add(itemPanel);
            }

            // Create scroll pane for the product panel
            JScrollPane scrollPane = new JScrollPane(productPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            scrollPane.getViewport().setBackground(Color.WHITE);
            scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            // Đặt kích thước phù hợp cho scroll pane
            Dimension tabbedPaneSize = jTabbedPane1.getSize();
            if (tabbedPaneSize.width > 0 && tabbedPaneSize.height > 0) {
                scrollPane.setPreferredSize(new Dimension(tabbedPaneSize.width - 40, tabbedPaneSize.height - 40));
                scrollPane.setMaximumSize(new Dimension(tabbedPaneSize.width - 40, tabbedPaneSize.height - 40));
            }

            // Add scroll pane to category panel
            categoryPanel.add(scrollPane, BorderLayout.CENTER);
            
            // Đảm bảo scroll pane có đủ không gian và không bị che
            scrollPane.setViewportBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

            // Add category panel to tabbed pane
            jTabbedPane1.addTab(category.getCategory_name(), categoryPanel);

            // Store the product panel for later updates
            categoryPanels.put(categoryId, productPanel);
        }

        // Set initial category
        if (!categories.isEmpty()) {
            currentCategory = categories.get(0).getCategory_id();
        }

        // Revalidate and repaint
        jTabbedPane1.revalidate();
        jTabbedPane1.repaint();
    }
    
    /**
     * Tìm kiếm sản phẩm và tự động chuyển tab
     */
    private void searchAndSwitchTab() {
        // Bỏ qua nếu đang hiển thị placeholder text
        if (currentSearch.isEmpty() || currentSearch.equals("nhập tên món ăn...")) {
            // Nếu không có từ khóa tìm kiếm, hiển thị tất cả sản phẩm
            filterProducts();
            return;
        }
        
        // Tìm kiếm sản phẩm trong tất cả các category
        String firstFoundCategoryId = null;
        boolean foundAnyProduct = false;
        int totalFoundProducts = 0;
        
        for (Category category : categories) {
            String categoryId = category.getCategory_id();
            List<Product> products = productsByCategory.get(categoryId);
            
            if (products != null) {
                List<Product> filteredProducts = filterProductsByName(products);
                if (!filteredProducts.isEmpty()) {
                    // Lưu category đầu tiên tìm thấy
                    if (firstFoundCategoryId == null) {
                        firstFoundCategoryId = categoryId;
                    }
                    foundAnyProduct = true;
                    totalFoundProducts += filteredProducts.size();
                    
                    // Cập nhật hiển thị sản phẩm đã lọc cho category này
                    updateProductButtonsForCategory(categoryId, filteredProducts);
                } else {
                    // Nếu không tìm thấy sản phẩm nào trong category này, hiển thị tất cả
                    updateProductButtonsForCategory(categoryId, products);
                }
            }
        }
        
        if (foundAnyProduct) {
            // Chuyển đến tab đầu tiên chứa sản phẩm tìm thấy
            if (firstFoundCategoryId != null) {
                switchToCategoryTab(firstFoundCategoryId);
            }
            
            // Hiển thị thông tin về kết quả tìm kiếm
            showSearchResultInfo(totalFoundProducts);
        } else {
            // Không tìm thấy sản phẩm nào, hiển thị thông báo
            XDialog.alert("Không tìm thấy món ăn: " + currentSearch);
            // Hiển thị tất cả sản phẩm trong tab hiện tại
            filterProducts();
        }
    }
    
    /**
     * Hiển thị thông tin về kết quả tìm kiếm
     */
    private void showSearchResultInfo(int totalFound) {
        // Có thể hiển thị thông tin này trong một label hoặc tooltip
        // Hiện tại chỉ in ra console để debug
        System.out.println("Tìm thấy " + totalFound + " món ăn phù hợp với: " + currentSearch);
    }
    
    /**
     * Thiết lập placeholder cho ô tìm kiếm
     */
    private void setupSearchPlaceholder() {
        // Thêm placeholder text cho ô tìm kiếm
        txtSearch.setToolTipText("Nhập tên món ăn để tìm kiếm (Enter để xóa)");
        
        // Thêm focus listener để hiển thị gợi ý
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtSearch.getText().equals("Nhập tên món ăn...")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(java.awt.Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setText("Nhập tên món ăn...");
                    txtSearch.setForeground(java.awt.Color.GRAY);
                }
            }
        });
        
        // Thiết lập placeholder text ban đầu
        txtSearch.setText("Nhập tên món ăn...");
        txtSearch.setForeground(java.awt.Color.GRAY);
        
        // Thêm key listener để xử lý Enter key
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (txtSearch.getText().equals("Nhập tên món ăn...")) {
                        txtSearch.setText("");
                        txtSearch.setForeground(java.awt.Color.BLACK);
                    } else {
                        txtSearch.setText("");
                        txtSearch.setForeground(java.awt.Color.BLACK);
                        currentSearch = "";
                        searchAndSwitchTab();
                    }
                }
            }
        });
    }
    
    /**
     * Chuyển đến tab của category cụ thể
     */
    private void switchToCategoryTab(String categoryId) {
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            if (category.getCategory_id().equals(categoryId)) {
                jTabbedPane1.setSelectedIndex(i);
                currentCategory = categoryId;
                break;
            }
        }
    }
    
    /**
     * Lọc sản phẩm theo tìm kiếm
     */
    private void filterProducts() {
        if (currentCategory.isEmpty() || !productsByCategory.containsKey(currentCategory)) {
            return;
        }
        
        List<Product> products = productsByCategory.get(currentCategory);
        List<Product> filteredProducts = filterProductsByName(products);
        
        // Update buttons with filtered products for current category
        updateProductButtonsForCategory(currentCategory, filteredProducts);
    }
    
    /**
     * Lọc sản phẩm theo tên
     */
    private List<Product> filterProductsByName(List<Product> products) {
        if (currentSearch.isEmpty()) {
            return products;
        }
        
        List<Product> filtered = new ArrayList<>();
        for (Product product : products) {
            if (product.getProductName().toLowerCase().contains(currentSearch)) {
                filtered.add(product);
            }
        }
        return filtered;
    }
    
    /**
     * Cập nhật nút với sản phẩm đã lọc cho category cụ thể
     */
    private void updateProductButtonsForCategory(String categoryId, List<Product> products) {
        // Find the panel for this category
        JPanel targetPanel = categoryPanels.get(categoryId);
        
        if (targetPanel == null) {
            return;
        }
        
        // Tính toán kích thước button
        Dimension buttonSize = calculateButtonSize();
        int imageSize = Math.min(buttonSize.width - 10, buttonSize.height - 10);
        
        // Clear existing components
        targetPanel.removeAll();
        
        // Create buttons and labels for each product
        for (Product product : products) {
            // Create product item panel using helper method
            JPanel itemPanel = createProductItemPanel(product, buttonSize, imageSize);
            
            // Add to target panel
            targetPanel.add(itemPanel);
        }
        
        // Revalidate and repaint
        targetPanel.revalidate();
        targetPanel.repaint();
    }
    
    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    private void addToCart(Product product) {
        if (product == null) {
            XDialog.alert("Sản phẩm không tồn tại!");
            return;
        }
        
        if (!product.isAvailable()) {
            XDialog.alert("Sản phẩm hiện không có sẵn!");
            return;
        }
        
        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId().equals(product.getProductId())) {
                item.setQuantity(item.getQuantity() + 1);
                updateCartDisplay();
                return;
            }
        }
        
        // Thêm sản phẩm mới vào giỏ hàng
        cartItems.add(new CartItem(product, 1));
        updateCartDisplay();
    }
    
    /**
     * Xóa món đã chọn khỏi giỏ hàng
     */
    private void removeSelectedItem() {
        int selectedRow = tblOrder.getSelectedRow();
        if (selectedRow == -1) {
            XDialog.alert("Vui lòng chọn món cần xóa!");
            return;
        }
        
        cartItems.remove(selectedRow);
        updateCartDisplay();
    }
    
    /**
     * Cập nhật hiển thị giỏ hàng
     */
    private void updateCartDisplay() {
        cartTableModel.setRowCount(0);
        int totalQuantity = 0;
        for (CartItem item : cartItems) {
            Object[] row = {
                item.getProduct().getProductName(),
                item.getQuantity(),
                item.getNote()
            };
            cartTableModel.addRow(row);
            totalQuantity += item.getQuantity();
        }
        jLabel3.setText(String.valueOf(totalQuantity));
    }
    
    /**
     * Đặt món
     */
    private void placeOrder() {
        try {
            // Validate cart không rỗng
            if (cartItems.isEmpty()) {
                XDialog.warning("Giỏ hàng trống! Vui lòng chọn món ăn.", "Cảnh báo");
                return;
            }
            
            // Validate current bill
            if (currentBill == null) {
                XDialog.error("Không tìm thấy thông tin hóa đơn!", "Lỗi");
                return;
            }
            
            // Tính tổng tiền
            final double totalAmount = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
            
            // Hiển thị dialog xác nhận đặt món
            String[] options = {"Đặt món", "Hủy"};
            Runnable[] actions = {
                () -> {
                    try {
                        // Lưu bill details
                        for (CartItem item : cartItems) {
                            BillDetails detail = new BillDetails();
                            detail.setBill_id(currentBill.getBill_id());
                            detail.setProduct_id(item.getProduct().getProductId());
                            detail.setAmount(item.getQuantity());
                            detail.setPrice(item.getProduct().getPrice());
                            detail.setDiscount(item.getProduct().getDiscount());
                            
                            billDetailsDAO.create(detail);
                        }
                        
                        // Cập nhật tổng tiền cho bill
                        currentBill.setTotal_amount(totalAmount);
                        // TODO: Cập nhật bill trong database
                        
                        XDialog.success("Đặt món thành công! Tổng tiền: " + formatCurrency(totalAmount), "Thành công");
                        
                        // Clear cart và đóng dialog
                        cartItems.clear();
                        updateCartDisplay();
                        this.dispose();
                        
                        // Refresh parent dialog nếu có
                        if (parentDialog != null) {
                            // TODO: Implement refresh method in BillUI
                            // parentDialog.refreshBillDetails();
                        }
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                        XDialog.error("Lỗi khi lưu đơn hàng: " + e.getMessage(), "Lỗi hệ thống");
                    }
                },
                () -> {
                    // Hủy - không làm gì
                }
            };
            
            XDialog.showCustomDialog(this, "Xác nhận đặt món", 
                "Bạn có chắc chắn muốn đặt " + cartItems.size() + " món với tổng tiền " + formatCurrency(totalAmount) + "?",
                options, actions);
                
        } catch (Exception e) {
            e.printStackTrace();
            XDialog.error("Lỗi khi đặt món: " + e.getMessage(), "Lỗi hệ thống");
        }
    }
    

    
    /**
     * Format tiền tệ
     */
    private String formatCurrency(double amount) {
        return String.format("%,.0f VNĐ", amount);
    }
    
    /**
     * Load hình ảnh sản phẩm một cách linh hoạt
     */
    private ImageIcon loadProductImage(String imageName, int size) {
        if (imageName == null || imageName.trim().isEmpty()) {
            return getDefaultImage(size);
        }
        
        // Debug: Kiểm tra hình ảnh
        debugImageLoading(imageName);
        
        // Thử các đường dẫn khác nhau - giống với MonAnJDialog
        String[] paths = {
            "/icons_and_images/" + imageName,
            "/icons_and_images/product/" + imageName,
            "/icons_and_images/product/mi/" + imageName,
            "/icons_and_images/product/drink/" + imageName,
            "/icons_and_images/product/more/" + imageName,
            "/images/product/" + imageName,
            "/images/" + imageName
        };
        
        for (String path : paths) {
            try {
                java.net.URL imageURL = getClass().getResource(path);
                if (imageURL != null) {
                    javax.swing.ImageIcon originalIcon = new javax.swing.ImageIcon(imageURL);
                    
                    // Kiểm tra nếu hình ảnh load thành công
                    if (originalIcon.getIconWidth() > 0 && originalIcon.getIconHeight() > 0) {
                        java.awt.Image scaledImage = originalIcon.getImage().getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
                        return new javax.swing.ImageIcon(scaledImage);
                    }
                }
            } catch (Exception e) {
                // Tiếp tục thử đường dẫn tiếp theo
            }
        }
        
        // Nếu không tìm thấy, sử dụng hình mặc định
        return getDefaultImage(size);
    }
    
    /**
     * Lấy hình ảnh mặc định (chỉ dùng icons8-plate-50.png)
     */
    private ImageIcon getDefaultImage(int size) {
        try {
            ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/icons_and_images/icons8-plate-50.png"));
            if (defaultIcon.getImage() != null) {
                Image img = defaultIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        } catch (Exception e) {
            // Nếu vẫn lỗi, trả về icon rỗng
        }
        return new ImageIcon();
    }
    
    /**
     * Tạo button sản phẩm với hiệu ứng đẹp
     */
    private JButton createProductButton(Product product, Dimension buttonSize, int imageSize) {
        JButton button = new JButton();
        button.setPreferredSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(false);
        
        // Thêm viền mặc định với màu đẹp hơn
        button.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2));
        
        // Set button image với padding
        ImageIcon icon = loadProductImage(product.getImage(), imageSize);
        button.setIcon(icon);
        
        // Set cursor pointer
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 3));
                button.setBackground(new Color(245, 250, 255)); // Light blue background
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2));
                button.setBackground(null);
            }
        });
        
        // Add action listener
        button.addActionListener(e -> showQuantityDialog(product));
        
        return button;
    }
    
    /**
     * Inner class cho giỏ hàng
     */
    private static class CartItem {
        private Product product;
        private int quantity;
        private String note;
        
        public CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
            this.note = "";
        }
        
        public Product getProduct() { return product; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public String getNote() { return note; }
        public void setNote(String note) { this.note = note; }
    }

    /**
     * Debug: Kiểm tra xem hình ảnh có tồn tại không
     */
    private void debugImageLoading(String imageName) {
        if (imageName == null || imageName.trim().isEmpty()) {
            System.out.println("DEBUG: Image name is null or empty");
            return;
        }
        
        String[] paths = {
            "/icons_and_images/" + imageName,
            "/icons_and_images/product/" + imageName,
            "/icons_and_images/product/mi/" + imageName,
            "/icons_and_images/product/drink/" + imageName,
            "/icons_and_images/product/more/" + imageName,
            "/images/product/" + imageName,
            "/images/" + imageName
        };
        
        System.out.println("DEBUG: Checking image: " + imageName);
        for (String path : paths) {
            java.net.URL imageURL = getClass().getResource(path);
            if (imageURL != null) {
                System.out.println("DEBUG: Found image at: " + path);
                return;
            }
        }
        System.out.println("DEBUG: Image not found in any path");
    }

    /**
     * Hiển thị dialog nhập số lượng và ghi chú
     */
    private void showQuantityDialog(Product product) {
        // Tạo dialog
        JDialog dialog = new JDialog(this, "Thêm vào giỏ hàng", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(350, 280);
        dialog.setLocationRelativeTo(this);
        
        // Panel chính
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Thông tin sản phẩm
        JLabel productLabel = new JLabel("Sản phẩm: " + product.getProductName());
        productLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        productLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(productLabel);
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Số lượng
        JLabel quantityLabel = new JLabel("Số lượng:");
        quantityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        // Giảm kích thước spinner
        quantitySpinner.setPreferredSize(new Dimension(80, 25));
        quantitySpinner.setMaximumSize(new Dimension(80, 25));
        
        JPanel quantityPanel = new JPanel();
        quantityPanel.setLayout(new BoxLayout(quantityPanel, BoxLayout.X_AXIS));
        quantityPanel.add(quantityLabel);
        quantityPanel.add(Box.createHorizontalStrut(10));
        quantityPanel.add(quantitySpinner);
        quantityPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(quantityPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Ghi chú
        JLabel noteLabel = new JLabel("Ghi chú:");
        noteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextArea noteArea = new JTextArea(3, 15);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        JScrollPane noteScrollPane = new JScrollPane(noteArea);
        noteScrollPane.setPreferredSize(new Dimension(250, 60));
        noteScrollPane.setMaximumSize(new Dimension(250, 60));
        noteScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(noteLabel);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(noteScrollPane);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        JButton addButton = new JButton("Thêm vào giỏ");
        JButton cancelButton = new JButton("Hủy");
        
        // Giảm kích thước buttons
        addButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setPreferredSize(new Dimension(80, 30));
        
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(cancelButton);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(buttonPanel);
        
        addButton.addActionListener(e -> {
            int quantity = (Integer) quantitySpinner.getValue();
            String note = noteArea.getText().trim();
            
            // Thêm vào giỏ hàng với số lượng và ghi chú
            addToCartWithQuantity(product, quantity, note);
            dialog.dispose();
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
    
    /**
     * Thêm sản phẩm vào giỏ hàng với số lượng và ghi chú
     */
    private void addToCartWithQuantity(Product product, int quantity, String note) {
        if (product == null) {
            XDialog.alert("Sản phẩm không tồn tại!");
            return;
        }
        
        if (!product.isAvailable()) {
            XDialog.alert("Sản phẩm hiện không có sẵn!");
            return;
        }
        
        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId().equals(product.getProductId())) {
                item.setQuantity(item.getQuantity() + quantity);
                // Cập nhật ghi chú nếu có
                if (!note.isEmpty()) {
                    item.setNote(note);
                }
                updateCartDisplay();
                XDialog.alert("Đã cập nhật số lượng sản phẩm trong giỏ hàng!");
                return;
            }
        }
        
        // Thêm sản phẩm mới vào giỏ hàng
        CartItem newItem = new CartItem(product, quantity);
        if (!note.isEmpty()) {
            newItem.setNote(note);
        }
        cartItems.add(newItem);
        updateCartDisplay();
        XDialog.alert("Đã thêm sản phẩm vào giỏ hàng!");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblOrder = new javax.swing.JTable();
        btnRemove = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnExit = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        jButton4.setText("jButton4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(204, 164, 133));

        jPanel4.setBackground(new java.awt.Color(204, 164, 133));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons_and_images/Search.png"))); // NOI18N

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jScrollPane4.setPreferredSize(new java.awt.Dimension(660, 393));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 665, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 435, Short.MAX_VALUE)
        );

        jScrollPane4.setViewportView(jPanel2);

        jTabbedPane1.addTab("Mì cay", jScrollPane4);
        jTabbedPane1.addTab("Nước", jScrollPane5);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(225, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(223, 223, 223))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(204, 164, 133));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        tblOrder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        tblOrder.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên món", "Số lượng", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrder.getTableHeader().setResizingAllowed(false);
        tblOrder.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tblOrder);
        if (tblOrder.getColumnModel().getColumnCount() > 0) {
            tblOrder.getColumnModel().getColumn(0).setResizable(false);
            tblOrder.getColumnModel().getColumn(1).setMinWidth(40);
            tblOrder.getColumnModel().getColumn(1).setMaxWidth(60);
            tblOrder.getColumnModel().getColumn(2).setResizable(false);
        }

        btnRemove.setBackground(new java.awt.Color(204, 204, 204));
        btnRemove.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRemove.setForeground(new java.awt.Color(153, 153, 153));
        btnRemove.setText("Xóa Món");
        btnRemove.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        btnExit.setBackground(new java.awt.Color(204, 204, 204));
        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnExit.setForeground(new java.awt.Color(153, 153, 153));
        btnExit.setText("Thoát");
        btnExit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        btnAdd.setBackground(new java.awt.Color(204, 204, 204));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(153, 153, 153));
        btnAdd.setText("Thêm món");
        btnAdd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tổng số lượng :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("0");

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemove, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemove)
                    .addComponent(btnAdd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(134, 39, 43));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Đặt Món");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(438, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(418, 418, 418))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 498, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addGap(0, 58, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddActionPerformed

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
            java.util.logging.Logger.getLogger(OrderUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderUI(null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblOrder;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
