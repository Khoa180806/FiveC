# 🧪 HƯỚNG DẪN KIỂM THỬ TAB BÁO CÁO

## 📋 **DANH SÁCH KIỂM THỬ**

### 🕐 **1. KIỂM THỬ QUICK DATE PRESETS**

#### ✅ **Test Case 1.1: Button "Hôm nay"**
- **Mục tiêu:** Kiểm tra button "Hôm nay" hoạt động đúng
- **Bước thực hiện:**
  1. Mở tab "BÁO CÁO"
  2. Click button "Hôm nay" (màu xanh lá)
  3. Kiểm tra date chooser "Từ ngày" và "Đến ngày" đã được set đúng ngày hôm nay
  4. Kiểm tra progress bar hiển thị "Đang tải dữ liệu..."
  5. Kiểm tra preview được refresh với dữ liệu ngày hôm nay
- **Kết quả mong đợi:** ✅ Date được set đúng, preview refresh thành công

#### ✅ **Test Case 1.2: Button "Tuần này"**
- **Mục tiêu:** Kiểm tra button "Tuần này" hoạt động đúng
- **Bước thực hiện:**
  1. Click button "Tuần này" (màu vàng)
  2. Kiểm tra date range từ đầu tuần đến cuối tuần hiện tại
  3. Kiểm tra preview refresh với dữ liệu tuần này
- **Kết quả mong đợi:** ✅ Date range đúng tuần hiện tại

#### ✅ **Test Case 1.3: Button "Tháng này"**
- **Mục tiêu:** Kiểm tra button "Tháng này" hoạt động đúng
- **Bước thực hiện:**
  1. Click button "Tháng này" (màu đỏ mì cay)
  2. Kiểm tra date range từ ngày 1 đến cuối tháng hiện tại
  3. Kiểm tra preview refresh với dữ liệu tháng này
- **Kết quả mong đợi:** ✅ Date range đúng tháng hiện tại

#### ✅ **Test Case 1.4: Button "Quý này"**
- **Mục tiêu:** Kiểm tra button "Quý này" hoạt động đúng
- **Bước thực hiện:**
  1. Click button "Quý này" (màu xanh dương)
  2. Kiểm tra date range từ đầu quý đến cuối quý hiện tại
  3. Kiểm tra preview refresh với dữ liệu quý này
- **Kết quả mong đợi:** ✅ Date range đúng quý hiện tại

#### ✅ **Test Case 1.5: Button "Năm nay"**
- **Mục tiêu:** Kiểm tra button "Năm nay" hoạt động đúng
- **Bước thực hiện:**
  1. Click button "Năm nay" (màu đỏ)
  2. Kiểm tra date range từ 01/01 đến 31/12 năm hiện tại
  3. Kiểm tra preview refresh với dữ liệu năm này
- **Kết quả mong đợi:** ✅ Date range đúng năm hiện tại

#### ✅ **Test Case 1.6: Button "Tùy chỉnh"**
- **Mục tiêu:** Kiểm tra button "Tùy chỉnh" hiển thị date chooser
- **Bước thực hiện:**
  1. Click button "Tùy chỉnh" (màu be)
  2. Kiểm tra date chooser "Từ ngày" và "Đến ngày" hiển thị
  3. Thay đổi ngày trong date chooser
  4. Kiểm tra preview refresh với ngày mới
- **Kết quả mong đợi:** ✅ Date chooser hiển thị, preview refresh thành công

### 📊 **2. KIỂM THỬ NỘI DUNG BÁO CÁO**

#### ✅ **Test Case 2.1: Checkbox "Doanh thu tổng quát"**
- **Mục tiêu:** Kiểm tra checkbox hoạt động đúng
- **Bước thực hiện:**
  1. Bỏ tích checkbox "Doanh thu tổng quát"
  2. Click "Xuất báo cáo"
  3. Kiểm tra thông báo lỗi yêu cầu chọn ít nhất 1 loại báo cáo
  4. Tích lại checkbox
  5. Click "Xuất báo cáo" lại
