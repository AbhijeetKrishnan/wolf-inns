public class ServiceStaff {
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
}
