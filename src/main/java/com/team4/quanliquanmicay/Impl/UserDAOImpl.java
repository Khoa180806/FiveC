package com.team4.quanliquanmicay.Impl;

import com.team4.quanliquanmicay.DAO.UserDAO;
import com.team4.quanliquanmicay.Entity.UserAccount;
import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.util.XQuery;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    String createSql = "INSERT INTO USER_ACCOUNT(user_id, username, pass, fullName, gender, email, phone_number, image, is_enabled, created_date, role_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE USER_ACCOUNT SET pass=?, fullName=?, gender=?, email=?, phone_number=?, image=?, is_enabled=?, created_date=?, role_id=? WHERE user_id=?";
    String deleteSql = "DELETE FROM USER_ACCOUNT WHERE user_id=?";
    String findAllSql = "SELECT user_id, username, pass, fullName, gender AS Gender, email, phone_number, image, is_enabled AS Is_enabled, created_date, role_id FROM USER_ACCOUNT";
    String findByIdSql = "SELECT user_id, username, pass, fullName, gender AS Gender, email, phone_number, image, is_enabled AS Is_enabled, created_date, role_id FROM USER_ACCOUNT WHERE user_id=?";

    @Override
    public UserAccount create(UserAccount entity) {
        Object[] values = {
            entity.getUser_id(),
            entity.getUsername(),
            entity.getPass(),
            entity.getFullName(),
            entity.getGender(),
            entity.getEmail(),
            entity.getPhone_number(),
            entity.getImage(),
            entity.getIs_enabled(),
            entity.getCreated_date(),
            entity.getRole_id(),
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(UserAccount entity) {
        Object[] values = {
            entity.getPass(),
            entity.getFullName(),
            entity.getGender(),
            entity.getEmail(),
            entity.getPhone_number(),
            entity.getImage(),
            entity.getIs_enabled(),
            entity.getCreated_date(),
            entity.getRole_id(),
            entity.getUser_id(),
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(String user_id) {
        XJdbc.executeUpdate(deleteSql, user_id);
    }

    @Override
    public List<UserAccount> findAll() {
        return XQuery.getBeanList(UserAccount.class, findAllSql);
    }

    @Override
    public UserAccount findById(String user_id) {
        return XQuery.getSingleBean(UserAccount.class, findByIdSql, user_id);
    }

    public static void main(String[] args) {
       UserDAOImpl dao = new UserDAOImpl();
       List<UserAccount> list = dao.findAll();
       for(UserAccount u : list){
           System.out.println(u.getFullName());
       }
    }
}
