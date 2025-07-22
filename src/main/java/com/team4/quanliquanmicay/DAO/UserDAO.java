package com.team4.quanliquanmicay.DAO;

import com.team4.quanliquanmicay.Entity.UserAccount;

public interface UserDAO extends CrudDAO<UserAccount, String> {
    UserAccount findByUsername(String username);
}
