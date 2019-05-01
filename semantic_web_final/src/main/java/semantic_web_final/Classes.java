package semantic_web_final;

public class Classes {
	String IRI;
	String name;
	public String getIRI() {
		return IRI;
	}
	public void setIRI(String iRI) {
		IRI = iRI;
	}
	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
	}
	Classes (String IRI , String name)
	{ 
		this.IRI = IRI;
		this.name = name;
	}
	@Override
	public String toString() {
		return "Class [  name=" + name + "]";
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
