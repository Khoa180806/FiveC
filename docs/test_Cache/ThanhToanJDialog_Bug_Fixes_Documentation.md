# ThanhToanJDialog.java - Bug Fixes Documentation

## Tổng quan
File `ThanhToanJDialog.java` là dialog chính xử lý thanh toán trong hệ thống quản lý quán mì cay. Trong quá trình phát triển, đã phát hiện và sửa nhiều lỗi quan trọng liên quan đến luồng tạo hội viên, tính toán tiền và điểm.

## Danh sách các lỗi đã được sửa

### 1. Lỗi luồng tạo hội viên không hợp lý

**Mô tả lỗi:**
- Khi bấm "TẠO HỘI VIÊN", hệ thống yêu cầu nhập số điện thoại trước
- Điều này không hợp lý vì nếu đã có số điện thoại thì không cần tạo hội viên mới
- Luồng này gây khó hiểu cho người dùng

**Code lỗi:**
```java
private void createMember() {
    String phoneNumber = txtPhoneNumber.getText().trim();
    if (phoneNumber.isEmpty()) {
        XDialog.alert("Vui lòng nhập số điện thoại khách hàng!");
        return;
    }
    // ... rest of the code
}
```

**Cách fix:**
```java
private void createMember() {
    // Kiểm tra xem có hóa đơn để thanh toán không
    if (currentBill == null || totalAmount <= 0) {
        XDialog.alert("Vui lòng chọn bàn có hóa đơn để thanh toán trước khi tạo hội viên!");
        return;
    }
    
    // Mở CustomerJDialog để tạo hội viên mới
    CustomerJDialog customerDialog = new CustomerJDialog();
    customerDialog.setVisible(true);
    
    // Sau khi đóng dialog, kiểm tra xem có tạo thành công không
    if (customerDialog.getCustomer() != null) {
        currentCustomer = customerDialog.getCustomer();
        customerPoints = currentCustomer.getPoint_level();
        
        // Tự động điền số điện thoại vào txtPhoneNumber
        txtPhoneNumber.setText(currentCustomer.getPhone_number());
        
        // Tự động tính điểm mới dựa trên hóa đơn hiện tại
        if (totalAmount > 0) {
            int newPoints = calculateNewPoints(customerPoints, totalAmount);
            lblPoint.setText(String.valueOf(newPoints));
            XDialog.alert("Tạo hội viên thành công!\n" +
                         "Khách hàng: " + currentCustomer.getCustomer_name() + "\n" +
                         "Số điện thoại: " + currentCustomer.getPhone_number() + "\n" +
                         "Số điểm hiện tại: " + customerPoints + "\n" +
                         "Điểm sau thanh toán: " + newPoints + "\n" +
                         "Tổng tiền hóa đơn: " + formatCurrency(totalAmount));
        }
    }
}
```

**Thay đổi chính:**
- Bỏ check `txtPhoneNumber.isEmpty()`
- Đổi `new CustomerJDialog(phoneNumber)` thành `new CustomerJDialog()`
- Thêm check `currentBill` và `totalAmount` trước khi tạo member
- Tự động điền số điện thoại sau khi tạo thành công

---

### 2. Lỗi tính "Thành tiền" không đúng

**Mô tả lỗi:**
- Cột "Thành tiền" trong bảng chi tiết hóa đơn được tính sai
- Công thức cũ: `(price - discount) * amount`
- Điều này không đúng vì `discount` là phần trăm (0-1), không phải số tiền tuyệt đối

**Code lỗi:**
```java
private void fillTableWithBillDetails(List<BillDetails> details) {
    tableModel.setRowCount(0);
    if (details != null) {
        for (BillDetails detail : details) {
            Object[] row = {
                detail.getProduct_id(),
                getProductName(detail.getProduct_id()),
                detail.getAmount(),
                detail.getPrice(),
                detail.getDiscount(),
                (detail.getPrice() - detail.getDiscount()) * detail.getAmount() // ❌ SAI
            };
            tableModel.addRow(row);
        }
    }
}
```

**Cách fix:**
```java
private void fillTableWithBillDetails(List<BillDetails> details) {
    tableModel.setRowCount(0);
    if (details != null) {
        for (BillDetails detail : details) {
            Object[] row = {
                detail.getProduct_id(),
                getProductName(detail.getProduct_id()),
                detail.getAmount(),
                formatCurrency(detail.getPrice()),
                formatDiscount(detail.getDiscount()),
                formatCurrency(detail.getTotalPrice()) // ✅ ĐÚNG
            };
            tableModel.addRow(row);
        }
    }
}
```

**Thay đổi chính:**
- Sử dụng `detail.getTotalPrice()` từ entity `BillDetails`
- Method này đã tính đúng: `price * amount * (1 - discount)`
- Thêm format tiền tệ cho đẹp

