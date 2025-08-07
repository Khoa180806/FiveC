package com.team4.quanliquanmicay.View;

import com.team4.quanliquanmicay.DAO.TableForCustomerDAO;
import com.team4.quanliquanmicay.Entity.TableForCustomer;
import com.team4.quanliquanmicay.Impl.TableForCustomerDAOImpl;
import com.team4.quanliquanmicay.util.XDialog;
import com.team4.quanliquanmicay.util.XTheme;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * UI chuyển bàn
 */
public class TransferTableUI extends JDialog {
    
    private final int currentTableNumber;
    private final TableForCustomerDAO tableDAO;
    private final ChooseTableUI parentUI;
    
    // UI Components
    private JLabel lblTitle;
    private JLabel lblCurrentTable;
    private JLabel lblCurrentTableInfo;
    private JLabel lblTargetTable;
    private JComboBox<String> cboTargetTable;
    private JButton btnConfirm;
    private JButton btnCancel;
    
    public TransferTableUI(ChooseTableUI parent, int currentTableNumber) {
        super(parent, "Chuyển Bàn", true);
        this.parentUI = parent;
        this.currentTableNumber = currentTableNumber;
        this.tableDAO = new TableForCustomerDAOImpl();
        
        initComponents();
        loadData();
        setupEventHandlers();
        
        // Thiết lập dialog
        this.setSize(400, 300);
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
        
        lblTitle = new JLabel("CHUYỂN BÀN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle);
        
        // Panel nội dung chính
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(204, 164, 133));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Thông tin bàn hiện tại
        lblCurrentTable = new JLabel("Bàn hiện tại:");
        lblCurrentTable.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCurrentTable.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(lblCurrentTable, gbc);
        
        lblCurrentTableInfo = new JLabel();
        lblCurrentTableInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblCurrentTableInfo.setForeground(Color.WHITE);
        lblCurrentTableInfo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblCurrentTableInfo.setOpaque(true);
        lblCurrentTableInfo.setBackground(new Color(255, 255, 255, 50));
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(lblCurrentTableInfo, gbc);
        
        // Bàn đích
        lblTargetTable = new JLabel("Chọn bàn đích:");
        lblTargetTable.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTargetTable.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(lblTargetTable, gbc);
        
        cboTargetTable = new JComboBox<>();
        cboTargetTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cboTargetTable.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(cboTargetTable, gbc);
        
        // Panel buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(204, 164, 133));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        btnConfirm = new JButton("Xác nhận");
        btnConfirm.setBackground(new Color(204, 255, 204));
        btnConfirm.setForeground(new Color(0, 153, 0));
        btnConfirm.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConfirm.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btnConfirm.setPreferredSize(new Dimension(100, 35));
        
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
    
    private void loadData() {
        try {
            // Load thông tin bàn hiện tại
            TableForCustomer currentTable = tableDAO.findById(currentTableNumber);
            if (currentTable != null) {
                String statusText = getStatusText(currentTable.getStatus());
                lblCurrentTableInfo.setText(String.format("Bàn số %d - %s - %d chỗ ngồi", 
                    currentTable.getTable_number(), statusText, currentTable.getAmount()));
                
                // Kiểm tra xem bàn hiện tại có đang hoạt động không
                if (currentTable.getStatus() != 1) {
                    XDialog.alert("Chỉ có thể chuyển bàn đang hoạt động!", "Thông báo");
                    this.dispose();
                    return;
                }
            }
            
            // Load danh sách bàn trống (chỉ hiển thị bàn trống)
            List<TableForCustomer> allTables = tableDAO.findAll();
            for (TableForCustomer table : allTables) {
                // Chỉ hiển thị bàn trống (status = 0) và không phải bàn hiện tại
                if (table.getStatus() == 0 && table.getTable_number() != currentTableNumber) {
                    cboTargetTable.addItem(String.format("Bàn %d (%d chỗ ngồi)", 
                        table.getTable_number(), table.getAmount()));
                }
            }
            
            if (cboTargetTable.getItemCount() == 0) {
                XDialog.alert("Không có bàn trống nào để chuyển!", "Thông báo");
                this.dispose();
            }
            
        } catch (Exception e) {
            XDialog.alert("Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi");
            this.dispose();
        }
    }
    
    private void setupEventHandlers() {
        btnConfirm.addActionListener(e -> handleConfirm());
        btnCancel.addActionListener(e -> handleCancel());
    }
    
    private void handleConfirm() {
        if (cboTargetTable.getSelectedItem() == null) {
            XDialog.alert("Vui lòng chọn bàn đích!", "Thông báo");
            return;
        }
        
        // Lấy thông tin bàn đích
        String selectedItem = (String) cboTargetTable.getSelectedItem();
        int targetTableNumber = Integer.parseInt(selectedItem.split(" ")[1]);
        
        // Lấy thông tin bàn hiện tại
        TableForCustomer currentTable = tableDAO.findById(currentTableNumber);
        TableForCustomer targetTable = tableDAO.findById(targetTableNumber);
        
        if (currentTable == null || targetTable == null) {
            XDialog.alert("Không tìm thấy thông tin bàn!", "Lỗi");
            return;
        }
        
        // Kiểm tra lại trạng thái bàn hiện tại
        if (currentTable.getStatus() != 1) {
            XDialog.alert("Chỉ có thể chuyển bàn đang hoạt động!", "Thông báo");
            return;
        }
        
        // Hiển thị thông tin xác nhận
        StringBuilder message = new StringBuilder();
        message.append("Bạn có chắc chắn muốn chuyển bàn không?\n\n");
        message.append("TỪ BÀN (ĐANG HOẠT ĐỘNG):\n");
        message.append("Số bàn: ").append(currentTable.getTable_number()).append("\n");
        message.append("Trạng thái: ").append(getStatusText(currentTable.getStatus())).append("\n");
        message.append("Số chỗ ngồi: ").append(currentTable.getAmount()).append("\n\n");
        message.append("SANG BÀN (TRỐNG):\n");
        message.append("Số bàn: ").append(targetTable.getTable_number()).append("\n");
        message.append("Trạng thái: ").append(getStatusText(targetTable.getStatus())).append("\n");
        message.append("Số chỗ ngồi: ").append(targetTable.getAmount()).append("\n\n");
        message.append("Lưu ý: Chỉ chuyển các bill chưa thanh toán!");
        
        if (XDialog.confirm(message.toString(), "Xác nhận chuyển bàn")) {
            performTransfer(currentTable, targetTable);
        }
    }
    
    private void handleCancel() {
        if (cboTargetTable.getSelectedItem() != null) {
            String selectedItem = (String) cboTargetTable.getSelectedItem();
            int targetTableNumber = Integer.parseInt(selectedItem.split(" ")[1]);
            
            TableForCustomer currentTable = tableDAO.findById(currentTableNumber);
            TableForCustomer targetTable = tableDAO.findById(targetTableNumber);
            
            if (currentTable != null && targetTable != null) {
                StringBuilder message = new StringBuilder();
                message.append("Bạn có chắc chắn muốn hủy chuyển bàn không?\n\n");
                message.append("Bàn hiện tại: ").append(currentTable.getTable_number()).append("\n");
                message.append("Bàn sắp chuyển: ").append(targetTable.getTable_number()).append("\n");
                
                if (XDialog.confirm(message.toString(), "Xác nhận hủy")) {
                    this.dispose();
                }
                // Nếu chọn "Không" thì tiếp tục chỉnh sửa
            }
        } else {
            this.dispose();
        }
    }
    
    private void performTransfer(TableForCustomer fromTable, TableForCustomer toTable) {
        try {
            // TODO: Thêm logic chuyển bill chưa thanh toán từ bàn cũ sang bàn mới
            // Đây là nơi sẽ gọi các service để chuyển bill
            
            // Hoán đổi trạng thái bàn
            // Bàn cũ (đang hoạt động) -> trống
            fromTable.setStatus(0);
            // Bàn mới (trống) -> đang hoạt động
            toTable.setStatus(1);
            
            tableDAO.update(fromTable);
            tableDAO.update(toTable);
            
            XDialog.alert("Chuyển bàn thành công!", "Thành công");
            
            // Cập nhật UI chính
            parentUI.loadTable();
            
            this.dispose();
            
        } catch (Exception e) {
            XDialog.alert("Lỗi khi chuyển bàn: " + e.getMessage(), "Lỗi");
        }
    }
    
    private String getStatusText(int status) {
        switch (status) {
            case 0: return "Trống";
            case 1: return "Đang phục vụ";
            case 2: return "Ngưng hoạt động";
            default: return "Không xác định";
        }
    }
} 