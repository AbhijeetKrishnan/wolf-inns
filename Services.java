public class Services {
	private String serviceCode;
	private String serviceDesc;

	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof Services)) {return false;}
		Services s = (Services) o;
		return (serviceCode==s.serviceCode && serviceDesc==s.serviceDesc);
	}
}
