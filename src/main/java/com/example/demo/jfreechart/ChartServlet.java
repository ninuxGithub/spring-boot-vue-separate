package com.example.demo.jfreechart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

/**
 * JFreeChart Servlet
 * @author huozhicheng@gmail.com
 * @date 2013-1-10下午5:34:22
 * @version 1.0
 */
public class ChartServlet extends HttpServlet{

	private static final long serialVersionUID = -3066626067997142035L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JFreeChart chart = null;
		if(request.getParameter("type").equals("barchart")){
			chart = Barchart.createChart("公司招聘人数","月份","人数",Barchart.createDataset());
		}else if(request.getParameter("type").equals("piechart")){
			double[] data =
				{ 9, 91 };
				String[] keys =
				{ "失败率", "成功率" };
			chart = Piechart.createPieChart3D("饼状图",Piechart.getDataPieSetByUtil(data, keys),keys);
		}else if(request.getParameter("type").equals("linechart")){
			double[][] data = new double[][]
				{
				{ 672, 766, 223, 540, 126 },
				{ 325, 521, 210, 340, 106 },
				{ 332, 256, 523, 240, 526 } };
			String[] rowKeys =
				{ "苹果", "梨子", "葡萄" };
				String[] columnKeys =
				{ "北京", "上海", "广州", "成都", "深圳" };
			CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);
			chart = LineChart.createLineChart("折线图", "x轴", "y轴", dataset);
		}else if(request.getParameter("type").equals("stackedbarchart")){
			double[][] data = new double[][]
				{
				{ 0.21, 0.66, 0.23, 0.40, 0.26 },
				{ 0.25, 0.21, 0.10, 0.40, 0.16 } };
			String[] rowKeys = { "苹果", "梨子" };
			String[] columnKeys = { "北京", "上海", "广州", "成都", "深圳" };
			
			CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);
			chart = Barchart.createStackedBarChart("柱状图", "x坐标", "y坐标", dataset);
		}
		//ServletUtilities是面向web开发的工具类，返回一个字符串文件名,文件名自动生成，生成好的图片会自动放在服务器（tomcat）的临时文件下（temp）
		String filename = ServletUtilities.saveChartAsPNG(chart, 800, 400, null, request.getSession());
		//根据文件名去临时目录下寻找该图片，这里的/DisplayChart路径要与配置文件里用户自定义的<url-pattern>一致
		String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;
		request.setAttribute("imgurl", graphURL);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	
}
