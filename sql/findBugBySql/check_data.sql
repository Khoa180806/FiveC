-- Kiểm tra dữ liệu hiện tại
SELECT bill_id, table_number, status, TYPEOF(status) as status_type 
FROM BILL 
WHERE ROWNUM <= 10;

-- Kiểm tra cấu trúc bảng
DESCRIBE BILL;

-- Kiểm tra có bill nào ở bàn 6 không
SELECT COUNT(*) as total_bills FROM BILL WHERE table_number = 6;
