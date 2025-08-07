package com.team4.quanliquanmicay.util;

import com.team4.quanliquanmicay.Entity.UserAccount;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp tiện ích hỗ trợ truy vấn và chuyển đổi sang đối tượng (Tối ưu cho Oracle)
 *
 * @author NghiemN
 * @version 1.1 (Oracle)
 */
public class XQuery {

    /**
     * Truy vấn 1 đối tượng
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
     */
    public static <B> List<B> getBeanList(Class<B> beanClass, String sql, Object... values) {
        return XJdbc.executeQuery(sql, rs -> {
            List<B> list = new ArrayList<>();
            try {
                while (rs.next()) {
                    list.add(XQuery.readBean(rs, beanClass));
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            return list;
        }, values);
    }

    /**
     * Đọc một đối tượng từ ResultSet (Tối ưu cho Oracle)
     */
    private static <B> B readBean(ResultSet resultSet, Class<B> beanClass) throws SQLException {
        try {
            B bean = beanClass.getDeclaredConstructor().newInstance();
            Method[] methods = beanClass.getMethods();
            for (Method method : methods) {
                String name = method.getName();
                if (name.startsWith("set") && method.getParameterCount() == 1) {
                    String fieldName = name.substring(3);
                    fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
                    boolean success = false;
                    Object value = null;
                    Class<?> paramType = method.getParameterTypes()[0];
                    // DEBUG: In ra tên setter và fieldName
                    // System.out.println("[DEBUG] Setter: " + name + " | fieldName: " + fieldName);
                    // Cách 1: Thử lấy theo đúng tên thuộc tính (alias SQL)
                    try {
                        // System.out.println("[DEBUG] Trying to get column: " + fieldName.toUpperCase());
                        value = resultSet.getObject(fieldName.toUpperCase());
                        if (value != null) {
                            // Nếu kiểu setter là int hoặc Integer và value là BigDecimal thì convert
                            if ((paramType == int.class || paramType == Integer.class) && value instanceof java.math.BigDecimal) {
                                // System.out.println("[DEBUG] Converting BigDecimal to Integer in readBean: " + value + " for field: " + fieldName);
                                value = ((java.math.BigDecimal) value).intValue();
                            }
                            if (paramType == double.class && value instanceof java.math.BigDecimal) {
                                value = ((java.math.BigDecimal) value).doubleValue();
                            }
                            if (paramType == boolean.class || paramType == Boolean.class) {
                                if (value instanceof Number) {
                                    value = ((Number) value).intValue() == 1;
                                } else if (value instanceof String) {
                                    value = "1".equals(value) || "true".equalsIgnoreCase((String) value);
                                }
                            }
                            // Xử lý chuyển đổi Date
                            if (paramType == java.util.Date.class) {
                                value = convertOracleType(paramType, value, fieldName);
                            }
                            method.invoke(bean, value);
                            success = true;
                        }
                    } catch (Exception e) {
                        // Ignore
                    }

                    // Cách 2: Thử lấy theo đúng tên thuộc tính (alias SQL)
                    if (!success) {
                        try {
                            // System.out.println("[DEBUG] Trying to get column: " + fieldName);
                            value = resultSet.getObject(fieldName);
                            if (value != null) {
                                if ((paramType == int.class || paramType == Integer.class) && value instanceof java.math.BigDecimal) {
                                    value = ((java.math.BigDecimal) value).intValue();
                                }
                                if (paramType == double.class && value instanceof java.math.BigDecimal) {
                                    value = ((java.math.BigDecimal) value).doubleValue();
                                }
                                if (paramType == float.class && value instanceof java.math.BigDecimal) {
                                    value = ((java.math.BigDecimal) value).floatValue();
                                }
                                // Xử lý chuyển đổi Date
                                if (paramType == java.util.Date.class) {
                                    value = convertOracleType(paramType, value, fieldName);
                                }
                                method.invoke(bean, value);
                                success = true;
                            }
                        } catch (Exception e) {
                            // Ignore
                        }
                    }
                    // Cách 2.5: Thử với tên cột snake_case UPPERCASE (ví dụ IS_AVAILABLE)
                    if (!success) {
                        try {
                            String columnName = fieldName.replaceAll("([A-Z])", "_$1").toUpperCase();
                            if (columnName.startsWith("_")) columnName = columnName.substring(1);
                            value = resultSet.getObject(columnName);
                            if (value != null) {
                                if ((paramType == int.class || paramType == Integer.class) && value instanceof java.math.BigDecimal) {
                                    value = ((java.math.BigDecimal) value).intValue();
                                }
                                method.invoke(bean, value);
                                success = true;
                            }
                        } catch (Exception e) {
                            // Ignore
                        }
                    }

                    // Cách 3: Thử với tên cột lowercase
                    if (!success) {
                        try {
                            // System.out.println("[DEBUG] Trying to get column: " + fieldName.toLowerCase());
                            value = resultSet.getObject(fieldName.toLowerCase());
                            if (value != null) {
                                if (paramType == int.class && value instanceof java.math.BigDecimal) {
                                    value = ((java.math.BigDecimal) value).intValue();
                                }
                                if (paramType == double.class && value instanceof java.math.BigDecimal) {
                                    value = ((java.math.BigDecimal) value).doubleValue();
                                }
                                if (paramType == float.class && value instanceof java.math.BigDecimal) {
                                    value = ((java.math.BigDecimal) value).floatValue();
                                }
                                // Xử lý chuyển đổi Date
                                if (paramType == java.util.Date.class) {
                                    value = convertOracleType(paramType, value, fieldName);
                                }
                                method.invoke(bean, value);
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
                            // System.out.println("[DEBUG] Trying to get column by index: " + columnIndex + " for field: " + fieldName);
                            if (columnIndex > 0) {
                                value = resultSet.getObject(columnIndex);
                                if (paramType == int.class && value instanceof java.math.BigDecimal) {
                                    value = ((java.math.BigDecimal) value).intValue();
                                }
                                if (paramType == double.class && value instanceof java.math.BigDecimal) {
                                    value = ((java.math.BigDecimal) value).doubleValue();
                                }
                                if (paramType == float.class && value instanceof java.math.BigDecimal) {
                                    value = ((java.math.BigDecimal) value).floatValue();
                                }
                                // Xử lý chuyển đổi Date
                                if (paramType == java.util.Date.class) {
                                    value = convertOracleType(paramType, value, fieldName);
                                }
                                method.invoke(bean, value);
                                success = true;
                            }
                        } catch (Exception e) {
                            // Ignore
                        }
                    }
                }
            }
            return bean;
        } catch (Exception e) {
            throw new SQLException("Error creating bean: " + e.getMessage(), e);
        }
    }

    /**
     * Lấy index cột dựa vào tên field - ĐÃ THÊM TABLE_FOR_CUSTOMER VÀ CUSTOMER
     */
    private static int getColumnIndex(String fieldName) {
        switch (fieldName) {
            // USER_ACCOUNT mapping (0-based index)
            case "User_id": return 1;
            case "Username": return 2;
            case "Pass": return 3;
            case "FullName": return 4;
            case "Gender": return 5; // Cột thứ 5 trong bảng USER_ACCOUNT
            case "Email": return 6;
            case "Phone_number": return 7;
            case "Image": return 8;
            case "Is_enabled": return 9;
            case "Created_date": return 10;
            case "Role_id": return 11;
            // TABLE_FOR_CUSTOMER mapping
            case "Table_number": return 1;
            case "Amount": return 2;
            case "Status": return 3;
            // CUSTOMER mapping
            case "phone_number": return 1;
            case "customer_name": return 2;
            case "point_level": return 3;
            case "level_ranking": return 4;
            case "created_date": return 5;
            // CATE mapping
            case "Category_id": return 1;
            case "Category_name": return 2;
            case "Is_available": return 3;
            case "is_available": return 3;
            default: return -1;
        }
    }

    /**
     * Convert kiểu dữ liệu phù hợp với Oracle (BigDecimal -> Integer, Timestamp/Date -> java.util.Date)
     */
    private static Object convertOracleType(Class<?> paramType, Object value, String fieldName) {
        if (value == null) {
            // System.out.println("[DEBUG] Null value for field: " + fieldName);
            return null;
        }
        // Xử lý BigDecimal -> Integer
        if (paramType == int.class || paramType == Integer.class) {
            if (value instanceof java.math.BigDecimal) {
                return ((java.math.BigDecimal) value).intValue();
            }
            if (value instanceof Number) {
                return ((Number) value).intValue();
            }
        }
        // Xử lý BigDecimal -> Long
        if (paramType == long.class || paramType == Long.class) {
            if (value instanceof java.math.BigDecimal) {
                return ((java.math.BigDecimal) value).longValue();
            }
            if (value instanceof Number) {
                return ((Number) value).longValue();
            }
        }
        // Xử lý Timestamp/Date -> java.util.Date
        if (paramType == java.util.Date.class) {
            if (value instanceof java.sql.Timestamp) {
                return new java.util.Date(((java.sql.Timestamp) value).getTime());
            }
            if (value instanceof java.sql.Date) {
                return new java.util.Date(((java.sql.Date) value).getTime());
            }
        }
        // Xử lý Boolean (Oracle thường trả về BigDecimal 0/1)
        if (paramType == boolean.class || paramType == Boolean.class) {
            if (value instanceof java.math.BigDecimal) {
                return ((java.math.BigDecimal) value).intValue() != 0;
            }
            if (value instanceof Number) {
                return ((Number) value).intValue() != 0;
            }
        }
        return value;
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