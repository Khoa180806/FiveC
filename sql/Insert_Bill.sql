-- ========================================
-- INSERT BILL, BILL_DETAIL, PAYMENT_HISTORY (AUTO SEED)
-- Tạo 200 BILL và 400 BILL_DETAIL (2 item/BILL)
-- Thời gian trải dài từ 01/01/2025 đến hiện tại
-- BILL đã thanh toán sẽ có PAYMENT_HISTORY tương ứng
-- Author: FiveC
-- ========================================

SET SERVEROUTPUT ON;

BEGIN
    DBMS_OUTPUT.PUT_LINE('=== BẮT ĐẦU TẠO DỮ LIỆU BILL/BILL_DETAIL/PAYMENT_HISTORY ===');
END;
/

DECLARE
    v_start_date           DATE := DATE '2025-01-01';
    v_end_date             DATE := TRUNC(SYSDATE);
    v_bill_id              NUMBER;
    v_user_id              NVARCHAR2(20);
    v_phone                NVARCHAR2(11);
    v_table                NUMBER;
    v_checkin              DATE;
    v_checkout             DATE;
    v_status               NVARCHAR2(50);
    v_total                NUMBER;
    v_payment_history_id   NUMBER;
    v_method               NUMBER;
    v_product_id           NVARCHAR2(10);
    v_price                NUMBER;
    v_discount             NUMBER(3,2);
    v_amount               NUMBER;
BEGIN
    FOR i IN 1..200 LOOP
        v_user_id := CASE TRUNC(DBMS_RANDOM.VALUE(1,6))
                        WHEN 1 THEN 'U001'
                        WHEN 2 THEN 'U002'
                        WHEN 3 THEN 'U003'
                        WHEN 4 THEN 'U004'
                        ELSE 'U005'
                     END;

        IF DBMS_RANDOM.VALUE(0,1) < 0.6 THEN
            v_phone := CASE TRUNC(DBMS_RANDOM.VALUE(1,6))
                          WHEN 1 THEN '0965432109'
                          WHEN 2 THEN '0913456789'
                          WHEN 3 THEN '0908765432'
                          WHEN 4 THEN '0976543210'
                          ELSE '0821234567'
                       END;
        ELSE
            v_phone := NULL;
        END IF;

        v_table := TRUNC(DBMS_RANDOM.VALUE(1,25));

        v_checkin := v_start_date
                     + (v_end_date - v_start_date) * (i - 1) / 199
                     + NUMTODSINTERVAL(TRUNC(DBMS_RANDOM.VALUE(10, 22*60)), 'MINUTE');

        IF DBMS_RANDOM.VALUE(0,1) < 0.7 THEN
            v_status := N'Đã thanh toán';
        ELSIF DBMS_RANDOM.VALUE(0,1) < 0.9 THEN
            v_status := N'Đang phục vụ';
        ELSE
            v_status := N'Hủy';
        END IF;

        v_total := 0;
        v_checkout := NULL;
        v_payment_history_id := NULL;

        INSERT INTO BILL (user_id, phone_number, payment_history_id, table_number, total_amount, checkin, checkout, status) VALUES (v_user_id, v_phone, NULL, v_table, 0, v_checkin, NULL, v_status) RETURNING bill_id INTO v_bill_id;

        FOR j IN 1..2 LOOP
            SELECT p.product_id
            , p.price
            , p.discount
            INTO v_product_id
            , v_price
            , v_discount
            FROM (
                SELECT product_id, price, discount FROM PRODUCT ORDER BY DBMS_RANDOM.VALUE
            ) p
            WHERE ROWNUM = 1;

            v_amount := TRUNC(DBMS_RANDOM.VALUE(1,4));

            INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (v_bill_id, v_product_id, v_amount, v_price, v_discount);

            v_total := v_total + (v_price * v_amount * (1 - v_discount));
        END LOOP;

        IF v_status = N'Đã thanh toán' THEN
            v_checkout := v_checkin + NUMTODSINTERVAL(TRUNC(DBMS_RANDOM.VALUE(30, 180)), 'MINUTE');
            v_method := TRUNC(DBMS_RANDOM.VALUE(1,6));

            INSERT INTO PAYMENT_HISTORY (payment_method_id, payment_date, total_amount, status, note) VALUES (v_method, v_checkout, ROUND(v_total), N'Thành công', N'Auto seed') RETURNING payment_history_id INTO v_payment_history_id;

            UPDATE BILL
            SET total_amount = ROUND(v_total)
            , payment_history_id = v_payment_history_id
            , checkout = v_checkout
            WHERE bill_id = v_bill_id;

        ELSIF v_status = N'Hủy' THEN
            v_checkout := v_checkin + NUMTODSINTERVAL(TRUNC(DBMS_RANDOM.VALUE(10, 60)), 'MINUTE');
            UPDATE BILL
            SET total_amount = ROUND(v_total)
            , checkout = v_checkout
            WHERE bill_id = v_bill_id;

        ELSE
            UPDATE BILL
            SET total_amount = ROUND(v_total)
            WHERE bill_id = v_bill_id;
        END IF;
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('✅ ĐÃ TẠO 200 BILL VÀ 400 BILL_DETAIL (2 ITEM/BILL).');
END;
/

-- Kiểm tra nhanh số lượng đã tạo
SELECT 'BILL' AS TABLE_NAME
, COUNT(*) AS TOTAL_RECORDS
FROM BILL
UNION ALL
SELECT 'BILL_DETAIL' AS TABLE_NAME
, COUNT(*) AS TOTAL_RECORDS
FROM BILL_DETAIL
UNION ALL
SELECT 'PAYMENT_HISTORY' AS TABLE_NAME
, COUNT(*) AS TOTAL_RECORDS
FROM PAYMENT_HISTORY;