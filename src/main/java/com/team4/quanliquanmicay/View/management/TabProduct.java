package com.team4.quanliquanmicay.View.management;

import com.team4.quanliquanmicay.DAO.BillDAO;
import com.team4.quanliquanmicay.DAO.BillDetailsDAO;
import com.team4.quanliquanmicay.DAO.ProductDAO;
import com.team4.quanliquanmicay.Entity.Bill;
import com.team4.quanliquanmicay.Entity.BillDetails;
import com.team4.quanliquanmicay.Entity.Product;
import com.team4.quanliquanmicay.util.TimeRange;
import com.team4.quanliquanmicay.util.XChart;
import com.team4.quanliquanmicay.util.XTheme;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import java.text.DecimalFormat;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.plot.PieLabelLinkStyle;
import java.awt.BasicStroke;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;

/**
 * Tab thống kê món ăn: biểu đồ + bảng doanh thu theo món trong khoảng thời gian.
 */
public class TabProduct extends JPanel {

	private final BillDAO billDAO;
	private final BillDetailsDAO billDetailsDAO;
	private final ProductDAO productDAO;

	private JComboBox<String> cboRange;
	private JComboBox<String> cboChartType;
	private JPanel chartContainer;
	private JPanel tableContainer;
	private JPanel kpiContainer;
	private JTable tblProd;
	private DefaultTableModel prodTableModel;
	private TableRowSorter<DefaultTableModel> sorter;
	private JTextField txtSearch;

	public TabProduct(BillDAO billDAO, BillDetailsDAO billDetailsDAO, ProductDAO productDAO) {
		this.billDAO = billDAO;
		this.billDetailsDAO = billDetailsDAO;
		this.productDAO = productDAO;
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		buildUI();
		refreshData();
	}

	private void buildUI() {
		// Header
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(Color.WHITE);
		header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel title = new JLabel("Doanh thu theo món");
		title.setFont(new Font("Tahoma", Font.BOLD, 20));
		title.setForeground(new Color(134, 39, 43));
		header.add(title, BorderLayout.WEST);

		JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
		rightHeader.setOpaque(false);
		cboRange = new JComboBox<>(new String[]{"Ngày", "Tháng", "Năm"});
		cboRange.setSelectedItem("Tháng");
		cboChartType = new JComboBox<>(new String[]{"Biểu đồ tròn", "Biểu đồ cột"});
		cboChartType.setSelectedItem("Biểu đồ tròn");
		txtSearch = new JTextField(14);
		txtSearch.setToolTipText("Tìm kiếm theo tên món...");
		JButton btnRefresh = XTheme.createBeButton("Làm mới", _ -> { refreshData(); });
		rightHeader.add(new JLabel("Khoảng:"));
		rightHeader.add(cboRange);
		rightHeader.add(new JLabel("Biểu đồ:"));
		rightHeader.add(cboChartType);
		rightHeader.add(new JLabel("Tìm kiếm:"));
		rightHeader.add(txtSearch);
		rightHeader.add(btnRefresh);
		header.add(rightHeader, BorderLayout.EAST);

		add(header, BorderLayout.NORTH);

		// Center with KPI + chart + table
		JPanel center = new JPanel();
		center.setBackground(Color.WHITE);
		center.setLayout(new javax.swing.BoxLayout(center, javax.swing.BoxLayout.Y_AXIS));

		kpiContainer = new JPanel();
		kpiContainer.setLayout(new java.awt.GridLayout(1, 3, 12, 0));
		kpiContainer.setBackground(Color.WHITE);
		kpiContainer.setBorder(BorderFactory.createEmptyBorder(4, 12, 0, 12));
		center.add(kpiContainer);

		chartContainer = new JPanel(new BorderLayout());
		chartContainer.setBorder(BorderFactory.createEmptyBorder(6, 12, 12, 12));
		chartContainer.setBackground(Color.WHITE);
		chartContainer.setPreferredSize(new Dimension(10, 480));
		chartContainer.setMinimumSize(new Dimension(10, 420));
		center.add(chartContainer);

		tableContainer = new JPanel(new BorderLayout());
		tableContainer.setBackground(Color.WHITE);
		tableContainer.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
		initTable();
		tableContainer.setPreferredSize(new Dimension(10, 220));
		center.add(tableContainer);


		JScrollPane scroll = new JScrollPane(center);
		scroll.setBorder(null);
		scroll.getViewport().setBackground(Color.WHITE);
		scroll.getVerticalScrollBar().setUnitIncrement(20);
		add(scroll, BorderLayout.CENTER);

		cboRange.addActionListener(_ -> { refreshData(); });
		cboChartType.addActionListener(_ -> { refreshData(); });
		txtSearch.getDocument().addDocumentListener(new DocumentListener() {
			@Override public void insertUpdate(DocumentEvent e) { applyFilter(); }
			@Override public void removeUpdate(DocumentEvent e) { applyFilter(); }
			@Override public void changedUpdate(DocumentEvent e) { applyFilter(); }
		});
	}

