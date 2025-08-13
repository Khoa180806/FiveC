-- Test script: Tạo một bill mới với status = 0 để test
INSERT INTO BILL(bill_id, user_id, phone_number, table_number, total_amount, checkin, checkout, status) 
VALUES(99999, 'admin', '0123456789', 6, 100000, SYSDATE, NULL, 0);

COMMIT;

-- Kiểm tra
SELECT bill_id, table_number, status FROM BILL WHERE table_number = 6;
