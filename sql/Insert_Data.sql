-- ========================================
-- DATABASE QUẢN LÝ QUÁN MÌ CAY - INSERT DATA
-- Dữ liệu demo cho hệ thống
-- Author: FiveC
-- Version: 2.0
-- Date: 03/07/2025
-- Description: Dữ liệu demo cho Oracle 21c XE
-- ========================================

SET SERVEROUTPUT ON;

BEGIN
    DBMS_OUTPUT.PUT_LINE('=== BẮT ĐẦU INSERT DỮ LIỆU DEMO ===');
END;
/

-- ========================================
-- 1. INSERT USER_ROLE - VAI TRÒ NGƯỜI DÙNG
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 1: Insert dữ liệu USER_ROLE...');
END;
/

INSERT INTO USER_ROLE (role_id, name_role) VALUES ('R001', N'Manager');
INSERT INTO USER_ROLE (role_id, name_role) VALUES ('R002', N'Staff');

-- ========================================
-- 2. INSERT USER_ACCOUNT - TÀI KHOẢN NHÂN VIÊN
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 2: Insert dữ liệu USER_ACCOUNT...');
END;
/

INSERT INTO USER_ACCOUNT (user_id, username, pass, fullName, gender, email, phone_number, image, is_enabled, role_id) 
VALUES ('U001', 'manager01', 'manager123', N'Nguyễn Văn Manager', 1, 'manager@quanmicay.com', '0328456789', 'manager.jpg', 1, 'R001');

INSERT INTO USER_ACCOUNT (user_id, username, pass, fullName, gender, email, phone_number, image, is_enabled, role_id) 
VALUES ('U002', 'manager02', 'manager123', N'Trần Thị Quản Lý', 0, 'manager02@quanmicay.com', '0912345678', 'manager2.jpg', 1, 'R001');

INSERT INTO USER_ACCOUNT (user_id, username, pass, fullName, gender, email, phone_number, image, is_enabled, role_id) 
VALUES ('U003', 'staff01', 'staff123', N'Lê Văn Phục Vụ', 1, 'staff01@quanmicay.com', '0909876543', 'staff01.jpg', 1, 'R002');

INSERT INTO USER_ACCOUNT (user_id, username, pass, fullName, gender, email, phone_number, image, is_enabled, role_id) 
VALUES ('U004', 'staff02', 'staff123', N'Phạm Thị Bếp', 0, 'staff02@quanmicay.com', '0967123987', 'staff02.jpg', 1, 'R002');

INSERT INTO USER_ACCOUNT (user_id, username, pass, fullName, gender, email, phone_number, image, is_enabled, role_id) 
VALUES ('U005', 'staff03', 'staff123', N'Hoàng Văn Thu Ngân', 1, 'staff03@quanmicay.com', '0834567890', 'staff03.jpg', 1, 'R002');

-- ========================================
-- 3. INSERT CUSTOMER - KHÁCH HÀNG
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 3: Insert dữ liệu CUSTOMER...');
END;
/

INSERT INTO CUSTOMER (phone_number, customer_name, point_level, level_ranking) VALUES ('0965432109', N'Nguyễn Văn Khách', 1250, N'VIP');
INSERT INTO CUSTOMER (phone_number, customer_name, point_level, level_ranking) VALUES ('0913456789', N'Trần Thị Minh', 850, N'Gold');
INSERT INTO CUSTOMER (phone_number, customer_name, point_level, level_ranking) VALUES ('0908765432', N'Lê Hoàng Anh', 450, N'Silver');
INSERT INTO CUSTOMER (phone_number, customer_name, point_level, level_ranking) VALUES ('0976543210', N'Phạm Văn Đức', 150, N'Bronze');
INSERT INTO CUSTOMER (phone_number, customer_name, point_level, level_ranking) VALUES ('0821234567', N'Võ Thị Lan', 0, N'Thường');

-- ========================================
-- 4. INSERT CATE - DANH MỤC SẢN PHẨM
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 4: Insert dữ liệu CATE...');
END;
/

