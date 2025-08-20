package com.team4.quanliquanmicay.DAO;

import com.team4.quanliquanmicay.Entity.Customer;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer, String> {
    
    /**
     * Search customers by phone number (partial match)
     */
    List<Customer> searchByPhone(String phoneNumber);
    
    /**
     * Sort customers by point level in ascending order
     */
    List<Customer> sortByPointAsc();
    
    /**
     * Sort customers by point level in descending order
     */
    List<Customer> sortByPointDesc();

    // Kiểm tra tồn tại theo số điện thoại
    boolean existsByPhone(String phoneNumber);

    // Cập nhật cho phép đổi số điện thoại (PK) và cascade sang BILL
    void updateWithPhoneChange(String oldPhoneNumber, Customer entity);
}
