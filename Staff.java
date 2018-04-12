import java.util.ArrayList;

public class Staff extends DatabaseObject {
	private int staffId;
	private String name;
	private String titleCode;
	private String deptCode;
	private String address;
	private String city;
	private String state;
	private String phone;
	private String dob;

	public int getStaffId() {
		return staffId;
	}
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitleCode() {
		return titleCode;
	}
	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof Staff)) {return false;}
		Staff s = (Staff) o;
		return (staffId==s.staffId && name.equals(s.name) &&
                        titleCode.equals(s.titleCode) && deptCode.equals(s.deptCode) &&
                        address.equals(s.address) && city.equals(s.city) &&
                        state.equals(s.state) && phone.equals(s.phone) &&
                        dob.equals(s.dob));
	}
	
	public ArrayList<String> toStringArrayList() {
		
		ArrayList<String> fieldValues = new ArrayList<String>();
		
		fieldValues.add(Integer.toString(this.getStaffId()));
		fieldValues.add(this.getName());
		fieldValues.add(this.getTitleCode());
		fieldValues.add(this.getDeptCode());
		fieldValues.add(this.getAddress());
		fieldValues.add(this.getCity());
		fieldValues.add(this.getState());
		fieldValues.add(this.getPhone());
		fieldValues.add(this.getDob());		
		
		return fieldValues;		
	}
	
	public ArrayList<String> getFieldNamesList() {
		
		ArrayList<String> fieldNames = new ArrayList<String>();
		
		fieldNames.add("Staff Id");
		fieldNames.add("Name");
		fieldNames.add("Job Title Code");
		fieldNames.add("Department Code");
		fieldNames.add("Address");
		fieldNames.add("City");
		fieldNames.add("State");
		fieldNames.add("Phone");
		fieldNames.add("Date of Birth");

		return fieldNames;			
	}
	
	
	public static Staff selectStaff() {
		ArrayList<Staff> staffList = InformationProcessing.retrieveAllStaff();
		ArrayList<DatabaseObject> databaseObjectList = (ArrayList<DatabaseObject>) ((ArrayList<?>) staffList);
		
		Staff selectedRecord = (Staff)MenuUtilities.paginatedRecordSelection(databaseObjectList);
		
		return selectedRecord;
	}
}
