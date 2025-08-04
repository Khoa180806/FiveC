-- ========================================
-- DATABASE QUẢN LÝ QUÁN MÌ CAY - SCRIPT CREATE DATABASE HOÀN CHỈNH
-- CHỈ CẦN BẤM EXECUTE MỘT LẦN
-- Author: FiveC
-- Version: 1.0
-- Date: 03/07/2025
-- Description: Hệ thống quản lý quán mì cay với 10 bảng chính
-- ========================================

SET SERVEROUTPUT ON;

BEGIN
    DBMS_OUTPUT.PUT_LINE('=== BẮT ĐẦU TẠO DATABASE QUẢN LÝ QUÁN MÌ CAY ===');
END;
/

-- ========================================
-- PHẦN 1: XÓA DATABASE CŨ (NẾU CÓ)
-- ========================================

BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 1: Xóa các bảng cũ (nếu có)...');
END;
/

-- Xóa các bảng theo thứ tự (foreign key)
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE BILL_DETAIL CASCADE CONSTRAINTS';
    DBMS_OUTPUT.PUT_LINE('- Đã xóa BILL_DETAIL');
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE BILL CASCADE CONSTRAINTS';
    DBMS_OUTPUT.PUT_LINE('- Đã xóa BILL');
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE PAYMENT_HISTORY CASCADE CONSTRAINTS';
    DBMS_OUTPUT.PUT_LINE('- Đã xóa PAYMENT_HISTORY');
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE PRODUCT CASCADE CONSTRAINTS';
    DBMS_OUTPUT.PUT_LINE('- Đã xóa PRODUCT');
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE USER_ACCOUNT CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE CUSTOMER CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE TABLE_FOR_CUSTOMER CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE PAYMENT_METHOD CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE CATE CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE USER_ROLE CASCADE CONSTRAINTS';
    DBMS_OUTPUT.PUT_LINE('- Đã xóa các bảng còn lại');
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

-- ========================================
-- PHẦN 2: TẠO CÁC BẢNG MỚI
-- ========================================

BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 2: Tạo các bảng mới...');
END;
/

-- 1. BẢNG USER_ROLE
CREATE TABLE USER_ROLE (
    role_id NVARCHAR2(5) PRIMARY KEY
    ,name_role NVARCHAR2(15) NOT NULL
);

-- 2. BẢNG USER_ACCOUNT
CREATE TABLE USER_ACCOUNT (
    user_id NVARCHAR2(20) PRIMARY KEY
    ,username NVARCHAR2(20) NOT NULL UNIQUE
    ,pass NVARCHAR2(50) NOT NULL
    ,fullName NVARCHAR2(50) NOT NULL
    ,email NVARCHAR2(100) UNIQUE
    ,phone_number NVARCHAR2(11) UNIQUE
    ,image NVARCHAR2(255)
    ,is_enabled NUMBER(1) DEFAULT 1
    ,created_date DATE DEFAULT SYSDATE
    ,role_id NVARCHAR2(5) NOT NULL
    ,CONSTRAINT FK_User_Role FOREIGN KEY (role_id) REFERENCES USER_ROLE(role_id)
);
    
    
-- 3. BẢNG CUSTOMER
CREATE TABLE CUSTOMER (
    phone_number NVARCHAR2(11) PRIMARY KEY
    ,customer_name NVARCHAR2(50) NOT NULL
    ,point_level NUMBER(10) DEFAULT 0
    ,level_ranking NVARCHAR2(30)
    ,created_date DATE DEFAULT SYSDATE
);

-- 4. BẢNG CATE
CREATE TABLE CATE (
    category_id NVARCHAR2(5) PRIMARY KEY
    ,category_name NVARCHAR2(50) NOT NULL UNIQUE
    ,is_available NUMBER(1) DEFAULT 1
);

-- 5. BẢNG PRODUCT
CREATE TABLE PRODUCT (
    product_id NVARCHAR2(10) PRIMARY KEY
    ,product_name NVARCHAR2(100) NOT NULL
    ,price NUMBER(7,0) NOT NULL
    ,discount NUMBER(3,2) DEFAULT 0.00
    ,unit NVARCHAR2(10) DEFAULT N'phần'
    ,image NVARCHAR2(255)
    ,is_available NUMBER(1) DEFAULT 1
    ,note NVARCHAR2(200)
    ,created_date DATE DEFAULT SYSDATE
    ,category_id NVARCHAR2(5) NOT NULL
    ,CONSTRAINT FK_Product_Category FOREIGN KEY (category_id) REFERENCES CATE(category_id)
    ,CONSTRAINT CHK_Product_Price CHECK (price > 0)
    ,CONSTRAINT CHK_Product_Discount CHECK (discount >= 0 AND discount <= 1)
);

-- 6. BẢNG TABLE_FOR_CUSTOMER
CREATE TABLE TABLE_FOR_CUSTOMER (
    table_number NUMBER(3) PRIMARY KEY
    ,amount NUMBER(2) NOT NULL
    ,status NUMBER(1) DEFAULT 1
    ,CONSTRAINT CHK_Table_Amount CHECK (amount > 0)
);

