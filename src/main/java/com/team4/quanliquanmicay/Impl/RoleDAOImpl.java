package com.team4.quanliquanmicay.Impl;

import com.team4.quanliquanmicay.DAO.RoleDAO;
import com.team4.quanliquanmicay.Entity.UserRole;
import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.util.XQuery;
import java.util.List;

public class RoleDAOImpl implements RoleDAO {

    String createSql = "INSERT INTO USER_ROLE(role_id, name_role) VALUES(?, ?)";
    String updateSql = "UPDATE USER_ROLE SET name_role=? WHERE role_id=?";
    String deleteSql = "DELETE FROM USER_ROLE WHERE role_id=?";
    String findAllSql = "SELECT role_id, name_role FROM USER_ROLE";
    String findByIdSql = "SELECT role_id, name_role FROM USER_ROLE WHERE role_id=?";

    @Override
    public UserRole create(UserRole entity) {
        Object[] values = {
            entity.getRole_id(),
            entity.getName_role(),
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(UserRole entity) {
        Object[] values = {
            entity.getName_role(),
            entity.getRole_id(),
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(String role_id) {
        XJdbc.executeUpdate(deleteSql, role_id);
    }

    @Override
    public List<UserRole> findAll() {
        return XQuery.getBeanList(UserRole.class, findAllSql);
    }

    @Override
    public UserRole findById(String role_id) {
        return XQuery.getSingleBean(UserRole.class, findByIdSql, role_id);
    }

}
