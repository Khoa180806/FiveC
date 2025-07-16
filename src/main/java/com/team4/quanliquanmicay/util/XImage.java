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
            ImageIcon icon = new ImageIcon(XImage.class.getResource(imagePath));
            Image image = icon.getImage();

            int width = label.getWidth();
            int height = label.getHeight();

            // Resize ảnh
            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            // Đặt ảnh đã resize vào JLabel
            label.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}