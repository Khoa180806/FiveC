package com.team4.quanliquanmicay.DAO;

import com.team4.quanliquanmicay.Entity.Product;

public interface ProductDAO extends CrudDAO<Product, String>{
    // Lấy danh sách đơn vị (unit) duy nhất theo categoryId
    java.util.List<String> findUnitsByCategoryId(String categoryId);
}