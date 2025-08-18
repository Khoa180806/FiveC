-- ========================================
-- INSERT SẢN PHẨM TỪ HÌNH ẢNH THỰC TẾ - PHẦN 2
-- Ăn Vặt, Khai Vị, Combo, Pancha, Thêm
-- Author: FiveC
-- Date: 2025
-- ========================================

SET SERVEROUTPUT ON;

BEGIN
    DBMS_OUTPUT.PUT_LINE('=== BẮT ĐẦU INSERT PHẦN 2: ĂN VẶT, KHAI VỊ, COMBO, PANCHA, THÊM ===');
END;
/

-- ========================================
-- 5. INSERT SẢN PHẨM ĂN VẶT (ANVAT)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 5: Insert sản phẩm Ăn Vặt...');
END;
/

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Ăn Vặt Chả Gà', 25000, 0.00, N'phần', 'anvat_cha_ga.jpg', 1, N'Chả gà ăn vặt', 'C006'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Bánh Prep', 20000, 0.00, N'phần', 'banh_prep.jpg', 1, N'Bánh prep', 'C006'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Cá Viên Chiên', 30000, 0.00, N'phần', 'ca_vien_chien.jpg', 1, N'Cá viên chiên giòn', 'C006'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Da Heo Chiên Giòn', 35000, 0.00, N'phần', 'da_heo_chien_gion.png', 1, N'Da heo chiên giòn', 'C006'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nấm Kim Châm Chiên Giòn', 25000, 0.00, N'phần', 'nam_kim_chan_chien_gion.png', 1, N'Nấm kim châm chiên giòn', 'C006'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Tôm Chiên', 40000, 0.00, N'phần', 'tom_chien.jpg', 1, N'Tôm chiên giòn', 'C006'
FROM PRODUCT;

-- ========================================
-- 6. INSERT SẢN PHẨM KHAI VỊ (KhaiVi)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 6: Insert sản phẩm Khai Vị...');
END;
/

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Bánh Plan', 30000, 0.00, N'phần', 'banh_Plan.jpg', 1, N'Bánh plan khai vị', 'C007'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Dâu Tây', 25000, 0.00, N'phần', 'dau_tay.jpg', 1, N'Dâu tây tươi', 'C007'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Kem Pho Mai', 20000, 0.00, N'phần', 'kem_pho_mai.jpg', 1, N'Kem pho mai', 'C007'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Kẹo Hồ Lô', 15000, 0.00, N'phần', 'keo_ho_lo.jpg', 1, N'Kẹo hồ lô', 'C007'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Khoai Pho Mai', 25000, 0.00, N'phần', 'khoai_pho_mai.jpg', 1, N'Khoai pho mai', 'C007'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Khoai Tây Chiên', 30000, 0.00, N'phần', 'khoai_tay_chien.png', 1, N'Khoai tây chiên giòn', 'C007'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Salad', 35000, 0.00, N'phần', 'salad.png', 1, N'Salad rau củ', 'C007'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Snack', 20000, 0.00, N'phần', 'snack.jpg', 1, N'Snack khai vị', 'C007'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Tôm Chiên Xù', 40000, 0.00, N'phần', 'tom_chien_xu.jpg', 1, N'Tôm chiên xù', 'C007'
FROM PRODUCT;

-- ========================================
-- 7. INSERT SẢN PHẨM COMBO
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 7: Insert sản phẩm Combo...');
END;
/

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Combo 129K', 129000, 0.05, N'set', 'combo_129.jpg', 1, N'Combo giá 129K', 'C005'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Combo 289K', 289000, 0.05, N'set', 'combo_289.jpg', 1, N'Combo giá 289K', 'C005'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Combo 319K', 319000, 0.10, N'set', 'combo_319.jpg', 1, N'Combo giá 319K', 'C005'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Combo 399K', 399000, 0.10, N'set', 'combo_399.jpg', 1, N'Combo giá 399K', 'C005'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Combo 629K', 629000, 0.10, N'set', 'combo_629.jpg', 1, N'Combo giá 629K', 'C005'
FROM PRODUCT;

