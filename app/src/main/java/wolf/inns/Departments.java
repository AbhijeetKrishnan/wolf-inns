package wolf.inns;

import java.util.ArrayList;

public class Departments extends DatabaseObject {
  private String deptCode;
  private String deptDesc;

  public String getDeptCode() {
    return deptCode;
  }

  public void setDeptCode(String deptCode) {
    this.deptCode = deptCode;
  }

  public String getDeptDesc() {
    return deptDesc;
  }

  public void setDeptDesc(String deptDesc) {
    this.deptDesc = deptDesc;
  }

  /** Return array list of field values of object */
  public ArrayList<String> toStringArrayList() {

    ArrayList<String> fieldValues = new ArrayList<String>();

    fieldValues.add(this.getDeptCode());
    fieldValues.add(this.getDeptDesc());

    return fieldValues;
  }

  /** Return array list of field names of class */
  public ArrayList<String> getFieldNamesList() {

    ArrayList<String> fieldNames = new ArrayList<String>();

    fieldNames.add("deptCode");
    fieldNames.add("deptDesc");

    return fieldNames;
  }

  /** Return an object */
  public static Departments select() {
    ArrayList<Departments> deptList = InformationProcessing.retrieveAllDepartments();
    ArrayList<DatabaseObject> databaseObjectList =
        (ArrayList<DatabaseObject>) ((ArrayList<?>) deptList);

    Departments selectedRecord =
        (Departments) MenuUtilities.paginatedRecordSelection(databaseObjectList);

    return selectedRecord;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Departments)) {
      return false;
    }
    Departments d = (Departments) o;
    return (deptCode.equals(d.deptCode) && deptDesc.equals(d.deptDesc));
  }
}
