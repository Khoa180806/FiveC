-- Update một bill để test
-- Chọn bill ở bàn 6 và set status = 0
UPDATE BILL SET status = 0 WHERE table_number = 6 AND ROWNUM = 1;

COMMIT;

-- Kiểm tra kết quả
SELECT bill_id, table_number, status FROM BILL WHERE table_number = 6;
