# 🔧 ORA-17004 Fix Documentation

## 📋 **Tổng quan vấn đề**

**Lỗi chính:** `ORA-17004: Invalid column type`  
**Thời gian:** 12-13/08/2025  
**Nguyên nhân:** Type mismatch giữa Java và Oracle Database  
**Ảnh hưởng:** Không thể tạo hóa đơn mới cho các bàn

---

## 🚨 **Các lỗi đã gặp phải**

### 1. **Lỗi ban đầu - Bàn #6**
```
DEBUG: Exception in createNewBillForTable: java.sql.SQLException: ORA-17004: Invalid column type
at com.team4.quanliquanmicay.View.BillUI.createNewBillForTable(BillUI.java:814)
```

**Nguyên nhân:** Cột `status` trong bảng `BILL` được định nghĩa là `NVARCHAR2(50)` nhưng code Java đang set `Integer`

### 2. **Lỗi sau khi fix bàn #6 - Các bàn khác**
```
DEBUG: Integer.class failed, trying BigDecimal.class...
DEBUG: BigDecimal.class failed, trying Long.class...
DEBUG: Exception in createNewBillForTable: java.sql.SQLException: ORA-17004: Invalid column type
at com.team4.quanliquanmicay.View.BillUI.createNewBillForTable(BillUI.java:826)
```

**Nguyên nhân:** Oracle trả về `BigDecimal` cho `MAX(payment_history_id)` nhưng `XJdbc.getValue()` force cast không an toàn

---

## 🛠️ **Các bước fix đã thực hiện**

### **Bước 1: Fix Database Schema**
```sql
-- Thay đổi cột status từ NVARCHAR2 sang INTEGER
ALTER TABLE BILL MODIFY status INTEGER;

-- Cập nhật dữ liệu hiện có
UPDATE BILL SET status = 0 WHERE status = 'Đang phục vụ';
UPDATE BILL SET status = 1 WHERE status = 'Đã thanh toán';
UPDATE BILL SET status = 2 WHERE status = 'Đã hủy';
```

### **Bước 2: Cập nhật Entity Bill.java**
```java
// Thay đổi từ String sang Integer
private Integer status; // Thay vì private String status;

// Cập nhật getter/setter
public Integer getStatus() { return status; }
public void setStatus(Integer status) { this.status = status; }
```

### **Bước 3: Cập nhật BillDAOImpl.java**
```java
// Trong phương thức mapResultSetToBill()
bill.setStatus(rs.getInt("status")); // Thay vì rs.getString("status")
```

### **Bước 4: Cập nhật BillUI.java**
```java
// Trong createNewBillForTable()
newBill.setStatus(0); // Integer thay vì String
```

### **Bước 5: Fix XJdbc.getValue() - Root Cause**
**Vấn đề:** Oracle trả về `BigDecimal` cho `MAX()` nhưng code force cast không an toàn

**Solution:** Thêm phương thức overloaded với safe type conversion

```java
/**
 * Truy vấn lấy giá trị đầu tiên với chuyển đổi kiểu an toàn
 */
public static <T> T getValue(String sql, Class<T> type, Object... values) {
    return executeQuery(sql, rs -> {
        if (rs.next()) {
            Object result = rs.getObject(1);
            if (result == null) return null;
            
            // Handle number conversions safely
            if (type == Integer.class) {
                if (result instanceof Number) {
                    return (T) Integer.valueOf(((Number) result).intValue());
                }
            } else if (type == Long.class) {
                if (result instanceof Number) {
                    return (T) Long.valueOf(((Number) result).longValue());
                }
            } else if (type == java.math.BigDecimal.class) {
                if (result instanceof Number) {
                    return (T) new java.math.BigDecimal(result.toString());
                }
            } else if (type == String.class) {
                return (T) result.toString();
            }
            
            // Default: try direct cast
            return (T) result;
        }
        return null;
    }, values);
}
```

### **Bước 6: Cập nhật BillUI.java để sử dụng phương thức mới**
```java
// Thay thế try-catch phức tạp bằng một dòng đơn giản
Integer lastPaymentId = XJdbc.getValue(getLastIdSql, Integer.class);
```

---

## 🎯 **Kết quả sau khi fix**

### ✅ **Đã giải quyết:**
- Lỗi `ORA-17004` khi tạo hóa đơn mới
- Type mismatch giữa Java và Database
- Không thể click vào các bàn khác ngoài bàn #6
- Force cast không an toàn trong `XJdbc.getValue()`

### ✅ **Cải thiện:**
- Code sạch hơn, ít try-catch phức tạp
- Type safety tốt hơn
- Xử lý conversion an toàn cho tất cả kiểu Number
- Debug output rõ ràng hơn

---

## 🔍 **Debug Output sau khi fix**

```
DEBUG: Starting createNewBillForTable for table 5
DEBUG: Inserting payment history...
DEBUG: Getting last payment_history_id...
DEBUG: Got payment_history_id: 10025
DEBUG: Creating bill with status: 0 (type: Integer)
```

**Thay vì:**
```
DEBUG: Integer.class failed, trying BigDecimal.class...
DEBUG: BigDecimal.class failed, trying Long.class...
DEBUG: Exception in createNewBillForTable: ORA-17004
```

---

## 📚 **Bài học rút ra**

1. **Kiểm tra Database Schema trước:** Đảm bảo kiểu dữ liệu khớp giữa Java và Database
2. **Không force cast:** Luôn sử dụng safe type conversion
3. **Debug từng bước:** Xác định chính xác dòng code gây lỗi
4. **Fix root cause:** Không chỉ vá tạm thời mà phải giải quyết gốc rễ
5. **Test kỹ:** Đảm bảo fix hoạt động cho tất cả trường hợp

---

## 🚀 **Các file đã được sửa**

1. `sql/Create_Table.sql` - Cập nhật schema
2. `src/main/java/com/team4/quanliquanmicay/Entity/Bill.java` - Thay đổi kiểu status
3. `src/main/java/com/team4/quanliquanmicay/Impl/BillDAOImpl.java` - Cập nhật mapping
4. `src/main/java/com/team4/quanliquanmicay/View/BillUI.java` - Sử dụng Integer status
5. `src/main/java/com/team4/quanliquanmicay/util/XJdbc.java` - Thêm safe type conversion

---

**Ngày tạo:** 13/08/2025  

