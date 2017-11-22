package com.example.demo.jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.text.DecimalFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 柱状图
 * 
 * @author huozhicheng@gmail.com
 * @date 2013-1-8下午2:15:14
 * @version 1.0
 */
public class Barchart {
	
	/**
	 * 创建柱状图
	 * @param chartTitle 图表标题
	 * @param xName      x轴标题
	 * @param yName      y轴标题
	 * @param dataset    数据集
	 * @return
	 */
	public static JFreeChart createChart(String chartTitle, String xName,String yName, CategoryDataset dataset) {
		/*
		 * createBarChart的参数分别为：
		 * 标题，横坐标标题，纵坐标标题，数据集，图标方向（水平、垂直），是否显示图例，是否显示tooltips，是否urls
		 */
		JFreeChart chart = ChartFactory.createBarChart(chartTitle, xName, yName,
				dataset, PlotOrientation.VERTICAL, true, true, false);
		/*
		 * VALUE_TEXT_ANTIALIAS_OFF表示将文字的抗锯齿关闭,
		 * 使用的关闭抗锯齿后，字体尽量选择12到14号的宋体字,这样文字最清晰好看
		 */
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		// 背景色
		chart.setBackgroundPaint(Color.white);
		// 设置标题字体
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 14));
		// 图例背景色
		chart.getLegend().setBackgroundPaint(new Color(110, 182, 229));
		// 图例字体
		chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));

		CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();
		// 设置纵虚线可见
		//categoryPlot.setDomainGridlinesVisible(true);
		// 虚线色彩
		//categoryPlot.setDomainGridlinePaint(Color.black);
		// 设置横虚线可见
		categoryPlot.setRangeGridlinesVisible(true);
		// 虚线色彩
		categoryPlot.setRangeGridlinePaint(Color.black);
		// 设置柱的透明度
		categoryPlot.setForegroundAlpha(1.0f);
		// 设置柱图背景色（注意，系统取色的时候要使用16位的模式来查看颜色编码，这样比较准确）
		categoryPlot.setBackgroundPaint(new Color(110, 182, 229));

		/*
		 * categoryPlot.setRangeCrosshairVisible(true);
		 * categoryPlot.setRangeCrosshairPaint(Color.blue);
		 */

		// 纵坐标--范围轴
		NumberAxis numberAxis = (NumberAxis) categoryPlot.getRangeAxis();
		// 纵坐标y轴坐标字体
		numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
		// 纵坐标y轴标题字体
		numberAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
		// 设置最高的一个 Item 与图片顶端的距离
		// numberAxis.setUpperMargin(0.5);
		// 设置最低的一个 Item 与图片底端的距离
		// numberAxis.setLowerMargin(0.5);
		// 设置刻度单位 为Integer
		numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// 横坐标--类别轴、域
		CategoryAxis categoryAxis = categoryPlot.getDomainAxis();
		// 横坐标x轴坐标字体
		categoryAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
		// 横坐标x轴标题字体
		categoryAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
		// 类别轴的位置，倾斜度
		// categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.5235987755982988D));
		// categoryAxis.setMaximumCategoryLabelWidthRatio(0.6f);// 横轴上的 Lable
		// 是否完整显示
		// 设置距离图片左端距离
		categoryAxis.setLowerMargin(0.1D);
		// 设置距离图片右端距离
		categoryAxis.setUpperMargin(0.1D);

		// 渲染 - 中间的部分
		BarRenderer barRenderer = (BarRenderer) categoryPlot.getRenderer();
		// 设置柱子宽度
		barRenderer.setMaximumBarWidth(0.05);
		// 设置柱子高度
		barRenderer.setMinimumBarLength(0.2);
		// 设置柱子边框颜色
		barRenderer.setBaseOutlinePaint(Color.BLACK);
		// 设置柱子边框可见
		barRenderer.setDrawBarOutline(true);
		// 设置柱的颜色
		barRenderer.setSeriesPaint(0, new Color(0, 255, 0));
		barRenderer.setSeriesPaint(1, new Color(0, 0, 255));
		barRenderer.setSeriesPaint(2, new Color(255, 0, 0));
		// 设置每个柱之间距离
		barRenderer.setItemMargin(0.2D);
		// 显示每个柱的数值，并修改该数值的字体属性
		barRenderer.setIncludeBaseInRange(true);
		barRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		barRenderer.setBaseItemLabelsVisible(true);

		return chart;
	}

	/**
	 * 柱状图数据集
	 * 
	 * @return
	 */
	public static CategoryDataset createDataset() {
		String str1 = "Java EE开发";
		String str2 = "IOS开发";
		String str3 = "Android开发";
		String str4 = "1月";
		String str5 = "2月";
		String str6 = "3月";
		String str7 = "4月";
		String str8 = "5月";

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.addValue(1.0D, str1, str4);
		dataset.addValue(4.0D, str1, str5);
		dataset.addValue(3.0D, str1, str6);
		dataset.addValue(5.0D, str1, str7);
		dataset.addValue(5.0D, str1, str8);

		dataset.addValue(5.0D, str2, str4);
		dataset.addValue(7.0D, str2, str5);
		dataset.addValue(6.0D, str2, str6);
		dataset.addValue(8.0D, str2, str7);
		dataset.addValue(4.0D, str2, str8);

		dataset.addValue(4.0D, str3, str4);
		dataset.addValue(3.0D, str3, str5);
		dataset.addValue(2.0D, str3, str6);
		dataset.addValue(3.0D, str3, str7);
		dataset.addValue(6.0D, str3, str8);
		return dataset;
	}

	/**
	 * 生成堆栈柱状图
	 * @param chartTitle  图表标题
	 * @param xName		  x轴名称
	 * @param yName		  y轴名称
	 * @param dataset	     数据集
	 * @return
	 */
	public static JFreeChart createStackedBarChart(String chartTitle, String xName,String yName, CategoryDataset dataset) {

		//JFreeChart对象
		JFreeChart chart = ChartFactory.createStackedBarChart(
				chartTitle, //图表标题
				xName, //目录轴的显示标签
				yName, //数值轴的显示标签
				dataset, //数据集
				PlotOrientation.VERTICAL, //图表方向：水平、垂直
				true, //是否显示图例
				false, //是否生成工具
				false //是否生成URL链接
				);
		//图例字体清晰
		chart.setTextAntiAlias(false);
		//图表背景色
		chart.setBackgroundPaint(Color.WHITE);
		//建立图表标题字体
		Font titleFont = new Font("宋体", Font.BOLD, 14);
		//建立图表图例字体
		Font legendFont = new Font("宋体", Font.PLAIN, 12);
		//建立x,y轴坐标的字体
		Font axisFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
		//设置图表的标题字体
		chart.getTitle().setFont(titleFont);
		//设置图表的图例字体
		chart.getLegend().setItemFont(legendFont);

		//Plot对象是图形的绘制结构对象
		CategoryPlot plot = chart.getCategoryPlot();

		//设置横虚线可见
		plot.setRangeGridlinesVisible(true);
		//虚线色彩
		plot.setRangeGridlinePaint(Color.black);
		//设置柱的透明度(如果是3D的必须设置才能达到立体效果，如果是2D的设置则使颜色变淡)
		//plot.setForegroundAlpha(0.65f);
		
		/** ----------  RangeAxis (范围轴，相当于 y 轴)---------- **/
		//数据轴精度
		NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
		numberAxis.setLabelFont(axisFont);//轴标题字体
		numberAxis.setTickLabelFont(axisFont);//轴数值字体
		//设置最高的一个 Item 与图片顶端的距离
		numberAxis.setUpperMargin(0.15);
		//设置最低的一个 Item 与图片底端的距离
		numberAxis.setLowerMargin(0.15);
		//设置最大值是1
		numberAxis.setUpperBound(1);
		//设置数据轴坐标从0开始
		//numberAxis.setAutoRangeIncludesZero(true);
		//数据显示格式是百分比
		DecimalFormat df = new DecimalFormat("0.00%");
		//数据轴数据标签的显示格式
		numberAxis.setNumberFormatOverride(df); 
		
		/** ----------  DomainAxis (区域轴，相当于 x 轴)---------- **/
		CategoryAxis domainAxis = (CategoryAxis)plot.getDomainAxis();
		domainAxis.setLabelFont(axisFont);//轴标题字体
		domainAxis.setTickLabelFont(axisFont);//轴数值字体

		// x轴坐标太长，建议设置倾斜，如下两种方式选其一，两种效果相同
		// 倾斜（1）横轴上的 Lable 45度倾斜
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		// 倾斜（2）Lable（Math.PI 3.0）度倾斜
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions
		// .createUpRotationLabelPositions(Math.PI / 3.0));
		
		//横轴上的 Lable 是否完整显示
		domainAxis.setMaximumCategoryLabelWidthRatio(1f);
		
		/** ----------  Renderer (图形绘制单元)---------- **/
		//Renderer 对象是图形的绘制单元
		StackedBarRenderer renderer = new StackedBarRenderer();
		//设置柱子宽度
		renderer.setMaximumBarWidth(0.05D);
		//设置柱子高度
		renderer.setMinimumBarLength(0.1D);
		//设置柱的边框颜色
		renderer.setBaseOutlinePaint(Color.BLACK);
		//设置柱的边框可见
		renderer.setDrawBarOutline(true);
		//设置柱的颜色(可设定也可默认)
		renderer.setSeriesPaint(0, new Color(0, 255, 0));
		renderer.setSeriesPaint(1, new Color(0, 0, 255));
		//设置每个平行柱的之间距离
		renderer.setItemMargin(0.4);
		
		// 显示每个柱的数值，并修改该数值的字体属性
		renderer.setIncludeBaseInRange(true);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		
		plot.setRenderer(renderer);
		
		return chart;
	}
}
