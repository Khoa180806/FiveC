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
        var dburl = "jdbc:oracle:thin:@127.0.0.1:1521/XE";
        var username = "SYSTEM";
        var password = "root123";
        try {
            if (!XJdbc.isReady()) {
                Class.forName(driver);
                
                // Thêm properties để cải thiện kết nối
                java.util.Properties props = new java.util.Properties();
                props.setProperty("user", username);
                props.setProperty("password", password);
                props.setProperty("oracle.net.CONNECT_TIMEOUT", "10000"); // 10 giây timeout
                // Tăng thời gian timeout đọc để tránh ORA-18730 khi thực hiện nhiều INSERT/UPDATE liên tiếp
                props.setProperty("oracle.jdbc.ReadTimeout", "60000"); // 60 giây read timeout
                props.setProperty("oracle.net.TNS_ADMIN", "");
                
                connection = DriverManager.getConnection(dburl, props);
                System.out.println("Kết nối database thành công!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi: Không tìm thấy Oracle JDBC Driver");
            System.err.println("Hãy đảm bảo đã thêm oracle-jdbc.jar vào classpath");
            throw new RuntimeException("Oracle JDBC Driver không tồn tại", e);
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối database: " + e.getMessage());
            System.err.println("Mã lỗi: " + e.getErrorCode());
            System.err.println("Hãy kiểm tra:");
            System.err.println("1. Oracle Database đã được cài đặt và chạy chưa?");
            System.err.println("2. Listener đã được khởi động chưa?");
            System.err.println("3. Firewall có chặn port 1521 không?");
            System.err.println("4. Network connectivity đến 127.0.0.1:1521");
            System.err.println("5. Thông tin kết nối có đúng không?");
            System.err.println("   - Host: 127.0.0.1");
            System.err.println("   - Port: 1521");
            System.err.println("   - Service: XE");
            System.err.println("   - Username: SYSTEM");
            System.err.println("   - Password: root123");
            
            // Thêm thông tin debug
            if (e.getErrorCode() == 12170) {
                System.err.println("ORA-12170: TNS:Connect timeout occurred");
                System.err.println("Nguyên nhân có thể:");
                System.err.println("- Oracle listener không chạy");
                System.err.println("- Firewall chặn kết nối");
                System.err.println("- IP 127.0.0.1 không đúng");
                System.err.println("- Port 1521 bị chặn");
            }
            
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
     * Truy vấn lấy giá trị đầu tiên với chuyển đổi kiểu an toàn
     */
    public static <T> T getValue(String sql, Class<T> type, Object... values) {
        return executeQuery(sql, rs -> {
            if (rs.next()) {
                Object result = rs.getObject(1);
                if (result == null) return null;
                
                // Handle number conversions safely
                if (type == Integer.class) {
                    if (result instanceof Number) {
                        return (T) Integer.valueOf(((Number) result).intValue());
                    }
                } else if (type == Long.class) {
                    if (result instanceof Number) {
                        return (T) Long.valueOf(((Number) result).longValue());
                    }
                } else if (type == java.math.BigDecimal.class) {
                    if (result instanceof Number) {
                        return (T) new java.math.BigDecimal(result.toString());
                    }
                } else if (type == String.class) {
                    return (T) result.toString();
                }
                
                // Default: try direct cast
                return (T) result;
            }
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
        // Thiết lập timeout cho câu lệnh để tránh treo gây ORA-18730 khi gặp lock/chờ quá lâu
        try { stmt.setQueryTimeout(30); } catch (Exception ignore) {}
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
            
  
            
        } catch (Exception e) {
            System.err.println("Không thể kết nối database: " + e.getMessage());
            System.err.println("Vui lòng kiểm tra cấu hình database và thử lại.");
        } finally {
            XJdbc.closeConnection();
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
