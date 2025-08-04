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

INSERT INTO USER_ACCOUNT (user_id, username, pass, fullName, email, phone_number, image, is_enabled, role_id) 
VALUES ('U001', 'manager01', 'manager123', N'Nguyễn Văn Manager', 'manager@quanmicay.com', '0328456789', 'manager.jpg', 1, 'R001');

INSERT INTO USER_ACCOUNT (user_id, username, pass, fullName, email, phone_number, image, is_enabled, role_id) 
VALUES ('U002', 'manager02', 'manager123', N'Trần Thị Quản Lý', 'manager02@quanmicay.com', '0912345678', 'manager2.jpg', 1, 'R001');

INSERT INTO USER_ACCOUNT (user_id, username, pass, fullName, email, phone_number, image, is_enabled, role_id) 
VALUES ('U003', 'staff01', 'staff123', N'Lê Văn Phục Vụ', 'staff01@quanmicay.com', '0909876543', 'staff01.jpg', 1, 'R002');

INSERT INTO USER_ACCOUNT (user_id, username, pass, fullName, email, phone_number, image, is_enabled, role_id) 
VALUES ('U004', 'staff02', 'staff123', N'Phạm Thị Bếp', 'staff02@quanmicay.com', '0967123987', 'staff02.jpg', 1, 'R002');

INSERT INTO USER_ACCOUNT (user_id, username, pass, fullName, email, phone_number, image, is_enabled, role_id) 
VALUES ('U005', 'staff03', 'staff123', N'Hoàng Văn Thu Ngân', 'staff03@quanmicay.com', '0834567890', 'staff03.jpg', 1, 'R002');

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

-- ========================================
-- 5. INSERT PRODUCT - SẢN PHẨM
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 5: Insert dữ liệu PRODUCT...');
END;
/

-- Mì Cay
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P001', N'Mì Cay Hàn Quốc Cấp 1', 45000, 0.00, N'phần', 'mi_cay_1.jpg', 1, N'Độ cay nhẹ, phù hợp người mới ăn', 'C001');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P002', N'Mì Cay Hàn Quốc Cấp 2', 50000, 0.00, N'phần', 'mi_cay_2.jpg', 1, N'Độ cay vừa phải', 'C001');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P003', N'Mì Cay Hàn Quốc Cấp 3', 55000, 0.05, N'phần', 'mi_cay_3.jpg', 1, N'Độ cay cao, cho người đã quen', 'C001');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P004', N'Mì Cay Đặc Biệt Siêu Cay', 65000, 0.00, N'phần', 'mi_cay_db.jpg', 1, N'Độ cay cực cao, thử thách bản thân', 'C001');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P005', N'Mì Cay Phô Mai', 60000, 0.00, N'phần', 'mi_cay_phomai.jpg', 1, N'Mì cay kết hợp với phô mai thơm ngon', 'C001');

-- Đồ Ăn Kèm
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P006', N'Chả Cá Hàn Quốc', 25000, 0.00, N'phần', 'cha_ca.jpg', 1, N'Chả cá tươi ngon, ăn kèm mì cay', 'C002');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P007', N'Kim Chi', 20000, 0.00, N'phần', 'kimchi.jpg', 1, N'Kim chi chua cay truyền thống', 'C002');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P008', N'Trứng Rán', 15000, 0.00, N'quả', 'trung_ran.jpg', 1, N'Trứng rán giòn, ăn kèm mì cay', 'C002');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P009', N'Xúc Xích Hàn Quốc', 30000, 0.00, N'phần', 'xuc_xich.jpg', 1, N'Xúc xích cay nồng đậm đà', 'C002');

-- Nước Uống
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P010', N'Trà Đá', 5000, 0.00, N'ly', 'tra_da.jpg', 1, N'Trà đá mát lạnh', 'C003');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P011', N'Coca Cola', 15000, 0.00, N'lon', 'coca.jpg', 1, N'Nước ngọt có gas', 'C003');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P012', N'Nước Suối', 10000, 0.00, N'chai', 'nuoc_suoi.jpg', 1, N'Nước suối tinh khiết', 'C003');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P013', N'Trà Sữa Trân Châu', 25000, 0.00, N'ly', 'tra_sua.jpg', 1, N'Trà sữa ngọt ngào với trân châu', 'C003');

-- Combo
INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P014', N'Combo Mì Cay + Nước', 55000, 0.10, N'combo', 'combo1.jpg', 1, N'Mì cay cấp 2 + Coca Cola', 'C005');

INSERT INTO PRODUCT (product_id, product_name, price, discount, unit, image, is_available, note, category_id) 
VALUES ('P015', N'Combo Đôi Bạn Thân', 120000, 0.15, N'combo', 'combo2.jpg', 1, N'2 mì cay + 2 nước + kim chi', 'C005');

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

