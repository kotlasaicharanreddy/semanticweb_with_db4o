package semantic_web_final;

public class Classes {
	String IRI;
	String classname;
	public String getIRI() {
		return IRI;
	}
	public void setIRI(String iRI) {
		IRI = iRI;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	Classes (String IRI , String classname)
	{
		IRI = this.IRI;
		classname = this.classname;
	}
	@Override
	public String toString() {
		return "Class [IRI=" + IRI + ", classname=" + classname + "]";
	}
	
	public boolean equals(Object obj)
	{
		if(obj == null)
			return false;
		if(!(obj instanceof Classes))
			return false;
		if(this == obj)
			return true;
		Classes cla = (Classes) obj;
		return this.IRI.equals(cla.IRI);
	}

}
