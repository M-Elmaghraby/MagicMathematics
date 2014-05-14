import java.util.ArrayList;

public class SolveEquation {

	private int numberOfIteration;
	private double precision;
	private ArrayList<XandY> iterations;
	private ArrayList<double[]> bisection ;

	/**
	 * Constructor for methods that needs 2 points (bisection , secant , false
	 * position)
	 * 
	 * @param method
	 * @param firstPoint
	 * @param secondPoint
	 * @throws BracketingException
	 * @throws overFlowException 
	 */
	public SolveEquation(MathematicalExpression function, String method,
			double firstPoint, double secondPoint, int numberItr,
			double precision) throws BracketingException, overFlowException {

		iterations = new ArrayList<XandY>();
		bisection = new ArrayList<double []>();
		numberOfIteration = numberItr;
		this.precision = precision;

		if (method.equalsIgnoreCase("secant"))
			secant(function, firstPoint, secondPoint);
		else if (method.equalsIgnoreCase("bisection"))
			bisection(function, firstPoint, secondPoint);
		else if (method.equalsIgnoreCase("false"))
			falsePosition(function, firstPoint, secondPoint);

	}
	
	/**
	 * constructor for newton-raphson method 
	 * @param function
	 * @param method
	 * @param firstPoint
	 * @param numberItr
	 * @param precision
	 * @throws BracketingException
	 * @throws overFlowException
	 */
	public SolveEquation(MathematicalExpression function, String method,
			double firstPoint, int numberItr,
			double precision) throws BracketingException, overFlowException {
		iterations = new ArrayList<XandY>();
		numberOfIteration = numberItr;
		this.precision = precision;
		
		if (method.equalsIgnoreCase("newton"))
			newton(function, firstPoint);
	}

	/**
	 * solving using secant method
	 * 
	 * @param method
	 * @param firstPoint
	 * @throws overFlowException 
	 */
	public void secant(MathematicalExpression function, double x0, double x1) throws overFlowException {
		double f0 = 0.0;
		double f1 = 0.0;
		double f2 = 10;
		double x2;
		int itr = 0;
		while (Math.abs(f2) > precision && (itr <= numberOfIteration)) {
			itr++;
			f0 = function.evaluateExpression(x0);
			f1 = function.evaluateExpression(x1);
			x2 = x0 - (f0 * (x1 - x0) / (f1 - f0)); // secant method formula
			x0 = x1;
			x1 = x2;
			f2 = function.evaluateExpression(x2);
			iterations.add(new XandY(x2, f2)); // the point x2 and the value of
												// the function at x2
		}
		if (itr > numberOfIteration)
			throw new overFlowException ("reached maximum number of iterations");
		numberOfIteration = itr ;

	}

	/**
	 * solving non-linear equations using newton-raphson
	 * 
	 * @param function
	 * @param x0
	 * @throws overFlowException 
	 */
	public void newton(MathematicalExpression function, double x0) throws overFlowException {
		double f0 = 10;
		double fPrime = 0.0;
		int itr = 0;

		while (Math.abs(f0) > precision && (itr <= numberOfIteration)) {
			itr++;
			f0 = function.evaluateExpression(x0);
			fPrime = function.diffrentiate(1, x0);
			if (fPrime == 0) {
				throw new ArithmeticException(
						"Division by zero in newton raphson");
			}
			x0 = x0 - (f0 / fPrime);
			f0 = function.evaluateExpression(x0);
			iterations.add(new XandY(x0, f0));
		}
		if (itr > numberOfIteration)
			throw new overFlowException ("reached maximum number of iterations");
		numberOfIteration = itr ;

	}

