-- ========================================
-- INSERT SẢN PHẨM TỪ HÌNH ẢNH THỰC TẾ
-- Dựa trên các folder: ANVAT, KhaiVi, LAU, Combo, MI, NUOCUONG, Pancha, Them
-- Author: FiveC
-- Date: 2025
-- ========================================

SET SERVEROUTPUT ON;

BEGIN
    DBMS_OUTPUT.PUT_LINE('=== BẮT ĐẦU INSERT SẢN PHẨM TỪ HÌNH ẢNH THỰC TẾ ===');
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
SELECT 'C001', N'Mì', 1 FROM DUAL 
WHERE NOT EXISTS (SELECT 1 FROM CATE WHERE category_id = 'C001');

INSERT INTO CATE (category_id, category_name, is_available) 
SELECT 'C002', N'Thêm', 1 FROM DUAL 
WHERE NOT EXISTS (SELECT 1 FROM CATE WHERE category_id = 'C002');

INSERT INTO CATE (category_id, category_name, is_available) 
SELECT 'C003', N'Nước Uống', 1 FROM DUAL 
WHERE NOT EXISTS (SELECT 1 FROM CATE WHERE category_id = 'C003');

INSERT INTO CATE (category_id, category_name, is_available) 
SELECT 'C004', N'Pancha', 1 FROM DUAL 
WHERE NOT EXISTS (SELECT 1 FROM CATE WHERE category_id = 'C004');

INSERT INTO CATE (category_id, category_name, is_available) 
SELECT 'C005', N'Combo', 1 FROM DUAL 
WHERE NOT EXISTS (SELECT 1 FROM CATE WHERE category_id = 'C005');

INSERT INTO CATE (category_id, category_name, is_available) 
SELECT 'C006', N'Ăn Vặt', 1 FROM DUAL 
WHERE NOT EXISTS (SELECT 1 FROM CATE WHERE category_id = 'C006');

INSERT INTO CATE (category_id, category_name, is_available) 
SELECT 'C007', N'Khai Vị', 1 FROM DUAL 
WHERE NOT EXISTS (SELECT 1 FROM CATE WHERE category_id = 'C007');

INSERT INTO CATE (category_id, category_name, is_available) 
SELECT 'C008', N'Lẩu', 1 FROM DUAL 
WHERE NOT EXISTS (SELECT 1 FROM CATE WHERE category_id = 'C008');

COMMIT;

-- ========================================
-- 2. INSERT SẢN PHẨM MÌ (MI)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 2: Insert sản phẩm Mì...');
END;
/

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
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Gà 2', 55000, 0.00, N'phần', 'mi_cay_ga_2.png', 1, N'Mì cay gà đặc biệt', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Cá', 50000, 0.00, N'phần', 'mi_cay_ca.png', 1, N'Mì cay với cá tươi', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Thập Cẩm', 65000, 0.00, N'phần', 'mi_cay_thap_cam.png', 1, N'Mì cay với nhiều loại thịt', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay Tôm Viên', 55000, 0.00, N'phần', 'mi_cay_tom_vien.png', 1, N'Mì cay với tôm viên', 'C001'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì Cay XX', 60000, 0.00, N'phần', 'mi_cay_xx.png', 1, N'Mì cay XX đặc biệt', 'C001'
FROM PRODUCT;

-- ========================================
-- 3. INSERT SẢN PHẨM LẨU (LAU)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 3: Insert sản phẩm Lẩu...');
END;
/

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu 1 Người', 120000, 0.00, N'nồi', 'lau_1_ng.png', 1, N'Lẩu dành cho 1 người', 'C008'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu Cặp Đôi', 200000, 0.00, N'nồi', 'lau_cap_doi.png', 1, N'Lẩu dành cho 2 người', 'C008'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu 2-3 Người', 280000, 0.00, N'nồi', 'lau2_3_ng.png', 1, N'Lẩu dành cho 2-3 người', 'C008'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu 3-5 Người', 350000, 0.00, N'nồi', 'lau3_5_ng.png', 1, N'Lẩu dành cho 3-5 người', 'C008'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu Gia Đình', 450000, 0.00, N'nồi', 'lau_gia_dinh.png', 1, N'Lẩu dành cho gia đình', 'C008'
FROM PRODUCT;

-- ========================================
-- 4. INSERT SẢN PHẨM NƯỚC UỐNG (NUOCUONG)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 4: Insert sản phẩm Nước Uống...');
END;
/

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nước Cam Ép', 25000, 0.00, N'ly', 'nuoc_cam_ep.jpg', 1, N'Nước ép cam tươi', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nước Chanh Xay', 15000, 0.00, N'ly', 'nuoc_chanh_xay.jpg', 1, N'Nước chanh xay', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nước Đá Chanh', 15000, 0.00, N'ly', 'nuoc_da_chanh.jpg', 1, N'Nước đá chanh', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nước Đá Tuyết', 20000, 0.00, N'ly', 'nuoc_da_tuyet.jpg', 1, N'Nước đá tuyết', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nước Đào', 20000, 0.00, N'ly', 'nuoc_dao.jpg', 1, N'Nước đào', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nước Ép Dâu', 30000, 0.00, N'ly', 'nuoc_ep_dau.jpeg', 1, N'Nước ép dâu tươi', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nước Ép Dừa Khóm', 25000, 0.00, N'ly', 'nuoc_ep_dua_khom.jpg', 1, N'Nước ép dừa khóm', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nước Ép Hàu', 35000, 0.00, N'ly', 'nuoc_ep_hau.jpg', 1, N'Nước ép hàu tươi', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nước Lipton', 15000, 0.00, N'ly', 'nuoc_lipton.jpg', 1, N'Nước trà Lipton', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nước Matcha', 25000, 0.00, N'ly', 'nuoc_matcha.jpg', 1, N'Nước matcha', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nước Soda', 15000, 0.00, N'ly', 'nuoc_soda.jpg', 1, N'Nước soda', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nước Nhiệt Đới', 20000, 0.00, N'ly', 'nuoc_nhiet_doi.jpg', 1, N'Nước nhiệt đới', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nước Việt Quốc', 20000, 0.00, N'ly', 'nuoc_viet_quoc.jpg', 1, N'Nước việt quốc', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nước Trà Tắc', 20000, 0.00, N'ly', 'nuoc_tra_tac.jpg', 1, N'Nước trà tắc', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nước Trà Vải', 20000, 0.00, N'ly', 'nuoc_tra_vai.jpg', 1, N'Nước trà vải', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nước Tắc Thái', 25000, 0.00, N'ly', 'nuoc_tac_thai.jpg', 1, N'Nước tắc thái', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Sinh Tố Đu Đủ', 25000, 0.00, N'ly', 'SinhToĐUU.jpg', 1, N'Sinh tố đu đủ', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Sinh Tố Cam Xoài', 30000, 0.00, N'ly', 'nuoc_sinh_to_cam_xoai.jpg', 1, N'Sinh tố cam xoài', 'C003'
FROM PRODUCT;

COMMIT;

BEGIN
    DBMS_OUTPUT.PUT_LINE('✅ ĐÃ INSERT THÀNH CÔNG PHẦN 1: MÌ, LẨU, NƯỚC UỐNG!');
    DBMS_OUTPUT.PUT_LINE('Tiếp tục với phần 2: Ăn Vặt, Khai Vị, Combo, Pancha, Thêm...');
END;
/
