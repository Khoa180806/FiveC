-- Quick test: Update bill đầu tiên có table_number = 6 về status = 0
UPDATE BILL 
SET status = 0 
WHERE table_number = 6 
  AND ROWNUM = 1;

-- Nếu không có bill nào ở bàn 6, tạo mới
INSERT INTO BILL(bill_id, user_id, phone_number, table_number, total_amount, checkin, checkout, status, payment_history_id) 
SELECT 99999, 'admin', '0123456789', 6, 0, SYSDATE, NULL, 0, 1
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM BILL WHERE table_number = 6);

COMMIT;

-- Kiểm tra kết quả
SELECT bill_id, table_number, status FROM BILL WHERE table_number = 6;
