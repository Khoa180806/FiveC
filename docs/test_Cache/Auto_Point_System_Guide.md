# Hướng Dẫn Sử Dụng Tính Năng Tích Điểm Tự Động

## Tổng Quan
Tính năng tích điểm tự động cho phép khách hàng tích điểm dựa trên số tiền thanh toán. Hệ thống sẽ tự động tính toán và cập nhật điểm cho khách hàng.

## Quy Tắc Tích Điểm
- **1 điểm = 1.000 VNĐ**
- Điểm được tính dựa trên tổng tiền hóa đơn
- Ví dụ: Hóa đơn 50.000đ = 50 điểm

## Các Tính Năng Chính

### 1. Tự Động Tìm Kiếm Khách Hàng
- Nhập số điện thoại vào ô "SỐ ĐIỆN THOẠI KHÁCH HÀNG"
- Nhấn **Enter** để tìm kiếm
- Hệ thống sẽ tự động hiển thị điểm hiện tại và điểm sẽ tích thêm

### 2. Tạo Khách Hàng Mới
- Nhập số điện thoại
- Nhấn nút **"TẠO HỘI VIÊN"**
- Điền thông tin khách hàng trong dialog
- Hệ thống sẽ tự động tính điểm dựa trên hóa đơn hiện tại

### 3. Hiển Thị Điểm Tự Động
- Khi có khách hàng và hóa đơn, hệ thống hiển thị:
  - Điểm hiện tại
  - Điểm sẽ tích thêm (trong ngoặc)
  - Ví dụ: "150 (+25)" = 150 điểm hiện tại + 25 điểm mới

### 4. Xác Nhận Thanh Toán
- Khi thanh toán, hệ thống hiển thị thông tin chi tiết:
  - Tổng tiền hóa đơn
  - Thông tin khách hàng
  - Điểm hiện tại
  - Điểm sẽ tích thêm
  - Tổng điểm sau thanh toán

### 5. Tích Điểm Tự Động
- Sau khi thanh toán thành công, điểm sẽ tự động được cộng vào tài khoản khách hàng
- Hệ thống log thông tin tích điểm để theo dõi

## Quy Trình Sử Dụng

### Bước 1: Chọn Bàn
1. Chọn bàn có hóa đơn cần thanh toán
2. Hệ thống tự động load thông tin hóa đơn

### Bước 2: Nhập Thông Tin Khách Hàng
1. **Nếu khách hàng đã có:**
   - Nhập số điện thoại
   - Nhấn Enter
   - Hệ thống tự động hiển thị điểm

2. **Nếu khách hàng chưa có:**
   - Nhập số điện thoại
   - Nhấn nút "TẠO HỘI VIÊN"
   - Điền thông tin trong dialog
   - Hệ thống tự động tính điểm

### Bước 3: Thanh Toán
1. Kiểm tra thông tin hiển thị
2. Nhấn nút "THANH TOÁN"
3. Xác nhận thông tin tích điểm
4. Hoàn tất thanh toán

## Lưu Ý Quan Trọng

### Validation
- Số điện thoại phải có định dạng hợp lệ (10-11 số, bắt đầu bằng 0)
- Tên khách hàng phải có ít nhất 2 ký tự
- Không thể tạo khách hàng với số điện thoại đã tồn tại

### Hiển Thị Điểm
- Điểm được hiển thị theo format: "Điểm hiện tại (+Điểm mới)"
- Ví dụ: "150 (+25)" = 150 điểm hiện tại, sẽ tích thêm 25 điểm

### Log Hệ Thống
- Mọi hoạt động tích điểm được log để theo dõi
- Thông tin log: Tên khách hàng, điểm cũ, điểm mới, số điểm tích thêm

## Ví Dụ Sử Dụng

### Ví Dụ 1: Khách Hàng Mới
1. Chọn bàn có hóa đơn 75.000đ
2. Nhập số điện thoại: 0123456789
3. Nhấn "TẠO HỘI VIÊN"
4. Điền tên: "Nguyễn Văn A"
5. Hệ thống hiển thị: "75 (+75)" (0 điểm + 75 điểm mới)
6. Thanh toán → Khách hàng có 75 điểm

### Ví Dụ 2: Khách Hàng Cũ
1. Chọn bàn có hóa đơn 120.000đ
2. Nhập số điện thoại: 0987654321
3. Nhấn Enter
4. Hệ thống hiển thị: "250 (+120)" (130 điểm cũ + 120 điểm mới)
5. Thanh toán → Khách hàng có 250 điểm

## Troubleshooting

### Lỗi Thường Gặp
1. **Không tìm thấy khách hàng:**
   - Kiểm tra số điện thoại đã nhập đúng chưa
   - Tạo khách hàng mới nếu chưa có

2. **Điểm không cập nhật:**
   - Kiểm tra hóa đơn có tổng tiền > 0
   - Đảm bảo đã chọn khách hàng

3. **Lỗi thanh toán:**
   - Kiểm tra kết nối database
   - Đảm bảo thông tin khách hàng hợp lệ

### Liên Hệ Hỗ Trợ
Nếu gặp vấn đề, vui lòng liên hệ team phát triển để được hỗ trợ. 