INSERT INTO CATE (category_id, category_name, is_available) VALUES ('C001', N'Mì Cay', 1);
INSERT INTO CATE (category_id, category_name, is_available) VALUES ('C002', N'Đồ Ăn Kèm', 1);
INSERT INTO CATE (category_id, category_name, is_available) VALUES ('C003', N'Nước Uống', 1);
INSERT INTO CATE (category_id, category_name, is_available) VALUES ('C004', N'Tráng Miệng', 1);
INSERT INTO CATE (category_id, category_name, is_available) VALUES ('C005', N'Combo', 1);
INSERT INTO CATE (category_id, category_name, is_available) VALUES ('C006', N'Đồ Ăn Vặt', 1);
INSERT INTO CATE (category_id, category_name, is_available) VALUES ('C007', N'Cơm', 1);
INSERT INTO CATE (category_id, category_name, is_available) VALUES ('C008', N'Lẩu', 1);
INSERT INTO CATE (category_id, category_name, is_available) VALUES ('C009', N'Khai Vị', 1);

-- ========================================
-- 5. INSERT PRODUCT - SẢN PHẨM
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 5: Insert dữ liệu PRODUCT...');
END;
/

-- Mì cay (C001)
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('M001', N'Mì cay bò', 65000, 0, N'phần', 'mi_cay_1.jpg', 1, NULL, 'C001');
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('M002', N'Mì cay hải sản', 75000, 0, N'phần', 'mi_cay_2.jpg', 1, NULL, 'C001');
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('M003', N'Mì cay xúc xích', 55000, 0, N'phần', 'mi_cay_3.jpg', 1, NULL, 'C001');
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('M004', N'Mì cay kim chi', 60000, 0, N'phần', 'mi_cay_4.png', 1, NULL, 'C001');

-- Đồ ăn kèm (C002)
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('AK01', N'Khoai tây chiên', 35000, 0, N'phần', 'anvat_khoai_tay.jpg', 1, NULL, 'C002');
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('AK02', N'Xúc xích chiên', 30000, 0, N'phần', 'anvat_xuc_xich.jpg', 1, NULL, 'C002');
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('AK03', N'Bánh gà', 45000, 0, N'phần', 'anvat_banh_ga.jpg', 1, NULL, 'C002');

-- Nước uống (C003)
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('NU01', N'Trà tắc', 25000, 0, N'ly', 'nuoc_tra_tac.jpg', 1, NULL, 'C003');
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('NU02', N'Coca', 20000, 0, N'lon', 'nuoc_coca.jpg', 1, NULL, 'C003');
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('NU03', N'Cam ép', 30000, 0, N'ly', 'nuoc_cam_ep.jpg', 1, NULL, 'C003');
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('NU04', N'7Up', 20000, 0, N'lon', 'nuoc_7up.webp', 1, NULL, 'C003');

-- Tráng miệng (C004)
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('TM01', N'Bánh flan', 20000, 0, N'phần', 'trang_mieng_banh_1.png', 1, NULL, 'C004');
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('TM02', N'Kem dừa', 25000, 0, N'ly', 'trang_mieng_banh_2.png', 1, NULL, 'C004');

-- Combo (C005)
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('CB01', N'Combo mì cay + nước', 95000, 0, N'combo', 'combo_mi_cay_1.jpg', 1, NULL, 'C005');
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('CB02', N'Combo 2 mì + 2 nước', 180000, 0, N'combo', 'combo_mi_cay_2.jpg', 1, NULL, 'C005');
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('CB03', N'Combo gia đình', 250000, 0, N'combo', 'combo_mi_cay_3.jpg', 1, NULL, 'C005');

-- Đồ ăn vặt (C006)
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('AV01', N'Bánh tráng trộn', 30000, 0, N'phần', 'anvat_banh_trang.jpg', 1, NULL, 'C006');
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('AV02', N'Chân gà sả tắc', 45000, 0, N'phần', 'anvat_chan_ga.jpg', 1, NULL, 'C006');
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('AV03', N'Xoài lắc', 25000, 0, N'phần', 'anvat_xoai_lac.jpg', 1, NULL, 'C006');

-- Cơm (C007)
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('COM1', N'Cơm gà xối mỡ', 55000, 0, N'phần', 'com_ga_ran.jpg', 1, NULL, 'C007');
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('COM2', N'Cơm bò xào', 65000, 0, N'phần', 'com_bo_xao.jpg', 1, NULL, 'C007');
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('COM3', N'Cơm heo xào', 60000, 0, N'phần', 'com_heo_xao.jpg', 1, NULL, 'C007');

-- Lẩu (C008)
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('LAU1', N'Lẩu bò', 220000, 0, N'nồi', 'lau_bo.jpg', 1, NULL, 'C008');
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('LAU2', N'Lẩu hải sản', 260000, 0, N'nồi', 'lau_hai_san.png', 1, NULL, 'C008');

