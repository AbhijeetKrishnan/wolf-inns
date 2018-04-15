import java.util.ArrayList;

public class JobTitles extends DatabaseObject{
	private String titleCode;
	private String titleDesc;

	public String getTitleCode() {
		return titleCode;
	}
	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}
	public String getTitleDesc(){
		return titleDesc;
	}
	public void setTitleDesc(String titleDesc){
		this.titleDesc=titleDesc;
	}
    
    /**
     * Return array list of field values of object
     */
    public ArrayList<String> toStringArrayList() {
		
		ArrayList<String> fieldValues = new ArrayList<String>();
		
		fieldValues.add(this.getTitleCode());
		fieldValues.add(this.getTitleDesc());

		return fieldValues;		
	}
	
    /**
     * Return array list of field names of class
     */
	public ArrayList<String> getFieldNamesList() {
		
		ArrayList<String> fieldNames = new ArrayList<String>();
		
		fieldNames.add("titleCode");
		fieldNames.add("titleDesc");

		return fieldNames;			
	}
	
	/**
     * Return an object
     */
	public static JobTitles select() {
		ArrayList<JobTitles> deptList = InformationProcessing.retrieveAllJobTitles();
		ArrayList<DatabaseObject> databaseObjectList = (ArrayList<DatabaseObject>) ((ArrayList<?>) deptList);
		
		JobTitles selectedRecord = (JobTitles) MenuUtilities.paginatedRecordSelection(databaseObjectList);
		
		return selectedRecord;
	}

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof JobTitles)) {return false;}
		JobTitles j = (JobTitles) o;
		return (titleCode.equals(j.titleCode) &&
                        titleDesc.equals(j.titleDesc));
	}
}
