-- ========================================
-- INSERT SẢN PHẨM VỚI PRODUCT_ID TỰ ĐỘNG
-- Author: FiveC
-- Date: 2025
-- ========================================

SET SERVEROUTPUT ON;

BEGIN
    DBMS_OUTPUT.PUT_LINE('=== BẮT ĐẦU INSERT SẢN PHẨM VỚI ID TỰ ĐỘNG ===');
END;
/

-- ========================================
-- 1. KIỂM TRA VÀ INSERT DANH MỤC CẦN THIẾT
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 1: Kiểm tra và insert danh mục cần thiết...');
END;
/

-- Kiểm tra và insert danh mục nếu chưa có
INSERT INTO CATE (category_id, category_name, is_available) 
SELECT 'C001', N'Mì Cay', 1 FROM DUAL 
WHERE NOT EXISTS (SELECT 1 FROM CATE WHERE category_id = 'C001');

INSERT INTO CATE (category_id, category_name, is_available) 
SELECT 'C003', N'Nước Uống', 1 FROM DUAL 
WHERE NOT EXISTS (SELECT 1 FROM CATE WHERE category_id = 'C003');

INSERT INTO CATE (category_id, category_name, is_available) 
SELECT 'C004', N'Tráng Miệng', 1 FROM DUAL 
WHERE NOT EXISTS (SELECT 1 FROM CATE WHERE category_id = 'C004');

INSERT INTO CATE (category_id, category_name, is_available) 
SELECT 'C005', N'Combo', 1 FROM DUAL 
WHERE NOT EXISTS (SELECT 1 FROM CATE WHERE category_id = 'C005');

INSERT INTO CATE (category_id, category_name, is_available) 
SELECT 'C006', N'Đồ Ăn Vặt', 1 FROM DUAL 
WHERE NOT EXISTS (SELECT 1 FROM CATE WHERE category_id = 'C006');

INSERT INTO CATE (category_id, category_name, is_available) 
SELECT 'C007', N'Cơm', 1 FROM DUAL 
WHERE NOT EXISTS (SELECT 1 FROM CATE WHERE category_id = 'C007');

INSERT INTO CATE (category_id, category_name, is_available) 
SELECT 'C008', N'Lẩu', 1 FROM DUAL 
WHERE NOT EXISTS (SELECT 1 FROM CATE WHERE category_id = 'C008');

INSERT INTO CATE (category_id, category_name, is_available) 
SELECT 'C009', N'Khai Vị', 1 FROM DUAL 
WHERE NOT EXISTS (SELECT 1 FROM CATE WHERE category_id = 'C009');

COMMIT;

-- ========================================
-- 2. INSERT SẢN PHẨM MÌ CAY (MI)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 2: Insert sản phẩm Mì Cay...');
END;
/

-- Mì Cay Cấp 1-3
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Cấp 1', 45000, 0.00, N'phần', 'mi_cay_cap_1.jpg', 1, N'Độ cay nhẹ, phù hợp người mới ăn', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Cấp 2', 50000, 0.00, N'phần', 'mi_cay_cap_2.jpg', 1, N'Độ cay vừa phải', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Cấp 3', 55000, 0.05, N'phần', 'mi_cay_cap_3.jpg', 1, N'Độ cay cao, cho người đã quen', 'C001'
FROM PRODUCT;

-- Mì Cay Đặc Biệt
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Đặc Biệt', 65000, 0.00, N'phần', 'mi_cay_dac_biet.jpg', 1, N'Độ cay cực cao, thử thách bản thân', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Đặc Biệt 2', 70000, 0.00, N'phần', 'mi_cay_dac_biet_2.jpg', 1, N'Mì cay đặc biệt với nhiều topping', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Đặc Biệt 3', 75000, 0.00, N'phần', 'mi_cay_dac_biet_3.jpg', 1, N'Mì cay đặc biệt siêu cay', 'C001'
FROM PRODUCT;

-- Mì Cay Hải Sản
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Hải Sản', 60000, 0.00, N'phần', 'mi_cay_hai_san.jpg', 1, N'Mì cay với hải sản tươi ngon', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Hải Sản 2', 65000, 0.00, N'phần', 'mi_cay_hai_san_2.jpg', 1, N'Mì cay hải sản đặc biệt', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Hải Sản 3', 70000, 0.00, N'phần', 'mi_cay_hai_san_3.jpg', 1, N'Mì cay hải sản cao cấp', 'C001'
FROM PRODUCT;

-- Mì Cay Thịt
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Bò', 55000, 0.00, N'phần', 'mi_cay_bo.png', 1, N'Mì cay với thịt bò tươi', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Bò XX', 60000, 0.00, N'phần', 'mi_cay_bo_xx.png', 1, N'Mì cay bò với nhiều thịt', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Bò Thường', 50000, 0.00, N'phần', 'mi_cay_bo_thuong.png', 1, N'Mì cay bò thường', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Gà', 50000, 0.00, N'phần', 'mi_cay_ga.jpg', 1, N'Mì cay với thịt gà', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Gà 2', 55000, 0.00, N'phần', 'mi_cay_ga_2.png', 1, N'Mì cay gà đặc biệt', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Heo', 45000, 0.00, N'phần', 'mi_cay_heo.jpg', 1, N'Mì cay với thịt heo', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Cá', 50000, 0.00, N'phần', 'mi_cay_ca.png', 1, N'Mì cay với cá tươi', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Tôm', 55000, 0.00, N'phần', 'mi_cay_tom.jpg', 1, N'Mì cay với tôm tươi', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Mực', 60000, 0.00, N'phần', 'mi_cay_muc.jpg', 1, N'Mì cay với mực tươi', 'C001'
FROM PRODUCT;

-- Mì Cay Đặc Biệt Khác
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Thập Cẩm', 65000, 0.00, N'phần', 'mi_cay_thap_cam.png', 1, N'Mì cay với nhiều loại thịt', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Tôm Viên', 55000, 0.00, N'phần', 'mi_cay_tom_vien.png', 1, N'Mì cay với tôm viên', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay XX', 60000, 0.00, N'phần', 'mi_cay_xx.png', 1, N'Mì cay XX đặc biệt', 'C001'
FROM PRODUCT;

COMMIT;

BEGIN
    DBMS_OUTPUT.PUT_LINE('✅ ĐÃ INSERT THÀNH CÔNG SẢN PHẨM MÌ CAY!');
END;
/
