
public class Rooms {

	private int hotelId;
	private String roomNumber;
	private int maxAllowedOcc;
	private double rate;
	private String categoryCode;
	
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
}
