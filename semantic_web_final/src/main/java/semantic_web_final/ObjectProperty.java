package semantic_web_final;

public class ObjectProperty {
	
	String IRI;
	String propertyname;
	ObjectProperty(String IRI, String propertyname)
	{
		this.IRI = IRI;
		this.propertyname = propertyname;
	}

	public String getIRI() {
		return IRI;
	}

	public String getPropertyname() {
		return propertyname;
	}

	public void setPropertyname(String propertyname) {
		this.propertyname = propertyname;
	}

	public void setIRI(String IRI) {
		this.IRI = IRI;
	}
	@Override
	public String toString() {
		return "ObjectProperty [propertyname=" + propertyname + "]";
	}
}
