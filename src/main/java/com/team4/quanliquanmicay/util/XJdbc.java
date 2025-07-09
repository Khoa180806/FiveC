package com.team4.quanliquanmicay.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Lớp tiện ích hỗ trợ làm việc với CSDL quan hệ
 *
 * @author NghiemN
 * @version 1.0
 */
public class XJdbc {

    private static Connection connection;

    /**
     * Mở kết nối nếu chưa mở hoặc đã đóng
     *
     * @return Kết nối đã sẵn sàng
     */
    public static Connection openConnection() {
        var driver = "oracle.jdbc.driver.OracleDriver";
        var dburl = "jdbc:oracle:thin:@localhost:1521:XE";
        var username = "SYSTEM";
        var password = "root123";
        try {
            if (!XJdbc.isReady()) {
                Class.forName(driver);
                connection = DriverManager.getConnection(dburl, username, password);
                System.out.println("Kết nối database thành công!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi: Không tìm thấy Oracle JDBC Driver");
            System.err.println("Hãy đảm bảo đã thêm oracle-jdbc.jar vào classpath");
            throw new RuntimeException("Oracle JDBC Driver không tồn tại", e);
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối database: " + e.getMessage());
            System.err.println("Hãy kiểm tra:");
            System.err.println("1. Oracle Database đã được cài đặt và chạy chưa?");
            System.err.println("2. Listener đã được khởi động chưa?");
            System.err.println("3. Thông tin kết nối có đúng không?");
            System.err.println("   - Host: localhost");
            System.err.println("   - Port: 1521");
            System.err.println("   - SID: XE");
            System.err.println("   - Username: SYSTEM");
            System.err.println("   - Password: root123");
            throw new RuntimeException("Không thể kết nối đến database", e);
        }
        return connection;
    }

    /**
     * Đóng kết nối
     */
    public static void closeConnection() {
        try {
            if (XJdbc.isReady()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Kiểm tra kết nối đã sẵn sàng hay chưa
     * @return true nếu kết nối đã được mở
     */
    public static boolean isReady() {
        try {
            return (connection != null && !connection.isClosed());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Thao tác dữ liệu
     *
     * @param sql câu lệnh SQL (INSERT, UPDATE, DELETE)
     * @param values các giá trị cung cấp cho các tham số trong SQL
     * @return số lượng bản ghi đã thực hiện
     * @throws RuntimeException không thực thi được câu lệnh SQL
     */
    public static int executeUpdate(String sql, Object... values) {
        try {
            var stmt = XJdbc.getStmt(sql, values);
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Truy vấn dữ liệu
     *
     * @param sql câu lệnh SQL (SELECT)
     * @param values các giá trị cung cấp cho các tham số trong SQL
     * @return tập kết quả truy vấn
     * @throws RuntimeException không thực thi được câu lệnh SQL
     */
    public static ResultSet executeQuery(String sql, Object... values) {
        try {
            var stmt = XJdbc.getStmt(sql, values);
            return stmt.executeQuery();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Truy vấn một giá trị
     *
     * @param <T> kiểu dữ liệu kết quả
     * @param sql câu lệnh SQL (SELECT)
     * @param values các giá trị cung cấp cho các tham số trong SQL
     * @return giá trị truy vấn hoặc null
     * @throws RuntimeException không thực thi được câu lệnh SQL
     */
    public static <T> T getValue(String sql, Object... values) {
        try {
            var resultSet = XJdbc.executeQuery(sql, values);
            if (resultSet.next()) {
                return (T) resultSet.getObject(1);
            }
            return null;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Tạo PreparedStatement từ câu lệnh SQL/PROC
     *
     * @param sql câu lệnh SQL/PROC
     * @param values các giá trị cung cấp cho các tham số trong SQL/PROC
     * @return đối tượng đã tạo
     * @throws SQLException không tạo được PreparedStatement
     */
    private static PreparedStatement getStmt(String sql, Object... values) throws SQLException {
        var conn = XJdbc.openConnection();
        var stmt = sql.trim().startsWith("{") ? conn.prepareCall(sql) : conn.prepareStatement(sql);
        for (int i = 0; i < values.length; i++) {
            stmt.setObject(i + 1, values[i]);
        }
        return stmt;
    }

    public static void main(String[] args) {
        // Kiểm tra kết nối database trước khi chạy demo
        try {
            System.out.println("Đang kiểm tra kết nối database...");
            XJdbc.openConnection();
            System.out.println("Kết nối thành công! Có thể chạy các demo.");
            
            // Uncomment các dòng sau khi database đã sẵn sàng
            // demo1();
            // demo2();
            // demo3();
            
            // String sql = "INSERT INTO CATE (category_id, category_name) VALUES(?, ?)";
            // XJdbc.executeUpdate(sql, "C01", "Loại 1");
            // XJdbc.executeUpdate(sql, "C02", "Loại 2");
            
        } catch (Exception e) {
            System.err.println("Không thể kết nối database: " + e.getMessage());
            System.err.println("Vui lòng kiểm tra cấu hình database và thử lại.");
        } finally {
            XJdbc.closeConnection();
        }
    }

    private static void demo1() {
        // Lấy tất cả user có role là 'ADMIN'
        String sql = "SELECT * FROM USER_ACCOUNT WHERE role_id = ?";
        var rs = XJdbc.executeQuery(sql, "ADMIN");
        try {
            while (rs.next()) {
                System.out.println("Username: " + rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void demo2() {
        // Lấy số lượng user đang kích hoạt
        String sql = "SELECT COUNT(*) FROM USER_ACCOUNT WHERE is_enabled = 1";
        var count = XJdbc.getValue(sql);
        System.out.println("Số user đang kích hoạt: " + count);
    }

    private static void demo3() {
        // Xóa user theo username
        String sql = "DELETE FROM USER_ACCOUNT WHERE username = ?";
        var affected = XJdbc.executeUpdate(sql, "user_test");
        System.out.println("Số dòng bị xóa: " + affected);
    }
}
