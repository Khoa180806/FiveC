-- Tạo bill đơn giản cho test
INSERT INTO BILL(user_id, table_number, total_amount, checkin, status) 
VALUES('admin', 6, 0, SYSDATE, 0);

COMMIT;

-- Kiểm tra
SELECT bill_id, table_number, status FROM BILL WHERE table_number = 6;
