package semantic_web_final;

import java.io.IOException;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import com.db4o.Db4o;
import com.db4o.query.Query;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Ontology_test {

	public static void main(String[] args) throws OWLOntologyCreationException, IOException {
		// TODO Auto-generated method stub
		//OwlApi_Class owl = new OwlApi_Class("resources/University.owl");
		//owl.get_Classes();
		
		
		
		//write in different function
		isTaughtBy("V N Muralidharan",":teaches","Algorithms");
	}
	public static void isTaughtBy(String prof_name, String predicate, String course)
	{
		String defaultPrefix = "http://www.semanticweb.org/deepakd/ontologies/2019/3/university#";
		ObjectContainer database = Db4o.openFile("resources/Triples");
		Query query = database.query();
		query.constrain(Triple.class);
		query.descend("predicate").descend("propertyname").constrain(predicate);
		ObjectSet<Triple> objset= query.execute();
		int flag = 0;
		for(Triple tr : objset)
		{
			
			Professor prof = (Professor) tr.getSubject();
			Course cour = (Course) tr.getObject();
			if(prof.getName().equals(prof_name) && cour.getCourseName().equals(course))
			{	flag = 1;
				ObjectProperty isTaughtBy = new ObjectProperty(defaultPrefix+"isTaughtBy",":isTaughtBy");
				Triple<Course,ObjectProperty,Professor> triple = new Triple<Course,ObjectProperty,Professor>(cour,isTaughtBy,prof);
				System.out.println(triple);
				database.store(triple);
			}
			
		}
		if(flag == 0)
			System.out.println("No such Entry");
		database.close();
	}
}
