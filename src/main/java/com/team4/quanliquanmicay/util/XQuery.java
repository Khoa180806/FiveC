package com.team4.quanliquanmicay.util;

import com.team4.quanliquanmicay.Entity.UserAccount;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
                
                // Thử nhiều cách để tìm cột
                boolean success = false;
                Object value = null;
                
                // Cách 1: Thử theo index cột (dựa vào thứ tự trong SQL)
                try {
                    int columnIndex = getColumnIndex(fieldName);
                    if (columnIndex > 0) {
                        value = resultSet.getObject(columnIndex);
                        method.invoke(bean, value);
                        System.out.printf("SUCCESS (by index %d): Set %s = %s\r\n", columnIndex, fieldName, value);
                        success = true;
                    }
                } catch (Exception e) {
                    // Ignore và thử cách khác
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
                
                // Cách 3: Thử với tên gốc
                if (!success) {
                    try {
                        value = resultSet.getObject(fieldName);
                        method.invoke(bean, value);
                        System.out.printf("SUCCESS (original): Set %s = %s\r\n", fieldName, value);
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
     * Dựa vào kết quả Oracle trước:
     * Column 1: USER_ID, Column 2: USERNAME, Column 3: PASS, Column 4: FULLNAME, 
     * Column 5: GENDER, Column 6: EMAIL, Column 7: PHONE_NUMBER, Column 8: IMAGE, 
     * Column 9: IS_ENABLED, Column 10: CREATED_DATE, Column 11: ROLE_ID
     */
    private static int getColumnIndex(String fieldName) {
        switch (fieldName) {
            case "User_id": return 1;       // USER_ID
            case "Username": return 2;      // USERNAME  
            case "Pass": return 3;          // PASS
            case "FullName": return 4;      // FULLNAME
            case "Gender": return 5;        // GENDER ← Đây là vị trí đúng
            case "Email": return 6;         // EMAIL
            case "Phone_number": return 7;  // PHONE_NUMBER
            case "Image": return 8;         // IMAGE
            case "Is_enabled": return 9;    // IS_ENABLED ← Đây là vị trí đúng
            case "Created_date": return 10; // CREATED_DATE
            case "Role_id": return 11;      // ROLE_ID
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