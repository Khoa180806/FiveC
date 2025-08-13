-- ========================================
-- INSERT TẤT CẢ SẢN PHẨM TỪ HÌNH ẢNH VỚI PRODUCT_ID TỰ ĐỘNG
-- Dựa trên các folder: ANVAT, COM, Combo, KhaiVi, LAU, MI, NUOCUONG, Pancha, Them
-- Author: FiveC
-- Date: 2025
-- ========================================

SET SERVEROUTPUT ON;

BEGIN
    DBMS_OUTPUT.PUT_LINE('=== BẮT ĐẦU INSERT TẤT CẢ SẢN PHẨM VỚI ID TỰ ĐỘNG ===');
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
SELECT 'C002', N'Đồ Ăn Kèm', 1 FROM DUAL 
WHERE NOT EXISTS (SELECT 1 FROM CATE WHERE category_id = 'C002');

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

-- ========================================
-- 3. INSERT SẢN PHẨM LẨU (LAU)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 3: Insert sản phẩm Lẩu...');
END;
/

-- Lẩu Hải Sản
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu Hải Sản', 120000, 0.00, N'nồi', 'lau_hai_san.jpg', 1, N'Lẩu hải sản tươi ngon', 'C008'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu Hải Sản 2', 130000, 0.00, N'nồi', 'lau_hai_san_2.jpg', 1, N'Lẩu hải sản đặc biệt', 'C008'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu Hải Sản 3', 140000, 0.00, N'nồi', 'lau_hai_san_1.png', 1, N'Lẩu hải sản cao cấp', 'C008'
FROM PRODUCT;

-- Lẩu Thịt
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu Bò', 100000, 0.00, N'nồi', 'lau_bo.jpg', 1, N'Lẩu bò tươi ngon', 'C008'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu Bò 2', 110000, 0.00, N'nồi', 'lau_bo_2.jpg', 1, N'Lẩu bò đặc biệt', 'C008'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu Bò 3', 120000, 0.00, N'nồi', 'lau_bo_1.png', 1, N'Lẩu bò cao cấp', 'C008'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu Gà', 90000, 0.00, N'nồi', 'lau_ga.jpg', 1, N'Lẩu gà thơm ngon', 'C008'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu Gà 2', 100000, 0.00, N'nồi', 'lau_ga_1.png', 1, N'Lẩu gà đặc biệt', 'C008'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu Cá', 95000, 0.00, N'nồi', 'lau_ca.jpg', 1, N'Lẩu cá tươi', 'C008'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu Cá 2', 105000, 0.00, N'nồi', 'lau_ca_1.png', 1, N'Lẩu cá đặc biệt', 'C008'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu Heo', 85000, 0.00, N'nồi', 'lau_heo.jpg', 1, N'Lẩu heo thơm ngon', 'C008'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu Heo 2', 95000, 0.00, N'nồi', 'lau_heo_1.png', 1, N'Lẩu heo đặc biệt', 'C008'
FROM PRODUCT;

-- Lẩu Đặc Biệt
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu Collagen', 150000, 0.00, N'nồi', 'lau_collagen_1.png', 1, N'Lẩu collagen bổ dưỡng', 'C008'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu Đặc Biệt', 160000, 0.00, N'nồi', 'lau_dac_biet.png', 1, N'Lẩu đặc biệt thập cẩm', 'C008'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lẩu Đặc Biệt 2', 170000, 0.00, N'nồi', 'lau_dac_biet_2.jpg', 1, N'Lẩu đặc biệt cao cấp', 'C008'
FROM PRODUCT;

-- ========================================
-- 4. INSERT SẢN PHẨM NƯỚC UỐNG (NUOCUONG)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 4: Insert sản phẩm Nước Uống...');
END;
/

