package com.team4.quanliquanmicay.Impl;

import com.team4.quanliquanmicay.DAO.ProductDAO;
import com.team4.quanliquanmicay.Entity.Product;
import com.team4.quanliquanmicay.util.XJdbc;
import com.team4.quanliquanmicay.util.XQuery;
import java.util.List;

public class ProductDAOImpl implements ProductDAO{

    String createSql = "INSERT INTO PRODUCT(product_id, product_name, price, discount, unit, image, is_available, note, created_date, category_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE PRODUCT SET product_name=?, price=?, discount=?, unit=?, image=?, is_available=?, note=?, created_date=?, category_id=? WHERE product_id=?";
    String deleteSql = "DELETE FROM PRODUCT WHERE product_id=?";
    String findAllSql = "SELECT PRODUCT_ID AS productId, PRODUCT_NAME AS productName, PRICE AS price, DISCOUNT AS discount, UNIT AS unit, IMAGE AS image, IS_AVAILABLE AS is_available, NOTE AS note, CREATED_DATE AS createdDate, CATEGORY_ID AS categoryId FROM PRODUCT";
    String findByIdSql = "SELECT "
    + "PRODUCT_ID AS productId, "
    + "PRODUCT_NAME AS productName, "
    + "PRICE AS price, "
    + "DISCOUNT AS discount, "
    + "UNIT AS unit, "
    + "IMAGE AS image, "
    + "IS_AVAILABLE AS is_available, "
    + "NOTE AS note, "
    + "CREATED_DATE AS createdDate, "
    + "CATEGORY_ID AS categoryId "
    + "FROM PRODUCT WHERE PRODUCT_ID=?";

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
            entity.getProductId() // Đảm bảo đủ tham số cho WHERE product_id = ?
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

    @Override
    public java.util.List<String> findUnitsByCategoryId(String categoryId) {
        String sql = "SELECT DISTINCT unit FROM PRODUCT WHERE category_id = ? AND is_available = 1";
            return com.team4.quanliquanmicay.util.XJdbc.queryList(sql, rs -> {
            try { return rs.getString(1); } catch (Exception e) { return null; }
        }, categoryId);
    }
}