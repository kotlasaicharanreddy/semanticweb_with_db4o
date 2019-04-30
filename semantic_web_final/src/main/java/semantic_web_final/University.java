package semantic_web_final;

public class University {
	String IRI;
	String name;
	public University(String iRI, String name) {
		this.IRI = iRI;
		this.name = name;
	}
	public String getIRI() {
		return IRI;
	}
	public void setIRI(String iRI) {
		this.IRI = iRI;
	}
	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "University [name=" + name + "]";
	}
	
	public boolean equals(Object obj) {
	    if (obj == null) return false;
	    if (!(obj instanceof University))
	        return false;
	    if (obj == this)
	        return true;
	    University stu = (University) obj;
	    return this.IRI.equals(stu.IRI);
	}
	

}
