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
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class JFreeChartDemoCombined2 {

	private static final String CHART_PATH = "E:\\file\\JFreeChart\\demo\\";

	public static void main(String[] args) {
		JFreeChartDemoCombined2 pm = new JFreeChartDemoCombined2();
		// 生成时序图
		pm.makeTimeSeriesChart();

		System.err.println("Jfree Chart Run Over...");
	}

	public void makeTimeSeriesChart() {

		JFreeChart chart = ChartFactory.createTimeSeriesChart("时序图折线图", "X/秒", "Y值", createDataset(), true, true, false);
		XYPlot plot = chart.getXYPlot();
		
		plot.setDomainGridlinePaint(Color.LIGHT_GRAY);  
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY); 
        
        // plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));  
        XYItemRenderer r = plot.getRenderer();  
        if (r instanceof XYLineAndShapeRenderer) {  
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;  
            renderer.setBaseShapesVisible(false);  
            renderer.setBaseShapesFilled(false);  
            renderer.setSeriesStroke(0, new BasicStroke(0.8f));//画笔
            renderer.setSeriesStroke(1, new BasicStroke(1f));//画笔
            renderer.setSeriesPaint(0, Color.red); //颜色 ---密集
            renderer.setSeriesPaint(1, Color.pink); //颜色--折线
        }  
        
		// 设置总的背景颜色
		chart.setBackgroundPaint(ChartColor.WHITE);
		// 设置标题颜色
		chart.getTitle().setPaint(ChartColor.BLACK);
		// 设置图的背景颜色
		plot.setBackgroundPaint(ChartColor.WHITE);
		
		// 设置表格线颜色
		// p.setRangeGridlinePaint(ChartColor.red);
		// 对X轴做操作
		DateAxis domainAxis = (DateAxis) plot.getDomainAxis(); //x轴设置
	    domainAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 2, new SimpleDateFormat("MM-dd")));
	    //DateAxis domainAxis = (DateAxis) plot.getDomainAxis();
	    domainAxis.setTickLabelFont(new Font("黑体", Font.PLAIN, 13));// 设置X轴坐标上的文字
	    domainAxis.setLabelFont(new Font("黑体", Font.PLAIN, 15)); // 设置X轴的标题文字
	    domainAxis.setVerticalTickLabels(true);

	    // 对Y轴做操作
	    ValueAxis rAxis = plot.getRangeAxis();
	    rAxis.setTickLabelFont(new Font("黑体", Font.PLAIN, 14)); // 设置Y轴坐标上的文字
	    rAxis.setLabelFont(new Font("黑体", Font.PLAIN, 15)); // 设置Y轴的标题文字
	    rAxis.setUpperMargin(0.25);// 设置最高的一个 Item 与图片顶端的距离
	    
	    //对Title 操作
	    chart.getTitle().setFont(new Font("黑体", Font.PLAIN, 15));
	    //对Legend 操作
	    chart.getLegend().setItemFont(new Font("黑体", Font.PLAIN, 15));
	    
	    // 设置总的背景颜色
	    chart.setBackgroundPaint(ChartColor.WHITE);
	    
	    // 设置图的背景颜色
	 	plot.setBackgroundPaint(ChartColor.WHITE);
	 	
	 	//设置网格不可见
	 	plot.setDomainGridlinesVisible(false);
	 	plot.setRangeGridlinesVisible(false);

		FileOutputStream outStream = null;
		String charName = "时序图.png";
		try {
			isChartPathExist(CHART_PATH);
			String chartName = CHART_PATH + charName;
			outStream = new FileOutputStream(chartName);

			// 将报表保存为png文件
			ChartUtilities.writeChartAsPNG(outStream, chart, 800, 510);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				outStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	
	@SuppressWarnings("deprecation")
	private static XYDataset createDataset() {  
        TimeSeriesCollection dataset = new TimeSeriesCollection();  
        
        TimeSeries timeSeries1 = new TimeSeries("A 时序图", Day.class);  
        TimeSeries timeSeries2 = new TimeSeries("B 折线图", Day.class);  
        TimeSeries timeSeries3 = new TimeSeries("C 折线图", Day.class);  
        TimeSeries timeSeries4 = new TimeSeries("D 折线图", Day.class);  
        TimeSeries timeSeries5 = new TimeSeries("E 折线图", Day.class);  
        
        
        Day current = new Day();

		double value = 100.0;
		
		for (int i = 0; i < 80; i++) {
			try {
				value = value + Math.random() - 0.5;
				timeSeries1.add(current, new Double(value));
				if(value>=100) {
					value = 100.0d;
				}
				
				if(i % 4 ==0 || i == 79) {
					timeSeries2.add(current, new Double(value));
				}
				if(i % 5 ==0 || i == 79) {
					timeSeries3.add(current, new Double(value));
				}
				if(i % 6 ==0 || i == 79) {
					timeSeries4.add(current, new Double(value));
				}
				if(i % 8 ==0 || i == 79) {
					timeSeries5.add(current, new Double(value));
				}
				current = (Day) current.next();
			} catch (SeriesException e) {
				e.printStackTrace();
			}
		}
		dataset.addSeries(timeSeries1);
		dataset.addSeries(timeSeries2);
		dataset.addSeries(timeSeries3);
		dataset.addSeries(timeSeries4);
		dataset.addSeries(timeSeries5);
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
