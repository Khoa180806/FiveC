package com.team4.quanliquanmicay.util;

import com.team4.quanliquanmicay.Entity.UserAccount;
import java.lang.reflect.Method;
import java.sql.ResultSet;
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
     * Đọc một đối tượng từ ResultSet
     */
    private static <B> B readBean(ResultSet resultSet, Class<B> beanClass) throws SQLException {
        try {
            B bean = beanClass.getDeclaredConstructor().newInstance();
            Method[] methods = beanClass.getMethods();
            
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
                            
                            // XỬ LÝ ĐẶC BIỆT CHO CÁC TRƯỜNG
                            if (value != null) {
                                // Xử lý Created_date: Timestamp -> Date
                                if (fieldName.equals("Created_date") && value instanceof java.sql.Timestamp) {
                                    java.sql.Timestamp timestamp = (java.sql.Timestamp) value;
                                    value = new java.util.Date(timestamp.getTime());
                                    System.out.printf("DEBUG Converted %s: Timestamp -> java.util.Date\r\n", fieldName);
                                }
                                // Xử lý Gender và Is_enabled: BigDecimal -> Integer
                                else if ((fieldName.equals("Gender") || fieldName.equals("Is_enabled")) && value instanceof java.math.BigDecimal) {
                                    value = ((java.math.BigDecimal) value).intValue();
                                    System.out.printf("DEBUG Converted %s: BigDecimal -> %s\r\n", fieldName, value);
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
                    
                    // Cách 2: Thử với tên cột UPPERCASE (nếu index không thành công)
                    if (!success) {
                        try {
                            String columnName = fieldName.toUpperCase();
                            value = resultSet.getObject(columnName);
                            
                            // Xử lý conversion tương tự
                            if (value != null) {
                                if (fieldName.equals("Created_date") && value instanceof java.sql.Timestamp) {
                                    java.sql.Timestamp timestamp = (java.sql.Timestamp) value;
                                    value = new java.util.Date(timestamp.getTime());
                                }
                                else if ((fieldName.equals("Gender") || fieldName.equals("Is_enabled")) && value instanceof java.math.BigDecimal) {
                                    value = ((java.math.BigDecimal) value).intValue();
                                }
                            }
                            
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
        } catch (Exception e) {
            throw new SQLException("Error creating bean: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lấy index cột dựa vào tên field - đã được fix
     */
    private static int getColumnIndex(String fieldName) {
        switch (fieldName) {
            case "User_id": return 1;
            case "Username": return 2;
            case "Pass": return 3;
            case "FullName": return 4;
            case "Gender": return 5;
            case "Email": return 6;
            case "Phone_number": return 7;
            case "Image": return 8;
            case "Is_enabled": return 9;
            case "Created_date": return 10;  // Fix index này
            case "Role_id": return 11;
            default: return -1;
        }
    }
    
    /**
     * Convert và set giá trị cho field
     */
    private static void setFieldValue(Object bean, Method method, Object value, String fieldName) {
        try {
            if (value == null) {
                method.invoke(bean, (Object) null);
                return;
            }

            Class<?> paramType = method.getParameterTypes()[0];
            
            // XỬ LÝ ĐẶC BIỆT CHO CREATED_DATE
            if (fieldName.equals("Created_date") && value instanceof java.sql.Timestamp) {
                // Convert Timestamp sang java.util.Date
                java.sql.Timestamp timestamp = (java.sql.Timestamp) value;
                java.util.Date date = new java.util.Date(timestamp.getTime());
                method.invoke(bean, date);
                System.out.println("SUCCESS: Set " + fieldName + " = " + date);
                return;
            }
            
            // Xử lý Gender và Is_enabled (BigDecimal -> Integer)
            if ((fieldName.equals("Gender") || fieldName.equals("Is_enabled")) && value instanceof java.math.BigDecimal) {
                java.math.BigDecimal bd = (java.math.BigDecimal) value;
                Integer intValue = bd.intValue();
                System.out.println("DEBUG Converted " + fieldName + ": BigDecimal -> " + intValue);
                method.invoke(bean, intValue);
                System.out.println("SUCCESS (by index " + getColumnIndex(fieldName) + "): Set " + fieldName + " = " + intValue);
                return;
            }

            // Các trường hợp khác
            if (paramType.isAssignableFrom(value.getClass())) {
                method.invoke(bean, value);
                System.out.println("SUCCESS (by index " + getColumnIndex(fieldName) + "): Set " + fieldName + " = " + value);
            } else {
                System.out.println("ERROR: Type mismatch for " + fieldName + " - Expected: " + paramType + ", Got: " + value.getClass());
            }
            
        } catch (Exception e) {
            System.out.println("ERROR setting " + fieldName + ": " + e.getMessage());
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