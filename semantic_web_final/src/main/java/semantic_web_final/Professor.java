package semantic_web_final;

public class Professor {

	String IRI;
	String name;
	String email;
	String gender;
	String ID;
	
	Professor(String IRI, String name, String email, String gender, String ID)
	{
		this.IRI = IRI;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.ID = ID;
	
	}

	public String getIRI() {
		return IRI;
	}

	public void setIRI(String IRI) {
		this.IRI = IRI;
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
	
	@Override
	public String toString() {
		return "Professor [name=" + name + "]";
	}
	
	public boolean equals(Object obj) {
	    if (obj == null) return false;
	    if (!(obj instanceof Professor))
	        return false;
	    if (obj == this)
	        return true;
	    Professor stu = (Professor) obj;
	    return this.IRI.equals(stu.IRI);
	}
}
