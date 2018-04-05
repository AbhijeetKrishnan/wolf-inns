public class PaymentMethods {
	private String payMethodCode;
	private String payMethodDesc;

	public String getPayMethodCode() {
		return payMethodCode;
	}
	public void setPayMethodCode(String payMethodCode) {
		this.payMethodCode = payMethodCode;
	}
	public String getPayMethodDesc() {
		return payMethodDesc;
	}
	public void setPayMethodDesc(String payMethodDesc) {
		this.payMethodDesc = payMethodDesc;
	}

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof PaymentMethods)) {return false;}
		PaymentMethods p = (PaymentMethods) o;
		return (payMethodCode==p.payMethodCode &&
                        payMethodDesc==p.payMethodDesc);
	}
}