-- Khai vị (C009)
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('KV01', N'Đậu hũ chiên', 30000, 0, N'phần', 'khaivi_dau_hu_chien.jpg', 1, NULL, 'C009');
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id)
VALUES ('KV02', N'Gỏi cuốn', 35000, 0, N'phần', 'khaivi_goi_cuon.jpg', 1, NULL, 'C009');

-- ========================================
-- 6. INSERT TABLE_FOR_CUSTOMER - BÀN ĂN
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 6: Insert dữ liệu TABLE_FOR_CUSTOMER...');
END;
/

INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (1, 2, 1);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (2, 4, 1);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (3, 2, 0);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (4, 6, 1);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (5, 4, 1);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (6, 2, 0);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (7, 8, 1);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (8, 4, 1);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (9, 2, 1);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (10, 6, 1);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (11, 4, 2);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (12, 2, 1);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (13, 8, 1);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (14, 10, 0);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (15, 6, 1);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (16, 8, 1);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (17, 8, 0);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (18, 10, 1);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (19, 10, 1);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (20, 6, 1);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (21, 6, 1);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (22, 8, 2);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (23, 10, 2);
INSERT INTO TABLE_FOR_CUSTOMER (table_number, amount, status) VALUES (24, 10, 2);

-- ========================================
-- 7. INSERT PAYMENT_METHOD - PHƯƠNG THỨC THANH TOÁN
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 7: Insert dữ liệu PAYMENT_METHOD...');
END;
/

INSERT INTO PAYMENT_METHOD (payment_method_id, method_name, is_enable) VALUES (1, N'Tiền mặt', 1);
INSERT INTO PAYMENT_METHOD (payment_method_id, method_name, is_enable) VALUES (2, N'Chuyển khoản', 1);
INSERT INTO PAYMENT_METHOD (payment_method_id, method_name, is_enable) VALUES (3, N'Thẻ tín dụng', 1);
INSERT INTO PAYMENT_METHOD (payment_method_id, method_name, is_enable) VALUES (4, N'Ví điện tử', 1);
INSERT INTO PAYMENT_METHOD (payment_method_id, method_name, is_enable) VALUES (5, N'QR Code', 1);

-- ========================================
-- 8. INSERT PAYMENT_HISTORY - LỊCH SỬ THANH TOÁN (25 RECORDS)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 8: Insert dữ liệu PAYMENT_HISTORY...');
END;
/

-- 5 payment history cho 5 hóa đơn hôm nay
INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (1, 125000, N'Thành công', N'Thanh toán hóa đơn bàn 1');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (2, 95000, N'Thành công', N'Chuyển khoản qua Vietcombank');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (3, 180000, N'Thành công', N'Thanh toán bằng thẻ tín dụng');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (1, 75000, N'Thành công', N'Tiền mặt bàn 5');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (5, 210000, N'Thành công', N'Quét QR VietQR');

-- 5 payment history cho 5 hóa đơn tuần này
INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (1, 135000, N'Thành công', N'Thanh toán hóa đơn bàn 8');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (3, 89000, N'Thành công', N'Thanh toán bằng thẻ tín dụng');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (2, 165000, N'Thành công', N'Chuyển khoản qua Techcombank');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (1, 72000, N'Thành công', N'Tiền mặt bàn 11');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (4, 195000, N'Thành công', N'Thanh toán qua ZaloPay');

-- 5 payment history cho 5 hóa đơn tháng này
INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (2, 145000, N'Thành công', N'Chuyển khoản qua BIDV');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (1, 98000, N'Thành công', N'Tiền mặt bàn 14');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (5, 175000, N'Thành công', N'Quét QR VietQR bàn 15');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (3, 115000, N'Thành công', N'Thanh toán bằng thẻ tín dụng');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (4, 225000, N'Thành công', N'Thanh toán qua MoMo bàn 17');

-- 5 payment history cho 5 hóa đơn quý này
INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (1, 155000, N'Thành công', N'Tiền mặt bàn 18');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (2, 105000, N'Thành công', N'Chuyển khoản qua Vietcombank');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (5, 185000, N'Thành công', N'Quét QR VietQR bàn 20');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (1, 85000, N'Thành công', N'Tiền mặt bàn 21');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (4, 235000, N'Thành công', N'Thanh toán qua ZaloPay bàn 22');

