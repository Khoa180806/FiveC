# Tổng Hợp Lỗi và Cách Khắc Phục - Hệ Thống Quản Lý Hóa Đơn

## 1. Lỗi Method Not Found

### Lỗi: `The method setBillDetailsId(String) is undefined for the type BillDetails`

**Nguyên nhân:** 
- Không khớp giữa tên method được sử dụng trong `OrderJDialog.java` và tên getter/setter thực tế trong `BillDetails.java`
- Ví dụ: `setBillDetailsId` vs `setBill_detail_id`

**Cách khắc phục:**
```java
// Trước
billDetail.setBillDetailsId("BD001");

// Sau
billDetail.setBill_detail_id(null); // Để DB tự generate
```

**Các method tương tự cần sửa:**
- `getBillId()` → `getBill_id()`
- `setProductId()` → `setProduct_id()`
- `setBillId()` → `setBill_id()`

---

## 2. Lỗi Type Mismatch

### Lỗi: `The method parseInt(String) in the type Integer is not applicable for the arguments (Integer)`

**Nguyên nhân:**
- Sau khi thay đổi `bill_id` và `bill_detail_id` từ `String` sang `Integer` trong entity classes
- Code vẫn sử dụng `Integer.parseInt()` trên giá trị `Integer`

**Cách khắc phục:**
```java
// Trước
String billId = String.valueOf(currentBill.getBill_id());
int id = Integer.parseInt(billId);

// Sau
Integer billId = currentBill.getBill_id();
int id = billId; // Hoặc billId.intValue()
```

---

## 3. Lỗi Database - Invalid Column Type

### Lỗi: `ORA-17004: Invalid column type`

**Nguyên nhân:**
- Cố gắng insert `java.util.Date` trực tiếp vào Oracle `DATE` column
- Oracle yêu cầu `java.sql.Timestamp` cho các column kiểu `DATE`

**Cách khắc phục:**
```java
// Thêm method helper trong BillDAOImpl.java
private Timestamp convertToTimestamp(java.util.Date date) {
    if (date == null) return null;
    return new Timestamp(date.getTime());
}

// Sử dụng trong INSERT/UPDATE
String sql = "INSERT INTO BILL (checkin, checkout) VALUES (?, ?)";
XJdbc.executeUpdate(sql, 
    convertToTimestamp(bill.getCheckin()),
    convertToTimestamp(bill.getCheckout())
);
```

---

## 4. Lỗi Foreign Key Constraint

### Lỗi: `ORA-02291: integrity constraint (SYSTEM.FK_BILL_PAYMENTHISTORY) violated - parent key not found`

**Nguyên nhân:**
- Khi tạo bill mới, `payment_history_id` được set = 0
- Nhưng 0 không tồn tại trong bảng `PAYMENT_HISTORY`
- Field `payment_history_id` trong `Bill.java` là `int` (không thể null)

**Cách khắc phục:**
```java
// Thay đổi trong Bill.java
private Integer payment_history_id; // Thay vì int

// Trong HoaDonJDialog.java
newBill.setPayment_history_id(null); // Thay vì 0
```

---

## 5. Lỗi Invalid Number

### Lỗi: `ORA-01722: invalid number`

**Nguyên nhân:**
- `bill_id` trong Oracle là `IDENTITY` column (auto-generated `NUMBER`)
- Code Java cố gắng insert `String` bill_id thủ công

**Cách khắc phục:**
```java
// Thay đổi trong Bill.java
private Integer bill_id; // Thay vì String

// Trong HoaDonJDialog.java - bỏ phần generate ID thủ công
// Trước
String billId = "BILL" + System.currentTimeMillis();
newBill.setBill_id(billId);

// Sau
newBill.setBill_id(null); // Để DB tự generate
```

---

## 6. Lỗi Table Not Found

### Lỗi: `ORA-00942: table or view does not exist`

**Nguyên nhân:**
- Không khớp tên bảng: `BillDetailsDAOImpl.java` query `BILL_DETAILS`
- Nhưng tên thực tế trong DB là `BILL_DETAIL`

**Cách khắc phục:**
```java
// Sửa tất cả SQL queries trong BillDetailsDAOImpl.java
private static final String INSERT_SQL = 
    "INSERT INTO BILL_DETAIL (bill_id, product_id, amount, price, discount) VALUES (?, ?, ?, ?, ?)";

private static final String SELECT_ALL_SQL = 
    "SELECT * FROM BILL_DETAIL";

private static final String UPDATE_SQL = 
    "UPDATE BILL_DETAIL SET bill_id=?, product_id=?, amount=?, price=?, discount=? WHERE bill_detail_id=?";
```

---

## 7. Lỗi Invalid Identifier

### Lỗi: `ORA-00904: "BILL_DETAILS_ID": invalid identifier`

**Nguyên nhân:**
- Không khớp tên column: Code sử dụng `bill_details_id`
- Nhưng tên thực tế trong DB là `bill_detail_id`

**Cách khắc phục:**
```java
// Sửa trong BillDetails.java
private Integer bill_detail_id; // Thay vì bill_details_id

// Sửa tất cả SQL queries
private static final String INSERT_SQL = 
    "INSERT INTO BILL_DETAIL (bill_detail_id, bill_id, product_id, amount, price, discount) VALUES (?, ?, ?, ?, ?, ?)";
```

---

## 8. Lỗi Build Cache

