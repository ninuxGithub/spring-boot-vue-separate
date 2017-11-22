package com.example.demo.jfreechart;
import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

public class CreateJFreeChart {

	/**
	 * 生成折线图
	 * @param chartTitle 图片标题
	 * @param xTitle x轴标题
	 * @param yTitle y轴标题
	 * @param xyDataset 数据集
	 */
	public JFreeChart makeLineAndShapeChart(String chartTitle, String xTitle, String yTitle, CategoryDataset  xyDataset){
		//构建一个chart
		JFreeChart chart = ChartFactory.createLineChart(chartTitle, xTitle, yTitle, xyDataset, PlotOrientation.VERTICAL, true, true, false);
		chart.setTextAntiAlias(false);
		//设置背景颜色
		chart.setBackgroundPaint(Color.WHITE);
		//配置字体  
        Font xfont = new Font("宋体",Font.TRUETYPE_FONT,12) ;// X轴字体
        Font yfont = new Font("SansSerif",Font.TRUETYPE_FONT,12) ;// Y轴字体
        Font legendFont = new Font("宋体",Font.PLAIN,12) ;//图例标题字体
        Font titleFont = new Font("隶书", Font.BOLD , 25) ; // 图片标题  
        
		//设置图标题title的字体
		chart.getTitle().setFont(titleFont);
		
		//设置图例的字体
		chart.getLegend().setItemFont(legendFont);
		//RectangleEdge rectangleEdge = new RectangleEdge("adf");
		
		//chart.getLegend().setPosition(position);
		
		
		//图形的绘制结构对象  
		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		// x轴 分类轴网格是否可见
		categoryplot.setDomainGridlinesVisible(true);
		// y轴  数据轴网格是否可见
		categoryplot.setRangeGridlinesVisible(true);
		// 虚线色彩
		categoryplot.setRangeGridlinePaint(Color.WHITE);
		// 虚线色彩
		categoryplot.setDomainGridlinePaint(Color.WHITE);
		//折线图的背景颜色
		categoryplot.setBackgroundPaint(Color.lightGray);
		// 设置轴和面板之间的距离
		//categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));

		/********************** 横轴 x *****************/
		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		domainAxis.setLabelFont(xfont);// 轴标题
		domainAxis.setTickLabelFont(xfont);// 轴数值
		//domainAxis.setLabelPaint(Color.BLUE);//轴标题的颜色
		//domainAxis.setTickLabelPaint(Color.BLUE);//轴数值的颜色

		//横轴 lable 的位置   横轴上的 Lable 45度倾斜 DOWN_45
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		// 设置距离图片左端距离
		domainAxis.setLowerMargin(0.0);
		// 设置距离图片右端距离
		domainAxis.setUpperMargin(0.0);

		/********************** 纵轴 y *****************/
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();//ValueAxis
		numberaxis.setLabelFont(yfont);
		numberaxis.setTickLabelFont(yfont);
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);

		// 获得renderer 注意这里是下嗍造型到lineandshaperenderer！！
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot.getRenderer();
		lineandshaperenderer.setBaseShapesVisible(true); // series 点（即数据点）可见
		lineandshaperenderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见

		// 显示折点数据
		lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		lineandshaperenderer.setBaseItemLabelsVisible(true);
		
		return chart;
	}
	public static void main(String args[]){
		double [][] data = new double[][]{{533,214,614,542,724},{462,836,345,854,224},{245,614,751,332,456}};
		String[] rowKeys = {"宝马","奔驰","大众"};
		String[] columnKeys = {"北京", "上海", "广州", "成都", "深圳"};
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);
		try {
			FileOutputStream fos = new FileOutputStream("e:/test/abc.png");
			ChartUtilities.writeChartAsPNG(fos, new CreateJFreeChart().makeLineAndShapeChart("各地汽车销量", "城市", "销量", dataset), 800, 600);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
