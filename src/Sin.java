/**
 * The implementation of the Sin Class
 * 
 * @author Hatem Moustafa & Mahmoud El-Maghraby
 */
public class Sin
{
	private Polynomial angle;

	/**
	 * The Constructor
	 * 
	 * @param p
	 *            : the angle
	 */
	public Sin(Polynomial p)
	{
		angle = p;

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
		double result = this.angle.evaluate(x);
		return Math.sin(result);
	}

	/**
	 * Method to print the function
	 */

	public String toString()
	{

		return " Sin(" + this.angle.toString() + ")";
	}

	/**
	 * Method to differentiate the Sin
	 * 
	 * @return the derivative of the Sin
	 */

	public Object[] differentiate()
	{

		Object[] derivative = new Object[2];
		derivative[0] = angle.differentiate();
		derivative[1] = new Cos(angle);
		return derivative;
	}
}
