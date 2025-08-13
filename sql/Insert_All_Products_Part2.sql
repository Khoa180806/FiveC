-- ========================================
-- INSERT TẤT CẢ SẢN PHẨM - PHẦN 2
-- Đồ Ăn Vặt, Cơm, Combo, Khai Vị, Tráng Miệng
-- Author: FiveC
-- Date: 2025
-- ========================================

SET SERVEROUTPUT ON;

BEGIN
    DBMS_OUTPUT.PUT_LINE('=== BẮT ĐẦU INSERT PHẦN 2: ĐỒ ĂN VẶT, CƠM, COMBO, KHAI VỊ, TRÁNG MIỆNG ===');
END;
/

-- ========================================
-- 5. INSERT SẢN PHẨM ĐỒ ĂN VẶT (ANVAT)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 5: Insert sản phẩm Đồ Ăn Vặt...');
END;
/

-- Bánh
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Bánh Phồng', 15000, 0.00, N'phần', 'anvat_banh_phong.jpg', 1, N'Bánh phồng tôm giòn rụm', 'C006'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Bánh Rán', 20000, 0.00, N'phần', 'anvat_banh_ran.jpg', 1, N'Bánh rán thơm ngon', 'C006'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Bánh Tôm', 25000, 0.00, N'phần', 'anvat_banh_tom.jpg', 1, N'Bánh tôm giòn', 'C006'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Bánh Xèo', 30000, 0.00, N'phần', 'anvat_banh_xeo.jpg', 1, N'Bánh xèo truyền thống', 'C006'
FROM PRODUCT;

-- Chả
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Chả Quế', 25000, 0.00, N'phần', 'anvat_cha_que.jpg', 1, N'Chả quế thơm ngon', 'C006'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Chả Ruốc', 30000, 0.00, N'phần', 'anvat_cha_ruoi.jpg', 1, N'Chả ruốc đặc biệt', 'C006'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Chả Tôm', 35000, 0.00, N'phần', 'anvat_cha_tom.jpg', 1, N'Chả tôm tươi', 'C006'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Chả Bò', 40000, 0.00, N'phần', 'anvat_cha_bo.jpg', 1, N'Chả bò thơm ngon', 'C006'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Chả Gà', 35000, 0.00, N'phần', 'anvat_cha_ga.jpg', 1, N'Chả gà truyền thống', 'C006'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Chả Cá', 30000, 0.00, N'phần', 'anvat_cha_ca.jpg', 1, N'Chả cá tươi', 'C006'
FROM PRODUCT;

-- ========================================
-- 6. INSERT SẢN PHẨM CƠM (COM)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 6: Insert sản phẩm Cơm...');
END;
/

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Cơm Bò', 45000, 0.00, N'phần', 'com_bo.jpg', 1, N'Cơm bò thơm ngon', 'C007'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Cơm Gà', 40000, 0.00, N'phần', 'com_ga.jpg', 1, N'Cơm gà truyền thống', 'C007'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Cơm Heo', 35000, 0.00, N'phần', 'com_heo.jpg', 1, N'Cơm heo thơm ngon', 'C007'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Cơm Cá', 40000, 0.00, N'phần', 'com_ca.jpg', 1, N'Cơm cá tươi', 'C007'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Cơm Đặc Biệt', 50000, 0.00, N'phần', 'com_dac_biet.jpg', 1, N'Cơm đặc biệt thập cẩm', 'C007'
FROM PRODUCT;

-- ========================================
-- 7. INSERT SẢN PHẨM COMBO
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 7: Insert sản phẩm Combo...');
END;
/

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Combo 1', 80000, 0.05, N'set', 'combo_1.jpg', 1, N'Combo mì cay + nước uống', 'C005'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Combo 2', 90000, 0.05, N'set', 'combo_2.jpg', 1, N'Combo lẩu + nước uống', 'C005'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Combo 3', 100000, 0.10, N'set', 'combo_3.jpg', 1, N'Combo đặc biệt', 'C005'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Combo 4', 110000, 0.10, N'set', 'combo_4.jpg', 1, N'Combo cao cấp', 'C005'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Combo 5', 120000, 0.10, N'set', 'combo_5.png', 1, N'Combo thập cẩm', 'C005'
FROM PRODUCT;

-- ========================================
-- 8. INSERT SẢN PHẨM KHAI VỊ (KhaiVi)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 8: Insert sản phẩm Khai Vị...');
END;
/

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Gỏi Cuốn', 25000, 0.00, N'phần', 'khaivi_goi_cuon.jpg', 1, N'Gỏi cuốn tươi ngon', 'C009'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Gỏi Gà', 30000, 0.00, N'phần', 'khaivi_goi_ga.jpg', 1, N'Gỏi gà thơm ngon', 'C009'
FROM PRODUCT;

-- ========================================
-- 9. INSERT SẢN PHẨM TRÁNG MIỆNG (Pancha)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 9: Insert sản phẩm Tráng Miệng...');
END;
/

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Bánh Flan', 15000, 0.00, N'phần', 'trang_mieng_banh_flan.png', 1, N'Bánh flan mềm mịn', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Bánh Tiramisu', 20000, 0.00, N'phần', 'trang_mieng_banh_tiramisu.png', 1, N'Bánh tiramisu Ý', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Bánh Cheesecake', 25000, 0.00, N'phần', 'trang_mieng_banh_cheesecake.png', 1, N'Bánh cheesecake', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Bánh Chocolate', 20000, 0.00, N'phần', 'trang_mieng_banh_chocolate.png', 1, N'Bánh chocolate đắng', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Bánh Mousse', 18000, 0.00, N'phần', 'trang_mieng_banh_mousse.png', 1, N'Bánh mousse mềm', 'C004'
FROM PRODUCT;

-- ========================================
-- 10. INSERT SẢN PHẨM THÊM (Them)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 10: Insert sản phẩm Thêm...');
END;
/

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Bánh Mì', 10000, 0.00, N'phần', 'them_banh_mi.png', 1, N'Bánh mì thêm', 'C002'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Bánh Bao', 15000, 0.00, N'phần', 'them_banh_bao.png', 1, N'Bánh bao thêm', 'C002'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Bánh Chưng', 20000, 0.00, N'phần', 'them_banh_chung.png', 1, N'Bánh chưng thêm', 'C002'
FROM PRODUCT;

COMMIT;

BEGIN
    DBMS_OUTPUT.PUT_LINE('✅ ĐÃ INSERT THÀNH CÔNG TẤT CẢ SẢN PHẨM!');
    DBMS_OUTPUT.PUT_LINE('=== HOÀN THÀNH QUÁ TRÌNH INSERT DỮ LIỆU ===');
END;
/
