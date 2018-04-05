public class RoomCategories{
	private String categoryCode;
	private String categoryDesc;

	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof RoomCategories)) {return false;}
		RoomCategories r = (RoomCategories) o;
		return (categoryCode==r.categoryCode &&
                        categoryDesc==r.categoryDesc);
	}
}
