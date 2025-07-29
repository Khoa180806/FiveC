/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.team4.quanliquanmicay.Controller;

import com.team4.quanliquanmicay.Entity.Product;

/**
 * Controller đơn giản cho quản lý sản phẩm món ăn
 * Kế thừa CrudController để tái sử dụng create, update, delete, clear
 * @author Team4
 */
public interface ProductController extends CrudController<Product> {
    
    /**
     * Tải và hiển thị danh sách loại món ăn vào ComboBox
     */
    void fillCategories();
    
    /**
     * Tải và hiển thị danh sách trạng thái vào ComboBox  
     */
    void fillStatus();
    
    /**
     * Chọn ảnh cho món ăn
     */
    void chooseImage();
    
    // Các method từ CrudController đã có sẵn:
    // void open();           // Hiển thị dialog
    // void create();         // Tạo món ăn mới  
    // void update();         // Cập nhật món ăn
    // void delete();         // Xóa món ăn
    // void clear();          // Làm mới form
}
