package semantic_web_final;

public class University {
	String IRI;
	String universityName;
	public University(String iRI, String universityName) {
		this.IRI = iRI;
		this.universityName = universityName;
	}
	public String getIRI() {
		return IRI;
	}
	public void setIRI(String iRI) {
		this.IRI = iRI;
	}
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	@Override
	public String toString() {
		return "University [universityName=" + universityName + "]";
	}
	

}
