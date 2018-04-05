public class RoomCategoriesServices {
	private String categoryCode;
	private String serviceCode;

	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof RoomCategoriesServices)) {return false;}
		RoomCategoriesServices r = (RoomCategoriesServices) o;
		return (categoryCode==r.categoryCode &&
                        serviceCode==r.serviceCode);
	}
}
