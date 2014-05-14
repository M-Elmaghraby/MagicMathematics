import java.util.ArrayList;

/**
 * @author Moustafa Ashmawy the class calculates the interpolating polynomial of
 *         tabulated data
 * */
public class DividedDifference {

	// that array is used to hold the first column of our two columns
	// it contains the values of x
	private double[] firstColumn;
	// that array is used to hold the second column of our two columns
	// initially it contains the values of y
	private double[] secondColumn;
	// second column copy to store the original data
	private double[] seocndCopy;
	// that array holds the coefficients of the interpolating Polynomial
	private double[] coefficients;
	private int interNum;

	// constructor
	public DividedDifference(double[] x, double[] y) {
		seocndCopy = y.clone();
		firstColumn = x;
		secondColumn = y;
		coefficients = new double[x.length];
		// a0 = f[X0]
		coefficients[0] = y[0];
		interNum = 2;
	}

	// the method that returns pairs of points
	public ArrayList<double[]> XandY() {
		ArrayList<double[]> result = new ArrayList<double[]>();
		for (int i = 0; i < firstColumn.length; i++) {
			result.add(i, new double[] { firstColumn[i], seocndCopy[i] });
		}
		return result;
	}

	// get the next interpolation
	public Polynomial getNextInterpolation() {
		if (interNum > firstColumn.length)
			return null;
		double[] tempX = new double[interNum];
		double[] tempY = new double[interNum];
		for (int i = 0; i < interNum; i++) {
			tempX[i] = firstColumn[i];
			tempY[i] = seocndCopy[i];
		}
		interNum++;
		return new LagrangeInterpolation(tempX, tempY).interpolate();
	}

	//the result set of Mathematical expression
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

	// this method is used to calculate the coefficients f the interpolating
	// Polynomial
	private void calculateCoeff() {
		int limit = secondColumn.length;
		// that integer holds where the real data starts from
		int offset = 1;
		// that double holds the data of the current cell of the second column
		// array so we don't have to copy the array
		double temp = 0;
		for (int j = 1; j < limit; j++) {
			temp = secondColumn[offset - 1];
			for (int i = offset; i < limit; i++) {
				double temp2 = secondColumn[i];
				secondColumn[i] = (secondColumn[i] - temp)
						/ (firstColumn[i] - firstColumn[i - offset]);
				temp = temp2;
			}
			coefficients[j] = secondColumn[offset];
			offset++;
		}
	}

	// generate the interpolating polynomial
	public Polynomial interpolate() {
		calculateCoeff();
		Polynomial result = new Polynomial(new double[] { coefficients[0] });
		for (int i = 1; i < coefficients.length; i++) {
			Polynomial temp = new Polynomial(new double[] { 1 });
			for (int j = 0; j < i; j++) {
				temp = temp.multiplyPolynomial(new Polynomial(new double[] {
						-firstColumn[j], 1 }));
			}
			temp = temp.multiplyPolynomial(new Polynomial(
					new double[] { coefficients[i] }));
			result = result.addPolynomial(temp);
		}
		return result;
	}

	// this method is used to calculate the error Polynomial
	public Polynomial errorPolynomial(MathematicalExpression mainFunc) {
		// the length of both the x and y array of points
		int limit = firstColumn.length;
		// the resulting polynomial
		Polynomial result = new Polynomial(new double[] { 1 });
		if (limit > 1)
			result = new Polynomial(new double[] { 1 });
		for (int i = 0; i < limit; i++) {
			// multiplying temp by (X - Xi)
			Polynomial temporary = new Polynomial(new double[] {
					-firstColumn[i], 1 });
			result = result.multiplyPolynomial(temporary);
		}
		result = result.multiplyPolynomial(new Polynomial(
				new double[] { (1.0 / factorial(limit)) }));
		// consider c is the mid point between a and b
		double c = (firstColumn[0] + firstColumn[limit - 1]) / 2;
		// getting the value of the derivative of the main function at c
		double val = 1.0 * mainFunc.diffrentiate(limit, c);
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