- **Kết quả mong đợi:** ✅ Validation hoạt động, export thành công khi có ít nhất 1 checkbox

#### ✅ **Test Case 2.2: Tất cả checkboxes**
- **Mục tiêu:** Kiểm tra tất cả checkboxes hoạt động
- **Bước thực hiện:**
  1. Tích/bỏ tích từng checkbox
  2. Kiểm tra trạng thái được lưu đúng
  3. Click "Xuất báo cáo" với các combination khác nhau
- **Kết quả mong đợi:** ✅ Tất cả checkboxes hoạt động đúng

### 📄 **3. KIỂM THỬ ĐỊNH DẠNG XUẤT**

#### ✅ **Test Case 3.1: Radio button PDF**
- **Mục tiêu:** Kiểm tra xuất PDF
- **Bước thực hiện:**
  1. Chọn radio button "PDF"
  2. Click "Xuất báo cáo"
  3. Kiểm tra file PDF được tạo
  4. Kiểm tra thông báo thành công
- **Kết quả mong đợi:** ✅ PDF được tạo và thông báo thành công

#### ✅ **Test Case 3.2: Radio button Excel**
- **Mục tiêu:** Kiểm tra xuất Excel
- **Bước thực hiện:**
  1. Chọn radio button "Excel"
  2. Click "Xuất báo cáo"
  3. Kiểm tra file Excel được tạo
  4. Kiểm tra thông báo thành công
- **Kết quả mong đợi:** ✅ Excel được tạo và thông báo thành công

#### ✅ **Test Case 3.3: Checkbox "Gộp nhiều thống kê"**
- **Mục tiêu:** Kiểm tra gộp file
- **Bước thực hiện:**
  1. Tích checkbox "Gộp nhiều thống kê vào 1 file"
  2. Chọn nhiều loại báo cáo
  3. Click "Xuất báo cáo"
  4. Kiểm tra file được gộp đúng
- **Kết quả mong đợi:** ✅ File được gộp thành công

### 📧 **4. KIỂM THỬ GỬI EMAIL**

#### ✅ **Test Case 4.1: Email hợp lệ**
- **Mục tiêu:** Kiểm tra gửi email với email hợp lệ
- **Bước thực hiện:**
  1. Nhập email hợp lệ (VD: test@example.com)
  2. Xuất báo cáo trước
  3. Click "Gửi email báo cáo"
  4. Kiểm tra thông báo thành công
- **Kết quả mong đợi:** ✅ Email được gửi thành công

#### ✅ **Test Case 4.2: Email không hợp lệ**
- **Mục tiêu:** Kiểm tra validation email
- **Bước thực hiện:**
  1. Nhập email không hợp lệ (VD: invalid-email)
  2. Click "Gửi email báo cáo"
  3. Kiểm tra thông báo lỗi
- **Kết quả mong đợi:** ✅ Hiển thị thông báo lỗi email không hợp lệ

#### ✅ **Test Case 4.3: Email trống**
- **Mục tiêu:** Kiểm tra email trống
- **Bước thực hiện:**
  1. Để trống field email
  2. Click "Gửi email báo cáo"
  3. Kiểm tra thông báo lỗi
- **Kết quả mong đợi:** ✅ Hiển thị thông báo lỗi email trống

### ⚡ **5. KIỂM THỬ PROGRESS BAR**

#### ✅ **Test Case 5.1: Progress bar khi tải dữ liệu**
- **Mục tiêu:** Kiểm tra progress bar hiển thị khi tải dữ liệu
- **Bước thực hiện:**
  1. Click bất kỳ button date preset
  2. Kiểm tra progress bar hiển thị "Đang tải dữ liệu..."
  3. Kiểm tra progress bar tự động ẩn khi hoàn thành
- **Kết quả mong đợi:** ✅ Progress bar hiển thị và ẩn đúng

