-- Xóa bills cũ ở bàn 6 (nếu có)
DELETE FROM BILL WHERE table_number = 6;

-- Tạo bill mới với bill_id cụ thể
INSERT INTO BILL(bill_id, user_id, table_number, total_amount, checkin, status, payment_history_id) 
VALUES(99999, 'admin', 6, 0, SYSDATE, 0, 1);

COMMIT;

-- Verify
SELECT bill_id, table_number, status, user_id FROM BILL WHERE table_number = 6;
