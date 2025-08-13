-- ========================================
-- INSERT SẢN PHẨM TỪ HÌNH ẢNH - PHẦN 2
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
VALUES ('P077', N'Bánh Phồng', 15000, 0.00, N'phần', 'anvat_banh_phong.jpg', 1, N'Bánh phồng tôm giòn rụm', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P078', N'Bánh Rán', 20000, 0.00, N'phần', 'anvat_banh_ran.jpg', 1, N'Bánh rán thơm ngon', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P079', N'Bánh Tôm', 25000, 0.00, N'phần', 'anvat_banh_tom.jpg', 1, N'Bánh tôm chiên giòn', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P080', N'Bánh Xèo', 30000, 0.00, N'phần', 'anvat_banh_xeo.jpg', 1, N'Bánh xèo truyền thống', 'C006');

-- Chả các loại
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P081', N'Chả Que', 25000, 0.00, N'phần', 'anvat_cha_que.jpg', 1, N'Chả que thơm ngon', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P082', N'Chả Ruốc', 20000, 0.00, N'phần', 'anvat_cha_ruoi.jpg', 1, N'Chả ruốc đặc biệt', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P083', N'Chả Tôm', 30000, 0.00, N'phần', 'anvat_cha_tom.jpg', 1, N'Chả tôm tươi ngon', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P084', N'Chả Bò', 25000, 0.00, N'phần', 'anvat_cha_bo.jpg', 1, N'Chả bò thơm ngon', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P085', N'Chả Bò 2', 25000, 0.00, N'phần', 'anvat_cha_bo_2.jpg', 1, N'Chả bò đặc biệt', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P086', N'Chả Bò 3', 25000, 0.00, N'phần', 'anvat_cha_bo_3.jpg', 1, N'Chả bò cao cấp', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P087', N'Chả Bò 4', 25000, 0.00, N'phần', 'anvat_cha_bo_4.jpg', 1, N'Chả bò thượng hạng', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P088', N'Chả Gà', 20000, 0.00, N'phần', 'anvat_cha_ga.jpg', 1, N'Chả gà thơm ngon', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P089', N'Chả Gà 2', 20000, 0.00, N'phần', 'anvat_cha_ga_2.jpg', 1, N'Chả gà đặc biệt', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P090', N'Chả Gà 3', 20000, 0.00, N'phần', 'anvat_cha_ga_3.jpg', 1, N'Chả gà cao cấp', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P091', N'Chả Gà 4', 20000, 0.00, N'phần', 'anvat_cha_ga_4.jpg', 1, N'Chả gà thượng hạng', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P092', N'Chả Cá', 25000, 0.00, N'phần', 'anvat_cha_ca.jpg', 1, N'Chả cá tươi ngon', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P093', N'Chả Cá 2', 25000, 0.00, N'phần', 'anvat_cha_ca_2.jpg', 1, N'Chả cá đặc biệt', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P094', N'Chả Cá 3', 25000, 0.00, N'phần', 'anvat_cha_ca_3.jpg', 1, N'Chả cá cao cấp', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P095', N'Chả Lụa', 20000, 0.00, N'phần', 'anvat_cha_lua.jpg', 1, N'Chả lụa truyền thống', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P096', N'Chả Heo', 20000, 0.00, N'phần', 'anvat_cha_heo.jpg', 1, N'Chả heo thơm ngon', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P097', N'Chả Heo 2', 20000, 0.00, N'phần', 'anvat_cha_heo_2.jpg', 1, N'Chả heo đặc biệt', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P098', N'Chả Heo 3', 20000, 0.00, N'phần', 'anvat_cha_heo_3.jpg', 1, N'Chả heo cao cấp', 'C006');

-- Mì Cay Michigo
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P099', N'Mì Cay Michigo', 40000, 0.00, N'phần', 'anvat_mi_cay_michigo.jpg', 1, N'Mì cay Michigo đặc biệt', 'C006');

