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
		return "Region [namespace=" + namespace + ", regionName=" + regionName + "]";
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

}
