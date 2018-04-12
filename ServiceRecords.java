public class ServiceRecords {
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

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof ServiceRecords)) {return false;}
		ServiceRecords s = (ServiceRecords) o;
		return (stayId==s.stayId && serviceCode.equals(s.serviceCode) &&
                        staffId==s.staffId && serviceDate.equals(s.serviceDate) &&
                        serviceTime.equals(s.serviceTime));
	}
}
