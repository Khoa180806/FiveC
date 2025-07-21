-- ========================================
-- DATABASE QUẢN L�? QU�?N MÌ CAY - INSERT DATA
-- Dữ liệu demo cho hệ thống
-- Author: FiveC
-- Version: 2.0
-- Date: 03/07/2025
-- Description: Dữ liệu demo cho Oracle 11g
-- ========================================

SET SERVEROUTPUT ON;

BEGIN
    DBMS_OUTPUT.PUT_LINE('=== BẮT �?ẦU INSERT DỮ LIỆU DEMO ===');
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
-- 3. INSERT CUSTOMER - KH�?CH HÀNG
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 3: Insert dữ liệu CUSTOMER...');
END;
/

INSERT INTO CUSTOMER (phone_number, customer_name, point_level, level_ranking) VALUES ('0965432109', N'Nguyễn Văn Khách', 1250, N'VIP');
INSERT INTO CUSTOMER (phone_number, customer_name, point_level, level_ranking) VALUES ('0913456789', N'Trần Thị Minh', 850, N'Gold');
INSERT INTO CUSTOMER (phone_number, customer_name, point_level, level_ranking) VALUES ('0908765432', N'Lê Hoàng Anh', 450, N'Silver');
INSERT INTO CUSTOMER (phone_number, customer_name, point_level, level_ranking) VALUES ('0976543210', N'Phạm Văn �?ức', 150, N'Bronze');
INSERT INTO CUSTOMER (phone_number, customer_name, point_level, level_ranking) VALUES ('0821234567', N'Võ Thị Lan', 0, N'Thư�?ng');

-- ========================================
-- 4. INSERT CATE - DANH MỤC SẢN PHẨM
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 4: Insert dữ liệu CATE...');
END;
/

INSERT INTO CATE (category_id, category_name, is_available) VALUES ('C001', N'Mì Cay', 1);
INSERT INTO CATE (category_id, category_name, is_available) VALUES ('C002', N'�?ồ Ăn Kèm', 1);
INSERT INTO CATE (category_id, category_name, is_available) VALUES ('C003', N'Nước Uống', 1);
INSERT INTO CATE (category_id, category_name, is_available) VALUES ('C004', N'Tráng Miệng', 1);
INSERT INTO CATE (category_id, category_name, is_available) VALUES ('C005', N'Combo', 1);

-- ========================================
-- 5. INSERT PRODUCT - SẢN PHẨM
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 5: Insert dữ liệu PRODUCT...');
END;
/

-- Mì Cay
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P001', N'Mì Cay Hàn Quốc Cấp 1', 45000, 0.00, N'phần', 'mi_cay_1.jpg', 1, N'�?ộ cay nhẹ, phù hợp ngư�?i mới ăn', 'C001');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P002', N'Mì Cay Hàn Quốc Cấp 2', 50000, 0.00, N'phần', 'mi_cay_2.jpg', 1, N'�?ộ cay vừa phải', 'C001');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P003', N'Mì Cay Hàn Quốc Cấp 3', 55000, 0.05, N'phần', 'mi_cay_3.jpg', 1, N'�?ộ cay cao, cho ngư�?i đã quen', 'C001');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P004', N'Mì Cay �?ặc Biệt Siêu Cay', 65000, 0.00, N'phần', 'mi_cay_db.jpg', 1, N'�?ộ cay cực cao, thử thách bản thân', 'C001');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P005', N'Mì Cay Phô Mai', 60000, 0.00, N'phần', 'mi_cay_phomai.jpg', 1, N'Mì cay kết hợp với phô mai thơm ngon', 'C001');

-- �?ồ Ăn Kèm
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P006', N'Chả Cá Hàn Quốc', 25000, 0.00, N'phần', 'cha_ca.jpg', 1, N'Chả cá tươi ngon, ăn kèm mì cay', 'C002');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P007', N'Kim Chi', 20000, 0.00, N'phần', 'kimchi.jpg', 1, N'Kim chi chua cay truy�?n thống', 'C002');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P008', N'Trứng Rán', 15000, 0.00, N'quả', 'trung_ran.jpg', 1, N'Trứng rán giòn, ăn kèm mì cay', 'C002');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P009', N'Xúc Xích Hàn Quốc', 30000, 0.00, N'phần', 'xuc_xich.jpg', 1, N'Xúc xích cay nồng đậm đà', 'C002');

-- Nước Uống
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P010', N'Trà �?á', 5000, 0.00, N'ly', 'tra_da.jpg', 1, N'Trà đá mát lạnh', 'C003');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P011', N'Coca Cola', 15000, 0.00, N'lon', 'coca.jpg', 1, N'Nước ng�?t có gas', 'C003');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P012', N'Nước Suối', 10000, 0.00, N'chai', 'nuoc_suoi.jpg', 1, N'Nước suối tinh khiết', 'C003');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P013', N'Trà Sữa Trân Châu', 25000, 0.00, N'ly', 'tra_sua.jpg', 1, N'Trà sữa ng�?t ngào với trân châu', 'C003');

-- Combo
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P014', N'Combo Mì Cay + Nước', 55000, 0.10, N'combo', 'combo1.jpg', 1, N'Mì cay cấp 2 + Coca Cola', 'C005');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P015', N'Combo �?ôi Bạn Thân', 120000, 0.15, N'combo', 'combo2.jpg', 1, N'2 mì cay + 2 nước + kim chi', 'C005');

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
select * from table_for_Customer
commit;

