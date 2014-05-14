import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * The Polynomial Class Implementation
 * 
 * @author Mahmoud El-Maghraby
 */

public class Polynomial
{
	private double[] coefficients;
	private int degree;

	/**
	 * The constructor
	 * 
	 * @param coefficients
	 */
	public Polynomial(double[] coefficients)
	{
//		for (int i = 0; i < coefficients.length; i++)
//		{
//			BigDecimal result = new BigDecimal(coefficients[i]).setScale(2, BigDecimal.ROUND_HALF_UP);
//            coefficients[i]= result.doubleValue();
//		}
		this.coefficients = coefficients;
		degree = coefficients.length - 1;
	}

	
	/**
	 * @return the coefficients
	 */
	public double[] getCoefficients()
	{
		return coefficients;
	}


	/**
	 * @param coefficients the coefficients to set
	 */
	public void setCoefficients(double[] coefficients)
	{
		this.coefficients = coefficients;
	}


	/**
	 * @return the degree
	 */
	public int getDegree()
	{
		return degree;
	}


	/**
	 * @param degree the degree to set
	 */
	public void setDegree(int degree)
	{
		this.degree = degree;
	}


	/**
	 * Method to differentiate the polynomial
	 * 
	 * @return the derivative of the polynomial
	 */
	public Polynomial differentiate()
	{
		if (degree >= 0)
		{
			double[] derivative = new double[degree];

			for (int i = 0; i < derivative.length; i++)
			{
				derivative[i] = (coefficients[i + 1] * (i + 1));
			}

			return new Polynomial(derivative);
		} else
		{
			return new Polynomial(new double[] { 0 });
		}
	}

	/**
	 * Method to evaluate the polynomial for a given value of X
	 * 
	 * @param value
	 *            : the X value
	 * @return : the value of the evaluation
	 */

	public double evaluate(double value)
	{
		double result = 0;
		for (int i = 0; i < coefficients.length; i++)
		{
			result += (Math.pow(value, i) * coefficients[i]);
		}
		return result;
	}
	
	/**
	 * Method to add a polynomial to the specified polynomial
	 * @param p : The polynomial to be added
	 */
	
	public Polynomial addPolynomial(Polynomial p)
	{
		if(p.getDegree() > this.degree)
		{
			for (int i = 0; i < this.coefficients.length; i++)
			{
				p.getCoefficients()[i]=p.getCoefficients()[i]+this.coefficients[i];
			}
			return new Polynomial(p.getCoefficients());
		}
		else
		{
			for (int i = 0; i < p.getCoefficients().length; i++)
			{
				coefficients[i] = coefficients[i]+p.getCoefficients()[i];
			}
		}
		return new Polynomial(coefficients) ;
	}
	
	/**
	 * Method to Multiply two polynomials and assign the result to the first 1
	 * @param p
	 * @return
	 */
	public Polynomial multiplyPolynomial(Polynomial p)
	{
		double[] newPoly = new double[p.getDegree()+this.getDegree()+1];
		
		for (int i = 0; i < p.getCoefficients().length; i++)
		{
			for (int j = 0; j < this.getCoefficients().length; j++)
			{
				newPoly[i+j]=(newPoly[i+j]+(p.getCoefficients()[i]*this.getCoefficients()[j]));
			}
		}
		
		return new Polynomial(newPoly) ;
	}

	/**
	 * Method to print the polynomial
	 */
	public String toString()
	{
		String result = "(";
		for (int i = coefficients.length - 1; i >= 0; i--)
		{
			if (coefficients[i] != 0)
			{
				if (i == 0)
				{
					result += coefficients[i] + "   ";
				} else if (i == 1)
				{
					result += coefficients[i] + "X + ";
				} else
				{
					result += coefficients[i] + "X^" + i + " + ";
				}
			}
		}
		if (result.length() > 3)
		{
			result = result.substring(0, result.length() - 3);
		} else
		{
			result = "(0";
		}

		return result+")";
	}

	public static void main(String[] args)
	{
		double[] coeff2= { 4, 0, 2, 3 };
		double[] coeff = { 5, 2};

		Polynomial poly = new Polynomial(coeff);
		Polynomial poly2 = new Polynomial(coeff2);
        ArrayList<Object> f = new ArrayList<Object>();
        f.add(poly);
        f.add(new Sin(poly2));
        f.add(new Operator('+'));
        f.add(poly2);
        f.add(new e(poly));
        
        
        MathematicalExpression m = new MathematicalExpression(f);
        System.out.println(m.diffrentiate(5, 5));
        System.out.println(poly2.toString());
        

	}
}
