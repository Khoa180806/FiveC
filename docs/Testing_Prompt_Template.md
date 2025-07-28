# Testing Prompt Template

## 🎯 **PROMPT ĐỂ THỰC HIỆN TEST CASES THEO TRÌNH TỰ**

### **Cấu trúc prompt cơ bản:**

```
Hãy thực hiện test cases cho [TÊN_COMPONENT] theo trình tự sau:

1. **PHÂN TÍCH COMPONENT:**
   - Mô tả chức năng chính của component
   - Liệt kê các tính năng cần test
   - Xác định các validation rules

2. **THỰC HIỆN TEST CASES THEO TRÌNH TỰ:**
   - Test Case 1: Khởi tạo component
   - Test Case 2: Chức năng Create/Add
   - Test Case 3: Validation dữ liệu (tất cả rules)
   - Test Case 4: Trùng lặp dữ liệu
   - Test Case 5: Chức năng Read/Display
   - Test Case 6: Chức năng Update/Edit
   - Test Case 7: Chức năng Delete (nếu có)
   - Test Case 8: Tìm kiếm và Filter
   - Test Case 9: Xử lý file/ảnh (nếu có)
   - Test Case 10: Performance và UI/UX
   - Test Case 11: Error handling

3. **CHO MỖI TEST CASE:**
   - Mô tả chi tiết các bước test
   - Input data cụ thể
   - Expected result
   - Actual result
   - Lỗi phát hiện (nếu có)
   - Cách fix (nếu có)

4. **TỔNG KẾT:**
   - Số lượng test cases đã thực hiện
   - Số test cases passed/failed
   - Các lỗi đã fix
   - Các tối ưu đã thực hiện
   - Recommendations

5. **TẠO FILE DOCUMENTATION:**
   - File test cases chi tiết
   - File debug log
   - File tối ưu code (nếu cần)
```

---

## 📝 **PROMPT CHI TIẾT CHO TỪNG LOẠI COMPONENT:**

### **A. CHO COMPONENT CRUD (Create, Read, Update, Delete):**

```
Hãy thực hiện comprehensive testing cho [TÊN_COMPONENT] - một component quản lý [LOẠI_DỮ_LIỆU]:

**YÊU CẦU TEST:**

1. **INITIALIZATION TEST:**
   - Kiểm tra component khởi tạo đúng cách
   - Load dữ liệu ban đầu
   - Setup các event listeners
   - Initialize cache (nếu có)

2. **CREATE FUNCTION TEST:**
   - Test thêm mới [LOẠI_DỮ_LIỆU]
   - Validation tất cả fields bắt buộc
   - Test các trường hợp edge cases
   - Kiểm tra duplicate prevention

3. **VALIDATION COMPREHENSIVE TEST:**
   - Test tất cả validation rules
   - Test format validation
   - Test length validation
   - Test business logic validation
   - Test required field validation

4. **DUPLICATE HANDLING TEST:**
   - Test trùng lặp primary key
   - Test trùng lặp unique fields
   - Test case sensitivity
   - Test whitespace handling

5. **READ/DISPLAY TEST:**
   - Test hiển thị danh sách
   - Test pagination (nếu có)
   - Test sorting (nếu có)
   - Test data formatting

6. **UPDATE FUNCTION TEST:**
   - Test cập nhật thông tin
   - Test validation khi update
   - Test conflict resolution
   - Test partial update

7. **DELETE FUNCTION TEST:**
   - Test xóa item
   - Test confirmation dialog
   - Test cascade delete (nếu có)
   - Test permission check

8. **SEARCH & FILTER TEST:**
   - Test text search
   - Test advanced search
   - Test filter by category/status
   - Test search performance

9. **FILE/IMAGE HANDLING TEST:**
   - Test upload file
   - Test file validation
   - Test image processing
   - Test file storage

10. **PERFORMANCE TEST:**
    - Test với large dataset
    - Test memory usage
    - Test response time
    - Test cache efficiency

11. **ERROR HANDLING TEST:**
    - Test database connection error
    - Test file system error
    - Test validation error
    - Test network error

**DELIVERABLES:**
- File test cases chi tiết: `docs/Test_Cases_[TÊN_COMPONENT].md`
- File debug log: `docs/Debug_Log_[TÊN_COMPONENT].md`
- Optimized code (nếu cần)
- Performance improvements
```

### **B. CHO COMPONENT CÓ XỬ LÝ ẢNH/FILE:**

