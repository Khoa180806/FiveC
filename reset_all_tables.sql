-- Script reset tất cả bàn về trạng thái sạch để test
-- CẢNH BÁO: Script này sẽ xóa tất cả bills hiện có!

-- Backup trước khi xóa
CREATE TABLE BILL_BACKUP_RESET AS SELECT * FROM BILL;

-- Xóa tất cả bills hiện có
DELETE FROM BILL_DETAILS;
DELETE FROM BILL;
DELETE FROM PAYMENT_HISTORY WHERE payment_history_id > 1;

-- Reset sequences (nếu có)
-- ALTER SEQUENCE bill_seq RESTART START WITH 10000;

-- Tạo vài bills mẫu cho test
-- Bàn 1: Đang phục vụ
INSERT INTO BILL(bill_id, user_id, table_number, total_amount, checkin, status, payment_history_id) 
VALUES(10000, 'admin', 1, 50000, SYSDATE-1, 0, 1);

-- Bàn 2: Đã thanh toán
INSERT INTO BILL(bill_id, user_id, table_number, total_amount, checkin, checkout, status, payment_history_id) 
VALUES(10001, 'admin', 2, 75000, SYSDATE-2, SYSDATE-1, 1, 1);

-- Bàn 3: Đang phục vụ
INSERT INTO BILL(bill_id, user_id, table_number, total_amount, checkin, status, payment_history_id) 
VALUES(10002, 'admin', 3, 30000, SYSDATE, 0, 1);

-- Bàn 4: Hủy
INSERT INTO BILL(bill_id, user_id, table_number, total_amount, checkin, checkout, status, payment_history_id) 
VALUES(10003, 'admin', 4, 25000, SYSDATE-1, SYSDATE, 2, 1);

COMMIT;

-- Kiểm tra kết quả
SELECT table_number, status, COUNT(*) as bill_count 
FROM BILL 
GROUP BY table_number, status 
ORDER BY table_number;
