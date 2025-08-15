package com.team4.quanliquanmicay.util;

import com.team4.quanliquanmicay.Entity.Bill;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Excel utilities for exporting reports
 */
public class XExcel {

    /**
     * Export list of paid bills to an Excel .xls file and return the generated file
     */
    public static File exportGeneralRevenueBills(List<Bill> paidBills, String rangeLabel) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Doanh thu");
        int r = 0;
        HSSFRow row0 = sheet.createRow(r++);
        row0.createCell(0).setCellValue("Báo cáo doanh thu tổng quát");
        HSSFRow row1 = sheet.createRow(r++);
        row1.createCell(0).setCellValue("Khoảng");
        row1.createCell(1).setCellValue(rangeLabel);

        HSSFRow header = sheet.createRow(r++);
        header.createCell(0).setCellValue("Bill ID");
        header.createCell(1).setCellValue("Bàn");
        header.createCell(2).setCellValue("Checkout");
        header.createCell(3).setCellValue("Tổng tiền");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        double total = 0;
        for (Bill b : paidBills) {
            HSSFRow rr = sheet.createRow(r++);
            rr.createCell(0).setCellValue(b.getBill_id());
            rr.createCell(1).setCellValue(b.getTable_number());
            rr.createCell(2).setCellValue(b.getCheckout() != null ? sdf.format(b.getCheckout()) : "");
            rr.createCell(3).setCellValue(b.getTotal_amount());
            total += b.getTotal_amount();
        }
        HSSFRow totalRow = sheet.createRow(r++);
        totalRow.createCell(2).setCellValue("Tổng");
        totalRow.createCell(3).setCellValue(total);

        for (int i = 0; i < 4; i++) sheet.autoSizeColumn(i);
        File out = new File("report_doanh_thu_tong_quat.xls");
        try (FileOutputStream fos = new FileOutputStream(out)) { wb.write(fos); }
        wb.close();
        return out;
    }
}
