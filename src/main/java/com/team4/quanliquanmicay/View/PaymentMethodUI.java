package com.team4.quanliquanmicay.View;

import com.team4.quanliquanmicay.DAO.PaymentMethodDAO;
import com.team4.quanliquanmicay.Entity.PaymentMethod;
import com.team4.quanliquanmicay.Impl.PaymentMethodDAOImpl;
import com.team4.quanliquanmicay.util.XDialog;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * UI chọn phương thức thanh toán
 */
public class PaymentMethodUI extends JDialog {
    
    private final PaymentMethodDAO paymentMethodDAO;
    private PaymentMethod selectedPaymentMethod;
    private final double totalAmount;
    
    // UI Components
    private JLabel lblTitle;
    private JLabel lblAmount;
    private JLabel lblAmountValue;
    private JLabel lblPaymentMethod;
    private JComboBox<PaymentMethod> cboPaymentMethod;
    private JTextArea txtNote;
    private JButton btnConfirm;
    private JButton btnCancel;
    
    public PaymentMethodUI(Frame parent, double totalAmount) {
        super(parent, "Chọn Phương Thức Thanh Toán", true);
        this.totalAmount = totalAmount;
        this.paymentMethodDAO = new PaymentMethodDAOImpl();
        this.selectedPaymentMethod = null;
        
        initComponents();
        loadPaymentMethods();
        setupEventHandlers();
        
        // Thiết lập dialog
        this.setSize(450, 350);
        this.setLocationRelativeTo(parent);
        this.setResizable(false);
    }
    
    private void initComponents() {
        // Thiết lập layout chính
        setLayout(new BorderLayout());
        
        // Panel tiêu đề
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(134, 39, 43));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        lblTitle = new JLabel("CHỌN PHƯƠNG THỨC THANH TOÁN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle);
        
        // Panel nội dung chính
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(204, 164, 133));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        
        // Thông tin tổng tiền
        lblAmount = new JLabel("Tổng tiền thanh toán:");
        lblAmount.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblAmount.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(lblAmount, gbc);
        
        lblAmountValue = new JLabel(formatCurrency(totalAmount));
        lblAmountValue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblAmountValue.setForeground(new Color(255, 215, 0)); // Màu vàng
        lblAmountValue.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblAmountValue.setOpaque(true);
        lblAmountValue.setBackground(new Color(255, 255, 255, 50));
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(lblAmountValue, gbc);
        
        // Phương thức thanh toán
        lblPaymentMethod = new JLabel("Chọn phương thức thanh toán:");
        lblPaymentMethod.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPaymentMethod.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(lblPaymentMethod, gbc);
        
        cboPaymentMethod = new JComboBox<>();
        cboPaymentMethod.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cboPaymentMethod.setPreferredSize(new Dimension(250, 30));
        cboPaymentMethod.setRenderer(new PaymentMethodRenderer());
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(cboPaymentMethod, gbc);
        
        // Ghi chú
        JLabel lblNote = new JLabel("Ghi chú (tùy chọn):");
        lblNote.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNote.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(lblNote, gbc);
        
        txtNote = new JTextArea(3, 20);
        txtNote.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtNote.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        txtNote.setLineWrap(true);
        txtNote.setWrapStyleWord(true);
        
        JScrollPane scrollNote = new JScrollPane(txtNote);
        scrollNote.setPreferredSize(new Dimension(250, 70));
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(scrollNote, gbc);
        
        // Panel buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(204, 164, 133));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        
        btnConfirm = new JButton("Xác nhận thanh toán");
        btnConfirm.setBackground(new Color(204, 255, 204));
        btnConfirm.setForeground(new Color(0, 153, 0));
        btnConfirm.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConfirm.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btnConfirm.setPreferredSize(new Dimension(150, 35));
        
        btnCancel = new JButton("Hủy");
        btnCancel.setBackground(new Color(255, 179, 179));
        btnCancel.setForeground(new Color(153, 0, 0));
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btnCancel.setPreferredSize(new Dimension(100, 35));
        
        buttonPanel.add(btnConfirm);
        buttonPanel.add(btnCancel);
        
