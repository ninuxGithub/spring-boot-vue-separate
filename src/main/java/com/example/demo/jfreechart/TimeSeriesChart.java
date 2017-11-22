package com.example.demo.jfreechart;
//package com.example.demo;
//
//import java.text.SimpleDateFormat;
//import java.util.Random;
//import java.util.Vector;
//
//import javax.swing.JFrame;
//import javax.swing.SwingUtilities;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.DateAxis;
//import org.jfree.chart.axis.DateTickUnit;
//import org.jfree.chart.axis.DateTickUnitType;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
//import org.jfree.data.time.TimeSeries;
//import org.jfree.data.time.TimeSeriesCollection;
//
//
//
//public class TimeSeriesChart {
//   public TimeSeriesChart() {
//   }
//
//   /**
//    * 创建数据集合
//    */
//   public TimeSeriesCollection createDataset() {
//      TimeSeriesCollection dataset = new TimeSeriesCollection();
//      String[] categories = { "dataA", "dataB" };
//
//      Random random = new Random();
//      for (int row = 0; row < categories.length; row++) {
//         Vector<Object[]> dateValues = new Vector<Object[]>();
//         for (int i = 0; i < 200; i++) {
//            String date = (2000 + i) + "-0" + i + "-21";
//            System.out.println(date);
//            Object[] dateValue = { date, random.nextInt(100)+i/20*20 };
//            dateValues.add(dateValue);
//
//         }
//         TimeSeries timeSeries = ChartUtils.createTimeseries(categories[row], dateValues);
//         dataset.addSeries(timeSeries);
//      }
//      return dataset;
//   }
//
//   public ChartPanel createChart() {
//      // 2：创建Chart[创建不同图形]
//      TimeSeriesCollection dataset = createDataset();
//      JFreeChart chart = ChartFactory.createTimeSeriesChart("哎哎哎", "", "", dataset, false, false, false);
//      // 3:设置抗锯齿，防止字体显示不清楚
//      ChartUtils.setAntiAlias(chart);// 抗锯齿
//      // 4:对柱子进行渲染[创建不同图形]
//      ChartUtils.setTimeSeriesRender(chart.getPlot(), false, true);
//      // 5:对其他部分进行渲染
//      XYPlot xyplot = (XYPlot) chart.getPlot();
//      ChartUtils.setXY_XAixs(xyplot);
//      ChartUtils.setXY_YAixs(xyplot);
//      // 日期X坐标轴
//      DateAxis domainAxis = (DateAxis) xyplot.getDomainAxis();
//      domainAxis.setAutoTickUnitSelection(false);
//      DateTickUnit dateTickUnit = null;
//      if (dataset.getItemCount(0) < 16) {
//         //刻度单位月,半年为间隔
//         dateTickUnit = new DateTickUnit(DateTickUnitType.MONTH, 6, new SimpleDateFormat("yyyy-MM")); // 第二个参数是时间轴间距
//      } else {// 数据过多,不显示数据
//         XYLineAndShapeRenderer xyRenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
//         xyRenderer.setBaseItemLabelsVisible(false);
//         //设置曲线是否显示数据节点
//         xyRenderer.setBaseShapesVisible(false);
//         dateTickUnit = new DateTickUnit(DateTickUnitType.YEAR, 1, new SimpleDateFormat("yyyy")); // 第二个参数是时间轴间距
//      }
//      // 设置时间单位
//      domainAxis.setTickUnit(dateTickUnit);
////    ChartUtils.setLegendEmptyBorder(chart);
//      // 设置图例位置
//      // 6:使用chartPanel接收
//      ChartPanel chartPanel = new ChartPanel(chart);
//      chartPanel.setMouseZoomable(true, false);
//      return chartPanel;
//   }
//
//   public static void main(String[] args) {
//      final JFrame frame = new JFrame();
//      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      frame.setSize(1024, 420);
//      frame.setLocationRelativeTo(null);
//
//      SwingUtilities.invokeLater(new Runnable() {
//         @Override
//         public void run() {
//            // 创建图形
//            ChartPanel chartPanel = new TimeSeriesChart().createChart();
//            frame.getContentPane().add(chartPanel);
//            frame.setVisible(true);
//         }
//      });
//
//   }
//
//}
