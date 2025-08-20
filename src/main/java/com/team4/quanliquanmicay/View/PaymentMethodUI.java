package com.team4.quanliquanmicay.View;

import com.team4.quanliquanmicay.DAO.PaymentMethodDAO;
import com.team4.quanliquanmicay.Entity.PaymentMethod;
import com.team4.quanliquanmicay.Impl.PaymentMethodDAOImpl;
import com.team4.quanliquanmicay.util.XDialog;
import com.team4.quanliquanmicay.util.XQR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * UI chọn phương thức thanh toán
 */
public class PaymentMethodUI extends JDialog {
    
    private final PaymentMethodDAO paymentMethodDAO;
    private PaymentMethod selectedPaymentMethod;
    private final double totalAmount;
    private final String billCode; // Mã hóa đơn
    
    // UI Components
    private JLabel lblTitle;
    private JLabel lblAmount;
    private JLabel lblAmountValue;
    private JLabel lblPaymentMethod;
    private JComboBox<PaymentMethod> cboPaymentMethod;
    private JTextArea txtNote;
    private JButton btnConfirm;
    private JButton btnCancel;

    // Bank config: sử dụng trực tiếp từ XQR (cấu hình tĩnh)
    
    public PaymentMethodUI(Frame parent, double totalAmount, String billCode) {
        super(parent, "Chọn Phương Thức Thanh Toán", true);
        this.totalAmount = totalAmount;
        this.billCode = billCode != null ? billCode : "";
        this.paymentMethodDAO = new PaymentMethodDAOImpl();
        this.selectedPaymentMethod = null;
        
        initComponents();
        loadPaymentMethods();
        setupEventHandlers();
        
        // Thiết lập dialog
        this.setSize(500, 450);
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
        gbc.insets = new Insets(6, 15, 6, 15);
        
        // Thông tin tổng tiền
        lblAmount = new JLabel("Tổng tiền thanh toán:");
        lblAmount.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblAmount.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(lblAmount, gbc);
        
        lblAmountValue = new JLabel(formatCurrency(totalAmount));
        lblAmountValue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblAmountValue.setForeground(new Color(255, 215, 0)); // Màu vàng
        lblAmountValue.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
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
        cboPaymentMethod.setPreferredSize(new Dimension(400, 30));
        cboPaymentMethod.setRenderer(new PaymentMethodRenderer());
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(cboPaymentMethod, gbc);
        
        // Ghi chú chi tiết (vẫn giữ TextArea như cũ)
        JLabel lblNote = new JLabel("Ghi chú chi tiết (tùy chọn):");
        lblNote.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNote.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(lblNote, gbc);
        
        txtNote = new JTextArea(8, 35);
        txtNote.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtNote.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        txtNote.setLineWrap(true);
        txtNote.setWrapStyleWord(true);
        
        JScrollPane scrollNote = new JScrollPane(txtNote);
        scrollNote.setPreferredSize(new Dimension(400, 150));
        scrollNote.setMinimumSize(new Dimension(400, 150));
        scrollNote.setMaximumSize(new Dimension(400, 150));
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(scrollNote, gbc);
        
        // Panel buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(204, 164, 133));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 10));
        
        btnConfirm = new JButton("Xác nhận thanh toán");
        btnConfirm.setBackground(new Color(204, 255, 204));
        btnConfirm.setForeground(new Color(0, 153, 0));
        btnConfirm.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConfirm.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btnConfirm.setPreferredSize(new Dimension(180, 35));
        
        btnCancel = new JButton("Hủy");
        btnCancel.setBackground(new Color(255, 179, 179));
        btnCancel.setForeground(new Color(153, 0, 0));
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btnCancel.setPreferredSize(new Dimension(120, 35));
        
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
        
        message.append("\nBạn có chắc chắn muốn tiếp tục với phương thức này?");

        if (!XDialog.confirm(message.toString(), "Xác nhận thanh toán")) {
            return;
        }

        int methodId = selectedPaymentMethod.getPayment_method_id();
        boolean accepted = true;
        switch (methodId) {
            case 1: // Tiền mặt
                accepted = showCashPaymentDialog();
                break;
            case 2: // Chuyển khoản
                accepted = showTransferPaymentDialog();
                break;
            default:
                accepted = true; // các phương thức khác không có UI phụ
        }

        if (accepted) {
            this.dispose();
        }
    }
    
    private void handleCancel() {
        if (XDialog.confirm("Bạn có chắc chắn muốn hủy thanh toán không?", "Xác nhận hủy")) {
            selectedPaymentMethod = null;
            this.dispose();
        }
    }

