package semantic_web_final;

public class Paper {

	String IRI;
	String paperId;
	String name;
	String publisherName;

	Paper(String IRI, String paperId, String name, String publisherName) {
		this.IRI = IRI;
		this.paperId = paperId;
		this.name = name;
		this.publisherName = publisherName;
	}
 
	public String getIRI() {
		return IRI;
	}

	public void setIRI(String iRI) {
		IRI = iRI;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	@Override
	public String toString() {
		return "Paper [paperId=" + paperId + ", name=" + name + ", publisherName=" + publisherName + "]";
	}
	
	public boolean equals(Object obj) {
	    if (obj == null) return false;
	    if (!(obj instanceof Paper))
	        return false;
	    if (obj == this)
	        return true;
	    Paper pap = (Paper) obj;
	    return this.IRI.equals(pap.IRI);
	}
}