	private void initTable() {
		prodTableModel = new DefaultTableModel(new Object[]{"Món", "Số lượng bán", "Doanh thu (VNĐ)"}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		tblProd = new JTable(prodTableModel) {
			@Override
			public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
				java.awt.Component c = super.prepareRenderer(renderer, row, column);
				if (!isRowSelected(row)) {
					c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
				}
				return c;
			}
		};
		tblProd.setRowHeight(28);
		tblProd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tblProd.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
		tblProd.getTableHeader().setBackground(new Color(134,39,43));
		tblProd.getTableHeader().setForeground(Color.WHITE);

		sorter = new TableRowSorter<>(prodTableModel);
		tblProd.setRowSorter(sorter);

		DefaultTableCellRenderer right = new DefaultTableCellRenderer();
		right.setHorizontalAlignment(SwingConstants.RIGHT);
		tblProd.getColumnModel().getColumn(1).setCellRenderer(right);
		tblProd.getColumnModel().getColumn(2).setCellRenderer(right);

		JScrollPane sp = new JScrollPane(tblProd);
		tableContainer.add(sp, BorderLayout.CENTER);
	}

	private void refreshData() {
		try {
			TimeRange range = getSelectedRange();

			List<Bill> bills = billDAO.findAll();
			List<BillDetails> billDetails = billDetailsDAO.findAll();
			List<Product> products = productDAO.findAll();

			if (bills == null) bills = new ArrayList<>();
			if (billDetails == null) billDetails = new ArrayList<>();
			if (products == null) products = new ArrayList<>();

			Map<Integer, Bill> billIdToBill = new HashMap<>();
			for (Bill b : bills) {
				if (b != null && b.getBill_id() != null) billIdToBill.put(b.getBill_id(), b);
			}
			Map<String, String> productIdToName = new HashMap<>();
			for (Product p : products) {
				if (p != null && p.getProductId() != null) productIdToName.put(p.getProductId(), p.getProductName());
			}

			Map<String, Double> revenueByProductId = new HashMap<>();
			Map<String, Integer> quantityByProductId = new HashMap<>();
			for (Product p : products) {
				if (p != null && p.getProductId() != null) {
					revenueByProductId.putIfAbsent(p.getProductId(), 0.0);
					quantityByProductId.putIfAbsent(p.getProductId(), 0);
				}
			}

			for (BillDetails d : billDetails) {
				if (d == null) continue;
				Bill bill = billIdToBill.get(d.getBill_id());
				if (bill == null || bill.getStatus() == null || bill.getStatus() != 1) continue;
				java.util.Date when = bill.getCheckout() != null ? bill.getCheckout() : bill.getCheckin();
				if (when == null || !withinRange(when, range)) continue;
				String productId = d.getProduct_id();
				if (productId != null) {
					revenueByProductId.merge(productId, d.getTotalPrice(), Double::sum);
					quantityByProductId.merge(productId, d.getAmount(), Integer::sum);
				}
			}

			List<Map.Entry<String, Double>> sorted = new ArrayList<>(revenueByProductId.entrySet());
			sorted.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

			// KPIs
			double totalRevenue = revenueByProductId.values().stream().mapToDouble(Double::doubleValue).sum();
			int totalQty = quantityByProductId.values().stream().mapToInt(Integer::intValue).sum();
			String topName = sorted.isEmpty() ? "N/A" : productIdToName.getOrDefault(sorted.get(0).getKey(), sorted.get(0).getKey());
			double topRevenue = sorted.isEmpty() ? 0.0 : sorted.get(0).getValue();
			updateKpis(totalRevenue, totalQty, topName, topRevenue);

			updateChart(sorted, productIdToName);
			updateTable(sorted, quantityByProductId, productIdToName);
		} catch (Exception ex) {
			ex.printStackTrace();
			showError("Lỗi tải dữ liệu doanh thu theo món: " + ex.getMessage());
		}
	}

