//package com.example.demo.jfreechart;
//
//import java.awt.BasicStroke;
//import java.awt.Font;
//import java.awt.Paint;
//import java.awt.Rectangle;
//
//import org.jfree.chart.LegendItem;
//import org.jfree.chart.LegendItemSource;
//import org.jfree.chart.block.Block;
//import org.jfree.chart.block.BlockContainer;
//import org.jfree.chart.block.BorderArrangement;
//import org.jfree.chart.block.CenterArrangement;
//import org.jfree.chart.block.LabelBlock;
//import org.jfree.chart.title.LegendGraphic;
//import org.jfree.chart.title.LegendItemBlockContainer;
//import org.jfree.chart.title.LegendTitle;
//
//public class CustomLegendTitle extends LegendTitle {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -5017893068151590925L;
//	
//	legendItemGraphicPadding;
//
//	public CustomLegendTitle(LegendItemSource source) {
//		super(source);
//	}
//
//	@Override
//	public Block createLegendItemBlock(LegendItem item) {
//		BlockContainer result;
//		// LegendGraphic lg = new LegendGraphic(item.getShape(), item.getFillPaint());
//		LegendGraphic lg = new LegendGraphic(item.getShape(), item.getFillPaint());
//		lg.setFillPaintTransformer(item.getFillPaintTransformer());
//		lg.setShapeFilled(item.isShapeFilled());
//		lg.setLine(item.getLine());
//		lg.setLine(new Rectangle(6, 4));
//		lg.setLineStroke(new BasicStroke(4f));
//		lg.setLinePaint(item.getLinePaint());
//		lg.setLineVisible(item.isLineVisible());
//		lg.setShapeVisible(item.isShapeVisible());
//		lg.setShapeOutlineVisible(item.isShapeOutlineVisible());
//		lg.setOutlinePaint(item.getOutlinePaint());
//		lg.setOutlineStroke(item.getOutlineStroke());
//		lg.setPadding(this.legendItemGraphicPadding);
//		LegendItemBlockContainer legendItem = new LegendItemBlockContainer(new BorderArrangement(), item.getDataset(),
//				item.getSeriesKey());
//		lg.setShapeAnchor(getLegendItemGraphicAnchor());
//		lg.setShapeLocation(getLegendItemGraphicLocation());
//		legendItem.add(lg, this.legendItemGraphicEdge);
//		Font textFont = item.getLabelFont();
//		if (textFont == null) {
//			textFont = this.itemFont;
//		}
//		Paint textPaint = item.getLabelPaint();
//		if (textPaint == null) {
//			textPaint = this.itemPaint;
//		}
//		LabelBlock labelBlock = new LabelBlock(item.getLabel(), textFont, textPaint);
//		labelBlock.setPadding(this.itemLabelPadding);
//		legendItem.add(labelBlock);
//		legendItem.setToolTipText(item.getToolTipText());
//		legendItem.setURLText(item.getURLText());
//		result = new BlockContainer(new CenterArrangement());
//		result.add(legendItem);
//		return result;
//	}
//}
