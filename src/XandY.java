import java.math.BigDecimal;


public class XandY {

	private double x ;
	private double y ;
	
	public XandY (double x , double y){
		this.x = x ;
		this.y = y ;
	}

	public double getX() {
		BigDecimal result = new BigDecimal(x).setScale(6, BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		BigDecimal result = new BigDecimal(y).setScale(6, BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
	}

	public void setY(double y) {
		this.y = y;
	}
}