    // UI phụ: Tiền mặt (nhập tiền nhận và xem tiền thối)
    private boolean showCashPaymentDialog() {
        final JDialog dlg = new JDialog(SwingUtilities.getWindowAncestor(this), "Thanh toán tiền mặt", Dialog.ModalityType.APPLICATION_MODAL);
        dlg.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 10, 6, 10);
        g.gridx = 0; g.gridy = 0; g.anchor = GridBagConstraints.WEST;

        JLabel lblTotal = new JLabel("Tổng tiền: " + formatCurrency(totalAmount));
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        dlg.add(lblTotal, g);

        g.gridy++;
        JLabel lblRecv = new JLabel("Tiền khách đưa:");
        dlg.add(lblRecv, g);

        g.gridy++; g.fill = GridBagConstraints.HORIZONTAL;
        JTextField txtRecv = new JTextField();
        txtRecv.setPreferredSize(new Dimension(220, 28));
        dlg.add(txtRecv, g);

        g.gridy++; g.fill = GridBagConstraints.NONE;
        JLabel lblChange = new JLabel("Tiền thối:");
        dlg.add(lblChange, g);

        g.gridy++; g.fill = GridBagConstraints.HORIZONTAL;
        JTextField txtChangeLocal = new JTextField();
        txtChangeLocal.setEditable(false);
        txtChangeLocal.setPreferredSize(new Dimension(220, 28));
        dlg.add(txtChangeLocal, g);

        txtRecv.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    String digits = txtRecv.getText().replaceAll("[^0-9]", "");
                    double recv = digits.isEmpty() ? 0 : Double.parseDouble(digits);
                    double change = Math.max(0, recv - totalAmount);
                    txtChangeLocal.setText(formatCurrency(change));
                } catch (Exception ex) {
                    txtChangeLocal.setText(formatCurrency(0));
                }
            }
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        JButton ok = new JButton("Xác nhận");
        JButton cancel = new JButton("Hủy");
        btnPanel.add(ok); btnPanel.add(cancel);

        g.gridy++;
        dlg.add(btnPanel, g);

        final boolean[] accepted = {false};
        ok.addActionListener(ev -> {
            String digits = txtRecv.getText().replaceAll("[^0-9]", "");
            double recv = digits.isEmpty() ? 0 : Double.parseDouble(digits);
            if (recv < totalAmount) {
                XDialog.alert("Tiền nhận chưa đủ!", "Cảnh báo");
                return;
            }
            accepted[0] = true;
            dlg.dispose();
        });
        cancel.addActionListener(ev -> dlg.dispose());

        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setResizable(false);
        dlg.setVisible(true);
        return accepted[0];
    }

    // UI phụ: Chuyển khoản (hiển thị QR theo số tiền và ghi chú)
    private boolean showTransferPaymentDialog() {
        final JDialog dlg = new JDialog(SwingUtilities.getWindowAncestor(this), "Thanh toán chuyển khoản", Dialog.ModalityType.APPLICATION_MODAL);
        dlg.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 15, 8, 15);
        g.gridx = 0; g.gridy = 0; g.anchor = GridBagConstraints.CENTER;

        JLabel lblTotal = new JLabel("Số tiền: " + formatCurrency(totalAmount));
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        dlg.add(lblTotal, g);



        g.gridy++; g.fill = GridBagConstraints.NONE; g.anchor = GridBagConstraints.CENTER;
        JLabel lblQr = new JLabel();
        lblQr.setPreferredSize(new Dimension(200, 200));
        lblQr.setHorizontalAlignment(SwingConstants.CENTER);
        lblQr.setVerticalAlignment(SwingConstants.CENTER);
        lblQr.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dlg.add(lblQr, g);

        Runnable refreshQr = () -> {
            String addInfo = "Thanh toan hoa don " + billCode;
            ImageIcon icon = XQR.buildAndFetchVietQrIcon(totalAmount, addInfo, "compact", 200);
            if (icon != null) {
                lblQr.setIcon(icon);
                lblQr.setText("");
            } else {
                lblQr.setIcon(null);
                lblQr.setText("Không tải được QR");
            }
        };
        refreshQr.run();

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        JButton ok = new JButton("Đã chuyển khoản");
        JButton cancel = new JButton("Hủy");
        btnPanel.add(ok); btnPanel.add(cancel);

        g.gridy++;
        dlg.add(btnPanel, g);

        final boolean[] accepted = {false};
        ok.addActionListener(ev -> {
            accepted[0] = true;
            dlg.dispose();
        });
        cancel.addActionListener(ev -> dlg.dispose());

        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setResizable(false);
        dlg.setVisible(true);
        return accepted[0];
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
        return txtNote != null ? txtNote.getText().trim() : "";
    }
    
    /**
     * Getter cho mã hóa đơn
     */
    public String getBillCode() {
        return billCode;
    }
}
