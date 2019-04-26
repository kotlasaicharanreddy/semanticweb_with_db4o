package semantic_web_final;

import java.util.ArrayList;

public class TripleStore {
	ArrayList<Triple> triple_list;
	TripleStore()
	{
		triple_list = new ArrayList<Triple>();
	}
	public ArrayList<Triple> getTriple() {
		return triple_list;
	}
	public void addTriple(Triple triple) {
		triple_list.add(triple);
	}
}
