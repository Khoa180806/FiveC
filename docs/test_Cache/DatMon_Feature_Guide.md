# Hướng Dẫn Tính Năng Đặt Món

## Tổng Quan
Tính năng đặt món cho phép nhân viên thêm các món ăn vào hóa đơn của khách hàng. Tính năng này được tích hợp vào `HoaDonJDialog` và sử dụng `DatMonJDialog` để giao diện đặt món.

## Cách Sử Dụng

### 1. Mở Dialog Đặt Món
- Từ `HoaDonJDialog`, chọn một hóa đơn
- Nhấn nút "ĐẶT MÓN" 
- Dialog `DatMonJDialog` sẽ mở ra

### 2. Giao Diện Đặt Món
Dialog đặt món có 2 tab chính:
- **Tab "Mì cay"**: Hiển thị các món mì cay có sẵn
- **Tab "Nước"**: Hiển thị các loại nước uống

### 3. Chức Năng Tìm Kiếm
- Sử dụng ô tìm kiếm để lọc món ăn theo tên
- Kết quả tìm kiếm sẽ hiển thị ngay lập tức

### 4. Thêm Món Vào Giỏ Hàng
- Click vào các nút món ăn để thêm vào giỏ hàng
- Mỗi lần click sẽ tăng số lượng lên 1
- Nếu món đã có trong giỏ hàng, số lượng sẽ tăng

### 5. Quản Lý Giỏ Hàng
- **Xem giỏ hàng**: Bảng bên phải hiển thị các món đã chọn
- **Xóa món**: Chọn món trong bảng và nhấn "XÓA"
- **Tổng số lượng**: Hiển thị tổng số lượng món đã chọn

### 6. Đặt Món
- Nhấn nút "ĐẶT MÓN" để hoàn tất
- Hệ thống sẽ lưu các món vào database
- Dialog sẽ đóng và cập nhật hóa đơn

## Cấu Trúc Code

### DatMonJDialog.java
- **Constructor**: Nhận `HoaDonJDialog` parent và `Bill` hiện tại
- **loadProducts()**: Tải danh sách sản phẩm từ database
- **addToCart()**: Thêm sản phẩm vào giỏ hàng
- **placeOrder()**: Lưu đơn hàng vào database
- **updateCartDisplay()**: Cập nhật hiển thị giỏ hàng

### BillDetailsDAO.java
- **findByBillId()**: Tìm chi tiết hóa đơn theo bill_id

### BillDetailsDAOImpl.java
- **findByBillId()**: Implementation để load chi tiết hóa đơn

## Database Schema

### Bảng PRODUCT
```sql
SELECT * FROM PRODUCT WHERE category_id = 'MI' AND is_available = 1
SELECT * FROM PRODUCT WHERE category_id = 'DRINK' AND is_available = 1
```

### Bảng BILL_DETAILS
```sql
INSERT INTO BILL_DETAILS(bill_details_id, bill_id, product_id, amount, price, discount) 
VALUES(?, ?, ?, ?, ?, ?)
```

## Lưu Ý Kỹ Thuật

1. **Xử lý lỗi**: Tất cả các thao tác database đều có try-catch
2. **Validation**: Kiểm tra hóa đơn tồn tại trước khi đặt món
3. **Refresh**: Tự động cập nhật hóa đơn sau khi đặt món thành công
4. **ID Generation**: Tự động tạo ID cho bill details

## Test

Để test tính năng:
1. Chạy `TestDatMon.java` để mở dialog test
2. Hoặc sử dụng từ `HoaDonJDialog` với hóa đơn thực

## Troubleshooting

### Lỗi thường gặp:
1. **"Không tìm thấy hóa đơn"**: Kiểm tra currentBill có null không
2. **"Lỗi khi tải sản phẩm"**: Kiểm tra kết nối database và bảng PRODUCT
3. **"Lỗi khi đặt món"**: Kiểm tra quyền ghi vào bảng BILL_DETAILS 