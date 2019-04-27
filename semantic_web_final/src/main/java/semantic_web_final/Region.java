package semantic_web_final;

public class Region {
	String namespace;
	String regionName;
	
	public Region(String namespace, String rn) {
		this.namespace = namespace;
		this.regionName = rn;
		
	}

	@Override
	public String toString() {
		return "Region [regionName=" + regionName + "]";
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public boolean equals(Object obj) {
	    if (obj == null) return false;
	    if (!(obj instanceof Region))
	        return false;
	    if (obj == this)
	        return true;
	    Region reg = (Region) obj;
	    return this.regionName.equals(reg.regionName);
	}
}
