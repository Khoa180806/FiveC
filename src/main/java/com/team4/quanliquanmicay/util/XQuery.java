package com.team4.quanliquanmicay.util;

import com.team4.quanliquanmicay.Entity.UserAccount;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp tiện ích hỗ trợ truy vấn và chuyển đổi sang đối tượng
 *
 * @author NghiemN
 * @version 1.0
 */
public class XQuery {

    /**
     * Truy vấn 1 đối tượng
     *
     * @param <B> kiểu của đối tượng cần chuyển đổi
     * @param beanClass lớp của đối tượng kết quả
     * @param sql câu lệnh truy vấn
     * @param values các giá trị cung cấp cho các tham số của SQL
     * @return kết quả truy vấn
     * @throws RuntimeException lỗi truy vấn
     */
    public static <B> B getSingleBean(Class<B> beanClass, String sql, Object... values) {
        List<B> list = XQuery.getBeanList(beanClass, sql, values);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * Truy vấn nhiều đối tượng
     *
     * @param <B> kiểu của đối tượng cần chuyển đổi
     * @param beanClass lớp của đối tượng kết quả
     * @param sql câu lệnh truy vấn
     * @param values các giá trị cung cấp cho các tham số của SQL
     * @return kết quả truy vấn
     * @throws RuntimeException lỗi truy vấn
     */
    public static <B> List<B> getBeanList(Class<B> beanClass, String sql, Object... values) {
        List<B> list = new ArrayList<>();
        try {
            ResultSet resultSet = XJdbc.executeQuery(sql, values);
            while (resultSet.next()) {
                list.add(XQuery.readBean(resultSet, beanClass));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    /**
     * Tạo bean với dữ liệu đọc từ bản ghi hiện tại
     *
     * @param <B> kiểu của đối tượng cần chuyển đổi
     * @param resultSet tập bản ghi cung cấp dữ liệu
     * @param beanClass lớp của đối tượng kết quả
     * @return kết quả truy vấn
     * @throws RuntimeException lỗi truy vấn
     */
    private static <B> B readBean(ResultSet resultSet, Class<B> beanClass) throws Exception {
        B bean = beanClass.getDeclaredConstructor().newInstance();
        Method[] methods = beanClass.getDeclaredMethods();
        
        for(Method method: methods){
            String name = method.getName();
            if (name.startsWith("set") && method.getParameterCount() == 1) {
                String fieldName = name.substring(3);
                
                boolean success = false;
                Object value = null;
                
                // Cách 1: Thử theo index cột
                try {
                    int columnIndex = getColumnIndex(fieldName);
                    if (columnIndex > 0) {
                        value = resultSet.getObject(columnIndex);
                        
                        // DEBUG: In ra giá trị thô từ database
                        System.out.printf("DEBUG Raw Value for %s (index %d): %s (type: %s)\r\n", 
                            fieldName, columnIndex, value, value != null ? value.getClass().getSimpleName() : "null");
                        
                        // Xử lý conversion cho Oracle NUMBER -> Java Integer
                        if (value != null && (fieldName.equals("Gender") || fieldName.equals("Is_enabled"))) {
                            if (value instanceof java.math.BigDecimal) {
                                value = ((java.math.BigDecimal) value).intValue();
                                System.out.printf("DEBUG Converted %s: %s -> %s\r\n", fieldName, "BigDecimal", value);
                            } else if (value instanceof Number) {
                                value = ((Number) value).intValue();
                                System.out.printf("DEBUG Converted %s: %s -> %s\r\n", fieldName, "Number", value);
                            }
                        }
                        
                        method.invoke(bean, value);
                        System.out.printf("SUCCESS (by index %d): Set %s = %s\r\n", columnIndex, fieldName, value);
                        success = true;
                    }
                } catch (Exception e) {
                    System.out.printf("ERROR at index %d for %s: %s\r\n", getColumnIndex(fieldName), fieldName, e.getMessage());
                    // Continue to try other methods
                }
                
                // Cách 2: Thử với tên cột UPPERCASE
                if (!success) {
                    try {
                        String columnName = fieldName.toUpperCase();
                        value = resultSet.getObject(columnName);
                        method.invoke(bean, value);
                        System.out.printf("SUCCESS (by name): Set %s = %s\r\n", fieldName, value);
                        success = true;
                    } catch (Exception e) {
                        // Ignore
                    }
                }
                
                if (!success) {
                    System.out.printf("+ Column '%s' not found!\r\n", fieldName);
                }
            }
        }
        return bean;
    }
    
    /**
     * Lấy index cột dựa vào tên field
     * Từ debug output, ta thấy các cột thành công:
     * Index 1: User_id, Index 2: Username, Index 3: Pass, Index 4: FullName
     * Index 6: Email, Index 7: Phone_number, Index 8: Image
     * Index 10: Created_date, Index 11: Role_id
     * 
     * Vậy Gender = Index 5, Is_enabled = Index 9
     */
    private static int getColumnIndex(String fieldName) {
        switch (fieldName) {
            case "User_id": return 1;       // ✓ SUCCESS
            case "Username": return 2;      // ✓ SUCCESS  
            case "Pass": return 3;          // ✓ SUCCESS
            case "FullName": return 4;      // ✓ SUCCESS
            case "Gender": return 5;        // ❌ Column not found - PROBLEM HERE
            case "Email": return 6;         // ✓ SUCCESS
            case "Phone_number": return 7;  // ✓ SUCCESS
            case "Image": return 8;         // ✓ SUCCESS
            case "Is_enabled": return 9;    // ❌ Column not found - PROBLEM HERE
            case "Created_date": return 10; // ✓ SUCCESS
            case "Role_id": return 11;      // ✓ SUCCESS
            default: return -1;
        }
    }
    
    public static void main(String[] args) {
        demo1();
        demo2();
    }

    private static void demo1() {
        String sql = "SELECT * FROM USER_ACCOUNT WHERE username=? AND pass=?";
        UserAccount user = XQuery.getSingleBean(UserAccount.class, sql, "NghiemN", "123456");
        System.out.println(user);
    }

    private static void demo2() {
        String sql = "SELECT * FROM USER_ACCOUNT WHERE fullName LIKE ?";
        List<UserAccount> list = XQuery.getBeanList(UserAccount.class, sql, "%Nguyễn %");
        System.out.println(list);
    }
}