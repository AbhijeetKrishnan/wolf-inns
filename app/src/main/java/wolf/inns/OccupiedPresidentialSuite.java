package wolf.inns;

public class OccupiedPresidentialSuite {
  private int hotelId;
  private String roomNumber;
  private int cateringStaffId;
  private int roomServiceStaffId;

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

  public int getCateringStaffId() {
    return cateringStaffId;
  }

  public void setCateringStaffId(int cateringStaffId) {
    this.cateringStaffId = cateringStaffId;
  }

  public int getRoomServiceStaffId() {
    return roomServiceStaffId;
  }

  public void setRoomServiceStaffId(int roomServiceStaffId) {
    this.roomServiceStaffId = roomServiceStaffId;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof OccupiedPresidentialSuite)) {
      return false;
    }
    OccupiedPresidentialSuite ops = (OccupiedPresidentialSuite) o;
    return (hotelId == ops.hotelId
        && roomNumber.equals(ops.roomNumber)
        && cateringStaffId == ops.cateringStaffId
        && roomServiceStaffId == ops.roomServiceStaffId);
  }
}
