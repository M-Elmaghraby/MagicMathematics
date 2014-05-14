/**
 * The implementation of the Exponential Class
 * 
 * @author Hatem Moustafa & Mahmoud El-Maghraby
 */
public class e
{
	private Polynomial exponent;

	/**
	 * The Constructor
	 * 
	 * @param p
	 *            : the exponent
	 */
	public e(Polynomial p)
	{
		exponent = p;

	}

	/**
	 * Method to evaluate the function
	 * 
	 * @param x
	 *            : the X value
	 * @return the value of the evaluation
	 */

	public double evaluate(double x)
	{
		double result = this.exponent.evaluate(x);
		return Math.exp(result);
	}

	/**
	 * Method to print the function
	 */

	public String toString()
	{

		return " e ^(" + this.exponent.toString() + ")";
	}

	/**
	 * Method to differentiate the Sin
	 * 
	 * @return the derivative of the Sin
	 */

	public Object[] differentiate()
	{

		Object[] derivative = new Object[2];
		derivative[0] = exponent.differentiate();
		derivative[1] = new e(exponent);
		return derivative;
	}
}
