import java.util.ArrayList;

public class Rooms extends DatabaseObject {
	private int hotelId;
	private String roomNumber;
	private int maxAllowedOcc;
	private double rate;
	private String categoryCode;
	private String available;

	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public int getMaxAllowedOcc() {
		return maxAllowedOcc;
	}
	public void setMaxAllowedOcc(int maxAllowedOcc) {
		this.maxAllowedOcc = maxAllowedOcc;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof Rooms)) {return false;}
		Rooms r = (Rooms) o;
		return (hotelId==r.hotelId && roomNumber.equals(r.roomNumber) &&
                        maxAllowedOcc==r.maxAllowedOcc && rate==r.rate &&
                        categoryCode.equals(r.categoryCode) && available.equals(r.available));
	}
    
    /**
     * Return array list of field names of class
     */	
	public ArrayList<String> toStringArrayList() {
		
		ArrayList<String> fieldValues = new ArrayList<String>();
		
		fieldValues.add(Integer.toString(this.getHotelId()));
		fieldValues.add(this.getRoomNumber());
		fieldValues.add(Integer.toString(this.getMaxAllowedOcc()));
		fieldValues.add(Double.toString(this.getRate()));
		fieldValues.add(this.getCategoryCode());
		fieldValues.add(this.getAvailable());
		
		return fieldValues;		
	}
    
	/**
     * Return array list of field names of class
     */	
	public ArrayList<String> getFieldNamesList() {
		
		ArrayList<String> fieldNames = new ArrayList<String>();
		
		fieldNames.add("Hotel Id");
		fieldNames.add("Room Number");
		fieldNames.add("Max Allowed Occupancy");
		fieldNames.add("Rate");
		fieldNames.add("Room Category Code");
		fieldNames.add("Available");

		return fieldNames;			
	}
	
    /**
     * Return an object
     */
	public static Rooms select() {
		// Get the list of service staff records to be selected from
		ArrayList<Rooms> roomsList = InformationProcessing.retrieveAllRooms();
		ArrayList<DatabaseObject> databaseObjectList = (ArrayList<DatabaseObject>) ((ArrayList<?>) roomsList);
		
		// Select a service staff record, null if none selected
		Rooms selectedRecord = (Rooms)MenuUtilities.paginatedRecordSelection(databaseObjectList);
		
		return selectedRecord;
	}
}