-- ========================================
-- 8. INSERT SẢN PHẨM PANCHA
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 8: Insert sản phẩm Pancha...');
END;
/

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Chân Gà', 45000, 0.00, N'phần', 'chan_ga.png', 1, N'Chân gà', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Cơm', 25000, 0.00, N'phần', 'com.png', 1, N'Cơm trắng', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Củ Cải Vàng', 20000, 0.00, N'phần', 'cu_cai_vang.png', 1, N'Củ cải vàng', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Đùi Gà Rán', 35000, 0.00, N'phần', 'dui_ga_ran.png', 1, N'Đùi gà rán', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Kim Bap S', 25000, 0.00, N'phần', 'kim_bapS.png', 1, N'Kim bap size S', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Kim Bap M', 30000, 0.00, N'phần', 'kim_bap_M.png', 1, N'Kim bap size M', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Kim Bap Chiên', 35000, 0.00, N'phần', 'kim_bap_chien.png', 1, N'Kim bap chiên', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Kim Chi', 20000, 0.00, N'phần', 'kim_chi.png', 1, N'Kim chi', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Khoai Tây Chiên', 25000, 0.00, N'phần', 'khoai_tay_chien.png', 1, N'Khoai tây chiên', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mandu', 30000, 0.00, N'phần', 'mandu.png', 1, N'Mandu', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Pho Mai Que', 25000, 0.00, N'phần', 'pho_mai_que.png', 1, N'Pho mai que', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Rong Me', 20000, 0.00, N'phần', 'rong_me.png', 1, N'Rong me', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Sun Ga Chiên', 40000, 0.00, N'phần', 'sun_ga_chien.png', 1, N'Sun ga chiên', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Takoyaki', 30000, 0.00, N'phần', 'takoyaki.png', 1, N'Takoyaki', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Thanh Cua Chiên', 35000, 0.00, N'phần', 'thanh_cua_chien.png', 1, N'Thanh cua chiên', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Toppoki Pho Mai', 35000, 0.00, N'phần', 'toppoki_pho_mai.png', 1, N'Toppoki pho mai', 'C004'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Trứng Ngâm Tương', 25000, 0.00, N'phần', 'trung_ngam_tuong.png', 1, N'Trứng ngâm tương', 'C004'
FROM PRODUCT;

-- ========================================
-- 9. INSERT SẢN PHẨM THÊM (Them)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 9: Insert sản phẩm Thêm...');
END;
/

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Bắp Cải', 15000, 0.00, N'phần', 'bap_cai.png', 1, N'Bắp cải thêm', 'C002'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Bông Cải', 20000, 0.00, N'phần', 'bongcai.png', 1, N'Bông cải thêm', 'C002'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Cá', 25000, 0.00, N'phần', 'ca.png', 1, N'Cá thêm', 'C002'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Cá Viên', 20000, 0.00, N'phần', 'ca_vien.png', 1, N'Cá viên thêm', 'C002'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Chả Cá', 30000, 0.00, N'phần', 'cha_ca.png', 1, N'Chả cá thêm', 'C002'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mì', 15000, 0.00, N'phần', 'mi.png', 1, N'Mì thêm', 'C002'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Mực', 35000, 0.00, N'phần', 'muc.png', 1, N'Mực thêm', 'C002'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Nấm', 20000, 0.00, N'phần', 'nam.png', 1, N'Nấm thêm', 'C002'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Thanh Cua', 25000, 0.00, N'phần', 'thanh_cua.png', 1, N'Thanh cua thêm', 'C002'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Thịt Bò', 40000, 0.00, N'phần', 'thit_bo.png', 1, N'Thịt bò thêm', 'C002'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Thịt Bò Mỹ', 50000, 0.00, N'phần', 'thit_bo_my.png', 1, N'Thịt bò Mỹ thêm', 'C002'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Tôm', 35000, 0.00, N'phần', 'tom.png', 1, N'Tôm thêm', 'C002'
FROM PRODUCT;

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
SELECT 'P' || LPAD(TO_NUMBER(SUBSTR(NVL(MAX(product_id), 'P000'), 2)) + 1, 3, '0'), N'Xúc Xích', 25000, 0.00, N'phần', 'xuc_xich.png', 1, N'Xúc xích thêm', 'C002'
FROM PRODUCT;

COMMIT;

BEGIN
    DBMS_OUTPUT.PUT_LINE('✅ ĐÃ INSERT THÀNH CÔNG TẤT CẢ SẢN PHẨM!');
    DBMS_OUTPUT.PUT_LINE('=== HOÀN THÀNH QUÁ TRÌNH INSERT DỮ LIỆU ===');
    DBMS_OUTPUT.PUT_LINE('📊 Tổng cộng đã insert:');
    DBMS_OUTPUT.PUT_LINE('   - 8 sản phẩm Mì');
    DBMS_OUTPUT.PUT_LINE('   - 5 sản phẩm Lẩu');
    DBMS_OUTPUT.PUT_LINE('   - 17 sản phẩm Nước Uống');
    DBMS_OUTPUT.PUT_LINE('   - 6 sản phẩm Ăn Vặt');
    DBMS_OUTPUT.PUT_LINE('   - 9 sản phẩm Khai Vị');
    DBMS_OUTPUT.PUT_LINE('   - 5 sản phẩm Combo');
    DBMS_OUTPUT.PUT_LINE('   - 16 sản phẩm Pancha');
    DBMS_OUTPUT.PUT_LINE('   - 12 sản phẩm Thêm');
    DBMS_OUTPUT.PUT_LINE('🎉 Tổng cộng: 78 sản phẩm!');
END;
/