#### ✅ **Test Case 5.2: Progress bar khi xuất báo cáo**
- **Mục tiêu:** Kiểm tra progress bar khi xuất báo cáo
- **Bước thực hiện:**
  1. Click "Xuất báo cáo"
  2. Kiểm tra progress bar hiển thị "Đang xuất báo cáo..."
  3. Kiểm tra progress bar tự động ẩn khi hoàn thành
- **Kết quả mong đợi:** ✅ Progress bar hiển thị và ẩn đúng

### 🎨 **6. KIỂM THỬ GIAO DIỆN**

#### ✅ **Test Case 6.1: Layout responsive**
- **Mục tiêu:** Kiểm tra layout không bị vỡ
- **Bước thực hiện:**
  1. Thay đổi kích thước cửa sổ
  2. Kiểm tra các component không bị overlap
  3. Kiểm tra text không bị cắt
- **Kết quả mong đợi:** ✅ Layout responsive tốt

#### ✅ **Test Case 6.2: Button sizes**
- **Mục tiêu:** Kiểm tra button sizes phù hợp
- **Bước thực hiện:**
  1. Kiểm tra tất cả buttons có kích thước 85x25px
  2. Kiểm tra text "Tháng này" hiển thị đầy đủ
  3. Kiểm tra font size 11px dễ đọc
- **Kết quả mong đợi:** ✅ Button sizes và text hiển thị đúng

### 🛡️ **7. KIỂM THỬ ERROR HANDLING**

#### ✅ **Test Case 7.1: Database error**
- **Mục tiêu:** Kiểm tra xử lý lỗi database
- **Bước thực hiện:**
  1. Ngắt kết nối database
  2. Click "Xuất báo cáo"
  3. Kiểm tra thông báo lỗi database
- **Kết quả mong đợi:** ✅ Hiển thị thông báo lỗi database

#### ✅ **Test Case 7.2: File permission error**
- **Mục tiêu:** Kiểm tra xử lý lỗi quyền ghi file
- **Bước thực hiện:**
  1. Thay đổi quyền thư mục xuất file
  2. Click "Xuất báo cáo"
  3. Kiểm tra thông báo lỗi quyền ghi
- **Kết quả mong đợi:** ✅ Hiển thị thông báo lỗi quyền ghi

## 📊 **BẢNG TỔNG HỢP KẾT QUẢ**

| Test Category | Total Cases | Passed | Failed | Success Rate |
|---------------|-------------|--------|--------|--------------|
| Quick Date Presets | 6 | - | - | - |
| Report Content | 2 | - | - | - |
| Export Format | 3 | - | - | - |
| Email Function | 3 | - | - | - |
| Progress Bar | 2 | - | - | - |
| UI/UX | 2 | - | - | - |
| Error Handling | 2 | - | - | - |
| **TOTAL** | **20** | **-** | **-** | **-** |

## 🎯 **HƯỚNG DẪN THỰC HIỆN**

### 📝 **Cách ghi kết quả:**
- ✅ **PASS:** Test case hoạt động đúng như mong đợi
- ❌ **FAIL:** Test case không hoạt động hoặc có lỗi
- ⚠️ **PARTIAL:** Test case hoạt động một phần

### 🔍 **Lưu ý khi test:**
1. **Test từng case một** - Không test nhiều case cùng lúc
2. **Ghi lại lỗi chi tiết** - Screenshot và mô tả lỗi
3. **Test trên nhiều môi trường** - Windows, Mac nếu có thể
4. **Test với dữ liệu thực** - Đảm bảo có dữ liệu trong database

### 📋 **Checklist trước khi test:**
- [ ] Database có dữ liệu
- [ ] Kết nối mạng ổn định (cho email)
- [ ] Quyền ghi file trong thư mục xuất
- [ ] Java environment đã setup đúng

---

**🎉 Chúc bạn kiểm thử thành công! Tab báo cáo đã được tối ưu và sẵn sàng cho việc test!**
