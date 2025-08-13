-- Kiểm tra bill mới tạo
SELECT bill_id, table_number, status, user_id, checkin 
FROM BILL 
WHERE table_number = 6 
ORDER BY bill_id DESC;

-- Kiểm tra bill_id cao nhất  
SELECT MAX(bill_id) as max_bill_id FROM BILL;

-- Kiểm tra tổng số bills
SELECT COUNT(*) as total_bills FROM BILL;
