import java.util.ArrayList;

public class RoomCategoriesServices extends DatabaseObject {
	private String categoryCode;
	private String serviceCode;

	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof RoomCategoriesServices)) {return false;}
		RoomCategoriesServices r = (RoomCategoriesServices) o;
		return (categoryCode.equals(r.categoryCode) &&
                        serviceCode.equals(r.serviceCode));
	}
	
    public ArrayList<String> toStringArrayList() {
		
		ArrayList<String> fieldValues = new ArrayList<String>();
		
		fieldValues.add(this.getCategoryCode());
		fieldValues.add(this.getServiceCode());
		
		return fieldValues;		
	}
	
	public ArrayList<String> getFieldNamesList() {
		
		ArrayList<String> fieldNames = new ArrayList<String>();
		
		fieldNames.add("Room Category Code");
		fieldNames.add("Service Code");

		return fieldNames;			
	}
	
	public static RoomCategoriesServices select() {

		ArrayList<RoomCategoriesServices> roomCategoriesServicesList = InformationProcessing.retrieveAllRoomCategoriesServices();
		ArrayList<DatabaseObject> databaseObjectList = (ArrayList<DatabaseObject>) ((ArrayList<?>) roomCategoriesServicesList);
		
		RoomCategoriesServices selectedRecord = (RoomCategoriesServices)MenuUtilities.paginatedRecordSelection(databaseObjectList);
		
		return selectedRecord;
	}
}
