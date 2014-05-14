/**
 * The implementation of the Cosine Class
 * 
 * @author Hatem Moustafa & Mahmoud El-Maghraby
 */
public class Cos
{
	private Polynomial angle;

	/**
	 * The Constructor
	 * 
	 * @param p
	 *            : the angle
	 */
	public Cos(Polynomial p)
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
		return Math.cos(result);
	}

	/**
	 * Method to print the function
	 */

	public String toString()
	{

		return " Cos(" + this.angle.toString() + ")";
	}

	/**
	 * Method to differentiate the Cosine 
	 * 
	 * @return the derivative of the Cosine
	 */

	public Object[] differentiate()
	{
		Object[] derivative = new Object[2];
		double[] coeffTerms = angle.differentiate().getCoefficients();
		
		for (int i = 0; i < coeffTerms.length; i++)
		{
			coeffTerms[i]= (-1*coeffTerms[i]);
		}
		
		Polynomial coefficient = new Polynomial(coeffTerms);
		derivative[0] = coefficient ;
		derivative[1] = new Sin(angle);
		return derivative;
	}
}
