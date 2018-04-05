public class Hotels {
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
		return (hotelId==h.hotelId && name==h.name &&
                        address==h.address && city==h.city &&
                        state==h.state && phone==h.phone &&
                        managerId==h.managerId);
	}
}
