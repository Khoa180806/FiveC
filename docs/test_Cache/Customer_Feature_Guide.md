# Customer Management Feature Guide

## Tổng quan
Hệ thống quản lý khách hàng cho nhà hàng FiveC, cho phép thêm, cập nhật và tìm kiếm thông tin khách hàng.

## Tính năng chính

### 1. Thêm khách hàng mới
- Nhập số điện thoại (10-11 số, bắt đầu bằng 0)
- Nhập tên khách hàng (ít nhất 2 ký tự)
- Nhấn nút "THÊM MỚI" để lưu vào database

### 2. Cập nhật thông tin khách hàng
- Nhập số điện thoại của khách hàng cần cập nhật
- Hệ thống sẽ tự động tìm và hiển thị thông tin hiện tại
- Chỉnh sửa tên khách hàng
- Nhấn nút "CẬP NHẬT" để lưu thay đổi

### 3. Tìm kiếm khách hàng
- Nhập số điện thoại vào ô tìm kiếm
- Hệ thống sẽ hiển thị thông tin khách hàng nếu tìm thấy

## Cấu trúc Database

### Bảng CUSTOMER
```sql
CREATE TABLE CUSTOMER (
    phone_number NVARCHAR2(11) PRIMARY KEY,
    customer_name NVARCHAR2(50) NOT NULL,
    point_level NUMBER(10) DEFAULT 0,
    level_ranking NVARCHAR2(30),
    created_date DATE DEFAULT SYSDATE
);
```

## Các thành phần chính

### 1. CustomerController
- Quản lý logic nghiệp vụ cho khách hàng
- Xử lý thêm, sửa, xóa, tìm kiếm khách hàng
- Validation dữ liệu đầu vào

### 2. CustomerDAO & CustomerDAOImpl
- Tương tác với database
- Thực hiện các thao tác CRUD

### 3. CustomerJDialog
- Giao diện người dùng
- Xử lý sự kiện từ người dùng
- Hiển thị thông báo và validation

## Validation Rules

### Số điện thoại
- Bắt buộc nhập
- Định dạng: 10-11 số, bắt đầu bằng 0
- Ví dụ: 0123456789, 0987654321

### Tên khách hàng
- Bắt buộc nhập
- Độ dài tối thiểu: 2 ký tự
- Không được để trống

## Xử lý lỗi

### Lỗi validation
- Hiển thị thông báo lỗi cụ thể
- Focus vào trường dữ liệu có lỗi

### Lỗi database
- Hiển thị thông báo lỗi từ database
- Ghi log lỗi để debug

### Lỗi trùng lặp
- Kiểm tra số điện thoại đã tồn tại
- Hiển thị cảnh báo nếu trùng lặp

## Cách sử dụng

### Thêm khách hàng mới
1. Mở CustomerJDialog
2. Nhập số điện thoại
3. Nhập tên khách hàng
4. Nhấn nút "THÊM MỚI"
5. Xác nhận thông báo thành công

### Cập nhật khách hàng
1. Nhập số điện thoại cần cập nhật
2. Hệ thống tự động tìm và hiển thị thông tin
3. Chỉnh sửa tên khách hàng
4. Nhấn nút "CẬP NHẬT"
5. Xác nhận thông báo thành công

### Tìm kiếm khách hàng
1. Nhập số điện thoại vào ô tìm kiếm
2. Hệ thống hiển thị thông tin nếu tìm thấy
3. Nếu không tìm thấy, hiển thị thông báo

## Thống kê
- Hiển thị tổng số khách hàng trong hệ thống
- Có thể mở rộng thêm các thống kê khác

## Mở rộng tính năng
- Quản lý điểm tích lũy
- Phân loại khách hàng theo cấp độ
- Lịch sử giao dịch
- Báo cáo khách hàng 