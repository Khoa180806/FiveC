-- ========================================
-- KIỂM TRA DANH MỤC HIỆN CÓ TRONG DATABASE
-- Author: FiveC
-- Date: 2025
-- ========================================

SET SERVEROUTPUT ON;

BEGIN
    DBMS_OUTPUT.PUT_LINE('=== KIỂM TRA DANH MỤC HIỆN CÓ ===');
END;
/

-- Kiểm tra tất cả danh mục
SELECT category_id
    ,category_name
    ,is_available
FROM CATE
ORDER BY category_id;

-- Kiểm tra số lượng sản phẩm hiện có
SELECT COUNT(*) AS TOTAL_PRODUCTS
FROM PRODUCT;

-- Kiểm tra product_id cuối cùng
SELECT MAX(product_id) AS LAST_PRODUCT_ID
FROM PRODUCT;