-- Bánh unnamed
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P100', N'Bánh 1', 15000, 0.00, N'phần', 'anvat_banh_1.png', 1, N'Bánh đặc biệt 1', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P101', N'Bánh 2', 15000, 0.00, N'phần', 'anvat_banh_2.png', 1, N'Bánh đặc biệt 2', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P102', N'Bánh 3', 15000, 0.00, N'phần', 'anvat_banh_3.png', 1, N'Bánh đặc biệt 3', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P103', N'Bánh 4', 15000, 0.00, N'phần', 'anvat_banh_4.png', 1, N'Bánh đặc biệt 4', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P104', N'Bánh 5', 15000, 0.00, N'phần', 'anvat_banh_5.png', 1, N'Bánh đặc biệt 5', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P105', N'Bánh 6', 15000, 0.00, N'phần', 'anvat_banh_6.png', 1, N'Bánh đặc biệt 6', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P106', N'Bánh 7', 15000, 0.00, N'phần', 'anvat_banh_7.png', 1, N'Bánh đặc biệt 7', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P107', N'Bánh 8', 15000, 0.00, N'phần', 'anvat_banh_8.png', 1, N'Bánh đặc biệt 8', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P108', N'Bánh 9', 15000, 0.00, N'phần', 'anvat_banh_9.png', 1, N'Bánh đặc biệt 9', 'C006');

-- ========================================
-- 6. INSERT SẢN PHẨM CƠM (COM)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 6: Insert sản phẩm Cơm...');
END;
/

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P109', N'Cơm Gà Rán', 45000, 0.00, N'phần', 'com_ga_ran.jpg', 1, N'Cơm gà rán thơm ngon', 'C007');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P110', N'Cơm Bò Xào', 50000, 0.00, N'phần', 'com_bo_xao.jpg', 1, N'Cơm bò xào đậm đà', 'C007');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P111', N'Cơm Cá Rán', 40000, 0.00, N'phần', 'com_ca_ran.jpg', 1, N'Cơm cá rán giòn', 'C007');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P112', N'Cơm Heo Xào', 35000, 0.00, N'phần', 'com_heo_xao.jpg', 1, N'Cơm heo xào thơm ngon', 'C007');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P113', N'Cơm Gà Xào', 40000, 0.00, N'phần', 'com_ga_xao.jpg', 1, N'Cơm gà xào đặc biệt', 'C007');

-- ========================================
-- 7. INSERT SẢN PHẨM COMBO
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 7: Insert sản phẩm Combo...');
END;
/

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P114', N'Combo Mì Cay 1', 80000, 0.10, N'combo', 'combo_mi_cay_1.jpg', 1, N'Combo mì cay + nước + đồ ăn kèm', 'C005');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P115', N'Combo Mì Cay 2', 90000, 0.10, N'combo', 'combo_mi_cay_2.jpg', 1, N'Combo mì cay đặc biệt', 'C005');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P116', N'Combo Mì Cay 3', 100000, 0.10, N'combo', 'combo_mi_cay_3.jpg', 1, N'Combo mì cay cao cấp', 'C005');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P117', N'Combo Mì Cay 4', 110000, 0.10, N'combo', 'combo_mi_cay_4.jpg', 1, N'Combo mì cay thượng hạng', 'C005');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P118', N'Combo Mì Cay 5', 120000, 0.10, N'combo', 'combo_mi_cay_5.png', 1, N'Combo mì cay đặc biệt 5', 'C005');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P119', N'Combo Mì Cay 6', 130000, 0.10, N'combo', 'combo_mi_cay_6.png', 1, N'Combo mì cay đặc biệt 6', 'C005');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P120', N'Combo Mì Cay 7', 140000, 0.10, N'combo', 'combo_mi_cay_7.png', 1, N'Combo mì cay đặc biệt 7', 'C005');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P121', N'Combo Mì Cay 8', 150000, 0.10, N'combo', 'combo_mi_cay_8.png', 1, N'Combo mì cay đặc biệt 8', 'C005');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P122', N'Combo Mì Cay 9', 160000, 0.10, N'combo', 'combo_mi_cay_9.png', 1, N'Combo mì cay đặc biệt 9', 'C005');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P123', N'Combo Mì Cay 10', 170000, 0.10, N'combo', 'combo_mi_cay_10.jpg', 1, N'Combo mì cay đặc biệt 10', 'C005');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P124', N'Combo Lẩu Collagen', 200000, 0.15, N'combo', 'combo_lau_collagen.webp', 1, N'Combo lẩu collagen bổ dưỡng', 'C005');

