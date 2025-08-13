# XValidation Usage Guide

## Tổng quan
XValidation là utility class cung cấp các phương thức validation toàn diện cho hệ thống quản lý quán mì cay FiveC. Class này được thiết kế để đảm bảo tính nhất quán và bảo mật dữ liệu trong toàn bộ ứng dụng.

## Các tính năng chính

### 1. Validation cơ bản
```java
// Kiểm tra chuỗi rỗng
XValidation.isEmpty(""); // true
XValidation.isNotEmpty("hello"); // true
XValidation.isBlank("   "); // true
XValidation.isNotBlank("hello"); // true
```

### 2. Validation số điện thoại
```java
// Số điện thoại Việt Nam
XValidation.isVietnamesePhone("0123456789"); // true
XValidation.isVietnamesePhone("0987654321"); // true

// Số điện thoại quốc tế
XValidation.isInternationalPhone("+84901234567"); // true

// Bất kỳ định dạng nào
XValidation.isPhone("0123456789"); // true
XValidation.isPhone("+84901234567"); // true

// Lấy thông báo lỗi
String error = XValidation.getPhoneValidationMessage("123");
// "Số điện thoại phải bắt đầu bằng 0 hoặc +84!"
```

### 3. Validation khách hàng
```java
// Tên khách hàng
XValidation.isValidCustomerName("Nguyễn Văn A"); // true
XValidation.isValidCustomerName("A"); // false (quá ngắn)

// Điểm khách hàng
XValidation.isValidPointLevel("100"); // true
XValidation.isValidPointLevel("-10"); // false

// Hạng khách hàng
XValidation.isValidCustomerRanking("Gold"); // true
XValidation.isValidCustomerRanking("VIP"); // true

// Kiểm tra tính nhất quán điểm-hạng
XValidation.isCustomerDataConsistent(500, "Diamond"); // true
XValidation.isCustomerDataConsistent(100, "Bronze"); // false

// Lấy thông báo lỗi
String error = XValidation.getCustomerNameValidationMessage("A");
// "Tên khách hàng quá ngắn! (Tối thiểu 2 ký tự)"
```

### 4. Validation sản phẩm
```java
// Mã sản phẩm
XValidation.isValidProductId("MI001"); // true
XValidation.isValidProductId("MI-001"); // false (có ký tự đặc biệt)

// Tên sản phẩm
XValidation.isValidProductName("Mì cay hải sản"); // true
XValidation.isValidProductName("Mì cay <script>"); // false

// Giá sản phẩm
XValidation.isValidProductPrice("50000"); // true
XValidation.isValidProductPrice("-1000"); // false

// Giảm giá
XValidation.isValidProductDiscount("0.1"); // true
XValidation.isValidProductDiscount("1.5"); // false

// Đơn vị
XValidation.isValidProductUnit("phần"); // true
XValidation.isValidProductUnit("kg"); // true
```

### 5. Validation người dùng
```java
// Email
XValidation.isEmail("user@example.com"); // true
XValidation.isEmail("invalid-email"); // false

// Username
XValidation.isUsername("user123"); // true
XValidation.isUsername("user-123"); // false

// Password
XValidation.isPassword("Password123!"); // true
XValidation.isSimplePassword("123456"); // true (đơn giản hơn)
```

### 6. Validation số
```java
// Số cơ bản
XValidation.isNumber("123.45"); // true
XValidation.isNumber("-123"); // true

// Số dương
XValidation.isPositiveNumber("123"); // true
XValidation.isPositiveNumber("-123"); // false

// Số không âm
XValidation.isNonNegativeNumber("0"); // true
XValidation.isNonNegativeNumber("-1"); // false

// Số nguyên
XValidation.isInteger("123"); // true
XValidation.isInteger("123.45"); // false

// Số nguyên dương
XValidation.isPositiveInteger("123"); // true
XValidation.isPositiveInteger("0"); // false
```

### 7. Validation ngày giờ
```java
// Ngày
XValidation.isDate("2024-01-15"); // true
XValidation.isDate("15/01/2024"); // false

// Giờ
XValidation.isTime("14:30"); // true
XValidation.isTime("25:00"); // false

// Ngày giờ
XValidation.isDateTime("2024-01-15 14:30"); // true
```

### 8. Validation hóa đơn
```java
// Số tiền
XValidation.isValidBillAmount("50000"); // true
XValidation.isValidBillAmount("0"); // false

// Số lượng
XValidation.isValidQuantity("2"); // true
XValidation.isValidQuantity("0"); // false
```

### 9. Bảo mật
```java
// Sanitize input
String clean = XValidation.sanitizeInput("Hello <script>alert('xss')</script>");
// "Hello alert('xss')"

// Kiểm tra ký tự nguy hiểm
XValidation.containsDangerousChars("<script>"); // true
XValidation.containsDangerousChars("Hello"); // false
```