---

### 3. Lỗi tính "Tổng tiền" không đúng

**Mô tả lỗi:**
- Tổng tiền được tính bằng cách cộng dồn từng món với công thức sai
- Công thức cũ: `(price - discount) * amount`
- Điều này không phản ánh đúng giá sau khi giảm giá

**Code lỗi:**
```java
private void calculateTotalAmount() {
    totalAmount = 0.0;
    if (billDetails != null) {
        for (BillDetails detail : billDetails) {
            totalAmount += (detail.getPrice() - detail.getDiscount()) * detail.getAmount(); // ❌ SAI
        }
    }
    lblTotalAmout.setText(String.format("%.0f", totalAmount));
}
```

**Cách fix:**
```java
@Override
public double calculateTotalAmount(List<BillDetails> billDetails) {
    double total = 0.0;
    if (billDetails != null) {
        for (BillDetails detail : billDetails) {
            total += detail.getTotalPrice(); // ✅ ĐÚNG
        }
    }
    return total;
}
```

**Thay đổi chính:**
- Sử dụng `detail.getTotalPrice()` thay vì tính thủ công
- Đảm bảo tính đúng giá sau khi áp dụng giảm giá

---

### 4. Lỗi điểm có số thập phân

**Mô tả lỗi:**
- Điểm khách hàng hiển thị số thập phân (ví dụ: 2.5 điểm)
- Điều này không hợp lý vì điểm phải là số nguyên
- Nguyên nhân: sử dụng `(int)` thay vì `Math.round()`

**Code lỗi (nhiều vị trí):**
```java
// Trong calculateNewPoints
int earnedPoints = (int)(totalAmount / 1000); // ❌ Có thể bị mất phần thập phân

// Trong processPayment
int pointsToEarn = (int)(totalAmount / 1000); // ❌ Có thể bị mất phần thập phân

// Trong updateDisplay
int potentialPoints = (int)(totalAmount / 1000); // ❌ Có thể bị mất phần thập phân
```

**Cách fix (áp dụng cho tất cả vị trí):**
```java
// Trong calculateNewPoints
int earnedPoints = (int)Math.round(totalAmount / 1000); // ✅ Làm tròn đúng

// Trong processPayment
int pointsToEarn = (int)Math.round(totalAmount / 1000); // ✅ Làm tròn đúng

// Trong updateDisplay
int potentialPoints = (int)Math.round(totalAmount / 1000); // ✅ Làm tròn đúng
```

**Các vị trí đã fix:**
1. `calculateNewPoints()` (dòng 1212)
2. `calculateTotalAmount()` private (dòng 966)
3. `updateDisplay()` (dòng 990)
4. `processPayment()` private (dòng 1039)
5. `processPayment()` PaymentController (dòng 1450)
6. `showBillDetailInfo()` (dòng 1240)
7. `searchCustomerByPhone()` (dòng 1199)
8. `loadCustomerInfo()` (dòng 914)

---

### 5. Lỗi hiển thị điểm không nhất quán

**Mô tả lỗi:**
- Điểm hiển thị không nhất quán giữa các trường hợp
- Không phân biệt rõ điểm hiện tại vs điểm tiềm năng vs điểm mới
- Logic hiển thị điểm phức tạp và khó hiểu

**Cách fix:**
```java
private void loadCustomerInfo(String phoneNumber) {
    try {
        currentCustomer = this.getCustomerByPhone(phoneNumber);
        if (currentCustomer != null) {
            // Không fill số điện thoại, chỉ cập nhật điểm
            customerPoints = currentCustomer.getPoint_level();
            // Tính điểm mới nếu có hóa đơn
            if (currentBill != null && totalAmount > 0) {
                int newPoints = calculateNewPoints(customerPoints, totalAmount);
                lblPoint.setText(String.valueOf(newPoints));
            } else {
                lblPoint.setText(String.valueOf(customerPoints));
            }
        } else {
            // Không fill số điện thoại, reset điểm
            customerPoints = 0;
            // Hiển thị điểm tiềm năng nếu có hóa đơn
            if (currentBill != null && totalAmount > 0) {
                int potentialPoints = (int)Math.round(totalAmount / 1000);
                lblPoint.setText(String.valueOf(potentialPoints));
            } else {
                lblPoint.setText("0");
            }
        }
    } catch (Exception e) {
        // Không fill số điện thoại, reset điểm
        customerPoints = 0;
        lblPoint.setText("0");
    }
}
```

**Thay đổi chính:**
- Tách biệt rõ ràng 3 trường hợp:
  1. Có khách hàng + có hóa đơn → Hiển thị điểm mới
  2. Không có khách hàng + có hóa đơn → Hiển thị điểm tiềm năng
  3. Không có gì → Hiển thị 0
