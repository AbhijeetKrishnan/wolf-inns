public class Customers {
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
}
