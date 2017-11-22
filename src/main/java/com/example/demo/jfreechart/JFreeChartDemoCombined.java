package com.example.demo.jfreechart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class JFreeChartDemoCombined {

	private static final String CHART_PATH = "E:\\file\\JFreeChart\\demo\\";

	public static void main(String[] args) {
		JFreeChartDemoCombined pm = new JFreeChartDemoCombined();
		// 生成时序图
		pm.makeTimeSeriesChart();

		System.err.println("Jfree Chart Run Over...");
	}

	@SuppressWarnings("deprecation")
	public void makeTimeSeriesChart() {

		final XYDataset dataset = timeSeriesDataSet();
		final XYDataset timeSeriesTwo = timeSeriesDataSet2();
		JFreeChart chart = ChartFactory.createTimeSeriesChart("时序图", "秒", "值", dataset, true, true, false);
		XYPlot plot = chart.getXYPlot();
		/**
		// 解决乱码问题
		// 1. 图形标题文字设置
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(new Font("宋体", Font.BOLD, 20));

		// 2. 图形X轴坐标文字的设置
		XYPlot plot = chart.getXYPlot();
		ValueAxis axis = plot.getDomainAxis();
		axis.setLabelFont(new Font("宋体", Font.BOLD, 22)); // 设置X轴坐标上标题的文字
		axis.setTickLabelFont(new Font("宋体", Font.BOLD, 15)); // 设置X轴坐标上的文字

		// 2. 图形Y轴坐标文字的设置
		ValueAxis valueAxis = plot.getRangeAxis();
		valueAxis.setLabelFont(new Font("宋体", Font.BOLD, 15)); // 设置Y轴坐标上标题的文字
		valueAxis.setTickLabelFont(new Font("sans-serif", Font.BOLD, 12));// 设置Y轴坐标上的文字
		*/
		// 设置总的背景颜色
		chart.setBackgroundPaint(ChartColor.WHITE);
		// 设置标题颜色
		chart.getTitle().setPaint(ChartColor.blue);
		// 获得图表对象
		// CategoryPlot p = chart.getCategoryPlot();
		// 设置图的背景颜色
		plot.setBackgroundPaint(ChartColor.WHITE);
		
		// 设置表格线颜色
		// p.setRangeGridlinePaint(ChartColor.red);
		
		
		/**添加折线图*/
		NumberAxis numberAxis = new NumberAxis("时序图横轴");
		numberAxis.setLabelFont(new Font("黑体", Font.PLAIN, 15));
		plot.setRangeAxis(1, numberAxis);// 设置该新轴对应索引
	    plot.setDataset(0, dataset);// 设置数据集索引
	    plot.setDataset(1, timeSeriesTwo);// 设置数据集索引
	    plot.mapDatasetToRangeAxis(1, 1);// 将该索引映射到axis
	    

//	    XYBarRenderer xyBarRender = new XYBarRenderer();
//	    xyBarRender.setMargin(0.7);
//
//	    xyBarRender.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.INSIDE10, TextAnchor.BASELINE_LEFT));
//	    xyBarRender.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
//	    xyBarRender.setBaseItemLabelFont(new Font("Dialog", 1, 11));
//	    xyBarRender.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
//	                                            StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
//	                                            new SimpleDateFormat("yyyy-MM-dd"), new DecimalFormat(
//	                                                    "0.000")));// 鼠标移上去显示数据
//	    plot.setRenderer(1, xyBarRender);


	    TextTitle otherTitle = new TextTitle("title");// 设置副标题
	    chart.addSubtitle(otherTitle);// 设置副标题
	    // 设置乱码字体
	    TextTitle textTitle = chart.getTitle(); // 设置标题字体
	    textTitle.setFont(new Font("黑体", Font.PLAIN, 14));
	    chart.getLegend().setItemFont(new Font("黑体", Font.PLAIN, 14)); // 图文字体

	    // 设置日期显示格式
	    DateAxis axis = (DateAxis) plot.getDomainAxis();
	    axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM"));
	    
//	    XYItemRenderer render = plot.getRenderer();
//	    render.setBaseShapesVisible(false);  
//	    render.setBaseShapesFilled(false); 
	    
	    // 设置墙体属性
//	    plot.setBackgroundPaint(Color.decode("#D3D3D3")); // 设置墙体背景颜色
//	    plot.setDomainGridlinesVisible(true);
//	    plot.setDomainGridlinePaint(Color.black);// 虚线色彩 y轴
//	    plot.setRangeGridlinesVisible(true);
//	    plot.setRangeGridlinePaint(Color.black);// 虚线色彩 x轴
	    // 对X轴做操作
	    DateAxis domainAxis = (DateAxis) plot.getDomainAxis();
	    domainAxis.setTickLabelFont(new Font("黑体", Font.PLAIN, 13));// 设置X轴坐标上的文字
	    domainAxis.setLabelFont(new Font("黑体", Font.PLAIN, 15)); // 设置X轴的标题文字
	    // domainAxis.setUpperMargin(0.05); // 设置距离图片左端距离
	    // domainAxis.setLowerMargin(0.05); // 设置距离图片右端距离

//	    // 对Y轴做操作
//	    ValueAxis rAxis = plot.getRangeAxis();
//	    rAxis.setTickLabelFont(new Font("黑体", Font.PLAIN, 14)); // 设置Y轴坐标上的文字
//	    rAxis.setLabelFont(new Font("黑体", Font.PLAIN, 15)); // 设置Y轴的标题文字
//	    rAxis.setUpperMargin(0.25);// 设置最高的一个 Item 与图片顶端的距离
//
//	    XYLineAndShapeRenderer lineRender = (XYLineAndShapeRenderer) plot.getRenderer();
//	    lineRender.setBaseShapesVisible(true); // series 点（即数据点）可见
//	    lineRender.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见
//	    lineRender.setBaseItemLabelsVisible(true);// 设置数据点的数据是否显示
//	    lineandshaperenderer
//	    .setBasePositiveItemLabelPosition(new ItemLabelPosition(
//	                                          ItemLabelAnchor.INSIDE10, TextAnchor.BASELINE_LEFT));
//	    lineandshaperenderer
//	    .setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
//	    lineandshaperenderer.setBaseItemLabelFont(new Font("Dialog", 1, 11));
//	    lineandshaperenderer .setBaseToolTipGenerator(new StandardXYToolTipGenerator(
//	                                 StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
//	                                 new SimpleDateFormat("yyyy-MM"), new DecimalFormat(
//	                                     "0.000")));// 鼠标移上去显示数据
	    // 数据轴精度从0开始
//	    NumberAxis na = (NumberAxis) plot.getRangeAxis();
//	    na.setAutoRangeIncludesZero(true);
//	    plot.setRenderer(1, lineRender);
//	    plot.setRenderer(1, lineRender);
//	    lineRender.setSeriesPaint(1, Color.BLUE); // 设置第一条折现的颜色，以此类推。
//	    lineRender.setSeriesPaint(0, Color.green); // 设置第一条折现的颜色，以此类推。
//	    lineRender.setSeriesStroke(1, new BasicStroke(3));
//	    lineRender.setSeriesStroke(0, new BasicStroke(3));
//	    lineRender.setBaseShapesVisible(true);  
//	    lineRender.setBaseShapesFilled(true); 
//	    lineRender.setBaseLinesVisible(true);
//	    lineRender.setLinesVisible(true);
	    /**添加折线图*/

		// 生成legend 标题
//		LegendTitle legendtitle = new LegendTitle(chart.getPlot());
//		BlockContainer blockcontainer = new BlockContainer(new BorderArrangement());
//		blockcontainer.setBorder(new BlockBorder(1.0D, 1.0D, 1.0D, 1.0D));
//		BlockContainer blockcontainer1 = legendtitle.getItemContainer();
//		blockcontainer1.setPadding(2D, 10D, 5D, 2D);
//		blockcontainer.add(blockcontainer1);
//		legendtitle.setWrapper(blockcontainer);
//		legendtitle.setPosition(RectangleEdge.BOTTOM);
//		legendtitle.setHorizontalAlignment(HorizontalAlignment.CENTER);
//		chart.addSubtitle(legendtitle);
//		chart.getLegend().setVisible(false);// 不显示系统生成的解释性语句

		FileOutputStream fos_jpg = null;
		String charName = "时序图.png";
		try {
			isChartPathExist(CHART_PATH);
			String chartName = CHART_PATH + charName;
			fos_jpg = new FileOutputStream(chartName);

			// 将报表保存为png文件
			ChartUtilities.writeChartAsPNG(fos_jpg, chart, 800, 510);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos_jpg.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private XYDataset timeSeriesDataSet2() {
		final TimeSeries series = new TimeSeries("Random Second Series -Line");

		Second current = new Second();

		double value = 100.0;
		for (int i = 0; i < 400; i++) {
			try {
				if(i % 60 == 0) {
					
					value = (value + Math.random() - 0.5) * 0.8;
					//System.out.println(value);
					series.add(current, new Double(value));
				}else {
					series.add(current, null);
					
				}
				current = (Second) current.next();
			} catch (SeriesException e) {
				System.err.println("Error adding to series");
			}
		}

		final XYDataset dataset = (XYDataset) new TimeSeriesCollection(series);
		return dataset;
	}

	private XYDataset timeSeriesDataSet() {
		final TimeSeries series = new TimeSeries("Random Second Series");

		Second current = new Second();

		double value = 100.0;
		for (int i = 0; i < 400; i++) {
			try {
				value = value + Math.random() - 0.5;
				series.add(current, new Double(value));
				current = (Second) current.next();
			} catch (SeriesException e) {
				System.err.println("Error adding to series");
			}
		}

		final XYDataset dataset = (XYDataset) new TimeSeriesCollection(series);
		return dataset;
	}



	/**
	 * 判断文件夹是否存在，如果不存在则新建
	 * 
	 * @param chartPath
	 */
	private void isChartPathExist(String chartPath) {
		File file = new File(chartPath);
		if (!file.exists()) {
			file.mkdirs();
			// log.info("CHART_PATH="+CHART_PATH+"create.");
		}
	}


}
