import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

public class Plot extends JFrame {

	private double xAxis[][];
	private double yAxis[][];

	private int pointer;
	private ArrayList<double[]> xData;
	private ArrayList<MathematicalExpression> interData;
	private ChartPanel chartPanel;


	/**
	 * Constructor for plot bisection method
	 * 
	 * @param function
	 * @param plotData
	 */
	public Plot(final MathematicalExpression function,
			final ArrayList<double[]> plotData) {
		super("plot b2a ya ged3an");
		pointer = 1;
		xData = plotData;

		XYDataset data = setDataSet(xData.get(0)[0], xData.get(0)[1],
				xData.get(0)[2], function);
		final JFreeChart chart = createChart(data);
		
		// we hide legend and we replace it with text field
		chart.getLegend().visible = false ;
		defaultRange(chart);
		ChartPanel chartPanel = new ChartPanel(chart);
		final JButton next = new JButton("next");
		final JButton prev = new JButton("previous");
		prev.setEnabled(false);

	   // action for previous button
	   next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				XYPlot plot = (XYPlot) chart.getPlot();
				plot.setDataset(setDataSet(xData.get(pointer)[0], xData.get(pointer)[1], xData.get(pointer)[2], function));
				pointer++ ;
				if (pointer == plotData.size())
					next.setEnabled(false);
				if (pointer > 1)
					prev.setEnabled(true);
				
			}
		});

		// action for previous button
		prev.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pointer = pointer - 2 ;
				XYPlot plot = (XYPlot) chart.getPlot();
				plot.setDataset(setDataSet(xData.get(pointer)[0], xData.get(pointer)[1], xData.get(pointer)[2], function));
				if (pointer != plotData.size())
					next.setEnabled(true);
				if (pointer < 1)
					prev.setEnabled(false);
				pointer ++ ;
			}
		});

		JPanel content = new JPanel(new BorderLayout());
		content.add(chartPanel);
		content.add(next, BorderLayout.SOUTH);
		content.add(prev, BorderLayout.EAST);

		chartPanel.setPreferredSize(new java.awt.Dimension(500, 700));
		setContentPane(content);
		setVisible(true);

	}

	/**
	 * Constructor for interpolation
	 * @param function
	 */
	public Plot(final ArrayList<MathematicalExpression> function , final ArrayList<double []> data){
	      
		    interData = function ;
			XYDataset dataset = setDataForInterpolation(interData.get(0),data);
			pointer = 1;
			final JFreeChart chart = createChart(dataset);
			
			chart.getLegend().visible = false ;
			defaultRange(chart);
			final ChartPanel chartPanel = new ChartPanel(chart);
			final JButton next = new JButton("next");
			final JButton prev = new JButton("previous");
			final JButton zoomIn = new JButton("zoom In");

			prev.setEnabled(false);
			
			 next.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						XYPlot plot = (XYPlot) chart.getPlot();
						plot.setDataset(setDataForInterpolation((interData.get(pointer)) ,data));
						pointer ++ ;
						if (pointer == interData.size())
							next.setEnabled(false);
						if (pointer > 1)
							prev.setEnabled(true);
					}
				});

				// action for previous button
				prev.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						pointer = pointer - 2 ;
						XYPlot plot = (XYPlot) chart.getPlot();
						plot.setDataset(setDataForInterpolation((interData.get(pointer)) ,data));
						if (pointer != interData.size())
							next.setEnabled(true);
						if (pointer < 1)
							prev.setEnabled(false);
						pointer ++ ;
					}
				});
				
				// action for zoom in 
				zoomIn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
				        chartPanel.zoomInBoth(2, 2);
					}
				});

				JPanel content = new JPanel(new BorderLayout());
				content.add(chartPanel);
				content.add(next, BorderLayout.SOUTH);
				content.add(prev, BorderLayout.EAST);
				content.add(zoomIn, BorderLayout.NORTH);


				chartPanel.setPreferredSize(new java.awt.Dimension(500, 700));
				setContentPane(content);
				setVisible(true);
	}
	
	/**
	 * Return dataset for interpolation
	 * @param function
	 * @return
	 */
	private XYDataset setDataForInterpolation (MathematicalExpression function , 
			ArrayList<double []> data){
		
		xAxis = new double[][] { { -250, 250 }, { 0, 0 } };
		yAxis = new double[][] { { 0, 0 }, { -250, 250 } };
		
		double[][] func = new double[2][100];
	    // X values
	      for (int i=0; i<100; i++)
	      {
	         func[0][i] = (i-50) / 9.0;
	      }
	      // Y values
	      for (int i=0; i<100; i++)
	      {
	         func[1][i] = function.evaluateExpression(func[0][i]);
	      }
	      
	      DefaultXYDataset dataset = new DefaultXYDataset();
			
			dataset.addSeries("x axis", xAxis);
			dataset.addSeries("y axis", yAxis);
			dataset.addSeries("function", func);
			
			for (int i = 0 ; i <= pointer+1 ; i++ ){
				dataset.addSeries("a"+i,
						line45(data.get(i)[0], data.get(i)[1]));
				dataset.addSeries("b"+i,
						line135(data.get(i)[0], data.get(i)[1]));
			}
			
			return dataset ;
	}
	
	
	/**
	 * Return the data set to the chart
	 * 
	 * @param xOfLeftLine
	 * @param xOfRightLine
	 * @param xOfMiddle
	 * @param function
	 * @return
	 */
	private XYDataset setDataSet(double xOfLeftLine, double xOfRightLine,
			double xOfMiddle, MathematicalExpression function) {

		double minValue = Math.min(xOfRightLine, xOfLeftLine);
		double maxValue = Math.max(xOfRightLine, xOfLeftLine);

		double[][] func = new double[2][500];
		xAxis = new double[][] { { -250, 250 }, { 0, 0 } };
		yAxis = new double[][] { { 0, 0 }, { -250, 250 } };
		double[][] leftLine = new double[][] { { minValue, minValue },
				{ 0, function.evaluateExpression(minValue) } };
		double[][] rightLine = new double[][] { { maxValue, maxValue },
				{ 0, function.evaluateExpression(maxValue) } };
		double[][] middleLine = new double[][] { { xOfMiddle, xOfMiddle },
				{ 0, function.evaluateExpression(xOfMiddle) } };

		// fill func array
		for (int i = 0; i < 500; i++) {
			func[0][i] = (i - 250) / 9.0;
		}
		for (int i = 0; i < 500; i++) {
			func[1][i] = function.evaluateExpression(func[0][i]);
		}

		DefaultXYDataset dataset = new DefaultXYDataset();
		
		dataset.addSeries("x axis", xAxis);
		dataset.addSeries("y axis", yAxis);
		dataset.addSeries("function", func);
		dataset.addSeries("xA", leftLine);
		dataset.addSeries("xB", rightLine);
		dataset.addSeries("xC", middleLine);

		dataset.addSeries("a ",
				line45(minValue, function.evaluateExpression(minValue)));
		dataset.addSeries("b",
				line135(minValue, function.evaluateExpression(minValue)));
		dataset.addSeries("c ",
				line45(maxValue, function.evaluateExpression(maxValue)));
		dataset.addSeries("d",
				line135(maxValue, function.evaluateExpression(maxValue)));
		dataset.addSeries("e ",
				line45(xOfMiddle, function.evaluateExpression(xOfMiddle)));
		dataset.addSeries("f",
				line135(xOfMiddle, function.evaluateExpression(xOfMiddle)));

		return dataset;
	}

	/**
	 * plot the function and vertical lines
	 * 
	 * @param dataset
	 * @param xMin
	 * @param xMax
	 * @param yMin
	 * @param yMax
	 * @return
	 */
	private JFreeChart createChart(XYDataset dataset) {
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Bisection Simulation", "x", "F(x)", dataset,
				PlotOrientation.VERTICAL, true, // Is a legend required?
				true, // Use tooltips
				true // Configure chart to generate URLs?
				);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.getRenderer().setSeriesPaint(0, Color.black); // x axis
		plot.getRenderer().setSeriesPaint(1, Color.black); // y axis
		plot.getRenderer().setSeriesPaint(2, Color.red); // function
		plot.getRenderer().setSeriesPaint(3, Color.blue); // left line
		plot.getRenderer().setSeriesPaint(4, Color.blue); // right line
		plot.getRenderer().setSeriesPaint(5, Color.yellow); // middle line

		plot.getRenderer().setSeriesPaint(6, Color.black); // point marker
		plot.getRenderer().setSeriesPaint(7, Color.black); // point marker
		plot.getRenderer().setSeriesPaint(8, Color.black); // point marker
		plot.getRenderer().setSeriesPaint(9, Color.black); // point marker
		plot.getRenderer().setSeriesPaint(10, Color.black); // point marker
		plot.getRenderer().setSeriesPaint(11, Color.black); // point marker
	
		
		return chart;
	}

	/**
	 * set range to default range
	 * 
	 * @param chart
	 * @return
	 */
	private JFreeChart defaultRange(JFreeChart chart) {

		XYPlot plot = (XYPlot) chart.getPlot();


		// setting initial bounds for x axis
		NumberAxis domain = (NumberAxis) plot.getDomainAxis();
		domain.setRange(-5, 5);
		// setting initial bounds for y axis (use auto range to fit all the
		// draw)
		NumberAxis range = (NumberAxis) plot.getRangeAxis();
		range.setRange(-5, 5);

		return chart;
	}
	
	// point marker
	private static double[][] line45(double x, double y) {
		double[][] line = new double[2][2];
		line[0][0] = x - (0.03);
		line[0][1] = x + (0.03);

		line[1][0] = y - (0.06);
		line[1][1] = y + (0.06);
		return line;
	}

	// point marker
	private static double[][] line135(double x, double y) {
		double[][] line = new double[2][2];
		line[0][0] = x - (0.03);
		line[0][1] = x + (0.03);

		line[1][0] = y + (0.06);
		line[1][1] = y - (0.06);
		return line;
	}

	public static void main(String[] args) throws IOException,
			BracketingException, overFlowException {
//
//		double[] coeff1 = { -1, 0, 1};
//		Polynomial poly1 = new Polynomial(coeff1);
//		ArrayList<Object> f1 = new ArrayList<Object>();
//		f1.add(poly1);
//
//		double firstPoint = 0.5 ;
//		double secondPoint = 2 ;
//		
//		MathematicalExpression m1 = new MathematicalExpression(f1);
//		SolveEquation solve = new SolveEquation(m1, "bisection", firstPoint, secondPoint, 50,
//				0.00001);
//
//		ArrayList<double[]> data = solve.plotBisection();
//		Plot p = new Plot(m1, data);

		
		double[] x = {8,0.4,0.8,1.2};
		double[] y = {Math.cos(0),Math.cos(0.4),Math.cos(0.8),Math.cos(1.2)};
		LagrangeInterpolation interpolation = new LagrangeInterpolation(x, y);
		ArrayList<MathematicalExpression> inter = new ArrayList<MathematicalExpression>();
		boolean finished = false ;
		
	    Polynomial poly;
		while (!finished){
			ArrayList<Object> temp = new ArrayList<Object>();
			poly = interpolation.getNextInterpolation();
			if (poly == null)
				break ;
			temp.add(poly);
			MathematicalExpression temp2 = new MathematicalExpression(temp);
			inter.add(temp2);
	    }
		
		
		Plot interPolation = new Plot(inter , interpolation.getXandY());
		
		

	}


}   
