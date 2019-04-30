package semantic_web_final;

public class DegreeProgram {
	String IRI;
	String degreeDuration;
	String name;
	DegreeProgram(String IRI, String degreeDuration, String name)
	{
		this.IRI = IRI;
		this.degreeDuration = degreeDuration;
		this.name = name;
	}
	public String getIRI() {
		return IRI;
	}
	public void setIRI(String iRI) {
		IRI = iRI;
	}
	public String getDegreeDuration() {
		return degreeDuration;
	}
	public void setDegreeDuration(String degreeDuration) {
		this.degreeDuration = degreeDuration;
	}
	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "DegreeProgram [name=" + name + "]";
	}
	public boolean equals(Object obj)
	{
		if(obj == null)
			return false;
		if(!(obj instanceof DegreeProgram))
			return false;
		if(this == obj)
			return true;
		DegreeProgram deg = (DegreeProgram) obj;
		return this.IRI.equals(deg.IRI);
	}
}