-- ========================================
-- 8. INSERT SẢN PHẨM KHAI VỊ (KhaiVi)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 8: Insert sản phẩm Khai Vị...');
END;
/

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P125', N'Gỏi Cuốn', 30000, 0.00, N'phần', 'khaivi_goi_cuon.jpg', 1, N'Gỏi cuốn tươi ngon', 'C009');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P126', N'Chả Rán', 25000, 0.00, N'phần', 'khaivi_cha_ran.jpg', 1, N'Chả rán giòn thơm', 'C009');

-- ========================================
-- 9. INSERT SẢN PHẨM TRÁNG MIỆNG (Pancha)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 9: Insert sản phẩm Tráng Miệng...');
END;
/

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P127', N'Bánh Tráng Miệng 1', 20000, 0.00, N'phần', 'trang_mieng_banh_1.png', 1, N'Bánh tráng miệng đặc biệt 1', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P128', N'Bánh Tráng Miệng 2', 20000, 0.00, N'phần', 'trang_mieng_banh_2.png', 1, N'Bánh tráng miệng đặc biệt 2', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P129', N'Bánh Tráng Miệng 3', 20000, 0.00, N'phần', 'trang_mieng_banh_3.png', 1, N'Bánh tráng miệng đặc biệt 3', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P130', N'Bánh Tráng Miệng 4', 20000, 0.00, N'phần', 'trang_mieng_banh_4.png', 1, N'Bánh tráng miệng đặc biệt 4', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P131', N'Bánh Tráng Miệng 5', 20000, 0.00, N'phần', 'trang_mieng_banh_5.png', 1, N'Bánh tráng miệng đặc biệt 5', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P132', N'Bánh Tráng Miệng 6', 20000, 0.00, N'phần', 'trang_mieng_banh_6.png', 1, N'Bánh tráng miệng đặc biệt 6', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P133', N'Bánh Tráng Miệng 7', 20000, 0.00, N'phần', 'trang_mieng_banh_7.png', 1, N'Bánh tráng miệng đặc biệt 7', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P134', N'Bánh Tráng Miệng 8', 20000, 0.00, N'phần', 'trang_mieng_banh_8.png', 1, N'Bánh tráng miệng đặc biệt 8', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P135', N'Bánh Tráng Miệng 9', 20000, 0.00, N'phần', 'trang_mieng_banh_9.png', 1, N'Bánh tráng miệng đặc biệt 9', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P136', N'Bánh Tráng Miệng 10', 20000, 0.00, N'phần', 'trang_mieng_banh_10.png', 1, N'Bánh tráng miệng đặc biệt 10', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P137', N'Bánh Tráng Miệng 11', 20000, 0.00, N'phần', 'trang_mieng_banh_11.png', 1, N'Bánh tráng miệng đặc biệt 11', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P138', N'Bánh Tráng Miệng 12', 20000, 0.00, N'phần', 'trang_mieng_banh_12.png', 1, N'Bánh tráng miệng đặc biệt 12', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P139', N'Bánh Tráng Miệng 13', 20000, 0.00, N'phần', 'trang_mieng_banh_13.png', 1, N'Bánh tráng miệng đặc biệt 13', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P140', N'Bánh Tráng Miệng 14', 20000, 0.00, N'phần', 'trang_mieng_banh_14.png', 1, N'Bánh tráng miệng đặc biệt 14', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P141', N'Bánh Tráng Miệng 15', 20000, 0.00, N'phần', 'trang_mieng_banh_15.png', 1, N'Bánh tráng miệng đặc biệt 15', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P142', N'Bánh Tráng Miệng 16', 20000, 0.00, N'phần', 'trang_mieng_banh_16.png', 1, N'Bánh tráng miệng đặc biệt 16', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P143', N'Bánh Tráng Miệng 17', 20000, 0.00, N'phần', 'trang_mieng_banh_17.png', 1, N'Bánh tráng miệng đặc biệt 17', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P144', N'Bánh Tráng Miệng 18', 20000, 0.00, N'phần', 'trang_mieng_banh_18.png', 1, N'Bánh tráng miệng đặc biệt 18', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P145', N'Bánh Tráng Miệng 19', 20000, 0.00, N'phần', 'trang_mieng_banh_19.png', 1, N'Bánh tráng miệng đặc biệt 19', 'C004');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P146', N'Bánh Tráng Miệng 20', 20000, 0.00, N'phần', 'trang_mieng_banh_20.png', 1, N'Bánh tráng miệng đặc biệt 20', 'C004');

