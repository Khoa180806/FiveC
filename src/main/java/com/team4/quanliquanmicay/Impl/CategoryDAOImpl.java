package com.team4.quanliquanmicay.Impl;

import com.team4.quanliquanmicay.DAO.CategoryDAO;
import com.team4.quanliquanmicay.Entity.Category;
import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.util.XQuery;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO{

    String createSql = "INSERT INTO CATE(category_id, category_name, IS_AVAILABLE) VALUES(?, ?, ?)";
    String updateSql = "UPDATE CATE SET category_name=?, IS_AVAILABLE=? WHERE category_id=?";
    String deleteSql = "DELETE FROM CATE WHERE category_id=?";
    String findAllSql = "SELECT category_id, category_name, IS_AVAILABLE AS \"is_available\" FROM CATE";
    String findByIdSql = "SELECT category_id, category_name, IS_AVAILABLE AS \"is_available\" FROM CATE WHERE category_id=?";

    @Override
    public Category create(Category entity) {
        Object[] values = {
            entity.getCategory_id(),
            entity.getCategory_name(),
            entity.getIs_available() == 1 ? 1 : 0,
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(Category entity) {
        Object[] values = {
            entity.getCategory_name(),
            entity.getIs_available() == 1 ? 1 : 0,
            entity.getCategory_id(),
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
