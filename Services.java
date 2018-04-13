import java.util.ArrayList;

public class Services extends DatabaseObject {
	private String serviceCode;
	private String serviceDesc;
	private double charge;

	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	public double getCharge() {
		return charge;
	}
	public void setCharge(double charge) {
		this.charge = charge;
	}

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof Services)) {return false;}
		Services s = (Services) o;
		return (serviceCode.equals(s.serviceCode) && serviceDesc.equals(s.serviceDesc) &&
			charge==s.charge);
	}
	
	public ArrayList<String> toStringArrayList() {
		
		ArrayList<String> fieldValues = new ArrayList<String>();
		
		fieldValues.add(this.getServiceCode());
		fieldValues.add(this.getServiceDesc());
		
		return fieldValues;		
	}
	
	public ArrayList<String> getFieldNamesList() {
		
		ArrayList<String> fieldNames = new ArrayList<String>();
		
		fieldNames.add("Service Code");
		fieldNames.add("Description");

		return fieldNames;			
	}
	
	public static Services select() {

		ArrayList<Services> roomCategoriesServicesList = InformationProcessing.retrieveServices();
		ArrayList<DatabaseObject> databaseObjectList = (ArrayList<DatabaseObject>) ((ArrayList<?>) roomCategoriesServicesList);
		
		Services selectedRecord = (Services)MenuUtilities.paginatedRecordSelection(databaseObjectList);
		
		return selectedRecord;
	}
}