### 10. Validation nhiều trường
```java
String[][] validations = {
    {"Số điện thoại", "0123456789", "phone"},
    {"Tên khách hàng", "Nguyễn Văn A", "customer_name"},
    {"Email", "user@example.com", "email"}
};

String errors = XValidation.validateMultipleFields(validations);
if (!errors.isEmpty()) {
    System.out.println("Lỗi validation:\n" + errors);
}
```

## Cách sử dụng trong CustomerManagement

```java
// Thay thế validation cũ
private void updateCustomerData() {
    // Lấy dữ liệu từ form
    String phoneNumber = txt_phone_number.getText().trim();
    String customerName = txt_customer_name.getText().trim();
    String pointLevelStr = txt_point_level.getText().trim();
    String levelRanking = txt_level_ranking.getText().trim();
    
    // Validation sử dụng XValidation
    if (XValidation.isEmpty(phoneNumber)) {
        XDialog.error("Số điện thoại không được để trống!");
        return;
    }
    
    if (!XValidation.isVietnamesePhone(phoneNumber)) {
        XDialog.error(XValidation.getPhoneValidationMessage(phoneNumber));
        return;
    }
    
    if (!XValidation.isValidCustomerName(customerName)) {
        XDialog.error(XValidation.getCustomerNameValidationMessage(customerName));
        return;
    }
    
    if (!XValidation.isValidPointLevel(pointLevelStr)) {
        XDialog.error("Điểm phải là số không âm!");
        return;
    }
    
    // Sanitize input
    phoneNumber = XValidation.sanitizeInput(phoneNumber);
    customerName = XValidation.sanitizeInput(customerName);
    
    // Tiếp tục xử lý...
}
```

## Cách sử dụng trong ProductManagement

```java
private void validateProduct() {
    String productId = txtProduct_Id.getText().trim();
    String productName = txtNameProduct.getText().trim();
    String priceStr = txtPrice.getText().trim();
    String discountStr = txtDiscount.getText().trim();
    
    // Validation sử dụng XValidation
    if (!XValidation.isValidProductId(productId)) {
        XDialog.error("Mã sản phẩm không hợp lệ!");
        return;
    }
    
    if (!XValidation.isValidProductName(productName)) {
        XDialog.error("Tên sản phẩm không hợp lệ!");
        return;
    }
    
    if (!XValidation.isValidProductPrice(priceStr)) {
        XDialog.error("Giá sản phẩm phải là số dương!");
        return;
    }
    
    if (!XValidation.isValidProductDiscount(discountStr)) {
        XDialog.error("Giảm giá phải từ 0 đến 100%!");
        return;
    }
    
    // Sanitize input
    productId = XValidation.sanitizeInput(productId);
    productName = XValidation.sanitizeInput(productName);
    
    // Tiếp tục xử lý...
}
```

## Lợi ích của XValidation

### 1. Tính nhất quán
- Tất cả validation rules được tập trung trong một class
- Đảm bảo cùng một rule được áp dụng ở mọi nơi
- Dễ dàng thay đổi rules mà không cần sửa nhiều file

### 2. Bảo mật
- Sanitize input để tránh SQL injection
- Kiểm tra ký tự nguy hiểm
- Validation chặt chẽ cho từng loại dữ liệu

### 3. Dễ sử dụng
- API đơn giản và rõ ràng
- Thông báo lỗi chi tiết và bằng tiếng Việt
- Hỗ trợ validation nhiều trường cùng lúc

### 4. Hiệu suất
- Sử dụng Pattern.compile() để tối ưu regex
- Validation nhanh và hiệu quả
- Không tạo object không cần thiết

### 5. Mở rộng
- Dễ dàng thêm validation rules mới
- Hỗ trợ nhiều định dạng dữ liệu
- Có thể customize theo yêu cầu cụ thể

## Best Practices

1. **Luôn sử dụng XValidation thay vì tự viết validation**
2. **Sanitize input trước khi lưu vào database**
3. **Hiển thị thông báo lỗi chi tiết cho người dùng**
4. **Validation ngay khi user nhập liệu (real-time)**
5. **Sử dụng validateMultipleFields() cho form có nhiều trường**

## Migration Guide

Để chuyển từ validation cũ sang XValidation:

1. **Thay thế validation methods cũ:**
```java
// Cũ
if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
    // handle error
}

// Mới
if (XValidation.isEmpty(phoneNumber)) {
    XDialog.error("Số điện thoại không được để trống!");
    return;
}
```

2. **Sử dụng thông báo lỗi có sẵn:**
```java
// Cũ
if (!phoneNumber.matches("^0[0-9]{9,10}$")) {
    XDialog.error("Số điện thoại không hợp lệ!");
}

// Mới
if (!XValidation.isVietnamesePhone(phoneNumber)) {
    XDialog.error(XValidation.getPhoneValidationMessage(phoneNumber));
}
```

3. **Sanitize input:**
```java
// Thêm vào đầu method
phoneNumber = XValidation.sanitizeInput(phoneNumber);
customerName = XValidation.sanitizeInput(customerName);
```

XValidation đã được thiết kế để tương thích với hệ thống hiện tại và cung cấp các tính năng validation toàn diện cho project quản lý quán mì cay FiveC.
