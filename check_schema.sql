-- Kiểm tra schema của table BILL
DESCRIBE BILL;

-- Kiểm tra data type của column status
SELECT column_name, data_type, data_length, data_precision, data_scale
FROM USER_TAB_COLUMNS 
WHERE table_name = 'BILL' AND column_name = 'STATUS';

-- Test insert trực tiếp
INSERT INTO BILL(bill_id, user_id, table_number, total_amount, checkin, status, payment_history_id) 
VALUES(99998, 'test', 99, 0, SYSDATE, 0, 1);

-- Kiểm tra insert có thành công không
SELECT bill_id, table_number, status FROM BILL WHERE bill_id = 99998;
