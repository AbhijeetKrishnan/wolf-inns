import java.util.ArrayList;

public class Stays extends DatabaseObject {
	private int stayId;
	private int hotelId;
	private String roomNumber;
	private int customerId;
	private int numOfGuests;
	private String checkinDate;
	private String checkinTime;
	private String checkoutDate;
	private String checkoutTime;
	private int billingId;

	public int getStayId() {
		return stayId;
	}
	public void setStayId(int stayId) {
		this.stayId = stayId;
	}
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
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getNumOfGuests() {
		return numOfGuests;
	}
	public void setNumOfGuests(int numOfGuests) {
		this.numOfGuests = numOfGuests;
	}
	public String getCheckinDate() {
		return checkinDate;
	}
	public void setCheckinDate(String checkinDate) {
		this.checkinDate = checkinDate;
	}
	public String getCheckinTime() {
		return checkinTime;
	}
	public void setCheckinTime(String checkinTime) {
		this.checkinTime = checkinTime;
	}
	public String getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public String getCheckoutTime() {
		return checkoutTime;
	}
	public void setCheckoutTime(String checkoutTime) {
		this.checkoutTime = checkoutTime;
	}
	public int getBillingId() {
		return billingId;
	}
	public void setBillingId(int billingId) {
		this.billingId = billingId;
	}

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof Stays)) {return false;}
		Stays s = (Stays) o;
		return (stayId==s.stayId && hotelId==s.hotelId &&
                        roomNumber.equals(s.roomNumber) && customerId==s.customerId &&
                        numOfGuests==s.numOfGuests && checkinDate.equals(s.checkinDate) &&
                        checkinTime.equals(s.checkinTime) && checkoutDate.equals(s.checkoutDate) &&
                        checkoutTime.equals(s.checkoutTime) && billingId==s.billingId);
	}
	
	
    public ArrayList<String> toStringArrayList() {
		
		ArrayList<String> fieldValues = new ArrayList<String>();
		
		fieldValues.add(Integer.toString(this.getStayId()));
		fieldValues.add(Integer.toString(this.getHotelId()));
		fieldValues.add(this.getRoomNumber());
		fieldValues.add(Integer.toString(this.getCustomerId()));
		fieldValues.add(Integer.toString(this.getNumOfGuests()));
		fieldValues.add(this.getCheckinDate());
		fieldValues.add(this.getCheckinTime());
		fieldValues.add(this.getCheckoutDate());
		fieldValues.add(this.getCheckoutTime());
		fieldValues.add(Integer.toString(this.getBillingId()));	
		
		return fieldValues;		
	}
	
	public ArrayList<String> getFieldNamesList() {
		
		ArrayList<String> fieldNames = new ArrayList<String>();
		
		fieldNames.add("Stay Id");
		fieldNames.add("Hotel Id");
		fieldNames.add("Room Number");
		fieldNames.add("Customer Id");
		fieldNames.add("Number of Guests");
		fieldNames.add("Checkin Date");
		fieldNames.add("Checkin Time");
		fieldNames.add("Checkout Date");
		fieldNames.add("Checkout Time");
		fieldNames.add("Billing Id");

		return fieldNames;			
	}
	
	
	public static Stays select() {
		ArrayList<Stays> staffList = InformationProcessing.retrieveAllStays();
		ArrayList<DatabaseObject> databaseObjectList = (ArrayList<DatabaseObject>) ((ArrayList<?>) staffList);
		
		Stays selectedRecord = (Stays)MenuUtilities.paginatedRecordSelection(databaseObjectList);
		
		return selectedRecord;
	}
}