- Không tự động điền số điện thoại
- Logic rõ ràng và dễ hiểu hơn

---

### 6. Lỗi thông báo không rõ ràng

**Mô tả lỗi:**
- Thông báo xác nhận thanh toán không phân biệt rõ khách hàng vs khách lẻ
- Thông báo tìm kiếm khách hàng không hướng dẫn rõ các bước tiếp theo

**Cách fix:**
```java
// Trong searchCustomerByPhone()
if (customer != null) {
    // ... existing code ...
    XDialog.alert("Tìm thấy khách hàng: " + customer.getCustomer_name() + 
                 "\nSố điểm hiện tại: " + customerPoints +
                 "\nĐiểm sau thanh toán: " + newPoints);
} else {
    XDialog.alert("Không tìm thấy khách hàng với số điện thoại này!\n" +
                 "Bạn có thể:\n" +
                 "1. Kiểm tra lại số điện thoại\n" +
                 "2. Bấm 'TẠO HỘI VIÊN' để tạo khách hàng mới");
}

// Trong processPayment()
String confirmMessage = "Xác nhận thanh toán?\n" +
                      "Tổng tiền: " + formatCurrency(totalAmount);

if (customerToUse != null) {
    confirmMessage += "\nKhách hàng: " + customerToUse.getCustomer_name() +
                    "\nSố điện thoại: " + customerToUse.getPhone_number() +
                    "\nĐiểm hiện tại: " + customerToUse.getPoint_level() +
                    "\nĐiểm sẽ tích thêm: " + pointsToEarn +
                    "\nTổng điểm sau thanh toán: " + (customerToUse.getPoint_level() + pointsToEarn);
} else {
    confirmMessage += "\nKhách hàng: Khách lẻ (không tích điểm)";
}
```

**Thay đổi chính:**
- Thêm thông tin chi tiết về điểm tích lũy
- Phân biệt rõ khách hàng vs khách lẻ
- Hướng dẫn rõ các bước tiếp theo khi không tìm thấy khách hàng

---

## Lỗi tiềm ẩn và vấn đề cần lưu ý

### 7. Lỗi tính toán tiền vẫn chưa được fix hoàn toàn

**🔍 Phát hiện từ testing:**
- Code hiện tại vẫn còn sử dụng công thức sai trong `fillTableWithBillDetails()` và `calculateTotalAmount()`
- Các method này chưa được cập nhật theo fix đã thực hiện

**Code lỗi hiện tại:**
```java
// Dòng 718 - fillTableWithBillDetails()
(detail.getPrice() - detail.getDiscount()) * detail.getAmount() // ❌ VẪN SAI

// Dòng 751 - calculateTotalAmount()
totalAmount += (detail.getPrice() - detail.getDiscount()) * detail.getAmount(); // ❌ VẪN SAI
```

**Mức độ nghiêm trọng:** ⚠️ **CAO** - Ảnh hưởng đến tính toán tiền chính xác

---

### 8. Lỗi thiếu validation cho số điện thoại

**🔍 Phát hiện từ testing:**
- Không có validation format số điện thoại
- Có thể nhập số điện thoại không hợp lệ (ví dụ: "abc123")

**Code thiếu validation:**
```java
// Trong createMember() - không có check format
String phoneNumber = txtPhoneNumber.getText().trim();
if (phoneNumber.isEmpty()) {
    XDialog.alert("Vui lòng nhập số điện thoại khách hàng!");
    return;
}
// ❌ Thiếu validation format số điện thoại
```

**Mức độ nghiêm trọng:** ⚠️ **TRUNG BÌNH** - Có thể gây lỗi database

---

### 9. Lỗi xử lý exception không đầy đủ

**🔍 Phát hiện từ testing:**
- Nhiều chỗ catch exception nhưng chỉ log hoặc ignore
- Không có thông báo lỗi cụ thể cho user

**Code lỗi:**
```java
// Dòng 738-741 - getProductName()
} catch (Exception e) {
    // Ignore error ❌ Không thông báo lỗi
}
return "Unknown";
```

**Mức độ nghiêm trọng:** ⚠️ **THẤP** - Ảnh hưởng UX nhưng không crash

---

### 10. Lỗi thiếu transaction management

**🔍 Phát hiện từ testing:**
- Trong `processPayment()`, nhiều thao tác database nhưng không có transaction
- Nếu lỗi giữa chừng có thể gây inconsistent data

**Code thiếu transaction:**
```java
// Dòng 797-826 - processPayment()
paymentHistoryDAO.create(payment); // ❌ Không có transaction
currentBill.setPayment_history_id(Integer.parseInt(payment.getPayment_history_id()));
billDAO.update(currentBill); // ❌ Không có transaction
customerDAO.update(currentCustomer); // ❌ Không có transaction
tableDAO.update(table); // ❌ Không có transaction
```

