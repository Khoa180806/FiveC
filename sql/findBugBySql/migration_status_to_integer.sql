-- Migration script: Chuyển column status từ NVARCHAR2 sang INTEGER
-- Người tạo: Assistant
-- Ngày: 13/08/2025
-- Mục đích: Giải quyết vấn đề ORA-17004 và ORA-17059

-- Bước 1: Backup dữ liệu hiện tại
CREATE TABLE BILL_BACKUP AS SELECT * FROM BILL;

-- Bước 2: Cập nhật các giá trị string thành integer tương ứng
UPDATE BILL SET status = '0' WHERE status = 'Đang phục vụ' OR status = 'null' OR status IS NULL;
UPDATE BILL SET status = '1' WHERE status = 'Đã thanh toán';  
UPDATE BILL SET status = '2' WHERE status = 'Hủy';

-- Bước 3: Xem kết quả sau khi cập nhật
SELECT status, COUNT(*) as count FROM BILL GROUP BY status ORDER BY status;

-- Bước 4: Thêm column mới kiểu INTEGER
ALTER TABLE BILL ADD status_new INTEGER;

-- Bước 5: Copy dữ liệu đã chuẩn hóa sang column mới
UPDATE BILL SET status_new = CAST(status AS INTEGER);

-- Bước 6: Kiểm tra dữ liệu
SELECT status, status_new, COUNT(*) as count FROM BILL GROUP BY status, status_new ORDER BY status_new;

-- Bước 7: Drop column cũ
ALTER TABLE BILL DROP COLUMN status;

-- Bước 8: Rename column mới
ALTER TABLE BILL RENAME COLUMN status_new TO status;

-- Bước 9: Thêm constraint và default value
ALTER TABLE BILL MODIFY status DEFAULT 0;
ALTER TABLE BILL ADD CONSTRAINT chk_bill_status CHECK (status IN (0, 1, 2));

-- Bước 10: Xem kết quả cuối cùng
DESCRIBE BILL;
SELECT status, COUNT(*) as count FROM BILL GROUP BY status ORDER BY status;

-- Commit thay đổi
COMMIT;

-- Note: Nếu có lỗi, có thể rollback bằng cách:
-- DROP TABLE BILL;
-- CREATE TABLE BILL AS SELECT * FROM BILL_BACKUP;
-- DROP TABLE BILL_BACKUP;
