-- Update bill hiện có về bàn 6 và status = 0
UPDATE BILL 
SET table_number = 6, status = 0 
WHERE bill_id = 10000;

COMMIT;

-- Verify
SELECT bill_id, table_number, status FROM BILL WHERE bill_id = 10000;
