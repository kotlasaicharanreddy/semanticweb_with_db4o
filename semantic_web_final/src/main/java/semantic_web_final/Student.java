package semantic_web_final;

public class Student {
	
	String IRI;
	String name;
	String email;
	String gender;
	String ID;
	String Cgpa;
	public Student(String iRI, String name, String email, String gender, String iD, String cgpa) {
		super();
		IRI = iRI;
		this.name = name;
		this.email = email;
		this.gender = gender;
		ID = iD;
		Cgpa = cgpa;
	}
	public String getIRI() {
		return IRI;
	}
	public void setIRI(String iRI) {
		IRI = iRI;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getCgpa() {
		return Cgpa;
	}
	public void setCgpa(String cgpa) {
		Cgpa = cgpa;
	}
	
	@Override
	public String toString() {
		return "Student [name=" + name + "]";
	}
	
	public boolean equals(Object obj) {
	    if (obj == null) return false;
	    if (!(obj instanceof Student))
	        return false;
	    if (obj == this)
	        return true;
	    Student stu = (Student) obj;
	    return this.IRI.equals(stu.IRI);
	}
}