```
Hãy thực hiện testing cho [TÊN_COMPONENT] với focus vào xử lý ảnh/file:

**SPECIAL REQUIREMENTS:**

1. **IMAGE HANDLING TEST:**
   - Test upload ảnh hợp lệ (.jpg, .png, .gif)
   - Test upload ảnh không hợp lệ
   - Test ảnh quá lớn (>5MB)
   - Test ảnh bị corrupt
   - Test auto-resize functionality
   - Test image display trong UI
   - Test image caching

2. **FILE VALIDATION TEST:**
   - Test file extension validation
   - Test file size validation
   - Test file content validation
   - Test file path handling
   - Test file storage location

3. **UI IMAGE TEST:**
   - Test image loading performance
   - Test image display size control
   - Test image aspect ratio maintenance
   - Test fallback image handling
   - Test image click functionality

4. **ERROR SCENARIOS:**
   - Test file not found
   - Test permission denied
   - Test disk space full
   - Test invalid file format
   - Test corrupted image file
```

### **C. CHO COMPONENT CÓ SEARCH/FILTER:**

```
Hãy thực hiện testing cho [TÊN_COMPONENT] với focus vào search/filter:

**SEARCH & FILTER REQUIREMENTS:**

1. **TEXT SEARCH TEST:**
   - Test exact match search
   - Test partial match search
   - Test case insensitive search
   - Test special characters handling
   - Test empty search
   - Test search with spaces
   - Test search performance với large dataset

2. **FILTER TEST:**
   - Test filter by category
   - Test filter by status
   - Test filter by date range
   - Test multiple filters combination
   - Test filter reset functionality
   - Test filter persistence

3. **REAL-TIME SEARCH TEST:**
   - Test search while typing
   - Test search debouncing
   - Test search result highlighting
   - Test search result navigation

4. **ADVANCED SEARCH TEST:**
   - Test boolean operators (AND, OR)
   - Test wildcard search
   - Test search in multiple fields
   - Test search history
```

---

## 🔧 **PROMPT CHO DEBUGGING VÀ OPTIMIZATION:**

```
Sau khi thực hiện test cases, hãy:

1. **IDENTIFY ISSUES:**
   - Liệt kê tất cả lỗi phát hiện
   - Phân loại lỗi theo mức độ nghiêm trọng
   - Xác định root cause của từng lỗi

2. **FIX CRITICAL ISSUES:**
   - Sửa lỗi NullPointerException
   - Sửa lỗi validation
   - Sửa lỗi UI/UX
   - Sửa lỗi performance

3. **OPTIMIZE CODE:**
   - Tối ưu database queries
   - Implement caching strategy
   - Optimize memory usage
   - Improve code readability

4. **CREATE DOCUMENTATION:**
   - File test cases với kết quả
   - File debug log với solutions
   - File optimization report
   - File recommendations

5. **PERFORMANCE TESTING:**
   - Test với realistic data volume
   - Measure response times
   - Monitor memory usage
   - Identify bottlenecks
```

---

## 📋 **TEMPLATE CHO TEST CASE DOCUMENTATION:**

```
# Test Cases - [TÊN_COMPONENT]

## 📋 **Tổng quan Test Cases**

### **Mục tiêu kiểm tra:**
- [Liệt kê các chức năng chính]

---

## 🔍 **TEST CASE 1: Khởi tạo Component**

### **Mô tả:** [Mô tả test case]
### **Các bước test:**
1. [Bước 1]
2. [Bước 2]
3. [Bước 3]

### **Kết quả mong đợi:**
- ✅ [Expected result 1]
- ✅ [Expected result 2]

### **Lỗi phát hiện:**
- ❌ [Lỗi 1]
- ❌ [Lỗi 2]

### **Đã fix:**
- ✅ [Cách fix 1]
- ✅ [Cách fix 2]

---

## 📊 **Tổng kết Test Results**

### **Tổng số test cases:** [X]
### **Passed:** [X] ✅
### **Failed:** [X] ❌
### **Success Rate:** [X]%

### **Các lỗi đã fix:**
1. ✅ [Lỗi 1]
2. ✅ [Lỗi 2]

### **Các tối ưu đã thực hiện:**
1. ✅ [Tối ưu 1]
2. ✅ [Tối ưu 2]
```

---

## 🚀 **CÁCH SỬ DỤNG PROMPT:**

### **Bước 1: Chọn template phù hợp**
- CRUD component → Template A
- Image/File handling → Template B  
- Search/Filter → Template C

### **Bước 2: Điền thông tin cụ thể**
- Thay `[TÊN_COMPONENT]` bằng tên thực
- Thay `[LOẠI_DỮ_LIỆU]` bằng loại dữ liệu
- Thêm requirements đặc biệt

### **Bước 3: Thực hiện theo trình tự**
- Tuân thủ thứ tự test cases
- Ghi lại kết quả chi tiết
- Fix lỗi ngay khi phát hiện

### **Bước 4: Tạo documentation**
- File test cases
- File debug log
- File optimization report

### **Bước 5: Review và recommendations**
- Tổng kết kết quả
- Đề xuất cải tiến
- Plan cho future development 