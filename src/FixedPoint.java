import java.util.ArrayList;

//if we got a fun with none fixed point.
public class FixedPoint
{
	double p0;
	MathematicalExpression fun;
	double accuracy;
	int iterations = 0;
	ArrayList<Double[]> res; 

	/**
	 * constructor, set the accuracy to 10^-5 by default.
	 * 
	 * @param p0
	 * @param fun
	 */
	public FixedPoint(double p0, MathematicalExpression fun)
	{
		this.p0 = p0;
		this.fun = fun;
		this.accuracy = Math.pow(10, -5);
		this.iterations = 50;
		this.res=new ArrayList<Double[]>();
	}

	/**
	 * constructor.
	 * 
	 * @param p0
	 * @param fun
	 * @param accuracy
	 * @param iterations
	 */
	public FixedPoint(double p0, MathematicalExpression fun, double accuracy,
			int iterations)
	{
		this.p0 = p0;
		this.fun = fun;
		this.accuracy = accuracy;
		this.iterations = iterations;
		this.res=new ArrayList<Double[]>();
	}

	/**
	 * calculate the fixed point for the specified function.
	 * 
	 * @return the fixed point
	 * @throws Exception 
	 */
	public ArrayList<Double[]> getFixedPt() throws Exception
	{
		Exception ex=new Exception("No fixed point found at the specified iteration");
		double fpt = 0;
		double p = p0;
		double pNext = 0;
		Double[] pair=new Double[2];
		boolean found = false;
		int index = 0;
		double diff;
		while (!found)
		{
			index++;
			pair[0]=p;
			pNext = fun.evaluateExpression(p);
			pair[1]=pNext;
			res.add(pair);
			pair = new Double[2];
			
			diff = Math.abs(p - pNext);
			if (diff < accuracy)
			{
				found = true;
				fpt = pNext;
			} else
			{
				p = pNext;
			}
			if(index > iterations)
			{
				throw ex;
			}
		}
		return res;
	}
}
