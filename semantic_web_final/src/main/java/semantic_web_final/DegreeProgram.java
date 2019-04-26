package semantic_web_final;

public class DegreeProgram {
	String IRI;
	String degreeDuration;
	String degreeName;
	DegreeProgram(String IRI, String degreeDuration, String degreeName)
	{
		this.IRI = IRI;
		this.degreeDuration = degreeDuration;
		this.degreeName = degreeName;
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
	public String getDegreeName() {
		return degreeName;
	}
	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}
	
	@Override
	public String toString() {
		return "DegreeProgram [degreeName=" + degreeName + "]";
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