### Lỗi: `XTheme cannot be resolved`

**Nguyên nhân:**
- Lỗi cache build trong Maven/NetBeans
- Compiled classes hoặc dependencies không được refresh đúng cách

**Cách khắc phục:**
```bash
mvn clean
# Hoặc trong NetBeans: Clean and Build Project
```

---

## 9. Lỗi Date Format

### Lỗi: Hiển thị sai format thời gian (`yy/MM/dd hh/pp/ss` thay vì `HH:mm:ss dd/MM/yyyy`)

**Nguyên nhân:**
- Pattern `SimpleDateFormat` bị hiểu sai
- Vấn đề locale/timezone

**Cách khắc phục:**
```java
// Trước
SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
txtBegin.setText(sdf.format(bill.getCheckin()));

// Sau - sử dụng XDate utility
txtBegin.setText(XDate.format(bill.getCheckin(), "HH:mm:ss dd/MM/yyyy"));
```

---

## 10. Lỗi GUI Builder Warning

### Lỗi: NetBeans GUI Builder warnings về "Generated Code" sections

**Nguyên nhân:**
- Trong quá trình optimize code, các comment đặc biệt bị xóa
- NetBeans GUI Builder cần các comment này để function

**Cách khắc phục:**
```java
// <editor-fold defaultstate="collapsed" desc="Generated Code">
// ... existing code ...
// </editor-fold>

// Variables declaration
private javax.swing.JButton btnOrder;
private javax.swing.JTable tblBillDetails;
// ... existing code ...
```

---

## 11. Lỗi Unsafe Type Casting

### Lỗi: Cast trực tiếp từ Object sang String/Integer không an toàn

**Nguyên nhân:**
- Sử dụng cast trực tiếp mà không check null
- Không handle `NumberFormatException`

**Cách khắc phục:**
```java
// Trước
String billId = (String) tblBillDetails.getValueAt(selectedRow, 0);
int id = Integer.parseInt(billId);

// Sau
Object value = tblBillDetails.getValueAt(selectedRow, 0);
if (value == null) {
    XDialog.alert("Giá trị không hợp lệ!");
    return;
}

try {
    String billId = XStr.valueOf(value);
    if (XValidation.isEmpty(billId)) {
        XDialog.alert("ID hóa đơn không hợp lệ!");
        return;
    }
    int id = Integer.parseInt(billId);
} catch (NumberFormatException e) {
    XDialog.alert("ID hóa đơn không đúng định dạng số!");
    return;
}
```

---

## 12. Các Best Practices Đã Áp Dụng

### 1. Sử dụng Utility Classes
```java
// Thay vì tự viết
if (str == null || str.trim().isEmpty()) {
    // handle
}

// Sử dụng XValidation
if (XValidation.isEmpty(str)) {
    // handle
}
```

### 2. SQL Constants
```java
// Thay vì
String sql = "SELECT * FROM BILL WHERE bill_id = ?";

// Sử dụng
private static final String FIND_BY_ID_SQL = "SELECT * FROM BILL WHERE bill_id = ?";
```

### 3. Entity Validation
```java
// Thêm method isValid() trong entities
public boolean isValid() {
    return user_id != null && !user_id.trim().isEmpty()
        && table_number > 0
        && total_amount >= 0;
}
```

### 4. Stream API cho Performance
```java
// Thay vì loop
double total = 0;
for (BillDetails detail : billDetails) {
    total += detail.getTotalPrice();
}

// Sử dụng Stream
double total = billDetails.stream()
    .mapToDouble(BillDetails::getTotalPrice)
    .sum();
```

---

## 13. Checklist Kiểm Tra

### Trước khi commit code:
- [ ] Kiểm tra tên bảng và column trong SQL queries
- [ ] Verify data types giữa Java entities và DB schema
- [ ] Test null checks và validation
- [ ] Kiểm tra IDENTITY columns không được insert thủ công
- [ ] Verify foreign key constraints
- [ ] Test date/time formatting
- [ ] Kiểm tra GUI Builder comments
- [ ] Run `mvn clean` nếu có lỗi build

---

## 14. Các File Chính Đã Sửa

1. **HoaDonJDialog.java** - Dialog chính quản lý hóa đơn
2. **OrderJDialog.java** (trước là DatMonJDialog.java) - Dialog đặt món
3. **Bill.java** - Entity hóa đơn
4. **BillDetails.java** - Entity chi tiết hóa đơn
5. **BillDAOImpl.java** - DAO implementation cho Bill
6. **BillDetailsDAOImpl.java** - DAO implementation cho BillDetails
7. **ChooseTable.java** - Dialog chọn bàn
8. **ThanhToanJDialog.java** - Dialog thanh toán
9. **TestDatMon.java** - Test class

---

## 15. Kết Luận

Quá trình phát triển hệ thống quản lý hóa đơn đã gặp nhiều lỗi liên quan đến:
- **Type mismatch** giữa Java và Database
- **Schema alignment** (tên bảng, column)
- **Data validation** và **null handling**
- **Build cache** issues
- **GUI Builder** compatibility

Các lỗi này đã được khắc phục bằng cách:
- **Standardize** data types across layers
- **Validate** input data thoroughly
- **Use utility classes** for common operations
- **Follow Oracle SQL standards**
- **Maintain GUI Builder compatibility**

Hệ thống hiện tại đã ổn định và có thể handle các use cases cơ bản của restaurant management. 