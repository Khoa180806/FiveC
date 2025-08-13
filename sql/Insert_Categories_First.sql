-- ========================================
-- INSERT DANH MỤC MỚI TRƯỚC KHI INSERT SẢN PHẨM
-- Author: FiveC
-- Date: 2025
-- ========================================

SET SERVEROUTPUT ON;

BEGIN
    DBMS_OUTPUT.PUT_LINE('=== BẮT ĐẦU INSERT DANH MỤC MỚI ===');
END;
/

-- ========================================
-- INSERT DANH MỤC MỚI (NẾU CHƯA CÓ)
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 1: Insert danh mục mới...');
END;
/

-- Thêm các danh mục mới nếu chưa có
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
-- KIỂM TRA DANH MỤC ĐÃ TẠO
-- ========================================
BEGIN
    DBMS_OUTPUT.PUT_LINE('Bước 2: Kiểm tra danh mục đã tạo...');
END;
/

SELECT category_id
    ,category_name
    ,is_available
FROM CATE
ORDER BY category_id;

BEGIN
    DBMS_OUTPUT.PUT_LINE('✅ ĐÃ INSERT THÀNH CÔNG DANH MỤC MỚI!');
    DBMS_OUTPUT.PUT_LINE('Bây giờ có thể chạy file Insert_Products_From_Images.sql');
END;
/
