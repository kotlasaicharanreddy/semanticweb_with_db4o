package semantic_web_final;
import java.io.IOException;
import java.util.Scanner;
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

	public static void main(String[] args) throws OWLOntologyCreationException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		ObjectContainer database = Db4o.openFile("resources/Triples");
		ObjectSet<Triple> result=database.queryByExample(new Triple(null,null,null));
		for(Object found : result)
		{
			Object subject = ((Triple<?, ?, ?>) found).getSubject();
			Object predicate = ((Triple<?, ?, ?>) found).getPredicate();
			Object object = ((Triple<?, ?, ?>) found).getObject();
			database.delete(subject);
			database.delete(predicate);
			database.delete(object);
			database.delete(found);
		}
		database.close(); 
		
		
		
		OwlApi_Class owl = new OwlApi_Class("resources/University.owl");
		owl.get_Classes();
		Helper.front();
		
		}
	}
	

