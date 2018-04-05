public class Stays {
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
                        roomNumber==s.roomNumber && customerId==s.customerId &&
                        numOfGuests==s.numOfGuests && checkinDate==s.checkinDate &&
                        checkinTime==s.checkinTime && checkoutDate==s.checkoutDate &&
                        checkoutTime==s.checkoutTime && billingId==s.billingId);
	}
}