-- ========================================
-- 7. INSERT PAYMENT_METHOD - PHƯƠNG THỨC THANH TO�?N
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 7: Insert dữ liệu PAYMENT_METHOD...');
END;
/

INSERT INTO PAYMENT_METHOD (payment_method_id, method_name, is_enable) VALUES (1, N'Ti�?n mặt', 1);
INSERT INTO PAYMENT_METHOD (payment_method_id, method_name, is_enable) VALUES (2, N'Chuyển khoản', 1);
INSERT INTO PAYMENT_METHOD (payment_method_id, method_name, is_enable) VALUES (3, N'Thẻ tín dụng', 1);
INSERT INTO PAYMENT_METHOD (payment_method_id, method_name, is_enable) VALUES (4, N'Ví điện tử', 1);
INSERT INTO PAYMENT_METHOD (payment_method_id, method_name, is_enable) VALUES (5, N'QR Code', 1);

-- ========================================
-- 8. INSERT PAYMENT_HISTORY - LỊCH SỬ THANH TO�?N
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 8: Insert dữ liệu PAYMENT_HISTORY...');
END;
/

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (1, 125000, N'Thành công', N'Thanh toán hóa đơn bàn 1');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (2, 95000, N'Thành công', N'Chuyển khoản qua Vietcombank');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (4, 180000, N'Thành công', N'Thanh toán qua MoMo');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (1, 75000, N'Thành công', N'Ti�?n mặt bàn 5');

INSERT INTO PAYMENT_HISTORY (payment_method_id, total_amount, status, note) 
VALUES (5, 210000, N'Thành công', N'Quét QR VietQR');

-- ========================================
-- 9. INSERT BILL - HÓA �?ƠN
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 9: Insert dữ liệu BILL...');
END;
/

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkout, status) 
VALUES ('U003', '0965432109', 1, 1, 125000, SYSDATE - 2, N'�?ã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkout, status) 
VALUES ('U003', '0913456789', 2, 2, 95000, SYSDATE - 1, N'�?ã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkout, status) 
VALUES ('U004', '0908765432', 3, 4, 180000, SYSDATE - 1, N'�?ã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkout, status) 
VALUES ('U003', '0976543210', 4, 5, 75000, SYSDATE, N'�?ã thanh toán');

INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkout, status) 
VALUES ('U004', '0821234567', 5, 7, 210000, SYSDATE, N'�?ã thanh toán');

-- Hóa đơn đang phục vụ
INSERT INTO BILL (user_id, phone_number, table_number, total_amount, status) 
VALUES ('U003', '0965432109', 3, 110000, N'�?ang phục vụ');

INSERT INTO BILL (user_id, table_number, total_amount, status) 
VALUES ('U004', 6, 85000, N'�?ang phục vụ');

-- ========================================
-- 10. INSERT BILL_DETAIL - CHI TIẾT HÓA �?ƠN
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 10: Insert dữ liệu BILL_DETAIL...');
END;
/

-- Chi tiết hóa đơn 10000 (đã thanh toán)
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10000, 'P002', 2, 50000, 0.00);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10000, 'P006', 1, 25000, 0.00);

-- Chi tiết hóa đơn 10001 (đã thanh toán)
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10001, 'P001', 1, 45000, 0.00);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10001, 'P007', 1, 20000, 0.00);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10001, 'P011', 2, 15000, 0.00);

-- Chi tiết hóa đơn 10002 (đã thanh toán)
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10002, 'P015', 1, 120000, 0.15);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10002, 'P008', 2, 15000, 0.00);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10002, 'P013', 2, 25000, 0.00);

-- Chi tiết hóa đơn 10003 (đã thanh toán)
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10003, 'P003', 1, 55000, 0.05);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10003, 'P010', 2, 5000, 0.00);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10003, 'P009', 1, 30000, 0.00);

-- Chi tiết hóa đơn 10004 (đã thanh toán)
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10004, 'P014', 3, 55000, 0.10);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10004, 'P012', 2, 10000, 0.00);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10004, 'P007', 2, 20000, 0.00);

-- Chi tiết hóa đơn 10005 (đang phục vụ)
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10005, 'P004', 1, 65000, 0.00);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10005, 'P005', 1, 60000, 0.00);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10005, 'P011', 2, 15000, 0.00);

-- Chi tiết hóa đơn 10006 (đang phục vụ)
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10006, 'P002', 1, 50000, 0.00);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10006, 'P008', 1, 15000, 0.00);
INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (10006, 'P012', 2, 10000, 0.00);

-- ========================================
-- COMMIT DỮ LIỆU
-- ========================================
COMMIT;

BEGIN
    DBMS_OUTPUT.PUT_LINE('✅ �?Ã INSERT THÀNH CÔNG TẤT CẢ DỮ LIỆU DEMO!');
    DBMS_OUTPUT.PUT_LINE('=== KẾT THÚC QU�? TRÌNH INSERT DỮ LIỆU ===');
END;
/

-- ========================================
-- KIỂM TRA DỮ LIỆU VỪA INSERT
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== TH�?NG KÊ DỮ LIỆU ===');
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

-- Chạy SQL này trong Oracle để cập nhật ngày cho nhân viên đã tồn tại
UPDATE USER_ACCOUNT 
SET created_date = SYSDATE 
WHERE created_date IS NULL;

COMMIT;