-- 7. BẢNG PAYMENT_METHOD
CREATE TABLE PAYMENT_METHOD (
    payment_method_id NUMBER(1) PRIMARY KEY
    ,method_name NVARCHAR2(50) NOT NULL UNIQUE
    ,is_enable NUMBER(1) DEFAULT 1
);

-- 8. BẢNG PAYMENT_HISTORY
CREATE TABLE PAYMENT_HISTORY (
    payment_history_id NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY
    ,payment_method_id NUMBER(1) NOT NULL
    ,payment_date DATE DEFAULT SYSDATE
    ,total_amount NUMBER(9,0)
    ,status NVARCHAR2(30) DEFAULT N'Thành công'
    ,note NVARCHAR2(255)
    ,CONSTRAINT FK_PaymentHistory_PaymentMethod FOREIGN KEY (payment_method_id) REFERENCES PAYMENT_METHOD(payment_method_id)
);

-- 9. BẢNG BILL

CREATE TABLE BILL (
    bill_id NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 10000 INCREMENT BY 1) PRIMARY KEY
    ,user_id NVARCHAR2(20) NOT NULL 
    ,phone_number NVARCHAR2(11)
    ,payment_history_id NUMBER
    ,table_number NUMBER(3)
    ,total_amount NUMBER(9,0) DEFAULT 0
    ,checkin DATE DEFAULT SYSDATE
    ,checkout DATE
    ,status NVARCHAR2(50) DEFAULT N'Đang phục vụ'
    ,CONSTRAINT FK_Bill_User FOREIGN KEY (user_id) REFERENCES USER_ACCOUNT(user_id)
    ,CONSTRAINT FK_Bill_Customer FOREIGN KEY (phone_number) REFERENCES CUSTOMER(phone_number)
    ,CONSTRAINT FK_Bill_PaymentHistory FOREIGN KEY (payment_history_id) REFERENCES PAYMENT_HISTORY(payment_history_id)
    ,CONSTRAINT FK_Bill_Table FOREIGN KEY (table_number) REFERENCES TABLE_FOR_CUSTOMER(table_number)
    ,CONSTRAINT CHK_Bill_Amount CHECK (total_amount >= 0)
);

-- 10. BẢNG BILL_DETAIL
CREATE TABLE BILL_DETAIL (
    bill_detail_id NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1000 INCREMENT BY 1) PRIMARY KEY
    ,bill_id NUMBER NOT NULL
    ,product_id NVARCHAR2(10) NOT NULL
    ,amount NUMBER(2) NOT NULL
    ,price NUMBER(9,0) NOT NULL
    ,discount NUMBER(3,2) DEFAULT 0
    ,CONSTRAINT FK_BillDetail_Bill FOREIGN KEY (bill_id) REFERENCES BILL(bill_id)
    ,CONSTRAINT FK_BillDetail_Product FOREIGN KEY (product_id) REFERENCES PRODUCT(product_id)
    ,CONSTRAINT CHK_BillDetail_Amount CHECK (amount > 0)
    ,CONSTRAINT CHK_BillDetail_Price CHECK (price > 0)
);

BEGIN
    DBMS_OUTPUT.PUT_LINE('✅ Đã tạo thành công tất cả 10 bảng!');
END;
/

-- ========================================
-- PHẦN 3: TẠO INDEX TỐI ƯU
-- ========================================

BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 3: Tạo các index tối ưu...');
END;
/

CREATE INDEX IDX_USER_USERNAME ON USER_ACCOUNT(username);
CREATE INDEX IDX_USER_ROLE ON USER_ACCOUNT(role_id);
CREATE INDEX IDX_CUSTOMER_PHONE ON CUSTOMER(phone_number);
CREATE INDEX IDX_PRODUCT_CATEGORY ON PRODUCT(category_id);
CREATE INDEX IDX_BILL_CUSTOMER ON BILL(phone_number);
CREATE INDEX IDX_BILL_USER ON BILL(user_id);
CREATE INDEX IDX_BILL_DATE ON BILL(checkin);
CREATE INDEX IDX_BILLDETAIL_BILL ON BILL_DETAIL(bill_id);
CREATE INDEX IDX_BILLDETAIL_PRODUCT ON BILL_DETAIL(product_id);

BEGIN
    DBMS_OUTPUT.PUT_LINE('✅ Đã tạo thành công 9 index!');
END;
/

COMMIT;

-- ========================================
-- HOÀN THÀNH
-- ========================================

BEGIN
    DBMS_OUTPUT.PUT_LINE(' HOÀN THÀNH TẠO DATABASE QUẢN LÝ QUÁN MÌ CAY!');
    DBMS_OUTPUT.PUT_LINE('✅ 10 bảng đã được tạo thành công');
    DBMS_OUTPUT.PUT_LINE('✅ 9 index đã được tạo thành công');
    DBMS_OUTPUT.PUT_LINE('✅ Database sẵn sàng sử dụng!');
    DBMS_OUTPUT.PUT_LINE('🎉 Ready for production!');
END;
/