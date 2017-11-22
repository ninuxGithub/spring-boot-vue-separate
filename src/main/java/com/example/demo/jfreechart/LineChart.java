package com.example.demo.jfreechart;

import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;

/**
 * 折线图
 * 
 * @author huozhicheng@gmail.com
 * @date 2013-1-11上午11:02:16
 * @version 1.0
 */
public class LineChart {

	/**
	 * 生成折线图
	 * @param chartTitle 图的标题
	 * @param x          横轴标题
	 * @param y          纵轴标题
	 * @param dataset    数据集
	 * @return
	 */
	public static JFreeChart createLineChart(String chartTitle, String x, String y, CategoryDataset dataset) {
		
		// 构建一个chart
		JFreeChart chart = ChartFactory.createLineChart(
				chartTitle, 
				x, 
				y,
				dataset, 
				PlotOrientation.VERTICAL, 
				true, 
				true, 
				false);
		//字体清晰
		chart.setTextAntiAlias(false);
		// 设置背景颜色
		chart.setBackgroundPaint(Color.WHITE);

		// 设置图标题的字体
		Font font = new Font("隶书", Font.BOLD, 25);
		chart.getTitle().setFont(font);

		// 设置面板字体
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
		// 设置图示的字体
		chart.getLegend().setItemFont(labelFont);

		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		// x轴 // 分类轴网格是否可见
		categoryplot.setDomainGridlinesVisible(true);
		// y轴 //数据轴网格是否可见
		categoryplot.setRangeGridlinesVisible(true);
		categoryplot.setRangeGridlinePaint(Color.WHITE);// 虚线色彩
		categoryplot.setDomainGridlinePaint(Color.WHITE);// 虚线色彩
		categoryplot.setBackgroundPaint(Color.lightGray);// 折线图的背景颜色

		// 设置轴和面板之间的距离
		// categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));

		// 横轴 x
		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		domainAxis.setLabelFont(labelFont);// 轴标题
		domainAxis.setTickLabelFont(labelFont);// 轴数值
		// domainAxis.setLabelPaint(Color.BLUE);//轴标题的颜色
		// domainAxis.setTickLabelPaint(Color.BLUE);//轴数值的颜色

		// 横轴 lable 的位置 横轴上的 Lable 45度倾斜 DOWN_45
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);

		// 设置距离图片左端距离
		domainAxis.setLowerMargin(0.0);
		// 设置距离图片右端距离
		domainAxis.setUpperMargin(0.0);

		// 纵轴 y
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setLabelFont(labelFont);
		numberaxis.setTickLabelFont(labelFont);
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);

		// 获得renderer 注意这里是下嗍造型到lineandshaperenderer！！
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
				.getRenderer();
		lineandshaperenderer.setBaseShapesVisible(true); // series 点（即数据点）可见
		lineandshaperenderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见

		// 显示折点数据
		lineandshaperenderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		lineandshaperenderer.setBaseItemLabelsVisible(true);

		return chart;
	}
}
