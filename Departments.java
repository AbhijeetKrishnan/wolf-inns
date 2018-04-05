public class Departments {
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

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof Departments)) {return false;}
		Departments d = (Departments) o;
		return (deptCode==d.deptCode &&
                        deptDesc==d.deptDesc);
	}
}
