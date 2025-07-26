package com.team4.quanliquanmicay.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;

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
        var dburl = "jdbc:oracle:thin:@localhost:1521/XE";
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
            System.err.println("   - Password: sa123");
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
     * Interface xử lý ResultSet, dùng cho các hàm truy vấn an toàn resource
     */
    @FunctionalInterface
    public interface ResultSetHandler<T> {
        T handle(ResultSet rs) throws SQLException;
    }

    /**
     * Thao tác dữ liệu (INSERT, UPDATE, DELETE) - Đảm bảo đóng resource
     */
    public static int executeUpdate(String sql, Object... values) {
        try (PreparedStatement stmt = getStmt(sql, values)) {
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Truy vấn dữ liệu, xử lý qua handler, đảm bảo đóng resource
     * @param sql câu lệnh SQL
     * @param handler lambda xử lý ResultSet
     * @param values tham số
     * @return kết quả handler trả về
     */
    public static <T> T executeQuery(String sql, ResultSetHandler<T> handler, Object... values) {
        try (PreparedStatement stmt = getStmt(sql, values);
             ResultSet rs = stmt.executeQuery()) {
            return handler.handle(rs);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Truy vấn lấy giá trị đầu tiên (1 dòng, 1 cột)
     */
    public static <T> T getValue(String sql, Object... values) {
        return executeQuery(sql, rs -> {
            if (rs.next()) return (T) rs.getObject(1);
            return null;
        }, values);
    }

    /**
     * Truy vấn trả về list object (dùng cho mapping entity)
     * @param sql câu lệnh SQL
     * @param mapper lambda map 1 row -> object
     * @param values tham số
     * @return list object
     */
    public static <T> List<T> queryList(String sql, Function<ResultSet, T> mapper, Object... values) {
        return executeQuery(sql, rs -> {
            List<T> list = new ArrayList<>();
            try {
                while (rs.next()) list.add(mapper.apply(rs));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return list;
        }, values);
    }

    /**
     * Tạo PreparedStatement từ câu lệnh SQL/PROC
     * (Chỉ dùng nội bộ, luôn đóng ở các hàm bên trên)
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

    // XÓA HOẶC COMMENT demo1 vì nó dùng executeQuery trả về ResultSet (KHÔNG AN TOÀN)
    // private static void demo1() {
    //     // Lấy tất cả user có role là 'ADMIN'
    //     String sql = "SELECT * FROM USER_ACCOUNT WHERE role_id = ?";
    //     var rs = XJdbc.executeQuery(sql, "ADMIN");
    //     try {
    //         while (rs.next()) {
    //             System.out.println("Username: " + rs.getString("username"));
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }

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
