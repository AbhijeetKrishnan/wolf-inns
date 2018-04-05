public class BillingInfo {
	private int billingId;
	private String responsiblePartySSN;
	private String address;
	private String city;
	private String state;
	private String payMethodCode;
	private String cardNumber;
	private double totalCharges;

	public int getBillingId() {
		return billingId;
	}
	public void setBillingId(int billingId) {
		this.billingId = billingId;
	}
	public String getResponsiblePartySSN() {
		return responsiblePartySSN;
	}
	public void setResponsiblePartySSN(String responsiblePartySSN) {
		this.responsiblePartySSN = responsiblePartySSN;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPayMethodCode() {
		return payMethodCode;
	}
	public void setPayMethodCode(String payMethodCode) {
		this.payMethodCode = payMethodCode;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public double getTotalCharges() {
		return totalCharges;
	}
	public void setTotalCharges(double totalCharges) {
		this.totalCharges = totalCharges;
	}

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof BillingInfo)) {return false;}
		BillingInfo b = (BillingInfo) o;
		return (billingId==b.billingId && responsiblePartySSN==b.responsiblePartySSN &&
                        address==b.address && city==b.city &&
                        state==b.state && payMethodCode==b.payMethodCode &&
                        cardNumber==b.cardNumber && totalCharges==b.totalCharges);
	}
}
