import java.util.ArrayList;

public class RoomCategories extends DatabaseObject {
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
		return (categoryCode.equals(r.categoryCode) &&
                        categoryDesc.equals(r.categoryDesc));
	}
	
	 public ArrayList<String> toStringArrayList() {
			
		ArrayList<String> fieldValues = new ArrayList<String>();
		
		fieldValues.add(this.getCategoryCode());
		fieldValues.add(this.getCategoryDesc());
		
		return fieldValues;		
	}
		
	public ArrayList<String> getFieldNamesList() {
		
		ArrayList<String> fieldNames = new ArrayList<String>();
		
		fieldNames.add("Room Category Code");
		fieldNames.add("Description");

		return fieldNames;			
	}
		
	public static RoomCategories select() {

		ArrayList<RoomCategories> roomCategoriesServicesList = InformationProcessing.retrieveAllRoomCategories();
		ArrayList<DatabaseObject> databaseObjectList = (ArrayList<DatabaseObject>) ((ArrayList<?>) roomCategoriesServicesList);
		
		RoomCategories selectedRecord = (RoomCategories)MenuUtilities.paginatedRecordSelection(databaseObjectList);
		
		return selectedRecord;
	}
}
