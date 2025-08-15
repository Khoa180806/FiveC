# ⚡ QUICK TEST CHECKLIST - TAB BÁO CÁO

## 🚀 **KIỂM THỬ NHANH (5 PHÚT)**

### ✅ **BƯỚC 1: KIỂM TRA GIAO DIỆN (1 phút)**
- [ ] **Mở tab "BÁO CÁO"**
- [ ] **Kiểm tra 6 buttons date presets hiển thị đúng:**
  - [ ] "Hôm nay" (xanh lá)
  - [ ] "Tuần này" (vàng) 
  - [ ] "Tháng này" (đỏ mì cay)
  - [ ] "Quý này" (xanh dương)
  - [ ] "Năm nay" (đỏ)
  - [ ] "Tùy chỉnh" (be)
- [ ] **Kiểm tra text "Tháng này" hiển thị đầy đủ** (không bị cắt)
- [ ] **Kiểm tra date chooser "Từ ngày" và "Đến ngày" hiển thị**
- [ ] **Kiểm tra các checkboxes nội dung báo cáo**
- [ ] **Kiểm tra radio buttons PDF/Excel**
- [ ] **Kiểm tra field email**

### ✅ **BƯỚC 2: KIỂM TRA DATE PRESETS (2 phút)**
- [ ] **Click "Hôm nay"** → Kiểm tra date được set đúng ngày hôm nay
- [ ] **Click "Tuần này"** → Kiểm tra date range từ đầu tuần đến cuối tuần
- [ ] **Click "Tháng này"** → Kiểm tra date range từ ngày 1 đến cuối tháng
- [ ] **Click "Quý này"** → Kiểm tra date range từ đầu quý đến cuối quý
- [ ] **Click "Năm nay"** → Kiểm tra date range từ 01/01 đến 31/12
- [ ] **Click "Tùy chỉnh"** → Kiểm tra date chooser hiển thị và có thể thay đổi ngày

### ✅ **BƯỚC 3: KIỂM TRA PROGRESS BAR (1 phút)**
- [ ] **Click bất kỳ button date preset** → Kiểm tra progress bar hiển thị "Đang tải dữ liệu..."
- [ ] **Đợi 1-2 giây** → Kiểm tra progress bar tự động ẩn
- [ ] **Kiểm tra progress bar nhỏ gọn** (không đẩy component khác lên)

### ✅ **BƯỚC 4: KIỂM TRA XUẤT BÁO CÁO (1 phút)**
- [ ] **Chọn ít nhất 1 checkbox** (VD: "Doanh thu tổng quát")
- [ ] **Chọn PDF** → Click "Xuất báo cáo"
- [ ] **Kiểm tra thông báo thành công** và file PDF được tạo
- [ ] **Chọn Excel** → Click "Xuất báo cáo" 
- [ ] **Kiểm tra thông báo thành công** và file Excel được tạo

## 🎯 **KẾT QUẢ NHANH**

### ✅ **NẾU TẤT CẢ PASS:**
- **Tab báo cáo hoạt động tốt!** 🎉
- **Có thể sử dụng cho production**

### ⚠️ **NẾU CÓ LỖI:**
- **Ghi lại lỗi cụ thể**
- **Screenshot lỗi**
- **Báo cáo lại để fix**

## 📋 **DANH SÁCH LỖI THƯỜNG GẶP**

### ❌ **Lỗi giao diện:**
- Button "Tháng này" bị cắt text
- Progress bar quá to
- Layout bị vỡ khi resize

### ❌ **Lỗi chức năng:**
- Date presets không set đúng ngày
- Progress bar không hiển thị/ẩn
- Xuất báo cáo bị lỗi
- Email validation không hoạt động

### ❌ **Lỗi performance:**
- Loading chậm
- UI bị lag khi click buttons
- Memory leak

## 🔧 **CÁCH FIX NHANH**

### 🎨 **Lỗi giao diện:**
- Tăng button width nếu text bị cắt
- Giảm progress bar height nếu quá to
- Check layout constraints

### ⚡ **Lỗi chức năng:**
- Check database connection
- Verify TimeRange methods
- Test with sample data

### 🚀 **Lỗi performance:**
- Optimize database queries
- Reduce UI updates
- Add caching if needed

---

**⚡ Checklist này giúp bạn test nhanh tab báo cáo trong 5 phút!**
