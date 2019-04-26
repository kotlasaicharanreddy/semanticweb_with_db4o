package semantic_web_final;

public class Course {
	String courseCredits;
	String courseHours;
	String courseName;
	String IRI;
	Course(String courseCredits, String courseHours, String courseName, String IRI)
	{
		this.courseCredits = courseCredits;
		this.courseHours = courseHours;
		this.courseName = courseName;
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

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getIRI() {
		return IRI;
	}

	public void setIRI(String iRI) {
		IRI = iRI;
	}

	@Override
	public String toString() {
		return "Course [courseName=" + courseName + "]";
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
