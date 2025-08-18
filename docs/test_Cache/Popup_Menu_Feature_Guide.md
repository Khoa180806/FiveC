# Hướng Dẫn Sử Dụng Chức Năng Popup Menu

## Tổng Quan
Hệ thống Five C đã được tích hợp chức năng popup menu cho quản lý danh mục, cho phép người dùng truy cập nhanh vào các chức năng quản lý khác nhau.

## Chức Năng Popup Menu Quản Lý Danh Mục

### Cách Sử Dụng
1. **Mở Popup Menu**: Click vào button "Quản Lý Danh Mục" trên giao diện chính
2. **Chọn Chức Năng**: Menu popup sẽ hiển thị với 2 tùy chọn:
   - **Loại món ăn**: Mở form CategoryManagement
   - **Sản phẩm**: Mở form ProductManagement

### Tính Năng
- ✅ **Hover Effect**: Menu items thay đổi màu khi hover
- ✅ **Tooltip**: Hiển thị thông tin mô tả khi hover
- ✅ **Error Handling**: Xử lý lỗi khi mở form
- ✅ **Responsive Design**: Menu tự động điều chỉnh kích thước theo button

### Cấu Trúc Code
```java
// Trong MainUI.java
private void setupCategoryManagementPopup() {
    JPopupMenu popupQuanLyDanhMuc = new JPopupMenu();
    JMenuItem itemLoaiMon = new JMenuItem("Loại món ăn");
    JMenuItem itemSanPham = new JMenuItem("Sản phẩm");
    
    // CSS styling
    // Action listeners
    // Mouse listeners
}
```

### Các Form Được Mở
1. **CategoryManagement.java**
   - Quản lý các loại món ăn
   - Thêm, sửa, xóa loại món
   - Quản lý trạng thái hoạt động

2. **ProductManagement.java**
   - Quản lý các sản phẩm/món ăn
   - Thêm, sửa, xóa sản phẩm
   - Quản lý giá, giảm giá, đơn vị
   - Upload và quản lý ảnh sản phẩm

## Lưu Ý Kỹ Thuật
- Popup menu được tạo bằng `JPopupMenu`
- Sử dụng `MouseListener` để xử lý sự kiện click
- Có xử lý exception để tránh crash ứng dụng
- Menu được style với màu sắc phù hợp với theme của ứng dụng

## Troubleshooting
- Nếu popup không hiển thị: Kiểm tra xem button có được khởi tạo đúng không
- Nếu form không mở: Kiểm tra console để xem lỗi chi tiết
- Nếu menu bị lỗi layout: Kiểm tra kích thước button và menu items 