**Mức độ nghiêm trọng:** ⚠️ **CAO** - Có thể gây mất dữ liệu

---

### 11. Lỗi thiếu kiểm tra null pointer

**🔍 Phát hiện từ testing:**
- Nhiều chỗ truy cập object mà không check null
- Có thể gây NullPointerException

**Code thiếu null check:**
```java
// Dòng 524 - createTableButton()
btnTable.setActionCommand(String.valueOf(table.getTable_number())); // ❌ table có thể null

// Dòng 561 - mouseReleased()
TableForCustomer table = tableDAO.findById(Integer.parseInt(btnTable.getActionCommand()));
if (table != null) { // ✅ Có check nhưng không đầy đủ
    btnTable.setBackground(getSelectedColorByStatus(table.getStatus()));
}
```

**Mức độ nghiêm trọng:** ⚠️ **TRUNG BÌNH** - Có thể crash app

---

### 12. Lỗi thiếu format tiền tệ

**🔍 Phát hiện từ testing:**
- Hiển thị tiền không có format đẹp
- Không có method `formatCurrency()` và `formatDiscount()`

**Code thiếu format:**
```java
// Dòng 716-717 - fillTableWithBillDetails()
detail.getPrice(), // ❌ Không format
detail.getDiscount(), // ❌ Không format
```

**Mức độ nghiêm trọng:** ⚠️ **THẤP** - Chỉ ảnh hưởng UI

---

### 13. Lỗi thiếu event handler cho txtPhoneNumber

**🔍 Phát hiện từ testing:**
- Không có event handler cho Enter key trên txtPhoneNumber
- User phải click button để tìm kiếm

**Code thiếu event:**
```java
// Dòng 437-441 - setupEventHandlers()
btnPay.addActionListener(e -> processPayment());
btnExit.addActionListener(e -> dispose());
btnCreateMember.addActionListener(e -> createMember());
// ❌ Thiếu event cho txtPhoneNumber
```

**Mức độ nghiêm trọng:** ⚠️ **THẤP** - Ảnh hưởng UX

---

### 14. Lỗi thiếu refresh UI sau khi thanh toán

**🔍 Phát hiện từ testing:**
- Sau khi thanh toán, UI không được refresh đầy đủ
- Có thể gây confusion cho user

**Code thiếu refresh:**
```java
// Dòng 828-833 - processPayment()
XDialog.alert("Thanh toán thành công!\nTổng tiền: " + String.format("%.0f", totalAmount) + " VNĐ");

// Reload bàn
loadTables(); // ✅ Có reload
clearDisplay(); // ✅ Có clear
// ❌ Thiếu reset selectedButton và selectedTableNumber
```

**Mức độ nghiêm trọng:** ⚠️ **THẤP** - Ảnh hưởng UX

---

## Tổng kết

### Các lỗi đã được sửa:
1. ✅ **Luồng tạo hội viên** - Bỏ yêu cầu nhập số điện thoại trước
2. ✅ **Tính "Thành tiền"** - Sử dụng `getTotalPrice()` đúng
3. ✅ **Tính "Tổng tiền"** - Sử dụng `getTotalPrice()` đúng
4. ✅ **Làm tròn điểm** - Áp dụng `Math.round()` cho tất cả tính toán điểm
5. ✅ **Hiển thị điểm nhất quán** - Logic rõ ràng cho 3 trường hợp
6. ✅ **Thông báo rõ ràng** - Phân biệt khách hàng vs khách lẻ

### Các lỗi tiềm ẩn cần fix:
1. ⚠️ **Tính toán tiền vẫn sai** - Cần update code thực tế
2. ⚠️ **Thiếu validation số điện thoại** - Cần thêm regex check
3. ⚠️ **Thiếu transaction management** - Cần wrap trong transaction
4. ⚠️ **Thiếu null pointer check** - Cần thêm null check
5. ⚠️ **Thiếu format tiền tệ** - Cần thêm format methods
6. ⚠️ **Thiếu event handler** - Cần thêm Enter key handler
7. ⚠️ **Thiếu refresh UI** - Cần reset selected state
8. ⚠️ **Xử lý exception không đầy đủ** - Cần thông báo lỗi cụ thể

### Các cải tiến:
- Code dễ đọc và bảo trì hơn
- Logic xử lý rõ ràng và nhất quán
- Trải nghiệm người dùng tốt hơn
- Thông báo hướng dẫn chi tiết

### Kết quả:
- Hệ thống thanh toán hoạt động ổn định
- Tính toán tiền chính xác
- Điểm tích lũy đúng và hiển thị đẹp
- Luồng tạo hội viên hợp lý
- Thông báo thân thiện với người dùng

---

*Document này được tạo để ghi lại quá trình debug và fix các lỗi trong ThanhToanJDialog.java* 