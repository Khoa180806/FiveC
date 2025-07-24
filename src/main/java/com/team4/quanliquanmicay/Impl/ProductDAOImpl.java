package com.team4.quanliquanmicay.Impl;

import com.team4.quanliquanmicay.DAO.ProductDAO;
import com.team4.quanliquanmicay.Entity.Product;
import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.util.XQuery;
import java.util.List;

public class ProductDAOImpl implements ProductDAO{

    String createSql = "INSERT INTO PRODUCT(product_id, name_product, price, discount, unit, image, is_available, note, created_date, category_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE PRODUCT SET name_product=?, price=?, discount=?, unit=?, image=?, is_available=?, note=?, created_date=?, category_id=? WHERE product_id=?";
    String deleteSql = "DELETE FROM PRODUCT WHERE product_id=?";
    String findAllSql = "SELECT product_id, name_product, price, discount, unit, image, is_available, note, created_date, category_id FROM PRODUCT";
    String findByIdSql = "SELECT product_id, name_product, price, discount, unit, image, is_available, note, created_date, category_id FROM PRODUCT WHERE product_id=?";
    String deleteByCategoryIdSql = "DELETE FROM PRODUCT WHERE CATEGORY_ID = ?";

    @Override
    public Product create(Product entity) {
        Object[] values = {
            entity.getProductId(),
            entity.getProductName(),
            entity.getPrice(),
            entity.getDiscount(),
            entity.getUnit(),
            entity.getImage(),
            entity.isAvailable(),
            entity.getNote(),
            entity.getCreatedDate(),
            entity.getCategoryId(),
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(Product entity) {
        Object[] values = {
            entity.getProductName(),
            entity.getPrice(),
            entity.getDiscount(),
            entity.getUnit(),
            entity.getImage(),
            entity.isAvailable(),
            entity.getNote(),
            entity.getCreatedDate(),
            entity.getCategoryId(),
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(String productId) {
        XJdbc.executeUpdate(deleteSql, productId);
    }

    @Override
    public List<Product> findAll() {
        return XQuery.getBeanList(Product.class, findAllSql);
    }

    @Override
    public Product findById(String productId) {
        return XQuery.getSingleBean(Product.class, findByIdSql, productId);
    }
}