-- Nước Ngọt
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'7Up', 15000, 0.00, N'lon', 'nuoc_7up.webp', 1, N'Nước ngọt 7Up mát lạnh', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Coca Cola', 15000, 0.00, N'lon', 'nuoc_coca.png', 1, N'Nước ngọt Coca Cola', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Pepsi', 15000, 0.00, N'lon', 'nuoc_pepsi.webp', 1, N'Nước ngọt Pepsi', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mirinda', 15000, 0.00, N'lon', 'nuoc_mirinda.jpg', 1, N'Nước ngọt Mirinda', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Sting', 15000, 0.00, N'lon', 'nuoc_sting.png', 1, N'Nước tăng lực Sting', 'C003'
FROM PRODUCT;

-- Nước Ép
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Ép Cam', 25000, 0.00, N'ly', 'nuoc_cam_ep.jpg', 1, N'Nước ép cam tươi', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Ép Dâu', 30000, 0.00, N'ly', 'nuoc_ep_dau.jpeg', 1, N'Nước ép dâu tươi', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Ép Dừa Khóm', 25000, 0.00, N'ly', 'nuoc_ep_dua_khom.jpg', 1, N'Nước ép dừa khóm', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Ép Hàu', 35000, 0.00, N'ly', 'nuoc_ep_hau.jpg', 1, N'Nước ép hàu tươi', 'C003'
FROM PRODUCT;

-- Sinh Tố
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Sinh Tố Cam Xoài', 30000, 0.00, N'ly', 'nuoc_sinh_to_cam_xoai.jpg', 1, N'Sinh tố cam xoài', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Sinh Tố Đu Đủ', 25000, 0.00, N'ly', 'nuoc_sinh_to_du_du.jpg', 1, N'Sinh tố đu đủ', 'C003'
FROM PRODUCT;

-- Trà
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Trà Sữa', 25000, 0.00, N'ly', 'nuoc_tra_sua.jpg', 1, N'Trà sữa thơm ngon', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Trà Tắc', 20000, 0.00, N'ly', 'nuoc_tra_tac.jpg', 1, N'Trà tắc mát lạnh', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Trà Vải', 20000, 0.00, N'ly', 'nuoc_tra_vai.jpg', 1, N'Trà vải thơm ngon', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Trà Đào', 20000, 0.00, N'ly', 'nuoc_dao.jpg', 1, N'Trà đào mát lạnh', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Trà Chanh', 15000, 0.00, N'ly', 'nuoc_chanh_xay.jpg', 1, N'Trà chanh xay', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Trà Đá Chanh', 15000, 0.00, N'ly', 'nuoc_da_chanh.jpg', 1, N'Trà đá chanh', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Trà Đá Tuyết', 20000, 0.00, N'ly', 'nuoc_da_tuyet.jpg', 1, N'Trà đá tuyết', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Trà Tắc Thái', 25000, 0.00, N'ly', 'nuoc_tac_thai.jpg', 1, N'Trà tắc thái', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Trà Thái Xanh', 20000, 0.00, N'ly', 'nuoc_thai_xanh.jpg', 1, N'Trà thái xanh', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Lipton', 15000, 0.00, N'ly', 'nuoc_lipton.jpg', 1, N'Trà Lipton', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Matcha', 25000, 0.00, N'ly', 'nuoc_matcha.jpg', 1, N'Trà Matcha', 'C003'
FROM PRODUCT;

-- Nước Khác
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Soda', 15000, 0.00, N'ly', 'nuoc_soda.jpg', 1, N'Soda mát lạnh', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nước Nhiệt Đới', 20000, 0.00, N'ly', 'nuoc_nhiet_doi.jpg', 1, N'Nước nhiệt đới', 'C003'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Việt Quốc', 20000, 0.00, N'ly', 'nuoc_viet_quoc.jpg', 1, N'Nước việt quốc', 'C003'
FROM PRODUCT;

COMMIT;

BEGIN
    DBMS_OUTPUT.PUT_LINE('✅ ĐÃ INSERT THÀNH CÔNG PHẦN 1: MÌ CAY, LẨU, NƯỚC UỐNG!');
    DBMS_OUTPUT.PUT_LINE('Tiếp tục với phần 2: Đồ Ăn Vặt, Cơm, Combo, Khai Vị, Tráng Miệng...');
END;
/
