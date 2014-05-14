/**
 * @author Mahmoud El-Maghraby
 */

import java.util.ArrayList;

public class MathematicalExpression
{
	ArrayList<Object> function;

	/**
	 * The Constructor
	 * 
	 * @param function
	 */
	public MathematicalExpression(ArrayList<Object> function)
	{
		this.function = function;
	}

	public MathematicalExpression()
	{
		function = new ArrayList<Object>();
	}

	/**
	 * Method to differentiate the mathematical expression
	 * 
	 * @return
	 */
	public MathematicalExpression differentiate()
	{
		int first = 0;
		int last = 0;

		ArrayList<Object> result = new ArrayList<Object>();

		for (int i = 0; i < function.size(); i++)
		{
			if (function.get(i).getClass().getName().equals("Operator"))
			{
				last = i;
				// we have the first and the last entries of the term
				for (int j = first; j < last; j++)
				{
					if (function.get(j).getClass().getName()
							.equals("Polynomial"))
					{
						result.add(((Polynomial) function.get(j))
								.differentiate());
					} else
					{
						Object[] deff = new Object[2];
						if (function.get(j).getClass().getName().equals("Sin"))
						{
							deff = ((Sin) function.get(j)).differentiate();
						} else if (function.get(j).getClass().getName()
								.equals("Cos"))
						{
							deff = ((Cos) function.get(j)).differentiate();
						} else if (function.get(j).getClass().getName()
								.equals("e"))
						{
							deff = ((e) function.get(j)).differentiate();
						}

						result.add(deff[0]);
						result.add(deff[1]);
					}
					for (int n = first; (n < last); n++)
					{
						if (n != j)
						{
							result.add(function.get(n));
						}
					}
					if (j != last - 1)
					{
						result.add(new Operator('+'));
					} else
					{
						result.add(function.get(last));
					}
				}
				first = last + 1;
			}
		}
		last = function.size();

		for (int j = first; j < last; j++)
		{
			if (function.get(j).getClass().getName().equals("Polynomial"))
			{
				result.add(((Polynomial) function.get(j)).differentiate());
			} else
			{
				Object[] deff = new Object[2];
				if (function.get(j).getClass().getName().equals("Sin"))
				{
					deff = ((Sin) function.get(j)).differentiate();
				} else if (function.get(j).getClass().getName().equals("Cos"))
				{
					deff = ((Cos) function.get(j)).differentiate();
				} else if (function.get(j).getClass().getName().equals("e"))
				{
					deff = ((e) function.get(j)).differentiate();
				}

				result.add(deff[0]);
				result.add(deff[1]);
			}
			for (int n = first; (n < last); n++)
			{
				if (n != j)
				{
					result.add(function.get(n));
				}
			}
			if (j != last - 1)
			{
				result.add(new Operator('+'));
			}
		}

		return new MathematicalExpression(result);
	}

	/**
	 * Method to evaluate the mathematical expression given some value x
	 * 
	 * @param value
	 *            : the x value
	 * @return : the value of the evaluation
	 */

	public double evaluateExpression(double value)
	{
		boolean plus = false;
		boolean minus = false;
		double result = 1;
		double finalResult = 0;
		for (int i = 0; i < function.size(); i++)
		{
			if (function.get(i).getClass().getName().equals("Sin"))
			{
				result *= ((Sin) function.get(i)).evaluate(value);
			} else if (function.get(i).getClass().getName().equals("Cos"))
			{
				result *= ((Cos) function.get(i)).evaluate(value);
			} else if (function.get(i).getClass().getName().equals("e"))
			{
				result *= ((e) function.get(i)).evaluate(value);
			} else if (function.get(i).getClass().getName()
					.equals("Polynomial"))
			{
				result *= ((Polynomial) function.get(i)).evaluate(value);
			} else
			{

				if (plus)
				{
					finalResult += result;
					plus = false;
				} else if (minus)
				{
					finalResult -= result;
					minus = false;
				} else
				{
					finalResult += result;
				}

				if (((Operator) function.get(i)).getOperator() == '+')
				{
					plus = true;
				} else
				{
					minus = true;
				}
				result = 1;
			}
		}
		if (plus)
		{
			return finalResult + result;
		} if(minus)
		{
			return finalResult - result;
		}
		finalResult+= result ;
		return finalResult ;

	}

	/**
	 * Method to get the value of the Nth differential of the expression
	 * 
	 * @param nDiffrential
	 *            : the Nth differential
	 * @param value
	 *            : the x value
	 * @return value of the Nth differential of the expression
	 */
	public double diffrentiate(int nDiffrential, double value)
	{
		MathematicalExpression m = this;
		for (int i = 0; i < nDiffrential; i++)
		{
			m = m.differentiate();
		}
		return m.evaluateExpression(value);
	}

	// the toString method
	public String toString()
	{
		String result = "";
		for (int i = 0; i < function.size(); i++)
		{
			if (function.get(i).getClass().getName().equals("Sin"))
			{
				result += ((Sin) function.get(i)).toString();
			} else if (function.get(i).getClass().getName().equals("Cos"))
			{
				result += ((Cos) function.get(i)).toString();
			} else if (function.get(i).getClass().getName().equals("e"))
			{
				result += ((e) function.get(i)).toString();
			} else if (function.get(i).getClass().getName()
					.equals("Polynomial"))
			{
				result += ((Polynomial) function.get(i)).toString();
			} else
			{
				result += " " + ((Operator) function.get(i)).getOperator()
						+ " ";
			}
		}
		return result;

	}
}
