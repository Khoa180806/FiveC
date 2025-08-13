# Test Cases - Popup Menu Quản Lý Hóa Đơn

## 📋 Tổng Quan Test Cases
Tài liệu này mô tả các test cases chi tiết để kiểm tra tính năng popup menu "Quản lý hóa đơn" với 3 chức năng: Hóa đơn, Phương thức thanh toán, và Lịch sử.

## 🎯 Test Cases Cơ Bản

### TC001: Mở Popup Menu
**Mục tiêu**: Kiểm tra popup menu xuất hiện khi click button
- **Precondition**: Ứng dụng đã khởi động, MainUI hiển thị
- **Steps**:
  1. Click vào button "Quản Lý Hóa Đơn"
  2. Quan sát popup menu xuất hiện
- **Expected Result**: 
  - Popup menu xuất hiện ngay bên dưới button
  - 3 menu items hiển thị: "Hóa đơn", "Phương thức thanh toán", "Lịch sử"
  - Popup có màu nền đỏ (#862B2B) với viền trắng
- **Status**: ✅ Pass/Fail

### TC002: Hover Effects
**Mục tiêu**: Kiểm tra hiệu ứng hover cho menu items
- **Precondition**: Popup menu đã mở
- **Steps**:
  1. Di chuột qua menu item "Hóa đơn"
  2. Di chuột qua menu item "Phương thức thanh toán"
  3. Di chuột qua menu item "Lịch sử"
- **Expected Result**:
  - Menu item đổi màu nền thành #B43232 khi hover
  - Màu trở về #862B2B khi rời chuột
  - Hiệu ứng mượt mà, không lag
- **Status**: ✅ Pass/Fail

### TC003: Tooltips Display
**Mục tiêu**: Kiểm tra tooltips hiển thị khi hover
- **Precondition**: Popup menu đã mở
- **Steps**:
  1. Hover chuột qua "Hóa đơn" → Đợi 1 giây
  2. Hover chuột qua "Phương thức thanh toán" → Đợi 1 giây
  3. Hover chuột qua "Lịch sử" → Đợi 1 giây
- **Expected Result**:
  - Tooltip "Quản lý hóa đơn và chi tiết hóa đơn" hiển thị
  - Tooltip "Quản lý các phương thức thanh toán" hiển thị
  - Tooltip "Xem lịch sử thanh toán và hóa đơn" hiển thị
- **Status**: ✅ Pass/Fail

## 🔧 Test Cases Chức Năng

### TC004: Mở BillManagement
**Mục tiêu**: Kiểm tra mở form BillManagement từ popup
- **Precondition**: Popup menu đã mở
- **Steps**:
  1. Click vào menu item "Hóa đơn"
  2. Quan sát form mới mở
- **Expected Result**:
  - Form BillManagement mở thành công
  - Console log: "🔄 Đang mở BillManagement..." và "✅ BillManagement đã được mở thành công!"
  - Form hiển thị đầy đủ giao diện
- **Status**: ✅ Pass/Fail

### TC005: Mở PaymentMethodManagement
**Mục tiêu**: Kiểm tra mở form PaymentMethodManagement từ popup
- **Precondition**: Popup menu đã mở
- **Steps**:
  1. Click vào menu item "Phương thức thanh toán"
  2. Quan sát form mới mở
- **Expected Result**:
  - Form PaymentMethodManagement mở thành công
  - Console log: "🔄 Đang mở PaymentMethodManagement..." và "✅ PaymentMethodManagement đã được mở thành công!"
  - Form hiển thị đầy đủ giao diện
- **Status**: ✅ Pass/Fail

### TC006: Mở HistoryManagement
**Mục tiêu**: Kiểm tra mở form HistoryManagement từ popup
- **Precondition**: Popup menu đã mở
- **Steps**:
  1. Click vào menu item "Lịch sử"
  2. Quan sát form mới mở
- **Expected Result**:
  - Form HistoryManagement mở thành công
  - Console log: "🔄 Đang mở HistoryManagement..." và "✅ HistoryManagement đã được mở thành công!"
  - Form hiển thị đầy đủ giao diện
- **Status**: ✅ Pass/Fail

## 🛡️ Test Cases Xử Lý Lỗi

### TC007: Error Handling - BillManagement
**Mục tiêu**: Kiểm tra xử lý lỗi khi mở BillManagement
- **Precondition**: File BillManagement.java bị lỗi hoặc không tồn tại
- **Steps**:
  1. Click vào menu item "Hóa đơn"
  2. Quan sát thông báo lỗi
- **Expected Result**:
  - JOptionPane hiển thị thông báo: "Lỗi khi mở Quản lý Hóa đơn: [error message]"
  - Console log: "❌ Lỗi khi mở BillManagement: [error message]"
  - Ứng dụng không crash
- **Status**: ✅ Pass/Fail

### TC008: Error Handling - PaymentMethodManagement
**Mục tiêu**: Kiểm tra xử lý lỗi khi mở PaymentMethodManagement
- **Precondition**: File PaymentMethodManagement.java bị lỗi
- **Steps**:
  1. Click vào menu item "Phương thức thanh toán"
  2. Quan sát thông báo lỗi
- **Expected Result**:
  - JOptionPane hiển thị: "Lỗi khi mở Quản lý Phương thức thanh toán: [error message]"
  - Console log: "❌ Lỗi khi mở PaymentMethodManagement: [error message]"
- **Status**: ✅ Pass/Fail

### TC009: Error Handling - HistoryManagement
**Mục tiêu**: Kiểm tra xử lý lỗi khi mở HistoryManagement
- **Precondition**: File HistoryManagement.java bị lỗi
- **Steps**:
  1. Click vào menu item "Lịch sử"
  2. Quan sát thông báo lỗi
- **Expected Result**:
  - JOptionPane hiển thị: "Lỗi khi mở Quản lý Lịch sử: [error message]"
  - Console log: "❌ Lỗi khi mở HistoryManagement: [error message]"
- **Status**: ✅ Pass/Fail

## 🎨 Test Cases UI/UX

### TC010: Responsive Design
**Mục tiêu**: Kiểm tra popup menu thích ứng với kích thước button
- **Precondition**: Button có kích thước khác nhau
- **Steps**:
  1. Thay đổi kích thước button "Quản Lý Hóa Đơn"
  2. Click để mở popup
  3. Quan sát kích thước menu items
- **Expected Result**:
  - Menu items tự động điều chỉnh width theo button
  - Height cố định 25px cho mỗi item
  - Text không bị cắt hoặc overflow
- **Status**: ✅ Pass/Fail

### TC011: Visual Styling
**Mục tiêu**: Kiểm tra styling của popup menu
- **Precondition**: Popup menu đã mở
- **Steps**:
  1. Quan sát màu sắc và font
  2. Kiểm tra padding và border
- **Expected Result**:
  - Background: #862B2B
  - Foreground: #FFFFFF (trắng)
  - Font: Segoe UI Bold, size 12
  - Border: EmptyBorder(8, 15, 8, 15)
  - Popup border: LineBorder trắng 2px
- **Status**: ✅ Pass/Fail

## 🔄 Test Cases Tương Tác

### TC012: Multiple Form Instances
**Mục tiêu**: Kiểm tra mở nhiều form cùng lúc
- **Precondition**: Popup menu đã mở
- **Steps**:
  1. Click "Hóa đơn" → Mở BillManagement
  2. Click "Phương thức thanh toán" → Mở PaymentMethodManagement
  3. Click "Lịch sử" → Mở HistoryManagement
- **Expected Result**:
  - Cả 3 form đều mở thành công
  - Không có conflict giữa các form
  - Mỗi form hoạt động độc lập
- **Status**: ✅ Pass/Fail

### TC013: Popup Close Behavior
**Mục tiêu**: Kiểm tra cách đóng popup menu
- **Precondition**: Popup menu đã mở
- **Steps**:
  1. Click bất kỳ đâu ngoài popup
  2. Nhấn phím ESC
  3. Click lại vào button
- **Expected Result**:
  - Popup đóng khi click ngoài
  - Popup đóng khi nhấn ESC
  - Popup mở lại khi click button
- **Status**: ✅ Pass/Fail

### TC014: Keyboard Navigation
**Mục tiêu**: Kiểm tra điều hướng bằng bàn phím
- **Precondition**: Popup menu đã mở
- **Steps**:
  1. Nhấn phím mũi tên xuống
  2. Nhấn phím mũi tên lên
  3. Nhấn Enter để chọn
- **Expected Result**:
  - Menu item được highlight khi di chuyển
  - Enter mở form tương ứng
  - ESC đóng popup
- **Status**: ✅ Pass/Fail

## 📊 Test Cases Performance

### TC015: Popup Open Speed
**Mục tiêu**: Kiểm tra tốc độ mở popup
- **Precondition**: Ứng dụng đã khởi động
- **Steps**:
  1. Đo thời gian từ lúc click đến lúc popup xuất hiện
  2. Lặp lại 10 lần
- **Expected Result**:
  - Thời gian trung bình < 100ms
  - Không có lag hoặc delay đáng kể
- **Status**: ✅ Pass/Fail

### TC016: Form Open Speed
**Mục tiêu**: Kiểm tra tốc độ mở form từ popup
- **Precondition**: Popup menu đã mở
- **Steps**:
  1. Click từng menu item
  2. Đo thời gian từ click đến form hiển thị
- **Expected Result**:
  - Thời gian mở form < 500ms
  - Không có freeze UI
- **Status**: ✅ Pass/Fail

## 🧪 Test Cases Edge Cases

### TC017: Rapid Clicking
**Mục tiêu**: Kiểm tra click nhanh liên tục
- **Precondition**: Popup menu đã mở
- **Steps**:
  1. Click nhanh liên tục vào các menu items
  2. Click nhanh vào button để mở/đóng popup
- **Expected Result**:
  - Không có lỗi exception
  - UI không bị lag
  - Chỉ form cuối cùng được mở
- **Status**: ✅ Pass/Fail

### TC018: Memory Usage
**Mục tiêu**: Kiểm tra sử dụng bộ nhớ
- **Precondition**: Ứng dụng đã chạy
- **Steps**:
  1. Mở popup nhiều lần
  2. Mở nhiều form
  3. Đóng các form
- **Expected Result**:
  - Không có memory leak
  - Bộ nhớ được giải phóng khi đóng form
- **Status**: ✅ Pass/Fail

## 📝 Test Report Template

### Test Execution Summary
```
Test Suite: Popup Menu Quản Lý Hóa Đơn
Date: [YYYY-MM-DD]
Tester: [Tên người test]
Environment: [OS, Java Version]

Results:
- Total Test Cases: 18
- Passed: [X]
- Failed: [Y]
- Skipped: [Z]
- Success Rate: [X/18 * 100]%

Failed Test Cases:
- TC[Number]: [Brief description]
- TC[Number]: [Brief description]

Notes:
[Any additional observations or issues found]
```

---

**Phiên bản**: 1.0  
**Cập nhật**: 2024-12-19  
**Tác giả**: QA Team 