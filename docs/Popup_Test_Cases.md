# Test Cases cho Chức Năng Popup Menu

## Test Case 1: Mở Popup Menu Quản Lý Danh Mục
**Mục tiêu**: Kiểm tra popup menu hiển thị khi click vào button "Quản Lý Danh Mục"

**Các bước thực hiện**:
1. Chạy ứng dụng MainUI
2. Click vào button "Quản Lý Danh Mục"
3. Quan sát popup menu xuất hiện

**Kết quả mong đợi**:
- ✅ Popup menu hiển thị với 2 menu items: "Loại món ăn" và "Sản phẩm"
- ✅ Menu có màu nền đỏ (134, 39, 43)
- ✅ Menu items có màu chữ trắng
- ✅ Menu có border trắng

## Test Case 2: Hover Effect trên Menu Items
**Mục tiêu**: Kiểm tra hiệu ứng hover khi di chuột qua menu items

**Các bước thực hiện**:
1. Mở popup menu
2. Di chuột qua menu item "Loại món ăn"
3. Di chuột qua menu item "Sản phẩm"

**Kết quả mong đợi**:
- ✅ Menu item thay đổi màu nền thành đỏ nhạt (180, 50, 50) khi hover
- ✅ Menu item trở về màu nền ban đầu khi không hover

## Test Case 3: Mở CategoryManagement từ Popup
**Mục tiêu**: Kiểm tra việc mở form CategoryManagement khi click vào "Loại món ăn"

**Các bước thực hiện**:
1. Mở popup menu
2. Click vào menu item "Loại món ăn"

**Kết quả mong đợi**:
- ✅ Form CategoryManagement mở thành công
- ✅ Console hiển thị log: "🔄 Đang mở CategoryManagement..."
- ✅ Console hiển thị log: "✅ CategoryManagement đã được mở thành công!"

## Test Case 4: Mở ProductManagement từ Popup
**Mục tiêu**: Kiểm tra việc mở form ProductManagement khi click vào "Sản phẩm"

**Các bước thực hiện**:
1. Mở popup menu
2. Click vào menu item "Sản phẩm"

**Kết quả mong đợi**:
- ✅ Form ProductManagement mở thành công
- ✅ Console hiển thị log: "🔄 Đang mở ProductManagement..."
- ✅ Console hiển thị log: "✅ ProductManagement đã được mở thành công!"

## Test Case 5: Error Handling
**Mục tiêu**: Kiểm tra xử lý lỗi khi không thể mở form

**Các bước thực hiện**:
1. Tạm thời đổi tên class CategoryManagement để gây lỗi
2. Click vào menu item "Loại món ăn"

**Kết quả mong đợi**:
- ✅ Console hiển thị log lỗi: "❌ Lỗi khi mở CategoryManagement: ..."
- ✅ Hiển thị dialog thông báo lỗi cho người dùng

## Test Case 6: Tooltip Functionality
**Mục tiêu**: Kiểm tra tooltip hiển thị khi hover

**Các bước thực hiện**:
1. Mở popup menu
2. Hover chuột qua menu items
3. Hover chuột qua button "Quản Lý Danh Mục"

**Kết quả mong đợi**:
- ✅ Tooltip hiển thị cho menu item "Loại món ăn": "Quản lý các loại món ăn trong hệ thống"
- ✅ Tooltip hiển thị cho menu item "Sản phẩm": "Quản lý các sản phẩm/món ăn trong hệ thống"
- ✅ Tooltip hiển thị cho button: "Click để mở menu quản lý danh mục"

## Test Case 7: Responsive Design
**Mục tiêu**: Kiểm tra menu tự động điều chỉnh kích thước

**Các bước thực hiện**:
1. Thay đổi kích thước cửa sổ MainUI
2. Mở popup menu

**Kết quả mong đợi**:
- ✅ Menu items tự động điều chỉnh width theo button
- ✅ Menu hiển thị đúng vị trí dưới button

## Test Case 8: Multiple Form Instances
**Mục tiêu**: Kiểm tra có thể mở nhiều form cùng lúc

**Các bước thực hiện**:
1. Mở CategoryManagement từ popup
2. Mở ProductManagement từ popup
3. Mở thêm CategoryManagement lần nữa

**Kết quả mong đợi**:
- ✅ Có thể mở nhiều form cùng lúc
- ✅ Mỗi form hoạt động độc lập
- ✅ Không có xung đột giữa các form

## Test Case 9: Keyboard Navigation
**Mục tiêu**: Kiểm tra điều hướng bằng bàn phím

**Các bước thực hiện**:
1. Mở popup menu
2. Sử dụng phím Tab để di chuyển giữa menu items
3. Sử dụng phím Enter để chọn menu item

**Kết quả mong đợi**:
- ✅ Có thể điều hướng bằng Tab
- ✅ Có thể chọn bằng Enter
- ✅ Focus hiển thị rõ ràng trên menu item được chọn

## Test Case 10: Accessibility
**Mục tiêu**: Kiểm tra tính tiếp cận của popup menu

**Các bước thực hiện**:
1. Sử dụng screen reader (nếu có)
2. Kiểm tra contrast ratio của text
3. Kiểm tra kích thước clickable area

**Kết quả mong đợi**:
- ✅ Text có contrast ratio đủ cao
- ✅ Menu items có kích thước đủ lớn để click
- ✅ Tooltip cung cấp thông tin mô tả đầy đủ 