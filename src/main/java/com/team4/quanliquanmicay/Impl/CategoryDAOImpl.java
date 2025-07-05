package com.team4.quanliquanmicay.Impl;

import com.team4.quanliquanmicay.DAO.CategoryDAO;
import com.team4.quanliquanmicay.Entity.Category;
import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.util.XQuery;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO{

    String createSql = "INSERT INTO CATEGORY(category_id, name_category, is_available) VALUES(?, ?, ?)";
    String updateSql = "UPDATE CATEGORY SET name_category=?, is_available=? WHERE category_id=?";
    String deleteSql = "DELETE FROM CATEGORY WHERE category_id=?";
    String findAllSql = "SELECT category_id, name_category, is_available FROM CATEGORY";
    String findByIdSql = "SELECT category_id, name_category, is_available FROM CATEGORY WHERE category_id=?";

    @Override
    public Category create(Category entity) {
        Object[] values = {
            entity.getCategoryId(),
            entity.getCategoryName(),
            entity.isAvailable(),
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(Category entity) {
        Object[] values = {
            entity.getCategoryName(),
            entity.isAvailable(),
            entity.getCategoryId(),
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(String categoryId) {
        XJdbc.executeUpdate(deleteSql, categoryId);
    }

    @Override
    public List<Category> findAll() {
        return XQuery.getBeanList(Category.class, findAllSql);
    }

    @Override
    public Category findById(String categoryId) {
        return XQuery.getSingleBean(Category.class, findByIdSql, categoryId);
    }
}