-- 5 payment history cho 5 hóa đơn năm này
INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (2, 165000, N'Thành công', N'Chuyển khoản qua Techcombank');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (1, 125000, N'Thành công', N'Tiền mặt bàn 24');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (5, 195000, N'Thành công', N'Quét QR VietQR bàn 25');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (3, 95000, N'Thành công', N'Thanh toán bằng thẻ tín dụng');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (4, 245000, N'Thành công', N'Thanh toán qua MoMo bàn 27');

-- ========================================
-- 9. INSERT BILL - HÓA ĐƠN (25 RECORDS - KHỚP VỚI PAYMENT_HISTORY)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 9: Insert dữ liệu BILL...');
END;
/

-- 5 HÓA ĐƠN TRONG HÔM NAY (SYSDATE) - payment_history_id 1-5
INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U003', '0965432109', 1, 1, 125000, TO_DATE(TO_CHAR(SYSDATE, 'DD/MM/YYYY') || ' 08:30:00', 'DD/MM/YYYY HH24:MI:SS'), TO_DATE(TO_CHAR(SYSDATE, 'DD/MM/YYYY') || ' 10:15:00', 'DD/MM/YYYY HH24:MI:SS'), N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U003', '0913456789', 2, 2, 95000, TO_DATE(TO_CHAR(SYSDATE, 'DD/MM/YYYY') || ' 11:00:00', 'DD/MM/YYYY HH24:MI:SS'), TO_DATE(TO_CHAR(SYSDATE, 'DD/MM/YYYY') || ' 12:45:00', 'DD/MM/YYYY HH24:MI:SS'), N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U004', '0908765432', 3, 4, 180000, TO_DATE(TO_CHAR(SYSDATE, 'DD/MM/YYYY') || ' 13:15:00', 'DD/MM/YYYY HH24:MI:SS'), TO_DATE(TO_CHAR(SYSDATE, 'DD/MM/YYYY') || ' 15:00:00', 'DD/MM/YYYY HH24:MI:SS'), N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U003', '0976543210', 4, 5, 75000, TO_DATE(TO_CHAR(SYSDATE, 'DD/MM/YYYY') || ' 16:30:00', 'DD/MM/YYYY HH24:MI:SS'), TO_DATE(TO_CHAR(SYSDATE, 'DD/MM/YYYY') || ' 17:45:00', 'DD/MM/YYYY HH24:MI:SS'), N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U004', '0821234567', 5, 7, 210000, TO_DATE(TO_CHAR(SYSDATE, 'DD/MM/YYYY') || ' 18:00:00', 'DD/MM/YYYY HH24:MI:SS'), TO_DATE(TO_CHAR(SYSDATE, 'DD/MM/YYYY') || ' 20:00:00', 'DD/MM/YYYY HH24:MI:SS'), N'Đã thanh toán');

-- 5 HÓA ĐƠN TRONG TUẦN NÀY (7 ngày gần nhất) - payment_history_id 6-10
INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U003', '0965432109', 6, 8, 135000, SYSDATE - 1, SYSDATE - 1 + 2/24, N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U004', '0913456789', 7, 9, 89000, SYSDATE - 2, SYSDATE - 2 + 1.5/24, N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U003', '0908765432', 8, 10, 165000, SYSDATE - 3, SYSDATE - 3 + 2.5/24, N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U003', '0976543210', 9, 11, 72000, SYSDATE - 4, SYSDATE - 4 + 1.25/24, N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U004', '0821234567', 10, 12, 195000, SYSDATE - 5, SYSDATE - 5 + 2.75/24, N'Đã thanh toán');

-- 5 HÓA ĐƠN TRONG THÁNG NÀY (30 ngày gần nhất) - payment_history_id 11-15
INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U004', '0965432109', 11, 13, 145000, SYSDATE - 7, SYSDATE - 7 + 2/24, N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U003', '0913456789', 12, 14, 98000, SYSDATE - 10, SYSDATE - 10 + 1.5/24, N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U004', '0908765432', 13, 15, 175000, SYSDATE - 12, SYSDATE - 12 + 2.25/24, N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U003', '0976543210', 14, 16, 115000, SYSDATE - 15, SYSDATE - 15 + 1.75/24, N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U004', '0821234567', 15, 17, 225000, SYSDATE - 18, SYSDATE - 18 + 3/24, N'Đã thanh toán');

