package com.team4.quanliquanmicay.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.Image;
import com.itextpdf.text.Font;
import org.jfree.chart.JFreeChart;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * PDF utilities for exporting reports
 */
public class XPDF {

    /**
     * Export a single chart to PDF with Vietnamese-friendly font.
     * Returns the generated file.
     */
    public static File exportGeneralRevenue(JFreeChart chart, String rangeLabel) throws Exception {
        File out = new File("report_doanh_thu_tong_quat.pdf");

        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(out));
        doc.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

        // Try to load Unicode font from resources to render Vietnamese properly
        try (InputStream is = XPDF.class.getResourceAsStream("/fonts/DejaVuSans.ttf")) {
            if (is != null) {
                File tmp = File.createTempFile("dejavu-", ".ttf");
                Files.copy(is, tmp.toPath(), StandardCopyOption.REPLACE_EXISTING);
                BaseFont bf = BaseFont.createFont(tmp.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                titleFont = new Font(bf, 16, Font.BOLD);
                normalFont = new Font(bf, 12, Font.NORMAL);
                tmp.delete();
            }
        } catch (Exception ignore) {}

        doc.add(new Paragraph("Báo cáo doanh thu tổng quát", titleFont));
        doc.add(new Paragraph("Khoảng: " + (rangeLabel != null ? rangeLabel : ""), normalFont));

        // Render chart image and embed
        BufferedImage img = chart.createBufferedImage(900, 500);
        File tmpPng = File.createTempFile("chart-", ".png");
        ImageIO.write(img, "png", tmpPng);
        Image chartImg = Image.getInstance(tmpPng.getAbsolutePath());
        chartImg.scaleToFit(520, 320);
        doc.add(chartImg);
        doc.close();
        tmpPng.delete();

        return out;
    }
}
