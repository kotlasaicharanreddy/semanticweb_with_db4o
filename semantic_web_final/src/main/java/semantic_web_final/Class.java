package semantic_web_final;

public class Class {
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
	Class (String IRI , String classname)
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
		if(!(obj instanceof Class))
			return false;
		if(this == obj)
			return true;
		Class cla = (Class) obj;
		return this.IRI.equals(cla.IRI);
	}

}