-- 5 HÓA ĐƠN TRONG QUÝ NÀY (90 ngày gần nhất) - payment_history_id 16-20
INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U003', '0965432109', 16, 18, 155000, SYSDATE - 25, SYSDATE - 25 + 2.5/24, N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U004', '0913456789', 17, 19, 105000, SYSDATE - 30, SYSDATE - 30 + 1.5/24, N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U003', '0908765432', 18, 20, 185000, SYSDATE - 35, SYSDATE - 35 + 2.75/24, N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U003', '0976543210', 19, 21, 85000, SYSDATE - 40, SYSDATE - 40 + 1.25/24, N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U004', '0821234567', 20, 22, 235000, SYSDATE - 45, SYSDATE - 45 + 3.25/24, N'Đã thanh toán');

-- 5 HÓA ĐƠN TRONG NĂM NÀY (365 ngày gần nhất) - payment_history_id 21-25
INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U004', '0965432109', 21, 23, 165000, SYSDATE - 60, SYSDATE - 60 + 2.5/24, N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U003', '0913456789', 22, 24, 125000, SYSDATE - 75, SYSDATE - 75 + 2/24, N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U004', '0908765432', 23, 22, 195000, SYSDATE - 90, SYSDATE - 90 + 3/24, N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U003', '0976543210', 24, 23, 95000, SYSDATE - 120, SYSDATE - 120 + 1.5/24, N'Đã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) 
VALUES ('U004', '0821234567', 25, 24, 245000, SYSDATE - 150, SYSDATE - 150 + 3.5/24, N'Đã thanh toán');

-- ========================================
-- 10. INSERT BILL_DETAIL - CHI TIẾT HÓA ĐƠN
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 10: Insert dữ liệu BILL_DETAIL...');
END;
/

-- Mỗi hóa đơn sẽ có nhiều dòng chi tiết với đa dạng sản phẩm từ các danh mục khác nhau
-- Hóa đơn 1: Mì cay bò + Nước coca + Bánh flan
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 1), 'M001', 1, 65000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 1), 'NU02', 2, 20000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 1), 'TM01', 1, 20000, 0);

-- Hóa đơn 2: Combo mì cay + nước
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 2), 'CB01', 1, 95000, 0);

-- Hóa đơn 3: Combo 2 mì + 2 nước
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 3), 'CB02', 1, 180000, 0);

-- Hóa đơn 4: Mì cay hải sản + Khoai tây chiên + Trà tắc
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 4), 'M002', 1, 75000, 0);

-- Hóa đơn 5: Lẩu bò + Cơm gà + Nước uống
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 5), 'LAU1', 1, 220000, 0);

-- Hóa đơn 6: Mì cay kim chi + Xúc xích chiên + Cam ép + Kem dừa
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 6), 'M004', 1, 60000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 6), 'AK02', 1, 30000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 6), 'NU03', 1, 30000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 6), 'TM02', 1, 25000, 0);

-- Hóa đơn 7: Cơm bò xào + Đậu hũ chiên + 7Up
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 7), 'COM2', 1, 65000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 7), 'KV01', 1, 30000, 0);

-- Hóa đơn 8: Lẩu hải sản + Gỏi cuốn + Bánh tráng trộn
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 8), 'LAU2', 1, 260000, 0);

-- Hóa đơn 9: Mì cay xúc xích + Bánh gà + Trà tắc
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 9), 'M003', 1, 55000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 9), 'AK03', 1, 45000, 0);

-- Hóa đơn 10: Combo gia đình
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 10), 'CB03', 1, 250000, 0);

-- Hóa đơn 11: Cơm gà xối mỡ + Chân gà sả tắc + Coca
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 11), 'COM1', 1, 55000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 11), 'AV02', 1, 45000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 11), 'NU02', 2, 20000, 0);

-- Hóa đơn 12: Mì cay bò + Xoài lắc + Bánh flan
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 12), 'M001', 1, 65000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 12), 'AV03', 1, 25000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 12), 'TM01', 1, 20000, 0);

-- Hóa đơn 13: Cơm heo xào + Khoai tây chiên + Cam ép + Kem dừa
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 13), 'COM3', 1, 60000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 13), 'AK01', 1, 35000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 13), 'NU03', 1, 30000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 13), 'TM02', 1, 25000, 0);

-- Hóa đơn 14: Mì cay hải sản + Gỏi cuốn + 7Up + Bánh flan
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 14), 'M002', 1, 75000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 14), 'KV02', 1, 35000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 14), 'NU04', 1, 20000, 0);

-- Hóa đơn 15: Combo 2 mì + 2 nước + Bánh tráng trộn
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 15), 'CB02', 1, 180000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 15), 'AV01', 1, 30000, 0);

