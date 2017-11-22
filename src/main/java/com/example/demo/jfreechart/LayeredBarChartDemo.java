package com.example.demo.jfreechart;

import java.awt.*;
import java.io.FileOutputStream;

import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.SortOrder;

@SuppressWarnings("serial")
public class LayeredBarChartDemo extends ApplicationFrame {

	public LayeredBarChartDemo(String s) {
		super(s);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	/**
	 * 生成显示图形用的数据集
	 * 
	 * @return
	 */
	private static CategoryDataset createDataset() {
		// 图例
		String s1 = "One";
		String s2 = "Two";
		String s3 = "Thr";
		// 横坐标分类
		String t1 = "C1";
		String t2 = "C2";
		String t3 = "C3";
		String t4 = "C4";
		String t5 = "C5";

		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		// addValue方法说明：参数1：柱状图显示的量
		// 参数2：柱子(或图例)标注(一个点显示一个柱子时，只需要一个)
		// 参数3：横坐标上显示的分类
		defaultcategorydataset.addValue(1D, s1, t1);
		defaultcategorydataset.addValue(3D, s1, t2);
		defaultcategorydataset.addValue(2D, s1, t3);
		defaultcategorydataset.addValue(5D, s1, t4);
		defaultcategorydataset.addValue(8D, s1, t5);
		defaultcategorydataset.addValue(5D, s2, t1);
		defaultcategorydataset.addValue(9D, s2, t2);
		defaultcategorydataset.addValue(6D, s2, t3);
		defaultcategorydataset.addValue(1D, s2, t4);
		defaultcategorydataset.addValue(5D, s2, t5);
		defaultcategorydataset.addValue(8D, s3, t1);
		defaultcategorydataset.addValue(1D, s3, t2);
		defaultcategorydataset.addValue(5D, s3, t3);
		defaultcategorydataset.addValue(2D, s3, t4);
		defaultcategorydataset.addValue(6D, s3, t5);
		return defaultcategorydataset;
	}

	/**
	 * 绘制图形，设置参数
	 * 
	 * @param categorydataset
	 * @return
	 */
	private static JFreeChart createChart(CategoryDataset categorydataset) {
		// 生成图形--参数说明
		// 参数1：标题
		// 参数2：分类
		// 参数3：值
		// 参数4：数据集
		// 参数5：柱子的显示方向
		// 参数6：是否显示图例
		// 参数7：暂不知道
		// 参数8：是否显示URL
		JFreeChart jfreechart = ChartFactory.createBarChart("BarImage", "CCC", "Value", categorydataset, PlotOrientation.VERTICAL, true, true, true);
		// 设置背景色
		jfreechart.setBackgroundPaint(Color.lightGray);
		// 设置图形对象
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		// 设置图形的背景色
		categoryplot.setBackgroundPaint(Color.yellow);
		// 设置图形上竖线的颜色
		categoryplot.setDomainGridlinePaint(Color.black);
		// 设置图形上竖线是否显示
		categoryplot.setDomainGridlinesVisible(true);
		// 设置图形上横线的颜色
		categoryplot.setRangeGridlinePaint(Color.black);
		// 获取横轴
		CategoryAxis categoryAxis = categoryplot.getDomainAxis();
		// 设置横坐标上的分类是否显示
		categoryAxis.setTickLabelsVisible(true);
		// 获取纵轴
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		// 设置纵轴的刻度线
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		LayeredBarRenderer layeredbarrenderer = new LayeredBarRenderer();
		layeredbarrenderer.setDrawBarOutline(false);
		// 设置柱子的边框是否显示
		categoryplot.setRenderer(layeredbarrenderer);
		// 设置柱子的排序方式
		categoryplot.setRowRenderingOrder(SortOrder.DESCENDING);
		GradientPaint gradientpaint1 = new GradientPaint(0.0F, 0.0F, Color.black, 0.0F, 0.0F, new Color(0, 0, 64));
		GradientPaint gradientpaint2 = new GradientPaint(0.0F, 0.0F, Color.green, 0.0F, 0.0F, new Color(0, 64, 0));
		GradientPaint gradientpaint3 = new GradientPaint(0.0F, 0.0F, Color.red, 0.0F, 0.0F, new Color(64, 0, 0));
		// 设置柱子的颜色
		layeredbarrenderer.setSeriesPaint(0, gradientpaint1);
		layeredbarrenderer.setSeriesPaint(1, gradientpaint2);
		layeredbarrenderer.setSeriesPaint(2, gradientpaint3);
		return jfreechart;
	}

	
	private static final String CHART_PATH = "E:\\file\\JFreeChart\\demo\\";
	public static JPanel createDemoPanel() {
		JFreeChart jfreechart = createChart(createDataset());
		FileOutputStream fos_jpg = null;
		String charName ="panel.png";
		try {
			String chartName = CHART_PATH + charName;
			fos_jpg = new FileOutputStream(chartName);

			// 将报表保存为png文件
			ChartUtilities.writeChartAsPNG(fos_jpg, jfreechart, 800, 510);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos_jpg.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ChartPanel(jfreechart);
	}

	public static void main(String args[]) {
		LayeredBarChartDemo layeredbarchartdemo1 = new LayeredBarChartDemo("LayeredBarChart");
		layeredbarchartdemo1.pack();
		RefineryUtilities.centerFrameOnScreen(layeredbarchartdemo1);
		layeredbarchartdemo1.setVisible(true);
	}
}