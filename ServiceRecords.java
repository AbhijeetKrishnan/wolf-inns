

import java.util.ArrayList;

public class ServiceRecords extends DatabaseObject {
	private int stayId;
	private String serviceCode;
	private int staffId;
	private String serviceDate;
	private String serviceTime;

	public int getStayId() {
		return stayId;
	}
	public void setStayId(int stayId) {
		this.stayId = stayId;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public int getStaffId() {
		return staffId;
	}
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	public String getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}
	public String getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
        
       
        
    public ArrayList<String> toStringArrayList() {
		
		ArrayList<String> fieldValues = new ArrayList<String>();
		
		fieldValues.add(Integer.toString(this.getStayId()));
		fieldValues.add(this.getServiceCode());
        fieldValues.add(Integer.toString(this.getStaffId()));
        fieldValues.add(this.getServiceDate());
        fieldValues.add(this.getServiceTime());
		
		
		return fieldValues;		
	}
	
	public ArrayList<String> getFieldNamesList() {
		
		ArrayList<String> fieldNames = new ArrayList<String>();
		
		fieldNames.add("Stay Id");
		fieldNames.add("Service Code");
        fieldNames.add("Staff Id");
        fieldNames.add("Service Date");
        fieldNames.add("Service Time");

		return fieldNames;			
	}
	
	
	public static ServiceRecords select() {
		ArrayList<ServiceRecords> serviceRecordsList = MaintainingServiceRecords.retrieveAllServiceRecords();
		ArrayList<DatabaseObject> databaseObjectList = (ArrayList<DatabaseObject>) ((ArrayList<?>) serviceRecordsList);
		
		ServiceRecords selectedRecord = (ServiceRecords) MenuUtilities.paginatedRecordSelection(databaseObjectList);
		
		return selectedRecord;
	}

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof ServiceRecords)) {return false;}
		ServiceRecords s = (ServiceRecords) o;
		return (stayId==s.stayId && serviceCode.equals(s.serviceCode) &&
                        staffId==s.staffId && serviceDate.equals(s.serviceDate) &&
                        serviceTime.equals(s.serviceTime));
	}

    @Override
    public String toString() {
        return String.format("|%12d|%12s|%12d|%12s|%12s|",stayId, serviceCode , staffId ,serviceDate , serviceTime);
    }
        
}