        // Thêm các panel vào dialog
        add(titlePanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadPaymentMethods() {
        try {
            List<PaymentMethod> paymentMethods = paymentMethodDAO.findAll();
            
            // Chỉ hiển thị các phương thức đang hoạt động
            for (PaymentMethod method : paymentMethods) {
                if (method.getIs_enable() == 1) {
                    cboPaymentMethod.addItem(method);
                }
            }
            
            if (cboPaymentMethod.getItemCount() == 0) {
                XDialog.alert("Không có phương thức thanh toán nào khả dụng!", "Thông báo");
                this.dispose();
            } else {
                // Mặc định chọn "Tiền mặt" (ID = 1) nếu có
                for (int i = 0; i < cboPaymentMethod.getItemCount(); i++) {
                    PaymentMethod method = (PaymentMethod) cboPaymentMethod.getItemAt(i);
                    if (method.getPayment_method_id() == 1) {
                        cboPaymentMethod.setSelectedIndex(i);
                        break;
                    }
                }
            }
            
        } catch (Exception e) {
            XDialog.alert("Lỗi khi tải phương thức thanh toán: " + e.getMessage(), "Lỗi");
            this.dispose();
        }
    }
    
    private void setupEventHandlers() {
        btnConfirm.addActionListener(evt -> handleConfirm());
        btnCancel.addActionListener(evt -> handleCancel());
        
        // Hiển thị thông tin chi tiết khi chọn phương thức
        cboPaymentMethod.addActionListener(evt -> updatePaymentMethodInfo());
    }
    
    private void handleConfirm() {
        if (cboPaymentMethod.getSelectedItem() == null) {
            XDialog.alert("Vui lòng chọn phương thức thanh toán!", "Thông báo");
            return;
        }
        
        selectedPaymentMethod = (PaymentMethod) cboPaymentMethod.getSelectedItem();
        
        // Hiển thị thông tin xác nhận cuối cùng
        StringBuilder message = new StringBuilder();
        message.append("XÁC NHẬN THANH TOÁN\n\n");
        message.append("Tổng tiền: ").append(formatCurrency(totalAmount)).append("\n");
        message.append("Phương thức: ").append(selectedPaymentMethod.getMethod_name()).append("\n");
        
        String note = txtNote.getText().trim();
        if (!note.isEmpty()) {
            message.append("Ghi chú: ").append(note).append("\n");
        }
        
        message.append("\nBạn có chắc chắn muốn thanh toán không?");
        
        if (XDialog.confirm(message.toString(), "Xác nhận thanh toán")) {
            this.dispose();
        }
    }
    
    private void handleCancel() {
        if (XDialog.confirm("Bạn có chắc chắn muốn hủy thanh toán không?", "Xác nhận hủy")) {
            selectedPaymentMethod = null;
            this.dispose();
        }
    }
    
    private void updatePaymentMethodInfo() {
        PaymentMethod method = (PaymentMethod) cboPaymentMethod.getSelectedItem();
        if (method != null) {
            // Cập nhật ghi chú mặc định theo phương thức
            switch (method.getPayment_method_id()) {
                case 1: // Tiền mặt
                    txtNote.setText("Thanh toán bằng tiền mặt tại quầy");
                    break;
                case 2: // Chuyển khoản
                    txtNote.setText("Chuyển khoản qua ngân hàng");
                    break;
                case 3: // Thẻ tín dụng
                    txtNote.setText("Thanh toán bằng thẻ tín dụng");
                    break;
                case 4: // Ví điện tử
                    txtNote.setText("Thanh toán qua ví điện tử");
                    break;
                case 5: // QR Code
                    txtNote.setText("Thanh toán bằng mã QR");
                    break;
                default:
                    txtNote.setText("");
                    break;
            }
        }
    }
    
    /**
     * Custom renderer cho JComboBox PaymentMethod
     */
    private static class PaymentMethodRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof PaymentMethod) {
                PaymentMethod method = (PaymentMethod) value;
                setText(method.getMethod_name());
                
                // Thêm icon tùy theo phương thức
                switch (method.getPayment_method_id()) {
                    case 1: setText("💵 " + method.getMethod_name()); break;
                    case 2: setText("🏦 " + method.getMethod_name()); break;
                    case 3: setText("💳 " + method.getMethod_name()); break;
                    case 4: setText("📱 " + method.getMethod_name()); break;
                    case 5: setText("📱 " + method.getMethod_name()); break;
                    default: setText(method.getMethod_name()); break;
                }
            }
            
            return this;
        }
    }
    
    /**
     * Format tiền tệ
     */
    private String formatCurrency(double amount) {
        return String.format("%,.0f VNĐ", amount);
    }
    
    /**
     * Getter cho phương thức thanh toán đã chọn
     */
    public PaymentMethod getSelectedPaymentMethod() {
        return selectedPaymentMethod;
    }
    
    /**
     * Getter cho ghi chú
     */
    public String getNote() {
        return txtNote.getText().trim();
    }
}
