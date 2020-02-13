package model;
import java.text.DecimalFormat;

public class CalculationHandler {

	private CalculationComposite parameters;
	private DecimalFormat df2;
	
	public CalculationHandler(CalculationComposite parameters) {
		this.parameters = parameters;
		this.df2 = new DecimalFormat("###.##");
	}
	
	public Double calcPayment() {	
		double r = parameters.getInterest()/100;
		double out = (((r / 12) * parameters.getPrincipal()) / (1 - Math.pow((1 + (r / 12)), (-1 * parameters.getPeriod()))));
		return Double.valueOf(df2.format(out));
		
	//	return out;
	}
	
	public Double calcGraceInterest() {
		double r = ((parameters.getInterest()/100) + (parameters.getFixedInterest()/100))/12;
		double out = parameters.getPrincipal() * r * parameters.getGracePeriod();
		return Double.valueOf(df2.format(out));
	}
	
	public Double calcMonthlyPayment() {
		Double out=0.0;
//		if(!parameters.getGracePeriodUsed().equals("")) {
//			out = this.calcPayment() + (this.calcGraceInterest() / parameters.getGracePeriod());
//		}
//		else {
//			out = this.calcPayment();
//		}
		if (parameters.getGracePeriodUsed().equals("false") || parameters.getGracePeriodUsed().equals("")) {
			out = this.calcPayment();
		} else {
			out = this.calcPayment() + (this.calcGraceInterest() / parameters.getGracePeriod());
		}
		return Double.valueOf(df2.format(out));
	}
}
