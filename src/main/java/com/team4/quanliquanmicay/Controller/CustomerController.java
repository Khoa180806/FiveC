/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.team4.quanliquanmicay.Controller;

import com.team4.quanliquanmicay.Entity.Customer;
import com.team4.quanliquanmicay.DAO.CustomerDAO;
import com.team4.quanliquanmicay.Impl.CustomerDAOImpl;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Windows
 */
public class CustomerController implements CrudController<Customer> {
    
    private CustomerDAO customerDAO;
    private Customer currentCustomer;
    
    public CustomerController() {
        this.customerDAO = new CustomerDAOImpl();
    }
    
    @Override
    public void open() {
        // Method to open customer dialog
    }
    
    @Override
    public void setForm(Customer entity) {
        this.currentCustomer = entity;
    }
    
    @Override
    public Customer getForm() {
        return this.currentCustomer;
    }
    
    @Override
    public void fillToTable() {
        // Method to fill data to table (if needed)
    }
    
    @Override
    public void edit() {
        // Method to edit customer
    }
    
    @Override
    public void create() {
        try {
            if (currentCustomer != null) {
                // Set default values for new customer
                if (currentCustomer.getPoint_level() == 0) {
                    currentCustomer.setPoint_level(0);
                }
                if (currentCustomer.getLevel_ranking() == null) {
                    currentCustomer.setLevel_ranking("Bronze");
                }
                if (currentCustomer.getCreated_date() == null) {
                    currentCustomer.setCreated_date(new Date());
                }
                
                customerDAO.create(currentCustomer);
                JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void update() {
        try {
            if (currentCustomer != null) {
                customerDAO.update(currentCustomer);
                JOptionPane.showMessageDialog(null, "Cập nhật khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void delete() {
        try {
            if (currentCustomer != null && currentCustomer.getPhone_number() != null) {
                int confirm = JOptionPane.showConfirmDialog(null, 
                    "Bạn có chắc chắn muốn xóa khách hàng này?", 
                    "Xác nhận xóa", 
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    customerDAO.deleteById(currentCustomer.getPhone_number());
                    JOptionPane.showMessageDialog(null, "Xóa khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void clear() {
        this.currentCustomer = null;
    }
    
    @Override
    public void setEditable(boolean editable) {
        // Method to set form editable state
    }
    
    @Override
    public void checkAll() {
        // Method to check all items in table
    }
    
    @Override
    public void uncheckAll() {
        // Method to uncheck all items in table
    }
    
    @Override
    public void deleteCheckedItems() {
        // Method to delete checked items
    }
    
    /**
     * Create a new customer from phone number and name
     */
    public void createCustomer(String phoneNumber, String customerName) {
        try {
            // Check if customer already exists
            Customer existingCustomer = customerDAO.findById(phoneNumber);
            if (existingCustomer != null) {
                JOptionPane.showMessageDialog(null, "Khách hàng với số điện thoại này đã tồn tại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Create new customer
            Customer newCustomer = Customer.builder()
                .phone_number(phoneNumber)
                .customer_name(customerName)
                .point_level(0)
                .level_ranking("Bronze")
                .created_date(new Date())
                .build();
            
            customerDAO.create(newCustomer);
            JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Find customer by phone number
     */
    public Customer findCustomerByPhone(String phoneNumber) {
        try {
            return customerDAO.findById(phoneNumber);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    /**
     * Get all customers
     */
    public List<Customer> getAllCustomers() {
        try {
            return customerDAO.findAll();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy danh sách khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    /**
     * Validate phone number format
     */
    public boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        // Vietnamese phone number format: 10-11 digits starting with 0
        return phoneNumber.matches("^0[0-9]{9,10}$");
    }
    
    /**
     * Validate customer name
     */
    public boolean isValidCustomerName(String customerName) {
        return customerName != null && !customerName.trim().isEmpty() && customerName.trim().length() >= 2;
    }
    
    /**
     * Update existing customer
     */
    public void updateCustomer(String phoneNumber, String customerName) {
        try {
            Customer existingCustomer = customerDAO.findById(phoneNumber);
            if (existingCustomer == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng với số điện thoại này!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Update customer information
            existingCustomer.setCustomer_name(customerName);
            customerDAO.update(existingCustomer);
            JOptionPane.showMessageDialog(null, "Cập nhật khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Check if customer exists by phone number
     */
    public boolean customerExists(String phoneNumber) {
        try {
            return customerDAO.findById(phoneNumber) != null;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get customer count for statistics
     */
    public int getCustomerCount() {
        try {
            List<Customer> customers = customerDAO.findAll();
            return customers != null ? customers.size() : 0;
        } catch (Exception e) {
            return 0;
        }
    }
    
    /**
     * Search customers by phone number
     */
    public List<Customer> searchCustomersByPhone(String phoneNumber) {
        try {
            return customerDAO.searchByPhone(phoneNumber);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    /**
     * Sort customers by point level in ascending order
     */
    public List<Customer> sortCustomersByPointAsc() {
        try {
            return customerDAO.sortByPointAsc();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi sắp xếp khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    /**
     * Sort customers by point level in descending order
     */
    public List<Customer> sortCustomersByPointDesc() {
        try {
            return customerDAO.sortByPointDesc();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi sắp xếp khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
