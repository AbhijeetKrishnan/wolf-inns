import java.util.ArrayList;

public class Customers extends DatabaseObject {
	private int customerId;
	private String name;
	private String dob;
	private String phone;
	private String email;

	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof Customers)) {return false;}
		Customers c = (Customers) o;
		return (customerId==c.customerId && name.equals(c.name) &&
                        dob.equals(c.dob) && phone.equals(c.phone) &&
                        email.equals(c.email));
	}

	public ArrayList<String> toStringArrayList() {
		
		ArrayList<String> fieldValues = new ArrayList<String>();
		
		fieldValues.add(Integer.toString(this.getCustomerId()));
		fieldValues.add(this.getName());
		fieldValues.add(this.getDob());
		fieldValues.add(this.getPhone());
		fieldValues.add(this.getEmail());
		
		return fieldValues;		
	}
	
	public ArrayList<String> getFieldNamesList() {
		
		ArrayList<String> fieldNames = new ArrayList<String>();
		
		fieldNames.add("Customer Id");
		fieldNames.add("Name");
		fieldNames.add("DOB");
		fieldNames.add("Phone");
		fieldNames.add("Email");

		return fieldNames;			
	}

	public static Customers select() {
		ArrayList<Customers> customerList = InformationProcessing.retrieveAllCustomers();
		ArrayList<DatabaseObject> databaseObjectList = (ArrayList<DatabaseObject>) ((ArrayList<?>) customerList);
		
		Customers selectedRecord = (Customers)MenuUtilities.paginatedRecordSelection(databaseObjectList);
		
		return selectedRecord;
	}
}
