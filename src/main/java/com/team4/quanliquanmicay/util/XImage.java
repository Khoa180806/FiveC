package com.team4.quanliquanmicay.util;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class XImage {

    /**
     * Set ảnh vào JLabel, resize cho vừa với kích thước JLabel.
     * @param label JLabel cần đặt ảnh
     * @param imagePath Đường dẫn ảnh (tính từ thư mục resources, ví dụ "/images/anh.jpg")
     */
    public static void setImageToLabel(JLabel label, String imagePath) {
        try {
            java.net.URL imageURL = XImage.class.getResource(imagePath);
            if (imageURL == null) {
                // Nếu không tìm thấy ảnh, dùng ảnh mặc định
                imageURL = XImage.class.getResource("/icons_and_images/Unknown person.png");
                if (imageURL == null) {
                    // Nếu không có ảnh mặc định, xóa icon hiện tại
                    label.setIcon(null);
                    return;
                }
            }
            
            ImageIcon icon = new ImageIcon(imageURL);
            Image image = icon.getImage();

            int width = label.getWidth();
            int height = label.getHeight();

            // Resize ảnh
            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            // Đặt ảnh đã resize vào JLabel
            label.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
            // Nếu có lỗi, xóa icon hiện tại
            label.setIcon(null);
        }
    }
}