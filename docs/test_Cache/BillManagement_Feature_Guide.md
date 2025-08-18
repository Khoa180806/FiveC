# Hướng Dẫn Sử Dụng Form BillManagement

## Tổng Quan
Form BillManagement được thiết kế để quản lý hóa đơn trong hệ thống quản lý quán mì cay. Form này cho phép xem, cập nhật và xóa hóa đơn, cũng như xem chi tiết các món ăn trong hóa đơn.

## Các Chức Năng Chính

### 1. Hiển Thị Danh Sách Hóa Đơn
- **Tab "Hóa đơn"**: Hiển thị danh sách tất cả hóa đơn với các thông tin:
  - Mã hóa đơn
  - Nhân viên phục vụ
  - Số bàn
  - Tổng tiền
  - Giờ vào
  - Giờ ra
  - Trạng thái

### 2. Xem Chi Tiết Hóa Đơn
- **Tab "Chi tiết"**: Khi chọn một hóa đơn từ tab "Hóa đơn", tab "Chi tiết" sẽ hiển thị:
  - Tên món ăn
  - Số lượng
  - Giá
  - Giảm giá
  - Tổng tiền

### 3. Cập Nhật Hóa Đơn
- **Nút "Update"**: Cho phép cập nhật trạng thái hóa đơn:
  - "Đang phục vụ": Hóa đơn đang được xử lý
  - "Đã thanh toán": Hóa đơn đã hoàn thành
  - "Hủy": Hóa đơn bị hủy

### 4. Xóa Hóa Đơn
- **Nút "Remove"**: Xóa hóa đơn và tất cả chi tiết liên quan
- Có xác nhận trước khi xóa để tránh xóa nhầm

### 5. Lọc Hóa Đơn
- **Combobox thời gian**: Lọc hóa đơn theo:
  - Hôm nay
  - Tuần này
  - Tháng này
  - Quý này
  - Năm này
- **Nút "LỌC"**: Áp dụng bộ lọc đã chọn

### 6. Chọn Ngày Tháng
- **Nút "..." bên cạnh ô "TỪ"**: Mở dialog chọn ngày bắt đầu với JCalendar
- **Nút "..." bên cạnh ô "ĐẾN"**: Mở dialog chọn ngày kết thúc với JCalendar
- **Combobox thời gian**: Tự động điền ngày bắt đầu và kết thúc theo khoảng thời gian được chọn

## Cách Sử Dụng

### Bước 1: Mở Form
```java
BillManagement billManagement = new BillManagement();
billManagement.setVisible(true);
```

### Bước 2: Xem Danh Sách Hóa Đơn
- Form sẽ tự động load tất cả hóa đơn khi khởi động
- Dữ liệu hiển thị trong tab "Hóa đơn"
- **Ngày mặc định**: Các ô ngày bắt đầu và kết thúc sẽ được điền mặc định với ngày hôm nay

### Bước 3: Xem Chi Tiết
- Click vào một dòng trong tab "Hóa đơn"
- Thông tin hóa đơn sẽ hiển thị ở panel bên trái
- Chi tiết món ăn sẽ hiển thị trong tab "Chi tiết"

### Bước 4: Cập Nhật Trạng Thái
1. Chọn hóa đơn cần cập nhật
2. Chọn trạng thái mới trong combobox "TRẠNG THÁI"
3. Click nút "Update"
4. Hệ thống sẽ tự động cập nhật giờ ra nếu chuyển sang "Đã thanh toán"

### Bước 5: Xóa Hóa Đơn
1. Chọn hóa đơn cần xóa
2. Click nút "Remove"
3. Xác nhận trong hộp thoại
4. Hệ thống sẽ xóa hóa đơn và tất cả chi tiết liên quan

### Bước 6: Lọc Dữ Liệu
1. **Cách 1 - Sử dụng combobox thời gian**:
   - Chọn khoảng thời gian trong combobox (Hôm nay, Tuần này, Tháng này, Quý này, Năm này)
   - Ngày bắt đầu và kết thúc sẽ tự động được điền
   - Click nút "LỌC"
   
2. **Cách 2 - Chọn ngày thủ công**:
   - Click nút "..." bên cạnh ô "TỪ" để chọn ngày bắt đầu
   - Click nút "..." bên cạnh ô "ĐẾN" để chọn ngày kết thúc
   - Click nút "LỌC"
   
3. Kết quả sẽ hiển thị trong tab "Hóa đơn"

## Xử Lý Lỗi

### Lỗi Kết Nối Database
- Hiển thị thông báo lỗi khi không thể kết nối database
- Kiểm tra cấu hình kết nối trong file config

### Lỗi Dữ Liệu
- Kiểm tra tính hợp lệ của dữ liệu trước khi thao tác
- Hiển thị thông báo lỗi cụ thể cho người dùng

### Lỗi Xóa Hóa Đơn
- Kiểm tra ràng buộc khóa ngoại trước khi xóa
- Xóa chi tiết hóa đơn trước khi xóa hóa đơn chính

## Lưu Ý Kỹ Thuật

### Date Picker Implementation
- Sử dụng JCalendar (JDateChooser) để chọn ngày
- Format ngày: dd/MM/yyyy
- Hiển thị trong dialog riêng biệt với nút OK/Cancel
- Dialog modal để đảm bảo người dùng chọn ngày trước khi tiếp tục
- **Validation**: Kiểm tra ngày kết thúc phải lớn hơn ngày bắt đầu
- **Lọc dữ liệu**: Hỗ trợ lọc theo khoảng ngày cụ thể hoặc theo thời gian định sẵn
- **Khởi tạo mặc định**: Tự động điền ngày hôm nay cho cả ô bắt đầu và kết thúc khi khởi động form
- Validation ngày hợp lệ được thực hiện khi format

### Kiểu Dữ Liệu Status
- Entity Bill sử dụng `Boolean` cho trường status
- UI hiển thị dưới dạng String: "Đang phục vụ", "Đã thanh toán", "Hủy"
- Cần chuyển đổi giữa Boolean và String khi hiển thị

### Format Tiền Tệ
- Sử dụng format `#,### VNĐ` cho các trường tiền
- Ví dụ: 1,500,000 VNĐ

### Format Ngày Giờ
- Sử dụng format `dd/MM/yyyy HH:mm:ss`
- Ví dụ: 15/12/2024 14:30:25

## Tương Lai Phát Triển

### Tính Năng Có Thể Thêm
1. **In hóa đơn**: Xuất PDF hoặc in trực tiếp
2. **Thống kê**: Biểu đồ doanh thu theo thời gian
3. **Tìm kiếm nâng cao**: Tìm theo tên khách hàng, số điện thoại
4. **Export dữ liệu**: Xuất ra Excel, CSV
5. **Phân quyền**: Chỉ admin mới có thể xóa hóa đơn

### Cải Tiến UI/UX
1. **Date picker**: ✅ Đã implement dialog chọn ngày với spinner
2. **Auto refresh**: Tự động cập nhật dữ liệu mỗi 30 giây
3. **Keyboard shortcuts**: Phím tắt cho các thao tác thường dùng
4. **Dark mode**: Giao diện tối cho mắt
5. **Responsive design**: Tương thích với màn hình khác nhau 