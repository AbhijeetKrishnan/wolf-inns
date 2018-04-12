import java.util.ArrayList;

public class Hotels extends DatabaseObject {
	private int hotelId;
	private String name;
	private String address;
	private String city;
	private String state;
	private String phone;
	private int managerId;

	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof Hotels)) {return false;}
		Hotels h = (Hotels) o;
		return (hotelId==h.hotelId && name.equals(h.name) &&
                        address.equals(h.address) && city.equals(h.city) &&
                        state.equals(h.state) && phone.equals(h.phone) &&
                        managerId==h.managerId);
	}	
	
	public ArrayList<String> toStringArrayList() {
		
		ArrayList<String> fieldValues = new ArrayList<String>();
		
		fieldValues.add(Integer.toString(this.getHotelId()));
		fieldValues.add(this.getName());
		fieldValues.add(this.getAddress());
		fieldValues.add(this.getCity());
		fieldValues.add(this.getState());
		fieldValues.add(this.getPhone());
		fieldValues.add(Integer.toString(this.getManagerId()));
		
		return fieldValues;		
	}
	
	public ArrayList<String> getFieldNamesList() {
		
		ArrayList<String> fieldNames = new ArrayList<String>();
		
		fieldNames.add("Hotel Id");
		fieldNames.add("Name");
		fieldNames.add("Address");
		fieldNames.add("City");
		fieldNames.add("State");
		fieldNames.add("Phone");
		fieldNames.add("Manager Id");

		return fieldNames;			
	}
	
	
	public static Hotels selectHotel() {
		ArrayList<Hotels> hotelList = InformationProcessing.retrieveAllHotels();
		ArrayList<DatabaseObject> databaseObjectList = (ArrayList<DatabaseObject>) ((ArrayList<?>) hotelList);
		
		Hotels selectedRecord = (Hotels)MenuUtilities.paginatedRecordSelection(databaseObjectList);
		
		return selectedRecord;
	}
}
