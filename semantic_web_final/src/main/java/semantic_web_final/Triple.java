package semantic_web_final;

public class Triple <S,P,O>{

	S subject;
	P predicate;
	O object;
	
	Triple(S s, P  p, O o)
	{
		this.subject = s;
		this.predicate = p;
		this.object = o;
	}

	public S getSubject() {
		return subject;
	}

	@Override
	public String toString() {
		return "Triple [subject=" + subject + ", predicate=" + predicate + ", object=" + object + "]";
	}

	public void setSubject(S subject) {
		this.subject = subject;
	}

	public P getPredicate() {
		return predicate;
	}

	public void setPredicate(P predicate) {
		this.predicate = predicate;
	}

	public O getObject() {
		return object;
	}

	public void setObject(O object) {
		this.object = object;
	}
	
	
}
