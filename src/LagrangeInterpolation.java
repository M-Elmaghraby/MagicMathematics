import java.util.ArrayList;

public class LagrangeInterpolation {

	// the array that holds the x points of the tabulated function
	private double[] x;
	// the array that holds the x points of the tabulated function
	private double[] y;
	// interpolation Number
	private int interNum;

	// the constructor of the class
	public LagrangeInterpolation(double[] x, double[] y) {
		this.x = x;
		this.y = y;
		interNum = 2;
	}

	public ArrayList<double[]> getXandY() {
		ArrayList<double[]> data = new ArrayList<double[]>();
		for (int i = 0; i < x.length; i++) {
			double[] temp = new double[2];
			temp[0] = x[i];
			temp[1] = y[i];
			data.add(temp);
		}
		return data;
	}

	/**
	 * get max X in interpolating points
	 * 
	 * @return
	 */
	public double getMax_X() {
		double max = 0.0;
		for (int i = 0; i < x.length; i++) {
			if (i == x.length - 1)
				break;
			max = Math.max(x[i], x[i + 1]);
		}
		return max;
	}

	/**
	 * get max Y in interpolating points
	 * 
	 * @return
	 */
	public double getMax_Y() {
		double max = 0.0;
		for (int i = 0; i < y.length; i++) {
			if (i == y.length - 1)
				break;
			max = Math.max(y[i], y[i + 1]);
		}
		return max;
	}

	/**
	 * get min X in interpolating points
	 * 
	 * @return
	 */
	public double getMin_X() {
		double min = 0.0;
		for (int i = 0; i < x.length; i++) {
			if (i == x.length - 1)
				break;
			min = Math.min(x[i], x[i + 1]);
		}
		return min;
	}

	/**
	 * get min Y in interpolating points
	 * 
	 * @return
	 */
	public double getMin_Y() {
		double min = 0.0;
		for (int i = 0; i < y.length; i++) {
			if (i == y.length - 1)
				break;
			min = Math.min(y[i], y[i + 1]);
		}
		return min;
	}

	// the result set of Mathematical expression
	public ArrayList<MathematicalExpression> getSteps() {
		ArrayList<MathematicalExpression> inter = new ArrayList<MathematicalExpression>();
		boolean finished = false;
		Polynomial poly;
		while (!finished) {
			ArrayList<Object> temp = new ArrayList<Object>();
			poly = this.getNextInterpolation();
			if (poly == null)
				break;
			temp.add(poly);
			MathematicalExpression temp2 = new MathematicalExpression(temp);
			inter.add(temp2);
		}
		interNum = 2;
		return inter;
	}

	// this method is used to get the interpolating Polynomial of the given (x,
	// y) pairs
	// the method returns null if failed to interpolate
	public Polynomial interpolate() {
		// the length of both the x and y array of points
		int limit = x.length;
		// the resulting polynomial
		Polynomial result = new Polynomial(new double[] {});
		if (limit > 1)
			result = new Polynomial(new double[] {});
		for (int i = 0; i < limit; i++) {
			// the polynomial to hold each Yi * ()
			Polynomial temp = new Polynomial(new double[] { y[i] });
			for (int j = 0; j < limit; j++) {
				if (i != j) {
					// multiplying temp by (X - Xi)/(Xi - Xj)
					Polynomial temporary = new Polynomial(new double[] {
							-x[j] / (x[i] - x[j]), 1 / (x[i] - x[j]) });
					temp = temp.multiplyPolynomial(temporary);
				}
			}
			result = result.addPolynomial(temp);
		}
		return result;
	}

	// add a new pair to the interpolating Polynomial
	public Polynomial addAndInterpolate(double xElem, double yElem) {
		// the length of poth the x and y array of points
		int limit = x.length;
		double[] newX = new double[limit + 1];
		double[] newY = new double[limit + 1];
		// adding the new elements to the pair arrays
		newX[limit] = xElem;
		newY[limit] = yElem;
		for (int i = 0; i < limit; i++) {
			// // copying the elements to the new arrays of pairs
			newX[i] = x[i];
			newY[i] = y[i];
		}

		// setting the lagrange object arrays to new Arrays.
		this.x = newX;
		this.y = newY;
		return interpolate();
	}

	// get the next interpolation
	public Polynomial getNextInterpolation() {
		if (interNum > x.length)
			return null;
		double[] tempX = new double[interNum];
		double[] tempY = new double[interNum];
		for (int i = 0; i < interNum; i++) {
			tempX[i] = x[i];
			tempY[i] = y[i];
		}
		interNum++;
		return new LagrangeInterpolation(tempX, tempY).interpolate();
	}

	// this method is used to calculate the error Polynomial
	public Polynomial errorPolynomial(MathematicalExpression mainFunc) {
		// the length of both the x and y array of points
		int limit = x.length;
		// the resulting polynomial
		Polynomial result = new Polynomial(new double[] { 1 });
		if (limit > 2)
			result = new Polynomial(new double[] { 1 });
		for (int i = 0; i < limit; i++) {
			// multiplying temp by (X - Xi)
			Polynomial temporary = new Polynomial(new double[] { -x[i], 1 });
			result = result.multiplyPolynomial(temporary);
		}
		result = result.multiplyPolynomial(new Polynomial(
				new double[] { 1.0 / factorial(limit) }));
		// consider c is the mid point between a and b
		double c = (x[0] + x[limit - 1]) / 2.0;
		// getting the value of the derivative of the main function at c
		double val = mainFunc.diffrentiate(limit, c);
		// multiply the function by the (N + 1) derivative of the main function
		result = result
				.multiplyPolynomial(new Polynomial(new double[] { val }));
		return result;
	}

	// calculate the error at a given point
	public double errorValue(MathematicalExpression mainFunction, double x) {
		return errorPolynomial(mainFunction).evaluate(x);
	}

	// calculate the factorial
	private int factorial(int limit) {
		if (limit < 0)
			return -1;
		if (limit == 1 || limit == 0)
			return 1;
		return limit * factorial(limit - 1);
	}

}
