package semantic_web_final;

public class Region {
	String namespace;
	String name;
	
	public Region(String namespace, String rn) {
		this.namespace = namespace;
		this.name = rn;
		
	}

	@Override
	public String toString() {
		return "Region [name=" + name + "]";
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}
	public boolean equals(Object obj) {
	    if (obj == null) return false;
	    if (!(obj instanceof Region))
	        return false;
	    if (obj == this)
	        return true;
	    Region reg = (Region) obj;
	    return this.name.equals(reg.name);
	}
}