	private void updateChart(List<Map.Entry<String, Double>> sortedRevenueByProductId, Map<String, String> productIdToName) {
		try {
			String chartType = cboChartType != null ? (String) cboChartType.getSelectedItem() : "Biểu đồ tròn";
			ChartPanel panel;
			if ("Biểu đồ cột".equals(chartType)) {
				panel = createBarPanel(sortedRevenueByProductId, productIdToName);
			} else {
				panel = createPiePanel(sortedRevenueByProductId, productIdToName);
			}
			chartContainer.removeAll();
			chartContainer.add(panel, BorderLayout.CENTER);
			chartContainer.revalidate();
			chartContainer.repaint();
		} catch (Exception ex) {
			ex.printStackTrace();
			chartContainer.removeAll();
			JLabel error = new JLabel("  Lỗi tạo biểu đồ: " + ex.getMessage());
			error.setHorizontalAlignment(SwingConstants.CENTER);
			error.setFont(new Font("Tahoma", Font.BOLD, 14));
			error.setForeground(Color.RED);
			chartContainer.add(error, BorderLayout.CENTER);
			chartContainer.revalidate();
			chartContainer.repaint();
		}
	}

	private ChartPanel createPiePanel(List<Map.Entry<String, Double>> sortedRevenueByProductId, Map<String, String> productIdToName) {
		DefaultPieDataset ds = new DefaultPieDataset();
		int count = 0;
		for (Map.Entry<String, Double> e : sortedRevenueByProductId) {
			if (e.getValue() == null || e.getValue() <= 0) continue;
			String name = productIdToName.getOrDefault(e.getKey(), e.getKey());
			ds.setValue(name, e.getValue());
			if (++count >= 10) break;
		}
		JFreeChart chart = XChart.createPieChart("Top 10 món bán chạy", ds);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setOutlineVisible(false);
		plot.setCircular(true);
		// Ghi chú (nhãn) nằm bên ngoài biểu đồ với đường nối
		plot.setSimpleLabels(false); // để label ra ngoài
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})", new DecimalFormat("#,##0"), new DecimalFormat("0.0%")));
		plot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);
		plot.setLabelLinkPaint(new Color(120,120,120));
		plot.setLabelLinkStroke(new BasicStroke(1.2f));
		plot.setMaximumLabelWidth(0.22);
		plot.setLabelFont(new Font("Tahoma", Font.PLAIN, 11));
		plot.setLabelGap(0.04);
		plot.setInteriorGap(0.04);
		// Legend bên ngoài biểu đồ
		if (chart.getLegend() != null) {
			chart.getLegend().setPosition(RectangleEdge.BOTTOM);
			chart.getLegend().setBackgroundPaint(Color.WHITE);
			chart.getLegend().setItemFont(new Font("Tahoma", Font.PLAIN, 11));
		}
		return XChart.createChartPanel(chart);
	}

	private ChartPanel createBarPanel(List<Map.Entry<String, Double>> sortedRevenueByProductId, Map<String, String> productIdToName) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int count = 0;
		for (Map.Entry<String, Double> e : sortedRevenueByProductId) {
			String name = productIdToName.getOrDefault(e.getKey(), e.getKey());
			dataset.addValue(e.getValue() != null ? e.getValue() : 0.0, "Doanh thu", name);
			if (++count >= 10) break;
		}
		JFreeChart chart = XChart.createBarChart("Top 10 món theo doanh thu", "Món", "VNĐ", dataset);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinePaint(new Color(220,220,220));
		plot.setRangeGridlinePaint(new Color(220,220,220));
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setSeriesPaint(0, new Color(134,39,43));
		renderer.setItemMargin(0.1);
		
		// THÊM: Hiển thị số liệu trên cột
		renderer.setDefaultItemLabelGenerator(
			new StandardCategoryItemLabelGenerator(
				"{2}", // Pattern để hiển thị giá trị
				new java.text.DecimalFormat("#,##0") // Format làm tròn về số nguyên, dấu phẩy ngăn cách hàng nghìn
			)
		);
		renderer.setDefaultItemLabelsVisible(true);
		
		// Tùy chỉnh font và vị trí label để số hiển thị rõ ràng
		renderer.setDefaultItemLabelFont(new Font("Tahoma", Font.BOLD, 11));
		renderer.setDefaultItemLabelPaint(Color.BLACK);
		
		// Tùy chỉnh vị trí label - đặt phía trên cột
		renderer.setDefaultPositiveItemLabelPosition(
			new org.jfree.chart.labels.ItemLabelPosition(
				org.jfree.chart.labels.ItemLabelAnchor.OUTSIDE12,
				org.jfree.chart.ui.TextAnchor.BOTTOM_CENTER,
				org.jfree.chart.ui.TextAnchor.BOTTOM_CENTER,
				0.0
			)
		);
		
		// Tự động scale trục Y để số không bị cắt
		double maxValue = sortedRevenueByProductId.stream()
			.mapToDouble(e -> e.getValue() != null ? e.getValue() : 0.0)
			.max()
			.orElse(0.0);
		
		if (maxValue > 0) {
			double upperBound = maxValue * 1.2; // Tăng 20% so với giá trị cao nhất
			plot.getRangeAxis().setUpperBound(upperBound);
			plot.getRangeAxis().setLowerBound(0.0);
		}
		
		// Legend bên ngoài biểu đồ
		if (chart.getLegend() != null) {
			chart.getLegend().setPosition(RectangleEdge.BOTTOM);
			chart.getLegend().setBackgroundPaint(Color.WHITE);
			chart.getLegend().setItemFont(new Font("Tahoma", Font.PLAIN, 11));
		}
		return XChart.createChartPanel(chart);
	}

	private void updateTable(List<Map.Entry<String, Double>> sortedRevenueByProductId,
							Map<String, Integer> quantityByProductId,
							Map<String, String> productIdToName) {
		prodTableModel.setRowCount(0);
		NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("vi-VN"));
		for (Map.Entry<String, Double> e : sortedRevenueByProductId) {
			String productId = e.getKey();
			String name = productIdToName.getOrDefault(productId, productId);
			int qty = quantityByProductId.getOrDefault(productId, 0);
			double revenue = e.getValue() != null ? e.getValue() : 0.0;
			prodTableModel.addRow(new Object[]{name, qty, nf.format(revenue)});
		}
	}

	private void updateKpis(double totalRevenue, int totalQty, String topName, double topRevenue) {
		kpiContainer.removeAll();
		kpiContainer.add(createKpiCard("Tổng doanh thu", String.format("%,.0f VNĐ", totalRevenue), new Color(134,39,43)));
		kpiContainer.add(createKpiCard("Tổng số lượng", String.valueOf(totalQty), new Color(52,144,220)));
		String topText = topRevenue > 0 ? String.format("%s<br/>%,.0f VNĐ", sanitize(topName), topRevenue) : "N/A";
		kpiContainer.add(createKpiCard("Top món", "<html><center>" + topText + "</center></html>", new Color(255,193,7)));
		kpiContainer.revalidate();
		kpiContainer.repaint();
	}

	private JPanel createKpiCard(String label, String value, Color valueColor) {
		JPanel card = new JPanel(new BorderLayout());
		card.setBackground(Color.WHITE);
		card.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(new Color(230,230,230), 1),
			BorderFactory.createEmptyBorder(10, 10, 10, 10)
		));

		JLabel lbTitle = new JLabel(label);
		lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbTitle.setForeground(new Color(108,117,125));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lbValue = new JLabel(value);
		lbValue.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbValue.setForeground(valueColor);
		lbValue.setHorizontalAlignment(SwingConstants.CENTER);
		lbValue.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

		card.add(lbTitle, BorderLayout.NORTH);
		card.add(lbValue, BorderLayout.CENTER);
		return card;
	}

	private void applyFilter() {
		if (sorter == null) return;
		String text = txtSearch != null ? txtSearch.getText().trim() : "";
		if (text.isEmpty()) {
			sorter.setRowFilter(null);
			return;
		}
		sorter.setRowFilter(RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(text), 0));
	}

	private String sanitize(String s) {
		if (s == null) return "";
		return s.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	private TimeRange getSelectedRange() {
		String selected = cboRange != null ? (String) cboRange.getSelectedItem() : "Ngày";
		if ("Tháng".equals(selected)) return TimeRange.thisMonth();
		if ("Năm".equals(selected)) return TimeRange.thisYear();
		return TimeRange.today();
	}

	private boolean withinRange(java.util.Date date, TimeRange range) {
		if (date == null || range == null) return false;
		return !date.before(range.getFrom()) && !date.after(range.getTo());
	}

	private void showError(String message) {
		JLabel errorLabel = new JLabel("  " + message);
		errorLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
		errorLabel.setForeground(Color.RED);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
		removeAll();
		setLayout(new BorderLayout());
		add(errorLabel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
}