-- ========================================
-- 10. INSERT SẢN PHẨM THÊM (Them)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 10: Insert sản phẩm Thêm...');
END;
/

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P147', N'Bánh Thêm 1', 15000, 0.00, N'phần', 'them_banh_1.png', 1, N'Bánh thêm đặc biệt 1', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P148', N'Bánh Thêm 2', 15000, 0.00, N'phần', 'them_banh_2.png', 1, N'Bánh thêm đặc biệt 2', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P149', N'Bánh Thêm 3', 15000, 0.00, N'phần', 'them_banh_3.png', 1, N'Bánh thêm đặc biệt 3', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P150', N'Bánh Thêm 4', 15000, 0.00, N'phần', 'them_banh_4.png', 1, N'Bánh thêm đặc biệt 4', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P151', N'Bánh Thêm 5', 15000, 0.00, N'phần', 'them_banh_5.png', 1, N'Bánh thêm đặc biệt 5', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P152', N'Bánh Thêm 6', 15000, 0.00, N'phần', 'them_banh_6.png', 1, N'Bánh thêm đặc biệt 6', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P153', N'Bánh Thêm 7', 15000, 0.00, N'phần', 'them_banh_7.png', 1, N'Bánh thêm đặc biệt 7', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P154', N'Bánh Thêm 8', 15000, 0.00, N'phần', 'them_banh_8.png', 1, N'Bánh thêm đặc biệt 8', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P155', N'Bánh Thêm 9', 15000, 0.00, N'phần', 'them_banh_9.png', 1, N'Bánh thêm đặc biệt 9', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P156', N'Bánh Thêm 10', 15000, 0.00, N'phần', 'them_banh_10.png', 1, N'Bánh thêm đặc biệt 10', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P157', N'Bánh Thêm 11', 15000, 0.00, N'phần', 'them_banh_11.png', 1, N'Bánh thêm đặc biệt 11', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P158', N'Bánh Thêm 12', 15000, 0.00, N'phần', 'them_banh_12.png', 1, N'Bánh thêm đặc biệt 12', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P159', N'Bánh Thêm 13', 15000, 0.00, N'phần', 'them_banh_13.png', 1, N'Bánh thêm đặc biệt 13', 'C006');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P160', N'Bánh Thêm 14', 15000, 0.00, N'phần', 'them_banh_14.png', 1, N'Bánh thêm đặc biệt 14', 'C006');

COMMIT;

-- ========================================
-- THỐNG KÊ DỮ LIỆU
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('✅ ĐÃ INSERT THÀNH CÔNG PHẦN 2: ĐỒ ĂN VẶT, CƠM, COMBO, KHAI VỊ, TRÁNG MIỆNG!');
    DBMS_OUTPUT.PUT_LINE('=== THỐNG KÊ SẢN PHẨM THEO DANH MỤC ===');
END;
/

SELECT c.category_name
    ,COUNT(p.product_id) AS TOTAL_PRODUCTS
    ,MIN(p.price) AS MIN_PRICE
    ,MAX(p.price) AS MAX_PRICE
    ,AVG(p.price) AS AVG_PRICE
FROM PRODUCT p
JOIN CATE c ON p.category_id = c.category_id
GROUP BY c.category_id, c.category_name
ORDER BY c.category_id;

BEGIN
    DBMS_OUTPUT.PUT_LINE('=== HOÀN THÀNH INSERT TẤT CẢ SẢN PHẨM TỪ HÌNH ẢNH ===');
    DBMS_OUTPUT.PUT_LINE('🎉 Tổng cộng: 160 sản phẩm đã được thêm vào database!');
END;
/
