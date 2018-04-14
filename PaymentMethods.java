import java.util.ArrayList;

public class PaymentMethods extends DatabaseObject{
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
		return (payMethodCode.equals(p.payMethodCode) &&
                        payMethodDesc.equals(p.payMethodDesc));
	}
	
public ArrayList<String> toStringArrayList() {
		
		ArrayList<String> fieldValues = new ArrayList<String>();
		
		fieldValues.add(this.getPayMethodCode());
		fieldValues.add(this.getPayMethodDesc());
		
		return fieldValues;		
	}
	
	public ArrayList<String> getFieldNamesList() {
		
		ArrayList<String> fieldNames = new ArrayList<String>();
		
		fieldNames.add("Payment Method Code");
		fieldNames.add("Description");

		return fieldNames;			
	}
	
	public static PaymentMethods select() {

		ArrayList<PaymentMethods> paymentMethodsList = MaintainBillingRecords.retrieveAllPaymentMethods();
		ArrayList<DatabaseObject> databaseObjectList = (ArrayList<DatabaseObject>) ((ArrayList<?>) paymentMethodsList);
		
		PaymentMethods selectedRecord = (PaymentMethods)MenuUtilities.paginatedRecordSelection(databaseObjectList);
		
		return selectedRecord;
	}
}