-- Hóa đơn 16: Lẩu bò + Đậu hũ chiên + Trà tắc
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 16), 'LAU1', 1, 220000, 0);

-- Hóa đơn 17: Mì cay kim chi + Bánh gà + Coca + Kem dừa
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 17), 'M004', 1, 60000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 17), 'AK03', 1, 45000, 0);

-- Hóa đơn 18: Cơm gà xối mỡ + Chân gà sả tắc + Cam ép + Bánh flan
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 18), 'COM1', 1, 55000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 18), 'AV02', 1, 45000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 18), 'NU03', 1, 30000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 18), 'TM01', 1, 20000, 0);

-- Hóa đơn 19: Mì cay xúc xích + Xoài lắc + 7Up
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 19), 'M003', 1, 55000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 19), 'AV03', 1, 25000, 0);

-- Hóa đơn 20: Combo gia đình + Gỏi cuốn
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 20), 'CB03', 1, 250000, 0);

-- Hóa đơn 21: Cơm bò xào + Khoai tây chiên + Trà tắc + Kem dừa
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 21), 'COM2', 1, 65000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 21), 'AK01', 1, 35000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 21), 'NU01', 1, 25000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 21), 'TM02', 1, 25000, 0);

-- Hóa đơn 22: Combo mì cay + nước + Bánh tráng trộn
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 22), 'CB01', 1, 95000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 22), 'AV01', 1, 30000, 0);

-- Hóa đơn 23: Lẩu hải sản + Đậu hũ chiên + Coca + Bánh flan
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 23), 'LAU2', 1, 260000, 0);

-- Hóa đơn 24: Mì cay bò + Xúc xích chiên + Cam ép
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 24), 'M001', 1, 65000, 0);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 24), 'AK02', 1, 30000, 0);

-- Hóa đơn 25: Combo gia đình + Chân gà sả tắc
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount)
VALUES ((SELECT bill_id FROM BILL WHERE payment_history_id = 25), 'CB03', 1, 250000, 0);

-- ========================================
-- COMMIT DỮ LIỆU
-- ========================================
COMMIT;

BEGIN
    DBMS_OUTPUT.PUT_LINE('✅ ĐÃ INSERT THÀNH CÔNG TẤT CẢ DỮ LIỆU DEMO!');
    DBMS_OUTPUT.PUT_LINE('=== KẾT THÚC QUÁ TRÌNH INSERT DỮ LIỆU ===');
END;
/

-- ========================================
-- KIỂM TRA DỮ LIỆU VỪA INSERT
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== THỐNG KÊ DỮ LIỆU ===');
END;
/

SELECT 'USER_ROLE' AS TABLE_NAME
    ,COUNT(*) AS TOTAL_RECORDS 
FROM USER_ROLE
UNION ALL
SELECT 'USER_ACCOUNT' AS TABLE_NAME
    ,COUNT(*) AS TOTAL_RECORDS 
FROM USER_ACCOUNT
UNION ALL
SELECT 'CUSTOMER' AS TABLE_NAME
    ,COUNT(*) AS TOTAL_RECORDS 
FROM CUSTOMER
UNION ALL
SELECT 'CATE' AS TABLE_NAME
    ,COUNT(*) AS TOTAL_RECORDS 
FROM CATE
UNION ALL
SELECT 'PRODUCT' AS TABLE_NAME
    ,COUNT(*) AS TOTAL_RECORDS 
FROM PRODUCT
UNION ALL
SELECT 'TABLE_FOR_CUSTOMER' AS TABLE_NAME
    ,COUNT(*) AS TOTAL_RECORDS 
FROM TABLE_FOR_CUSTOMER
UNION ALL
SELECT 'PAYMENT_METHOD' AS TABLE_NAME
    ,COUNT(*) AS TOTAL_RECORDS 
FROM PAYMENT_METHOD
UNION ALL
SELECT 'PAYMENT_HISTORY' AS TABLE_NAME
    ,COUNT(*) AS TOTAL_RECORDS 
FROM PAYMENT_HISTORY
UNION ALL
SELECT 'BILL' AS TABLE_NAME
    ,COUNT(*) AS TOTAL_RECORDS 
FROM BILL
UNION ALL
SELECT 'BILL_DETAIL' AS TABLE_NAME
    ,COUNT(*) AS TOTAL_RECORDS
FROM BILL_DETAIL;

