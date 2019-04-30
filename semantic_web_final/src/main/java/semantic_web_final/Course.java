package semantic_web_final;

public class Course {
	String courseCredits;
	String courseHours;
	String name;
	String IRI;
	Course(String courseCredits, String courseHours, String name, String IRI)
	{
		this.courseCredits = courseCredits;
		this.courseHours = courseHours;
		this.name = name;
		this.IRI = IRI;
	}
	
	public String getCourseCredits() {
		return courseCredits;
	}

	public void setCourseCredits(String courseCredits) {
		this.courseCredits = courseCredits;
	}

	public String getCourseHours() {
		return courseHours;
	}

	public void setCourseHours(String courseHours) {
		this.courseHours = courseHours;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public String getIRI() {
		return IRI;
	}

	public void setIRI(String iRI) {
		IRI = iRI;
	}

	@Override
	public String toString() {
		return "Course [name=" + name + "]";
	}
	public boolean equals(Object obj) {
	    if (obj == null) return false;
	    if (!(obj instanceof Course))
	        return false;
	    if (obj == this)
	        return true;
	    Course cour = (Course) obj;
	    return this.IRI.equals(cour.IRI);
	}
}
