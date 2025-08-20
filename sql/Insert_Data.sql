-- ========================================
-- DATABASE QUẢN LÝ QUÁN MÌ CAY - INSERT DATA (BASIC SEED ONLY)
-- Chỉ khởi tạo dữ liệu cơ bản: USER_ROLE, USER_ACCOUNT, CUSTOMER,
-- TABLE_FOR_CUSTOMER, PAYMENT_METHOD. Không chèn CATE/PRODUCT/BILL/DETAIL.
-- Author: FiveC
-- Version: 2.1 (Basic)
-- Date: 03/07/2025
-- ========================================

SET SERVEROUTPUT ON;

BEGIN
    DBMS_OUTPUT.PUT_LINE('=== BẮT ĐẦU INSERT DỮ LIỆU CƠ BẢN ===');
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
-- 4. INSERT TABLE_FOR_CUSTOMER - BÀN ĂN
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 4: Insert dữ liệu TABLE_FOR_CUSTOMER...');
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
-- 5. INSERT PAYMENT_METHOD - PHƯƠNG THỨC THANH TOÁN
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 5: Insert dữ liệu PAYMENT_METHOD...');
END;
/

INSERT INTO PAYMENT_METHOD (payment_method_id, method_name, is_enable) VALUES (1, N'Tiền mặt', 1);
INSERT INTO PAYMENT_METHOD (payment_method_id, method_name, is_enable) VALUES (2, N'Chuyển khoản', 1);
INSERT INTO PAYMENT_METHOD (payment_method_id, method_name, is_enable) VALUES (3, N'Thẻ tín dụng', 1);
INSERT INTO PAYMENT_METHOD (payment_method_id, method_name, is_enable) VALUES (4, N'Ví điện tử', 1);
INSERT INTO PAYMENT_METHOD (payment_method_id, method_name, is_enable) VALUES (5, N'QR Code', 1);

-- ========================================
-- COMMIT DỮ LIỆU
-- ========================================
COMMIT;

BEGIN
    DBMS_OUTPUT.PUT_LINE('✅ ĐÃ INSERT THÀNH CÔNG DỮ LIỆU CƠ BẢN!');
    DBMS_OUTPUT.PUT_LINE('Tiếp theo hãy chạy:');
    DBMS_OUTPUT.PUT_LINE('  1) sql/Insert_Products_From_Images.sql');
    DBMS_OUTPUT.PUT_LINE('  2) sql/Insert_Products_From_Images_Part2.sql');
END;
/

-- ========================================
-- KIỂM TRA DỮ LIỆU CƠ BẢN
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== THỐNG KÊ DỮ LIỆU CƠ BẢN ===');
END;
/

SELECT 'USER_ROLE' AS TABLE_NAME, COUNT(*) AS TOTAL_RECORDS FROM USER_ROLE
UNION ALL
SELECT 'USER_ACCOUNT' AS TABLE_NAME, COUNT(*) AS TOTAL_RECORDS FROM USER_ACCOUNT
UNION ALL
SELECT 'CUSTOMER' AS TABLE_NAME, COUNT(*) AS TOTAL_RECORDS FROM CUSTOMER
UNION ALL
SELECT 'TABLE_FOR_CUSTOMER' AS TABLE_NAME, COUNT(*) AS TOTAL_RECORDS FROM TABLE_FOR_CUSTOMER
UNION ALL
SELECT 'PAYMENT_METHOD' AS TABLE_NAME, COUNT(*) AS TOTAL_RECORDS FROM PAYMENT_METHOD;

