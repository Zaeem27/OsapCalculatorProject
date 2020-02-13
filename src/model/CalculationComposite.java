package model;
import java.text.DecimalFormat;

public class CalculationComposite {
	
	private Double principal;
	private Double interest;
	private Double period;
	private Double fixedInterest;
	private Integer gracePeriod;
	private String gracePeriodUsed;
	private Double totalInterest;
	
	public CalculationComposite() {
		this.principal = 0.0;
		this.interest = 0.0;
		this.period = 0.0;
		this.fixedInterest=0.0;
		this.gracePeriod=0;
		this.gracePeriodUsed = "";
		this.totalInterest=0.0;
	}
	
	public CalculationComposite(Double principal, Double interest, Double period, Double fixedInterest, Integer gracePeriod, String gracePeriodUsed) {
		this.principal = principal;
		this.interest = interest;
		this.period = period;
		this.fixedInterest = fixedInterest;
		this.gracePeriod = gracePeriod;
		this.gracePeriodUsed = gracePeriodUsed;
//		if (!gracePeriodUsed.equals("")) {
//			this.totalInterest = this.fixedInterest +this.interest;
//		}
		if (gracePeriodUsed.equals("false") || gracePeriodUsed.equals("")) {
			this.totalInterest = this.fixedInterest;
		} else {
			this.totalInterest = this.fixedInterest +this.interest;
		}
	}
	
	//empty string means not used
	public String isGracePeriodUsed() {
		return gracePeriodUsed;
	}

	public void setGracePeriodUsed(String gracePeriodUsed) {
		this.gracePeriodUsed = gracePeriodUsed;
	}

	public Double getPrincipal() {
		return principal;
	}

	public Double getFixedInterest() {
		return fixedInterest;
	}

	public void setFixedInterest(Double fixedInterest) {
		this.fixedInterest = fixedInterest;
	}

	public Integer getGracePeriod() {
		return gracePeriod;
	}

	public void setGracePeriod(Integer gracePeriod) {
		this.gracePeriod = gracePeriod;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getPeriod() {
		return period;
	}

	public void setPeriod(Double period) {
		this.period = period;
	}
	
	public Double getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(Double totalInterest) {
		this.totalInterest = totalInterest;
	}



	String getGracePeriodUsed() {
		return gracePeriodUsed;
	}

}
