public class JobTitles{
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

	@Override
	public boolean equals(Object o) {
		if (o==this) {return true;}
		if (!(o instanceof JobTitles)) {return false;}
		JobTitles j = (JobTitles) o;
		return (titleCode.equals(j.titleCode) &&
                        titleDesc.equals(j.titleDesc));
	}
}
