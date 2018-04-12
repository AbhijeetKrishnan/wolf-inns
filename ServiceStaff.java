import java.util.ArrayList;

public class ServiceStaff extends DatabaseObject {
	private int staffId;
	private int hotelId;

	public int getStaffId() {
		return staffId;
	}
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof ServiceStaff)) {return false;}
		ServiceStaff s = (ServiceStaff) o;
		return (staffId==s.staffId && hotelId==s.hotelId);
	}
	
	public ArrayList<String> toStringArrayList() {
		
		ArrayList<String> fieldValues = new ArrayList<String>();
		
		fieldValues.add(Integer.toString(this.getStaffId()));
		fieldValues.add(Integer.toString(this.getHotelId()));
		
		return fieldValues;		
	}
	
	public ArrayList<String> getFieldNamesList() {
		
		ArrayList<String> fieldNames = new ArrayList<String>();
		
		fieldNames.add("Staff Id");
		fieldNames.add("Hotel Id");

		return fieldNames;			
	}
	
	public static ServiceStaff selectServiceStaff() {
		// Get the list of service staff records to be selected from
		ArrayList<ServiceStaff> serviceStaffList = InformationProcessing.retrieveAllServiceStaff();
		ArrayList<DatabaseObject> databaseObjectList = (ArrayList<DatabaseObject>) ((ArrayList<?>) serviceStaffList);
		
		// Select a service staff record, null if none selected
		ServiceStaff selectedRecord = (ServiceStaff)MenuUtilities.paginatedRecordSelection(databaseObjectList);
		
		return selectedRecord;
	}
}
