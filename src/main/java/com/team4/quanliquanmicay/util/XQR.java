package com.team4.quanliquanmicay.util;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;

/**
 * Tiện ích liên quan tới QR (VietQR,...)
 */
public final class XQR {

    private XQR() {}

    /**
     * KHAI BÁO CẤU HÌNH TRỰC TIẾP TẠI ĐÂY
     * - Điền sẵn mã ngân hàng, số tài khoản, tên chủ tài khoản
     * - Có thể thay đổi lúc runtime bằng các setter bên dưới nếu cần
     */
    private static String BANK_CODE = "MB";        // VD: MB, VCB, ACB, ...
    private static String ACCOUNT_NUMBER = "2468523993743";   // VD: 123456789
    private static String ACCOUNT_NAME = "PHAN HOANG ANH KHOA";     // VD: FIVE C COMPANY

    public static void setBankConfig(String bankCode, String accountNumber, String accountName) {
        BANK_CODE = bankCode != null ? bankCode.trim() : "";
        ACCOUNT_NUMBER = accountNumber != null ? accountNumber.trim() : "";
        ACCOUNT_NAME = accountName != null ? accountName.trim() : "";
    }

    public static String getBankCode() { return BANK_CODE; }
    public static String getAccountNumber() { return ACCOUNT_NUMBER; }
    public static String getAccountName() { return ACCOUNT_NAME; }

    /**
     * Tạo URL ảnh VietQR (dịch vụ img.vietqr.io)
     * @param bankCode Mã ngân hàng (VD: MB, VCB, ACB,...)
     * @param accountNumber Số tài khoản
     * @param accountName Tên chủ tài khoản (tùy chọn)
     * @param amount Số tiền (VND)
     * @param addInfo Nội dung chuyển khoản (tùy chọn)
     * @param template Mẫu ảnh (mặc định: "compact")
     * @return URL ảnh VietQR hợp lệ hoặc null nếu thiếu tham số bắt buộc
     */
    public static String buildVietQrUrl(String bankCode,
                                        String accountNumber,
                                        String accountName,
                                        double amount,
                                        String addInfo,
                                        String template) {
        if (isBlank(bankCode) || isBlank(accountNumber)) {
            return null;
        }
        if (template == null || template.isBlank()) {
            template = "compact";
        }

        String base = "https://img.vietqr.io/image/" + bankCode.trim() + "-" + accountNumber.trim() + "-" + template + ".png";
        long rounded = Math.max(0, Math.round(amount));
        String safeInfo = isBlank(addInfo) ? "Thanh toan hoa don" : addInfo.trim();
        String safeName = accountName == null ? "" : accountName.trim();

        String query = String.format("?amount=%d&addInfo=%s&accountName=%s",
                rounded,
                urlEncode(safeInfo),
                urlEncode(safeName));
        return base + query;
    }

    /**
     * Overload: sử dụng cấu hình tĩnh trong lớp (đã set ở trên)
     */
    public static String buildVietQrUrl(double amount, String addInfo, String template) {
        return buildVietQrUrl(BANK_CODE, ACCOUNT_NUMBER, ACCOUNT_NAME, amount, addInfo, template);
    }

    /**
     * Tải ảnh từ URL và scale về kích thước vuông mong muốn.
     * @param url URL hình ảnh
     * @param sizePx Kích thước cạnh (px)
     * @return ImageIcon đã scale, hoặc null nếu lỗi
     */
    public static ImageIcon fetchQrIcon(String url, int sizePx) {
        try {
            if (isBlank(url)) return null;
            ImageIcon icon = new ImageIcon(new URL(url));
            Image image = icon.getImage().getScaledInstance(sizePx, sizePx, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Tạo URL VietQR và tải ImageIcon tương ứng.
     */
    public static ImageIcon buildAndFetchVietQrIcon(String bankCode,
                                                    String accountNumber,
                                                    String accountName,
                                                    double amount,
                                                    String addInfo,
                                                    String template,
                                                    int sizePx) {
        String url = buildVietQrUrl(bankCode, accountNumber, accountName, amount, addInfo, template);
        return fetchQrIcon(url, sizePx);
    }

    /**
     * Overload: tạo và tải VietQR dùng cấu hình tĩnh trong lớp.
     */
    public static ImageIcon buildAndFetchVietQrIcon(double amount,
                                                    String addInfo,
                                                    String template,
                                                    int sizePx) {
        String url = buildVietQrUrl(amount, addInfo, template);
        return fetchQrIcon(url, sizePx);
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String urlEncode(String s) {
        try {
            return java.net.URLEncoder.encode(s, java.nio.charset.StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            return s;
        }
    }
}
