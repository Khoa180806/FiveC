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
            // DEBUG: In ra danh sách tên cột trong ResultSet
            java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            System.out.print("[DEBUG] Các cột trong ResultSet: ");
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + " | ");
            }
            System.out.println();
            B bean = beanClass.getDeclaredConstructor().newInstance();
            Method[] methods = beanClass.getMethods();
            
            for(Method method: methods){
                String name = method.getName();
                if (name.startsWith("set") && method.getParameterCount() == 1) {
                    String fieldName = name.substring(3);
                    // Chuẩn hóa fieldName về camelCase (ví dụ: Is_available -> is_available)
                    fieldName = fieldName.substring(0,1).toLowerCase() + fieldName.substring(1);

                    boolean success = false;
                    Object value = null;

                    Class<?> paramType = method.getParameterTypes()[0];
                    // Cách 1: Thử lấy theo đúng tên thuộc tính (alias SQL)
                    try {
                        value = resultSet.getObject(fieldName);
                        if (value != null) {
                            // Nếu kiểu setter là int và value là BigDecimal thì convert
                            if (paramType == int.class && value instanceof java.math.BigDecimal) {
                                value = ((java.math.BigDecimal) value).intValue();
                            }
                            method.invoke(bean, value);
                            System.out.printf("SUCCESS (by fieldName): Set %s = %s\r\n", fieldName, value);
                            success = true;
                        }
                    } catch (Exception e) {
                        // Ignore
                    }

                    // Cách 2: Thử với tên cột UPPERCASE
                    if (!success) {
                        try {
                            String columnName = fieldName.toUpperCase();
                            value = resultSet.getObject(columnName);
                            if (value != null) {
                                if (paramType == int.class && value instanceof java.math.BigDecimal) {
                                    value = ((java.math.BigDecimal) value).intValue();
                                }
                                method.invoke(bean, value);
                                System.out.printf("SUCCESS (by uppercase): Set %s = %s\r\n", fieldName, value);
                                success = true;
                            }
                        } catch (Exception e) {
                            // Ignore
                        }
                    }

                    // Cách 3: Thử với tên cột lowercase
                    if (!success) {
                        try {
                            String columnName = fieldName.toLowerCase();
                            value = resultSet.getObject(columnName);
                            if (value != null) {
                                if (paramType == int.class && value instanceof java.math.BigDecimal) {
                                    value = ((java.math.BigDecimal) value).intValue();
                                }
                                method.invoke(bean, value);
                                System.out.printf("SUCCESS (by lowercase): Set %s = %s\r\n", fieldName, value);
                                success = true;
                            }
                        } catch (Exception e) {
                            // Ignore
                        }
                    }

                    // Cách 4: Thử theo index cột (nếu có mapping)
                    if (!success) {
                        try {
                            int columnIndex = getColumnIndex(fieldName);
                            if (columnIndex > 0) {
                                value = resultSet.getObject(columnIndex);
                                if (paramType == int.class && value instanceof java.math.BigDecimal) {
                                    value = ((java.math.BigDecimal) value).intValue();
                                }
                                method.invoke(bean, value);
                                System.out.printf("SUCCESS (by index %d): Set %s = %s\r\n", columnIndex, fieldName, value);
                                success = true;
                            }
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
     * Lấy index cột dựa vào tên field - ĐÃ THÊM TABLE_FOR_CUSTOMER
     */
    private static int getColumnIndex(String fieldName) {
        switch (fieldName) {
            // USER_ACCOUNT mapping
            case "User_id": return 1;
            case "Username": return 2;
            case "Pass": return 3;
            case "FullName": return 4;
            case "Gender": return 5;
            case "Email": return 6;
            case "Phone_number": return 7;
            case "Image": return 8;
            case "Is_enabled": return 9;
            case "Created_date": return 10;
            case "Role_id": return 11;
            
            // TABLE_FOR_CUSTOMER mapping - THÊM VÀO
            case "Table_number": return 1;
            case "Amount": return 2;
            case "Status": return 3;
            
            // CATE mapping
            case "Category_id": return 1;
            case "Category_name": return 2;
            case "Is_available": return 3; // mapping cho Category
            case "is_available": return 3; // mapping cho Category
            
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
            
            // Xử lý Gender, Is_enabled, Amount, Status, Table_number (BigDecimal -> Integer)
            if ((fieldName.equals("Gender") || fieldName.equals("Is_enabled") || 
                 fieldName.equals("Amount") || fieldName.equals("Status") || 
                 fieldName.equals("Table_number")) && value instanceof java.math.BigDecimal) {
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