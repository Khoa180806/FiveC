package com.team4.quanliquanmicay.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.block.BlockBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;

/**
 * Utility class cho việc tạo biểu đồ với JFreeChart
 * @author FiveC Team
 */
public class XChart {
    
    // ========================================
    // BIỂU ĐỒ CỘT (BAR CHART)
    // ========================================
    
    /**
     * Tạo biểu đồ cột đơn giản
     */
    public static JFreeChart createBarChart(String title, String categoryAxisLabel, 
                                          String valueAxisLabel, DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
            title,           // Tiêu đề biểu đồ
            categoryAxisLabel, // Nhãn trục X
            valueAxisLabel,    // Nhãn trục Y
            dataset,          // Dữ liệu
            PlotOrientation.VERTICAL, // Hướng biểu đồ
            true,            // Hiển thị legend
            true,            // Hiển thị tooltips
            false            // Hiển thị URLs
        );
        
        // Tùy chỉnh giao diện
        customizeChart(chart);
        return chart;
    }
    
    /**
     * Tạo biểu đồ cột từ Map data
     */
    public static JFreeChart createBarChartFromMap(String title, String categoryAxisLabel, 
                                                  String valueAxisLabel, Map<String, Number> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (Map.Entry<String, Number> entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), "Data", entry.getKey());
        }
        
        return createBarChart(title, categoryAxisLabel, valueAxisLabel, dataset);
    }
    
    // ========================================
    // BIỂU ĐỒ TRÒN (PIE CHART)
    // ========================================
    
    /**
     * Tạo biểu đồ tròn đơn giản
     */
    public static JFreeChart createPieChart(String title, DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
            title,    // Tiêu đề biểu đồ
            dataset,  // Dữ liệu
            true,     // Hiển thị legend
            true,     // Hiển thị tooltips
            false     // Hiển thị URLs
        );
        
        // Tùy chỉnh giao diện
        customizeChart(chart);
        return chart;
    }
    
    /**
     * Tạo biểu đồ tròn từ Map data
     */
    public static JFreeChart createPieChartFromMap(String title, Map<String, Number> data) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        for (Map.Entry<String, Number> entry : data.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        
        return createPieChart(title, dataset);
    }
    
    // ========================================
    // BIỂU ĐỒ ĐƯỜNG (LINE CHART)
    // ========================================
    
    /**
     * Tạo biểu đồ đường đơn giản
     */
    public static JFreeChart createLineChart(String title, String xAxisLabel, 
                                           String yAxisLabel, XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
            title,           // Tiêu đề biểu đồ
            xAxisLabel,      // Nhãn trục X
            yAxisLabel,      // Nhãn trục Y
            dataset,         // Dữ liệu
            PlotOrientation.VERTICAL, // Hướng biểu đồ
            true,            // Hiển thị legend
            true,            // Hiển thị tooltips
            false            // Hiển thị URLs
        );
        
        // Tùy chỉnh giao diện
        customizeChart(chart);
        return chart;
    }
    
    /**
     * Tạo biểu đồ đường từ List data
     */
    public static JFreeChart createLineChartFromList(String title, String xAxisLabel, 
                                                   String yAxisLabel, List<Point> dataPoints) {
        XYSeries series = new XYSeries("Data");
        
        for (Point point : dataPoints) {
            series.add(point.x, point.y);
        }
        
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        return createLineChart(title, xAxisLabel, yAxisLabel, dataset);
    }
    
    // ========================================
    // BIỂU ĐỒ KẾT HỢP (COMBINED CHART)
    // ========================================
    
    /**
     * Tạo biểu đồ kết hợp cột và đường
     */
    public static JFreeChart createCombinedChart(String title, String categoryAxisLabel, 
                                               String valueAxisLabel, DefaultCategoryDataset barData, 
                                               DefaultCategoryDataset lineData) {
        JFreeChart chart = ChartFactory.createBarChart(
            title, categoryAxisLabel, valueAxisLabel, barData,
            PlotOrientation.VERTICAL, true, true, false
        );
        
        // Ghi chú: Overlay line trên bar với CategoryPlot yêu cầu cấu hình nâng cao.
        // Ở phiên bản hiện tại, ta giữ biểu đồ cột. Nếu cần overlay, sẽ nâng cấp ở bước sau.
        
        customizeChart(chart);
        return chart;
    }
    
    // ========================================
    // TÙY CHỈNH GIAO DIỆN
    // ========================================
    
    /**
     * Tùy chỉnh giao diện biểu đồ
     */
    private static void customizeChart(JFreeChart chart) {
        // Tùy chỉnh tiêu đề
        TextTitle title = chart.getTitle();
        if (title != null) {
            title.setFont(new Font("Segoe UI", Font.BOLD, 16));
            title.setPaint(Color.BLACK);
        }
        
        // Tùy chỉnh legend
        LegendTitle legend = chart.getLegend();
        if (legend != null) {
            legend.setFrame(BlockBorder.NONE);
            // LegendTitle không có method setFont trong JFreeChart 1.5.6
        }
        
        // Tùy chỉnh plot
        if (chart.getPlot() instanceof XYPlot) {
            XYPlot plot = (XYPlot) chart.getPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
            plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
            
            // Tùy chỉnh trục
            NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            
            if (domainAxis != null) {
                domainAxis.setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
                domainAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 10));
            }
            
            if (rangeAxis != null) {
                rangeAxis.setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
                rangeAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 10));
            }
        }
    }
    
    // ========================================
    // HIỂN THỊ BIỂU ĐỒ
    // ========================================
    
    /**
     * Hiển thị biểu đồ trong JFrame
     */
    public static void showChart(JFreeChart chart, String frameTitle) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setMouseZoomable(true, false);
        
        JFrame frame = new JFrame(frameTitle);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    /**
     * Hiển thị biểu đồ trong JDialog
     */
    public static void showChartDialog(JFreeChart chart, String dialogTitle, JFrame parent) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(700, 500));
        chartPanel.setMouseZoomable(true, false);
        
        JDialog dialog = new JDialog(parent, dialogTitle, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setContentPane(chartPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
    
    /**
     * Tạo ChartPanel để nhúng vào form
     */
    public static ChartPanel createChartPanel(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        chartPanel.setMouseZoomable(true, false);
        return chartPanel;
    }
    
    // ========================================
    // DEMO VÀ TEST
    // ========================================
    
    /**
     * Demo tạo biểu đồ cột
     */
    public static JFreeChart createDemoBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(120, "Doanh thu", "T1");
        dataset.addValue(150, "Doanh thu", "T2");
        dataset.addValue(180, "Doanh thu", "T3");
        dataset.addValue(200, "Doanh thu", "T4");
        dataset.addValue(160, "Doanh thu", "T5");
        dataset.addValue(220, "Doanh thu", "T6");
        
        return createBarChart("Doanh thu theo tháng", "Tháng", "Doanh thu (triệu VNĐ)", dataset);
    }
    
    /**
     * Demo tạo biểu đồ tròn
     */
    public static JFreeChart createDemoPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Mi cay", 35);
        dataset.setValue("Nuoc uong", 25);
        dataset.setValue("An vat", 20);
        dataset.setValue("Lau", 15);
        dataset.setValue("Khac", 5);
        
        return createPieChart("Tỷ lệ doanh thu theo danh mục", dataset);
    }
    
    /**
     * Demo tạo biểu đồ đường
     */
    public static JFreeChart createDemoLineChart() {
        XYSeries series = new XYSeries("Lượng khách");
        series.add(1, 50);
        series.add(2, 65);
        series.add(3, 80);
        series.add(4, 75);
        series.add(5, 90);
        series.add(6, 85);
        
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        return createLineChart("Lượng khách theo ngày", "Ngày", "Số khách", dataset);
    }
    
    /**
     * Test tất cả các loại biểu đồ
     */
    public static void main(String[] args) {
        // Demo biểu đồ cột
        JFreeChart barChart = createDemoBarChart();
        showChart(barChart, "Demo Biểu đồ cột");
        
        // Demo biểu đồ tròn
        JFreeChart pieChart = createDemoPieChart();
        showChart(pieChart, "Demo Biểu đồ tròn");
        
        // Demo biểu đồ đường
        JFreeChart lineChart = createDemoLineChart();
        showChart(lineChart, "Demo Biểu đồ đường");
    }
}