	/**
	 * solving non-linear equations using bisection method
	 * 
	 * @param function
	 * @param xA
	 * @param xB
	 * @throws BracketingException
	 * @throws overFlowException 
	 */
	public void bisection(MathematicalExpression function, double xA, double xB)
			throws BracketingException, overFlowException {
		double fA = function.evaluateExpression(xA);
		double fB = function.evaluateExpression(xB);
		double fC = 10; // a number that is big
		double xC = 0;
		int itr = 0;

		if (fA == 0.0){
			itr = 0;
			return;
			}
		if (fB == 0.0){
			itr = 0 ;
			return;
		}
		if (fA * fB > 0)
			throw new BracketingException("root is not bracketed in x1 and x2");

		while (Math.abs(fC) >= precision && (itr <= numberOfIteration)) {
			xC = (xA + xB) / 2;
			fC = function.evaluateExpression(xC);
			bisection.add(new double [] {xA , xB ,xC});
			iterations.add(new XandY(xC, fC));
			if (fA * fC < 0)
				xB = xC;
			else {
				xA = xC;
				fA = function.evaluateExpression(xA);
			}
			itr++;
		}
		if (itr > numberOfIteration)
			throw new overFlowException ("reached maximum number of iterations");
		numberOfIteration = itr ;

	}

	/**
	 * solving non-linear equation using false-position method
	 * 
	 * @param function
	 * @param xA
	 * @param xB
	 * @throws BracketingException
	 * @throws overFlowException 
	 */

	public void falsePosition(MathematicalExpression function, double xA,
			double xB) throws BracketingException, overFlowException {

		double fA = function.evaluateExpression(xA);
		double fB = function.evaluateExpression(xB);
		double fC = 10; // a number that is big
		double xC = 0;
		int itr = 0;

		if (fA == 0.0)
			return; //TODO I dont know what exactly should be done ...
		if (fB == 0.0)
			return;
		if (fA * fB > 0)
			throw new BracketingException("root is not bracketed in x1 and x2");

		while (Math.abs(fC) >= precision && (itr <= numberOfIteration)) {
			xC = xA - (fA * (xB - xA) / (fB - fA));
			fC = function.evaluateExpression(xC);
			iterations.add(new XandY(xC, fC));
			if (fA * fC < 0)
				xB = xC;
			else {
				xA = xC;
				fA = function.evaluateExpression(xA);
			}
			itr++;
		}
		if (itr > numberOfIteration)
			throw new overFlowException ("reached maximum number of iterations");
		numberOfIteration = itr ;
	}

	
	/**
	 * Prints the data of each iteration     x and f(x)
	 */
	public ArrayList<XandY> getResult() {
		
		return iterations ;
	}
	
	
	/**
	 * This method used in simulating bisection algorithm
	 * @return
	 */
	public ArrayList<double []> plotBisection (){
		return bisection ;
	}
	/**
	 * Get number of iterations
	 * @return
	 */
	public int getNumberOfIteration() {
		return numberOfIteration;
	}

	/**
	 * Get the precision value 
	 * @return
	 */
	public double getPrecision() {
		return precision;
	}
	
	/**
	 * Get min x
	 * @param x1
	 * @param x2
	 * @return
	 */
	public double getMinX(double x1 , double x2){
		return Math.min(x1, x2);
	}
	
	/**
	 * Get max x 
	 * @param x1
	 * @param x2
	 * @return
	 */
	public double getMaxX(double x1 , double x2){
		return Math.max(x1, x2);
	}
	
	/**
	 * Get min Y
	 * @param y1
	 * @param y2
	 * @return
	 */
	public double getMinY(double y1 , double y2){
		return Math.min(y1, y2);
	}
	
	/**
	 * Get max Y
	 * @param y1
	 * @param y2
	 * @return
	 */
	public double getMaxY(double y1 , double y2){
		return Math.max(y1, y2);
	}

	public static void main(String[] args) throws BracketingException, overFlowException {
		double[] coeff2 = { 45, 0, -18, 0, 1 };
		// int[] coeff = { 5, 2};

		// Polynomial poly = new Polynomial(coeff);
		Polynomial poly2 = new Polynomial(coeff2);
		ArrayList<Object> f = new ArrayList<Object>();
		// f.add(poly);
		// f.add(new Sin(poly2));
		// f.add(new Operator('+'));
		f.add(poly2);
		// f.add(new e(poly));
		System.out.println(f.toString());

		MathematicalExpression m = new MathematicalExpression(f);
		SolveEquation solve = new SolveEquation(m, "newton", 2, 50, 0.00001);
	}

}
