package com.team4.quanliquanmicay.Controller;

import com.team4.quanliquanmicay.Entity.UserAccount;

/**
 *
 * @author Team4
 */
public interface EmployeeController extends CrudController<UserAccount> {
    void loadRoles();           // Tải danh sách vai trò vào ComboBox
    void validateEmployee();    // Kiểm tra hợp lệ thông tin nhân viên
}
